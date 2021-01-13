package com.tcb.env.dao;

import java.util.List;

import com.tcb.env.model.MobilePageModel;

/**
 * 
 * <p>[功能描述]：移动信息数据库接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月2日上午11:54:49
 * @since	EnvDust 1.0.0
 *
 */
public interface IMobilePageDao {
	
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
