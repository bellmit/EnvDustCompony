package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;

import com.tcb.env.pojo.OnlineRate;

/**
 * 
 * <p>[功能描述]：在线率服务接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月29日上午11:11:17
 * @since	envdust 1.0.0
 *
 */
public interface IOnlineRateService {

	/**
	 * 
	 * <p>[功能描述]：获取小时在线率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getHourOnlineRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取小时在线率</p>
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
	List<OnlineRate> getHourOnlineRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,
			int rowIndex,int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取日在线率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getDayOnlineRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取日在线率</p>
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
	List<OnlineRate> getDayOnlineRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,
			int rowIndex,int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取月在线率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getMonthOnlineRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取月在线率</p>
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
	List<OnlineRate> getMonthOnlineRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,
			int rowIndex,int rowCount);
	
	/**
	 * 
	 * <p>[功能描述]：获取年在线率个数</p>
	 * 
	 * @author	王垒, 2017年9月28日上午11:20:13
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	int getYearOnlineRateCount(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取年在线率</p>
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
	List<OnlineRate> getYearOnlineRate(
			List<String> deviceCodeList,Timestamp beginTime,Timestamp endTime,
			int rowIndex,int rowCount);
	
}
