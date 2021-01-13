package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.AreaModel;
import com.tcb.env.model.DeviceValueModel;
import com.tcb.env.model.GeneralAlarmModel;
import com.tcb.env.model.GeneralDeviceDataModel;
import com.tcb.env.model.GeneralDeviceLocationModel;

/**
 * <p>[功能描述]：监控数据库接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年12月22日上午10:43:07
 * @since EnvDust 1.0.0
 */
public interface IGeneralMonitorDao {

    /**
     * <p>[功能描述]：设备排放值排名</p>
     *
     * @param deviceCodeList
     * @param thingCode
     * @param dataType
     * @param onlineFlag
     * @param order
     * @param limit
     * @return
     * @author 王垒, 2017年12月22日上午10:54:23
     * @since EnvDust 1.0.0
     */
    List<DeviceValueModel> getGenaralMonitorValueRanking(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("thingCode") String thingCode,
            @Param("dataType") String dataType,
            @Param("onlineFlag") String onlineFlag,
            @Param("order") String order,
            @Param("limit") int limit);

    /**
     * 设备监测物平均值
     * @param deviceCodeList
     * @param thingCode
     * @param dataType
     * @return
     */
    String getDeviceListAvgValue(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("thingCode") String thingCode,
            @Param("dataType") String dataType);

    /**
     * <p>[功能描述]：查询设备坐标状态</p>
     *
     * @param deviceCodeList
     * @param dataTypeList
     * @param noDataTypeList
     * @return
     * @author 王垒, 2018年1月4日上午11:22:14
     * @since EnvDust 1.0.0
     */
    List<GeneralDeviceLocationModel> getGeneralDeviceLocation(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("dataTypeList") List<String> dataTypeList,
            @Param("noDataTypeList") List<String> noDataTypeList);

    /**
     * <p>[功能描述]：查询报警信息</p>
     *
     * @param deviceCodeList
     * @param dataTypeList
     * @param noDataTypeList
     * @param limit
     * @return
     * @author 王垒, 2018年1月4日下午12:28:03
     * @since EnvDust 1.0.0
     */
    List<GeneralAlarmModel> getGeneralAlarm(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("dataTypeList") List<String> dataTypeList,
            @Param("noDataTypeList") List<String> noDataTypeList,
            @Param("limit") int limit);

    /**
     * <p>[功能描述]：查询设备最新数据信息</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param dataTypeList
     * @param statusCodeList
     * @param noStatusCodeList
     * @return
     * @author 王垒, 2018年1月4日下午3:56:30
     * @since EnvDust 1.0.0
     */
    List<GeneralDeviceDataModel> getGeneralDeviceData(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("thingCodeList") List<String> thingCodeList,
            @Param("dataTypeList") List<String> dataTypeList,
            @Param("statusCodeList") List<String> statusCodeList,
            @Param("noStatusCodeList") List<String> noStatusCodeList);

    /**
     * <p>[功能描述]：查询无数据设备信息</p>
     *
     * @param deviceCodeList
     * @param statusCodeList
     * @param noStatusCodeList
     * @return
     * @author 王垒, 2018年1月5日下午12:54:10
     * @since EnvDust 1.0.0
     */
    List<GeneralDeviceDataModel> getGeneralDeviceNoData(
            @Param("deviceCodeList") List<String> deviceCodeList,
            @Param("statusCodeList") List<String> statusCodeList,
            @Param("noStatusCodeList") List<String> noStatusCodeList);

    /**
     * <p>[功能描述]：获取用户权限设备的区域列表</p>
     *
     * @param userCode
     * @return
     * @author 王垒, 2018年1月4日下午2:33:28
     * @since EnvDust 1.0.0
     */
    List<AreaModel> getAuthorityDeviceArea(@Param("userCode") String userCode);

}
