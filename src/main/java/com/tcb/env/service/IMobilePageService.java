package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.MobilePageModel;

/**
 * 
 * <p>[功能描述]：移动信息接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月2日下午1:36:53
 * @since	EnvDust 1.0.0
 *
 */
public interface IMobilePageService {

	/**
	 * 
	 * <p>[功能描述]：获取移动页面信息</p>
	 * 
	 * @author	王垒, 2018年2月2日上午11:55:53
	 * @since	EnvDust 1.0.0
	 *
	 * @return
	 */
	List<MobilePageModel> getMobilePage();
	
}
