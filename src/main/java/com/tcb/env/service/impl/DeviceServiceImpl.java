/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.tcb.env.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.dao.IAreaDao;
import com.tcb.env.dao.IDeviceDao;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SortListUtil;

/**
 * <p>
 * [功能描述]：
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月25日上午11:55:07
 * @since EnvDust 1.0.0
 */
@Service("deviceService")
@Transactional(rollbackFor = Exception.class)
class DeviceServiceImpl implements IDeviceService {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DeviceServiceImpl";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeviceServiceImpl.class);

    @Resource
    private IDeviceDao deviceDao;

    @Resource
    private IAreaDao areaDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Override
    public List<Device> getDevice(Device device) {

        return deviceDao.getDevice(device);
    }

    @Override
    public int insertDevice(Device device) throws Exception {
        int result = deviceDao.insertDevice(device);
        return result;
    }

    @Override
    public int getCount(Device device) {

        return deviceDao.getCount(device);
    }

    @Override
    public int deleteDevice(List<Integer> deviceid) throws Exception {

        return deviceDao.deleteDevice(deviceid);
    }

    @Override
    public int updateDevice(List<Device> listdevice) throws Exception {
        return deviceDao.updateDevice(listdevice);
    }

    @Override
    public boolean createStorageTable(Device device) throws Exception {
        boolean flag = false;
        try {
            deviceDao.createStorageTable(device.getDeviceCode());
            flag = true;
        } catch (Exception e) {
            flag = false;
            logger.error(LOG + "：创建表失败，原因：" + e.getMessage());
        }
        return flag;
    }

    @Override
    public void dropStorageTable(Device device) throws Exception {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        String deviceCode = device.getDeviceCode();
        if (dbName != null && !dbName.isEmpty() && deviceCode != null && !deviceCode.isEmpty()
                && deviceDao.extStorageTable(dbName, deviceCode) != null
                && !deviceDao.extStorageTable(dbName, deviceCode).isEmpty()) {
            deviceDao.dropStorageTable(dbName, deviceCode);
        }
        if (dbOldName != null && !dbOldName.isEmpty() && deviceCode != null && !deviceCode.isEmpty()
                && deviceDao.extStorageTable(dbOldName, deviceCode) != null
                && !deviceDao.extStorageTable(dbOldName, deviceCode).isEmpty()) {
            deviceDao.dropStorageTable(dbOldName, deviceCode);
        }
    }

    @Override
    public void dropSampleTable(String deviceCode) throws Exception {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        if (dbName != null && !dbName.isEmpty() && deviceCode != null && !deviceCode.isEmpty()
                && deviceDao.extStorageTable(dbName, deviceCode) != null
                && !deviceDao.extStorageTable(dbName, deviceCode).isEmpty()) {
            deviceDao.dropStorageTable(dbName, deviceCode);
        }
        if (dbOldName != null && !dbOldName.isEmpty() && deviceCode != null && !deviceCode.isEmpty()
                && deviceDao.extStorageTable(dbOldName, deviceCode) != null
                && !deviceDao.extStorageTable(dbOldName, deviceCode).isEmpty()) {
            deviceDao.dropStorageTable(dbOldName, deviceCode);
        }
    }

    @Override
    public int getDeviceCodeExist(int deviceId, String deviceCode) {

        return deviceDao.getDeviceCodeExist(deviceId, deviceCode);
    }

    @Override
    public int getDeviceMnExist(int deviceId, String devicemn) {
        return deviceDao.getDeviceMnExist(deviceId, devicemn);
    }

    @Override
    public int getMapDeviceCount(List<String> listDevCode, String statusCode, String noStatusCode,
                                 Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId) {
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                return deviceDao.getMapDeviceCount(listDevCode, statusCode, noStatusCode, beginAlarmTime, endAlarmTime, overAlarmId);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<MapDeviceModel> getMapDevice(List<String> listDevCode, String statusCode, String noStatusCode,
                                             Timestamp beginAlarmTime, Timestamp endAlarmTime, String overAlarmId, int rowIndex, int rowCount) {
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                return deviceDao.getMapDevice(listDevCode, statusCode, noStatusCode, beginAlarmTime, endAlarmTime, overAlarmId, rowIndex, rowCount);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getMapAlarmDeviceCount(List<String> listDevCode, Timestamp originalUpdateTime, String statusCode) {
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                return deviceDao.getMapAlarmDeviceCount(listDevCode, originalUpdateTime, statusCode);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<MapDeviceModel> getMapAlarmDevice(List<String> listDevCode,
                                                  String statusCode) {
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                return deviceDao.getMapAlarmDevice(listDevCode, statusCode);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDeviceOverThing(String deviceCode,
                                     List<String> listThingCode) {
        String result = "";
        try {
            if (deviceCode != null && !deviceCode.isEmpty()
                    && listThingCode != null && listThingCode.size() > 0) {
                List<String> list = deviceDao.getDeviceOverThing(deviceCode,
                        listThingCode);
                if (list != null && list.size() > 0) {
                    for (String string : list) {
                        if (string != null && !string.isEmpty()) {
                            result += string + ",";
                        }
                    }
                    if (result.length() > 0) {
                        result = result.substring(0, result.length() - 1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取设备状态失败，信息：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getDeviceOverThingAvg(String deviceCode,
                                        List<String> listThingCode) {
        String result = "";
        try {
            if (deviceCode != null && !deviceCode.isEmpty()
                    && listThingCode != null && listThingCode.size() > 0) {
                List<String> list = deviceDao.getDeviceOverThingAvg(deviceCode,
                        listThingCode);
                if (list != null && list.size() > 0) {
                    for (String string : list) {
                        if (string != null && !string.isEmpty()) {
                            result += string + ",";
                        }
                    }
                    if (result.length() > 0) {
                        result = result.substring(0, result.length() - 1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取设备状态失败，信息：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getDeviceName(String deviceCode) {
        return deviceDao.getDeviceName(deviceCode);
    }

    @Override
    public String getDeviceCode(String deviceId) {
        return deviceDao.getDeviceCode(deviceId);
    }

    @Override
    public Map<String, List<ThermodynamicModel>> getThermodynamic(List<String> listDevCode,
                                                                  String dataType, String thingCode, String beginTime, String endTime, boolean wFlag) {
        Map<String, List<ThermodynamicModel>> listMap = new TreeMap<String, List<ThermodynamicModel>>();
        try {
            if (beginTime != null && !beginTime.isEmpty() && dataType != null && !dataType.isEmpty()
                    && listDevCode != null && listDevCode.size() > 0) {
                Timestamp beginStampTime = DateUtil.StringToTimestampSecond(beginTime);
                Timestamp endStampTime = DateUtil.StringToTimestampSecond(endTime);
                while (endStampTime.compareTo(beginStampTime) >= 0) {

                    List<ThermodynamicModel> thermList = new ArrayList<ThermodynamicModel>();
                    String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                    if (!DateUtil.isRecentlyData(beginStampTime, DefaultArgument.RECENT_DAYS)) {
                        dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                    }
                    //总量
                    double allCount = 0;
                    for (String deviceCode : listDevCode) {
                        List<ThermodynamicModel> tempList = deviceDao.getThermodynamic(dbName, deviceCode, dataType, thingCode, beginStampTime);
                        if (tempList != null && tempList.size() > 0) {
                            //获取风速风向
                            for (ThermodynamicModel temp : tempList) {
                                allCount += temp.getCount();
                                if (wFlag) {
                                    //获取风速
                                    String wsValue = deviceDao.getDeviceAppointValue(dbName, temp.getDeviceCode(), dataType, DefaultArgument.PRO_2017_WS_CODE, beginStampTime);
                                    //String wsValue = String.valueOf(Math.random()*20);
                                    temp.setWs(wsValue);
                                    //获取风向
                                    String wdValue = deviceDao.getDeviceAppointValue(dbName, temp.getDeviceCode(), dataType, DefaultArgument.PRO_2017_WD_CODE, beginStampTime);
                                    //String wdValue = String.valueOf(Math.random()*360);
                                    temp.setWd(wdValue);
                                }
                                thermList.add(temp);
                            }
                        }
                    }
                    //赋值百分比
                    if (thermList != null && thermList.size() > 0 && allCount > 0) {
                        for (ThermodynamicModel temp : thermList) {
                            temp.setRatio(String.format("%.2f", (temp.getCount() / allCount) * 100));
                        }
                    }
                    //排序
                    SortListUtil<ThermodynamicModel> sortList = new SortListUtil<ThermodynamicModel>();
                    sortList.sortDouble(thermList, "getCount", "desc");

                    String date_format = "";
                    switch (dataType) {
                        case "2011"://实时数据
                        case "2051"://分钟数据
                            date_format = "yyyy-MM-dd HH:mm";
                            break;
                        case "2031"://日数据
                            date_format = "yyyy-MM-dd";
                            break;
                        case "2061"://小时数据
                            date_format = "yyyy-MM-dd HH";
                        default:
                            break;
                    }
                    String timeKey = DateUtil.TimestampToString(beginStampTime, date_format);
                    if (!listMap.containsKey(timeKey)) {
                        listMap.put(timeKey, thermList);
                    } else {
                        listMap.remove(timeKey);
                        listMap.put(timeKey, thermList);
                    }
                    beginStampTime = DateUtil.getAddTime(beginStampTime, dataType);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取热力数据失败，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

    @Override
    public Map<String, List<ThermodynamicModel>> getThermRecently(
            List<String> listDeviceCode, String thingCode) {
        Map<String, List<ThermodynamicModel>> listMap = new TreeMap<String, List<ThermodynamicModel>>();
        List<ThermodynamicModel> list = deviceDao.getThermRecently(listDeviceCode, thingCode);
        String timeKey = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        listMap.put(timeKey, list);
        return listMap;
    }

    @Override
    public Map<String, List<LocationModel>> getSpreadRecently(
            List<String> listDeviceCode, String wd, String ws, String thingCode) {
        Map<String, List<LocationModel>> listMap = new TreeMap<String, List<LocationModel>>();
        List<LocationModel> list = new ArrayList<LocationModel>();
        List<String> listThingCode = new ArrayList<String>();
        listThingCode.add(wd);
        listThingCode.add(ws);
        listThingCode.add(thingCode);
        List<LocationModel> listVal = deviceDao.getLocationRecently(listDeviceCode, thingCode);
        List<LocationModel> listWd = deviceDao.getLocationRecently(listDeviceCode, wd);
        List<LocationModel> listWs = deviceDao.getLocationRecently(listDeviceCode, ws);
        for (LocationModel locationModel : listVal) {
            for (LocationModel temp : listWd) {
                if (temp.getCode() != null && temp.getCode().equals(locationModel.getCode())) {
                    locationModel.setWd(temp.getVal());
                    break;
                }
            }
            for (LocationModel temp : listWs) {
                if (temp.getCode() != null && temp.getCode().equals(locationModel.getCode())) {
                    locationModel.setWs(temp.getVal());
                    break;
                }
            }
            list.add(locationModel);
        }
        String timeKey = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        listMap.put(timeKey, list);
        return listMap;
    }

    @Override
    public AreaStatisticModel getAreaValue(int areaId, String dataType,
                                           String thingCode, String beginTime, String endTime) {
        AreaStatisticModel areaStatisticModel = new AreaStatisticModel();
        try {
            double totalGasFlow = 0;//总排放烟气流量
            double totalDischarge = 0;//总排放量
            String areaName = areaDao.getAreaName(areaId);//工地园区名称
            Timestamp beginStampTime = DateUtil.StringToTimestampSecond(beginTime);
            Timestamp endStampTime = DateUtil.StringToTimestampSecond(endTime);
            while (endStampTime.compareTo(beginStampTime) >= 0) {
                double unitGasFlow = 0;//单位时间总排放烟气流量
                double unitDischarge = 0;//单位时间总排放量
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                String dbOldName = "";
                if (!DateUtil.isRecentlyData(beginStampTime, DefaultArgument.RECENT_DAYS)) {
                    dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                //获取监测物数值
                List<AreaDeviceValueModel> listADVThingVal = deviceDao.getAreaDeviceValue(dbName, dbOldName, areaId, null, dataType, thingCode, beginStampTime);
                for (AreaDeviceValueModel areaDeviceValueModel : listADVThingVal) {
                    if (areaDeviceValueModel != null) {
                        String deviceCode = areaDeviceValueModel.getDeviceCode();
                        String gasRate = areaDeviceValueModel.getDataValue();
                        Timestamp dataTime = DateUtil.StringToTimestampSecond(areaDeviceValueModel.getDataTime());
                        List<AreaDeviceValueModel> listADVGasFlow = deviceDao.getAreaDeviceValue(dbName, dbOldName, areaId, deviceCode, dataType, DefaultArgument.GAS_FLOW_CODE, dataTime);
                        if (listADVGasFlow == null || listADVGasFlow.size() == 0) {
                            listADVGasFlow = deviceDao.getAreaDeviceValue(dbName, dbOldName, areaId, deviceCode, dataType, DefaultArgument.WS, dataTime);
                        }
                        if (listADVGasFlow != null && listADVGasFlow.size() > 0) {
                            String gasFlow = listADVGasFlow.get(0).getDataValue();
                            if (gasRate != null && !gasRate.isEmpty() && gasFlow != null && !gasFlow.isEmpty()) {
                                unitGasFlow += Double.valueOf(gasFlow);
                                unitDischarge += (Double.valueOf(gasRate) * Double.valueOf(gasFlow));
                            }
                        }
                    }
                }
                totalGasFlow += unitGasFlow;
                totalDischarge += unitDischarge;
                beginStampTime = DateUtil.getAddTime(beginStampTime, dataType);
            }
            areaStatisticModel.setAreaId(String.valueOf(areaId));
            areaStatisticModel.setAreaName(areaName);
            if (totalGasFlow == 0) {
                areaStatisticModel.setAvgValue(String.format("%.2f", totalDischarge));
            } else {
                areaStatisticModel.setAvgValue(String.format("%.2f", totalDischarge / totalGasFlow));
            }
            areaStatisticModel.setTotalValue(String.format("%.2f", totalDischarge));
        } catch (Exception e) {
            logger.error(LOG + ":获取区域排放统计数据失败，错误信息为：" + e.getMessage());
        }
        return areaStatisticModel;
    }

    @Override
    public int updateDeviceLocation(String deviceCode, String deviceX,
                                    String deviceY) {
        int result = 0;
        if (deviceX != null && !deviceX.isEmpty() && deviceY != null && !deviceY.isEmpty()) {
            result = deviceDao.updateDeviceLocation(deviceCode, deviceX, deviceY);
        }
        return result;
    }

    @Override
    public List<Device> getAreaAuthDevice(String areaId, String userCode) {
        return deviceDao.getAreaAuthDevice(areaId, userCode);
    }

    @Override
    public String getAreaName(String deviceCode) {
        return deviceDao.getAreaName(deviceCode);
    }

    @Override
    public GasPathModel getGapPath(String deviceCode) {
        return deviceDao.getGapPath(deviceCode);
    }

    @Override
    public boolean createSampleTable(Device device) {
        boolean flag = false;
        try {
            deviceDao.createSampleTable(device.getDeviceCode());
            flag = true;
        } catch (Exception e) {
            flag = false;
            logger.error(LOG + "：创建表失败，原因：" + e.getMessage());
        }
        return flag;
    }

}
