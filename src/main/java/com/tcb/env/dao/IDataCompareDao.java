package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.DataCompareModel;

/**
 * <p>[功能描述]：数据对比数据库操作接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年4月24日上午10:44:36
 * @since EnvDust 1.0.0
 */
public interface IDataCompareDao {

    /**
     * <p>[功能描述]：获取日数据</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @param convertType
     * @return
     * @author 王垒, 2018年4月24日上午11:58:25
     * @since EnvDust 1.0.0
     */
    List<DataCompareModel> getDayDataCompare(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime,
            @Param("convertType") String convertType);

    /**
     * <p>[功能描述]：获取月数据</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @param convertType
     * @return
     * @author 王垒, 2018年4月24日上午11:58:25
     * @since EnvDust 1.0.0
     */
    List<DataCompareModel> getMonthDataCompare(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime,
            @Param("convertType") String convertType);


    /**
     * <p>[功能描述]：获取日数据值</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @param convertType
     * @return
     * @author 王垒, 2018年4月24日上午11:58:25
     * @since EnvDust 1.0.0
     */
    String getDayDataValue(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime,
            @Param("convertType") String convertType);

    /**
     * <p>[功能描述]：获取月数据值</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @param convertType
     * @return
     * @author 王垒, 2018年4月24日上午11:58:25
     * @since EnvDust 1.0.0
     */
    String getMonthDataValue(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime,
            @Param("convertType") String convertType);

}
