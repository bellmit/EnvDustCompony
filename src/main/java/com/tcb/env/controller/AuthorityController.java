/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.model.AuthorityModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Authority;
import com.tcb.env.service.IAuthorityService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：权限控制</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月6日上午10:40:51
 * @since	EnvDust 1.0.0
 * 
 */
@Controller
@RequestMapping("/AuthorityController")
public class AuthorityController {
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "AuthorityController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(AuthorityController.class);

	/**
	 * 声明gson对象
	 */
	Gson gson = new Gson();
	/**
	 * 声明User服务
	 */
	@Resource
	private IUserService userService;
	@Resource
	private IAuthorityService authorityService;
	/**
	 * 声明资源文件
	 */
	@Resource
	private Dom4jConfig dom4jConfig;
	
	/**
	 * <p>[功能描述]：权限查询</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午11:39:04
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/queryAuthority", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<AuthorityModel> queryAuthority(
			AuthorityModel authorityModel, HttpSession httpsession) {
		//创建类
		ResultListModel<AuthorityModel> resultListModel = new ResultListModel<AuthorityModel>();
		List<AuthorityModel> listAuthorityModel = new ArrayList<AuthorityModel>();
		List<Authority> listAuthority = new ArrayList<Authority>();
		//转换成组织
		Authority authority = ConvertAuthority(authorityModel,httpsession);
		int count = authorityService.getAuthorityCount(authority);
		if (count > 0) {
			//查询数据库得到所有组织
			listAuthority = authorityService.getAuthority(authority);
			//依次转换成model
			for (Authority temp : listAuthority) {
				AuthorityModel authorityModelt = ConvertAuthorityModel(temp);
				if (authorityModelt != null) {
					listAuthorityModel.add(authorityModelt);					
				}
			}
			resultListModel.setRows(listAuthorityModel);
		}
		resultListModel.setTotal(count);
		return resultListModel;
	}
	/**
	 * <p>[功能描述]：新增权限数据</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午11:54:16
	 * @since	EnvDust 1.0.0
	 *
	 * @param authorityModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/insertAuthority", method = { RequestMethod.POST })
	public @ResponseBody ResultModel insertAuthority(AuthorityModel authorityModel,
			HttpSession httpsession) {
		//创建返回result类
		ResultModel resultModel = new ResultModel();
		//如果传入了类正常
		if (authorityModel != null) {
			try {
				//看要添加组织的名称
				Authority authority = new Authority();
				authority.setAuthorityCode(authorityModel.getAuthorityCode());;
				//如果新增的组织名称在数据库中不存在，则返回0.则添加
				if (authorityService.getAuthorityCount(authority) == 0) {
					List<Authority> listauthority = new ArrayList<Authority>();
					listauthority.add(ConvertAuthority(authorityModel, httpsession));		
					//插入组织数据，如果成功则返回添加个数1
					int intresult = authorityService.insertAuthority(listauthority);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增权限组失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此权限组编号，请使用其他权限组编号！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增权限组失败！");
				logger.error(LOG + "：新增权限组失败，信息为:" + e.getMessage());
			}
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：删除数据</p>
	 * 
	 * @author	任崇彬, 2016年4月6日下午3:29:35
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return 
	 */
	@RequestMapping(value = "/deleteAuthority", method = { RequestMethod.POST })
	public @ResponseBody ResultModel deleteAuthority(
			@RequestParam(value = "list[]") List<String> list) {
		ResultModel resultModel = new ResultModel();
		//传入list表示要删除多少条数据
		//如果条数大于0
		if (list != null && list.size() > 0) {
			try {
				List<String> authorityid = new ArrayList<String>();
				for (String authid : list) {
					String authCode = authorityService.getAuthorityCodeById(authid);
					if(!dom4jConfig.getDeDevConfig().getRootAuthority().equals(authCode)){
						authorityid.add(authid);
					}
				}
				//删除oranization数据
				int intresult = authorityService.deleteAuthority(authorityid);
				//如果返回值大于0则删除用户成功
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除权限组失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除权限组失败！");
				logger.error(LOG + "：删除权限组失败，信息为:" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除权限组失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：更改权限组数据</p>
	 * 
	 * @author	任崇彬, 2016年4月6日下午3:51:15
	 * @since	EnvDust 1.0.0
	 *
	 * @param authorityModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/updateAuthority", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updateAuthority(AuthorityModel authorityModel,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		//如果不为空
		if (authorityModel != null) {
			try {
				if (authorityService.getAuthorityExist(
						authorityModel.getAuthorityCode(),authorityModel.getAuthorityId()) == 0) {
					List<Authority> listauthority = new ArrayList<Authority>();
					listauthority.add(ConvertAuthority(authorityModel, httpsession));
					int intresult = authorityService.updateAuthority(listauthority);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新权限组失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此权限组，请使用其他权限组！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新权限组失败！");
				logger.error(LOG + "：更新权限组失败，信息为:" + e.getMessage());
			}
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：model转成权限</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午11:07:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param authorityModel
	 * @param httpsession
	 * @return 
	 */
	private Authority ConvertAuthority(AuthorityModel authorityModel, HttpSession httpsession){
		Authority  authority = new Authority();
		if(authorityModel != null){
			authority.setAuthorityId(authorityModel.getAuthorityId());
			authority.setAuthorityCode(authorityModel.getAuthorityCode());
			authority.setAuthorityName(authorityModel.getAuthorityName());
			authority.setRemark(authorityModel.getRemark());				
	//获取当前操作者并设置
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginuser != null) {
				authority.setOptUser(loginuser.getUserId());
			} else {
				authority.setOptUser(authorityModel.getAuthorityId());
			}
			//操作时间穿默认值就可以了
			authority.setRowCount(authorityModel.getRows());
			authority.setRowIndex((authorityModel.getPage()-1) * authorityModel.getRows());
		}
		return authority;
	}
/**
 * <p>[功能描述]：将权限转换成model</p>
 * 
 * @author	任崇彬, 2016年4月6日上午11:00:10
 * @since	EnvDust 1.0.0
 *
 * @param authority
 * @return 
 */
private AuthorityModel ConvertAuthorityModel(Authority authority){
	AuthorityModel authorityModel = new AuthorityModel();
	if(authority != null){
		authorityModel.setAuthorityId(authority.getAuthorityId());
		authorityModel.setAuthorityCode(authority.getAuthorityCode());
		authorityModel.setAuthorityName(authority.getAuthorityName());
		authorityModel.setRemark(authority.getRemark());
		authorityModel.setOptUserName(userService.getUserNameById(authority.getOptUser(), null));
		authorityModel.setOptTime(DateUtil.TimestampToString(authority.getOptTime(),
				DateUtil.DATA_TIME));
	}
	return authorityModel;
	
}
}
