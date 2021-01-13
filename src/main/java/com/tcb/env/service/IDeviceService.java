/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.tcb.env.model.*;
import com.tcb.env.pojo.Device;

/**
 * <p>
 * [功能描述]：设备服务接口
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月25日上午11:58:28
 * @since EnvDust 1.0.0
 */
public interface IDeviceService {
    /**
     * <p>
     * [功能描述]：查询设备
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月25日下午1:37:53
     * @since EnvDust 1.0.0
     */
    List<Device> getDevice(Device device);

    /**
     * <p>
     * [功能描述]：新增设备
     * </p>
     *
     * @param device
     * @return
     * @throws Exception
     * @author 任崇彬, 2016年3月28日上午9:49:12
     * @since EnvDust 1.0.0
     */
    int insertDevice(Device device) throws Exception;

    /**
     * <p>
     * [功能描述]：查询个数
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月28日下午2:11:23
     * @since EnvDust 1.0.0
     */
    int getCount(Device device);

    /**
     * <p>
     * [功能描述]：删除设备
     * </p>
     *
     * @param deviceid
     * @return
     * @author 任崇彬, 2016年3月29日上午11:06:21
     * @since EnvDust 1.0.0
     */
    int deleteDevice(List<Integer> deviceid) throws Exception;

    /**
     * <p>
     * [功能描述]：更新设备
     * </p>
     *
     * @param listdevice
     * @return
     * @author 任崇彬, 2016年3月29日上午11:18:38
     * @since EnvDust 1.0.0
     */
    int updateDevice(List<Device> listdevice) throws Exception;

    /**
     * <p>
     * [功能描述]：查询设备编码是否存在
     * </p>
     *
     * @param deviceId
     * @param deviceCode
     * @return
     * @author 任崇彬, 2016年3月30日上午8:49:11
     * @since EnvDust 1.0.0
     */
    int getDeviceCodeExist(int deviceId, String deviceCode);

    /**
     * <p>
     * [功能描述]：查询设备MN码是否存在
     * </p>
     *
     * @param deviceId
     * @param devicemn
     * @return
     * @author 任崇彬, 2016年4月14日上午8:23:19
     * @since EnvDust 1.0.0
     */
    int getDeviceMnExist(int deviceId, String devicemn);

    /**
     * <p>
     * [功能描述]：创建监测物存储表
     * </p>
     *
     * @param device
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    boolean createStorageTable(Device device) throws Exception;

    /**
     * <p>
     * [功能描述]：创建采样设备物质存储表
     * </p>
     *
     * @param device
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    boolean createSampleTable(Device device);

    /**
     * <p>
     * [功能描述]：删除监测物存储表
     * </p>
     *
     * @param device
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    void dropStorageTable(Device device) throws Exception;

    /**
     * <p>
     * [功能描述]：删除采样设备存储表
     * </p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2016年4月1日下午3:09:28
     * @since EnvDust 1.0.0
     */
    void dropSampleTable(String deviceCode) throws Exception;

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
    int getMapDeviceCount(List<String> listDevCode, String statusCode, String noStatusCode, Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId);

    /**
     * <p>
     * [功能描述]：查询地图数据信息(非statusCode)
     * </p>
     *
     * @param listDevCode
     * @param statusCode
     * @param noStatusCode
     * @param beginAlarmTime
     * @param endAlarmTime
     * @param overAlarmId
     * @param rowIndex
     * @param rowCount
     * @return
     * @author 王垒, 2016年4月20日下午3:15:13
     * @since EnvDust 1.0.0
     */
    List<MapDeviceModel> getMapDevice(List<String> listDevCode, String statusCode, String noStatusCode,
                                      Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId, int rowIndex, int rowCount);

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
            List<String> listDevCode,
            Timestamp originalUpdateTime,
            String statusCode);

    /**
     * <p>
     * [功能描述]：查询地图数据信息(非statusCode)
     * </p>
     *
     * @param listDevCode
     * @param statusCode
     * @return
     * @author 王垒, 2016年4月20日下午3:15:13
     * @since EnvDust 1.0.0
     */
    List<MapDeviceModel> getMapAlarmDevice(List<String> listDevCode,
                                           String statusCode);

    /**
     * <p>
     * [功能描述]：查询设备监测物报警状信息（实时）
     * </p>
     *
     * @param deviceCode
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月21日下午4:10:48
     * @since EnvDust 1.0.0
     */
    String getDeviceOverThing(String deviceCode,
                              List<String> listThingCode);

    /**
     * <p>
     * [功能描述]：查询设备监测物报警状信息（小时）
     * </p>
     *
     * @param deviceCode
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月21日下午4:10:48
     * @since EnvDust 1.0.0
     */
    String getDeviceOverThingAvg(String deviceCode,
                                 List<String> listThingCode);

    /**
     * <p>[功能描述]：通过设备编码获取名称</p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2016年6月6日上午8:14:10
     * @since EnvDust 1.0.0
     */
    String getDeviceName(String deviceCode);

    /**
     * <p>[功能描述]：通过设备ID获取编码</p>
     *
     * @param deviceId
     * @return
     * @author 王垒, 2016年6月6日上午8:14:10
     * @since EnvDust 1.0.0
     */
    String getDeviceCode(String deviceId);

    /**
     * <p>[功能描述]：获取设备热力图信息</p>
     *
     * @param listDevCode
     * @param dataType
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param wFlag
     * @return
     * @author 王垒, 2017年8月10日下午2:31:25
     * @since EnvDust 1.0.0
     */
    Map<String, List<ThermodynamicModel>> getThermodynamic(List<String> listDevCode, String dataType,
                                                           String thingCode, String beginTime, String endTime, boolean wFlag);

    /**
     * <p>[功能描述]：获取设备热力图最近数据</p>
     *
     * @param listDeviceCode
     * @param thingCode
     * @return
     * @author 王垒, 2017年8月16日下午4:32:45
     * @since EnvDust 1.0.0
     */
    Map<String, List<ThermodynamicModel>> getThermRecently(
            List<String> listDeviceCode,
            String thingCode);

    /**
     * <p>[功能描述]：获取设备扩散模型相关数据</p>
     *
     * @param listDeviceCode
     * @param wd
     * @param ws
     * @param thingCode
     * @return
     * @author 王垒, 2017年8月16日下午4:32:45
     * @since EnvDust 1.0.0
     */
    Map<String, List<LocationModel>> getSpreadRecently(
            List<String> listDeviceCode, String wd, String ws,
            String thingCode);

    /**
     * <p>[功能描述]：获取工地园区排放统计信息</p>
     *
     * @param areaId
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2017年9月6日下午2:39:44
     * @since EnvDust 1.0.0
     */
    AreaStatisticModel getAreaValue(
            int areaId, String dataType, String thingCode,
            String beginTime, String endTime);

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
    int updateDeviceLocation(String deviceCode, String deviceX, String deviceY);

    /**
     * <p>[功能描述]：查询区域下权限设备</p>
     *
     * @param areaId
     * @param userCode
     * @return
     * @author 王垒, 2018年1月22日上午11:07:34
     * @since EnvDust 1.0.0
     */
    List<Device> getAreaAuthDevice(String areaId, String userCode);

    /**
     * <p>[功能描述]：通过设备编码获取所属区域名称</p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2016年6月6日上午8:14:10
     * @since EnvDust 1.0.0
     */
    String getAreaName(String deviceCode);

    /**
     * 获取设备进气状态信息
     *
     * @param deviceCode
     * @return
     */
    GasPathModel getGapPath(String deviceCode);

}
