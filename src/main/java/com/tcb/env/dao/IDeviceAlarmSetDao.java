package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.DeviceAlarmSet;

/**
 * 
 * <p>
 * [功能描述]：设备报警门限信息Dao
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月31日下午2:17:36
 * @since EnvDust 1.0.0
 *
 */
public interface IDeviceAlarmSetDao {

	/**
	 * 
	 * <p>
	 * [功能描述]：报警门限是否存在
	 * </p>
	 * 
	 * @author 王垒, 2016年6月1日下午2:23:31
	 * @since EnvDust 1.0.0
	 *
	 * @param deviceAlarmSet
	 * @return
	 */
	int exitDeviceAlarmSet(
			@Param("deviceAlarmSet") DeviceAlarmSet deviceAlarmSet);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取报警门限CommId
	 * </p>
	 * 
	 * @author 王垒, 2016年6月2日上午11:34:07
	 * @since EnvDust 1.0.0
	 *
	 * @param deviceCode
	 * @param thingCode
	 * @param cnCode
	 * @return
	 */
	List<String> getAlarmMainCommId(@Param("deviceCode") String deviceCode,
			@Param("thingCode") String thingCode,@Param("cnCode") String cnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：查询设备报警门限信息个数
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午2:18:56
	 * @since EnvDust 1.0.0
	 *
	 * @param deviceAlarmSet
	 * @param listDeviceCode
	 * @param listThingCode
	 * @return
	 */
	int getDeviceAlarmSetCount(
			@Param("deviceAlarmSet") DeviceAlarmSet deviceAlarmSet,
			@Param("listDeviceCode") List<String> listDeviceCode,
			@Param("listThingCode") List<String> listThingCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：查询设备报警门限信息
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午2:20:06
	 * @since EnvDust 1.0.0
	 *
	 * @param deviceAlarmSet
	 * @param listDeviceCode
	 * @param listThingCode
	 * @return
	 */
	List<DeviceAlarmSet> getDeviceAlarmSet(
			@Param("deviceAlarmSet") DeviceAlarmSet deviceAlarmSet,
			@Param("listDeviceCode") List<String> listDeviceCode,
			@Param("listThingCode") List<String> listThingCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入设备报警门限信息
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午2:21:54
	 * @since EnvDust 1.0.0
	 *
	 * @param listDeviceAlarmSet
	 * @return
	 */
	int insertDeviceAlarmSet(
			@Param("listDeviceAlarmSet") List<DeviceAlarmSet> listDeviceAlarmSet);

	/**
	 * 
	 * <p>
	 * [功能描述]：更新设备报警门限信息
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午2:24:42
	 * @since EnvDust 1.0.0
	 *
	 * @param deviceAlarmSet
	 * @return
	 */
	int updateDeviceAlarmSet(
			@Param("deviceAlarmSet") DeviceAlarmSet deviceAlarmSet);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除报警门限信息
	 * </p>
	 * 
	 * @author 王垒, 2016年6月2日下午12:36:23
	 * @since EnvDust 1.0.0
	 *
	 * @param listDeviceAlarmSet
	 * @return
	 */
	int deleteDeviceAlarmSet(
			@Param("listDeviceAlarmSet") List<DeviceAlarmSet> listDeviceAlarmSet);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取单个报警门限信息（设备编码和监测物编码）
	 * </p>
	 * 
	 * @author 王垒, 2016年6月2日下午3:20:29
	 * @since EnvDust 1.0.0
	 *
	 * @param deviceCode
	 * @param thingCodeList
	 * @return
	 */
	List<DeviceAlarmSet> getDeviceAlarmSetSingle(
			@Param("deviceCode") String deviceCode,
			@Param("thingCodeList") List<String> thingCodeList);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取关联计划主表的Id
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日下午2:35:12
	 * @since EnvDust 1.0.0
	 *
	 * @param listDasId
	 * @return
	 */
	List<CommMain> getCommMainIdByDasId(
			@Param("listDasId") List<Integer> listDasId);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除报警门限信息
	 * </p>
	 * 
	 * @author 王垒, 2016年6月2日下午12:36:23
	 * @since EnvDust 1.0.0
	 *
	 * @param listDasId
	 * @return
	 */
	int deleteDeviceAlarmSetByDasId(
			@Param("listDasId") List<Integer> listDasId);

}
