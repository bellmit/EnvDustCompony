package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.DeviceAlarmSet;

/**
 * <p>
 * [功能描述]：报警门限服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午10:24:33
 * @since EnvDust 1.0.0
 */
public interface IDeviceAlarmSetService {

    /**
     * <p>[功能描述]：报警门限是否存在</p>
     *
     * @param deviceAlarmSet
     * @return
     * @author 王垒, 2016年6月1日下午2:23:31
     * @since EnvDust 1.0.0
     */
    int exitDeviceAlarmSet(DeviceAlarmSet deviceAlarmSet);

    /**
     * <p>
     * [功能描述]：查询设备报警门限信息个数
     * </p>
     *
     * @param deviceAlarmSet
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年5月31日下午2:18:56
     * @since EnvDust 1.0.0
     */
    int getDeviceAlarmSetCount(DeviceAlarmSet deviceAlarmSet,
                               List<String> listDeviceCode, List<String> listThingCode);

    /**
     * <p>
     * [功能描述]：查询设备报警门限信息
     * </p>
     *
     * @param deviceAlarmSet
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年5月31日下午2:20:06
     * @since EnvDust 1.0.0
     */
    List<DeviceAlarmSet> getDeviceAlarmSet(
            DeviceAlarmSet deviceAlarmSet, List<String> listDeviceCode,
            List<String> listThingCode);

    /**
     * <p>
     * [功能描述]：更新设备报警门限信息
     * </p>
     *
     * @param deviceAlarmSet
     * @return
     * @author 王垒, 2016年5月31日下午2:24:42
     * @since EnvDust 1.0.0
     */
    int updateDeviceAlarmSet(DeviceAlarmSet deviceAlarmSet) throws Exception;

    /**
     * <p>[功能描述]：设置门限值</p>
     *
     * @param thingCode
     * @param maxValue
     * @param minValue
     * @param dataFlag
     * @param levelNo
     * @param excuteTime
     * @param noDown
     * @param listDevCode
     * @param optId
     * @return
     * @author 王垒, 2016年6月1日下午1:54:55
     * @since EnvDust 1.0.0
     */
    int setDeviceAlarmSet(String thingCode, String maxValue, String minValue, String dataFlag, String levelNo,
                          String excuteTime, boolean noDown, List<String> listDevCode,
                          int optId) throws Exception;

    /**
     * <p>
     * [功能描述]：获取单个报警门限信息（设备编码和监测物编码）
     * </p>
     *
     * @param deviceCode
     * @param thingCodeList
     * @return
     * @author 王垒, 2016年6月2日下午3:20:29
     * @since EnvDust 1.0.0
     */
    List<DeviceAlarmSet> getDeviceAlarmSetSingle(String deviceCode, List<String> thingCodeList);

    /**
     * <p>[功能描述]：删除报警门限</p>
     *
     * @param listDasId
     * @return
     * @author 王垒, 2016年6月6日下午2:19:17
     * @since EnvDust 1.0.0
     */
    int deleteDeviceAlarmSet(List<Integer> listDasId);

}
