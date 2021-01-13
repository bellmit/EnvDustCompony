package com.tcb.env.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.EnvStatisticalReportModel;

/**
 * <p>[功能描述]：统计报表Dao</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月4日下午2:59:33
 * @since EnvDust 1.0.0
 */
public interface IEnvStatisticalReportDao {

    /**
     * <p>[功能描述]：查询时统计报表个数</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrHourCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：查询时统计报表</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrHour(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>[功能描述]：查询日统计报表个数</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrDayCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：查询日统计报表</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrDay(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>[功能描述]：查询月统计报表个数</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrMonthCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：查询月统计报表</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrMonth(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>[功能描述]：查询年统计报表个数</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrYearCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：查询年统计报表</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrYear(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("selectTime") Timestamp selectTime,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>[功能描述]：查询时间段统计报表个数</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrTimesCount(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("beginTime") Timestamp beginTime,
            @Param("endTime") Timestamp endTime);

    /**
     * <p>[功能描述]：查询时间段统计报表</p>
     *
     * @param dbName
     * @param dbOldName
     * @param deviceCode
     * @param thingCodeList
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrTimes(
            @Param("dbName") String dbName,
            @Param("dbOldName") String dbOldName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("beginTime") Timestamp beginTime,
            @Param("endTime") Timestamp endTime,
            @Param("rowIndex") int rowIndex,
            @Param("rowCount") int rowCount);

    /**
     * <p>[功能描述]：获取指定小时数据值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:03:51
     * @since EnvDust 1.0.0
     */
    String getOlrHour(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定小时数据折算值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:03:51
     * @since EnvDust 1.0.0
     */
    String getOlrZsHour(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定小时数据流量折算值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:03:51
     * @since EnvDust 1.0.0
     */
    String getOlrZsHourFlow(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定日数据值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:05:10
     * @since EnvDust 1.0.0
     */
    String getOlrDay(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定日数据折算值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:05:10
     * @since EnvDust 1.0.0
     */
    String getOlrZsDay(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定日数据流量折算值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:05:10
     * @since EnvDust 1.0.0
     */
    String getOlrZsDayFlow(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定月数据值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:05:26
     * @since EnvDust 1.0.0
     */
    String getOlrZsMonth(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定月数据流量折算值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:05:10
     * @since EnvDust 1.0.0
     */
    String getOlrZsMonthFlow(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取指定月数据值</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午10:05:26
     * @since EnvDust 1.0.0
     */
    String getOlrMonth(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取超标报警日条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午10:19:23
     * @since EnvDust 1.0.0
     */
    int getOverAlarmDayCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("levelNo") String levelNo,
            @Param("convertType") String convertType,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取每日所有数据条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午10:33:00
     * @since EnvDust 1.0.0
     */
    int getAllDayCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取超标报警月条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午10:43:24
     * @since EnvDust 1.0.0
     */
    int getOverAlarmMonthCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("levelNo") String levelNo,
            @Param("convertType") String convertType,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取每月所有数据条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午10:33:00
     * @since EnvDust 1.0.0
     */
    int getAllMonthCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取超标报警年条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午10:43:24
     * @since EnvDust 1.0.0
     */
    int getOverAlarmYearCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("levelNo") String levelNo,
            @Param("convertType") String convertType,
            @Param("selectTime") Timestamp selectTime);

    /**
     * <p>[功能描述]：获取每年所有数据条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午10:33:00
     * @since EnvDust 1.0.0
     */
    int getAllYearCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("selectTime") Timestamp selectTime);


    /**
     * <p>[功能描述]：获取超标报警时间段条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年8月6日上午10:19:23
     * @since EnvDust 1.0.0
     */
    int getOverAlarmTimesCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("levelNo") String levelNo,
            @Param("convertType") String convertType,
            @Param("beginTime") Timestamp beginTime,
            @Param("endTime") Timestamp endTime);

    /**
     * <p>[功能描述]：获取时间段所有条数</p>
     *
     * @param dbName
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年8月6日上午10:19:23
     * @since EnvDust 1.0.0
     */
    int getAllTimesCount(
            @Param("dbName") String dbName,
            @Param("deviceCode") String deviceCode,
            @Param("thingCode") String thingCode,
            @Param("updateType") String updateType,
            @Param("beginTime") Timestamp beginTime,
            @Param("endTime") Timestamp endTime);


}
