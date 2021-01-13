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
import com.tcb.env.model.IpModel;

import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Ip;

import com.tcb.env.service.IIpService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月1日下午3:51:50
 * @since	EnvDust 1.0.0
 * 
 */
@Controller
@RequestMapping("/IpController")
public class IpController {
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "IpController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(IpController.class);

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
	private IIpService ipService;
	
	/**
	 * <p>[功能描述]：ip查询</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午2:17:34
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/queryIp", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<IpModel> queryIp(
			IpModel ipModel, HttpSession httpsession) {
		//创建类
		ResultListModel<IpModel> resultListModel = new ResultListModel<IpModel>();
		List<IpModel> listIpModel = new ArrayList<IpModel>();
		List<Ip> listip = new ArrayList<Ip>();
		//转换成组织
		Ip ip = ConvertIp(ipModel,httpsession);
		int count = ipService.getCount(ip);
		if (count > 0) {
			//查询数据库得到所有组织
			listip = ipService.getIp(ip);
			//依次转换成model
			for (Ip temp : listip) {
				IpModel ipModelt = ConvertIpModel(temp);
				if (ipModelt != null) {
					listIpModel.add(ipModelt);					
				}
			}
			resultListModel.setRows(listIpModel);
		}
		resultListModel.setTotal(count);
		return resultListModel;
	}
	/**
	 * <p>[功能描述]：插入数据</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午4:14:53
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/insertIp", method = { RequestMethod.POST })
	public @ResponseBody ResultModel insertIp(IpModel ipModel,
			HttpSession httpsession) {
		//创建返回result类
		ResultModel resultModel = new ResultModel();
		//如果传入了类正常
		if (ipModel != null) {
			try {
				//看要添加组织的名称
				Ip ip = new Ip();
				ip.setIpAddress(ipModel.getIpAddress());
				//如果新增的组织名称在数据库中不存在，则返回0.则添加
				if (ipService.getCount(ip) == 0) {
					List<Ip> listip = new ArrayList<Ip>();
					listip.add(ConvertIp(ipModel, httpsession));		
					//插入组织数据，如果成功则返回添加个数1
					int intresult = ipService.insertIp(listip);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("新增IP失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此IP地址，请使用其他IP地址！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("新增IP失败！");
				logger.error(LOG + "：新增IP失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	
	/**
	 * <p>[功能描述]：删除数据</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午4:16:43
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return 
	 */
	@RequestMapping(value = "/deleteIp", method = { RequestMethod.POST })
	public @ResponseBody ResultModel deleteIp(
			@RequestParam(value = "list[]") List<Integer> list) {
		ResultModel resultModel = new ResultModel();
		//传入list表示要删除多少条数据
		//如果条数大于0
		if (list != null && list.size() > 0) {
			try {
				List<Integer> ipid = new ArrayList<Integer>();
				//list存的是整数类型，所以userid为局部变量用来表示每个整数类型。
				//用For循环来把list依次放入oranizationid中
				for (Integer userid : list) {
					ipid.add(userid);
				}
				//删除oranization数据
				int intresult = ipService.deleteIp(ipid);
				//如果返回值大于0则删除用户成功
				if (intresult > 0) {
					resultModel.setResult(true);
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("删除IP失败！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("删除IP失败！");
				logger.error(LOG + "：删除IP失败，信息为：" + e.getMessage());
			}
		} else {
			resultModel.setResult(false);
			resultModel.setDetail("删除IP失败，错误原因：服务器未接收到删除数据！");
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：修改IP</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午8:45:11
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerModel
	 * @param httpsession
	 * @return 
	 */
	@RequestMapping(value = "/updateIp", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updateIp(IpModel ipModel,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		//如果不为空
		if (ipModel != null) {
			try {
				if (ipService.getIpExist(ipModel.getIpId(),
						ipModel.getIpAddress()) == 0) {
					List<Ip> listip = new ArrayList<Ip>();
					listip.add(ConvertIp(ipModel, httpsession));
					int intresult = ipService.updateIp(listip);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新IP失败！");
					}
				} else {
					resultModel.setResult(false);
					resultModel.setDetail("系统已存在此IP地址，请使用其他IP地址！");
				}
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新IP数据失败！");
				logger.error(LOG + "：更新IP数据失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：model转ip</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午3:19:29
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipModel
	 * @param httpsession
	 * @return 
	 */
	private Ip ConvertIp(IpModel ipModel, HttpSession httpsession){
		Ip ip = new Ip();
		if (ipModel != null) {
			ip.setIpId(ipModel.getIpId());
			ip.setIpAddress(ipModel.getIpAddress());
			ip.setRemark(ipModel.getRemark());
	//获取当前操作者并设置
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginuser != null) {
				ip.setOptUser(loginuser.getUserId());
			} else {
				ip.setOptUser(ipModel.getIpId());
			}
			//操作时间穿默认值就可以了
			ip.setRowCount(ipModel.getRows());
			ip.setRowIndex((ipModel.getPage()-1) * ipModel.getRows());
		}
		return ip;
	}
	/**
	 * <p>[功能描述]：ip转成model </p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午1:49:17
	 * @since	EnvDust 1.0.0
	 *
	 * @param ip
	 * @return 
	 */
	private IpModel ConvertIpModel(Ip ip){
		IpModel ipModel = new IpModel();
		if(ip != null){
			ipModel.setIpId(ip.getIpId());
			ipModel.setIpAddress(ip.getIpAddress());
			ipModel.setRemark(ip.getRemark());			
			
			ipModel.setOptUserName(userService.getUserNameById(ip.getOptUser(),null));
			ipModel.setOptTime(DateUtil.TimestampToString(ip.getOptTime(),
					DateUtil.DATA_TIME));
		}
		return ipModel;
	}

}
