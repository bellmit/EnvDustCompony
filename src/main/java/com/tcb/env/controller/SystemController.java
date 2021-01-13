package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tcb.env.model.SystemModel;
import com.tcb.env.service.ISystemService;

/**
 * 
 * <p>[功能描述]：系统版本参数控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月6日下午2:30:04
 * @since	EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/SystemController")
public class SystemController {
	
	/**
	 * 声明系统版本参数服务类
	 */
	@Resource
	private ISystemService systemService;
	
	/**
	 * 
	 * <p>[功能描述]：获取系统参数最新版本信息</p>
	 * 
	 * @author	王垒, 2018年2月6日下午3:05:34
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysCode
	 * @param httpsession
	 * @return
	 */
	@RequestMapping(value = "/querySystem", method = { RequestMethod.POST })
	public @ResponseBody SystemModel querySystem(String sysCode,HttpSession httpsession) {
		SystemModel systemModel = new SystemModel();
		//创建类
		List<SystemModel> listSystemModel = new ArrayList<SystemModel>();
		listSystemModel = systemService.getNewSystem(sysCode);
		if(listSystemModel != null && listSystemModel.size()>0){
			systemModel = listSystemModel.get(0);
		}
		return systemModel;
	}
	

}
