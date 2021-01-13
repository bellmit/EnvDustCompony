package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <p>
 * [功能描述]：权限监测物数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日上午8:57:58
 * @since EnvDust 1.0.0
 *
 */
public interface IAccessMonitorDao {

	/**
	 * 
	 * <p>
	 * [功能描述]：添加权限组监测物
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日上午8:53:48
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listMonCode
	 * @param optUser
	 * @return
	 */
	public int insertAccessMonitor(@Param("ahrCode") String ahrCode,
			@Param("listMonCode") List<String> listMonCode,
			@Param("optUser") int optUser);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限组监测物
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日上午8:54:34
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listAhrCode
	 * @return
	 */
	public int deleteAccessMonitor(
			@Param("listAhrCode") List<String> listAhrCode);

}
