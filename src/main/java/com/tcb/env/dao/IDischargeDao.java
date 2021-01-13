package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.tcb.env.model.DischargeModel;

/**
 * 
 * <p>[功能描述]：排放量Dao</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年10月13日下午1:23:53
 * @since	EnvDust 1.0.0
 *
 */
public interface IDischargeDao {
	
	/**
	 * 
	 * <p>[功能描述]：获取设备小时排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param dbName
	 * @param dbOldName
	 * @param deviceCode
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceHourDischarge(
			@Param("dbName")String dbName,
			@Param("dbOldName")String dbOldName,
			@Param("deviceCode")String deviceCode,
			@Param("thingCode")String thingCode,
			@Param("selectTime")Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备日排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param dbName
	 * @param dbOldName
	 * @param deviceCode
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceDayDischarge(
			@Param("dbName")String dbName,
			@Param("dbOldName")String dbOldName,
			@Param("deviceCode")String deviceCode,
			@Param("thingCode")String thingCode,
			@Param("selectTime")Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备月排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param dbName
	 * @param dbOldName
	 * @param deviceCode
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceMonthDischarge(
			@Param("dbName")String dbName,
			@Param("dbOldName")String dbOldName,
			@Param("deviceCode")String deviceCode,
			@Param("thingCode")String thingCode,
			@Param("selectTime")Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备年排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param dbName
	 * @param dbOldName
	 * @param deviceCode
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceYearDischarge(
			@Param("dbName") String dbName,
			@Param("dbOldName") String dbOldName,
			@Param("deviceCode")String deviceCode,
			@Param("thingCode")String thingCode,
			@Param("selectTime")Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备时间段排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param dbName
	 * @param dbOldName
	 * @param deviceCode
	 * @param thingCode
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<DischargeModel> getDeviceTimesDischarge(
			@Param("dbName")String dbName,
			@Param("dbOldName")String dbOldName,
			@Param("deviceCode")String deviceCode,
			@Param("thingCode")String thingCode,
			@Param("beginTime")Timestamp beginTime,
			@Param("endTime")Timestamp endTime);

}
