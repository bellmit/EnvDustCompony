package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.OnlineRate;

/**
 * 
 * <p>[功能描述]：在线率Dao</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月28日上午10:23:58
 * @since	envdust 1.0.0
 *
 */
public interface IOnlineRateDao {

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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
}
