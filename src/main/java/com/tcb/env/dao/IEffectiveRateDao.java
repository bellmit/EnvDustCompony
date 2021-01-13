package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.EffectiveRate;

/**
 * 
 * <p>[功能描述]：有效率Dao</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月28日上午10:42:29
 * @since	envdust 1.0.0
 *
 */
public interface IEffectiveRateDao {

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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);
	
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
			@Param("deviceCodeList")List<String> deviceCodeList,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime,
			@Param("rowIndex")int rowIndex,
			@Param("rowCount")int rowCount);
	
}
