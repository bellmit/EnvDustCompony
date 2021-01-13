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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.SysflagModel;
import com.tcb.env.model.UserModel;

import com.tcb.env.pojo.Sysflag;
import com.tcb.env.service.ISysflagService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月8日上午11:12:07
 * @since	EnvDust 1.0.0
 * 
 */
@Controller
@RequestMapping("/SysflagController")
public class SysflagController {
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "SysflagController";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(SysflagController.class);

	/**
	 * 声明gson对象
	 */
	Gson gson = new Gson();
	/**
	 * 声明User服务
	 */
	@Resource
	private ISysflagService sysflagService;
	@Resource
	private IUserService userService;
	
	@RequestMapping(value = "/querySysflag", method = { RequestMethod.POST })
	public @ResponseBody ResultListModel<SysflagModel> queryIp(
			SysflagModel sysflagModel, HttpSession httpsession) {
		//创建类
		ResultListModel<SysflagModel> resultListModel = new ResultListModel<SysflagModel>();
		List<SysflagModel> listSysflagModel = new ArrayList<SysflagModel>();
		List<Sysflag> listsysflag = new ArrayList<Sysflag>();
		//转换成组织
		Sysflag sysflag = ConvertSysflag(sysflagModel,httpsession);
		int count = sysflagService.getCount(sysflag);
		if (count > 0) {
			//查询数据库得到所有组织
			listsysflag = sysflagService.getSysflag(sysflag);
			//依次转换成model
			for (Sysflag temp : listsysflag) {
				SysflagModel sysflagModelt = ConvertSysflagModel(temp);
				if (sysflagModelt != null) {
					listSysflagModel.add(sysflagModelt);					
				}
			}
			resultListModel.setRows(listSysflagModel);
		}
		resultListModel.setTotal(count);
		return resultListModel;
	}
	@RequestMapping(value = "/updateSysflag", method = { RequestMethod.POST })
	public @ResponseBody ResultModel updateSysflag(SysflagModel sysflagModel,
			HttpSession httpsession) {
		ResultModel resultModel = new ResultModel();
		if (sysflagModel != null) {
			try {
				
					List<Sysflag> listSysflag = new ArrayList<Sysflag>();
					listSysflag.add(ConvertSysflag(sysflagModel, httpsession));
					int intresult = sysflagService.updateSysflag(listSysflag);
					if (intresult > 0) {
						resultModel.setResult(true);
					} else {
						resultModel.setResult(false);
						resultModel.setDetail("更新ip限制失败！");
					}
				 
				
			} catch (Exception e) {
				resultModel.setResult(false);
				resultModel.setDetail("更新设备失败！");
				logger.error(LOG + "：更新设备失败，信息为：" + e.getMessage());
			}
		}
		return resultModel;
	}
	/**
	 * <p>[功能描述]：model转换成sysflag</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午11:20:00
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflagModel
	 * @param httpsession
	 * @return 
	 */
	private Sysflag ConvertSysflag(SysflagModel sysflagModel, HttpSession httpsession){
		Sysflag sysflag = new Sysflag();
		if (sysflagModel != null) {
			sysflag.setSysId(sysflagModel.getSysId());
			sysflag.setSysFlagCode(sysflagModel.getSysFlagCode());
			sysflag.setSysValue(sysflagModel.getSysValue());
	//获取当前操作者并设置
			UserModel loginuser = (UserModel) httpsession
					.getAttribute(DefaultArgument.LOGIN_USER);
			if (loginuser != null) {
				sysflag.setOptUser(loginuser.getUserId());
			} else {
				sysflag.setOptUser(sysflagModel.getSysId());
			}
		}
		return sysflag;
	}
	/**
	 * <p>[功能描述]：flag转换成model</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午11:27:07
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflag
	 * @return 
	 */
	private SysflagModel ConvertSysflagModel(Sysflag sysflag){
		SysflagModel sysflagModel = new SysflagModel();
		if(sysflag != null){
			sysflagModel.setSysId(sysflag.getSysId());		
			sysflagModel.setSysFlagCode(sysflag.getSysFlagCode());
			sysflagModel.setSysValue(sysflag.getSysValue());			
			sysflagModel.setOptUserName(userService.getUserNameById(sysflag.getOptUser(),null));
			sysflagModel.setOptTime(DateUtil.TimestampToString(sysflag.getOptTime(),
					DateUtil.DATA_TIME));
		}
		return sysflagModel;
	}

}
