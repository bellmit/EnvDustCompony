package com.tcb.env.service;

import java.util.List;
import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>
 * [功能描述]：计划上传数据服务接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年6月7日上午8:58:13
 * @since EnvDust 1.0.0
 *
 */
public interface IReviewDataService {

	/**
	 * 
	 * <p>
	 * [功能描述]：获取计划上传数据个数
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
	public int getReviewDataCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取计划上传数据
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
	public List<CommMain> getReviewData(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入设置获取实时数据
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:10:20
	 * @since EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param rldExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	public int insertRldData(String cnCode, String rldExcuteTime,
			List<String> listDeviceCode, int optId) throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：插入分段数据
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:10:20
	 * @since EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param beginTime
	 * @param endTime
	 * @param stExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	public int insertSpanTimeData(String cnCode, String beginTime,
			String endTime, String stExcuteTime,
			List<String> listDeviceCode, int optId) throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：删除获取数据计划
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午9:10:49
	 * @since EnvDust 1.0.0
	 *
	 * @param listCommId
	 * @return
	 * @throws Exception
	 */
	public int deleteReviewData(List<Integer> listCommId) throws Exception;

}
