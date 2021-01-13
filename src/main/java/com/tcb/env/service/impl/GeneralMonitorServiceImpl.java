package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IGeneralMonitorDao;
import com.tcb.env.model.AreaModel;
import com.tcb.env.model.DeviceValueModel;
import com.tcb.env.model.GeneralAlarmModel;
import com.tcb.env.model.GeneralDeviceDataModel;
import com.tcb.env.model.GeneralDeviceLocationModel;
import com.tcb.env.service.IGeneralMonitorService;

/**
 * <p>[功能描述]：获取各物质浓度值接口实现类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年12月22日上午10:42:24
 * @since EnvDust 1.0.0
 */
@Service("generalMonitorService")
class GeneralMonitorServiceImpl implements IGeneralMonitorService {

    @Resource
    private IGeneralMonitorDao generalMonitorDao;

    @Override
    public List<DeviceValueModel> getGenaralMonitorValueRanking(
            List<String> deviceCodeList, String thingCode, String dataType, String onlineFlag,
            String order, int limit) {
        return generalMonitorDao.getGenaralMonitorValueRanking(deviceCodeList, thingCode, dataType, onlineFlag, order, limit);
    }

    @Override
    public String getDeviceListAvgValue(List<String> deviceCodeList, String thingCode, String dataType) {
        return generalMonitorDao.getDeviceListAvgValue(deviceCodeList, thingCode, dataType);
    }

    @Override
    public List<GeneralDeviceLocationModel> getGeneralDeviceLocation(
            List<String> deviceCodeList, List<String> dataTypeList, List<String> noDataTypeList) {
        return generalMonitorDao.getGeneralDeviceLocation(deviceCodeList, dataTypeList, noDataTypeList);
    }

    @Override
    public List<GeneralAlarmModel> getGeneralAlarm(List<String> deviceCodeList,
                                                   List<String> dataTypeList, List<String> noDataTypeList, int limit) {
        return generalMonitorDao.getGeneralAlarm(deviceCodeList, dataTypeList, noDataTypeList, limit);
    }

    @Override
    public List<GeneralDeviceDataModel> getGeneralDeviceData(
            List<String> deviceCodeList,
            List<String> thingCodeList,
            List<String> dataTypeList,
            List<String> statusCodeList,
            List<String> noStatusCodeList) {
        return generalMonitorDao.getGeneralDeviceData(deviceCodeList, thingCodeList, dataTypeList, statusCodeList, noStatusCodeList);
    }

    @Override
    public List<AreaModel> getAuthorityDeviceArea(String userCode) {
        return generalMonitorDao.getAuthorityDeviceArea(userCode);
    }

    @Override
    public List<GeneralDeviceDataModel> getGeneralDeviceNoData(
            List<String> deviceCodeList, List<String> statusCodeList,
            List<String> noStatusCodeList) {
        return generalMonitorDao.getGeneralDeviceNoData(deviceCodeList, statusCodeList, noStatusCodeList);
    }

}
