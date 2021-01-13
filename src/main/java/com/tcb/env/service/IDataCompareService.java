package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;

import com.tcb.env.model.DataCompareModel;

/**
 * <p>[功能描述]：数据对比服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年4月25日上午11:50:32
 * @since EnvDust 1.0.0
 */
public interface IDataCompareService {

    /**
     * <p>[功能描述]：获取日数据</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @param compareTime
     * @param convertType
     * @return
     * @author 王垒, 2018年4月24日上午11:58:25
     * @since EnvDust 1.0.0
     */
    List<DataCompareModel> getDayDataCompare(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime,
            Timestamp compareTime,
            String convertType);

    /**
     * <p>[功能描述]：获取月数据</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @param compareTime
     * @param convertType
     * @return
     * @author 王垒, 2018年4月24日上午11:58:25
     * @since EnvDust 1.0.0
     */
    List<DataCompareModel> getMonthDataCompare(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime,
            Timestamp compareTime,
            String convertType);

}
