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

import com.tcb.env.model.MobilePageModel;
import com.tcb.env.model.ResultListMobileModel;
import com.tcb.env.service.IMobilePageService;
import com.tcb.env.service.ISysflagService;

/**
 * 
 * <p>[功能描述]：移动信息控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月2日下午1:45:32
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/MobilePageController")
public class MobilePageController {
	
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "MonitorController";

	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(MonitorController.class);
	
	/**
	 * 声明移动信息服务类
	 */
	@Resource
	private IMobilePageService mobilePageService;
	
	/**
	 * 声明系统标识服务类
	 */
	@Resource
	private ISysflagService sysflagService;
	
	
	/**
	 * 
	 * <p>[功能描述]：查询移动页面信息</p>
	 * 
	 * @author	王垒, 2018年2月2日下午2:07:12
	 * @since	EnvDust 1.0.0
	 *
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/getMobilePage", method = { RequestMethod.POST })
	public @ResponseBody ResultListMobileModel<MobilePageModel> getMobilePage(HttpSession httpsession) {
		
		// 创建类
		ResultListMobileModel<MobilePageModel> resultListMobileModel = new ResultListMobileModel<MobilePageModel>();
		List<MobilePageModel> listMobilePageModel = new ArrayList<MobilePageModel>();
		try
		{
			//获取title
			String title = sysflagService.getSysFlagValue("mobiletitle");
			resultListMobileModel.setTitle(title);
			//获取logo url
			String logoUrl = sysflagService.getSysFlagValue("mobileurl");
			resultListMobileModel.setLogoUrl(logoUrl);
			// 查询移动页面信息
			listMobilePageModel = mobilePageService.getMobilePage();
			resultListMobileModel.setRows(listMobilePageModel);
		}
		catch(Exception e){
			logger.error(LOG + "：查询移动页面信息失败，信息为：" + e.getMessage());
		}
		return resultListMobileModel;
	}
	

}
