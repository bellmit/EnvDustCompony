package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.Device;

/**
 * 
 * <p>
 * [功能描述]：权限组访问数据操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日上午9:29:24
 * @since EnvDust 1.0.0
 *
 */
public interface IAccessOperatorService {

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
	public int getAhrDeviceCount(Device device, String ahrCode, String flag);

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
	public List<Device> getAhrDevice(Device device, String ahrCode, String flag);

	/**
	 * <p>
	 * [功能描述]：查询符合条件设备个数
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月28日上午9:49:30
	 * @since EnvDust 1.0.0
	 *
	 * @param device
	 * @param ahrCode
	 * @return
	 */
	public int getJudgeAhrDeviceCount(Device device, String ahrCode);

	/**
	 * <p>
	 * [功能描述]：查询符合条件设备
	 * </p>
	 * 
	 * @author 任崇彬, 2016年3月28日上午9:49:30
	 * @since EnvDust 1.0.0
	 *
	 * @param device
	 * @param ahrCode
	 * @return
	 */
	public List<Device> getJudgeAhrDevice(Device device, String ahrCode);

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
	 * @Exception Exception
	 * @return
	 */
	public int insertAccessDevice(String ahrCode, List<String> listDevCode,
			int optUser) throws Exception;

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
	 * @Exception Exception
	 * @return
	 */
	public int deleteAccessDevice(List<String> listAhrCode) throws Exception;

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
	public int deleteAccessDeviceSingle(String ahrCode, List<String> listDevCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：更新权限组设备数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月7日上午12:31:39
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listDevCode
	 * @param optUser
	 * @return
	 * @throws Exception
	 */
	public void updateAccessDevice(String ahrCode, List<String> listDevCode,
			int optUser) throws Exception;

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
	 * @Exception Exception
	 * @return
	 */
	public int insertAccessMonitor(String ahrCode, List<String> listMonCode,
			int optUser) throws Exception;

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
	 * @Exception Exception
	 * @return
	 */
	public int deleteAccessMonitor(List<String> listAhrCode) throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：更新权限组监测物
	 * </p>
	 * 
	 * @author 王垒, 2016年4月7日上午12:30:35
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listMonCode
	 * @param optUser
	 * @return
	 * @throws Exception
	 */
	public void updateAccessMonitor(String ahrCode, List<String> listMonCode,
			int optUser) throws Exception;

}
