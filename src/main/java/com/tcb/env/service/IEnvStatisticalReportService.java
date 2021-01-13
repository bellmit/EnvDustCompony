package com.tcb.env.service;

import java.sql.Timestamp;
import java.util.List;

import com.tcb.env.model.AlarmRateModel;
import com.tcb.env.model.EnvStatisticalReportModel;
import com.tcb.env.model.OnlineMonitorReportMainModel;

/**
 * <p>[功能描述]：年月日统计报表接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月4日下午3:50:02
 * @since EnvDust 1.0.0
 */
public interface IEnvStatisticalReportService {

    /**
     * <p>[功能描述]：查询时统计报表个数</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrHourCount(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime);

    /**
     * <p>[功能描述]：查询时统计报表</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrHour(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime,
            int rowIndex,
            int rowCount);

    /**
     * <p>[功能描述]：查询日统计报表个数</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrDayCount(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime);

    /**
     * <p>[功能描述]：查询日统计报表</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrDay(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime,
            int rowIndex,
            int rowCount);

    /**
     * <p>[功能描述]：查询月统计报表个数</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrMonthCount(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime);

    /**
     * <p>[功能描述]：查询月统计报表</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrMonth(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime,
            int rowIndex,
            int rowCount);

    /**
     * <p>[功能描述]：查询年统计报表个数</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrYearCount(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime);

    /**
     * <p>[功能描述]：查询年统计报表</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrYear(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp selectTime,
            int rowIndex,
            int rowCount);

    /**
     * <p>[功能描述]：查询时间段统计报表个数</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    int getEsrTimesCount(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp beginTime,
            Timestamp endTime);

    /**
     * <p>[功能描述]：查询时间段统计报表</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年5月4日下午3:12:17
     * @since EnvDust 1.0.0
     */
    List<EnvStatisticalReportModel> getEsrTimes(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            Timestamp beginTime,
            Timestamp endTime,
            int rowIndex,
            int rowCount);

    /**
     * <p>[功能描述]：查询连续监测日报表</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午9:21:23
     * @since EnvDust 1.0.0
     */
    OnlineMonitorReportMainModel getOlrZsDay(
            String deviceCode,
            String thingCode,
            String selectTime);

    /**
     * <p>[功能描述]：查询连续监测月报表</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午9:21:23
     * @since EnvDust 1.0.0
     */
    OnlineMonitorReportMainModel getOlrZsMonth(
            String deviceCode,
            String thingCode,
            String selectTime);

    /**
     * <p>[功能描述]：查询连续监测年报表</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @return
     * @author 王垒, 2018年5月17日上午9:21:23
     * @since EnvDust 1.0.0
     */
    OnlineMonitorReportMainModel getOlrZsYear(
            String deviceCode,
            String thingCode,
            String selectTime);

    /**
     * <p>[功能描述]：查询连续监测时间段报表</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年5月17日上午9:21:23
     * @since EnvDust 1.0.0
     */
    OnlineMonitorReportMainModel getOlrZsTimes(
            String deviceCode,
            String thingCode,
            String beginTime,
            String endTime);

    /**
     * <p>[功能描述]：查询超标率-日</p>
     *
     * @param deviceCodeList
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午11:08:44
     * @since EnvDust 1.0.0
     */
    List<AlarmRateModel> getAlarmDayRate(
            List<String> deviceCodeList,
            String thingCode,
            String updateType,
            String levelNo,
            String convertType,
            String selectTime);

    /**
     * <p>[功能描述]：查询超标率-月</p>
     *
     * @param deviceCodeList
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午11:08:44
     * @since EnvDust 1.0.0
     */
    List<AlarmRateModel> getAlarmMonthRate(
            List<String> deviceCodeList,
            String thingCode,
            String updateType,
            String levelNo,
            String convertType,
            String selectTime);

    /**
     * <p>[功能描述]：查询超标率-年</p>
     *
     * @param deviceCodeList
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param selectTime
     * @return
     * @author 王垒, 2018年8月6日上午11:08:44
     * @since EnvDust 1.0.0
     */
    List<AlarmRateModel> getAlarmYearRate(
            List<String> deviceCodeList,
            String thingCode,
            String updateType,
            String levelNo,
            String convertType,
            String selectTime);

    /**
     * <p>[功能描述]：查询超标率-时间段</p>
     *
     * @param deviceCodeList
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2018年8月6日上午11:08:44
     * @since EnvDust 1.0.0
     */
    List<AlarmRateModel> getAlarmTimesRate(
            List<String> deviceCodeList,
            String thingCode,
            String updateType,
            String levelNo,
            String convertType,
            String beginTime,
            String endTime);

    /**
     * 查询超标率-时间段
     *
     * @param deviceCodeList
     * @param thingCode
     * @param updateType
     * @param levelNo
     * @param convertType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<AlarmRateModel> getAlarmTimesRate(
            List<String> deviceCodeList,
            String thingCode,
            String updateType,
            String levelNo,
            String convertType,
            Timestamp beginTime,
            Timestamp endTime);

}
