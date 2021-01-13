package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.dao.IDeviceDao;
import com.tcb.env.dao.IEnvStatisticalReportDao;
import com.tcb.env.dao.IMonitorDao;
import com.tcb.env.model.AlarmRateModel;
import com.tcb.env.model.EnvStatisticalReportModel;
import com.tcb.env.model.OnlineMonitorReportMainModel;
import com.tcb.env.model.OnlineMonitorReportTimeModel;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.IEnvStatisticalReportService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.springframework.util.StringUtils;

/**
 * <p>[功能描述]：年月日统计报表接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月4日下午3:57:30
 * @since EnvDust 1.0.0
 */
@Service("envStatisticalReportService")
class EnvStatisticalReportServiceImpl implements IEnvStatisticalReportService {

    /**
     * 日志输出标记
     */
    private static final String LOG = "EnvStatisticalReportServiceImpl";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(EnvStatisticalReportServiceImpl.class);

    @Resource
    private IEnvStatisticalReportDao envStatisticalReportDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Resource
    private IDeviceDao deviceDao;

    @Resource
    private IMonitorDao monitorDao;


    @Override
    public int getEsrHourCount(List<String> deviceCodeList,
                               List<String> thingCodeList, Timestamp selectTime) {
        int count = 0;
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    count += envStatisticalReportDao.getEsrHourCount
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime);
                } catch (Exception e) {
                    logger.error(LOG + "：查询时统计报表个数失败，编码为：" + deviceCode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取时统计报表个数失败，错误信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<EnvStatisticalReportModel> getEsrHour(
            List<String> deviceCodeList, List<String> thingCodeList,
            Timestamp selectTime, int rowIndex, int rowCount) {
        List<EnvStatisticalReportModel> listStorage = new ArrayList<EnvStatisticalReportModel>();
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    List<EnvStatisticalReportModel> tempList = envStatisticalReportDao.getEsrHour
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime, rowIndex, rowCount);
                    if (tempList != null && tempList.size() > 0) {
                        listStorage.addAll(tempList);
                    }
                } catch (Exception e) {
                    logger.error(LOG + "：查询时统计报表失败，编码为：" + deviceCode);
                }
            }

        } catch (Exception e) {
            logger.error(LOG + "：查询时统计报表失败，信息为：" + e.getMessage());
        }
        return listStorage;
    }

    @Override
    public int getEsrDayCount(List<String> deviceCodeList, List<String> thingCodeList,
                              Timestamp selectTime) {
        int count = 0;
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    count += envStatisticalReportDao.getEsrDayCount
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime);
                } catch (Exception e) {
                    logger.error(LOG + "：查询日统计报表个数失败，编码为：" + deviceCode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取日统计报表个数失败，错误信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<EnvStatisticalReportModel> getEsrDay(List<String> deviceCodeList,
                                                     List<String> thingCodeList, Timestamp selectTime, int rowIndex,
                                                     int rowCount) {
        List<EnvStatisticalReportModel> listStorage = new ArrayList<EnvStatisticalReportModel>();
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    List<EnvStatisticalReportModel> tempList = envStatisticalReportDao.getEsrDay
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime, rowIndex, rowCount);
                    if (tempList != null && tempList.size() > 0) {
                        listStorage.addAll(tempList);
                    }
                } catch (Exception e) {
                    logger.error(LOG + "：查询日统计报表失败，编码为：" + deviceCode);
                }
            }

        } catch (Exception e) {
            logger.error(LOG + "：查询日统计报表失败，信息为：" + e.getMessage());
        }
        return listStorage;
    }

    @Override
    public int getEsrMonthCount(List<String> deviceCodeList, List<String> thingCodeList,
                                Timestamp selectTime) {
        int count = 0;
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    count += envStatisticalReportDao.getEsrMonthCount
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime);
                } catch (Exception e) {
                    logger.error(LOG + "：查询月统计报表个数失败，编码为：" + deviceCode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取月统计报表个数失败，错误信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<EnvStatisticalReportModel> getEsrMonth(List<String> deviceCodeList,
                                                       List<String> thingCodeList, Timestamp selectTime, int rowIndex,
                                                       int rowCount) {
        List<EnvStatisticalReportModel> listStorage = new ArrayList<EnvStatisticalReportModel>();
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    List<EnvStatisticalReportModel> tempList = envStatisticalReportDao.getEsrMonth
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime, rowIndex, rowCount);
                    if (tempList != null && tempList.size() > 0) {
                        listStorage.addAll(tempList);
                    }
                } catch (Exception e) {
                    logger.error(LOG + "：查询月统计报表失败，编码为：" + deviceCode);
                }
            }

        } catch (Exception e) {
            logger.error(LOG + "：查询月统计报表失败，信息为：" + e.getMessage());
        }
        return listStorage;
    }

    @Override
    public int getEsrYearCount(List<String> deviceCodeList, List<String> thingCodeList,
                               Timestamp selectTime) {
        int count = 0;
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    count += envStatisticalReportDao.getEsrYearCount
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime);
                } catch (Exception e) {
                    logger.error(LOG + "：查询年统计报表个数失败，编码为：" + deviceCode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取年统计报表个数失败，错误信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<EnvStatisticalReportModel> getEsrYear(List<String> deviceCodeList,
                                                      List<String> thingCodeList, Timestamp selectTime, int rowIndex,
                                                      int rowCount) {
        List<EnvStatisticalReportModel> listStorage = new ArrayList<EnvStatisticalReportModel>();
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    List<EnvStatisticalReportModel> tempList = envStatisticalReportDao.getEsrYear
                            (dbName, dbOldName, deviceCode, thingCodeList, selectTime, rowIndex, rowCount);
                    if (tempList != null && tempList.size() > 0) {
                        listStorage.addAll(tempList);
                    }
                } catch (Exception e) {
                    logger.error(LOG + "：查询年统计报表失败，编码为：" + deviceCode);
                }
            }

        } catch (Exception e) {
            logger.error(LOG + "：查询年统计报表失败，信息为：" + e.getMessage());
        }
        return listStorage;
    }

    @Override
    public int getEsrTimesCount(List<String> deviceCodeList,
                                List<String> thingCodeList, Timestamp beginTime, Timestamp endTime) {
        int count = 0;
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(beginTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    count += envStatisticalReportDao.getEsrTimesCount
                            (dbName, dbOldName, deviceCode, thingCodeList, beginTime, endTime);
                } catch (Exception e) {
                    logger.error(LOG + "：查询时间段统计报表个数失败，编码为：" + deviceCode);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取时间段统计报表个数失败，错误信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<EnvStatisticalReportModel> getEsrTimes(List<String> deviceCodeList,
                                                       List<String> thingCodeList, Timestamp beginTime, Timestamp endTime,
                                                       int rowIndex, int rowCount) {
        List<EnvStatisticalReportModel> listStorage = new ArrayList<EnvStatisticalReportModel>();
        try {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(beginTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                try {
                    List<EnvStatisticalReportModel> tempList = envStatisticalReportDao.getEsrTimes
                            (dbName, dbOldName, deviceCode, thingCodeList, beginTime, endTime, rowIndex, rowCount);
                    if (tempList != null && tempList.size() > 0) {
                        listStorage.addAll(tempList);
                    }
                } catch (Exception e) {
                    logger.error(LOG + "：查询时间段统计报表失败，编码为：" + deviceCode);
                }
            }

        } catch (Exception e) {
            logger.error(LOG + "：查询时间段统计报表失败，信息为：" + e.getMessage());
        }
        return listStorage;
    }

    @Override
    public OnlineMonitorReportMainModel getOlrZsDay(
            String deviceCode, String thingCode, String selectTime) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        Device device = new Device();
        device.setDeviceCode(deviceCode);
        List<Device> deviceList = deviceDao.getDevice(device);
        if (deviceList != null && deviceList.size() == 1) {
            onlineMonitorReportMainModel.setDeviceCode(deviceList.get(0).getDeviceCode());
            onlineMonitorReportMainModel.setDeviceMn(deviceList.get(0).getDeviceMn());
            onlineMonitorReportMainModel.setDeviceName(deviceList.get(0).getDeviceName());
            onlineMonitorReportMainModel.setThingName(monitorDao.getMonitorName(thingCode));
            onlineMonitorReportMainModel.setMonitorDate(selectTime);
            List<OnlineMonitorReportTimeModel> omrtModelList = new ArrayList<OnlineMonitorReportTimeModel>();
            for (int i = 0; i < 24; i++) {
                OnlineMonitorReportTimeModel onlineMonitorReportTimeModel = new OnlineMonitorReportTimeModel();
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                Timestamp timeSelect = DateUtil.StringToTimestampSecond(selectTime + " " + String.format("%02d", i) + ":00:00");
                if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                    dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                String memo = "";
                String timeDis = String.format("%02d", i) + "~" + String.format("%02d", (i + 1));
                String thingValue = envStatisticalReportDao.getOlrHour(dbName, deviceCode, thingCode, timeSelect);//监测项浓度(mg/m3)
                if (thingValue == null || thingValue.isEmpty()) {
                    thingValue = "0";
                    memo += "无数据;";
                }
                String flowSpeed = envStatisticalReportDao.getOlrHour(dbName, deviceCode, DefaultArgument.PRO_GAS_VELOCITY_CODE, timeSelect);//烟气流速(m/s)
                String standardFlow = envStatisticalReportDao.getOlrZsHourFlow(dbName, deviceCode, DefaultArgument.PRO_EXHOUST_GAS_CODE, timeSelect);//标态流量(m3/h)
                if (standardFlow == null || standardFlow.isEmpty()) {
                    standardFlow = "0";
                }
                String thingCou = String.format("%.8f", (Double.valueOf(thingValue) * Double.valueOf(standardFlow) / (1000 * 1000)));//流量(kg/h)
                String temperature = envStatisticalReportDao.getOlrHour(dbName, deviceCode, DefaultArgument.PRO_GAS_TEMPERATURE_CODE, timeSelect);//烟气温度(c)
                String humidity = envStatisticalReportDao.getOlrHour(dbName, deviceCode, DefaultArgument.PRO_GAS_HUMIDITY_CODE, timeSelect);//烟气湿度(%)
                onlineMonitorReportTimeModel.setTime(timeDis);
                onlineMonitorReportTimeModel.setThingValue(thingValue);
                onlineMonitorReportTimeModel.setThingCou(thingCou);
                onlineMonitorReportTimeModel.setStandardFlow(standardFlow);
                onlineMonitorReportTimeModel.setFlowSpeed(flowSpeed);
                onlineMonitorReportTimeModel.setTemperature(temperature);
                onlineMonitorReportTimeModel.setHumidity(humidity);
                onlineMonitorReportTimeModel.setMemo(memo);
                omrtModelList.add(onlineMonitorReportTimeModel);
            }
            if (omrtModelList != null && omrtModelList.size() > 0) {
                //平均值
                OnlineMonitorReportTimeModel omtrmAvg = new OnlineMonitorReportTimeModel();
                omtrmAvg.setTime("平均值");
                double thingValueAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).average().getAsDouble();
                double thingCouAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).average().getAsDouble();
                double standardFlowAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).average().getAsDouble();
                double flowSpeedAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).average().getAsDouble();
                double temperatureAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).average().getAsDouble();
                double humidityAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).average().getAsDouble();
                omtrmAvg.setThingValue(String.format("%.3f", thingValueAvg));
                omtrmAvg.setThingCou(String.format("%.8f", thingCouAvg));
                omtrmAvg.setStandardFlow(String.format("%.3f", standardFlowAvg));
                omtrmAvg.setFlowSpeed(String.format("%.3f", flowSpeedAvg));
                omtrmAvg.setTemperature(String.format("%.3f", temperatureAvg));
                omtrmAvg.setHumidity(String.format("%.3f", humidityAvg));
                omrtModelList.add(omtrmAvg);
                //最大值
                OnlineMonitorReportTimeModel omtrmMax = new OnlineMonitorReportTimeModel();
                omtrmMax.setTime("最大值");
                double thingValueMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).max().getAsDouble();
                double thingCouMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).max().getAsDouble();
                double standardFlowMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).max().getAsDouble();
                double flowSpeedMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).max().getAsDouble();
                double temperatureMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).max().getAsDouble();
                double humidityMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).max().getAsDouble();
                omtrmMax.setThingValue(String.valueOf(thingValueMax));
                omtrmMax.setThingCou(String.format("%.8f", thingCouMax));
                omtrmMax.setStandardFlow(String.valueOf(standardFlowMax));
                omtrmMax.setFlowSpeed(String.valueOf(flowSpeedMax));
                omtrmMax.setTemperature(String.valueOf(temperatureMax));
                omtrmMax.setHumidity(String.valueOf(humidityMax));
                omrtModelList.add(omtrmMax);
                //最大值
                OnlineMonitorReportTimeModel omtrmMin = new OnlineMonitorReportTimeModel();
                omtrmMin.setTime("最小值");
                double thingValueMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).min().getAsDouble();
                double thingCouMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).min().getAsDouble();
                double standardFlowMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).min().getAsDouble();
                double flowSpeedMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).min().getAsDouble();
                double temperatureMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).min().getAsDouble();
                double humidityMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).min().getAsDouble();
                omtrmMin.setThingValue(String.valueOf(thingValueMin));
                omtrmMin.setThingCou(String.format("%.8f", thingCouMin));
                omtrmMin.setStandardFlow(String.valueOf(standardFlowMin));
                omtrmMin.setFlowSpeed(String.valueOf(flowSpeedMin));
                omtrmMin.setTemperature(String.valueOf(temperatureMin));
                omtrmMin.setHumidity(String.valueOf(humidityMin));
                omrtModelList.add(omtrmMin);
                //日排放总量
                OnlineMonitorReportTimeModel omtrmSum = new OnlineMonitorReportTimeModel();
                omtrmSum.setTime("日排放总量(t)");
                double thingCouSum = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).sum();
                omtrmSum.setThingCou(String.format("%.8f", thingCouSum / 1000));
                omrtModelList.add(omtrmSum);
            }
            onlineMonitorReportMainModel.setOmrtModelList(omrtModelList);
        }
        return onlineMonitorReportMainModel;
    }

    @Override
    public OnlineMonitorReportMainModel getOlrZsMonth(
            String deviceCode, String thingCode, String selectTime) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        Device device = new Device();
        device.setDeviceCode(deviceCode);
        List<Device> deviceList = deviceDao.getDevice(device);
        if (deviceList != null && deviceList.size() == 1) {
            onlineMonitorReportMainModel.setDeviceCode(deviceList.get(0).getDeviceCode());
            onlineMonitorReportMainModel.setDeviceMn(deviceList.get(0).getDeviceMn());
            onlineMonitorReportMainModel.setDeviceName(deviceList.get(0).getDeviceName());
            onlineMonitorReportMainModel.setThingName(monitorDao.getMonitorName(thingCode));
            onlineMonitorReportMainModel.setMonitorDate(selectTime);
            int days = DateUtil.getDaysOfMonth(selectTime + "-01");
            List<OnlineMonitorReportTimeModel> omrtModelList = new ArrayList<OnlineMonitorReportTimeModel>();
            for (int i = 1; i <= days; i++) {
                OnlineMonitorReportTimeModel onlineMonitorReportTimeModel = new OnlineMonitorReportTimeModel();
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                Timestamp timeSelect = DateUtil.StringToTimestampSecond(selectTime + "-" + String.format("%02d", i) + " 00:00:00");
                if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                    dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                String memo = "";
                String timeDis = String.format("%02d", i) + "日";
                String thingValue = envStatisticalReportDao.getOlrDay(dbName, deviceCode, thingCode, timeSelect);//监测项浓度(mg/m3)
                if (thingValue == null || thingValue.isEmpty()) {
                    thingValue = "0";
                    memo += "无数据;";
                }
                String flowSpeed = envStatisticalReportDao.getOlrDay(dbName, deviceCode, DefaultArgument.PRO_GAS_VELOCITY_CODE, timeSelect);//烟气流速(m/s)
                String standardFlow = envStatisticalReportDao.getOlrZsDayFlow(dbName, deviceCode, DefaultArgument.PRO_EXHOUST_GAS_CODE, timeSelect);//标态流量(m3/h)
                if (standardFlow == null || standardFlow.isEmpty()) {
                    standardFlow = "0";
                }
                String thingCou = String.format("%.8f", (Double.valueOf(thingValue) * Double.valueOf(standardFlow) / (1000 * 1000 * 1000)));//流量(t/d)
                String temperature = envStatisticalReportDao.getOlrDay(dbName, deviceCode, DefaultArgument.PRO_GAS_TEMPERATURE_CODE, timeSelect);//烟气温度(c)
                String humidity = envStatisticalReportDao.getOlrDay(dbName, deviceCode, DefaultArgument.PRO_GAS_HUMIDITY_CODE, timeSelect);//烟气湿度(%)
                onlineMonitorReportTimeModel.setTime(timeDis);
                onlineMonitorReportTimeModel.setThingValue(thingValue);
                onlineMonitorReportTimeModel.setThingCou(thingCou);
                onlineMonitorReportTimeModel.setStandardFlow(standardFlow);
                onlineMonitorReportTimeModel.setFlowSpeed(flowSpeed);
                onlineMonitorReportTimeModel.setTemperature(temperature);
                onlineMonitorReportTimeModel.setHumidity(humidity);
                onlineMonitorReportTimeModel.setMemo(memo);
                omrtModelList.add(onlineMonitorReportTimeModel);
            }
            if (omrtModelList != null && omrtModelList.size() > 0) {
                //平均值
                OnlineMonitorReportTimeModel omtrmAvg = new OnlineMonitorReportTimeModel();
                omtrmAvg.setTime("平均值");
                double thingValueAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).average().getAsDouble();
                double thingCouAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).average().getAsDouble();
                double standardFlowAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).average().getAsDouble();
                double flowSpeedAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).average().getAsDouble();
                double temperatureAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).average().getAsDouble();
                double humidityAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).average().getAsDouble();
                omtrmAvg.setThingValue(String.format("%.3f", thingValueAvg));
                omtrmAvg.setThingCou(String.format("%.8f", thingCouAvg));
                omtrmAvg.setStandardFlow(String.format("%.3f", standardFlowAvg));
                omtrmAvg.setFlowSpeed(String.format("%.3f", flowSpeedAvg));
                omtrmAvg.setTemperature(String.format("%.3f", temperatureAvg));
                omtrmAvg.setHumidity(String.format("%.3f", humidityAvg));
                omrtModelList.add(omtrmAvg);
                //最大值
                OnlineMonitorReportTimeModel omtrmMax = new OnlineMonitorReportTimeModel();
                omtrmMax.setTime("最大值");
                double thingValueMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).max().getAsDouble();
                double thingCouMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).max().getAsDouble();
                double standardFlowMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).max().getAsDouble();
                double flowSpeedMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).max().getAsDouble();
                double temperatureMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).max().getAsDouble();
                double humidityMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).max().getAsDouble();
                omtrmMax.setThingValue(String.valueOf(thingValueMax));
                omtrmMax.setThingCou(String.format("%.8f", thingCouMax));
                omtrmMax.setStandardFlow(String.valueOf(standardFlowMax));
                omtrmMax.setFlowSpeed(String.valueOf(flowSpeedMax));
                omtrmMax.setTemperature(String.valueOf(temperatureMax));
                omtrmMax.setHumidity(String.valueOf(humidityMax));
                omrtModelList.add(omtrmMax);
                //最大值
                OnlineMonitorReportTimeModel omtrmMin = new OnlineMonitorReportTimeModel();
                omtrmMin.setTime("最小值");
                double thingValueMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).min().getAsDouble();
                double thingCouMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).min().getAsDouble();
                double standardFlowMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).min().getAsDouble();
                double flowSpeedMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).min().getAsDouble();
                double temperatureMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).min().getAsDouble();
                double humidityMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).min().getAsDouble();
                omtrmMin.setThingValue(String.valueOf(thingValueMin));
                omtrmMin.setThingCou(String.format("%.8f", thingCouMin));
                omtrmMin.setStandardFlow(String.valueOf(standardFlowMin));
                omtrmMin.setFlowSpeed(String.valueOf(flowSpeedMin));
                omtrmMin.setTemperature(String.valueOf(temperatureMin));
                omtrmMin.setHumidity(String.valueOf(humidityMin));
                omrtModelList.add(omtrmMin);
                //日排放总量
                OnlineMonitorReportTimeModel omtrmSum = new OnlineMonitorReportTimeModel();
                omtrmSum.setTime("月排放总量(t)");
                double thingCouSum = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).sum();
                omtrmSum.setThingCou(String.format("%.8f", thingCouSum));
                omrtModelList.add(omtrmSum);
            }
            onlineMonitorReportMainModel.setOmrtModelList(omrtModelList);
        }
        return onlineMonitorReportMainModel;
    }

    @Override
    public OnlineMonitorReportMainModel getOlrZsYear(
            String deviceCode, String thingCode, String selectTime) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        Device device = new Device();
        device.setDeviceCode(deviceCode);
        List<Device> deviceList = deviceDao.getDevice(device);
        if (deviceList != null && deviceList.size() == 1) {
            onlineMonitorReportMainModel.setDeviceCode(deviceList.get(0).getDeviceCode());
            onlineMonitorReportMainModel.setDeviceMn(deviceList.get(0).getDeviceMn());
            onlineMonitorReportMainModel.setDeviceName(deviceList.get(0).getDeviceName());
            onlineMonitorReportMainModel.setThingName(monitorDao.getMonitorName(thingCode));
            onlineMonitorReportMainModel.setMonitorDate(selectTime);
            List<OnlineMonitorReportTimeModel> omrtModelList = new ArrayList<OnlineMonitorReportTimeModel>();
            for (int i = 1; i <= 12; i++) {
                OnlineMonitorReportTimeModel onlineMonitorReportTimeModel = new OnlineMonitorReportTimeModel();
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                Timestamp timeSelect = DateUtil.StringToTimestampSecond(selectTime + "-" + String.format("%02d", i) + "-01 00:00:00");
                if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                    dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                String memo = "";
                String timeDis = String.format("%02d", i) + "月";
                String thingValue = envStatisticalReportDao.getOlrMonth(dbName, deviceCode, thingCode, timeSelect);//监测项浓度(mg/m3)
                if (thingValue == null || thingValue.isEmpty()) {
                    thingValue = "0";
                    memo += "无数据;";
                }
                String flowSpeed = envStatisticalReportDao.getOlrMonth(dbName, deviceCode, DefaultArgument.PRO_GAS_VELOCITY_CODE, timeSelect);//烟气流速(m/s)
                String standardFlow = envStatisticalReportDao.getOlrZsMonthFlow(dbName, deviceCode, DefaultArgument.PRO_EXHOUST_GAS_CODE, timeSelect);//标态流量(m3/h)
                if (standardFlow == null || standardFlow.isEmpty()) {
                    standardFlow = "0";
                }
                String thingCou = String.format("%.8f", (Double.valueOf(thingValue) * Double.valueOf(standardFlow) / (1000 * 1000 * 1000)));//流量(t/m)
                String temperature = envStatisticalReportDao.getOlrMonth(dbName, deviceCode, DefaultArgument.PRO_GAS_TEMPERATURE_CODE, timeSelect);//烟气温度(c)
                String humidity = envStatisticalReportDao.getOlrMonth(dbName, deviceCode, DefaultArgument.PRO_GAS_HUMIDITY_CODE, timeSelect);//烟气湿度(%)
                onlineMonitorReportTimeModel.setTime(timeDis);
                onlineMonitorReportTimeModel.setThingValue(thingValue);
                onlineMonitorReportTimeModel.setThingCou(thingCou);
                onlineMonitorReportTimeModel.setStandardFlow(standardFlow);
                onlineMonitorReportTimeModel.setFlowSpeed(flowSpeed);
                onlineMonitorReportTimeModel.setTemperature(temperature);
                onlineMonitorReportTimeModel.setHumidity(humidity);
                onlineMonitorReportTimeModel.setMemo(memo);
                omrtModelList.add(onlineMonitorReportTimeModel);
            }
            if (omrtModelList != null && omrtModelList.size() > 0) {
                //平均值
                OnlineMonitorReportTimeModel omtrmAvg = new OnlineMonitorReportTimeModel();
                omtrmAvg.setTime("平均值");
                double thingValueAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).average().getAsDouble();
                double thingCouAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).average().getAsDouble();
                double standardFlowAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).average().getAsDouble();
                double flowSpeedAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).average().getAsDouble();
                double temperatureAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).average().getAsDouble();
                double humidityAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).average().getAsDouble();
                omtrmAvg.setThingValue(String.format("%.3f", thingValueAvg));
                omtrmAvg.setThingCou(String.format("%.8f", thingCouAvg));
                omtrmAvg.setStandardFlow(String.format("%.3f", standardFlowAvg));
                omtrmAvg.setFlowSpeed(String.format("%.3f", flowSpeedAvg));
                omtrmAvg.setTemperature(String.format("%.3f", temperatureAvg));
                omtrmAvg.setHumidity(String.format("%.3f", humidityAvg));
                omrtModelList.add(omtrmAvg);
                //最大值
                OnlineMonitorReportTimeModel omtrmMax = new OnlineMonitorReportTimeModel();
                omtrmMax.setTime("最大值");
                double thingValueMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).max().getAsDouble();
                double thingCouMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).max().getAsDouble();
                double standardFlowMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).max().getAsDouble();
                double flowSpeedMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).max().getAsDouble();
                double temperatureMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).max().getAsDouble();
                double humidityMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).max().getAsDouble();
                omtrmMax.setThingValue(String.valueOf(thingValueMax));
                omtrmMax.setThingCou(String.format("%.8f", thingCouMax));
                omtrmMax.setStandardFlow(String.valueOf(standardFlowMax));
                omtrmMax.setFlowSpeed(String.valueOf(flowSpeedMax));
                omtrmMax.setTemperature(String.valueOf(temperatureMax));
                omtrmMax.setHumidity(String.valueOf(humidityMax));
                omrtModelList.add(omtrmMax);
                //最大值
                OnlineMonitorReportTimeModel omtrmMin = new OnlineMonitorReportTimeModel();
                omtrmMin.setTime("最小值");
                double thingValueMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).min().getAsDouble();
                double thingCouMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).min().getAsDouble();
                double standardFlowMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).min().getAsDouble();
                double flowSpeedMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).min().getAsDouble();
                double temperatureMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).min().getAsDouble();
                double humidityMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).min().getAsDouble();
                omtrmMin.setThingValue(String.valueOf(thingValueMin));
                omtrmMin.setThingCou(String.format("%.8f", thingCouMin));
                omtrmMin.setStandardFlow(String.valueOf(standardFlowMin));
                omtrmMin.setFlowSpeed(String.valueOf(flowSpeedMin));
                omtrmMin.setTemperature(String.valueOf(temperatureMin));
                omtrmMin.setHumidity(String.valueOf(humidityMin));
                omrtModelList.add(omtrmMin);
                //日排放总量
                OnlineMonitorReportTimeModel omtrmSum = new OnlineMonitorReportTimeModel();
                omtrmSum.setTime("年排放总量(t)");
                double thingCouSum = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).sum();
                omtrmSum.setThingCou(String.format("%.8f", thingCouSum));
                omrtModelList.add(omtrmSum);
            }
            onlineMonitorReportMainModel.setOmrtModelList(omrtModelList);
        }
        return onlineMonitorReportMainModel;
    }

    @Override
    public OnlineMonitorReportMainModel getOlrZsTimes(String deviceCode,
                                                      String thingCode, String beginTime, String endTime) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        Device device = new Device();
        device.setDeviceCode(deviceCode);
        List<Device> deviceList = deviceDao.getDevice(device);
        if (deviceList != null && deviceList.size() == 1) {
            onlineMonitorReportMainModel.setDeviceCode(deviceList.get(0).getDeviceCode());
            onlineMonitorReportMainModel.setDeviceMn(deviceList.get(0).getDeviceMn());
            onlineMonitorReportMainModel.setDeviceName(deviceList.get(0).getDeviceName());
            onlineMonitorReportMainModel.setThingName(monitorDao.getMonitorName(thingCode));
            onlineMonitorReportMainModel.setMonitorDate(beginTime);
            List<OnlineMonitorReportTimeModel> omrtModelList = new ArrayList<OnlineMonitorReportTimeModel>();
            Timestamp beginTimeStamp = DateUtil.StringToTimestampSecond(beginTime + " 00:00:00");
            Timestamp endTimeStamp = DateUtil.StringToTimestampSecond(endTime + " 00:00:00");
            while (beginTimeStamp.compareTo(endTimeStamp) <= 0) {
                OnlineMonitorReportTimeModel onlineMonitorReportTimeModel = new OnlineMonitorReportTimeModel();
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                Timestamp timeSelect = beginTimeStamp;
                if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                    dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                String memo = "";
                String timeDis = DateUtil.TimestampToString(timeSelect, DateUtil.DEFUALT_TIME);
                String thingValue = envStatisticalReportDao.getOlrDay(dbName, deviceCode, thingCode, timeSelect);//监测项浓度(mg/m3)
                if (thingValue == null || thingValue.isEmpty()) {
                    thingValue = "0";
                    memo += "无数据;";
                }
                String flowSpeed = envStatisticalReportDao.getOlrDay(dbName, deviceCode, DefaultArgument.PRO_GAS_VELOCITY_CODE, timeSelect);//烟气流速(m/s)
                String standardFlow = envStatisticalReportDao.getOlrZsDayFlow(dbName, deviceCode, DefaultArgument.PRO_EXHOUST_GAS_CODE, timeSelect);//标态流量(m3/h)
                if (standardFlow == null || standardFlow.isEmpty()) {
                    standardFlow = "0";
                }
                String thingCou = String.format("%.8f", (Double.valueOf(thingValue) * Double.valueOf(standardFlow) / (1000 * 1000 * 1000)));//流量(t/d)
                String temperature = envStatisticalReportDao.getOlrDay(dbName, deviceCode, DefaultArgument.PRO_GAS_TEMPERATURE_CODE, timeSelect);//烟气温度(c)
                String humidity = envStatisticalReportDao.getOlrDay(dbName, deviceCode, DefaultArgument.PRO_GAS_HUMIDITY_CODE, timeSelect);//烟气湿度(%)
                onlineMonitorReportTimeModel.setTime(timeDis);
                onlineMonitorReportTimeModel.setThingValue(thingValue);
                onlineMonitorReportTimeModel.setThingCou(thingCou);
                onlineMonitorReportTimeModel.setStandardFlow(standardFlow);
                onlineMonitorReportTimeModel.setFlowSpeed(flowSpeed);
                onlineMonitorReportTimeModel.setTemperature(temperature);
                onlineMonitorReportTimeModel.setHumidity(humidity);
                onlineMonitorReportTimeModel.setMemo(memo);
                omrtModelList.add(onlineMonitorReportTimeModel);
                beginTimeStamp = DateUtil.getAddTime(beginTimeStamp, DefaultArgument.PRO_GET_DAY);
            }
            if (omrtModelList != null && omrtModelList.size() > 0) {
                //平均值
                OnlineMonitorReportTimeModel omtrmAvg = new OnlineMonitorReportTimeModel();
                omtrmAvg.setTime("平均值");
                double thingValueAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).average().getAsDouble();
                double thingCouAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).average().getAsDouble();
                double standardFlowAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).average().getAsDouble();
                double flowSpeedAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).average().getAsDouble();
                double temperatureAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).average().getAsDouble();
                double humidityAvg = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).average().getAsDouble();
                omtrmAvg.setThingValue(String.format("%.3f", thingValueAvg));
                omtrmAvg.setThingCou(String.format("%.8f", thingCouAvg));
                omtrmAvg.setStandardFlow(String.format("%.3f", standardFlowAvg));
                omtrmAvg.setFlowSpeed(String.format("%.3f", flowSpeedAvg));
                omtrmAvg.setTemperature(String.format("%.3f", temperatureAvg));
                omtrmAvg.setHumidity(String.format("%.3f", humidityAvg));
                omrtModelList.add(omtrmAvg);
                //最大值
                OnlineMonitorReportTimeModel omtrmMax = new OnlineMonitorReportTimeModel();
                omtrmMax.setTime("最大值");
                double thingValueMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).max().getAsDouble();
                double thingCouMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).max().getAsDouble();
                double standardFlowMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).max().getAsDouble();
                double flowSpeedMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).max().getAsDouble();
                double temperatureMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).max().getAsDouble();
                double humidityMax = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).max().getAsDouble();
                omtrmMax.setThingValue(String.valueOf(thingValueMax));
                omtrmMax.setThingCou(String.format("%.8f", thingCouMax));
                omtrmMax.setStandardFlow(String.valueOf(standardFlowMax));
                omtrmMax.setFlowSpeed(String.valueOf(flowSpeedMax));
                omtrmMax.setTemperature(String.valueOf(temperatureMax));
                omtrmMax.setHumidity(String.valueOf(humidityMax));
                omrtModelList.add(omtrmMax);
                //最大值
                OnlineMonitorReportTimeModel omtrmMin = new OnlineMonitorReportTimeModel();
                omtrmMin.setTime("最小值");
                double thingValueMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingValueDouble).min().getAsDouble();
                double thingCouMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).min().getAsDouble();
                double standardFlowMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertStandardFlowDouble).min().getAsDouble();
                double flowSpeedMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertFlowSpeedDouble).min().getAsDouble();
                double temperatureMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertTemperatureDouble).min().getAsDouble();
                double humidityMin = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertHumidityDouble).min().getAsDouble();
                omtrmMin.setThingValue(String.valueOf(thingValueMin));
                omtrmMin.setThingCou(String.format("%.8f", thingCouMin));
                omtrmMin.setStandardFlow(String.valueOf(standardFlowMin));
                omtrmMin.setFlowSpeed(String.valueOf(flowSpeedMin));
                omtrmMin.setTemperature(String.valueOf(temperatureMin));
                omtrmMin.setHumidity(String.valueOf(humidityMin));
                omrtModelList.add(omtrmMin);
                //阶段排放总量
                OnlineMonitorReportTimeModel omtrmSum = new OnlineMonitorReportTimeModel();
                omtrmSum.setTime("阶段排放总量(t)");
                double thingCouSum = omrtModelList.stream().mapToDouble(OnlineMonitorReportTimeModel::convertThingCouDouble).sum();
                omtrmSum.setThingCou(String.format("%.8f", thingCouSum));
                omrtModelList.add(omtrmSum);
            }
            onlineMonitorReportMainModel.setOmrtModelList(omrtModelList);
        }
        return onlineMonitorReportMainModel;
    }

    @Override
    public List<AlarmRateModel> getAlarmDayRate(List<String> deviceCodeList,
                                                String thingCode, String updateType, String levelNo, String convertType, String selectTime) {
        List<AlarmRateModel> alarmRateModelList = new ArrayList<AlarmRateModel>();
        if (deviceCodeList != null && deviceCodeList.size() > 0) {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            Timestamp timeSelect = DateUtil.StringToTimestampSecond(selectTime + " 00:00:00");
            if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int totalAlarmCount = 0;
            int totalAllCount = 0;
            for (String deviceCode : deviceCodeList) {
                AlarmRateModel alarmRateModel = new AlarmRateModel();
                String areaName = deviceDao.getAreaName(deviceCode);
                String deviceName = deviceDao.getDeviceName(deviceCode);
                int alarmCount = envStatisticalReportDao.getOverAlarmDayCount(dbName, deviceCode, thingCode, updateType, levelNo, convertType, timeSelect);
                int allCount = envStatisticalReportDao.getAllDayCount(dbName, deviceCode, thingCode, updateType, timeSelect);
                if (dbOldName != null && !dbOldName.isEmpty()) {
                    alarmCount += envStatisticalReportDao.getOverAlarmMonthCount(dbOldName, deviceCode, thingCode, updateType, levelNo, convertType, timeSelect);
                    allCount += envStatisticalReportDao.getAllMonthCount(dbOldName, deviceCode, thingCode, updateType, timeSelect);
                }
                alarmRateModel.setAreaName(areaName);
                alarmRateModel.setDeviceCode(deviceCode);
                alarmRateModel.setDeviceName(deviceName);
                alarmRateModel.setAlarmCount(String.valueOf(alarmCount));
                alarmRateModel.setAllCount(String.valueOf(allCount));
                if (allCount > 0) {
                    alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(alarmCount) / allCount * 100));
                } else {
                    alarmRateModel.setAlarmRate("0.00");
                }
                totalAlarmCount += alarmCount;
                totalAllCount += allCount;
                alarmRateModelList.add(alarmRateModel);
            }
            //追加合计
            AlarmRateModel alarmRateModel = new AlarmRateModel();
            alarmRateModel.setDeviceCode("");
            alarmRateModel.setDeviceName("合计");
            alarmRateModel.setAlarmCount(String.valueOf(totalAlarmCount));
            alarmRateModel.setAllCount(String.valueOf(totalAllCount));
            if (totalAllCount > 0) {
                alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(totalAlarmCount) / totalAllCount * 100));
            } else {
                alarmRateModel.setAlarmRate("0.00");
            }
            alarmRateModelList.add(alarmRateModel);
        }
        return alarmRateModelList;
    }

    @Override
    public List<AlarmRateModel> getAlarmMonthRate(List<String> deviceCodeList,
                                                  String thingCode, String updateType, String levelNo, String convertType, String selectTime) {
        List<AlarmRateModel> alarmRateModelList = new ArrayList<AlarmRateModel>();
        if (deviceCodeList != null && deviceCodeList.size() > 0) {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            Timestamp timeSelect = DateUtil.StringToTimestampSecond(selectTime + "-01 00:00:00");
            if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int totalAlarmCount = 0;
            int totalAllCount = 0;
            for (String deviceCode : deviceCodeList) {
                AlarmRateModel alarmRateModel = new AlarmRateModel();
                String areaName = deviceDao.getAreaName(deviceCode);
                String deviceName = deviceDao.getDeviceName(deviceCode);
                int alarmCount = envStatisticalReportDao.getOverAlarmMonthCount(dbName, deviceCode, thingCode, updateType, levelNo, convertType, timeSelect);
                int allCount = envStatisticalReportDao.getAllMonthCount(dbName, deviceCode, thingCode, updateType, timeSelect);
                if (dbOldName != null && !dbOldName.isEmpty()) {
                    alarmCount += envStatisticalReportDao.getOverAlarmMonthCount(dbOldName, deviceCode, thingCode, updateType, levelNo, convertType, timeSelect);
                    allCount += envStatisticalReportDao.getAllMonthCount(dbOldName, deviceCode, thingCode, updateType, timeSelect);
                }
                alarmRateModel.setAreaName(areaName);
                alarmRateModel.setDeviceCode(deviceCode);
                alarmRateModel.setDeviceName(deviceName);
                alarmRateModel.setAlarmCount(String.valueOf(alarmCount));
                alarmRateModel.setAllCount(String.valueOf(allCount));
                if (allCount > 0) {
                    alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(alarmCount) / allCount * 100));
                } else {
                    alarmRateModel.setAlarmRate("0.00");
                }
                totalAlarmCount += alarmCount;
                totalAllCount += allCount;
                alarmRateModelList.add(alarmRateModel);
            }
            //追加合计
            AlarmRateModel alarmRateModel = new AlarmRateModel();
            alarmRateModel.setDeviceCode("");
            alarmRateModel.setDeviceName("合计");
            alarmRateModel.setAlarmCount(String.valueOf(totalAlarmCount));
            alarmRateModel.setAllCount(String.valueOf(totalAllCount));
            if (totalAllCount > 0) {
                alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(totalAlarmCount) / totalAllCount * 100));
            } else {
                alarmRateModel.setAlarmRate("0.00");
            }
            alarmRateModelList.add(alarmRateModel);
        }
        return alarmRateModelList;
    }

    @Override
    public List<AlarmRateModel> getAlarmYearRate(List<String> deviceCodeList,
                                                 String thingCode, String updateType, String levelNo, String convertType, String selectTime) {
        List<AlarmRateModel> alarmRateModelList = new ArrayList<AlarmRateModel>();
        if (deviceCodeList != null && deviceCodeList.size() > 0) {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            Timestamp timeSelect = DateUtil.StringToTimestampSecond(selectTime + "-01-01 00:00:00");
            if (!DateUtil.isRecentlyData(timeSelect, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            int totalAlarmCount = 0;
            int totalAllCount = 0;
            for (String deviceCode : deviceCodeList) {
                AlarmRateModel alarmRateModel = new AlarmRateModel();
                String areaName = deviceDao.getAreaName(deviceCode);
                String deviceName = deviceDao.getDeviceName(deviceCode);
                int alarmCount = envStatisticalReportDao.getOverAlarmYearCount(dbName, deviceCode, thingCode, updateType, levelNo, convertType, timeSelect);
                int allCount = envStatisticalReportDao.getAllYearCount(dbName, deviceCode, thingCode, updateType, timeSelect);
                if (dbOldName != null && !dbOldName.isEmpty()) {
                    alarmCount += envStatisticalReportDao.getOverAlarmMonthCount(dbOldName, deviceCode, thingCode, updateType, levelNo, convertType, timeSelect);
                    allCount += envStatisticalReportDao.getAllMonthCount(dbOldName, deviceCode, thingCode, updateType, timeSelect);
                }
                alarmRateModel.setAreaName(areaName);
                alarmRateModel.setDeviceCode(deviceCode);
                alarmRateModel.setDeviceName(deviceName);
                alarmRateModel.setAlarmCount(String.valueOf(alarmCount));
                alarmRateModel.setAllCount(String.valueOf(allCount));
                if (allCount > 0) {
                    alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(alarmCount) / allCount * 100));
                } else {
                    alarmRateModel.setAlarmRate("0.00");
                }
                totalAlarmCount += alarmCount;
                totalAllCount += allCount;
                alarmRateModelList.add(alarmRateModel);
            }
            //追加合计
            AlarmRateModel alarmRateModel = new AlarmRateModel();
            alarmRateModel.setDeviceCode("");
            alarmRateModel.setDeviceName("合计");
            alarmRateModel.setAlarmCount(String.valueOf(totalAlarmCount));
            alarmRateModel.setAllCount(String.valueOf(totalAllCount));
            if (totalAllCount > 0) {
                alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(totalAlarmCount) / totalAllCount * 100));
            } else {
                alarmRateModel.setAlarmRate("0.00");
            }
            alarmRateModelList.add(alarmRateModel);
        }
        return alarmRateModelList;
    }

    @Override
    public List<AlarmRateModel> getAlarmTimesRate(List<String> deviceCodeList,
                                                  String thingCode, String updateType, String levelNo, String convertType, String beginTime, String endTime) {
        List<AlarmRateModel> alarmRateModelList = new ArrayList<AlarmRateModel>();
        if (deviceCodeList != null && deviceCodeList.size() > 0) {
            int totalAlarmCount = 0;
            int totalAllCount = 0;
            for (String deviceCode : deviceCodeList) {
                AlarmRateModel alarmRateModel = new AlarmRateModel();
                String areaName = deviceDao.getAreaName(deviceCode);
                String deviceName = deviceDao.getDeviceName(deviceCode);
                Timestamp beginTimeStamp = DateUtil.StringToTimestampSecond(beginTime + " 00:00:00");
                Timestamp endTimeStamp = DateUtil.StringToTimestampSecond(endTime + " 00:00:00");
                alarmRateModel.setAreaName(areaName);
                alarmRateModel.setDeviceCode(deviceCode);
                alarmRateModel.setDeviceName(deviceName);
                int alarmCount = 0;
                int allCount = 0;
                while (beginTimeStamp.compareTo(endTimeStamp) <= 0) {
                    String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                    if (!DateUtil.isRecentlyData(beginTimeStamp, DefaultArgument.RECENT_DAYS)) {
                        dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
                    }
                    alarmCount += envStatisticalReportDao.getOverAlarmDayCount(dbName, deviceCode, thingCode, updateType, levelNo, convertType, beginTimeStamp);
                    allCount += envStatisticalReportDao.getAllDayCount(dbName, deviceCode, thingCode, updateType, beginTimeStamp);
                    beginTimeStamp = DateUtil.getAddTime(beginTimeStamp, DefaultArgument.PRO_GET_DAY);
                }
                if (allCount > 0) {
                    alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(alarmCount) / allCount * 100));
                } else {
                    alarmRateModel.setAlarmRate("0.00");
                }
                alarmRateModel.setAlarmCount(String.valueOf(alarmCount));
                alarmRateModel.setAllCount(String.valueOf(allCount));
                totalAlarmCount += alarmCount;
                totalAllCount += allCount;
                alarmRateModelList.add(alarmRateModel);
            }
            //追加合计
            AlarmRateModel alarmRateModel = new AlarmRateModel();
            alarmRateModel.setDeviceCode("");
            alarmRateModel.setDeviceName("合计");
            alarmRateModel.setAlarmCount(String.valueOf(totalAlarmCount));
            alarmRateModel.setAllCount(String.valueOf(totalAllCount));
            if (totalAllCount > 0) {
                alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(totalAlarmCount) / totalAllCount * 100));
            } else {
                alarmRateModel.setAlarmRate("0.00");
            }
            alarmRateModelList.add(alarmRateModel);
        }
        return alarmRateModelList;
    }

    @Override
    public List<AlarmRateModel> getAlarmTimesRate(List<String> deviceCodeList, String thingCode, String updateType,
                                                  String levelNo, String convertType, Timestamp beginTime, Timestamp endTime) {
        List<AlarmRateModel> alarmRateModelList = new ArrayList<AlarmRateModel>();
        if (deviceCodeList != null && deviceCodeList.size() > 0) {
            String dbName = dom4jConfig.getDataBaseConfig().getDbName();
            String dbOldName = "";
            if (!DateUtil.isRecentlyData(beginTime, DefaultArgument.RECENT_DAYS)) {
                dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            }
            for (String deviceCode : deviceCodeList) {
                AlarmRateModel alarmRateModel = new AlarmRateModel();
                String areaName = deviceDao.getAreaName(deviceCode);
                String deviceName = deviceDao.getDeviceName(deviceCode);
                int alarmCount = envStatisticalReportDao.getOverAlarmTimesCount(dbName, deviceCode, thingCode, updateType, levelNo, convertType, beginTime, endTime);
                if (!"all".equals(convertType)) {
                    alarmCount += envStatisticalReportDao.getOverAlarmTimesCount(dbName, deviceCode, thingCode, updateType, levelNo, "zs", beginTime, endTime);
                }
                int allCount = envStatisticalReportDao.getAllTimesCount(dbName, deviceCode, thingCode, updateType, beginTime, endTime);
                if (dbOldName != null && !dbOldName.isEmpty()) {
                    alarmCount += envStatisticalReportDao.getOverAlarmTimesCount(dbOldName, deviceCode, thingCode, updateType, levelNo, convertType, beginTime, endTime);
                    if (!"all".equals(convertType)) {
                        alarmCount += envStatisticalReportDao.getOverAlarmTimesCount(dbOldName, deviceCode, thingCode, updateType, levelNo, "zs", beginTime, endTime);
                    }
                    allCount += envStatisticalReportDao.getAllTimesCount(dbOldName, deviceCode, thingCode, updateType, beginTime, endTime);
                }
                alarmRateModel.setAreaName(areaName);
                alarmRateModel.setDeviceCode(deviceCode);
                alarmRateModel.setDeviceName(deviceName);
                alarmRateModel.setAlarmCount(String.valueOf(alarmCount));
                alarmRateModel.setAllCount(String.valueOf(allCount));
                if (allCount > 0) {
                    alarmRateModel.setAlarmRate(String.format("%.2f", Double.valueOf(alarmCount) / allCount * 100));
                } else {
                    alarmRateModel.setAlarmRate("0.00");
                }
                alarmRateModelList.add(alarmRateModel);
            }
        }
        return alarmRateModelList;
    }


}
