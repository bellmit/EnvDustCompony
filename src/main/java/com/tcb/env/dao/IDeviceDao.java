/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;

import com.tcb.env.model.*;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Device;

/**
 * <p>
 * [功能描述]：设备dao
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月25日下午1:26:14
 * @since EnvDust 1.0.0
 */
public interface IDeviceDao {

    /**
     * <p>[功能描述]：查询区域下权限设备</p>
     *
     * @param areaId
     * @param userCode
     * @return
     * @author 王垒, 2018年1月22日上午11:07:34
     * @since EnvDust 1.0.0
     */
    List<Device> getAreaAuthDevice(@Param("areaId") String areaId, @Param("userCode") String userCode);

    /**
     * <p>
     * [功能描述]：查询设备
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月28日上午9:49:30
     * @since EnvDust 1.0.0
     */
    List<Device> getDevice(@Param("device") Device device);

    /**
     * <p>
     * [功能描述]：新增设备
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月28日上午9:49:42
     * @since EnvDust 1.0.0
     */
    int insertDevice(@Param("device") Device device);

    /**
     * <p>
     * [功能描述]：获得个数
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月28日下午2:26:15
     * @since EnvDust 1.0.0
     */
    int getCount(@Param("device") Device device);

    /**
     * <p>
     * [功能描述]：删除设备
     * </p>
     *
     * @param deviceid
     * @return
     * @author 任崇彬, 2016年3月29日上午11:08:23
     * @since EnvDust 1.0.0
     */
    int deleteDevice(@Param("deviceid") List<Integer> deviceid);

    /**
     * <p>
     * [功能描述]：更新设备
     * </p>
     *
     * @param list
     * @return
     * @author 任崇彬, 2016年3月29日上午11:24:31
     * @since EnvDust 1.0.0
     */
    int updateDevice(@Param("list") List<Device> list);

    /**
     * <p>
     * [功能描述]：查询是设备编号否存在
     * </p>
     *
     * @param userid
     * @param devicecode
     * @return
     * @author 任崇彬, 2016年3月30日上午8:52:00
     * @since EnvDust 1.0.0
     */
    int getDeviceCodeExist(@Param("deviceid") int userid,
                           @Param("devicecode") String devicecode);

    /**
     * <p>[功能描述]：查询设备MN号</p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2018年7月27日上午9:22:23
     * @since EnvDust 1.0.0
     */
    String getDeviceMn(@Param("deviceCode") String deviceCode);

    /**
     * <p>
     * [功能描述]：查询设备MN号是都存在
     * </p>
     *
     * @param deviceid
     * @return
     * @author 任崇彬, 2016年4月14日上午8:26:46
     * @since EnvDust 1.0.0
     */
    int getDeviceMnExist(@Param("deviceid") int deviceid,
                         @Param("devicemn") String devicemn);

    /**
     * <p>
     * [功能描述]：创建监测物存储表
     * </p>
     *
     * @param tableName
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    int createStorageTable(@Param("tableName") String tableName);

    /**
     * <p>
     * [功能描述]：创建采样设备物质存储表
     * </p>
     *
     * @param tableName
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    int createSampleTable(@Param("tableName") String tableName);

    /**
     * <p>
     * [功能描述]：判断是否存在表
     * </p>
     *
     * @param dbName
     * @param tableName
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    String extStorageTable(
            @Param("dbName") String dbName,
            @Param("tableName") String tableName);

    /**
     * <p>
     * [功能描述]：删除监测物存储表
     * </p>
     *
     * @param dbName
     * @param tableName
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    int dropStorageTable(
            @Param("dbName") String dbName,
            @Param("tableName") String tableName);

    /**
     * <p>
     * [功能描述]：查询地图数据信息个数(非statusCode)
     * </p>
     *
     * @param listDevCode
     * @param statusCode
     * @param noStatusCode
     * @param beginAlarmTime
     * @param endAlarmTime
     * @param overAlarmId
     * @return
     * @author 王垒, 2016年4月20日下午3:15:13
     * @since EnvDust 1.0.0
     */
    int getMapDeviceCount(
            @Param("listDevCode") List<String> listDevCode,
            @Param("statusCode") String statusCode,
            @Param("noStatusCode") String noStatusCode,
            @Param("beginAlarmTime") Timestamp beginAlarmTime,
            @Param("endAlarmTime") Timestamp endAlarmTime,
            @Param("overAlarmId") String overAlarmId);

    /**
     * <p>
     * [功能描述]：查询地图数据信息(非statusCode)
     * </p>
     *
     * @param listDevCode
     * @param statusCode
     * @param beginAlarmTime
     * @param endAlarmTime
     * @param overAlarmId
     * @param rowIndex
     * @param rowCount
     * @return
     * @author 王垒, 2016年4月20日下午3:15:13
     * @since EnvDust 1.0.0
     */
    List<MapDeviceModel> getMapDevice(
            @Param("listDevCode") List<String> listDevCode,
            @Param("statusCode") String statusCode,
            @Param("noStatusCode") String noStatusCode,
            @Param("beginAlarmTime") Timestamp beginAlarmTime,
            @Param("endAlarmTime") Timestamp endAlarmTime,
            @Param("overAlarmId") String overAlarmId,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>
     * [功能描述]：查询地图是否有新的报警(非statusCode)
     * </p>
     *
     * @param listDevCode
     * @param originalUpdateTime
     * @param statusCode
     * @return
     * @author 王垒, 2016年4月20日下午3:15:13
     * @since EnvDust 1.0.0
     */
    int getMapAlarmDeviceCount(
            @Param("listDevCode") List<String> listDevCode,
            @Param("originalUpdateTime") Timestamp originalUpdateTime,
            @Param("statusCode") String statusCode);

    /**
     * <p>
     * [功能描述]：查询地图报警数据信息(非statusCode)
     * </p>
     *
     * @param listDevCode
     * @param statusCode
     * @return
     * @author 王垒, 2016年4月20日下午3:15:13
     * @since EnvDust 1.0.0
     */
    List<MapDeviceModel> getMapAlarmDevice(
            @Param("listDevCode") List<String> listDevCode,
            @Param("statusCode") String statusCode);

    /**
     * <p>
     * [功能描述]：查询设备监测物报警状信息看看(实时)
     * </p>
     *
     * @param deviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年4月21日下午4:10:48
     * @since EnvDust 1.0.0
     */
    List<String> getDeviceOverThing(
            @Param("deviceCode") String deviceCode,
            @Param("listThingCode") List<String> listThingCode);

    /**
     * <p>
     * [功能描述]：查询设备监测物报警状信息(小时)
     * </p>
     *
     * @param deviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年4月21日下午4:10:48
     * @since EnvDust 1.0.0
     */
    List<String> getDeviceOverThingAvg(
            @Param("deviceCode") String deviceCode,
            @Param("listThingCode") List<String> listThingCode);

    /**
     * <p>[功能描述]：通过设备编码获取名称</p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2016年6月6日上午8:14:10
     * @since EnvDust 1.0.0
     */
    String getDeviceName(@Param("deviceCode") String deviceCode);

    /**
     * <p>[功能描述]：通过设备ID获取编码</p>
     *
     * @param deviceId
     * @return
     * @author 王垒, 2016年6月6日上午8:14:10
     * @since EnvDust 1.0.0
     */
    String getDeviceCode(@Param("deviceId") String deviceId);

    /**
     * <p>[功能描述]：通过设备编码获取所属区域名称</p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2016年6月6日上午8:14:10
     * @since EnvDust 1.0.0
     */
    String getAreaName(@Param("deviceCode") String deviceCode);

    /**
     * <p>[功能描述]：插入设备时间记录表</p>
     *
     * @param device
     * @return
     * @author 王垒, 2016年6月15日上午8:35:25
     * @since EnvDust 1.0.0
     */
    int insertDeviceTime(@Param("device") Device device);

    /**
     * <p>[功能描述]：获取设备热力图信息（分表数据）</p>
     *
     * @param dbName
     * @param deviceCode
     * @param dataType
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2017年8月10日下午2:51:25
     * @since envdust 1.0.0
     */
    List<ThermodynamicModel> getThermodynamic(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("dataType") String dataType,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取设备热力图信息（热力表）</p>
     *
     * @param dbName
     * @param listDeviceCode
     * @param dataType
     * @param thingCode
     * @param beginTime
     * @return
     * @author 王垒, 2017年8月16日下午1:46:06
     * @since EnvDust 1.0.0
     */
    List<ThermodynamicModel> getThermTableData(
            @Param("dbName") String dbName,
            @Param("listDeviceCode") List<String> listDeviceCode,
            @Param("dataType") String dataType,
            @Param("thingCode") String thingCode,
            @Param("beginTime") Timestamp beginTime);

    /**
     * <p>[功能描述]：获取设备热力图最近数据</p>
     *
     * @param listDeviceCode
     * @param thingCode
     * @return
     * @author 王垒, 2017年8月16日下午4:32:45
     * @since EnvDust 1.0.0
     */
    List<ThermodynamicModel> getThermRecently(
            @Param("listDeviceCode") List<String> listDeviceCode,
            @Param("thingCode") String thingCode
    );

    /**
     * <p>[功能描述]：获取设备最近上传数据</p>
     *
     * @param listDeviceCode
     * @param thingCode
     * @return
     * @author 王垒, 2017年9月4日下午2:09:32
     * @since EnvDust 1.0.0
     */
    List<LocationModel> getLocationRecently(
            @Param("listDeviceCode") List<String> listDeviceCode,
            @Param("thingCode") String thingCode
    );

    /**
     * <p>[功能描述]：获取工地园区设备排放信息</p>
     *
     * @param dbName
     * @param dbOldName
     * @param areaId
     * @param deviceCode
     * @param thingCode
     * @param beginTime
     * @return
     * @author 王垒, 2017年9月6日下午2:39:44
     * @since EnvDust 1.0.0
     */
    List<AreaDeviceValueModel> getAreaDeviceValue(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("areaId") int areaId,
            @Param("deviceCode") String deviceCode,
            @Param("dataType") String dataType,
            @Param("thingCode") String thingCode,
            @Param("beginTime") Timestamp beginTime);

    /**
     * <p>
     * [功能描述]：更新设备状态
     * </p>
     *
     * @param deviceCode
     * @param statusCode
     * @return
     * @author 王垒, 2016年3月29日上午11:24:31
     * @since EnvDust 1.0.0
     */
    int updateDeviceStatus(
            @Param("deviceCode") String deviceCode,
            @Param("statusCode") String statusCode);

    /**
     * <p>[功能描述]：更新设备地图坐标</p>
     *
     * @param deviceCode
     * @param deviceX
     * @param deviceY
     * @return
     * @author 王垒, 2018年1月10日上午9:12:07
     * @since EnvDust 1.0.0
     */
    int updateDeviceLocation(
            @Param("deviceCode") String deviceCode,
            @Param("deviceX") String deviceX,
            @Param("deviceY") String deviceY);

    /**
     * <p>[功能描述]：获取指定时间物质的数值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param dataType
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月28日上午11:46:48
     * @since EnvDust 1.0.0
     */
    String getDeviceAppointValue(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("dataType") String dataType,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * 获取指定时间物质的数值
     * @param areaId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<LocationModel> getDeviceTvocData(
            @Param("areaId") int areaId,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime
    );

    /**
     * 获取设备进气状态信息
     * @param deviceCode
     * @return
     */
    GasPathModel getGapPath(
            @Param("deviceCode") String deviceCode
    );


}
