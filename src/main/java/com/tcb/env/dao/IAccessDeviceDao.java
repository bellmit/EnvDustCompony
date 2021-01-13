package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Device;

/**
 * 
 * <p>
 * [功能描述]：权限设备数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日上午8:45:59
 * @since EnvDust 1.0.0
 *
 */
public interface IAccessDeviceDao {

	/**
	 * <p>
	 * [功能描述]：查询没在权限用户中的设备个数
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月28日上午9:49:30
	 * @since EnvDust 1.0.0
	 *
	 * @param device
	 * @param ahrCode
	 * @param flag
	 *            :(0:不在权限内;1:在权限内)
	 * @return
	 */
	public int getAhrDeviceCount(@Param("device") Device device,
			@Param("ahrCode") String ahrCode, @Param("flag") String flag);

	/**
	 * <p>
	 * [功能描述]：查询没在权限用户中的设备
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月28日上午9:49:30
	 * @since EnvDust 1.0.0
	 *
	 * @param device
	 * @param ahrCode
	 * @param flag
	 *            :(0:不在权限内;1:在权限内)
	 * @return
	 */
	public List<Device> getAhrDevice(@Param("device") Device device,
			@Param("ahrCode") String ahrCode, @Param("flag") String flag);

	/**
	 * 
	 * <p>
	 * [功能描述]：添加权限组设备
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日上午8:53:48
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listDevCode
	 * @param optUser
	 * @return
	 */
	public int insertAccessDevice(@Param("ahrCode") String ahrCode,
			@Param("listDevCode") List<String> listDevCode,
			@Param("optUser") int optUser);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限组设备
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日上午8:54:34
	 * @since EnvDust 1.0.0
	 *
	 * @param listAhrCode
	 * @return
	 */
	public int deleteAccessDevice(@Param("listAhrCode") List<String> listAhrCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限组设备
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日上午8:54:34
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listDevCode
	 * @return
	 */
	public int deleteAccessDeviceSingle(@Param("ahrCode") String ahrCode,
			@Param("listDevCode") List<String> listDevCode);

}
