package com.tcb.env.service;

import java.util.List;
import java.util.Map;

import com.tcb.env.pojo.Alarm;

/**
 * <p>
 * [功能描述]：报警操作服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月26日下午2:51:15
 * @since EnvDust 1.0.0
 */
public interface IAlarmService {

    /**
     * <p>
     * [功能描述]：获取报警信息个数
     * </p>
     *
     * @param alarm
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月26日下午2:08:42
     * @since EnvDust 1.0.0
     */
    int getAlarmCount(Alarm alarm, List<String> listDevCode);

    /**
     * <p>
     * [功能描述]：获取报警信息
     * </p>
     *
     * @param alarm
     * @param listDevCode
     * @return
     * @author 王垒, 2016年4月26日下午2:08:42
     * @since EnvDust 1.0.0
     */
    List<Alarm> getAlarm(Alarm alarm, List<String> listDevCode);

    /**
     * <p>
     * [功能描述]：更新报警信息
     * </p>
     *
     * @param alarm
     * @param listId
     * @return
     * @author 王垒, 2016年4月26日下午2:09:14
     * @since EnvDust 1.0.0
     */
    int updateAlarm(Alarm alarm, List<String> listId) throws Exception;

    /**
     * <p>
     * [功能描述]：删除报警信息
     * </p>
     *
     * @param listid
     * @return
     * @author 王垒, 2016年4月26日下午2:10:20
     * @since EnvDust 1.0.0
     */
    int deleteAlarm(List<Integer> listid) throws Exception;

    /**
     * <p>
     * [功能描述]：删除设备报警信息
     * </p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2019年3月14日下午2:10:20
     * @since EnvDust 1.0.0
     */
    int deleteDeviceAlarm(String deviceCode) throws Exception;

    /**
     * <p>[功能描述]：获取最新报警信息（未解决）</p>
     *
     * @param deviceCode
     * @param alarmType
     * @return
     * @author 王垒, 2017年11月27日上午9:08:41
     * @since EnvDust 1.0.0
     */
    List<Map> getRecentlyAlarmInfo(String deviceCode, String alarmType);

    /**
     * <p>[功能描述]：获取最新报警信息（未解决）</p>
     *
     * @param deviceCode
     * @param alarmType
     * @return
     * @author 王垒, 2017年11月27日上午9:08:41
     * @since EnvDust 1.0.0
     */
    List<Alarm> getRecentlyAlarmIdInfo(String deviceCode, String alarmType);

    /**
     * <p>[功能描述]：获取需要发送短信的设备报警</p>
     *
     * @return
     * @author 王垒, 2018年3月29日上午11:42:56
     * @since EnvDust 1.0.0
     */
    List<Alarm> getSmsAlarmInfo();


    /**
     * <p>[功能描述]：更新发送标识</p>
     *
     * @param alarmId
     * @param sendFlag
     * @return
     * @author 王垒, 2018年3月29日上午11:57:22
     * @since EnvDust 1.0.0
     */
    int updateSmsAlarmFlag(String alarmId, boolean sendFlag);


    /**
     * <p>[功能描述]：获取负责人组织相关电话</p>
     *
     * @param deviceId
     * @param statusCode
     * @param thingCode
     * @return
     * @author 王垒, 2018年3月29日上午11:58:58
     * @since EnvDust 1.0.0
     */
    List<String> getSmsPhone(String deviceId, String statusCode, String thingCode);

    /**
     * 获取负责人组织相关邮件地址
     *
     * @param deviceId
     * @param statusCode
     * @param thingCode
     * @return
     */
    List<String> getSmsMail(String deviceId, String statusCode, String thingCode);

    /**
     * 获取设备报警级别
     *
     * @param deviceCode
     * @return
     */
    String getAlarmLevel(String deviceCode);

}
