package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;

import com.tcb.env.model.DischargeModel;

/**
 * 
 * <p>[功能描述]：设备排放量接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年10月16日上午10:59:15
 * @since	envdust 1.0.0
 *
 */
public interface IDischargeService {
	
	/**
	 * 
	 * <p>[功能描述]：获取设备小时排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceHourDischarge(
			List<String> deviceCodeList,
			String thingCode,
			Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备日排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceDayDischarge(
			List<String> deviceCodeList,
			String thingCode,
			Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备月排放量</p>
	 * 
	 * @author	王垒, 2017年10月16日上午10:39:33
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceMonthDischarge(
			List<String> deviceCodeList,
			String thingCode,
			Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备年排放量</p>
	 * 
	 * @author	王垒, 2017年10月19日上午10:19:04
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param thingCode
	 * @param selectTime
	 * @return
	 */
	List<DischargeModel> getDeviceYearDischarge(
			List<String> deviceCodeList,
			String thingCode,
			Timestamp selectTime);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备时间段排放量</p>
	 * 
	 * @author	王垒, 2017年10月19日上午10:19:04
	 * @since	envdust 1.0.0
	 *
	 * @param deviceCodeList
	 * @param thingCode
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<DischargeModel> getDeviceTimesDischarge(
			List<String> deviceCodeList,
			String thingCode,
			Timestamp beginTime,
			Timestamp endTime);
	
}
