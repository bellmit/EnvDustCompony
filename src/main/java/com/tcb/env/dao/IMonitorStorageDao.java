package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.MonitorStorageModel;
import com.tcb.env.model.OriginalDataModel;
import com.tcb.env.pojo.NetStatusCount;

/**
 * <p>
 * [功能描述]：监测数据Dao
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月29日下午2:41:20
 * @since EnvDust 1.0.0
 */
public interface IMonitorStorageDao {

    /**
     * <p>
     * [功能描述]：获取分钟统计数据
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerMinuteMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取小时统计数据
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerHourMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取每日统计数据
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerDayMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取每月统计数据(每日统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerMonthMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取季度统计数据(每日统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerQuarterMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取分钟统计数据(实时统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerMinuteStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取小时统计数据(实时统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerHourStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取每日统计数据(实时统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerDayStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取每月统计数据(实时统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerMonthStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * <p>
     * [功能描述]：获取季度统计数据(实时统计)
     * </p>
     *
     * @param hashmap
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getPerQuarterStaMonitorData(
            Map<String, Object> hashmap);

    /**
     * 获取监测物数据个数
     *
     * @param devicecode
     * @param listthingcode
     * @param nowtime
     * @param selecttime
     * @return
     */
    int getTimelyMonitorDataCount(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("nowtime") Timestamp nowtime,
            @Param("selecttime") Timestamp selecttime,
            @Param("dataType") String dataType);

    /**
     * 获取监测物数据
     *
     * @param devicecode
     * @param listthingcode
     * @param nowtime
     * @param selecttime
     * @param dataType
     * @return
     */
    List<MonitorStorageModel> getTimelyMonitorData(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("nowtime") Timestamp nowtime,
            @Param("selecttime") Timestamp selecttime,
            @Param("dataType") String dataType);

    /**
     * <p>
     * [功能描述]：是否存在新上传的小时数据(小时)
     * </p>
     *
     * @param devicecode    :设备编号
     * @param listthingcode :监测物列表
     * @param nowtime       :系统当前时间
     * @param selecttime    :上次查询时间
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    int getHourMonitorDataCount(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("nowtime") Timestamp nowtime,
            @Param("selecttime") Timestamp selecttime);

    /**
     * <p>
     * [功能描述]：获取实时小时数据(小时)
     * </p>
     *
     * @param devicecode    :设备编号
     * @param listthingcode :监测物列表
     * @param nowtime       :系统当前时间
     * @param selecttime    :上次查询时间
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getHourMonitorData(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("nowtime") Timestamp nowtime,
            @Param("selecttime") Timestamp selecttime);

    /**
     * <p>[功能描述]：获取网络监控最新数据更新时间</p>
     *
     * @param devicecode
     * @param listthingcode
     * @param statusCode
     * @return
     * @author 王垒, 2017年8月9日上午10:41:41
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getNetMonitorRecentTime(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("statusCode") String statusCode);

    /**
     * <p>[功能描述]：获取网络监控最新数据更新值</p>
     *
     * @param devicecode
     * @param listthingcode
     * @param statusCode
     * @return
     * @author 王垒, 2017年8月9日上午10:41:41
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getNetMonitorRecentData(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("statusCode") String statusCode);

    /**
     * <p>
     * [功能描述]：获取网络监控数据（有设备上传数据,暂不使用）
     * </p>
     *
     * @param devicecode    ：设备编号
     * @param listthingcode ：监测物列表
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<MonitorStorageModel> getNetMonitorData(
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode);

    /**
     * <p>
     * [功能描述]：获取网络监控数据（无设备上传数据,暂不使用）
     * </p>
     *
     * @param devicecode
     * @return
     * @author 王垒, 2016年4月5日上午10:08:35
     * @since EnvDust 1.0.0
     */
    MonitorStorageModel getNetNoData(
            @Param("deviceCode") String deviceCode);

    /**
     * <p>
     * [功能描述]：通过设备code获取name
     * </p>
     *
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月6日下午4:11:52
     * @since EnvDust 1.0.0
     */
    List<String> getDeviceNamebyCode(
            @Param("listDevCode") List<String> listDevCode);

    /**
     * <p>
     * [功能描述]：通过监测物code获取name
     * </p>
     *
     * @param listMonCode
     * @return
     * @author 王垒, 2016年4月6日下午4:11:52
     * @since EnvDust 1.0.0
     */
    List<String> getMonNamebyCode(
            @Param("listMonCode") List<String> listMonCode);

    /**
     * <p>
     * [功能描述]：获取原始数据个数
     * </p>
     *
     * @param dbName        :数据库名称
     * @param dbOldName     :旧数据库名称
     * @param devicecode    :设备编号
     * @param listthingcode :监测物列表
     * @param begintime     :开始时间
     * @param endtime       :结束时间
     * @param updateType    :数据类型
     * @param select        :选择标识
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    int getOriginalDataCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("begintime") Timestamp begintime,
            @Param("endtime") Timestamp endtime,
            @Param("updateType") String updateType,
            @Param("select") String select);

    /**
     * <p>
     * [功能描述]：获取原始数据
     * </p>
     *
     * @param dbName        :数据库名称
     * @param dbOldName     :旧数据库名称
     * @param devicecode    :设备编号
     * @param listthingcode :监测物列表
     * @param begintime     :开始时间
     * @param endtime       :结束时间
     * @param updateType    :数据类型
     * @param select        :选择标识
     * @param rowIndex      :起始行数
     * @param rowCount      :查询行数
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    List<OriginalDataModel> getOriginalData(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("devicecode") String devicecode,
            @Param("listthingcode") List<String> listthingcode,
            @Param("begintime") Timestamp begintime,
            @Param("endtime") Timestamp endtime,
            @Param("updateType") String updateType,
            @Param("select") String select,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>
     * [功能描述]：获取原始数据(通过ID)
     * </p>
     *
     * @param dbName     :数据库名称
     * @param storageId  :存储ID
     * @param deviceCode :设备编号
     * @return
     * @author 王垒, 2016年3月29日下午2:50:06
     * @since EnvDust 1.0.0
     */
    OriginalDataModel getOriginalDataById(
            @Param("dbName") String dbName,
            @Param("storageId") String storageId,
            @Param("deviceCode") String deviceCode);

    /**
     * <p>[功能描述]：获取网络状态个数</p>
     *
     * @param usercode
     * @param listdevicecode
     * @return
     * @author 王垒, 2016年7月1日下午3:43:20
     * @since EnvDust 1.0.0
     */
    List<NetStatusCount> getNetStatusCount(
            @Param("usercode") String usercode,
            @Param("listdevicecode") List<String> listdevicecode);

    /**
     * <p>[功能描述]：更新原始数据(数据修约)</p>
     *
     * @param dbName
     * @param originalDataModel
     * @return
     * @author 王垒, 2018年5月28日下午2:44:24
     * @since EnvDust 1.0.0
     */
    int updateOriginalData(
            @Param("dbName") String dbName,
            @Param("originalDataModel") OriginalDataModel originalDataModel);

    /**
     * <p>[功能描述]：删除原始数据(数据修约)</p>
     *
     * @param dbName
     * @param storageId
     * @param deviceCode
     * @return
     * @author 王垒, 2018年5月28日下午2:44:24
     * @since EnvDust 1.0.0
     */
    int deleteOriginalData(
            @Param("dbName") String dbName,
            @Param("storageId") String storageId,
            @Param("deviceCode") String deviceCode);

    /**
     * <p>[功能描述]：通过状态筛选设备编码</p>
     *
     * @param deviceCodeList
     * @param deviceStatus
     * @return
     * @author 王垒, 2018年12月10日下午2:21:01
     * @since EnvDust 1.0.0
     */
    List<String> getDeviceCodeByStatus(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("deviceStatus") String deviceStatus);

}
