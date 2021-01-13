package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.AreaModel;
import com.tcb.env.model.DeviceValueModel;
import com.tcb.env.model.GeneralAlarmModel;
import com.tcb.env.model.GeneralDeviceDataModel;
import com.tcb.env.model.GeneralDeviceLocationModel;

/**
 * <p>[功能描述]：监控数据接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年12月22日上午10:20:41
 * @since EnvDust 1.0.0
 */
public interface IGeneralMonitorService {

    /**
     * <p>[功能描述]：区域内设备排放值排名</p>
     *
     * @param deviceCodeList
     * @param thingCode
     * @param dataType
     * @param order
     * @param limit
     * @return
     * @author 王垒, 2017年12月22日上午10:39:55
     * @since EnvDust 1.0.0
     */
    List<DeviceValueModel> getGenaralMonitorValueRanking(
            List<String> deviceCodeList, String thingCode,
            String dataType, String onlineFlag, String order, int limit);

    /**
     * 设备监测物平均值
     * @param deviceCodeList
     * @param thingCode
     * @param dataType
     * @return
     */
    String getDeviceListAvgValue(
            List<String> deviceCodeList,
            String thingCode,
            String dataType);

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
            List<String> deviceCodeList,
            List<String> dataTypeList,
            List<String> noDataTypeList);

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
            List<String> deviceCodeList,
            List<String> dataTypeList,
            List<String> noDataTypeList,
            int limit);

    /**
     * <p>[功能描述]：查询设备最新数据信息</p>
     *
     * @param deviceCodeList
     * @param thingCodeList
     * @param dataTypeList
     * @param statusCodeList
     * @param noStatusCodeList
     * @return
     * @author 王垒, 2018年1月4日下午3:59:10
     * @since EnvDust 1.0.0
     */
    List<GeneralDeviceDataModel> getGeneralDeviceData(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            List<String> dataTypeList,
            List<String> statusCodeList,
            List<String> noStatusCodeList);

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
            List<String> deviceCodeList,
            List<String> statusCodeList,
            List<String> noStatusCodeList);

    /**
     * <p>[功能描述]：获取用户权限设备的区域列表</p>
     *
     * @param userCode
     * @return
     * @author 王垒, 2018年1月4日下午2:33:28
     * @since EnvDust 1.0.0
     */
    List<AreaModel> getAuthorityDeviceArea(String userCode);

}
