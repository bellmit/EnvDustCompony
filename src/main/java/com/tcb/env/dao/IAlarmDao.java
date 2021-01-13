package com.tcb.env.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Alarm;

/**
 * <p>
 * [功能描述]：报警数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月26日下午1:48:21
 * @since EnvDust 1.0.0
 */
public interface IAlarmDao {

    /**
     * <p>
     * [功能描述]：获取报警信息个数
     * </p>
     *
     * @param dbName      :数据库名称
     * @param dbOldName   :旧数据库名称
     * @param alarm
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月26日下午2:08:42
     * @since EnvDust 1.0.0
     */
    int getAlarmCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("alarm") Alarm alarm,
            @Param("listDevCode") List<String> listDevCode);

    /**
     * <p>
     * [功能描述]：获取报警信息
     * </p>
     *
     * @param dbName      :数据库名称
     * @param dbOldName   :旧数据库名称
     * @param alarm
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月26日下午2:08:42
     * @since EnvDust 1.0.0
     */
    List<Alarm> getAlarm(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("alarm") Alarm alarm,
            @Param("listDevCode") List<String> listDevCode);

    /**
     * <p>
     * [功能描述]：更新报警信息
     * </p>
     *
     * @param dbName :数据库名称
     * @param alarm
     * @return
     * @author 王垒, 2016年4月26日下午2:09:14
     * @since EnvDust 1.0.0
     */
    int updateAlarm(
            @Param("dbName") String dbName,
            @Param("alarm") Alarm alarm);

    /**
     * <p>
     * [功能描述]：删除报警信息
     * </p>
     *
     * @param dbName :数据库名称
     * @param listid
     * @return
     * @author 王垒, 2016年4月26日下午2:10:20
     * @since EnvDust 1.0.0
     */
    int deleteAlarm(
            @Param("dbName") String dbName,
            @Param("listid") List<Integer> listid);

    /**
     * <p>
     * [功能描述]：删除设备报警信息
     * </p>
     *
     * @param dbName     :数据库名称
     * @param deviceCode :设备编码
     * @return
     * @author 王垒, 2019年3月14日下午2:10:20
     * @since EnvDust 1.0.0
     */
    int deleteDeviceAlarm(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode);

    /**
     * <p>[功能描述]：获取最新报警信息</p>
     *
     * @param dbName     :数据库名称
     * @param deviceCode
     * @param alarmType
     * @return
     * @author 王垒, 2017年11月27日上午9:08:41
     * @since EnvDust 1.0.0
     */
    List<Map> getRecentlyAlarmInfo(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("alarmType") String alarmType);

    /**
     * 获取获取最新报警信息（未解决）
     *
     * @param dbName
     * @param deviceCode
     * @param alarmType
     * @return
     */
    List<Alarm> getRecentlyAlarmIdInfo(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("alarmType") String alarmType);

    /**
     * <p>[功能描述]：获取报警设备编码</p>
     *
     * @param dbName    :数据库名称
     * @param dbOldName :旧数据库名称
     * @param alarmId
     * @return
     * @author 王垒, 2018年1月23日下午12:35:37
     * @since EnvDust 1.0.0
     */
    String getDeviceCode(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("alarmId") String alarmId);

    /**
     * <p>[功能描述]：获取需要发送短信的设备报警</p>
     *
     * @param dbName
     * @return
     * @author 王垒, 2018年3月29日上午11:42:56
     * @since EnvDust 1.0.0
     */
    List<Alarm> getSmsAlarmInfo(@Param("dbName") String dbName);

    /**
     * <p>[功能描述]：更新发送标识</p>
     *
     * @param dbName
     * @param alarmId
     * @param sendFlag
     * @return
     * @author 王垒, 2018年3月29日上午11:57:22
     * @since EnvDust 1.0.0
     */
    int updateSmsAlarmFlag(
            @Param("dbName") String dbName,
            @Param("alarmId") String alarmId,
            @Param("sendFlag") boolean sendFlag);

    /**
     * <p>[功能描述]：获取负责人组织相关电话</p>
     *
     * @param dbName
     * @param deviceId
     * @param statusCode
     * @param thingCode
     * @return
     * @author 王垒, 2018年3月29日上午11:58:58
     * @since EnvDust 1.0.0
     */
    List<String> getSmsPhone(
            @Param("dbName") String dbName,
            @Param("deviceId") String deviceId,
            @Param("statusCode") String statusCode,
            @Param("thingCode") String thingCode);

    /**
     * 获取负责人组织相关邮件地址
     *
     * @param dbName
     * @param deviceId
     * @param statusCode
     * @param thingCode
     * @return
     */
    List<String> getSmsMail(
            @Param("dbName") String dbName,
            @Param("deviceId") String deviceId,
            @Param("statusCode") String statusCode,
            @Param("thingCode") String thingCode);

    /**
     * 获取设备报警级别
     *
     * @param dbName
     * @param deviceCode
     * @return
     */
    String getAlarmLevel(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode);

}
