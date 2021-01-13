package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>
 * [功能描述]：设置数据间隔接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年6月6日上午9:00:41
 * @since EnvDust 1.0.0
 *
 */
public interface IDataIntervalService {

	/**
	 * 
	 * <p>
	 * [功能描述]：获取上报间隔计划数据个数
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:06:38
	 * @since EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	public int getDataIntervalCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取上报间隔计划数据
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:06:43
	 * @since EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	public List<CommMain> getDataInterval(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入数据上传间隔计划
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:10:20
	 * @since EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param riInterval
	 * @param riExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	public int insertDataInterval(String cnCode, String riInterval,
			String riExcuteTime, List<String> listDeviceCode, int optId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：删除数据上传间隔计划
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:10:49
	 * @since EnvDust 1.0.0
	 *
	 * @param listCommId
	 * @return
	 * @throws Exception
	 */
	public int deleteDataInterval(List<Integer> listCommId)
			throws Exception;

}
