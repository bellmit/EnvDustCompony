package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;

import com.tcb.env.pojo.EffectiveRate;

/**
 * 
 * <p>[功能描述]：有效率服务接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月29日上午11:10:55
 * @since	envdust 1.0.0
 *
 */
public interface IEffectiveRateService {
	
	/**
	 * 
	 * <p>[功能描述]：获取小时有效率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getHourEffectiveRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取小时有效率</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:22:47
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @param rowIndex
	 * @param rowCount
	 * @return
	 */
	List<EffectiveRate> getHourEffectiveRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,
			int rowIndex,int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取日有效率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getDayEffectiveRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取日有效率</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:22:47
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @param rowIndex
	 * @param rowCount
	 * @return
	 */
	List<EffectiveRate> getDayEffectiveRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,
			int rowIndex,int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取月有效率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getMonthEffectiveRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取月有效率</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:22:47
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @param rowIndex
	 * @param rowCount
	 * @return
	 */
	List<EffectiveRate> getMonthEffectiveRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,int rowIndex,int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取年有效率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getYearEffectiveRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取年有效率</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:22:47
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @param rowIndex
	 * @param rowCount
	 * @return
	 */
	List<EffectiveRate> getYearEffectiveRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,int rowIndex,int rowCount);
	
}
