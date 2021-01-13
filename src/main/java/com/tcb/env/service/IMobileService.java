package com.tcb.env.service;

import com.tcb.env.model.MobilePointModel;

import java.util.List;

/**
 * <p>[功能描述]：移动端服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2019年4月10日下午1:36:53
 * @since EnvDust 1.0.0
 */
public interface IMobileService {

    /**
     * 获取设备点位信息
     *
     * @param deviceCodeList
     * @param thingCode
     * @param dataType
     * @param levelNo
     * @return
     */
    List<MobilePointModel> getMobilePoint(List<String> deviceCodeList, String thingCode, String dataType, String levelNo);

    /**
     * 获取设备最新上传数据
     *
     * @param deviceCode
     * @param dataType
     * @return
     */
    MobilePointModel getPointRecentData(String deviceCode, String dataType);

    /**
     * 获取多设备最新上传平均数据
     *
     * @param deviceCodeList
     * @param dataType
     * @return
     */
    MobilePointModel getPointsRecentData(List<String> deviceCodeList, List<String> thingCodeList, String dataType);

}
