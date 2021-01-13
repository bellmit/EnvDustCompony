package com.tcb.env.dao;

import com.tcb.env.model.MobileMonitorModel;
import com.tcb.env.model.MobilePointModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>[功能描述]：移动端数据库接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2019年4月10日下午1:36:53
 * @since EnvDust 1.0.0
 */
public interface IMobileDao {

    /**
     * 获取设备点位信息
     *
     * @param deviceCodeList
     * @param thingCode
     * @param dataType
     * @param levelNo
     * @return
     */
    List<MobilePointModel> getMobilePoint(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("thingCode") String thingCode,
            @Param("dataType") String dataType,
            @Param("levelNo") String levelNo);

    /**
     * 获取设备最新上传数据
     *
     * @param deviceCode
     * @param dataType
     * @return
     */
    List<MobilePointModel> getPointRecentData(
            @Param("deviceCode") String deviceCode,
            @Param("dataType") String dataType);

    /**
     * 获取多设备最新上传平均数据
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param dataType
     * @return
     */
    MobilePointModel getPointsRecentData(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("dataType") String dataType);

    /**
     * 获取监测物级别信息
     *
     * @param mobileMonitorModel
     * @return
     */
    MobileMonitorModel getThingLevel(
            @Param("mobileMonitorModel") MobileMonitorModel mobileMonitorModel);

}
