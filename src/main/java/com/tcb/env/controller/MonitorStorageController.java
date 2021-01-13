package com.tcb.env.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.management.VMOption;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.tcb.env.model.MapModel;
import com.tcb.env.model.MonitorStorageModel;
import com.tcb.env.model.NetMonitorDataModel;
import com.tcb.env.model.NetMonitorTimeModel;
import com.tcb.env.model.OriginalDataModel;
import com.tcb.env.model.ResultAjaxPushModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.HeWeather;
import com.tcb.env.pojo.NetStatusCount;
import com.tcb.env.service.IAreaService;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.IMonitorStorageService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IWeatherService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.EnumUtil;
import com.tcb.env.util.EnumUtil.TimeFreque;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：数据查询控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月5日下午5:50:47
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/MonitorStorageController")
public class MonitorStorageController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "MonitorStorageController";

    /**
     * 实时查询标识
     */
    private static boolean haveTimelyResult = false;

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(MonitorStorageController.class);
    /**
     * 声明监测物查询服务类
     */
    @Resource
    private IMonitorStorageService monitorStorageService;
    /**
     * 声明树服务接口
     */
    @Resource
    private ITreeService treeService;
    /**
     * 声明键值查询服务类
     */
    @Resource
    private IMapService mapService;
    /**
     * 声明天气服务类
     */
    @Resource
    private IWeatherService weatherService;
    /**
     * 声明区域服务类
     */
    @Resource
    private IAreaService areaService;
    /**
     * 声明设备服务类
     */
    @Resource
    private IDeviceService deviceService;

    /**
     * 声明线程管理（带join不适用）
     */
    // @Resource(name = "taskExecutor")
    // private TaskExecutor taskExecutor;

    /**
     * <p>
     * [功能描述]：获取单站点多参数历史数据(列表)
     * </p>
     *
     * @param zsFlag     ：折算标志
     * @param devicecode ：站点
     * @param list       ：参数列表
     * @param starttime  ：查询开始时间
     * @param endtime    ：查询结束时间
     * @return
     * @author 王垒, 2016年3月30日下午1:40:33
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getPerStatisticsData", method = {RequestMethod.POST})
    public @ResponseBody
    String getSingleDeviceStatisticsData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, @RequestParam(value = "list[]") List<String> list,
            String starttime, String endtime, String freque) {
        TreeMap<String, HashMap<String, Double>> treeMap = new TreeMap<String, HashMap<String, Double>>(
                new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o2.toString().compareTo(o1.toString());
                    }
                }
        );
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return "[]";
        }
        try {
            List<String> listDevicecode = new ArrayList<String>();
            listDevicecode.add(devicecode);
            List<String> listThingcode = list;
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            if (mapMonitor != null && mapMonitor.size() > 0) {
                for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                        .entrySet()) {
                    if (tempListMonitor.getKey() > 0) {
                        if (treeMap != null) {
                            for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                                String frequetime = tempMonitor.getFrequeTime();
                                String thingname = tempMonitor.getThingName();
                                Double monitorvalue = tempMonitor.getMonitorValue();
                                if (treeMap.containsKey(frequetime)) {
                                    if (treeMap.get(frequetime).containsKey(thingname)) {
                                        continue;
                                    } else {
                                        treeMap.get(frequetime).put(thingname, monitorvalue);
                                    }
                                } else {
                                    HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                    hashMaplist.put(thingname, monitorvalue);
                                    treeMap.put(frequetime, hashMaplist);
                                }
                                //折算值
                                if (zsFlag) {
                                    String thingnamezs = thingname + "-zs";
                                    Double zsmonitorvalue = tempMonitor.getZsMonitorValue();
                                    if (treeMap.containsKey(frequetime)) {
                                        if (treeMap.get(frequetime).containsKey(thingnamezs)) {
                                            continue;
                                        } else {
                                            treeMap.get(frequetime).put(thingnamezs, zsmonitorvalue);
                                        }
                                    } else {
                                        HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                        hashMaplist.put(thingnamezs, zsmonitorvalue);
                                        treeMap.put(frequetime, hashMaplist);
                                    }
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return ConvertToJson(treeMap);
    }

    /**
     * <p>
     * [功能描述]：获取单站点多参数历史数据(图形)
     * </p>
     *
     * @param zsFlag     : 折算标识
     * @param devicecode ：站点
     * @param list       ：参数列表
     * @param starttime  ：查询开始时间
     * @param endtime    ：查询结束时间
     * @return
     * @author 王垒, 2016年3月30日下午1:40:33
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getSingleDeviceStatisticsChartData", method = {RequestMethod.POST})
    public @ResponseBody
    TreeMap<String, List<String>> getSingleDeviceStatisticsChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, @RequestParam(value = "list[]") List<String> list,
            String starttime, String endtime, String freque) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        try {
            List<String> listDevicecode = new ArrayList<String>();
            listDevicecode.add(devicecode);
            List<String> listThingcode = list;
            List<String> listName = monitorStorageService.getMonNamebyCode(listThingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            // 排序处理
            TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
            for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                    .entrySet()) {
                if (tempListMonitor.getKey() > 0) {
                    for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                }
            }
            for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor.entrySet()) {
                String frequetime = tempTreeMonitor.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测物
                for (String temp : listName) {
                    String thingname;
                    String monitorvalue = null;
                    String zsmonitorvalue = null;
                    for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                        thingname = tempMonitor.getThingName();
                        if (temp.equals(thingname)) {
                            monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                            zsmonitorvalue = String.valueOf(tempMonitor.getZsMonitorValue());
                            break;
                        }
                    }
                    if (treeMap.containsKey(temp)) {
                        treeMap.get(temp).add(monitorvalue);
                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(temp, listThing);
                    }
                    //折算值
                    if (zsFlag) {
                        String tempzs = temp + "-折算";
                        if (treeMap.containsKey(tempzs)) {
                            treeMap.get(tempzs).add(zsmonitorvalue);
                        } else {
                            List<String> listThing = new ArrayList<String>();
                            listThing.add(zsmonitorvalue);
                            treeMap.put(tempzs, listThing);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * <p>
     * [功能描述]：获取多站点单参数历史数据(列表)
     * </p>
     *
     * @param zsFlag    : 折算标识
     * @param list      ：站点列表
     * @param thingcode ：参数
     * @param starttime ：查询开始时间
     * @param endtime   ：查询结束时间
     * @return
     * @author 王垒, 2016年3月30日下午1:41:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getSingleThingStatisticsData", method = {RequestMethod.POST})
    public @ResponseBody
    String getSingleThingStatisticsData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]") List<String> list,
            String thingcode, String starttime, String endtime, String freque) {
        TreeMap<String, HashMap<String, Double>> treeMap = new TreeMap<String, HashMap<String, Double>>(
                new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o2.toString().compareTo(o1.toString());
                    }
                }
        );
        if (thingcode.equals(DefaultArgument.INT_DEFAULT)) {
            return "[]";
        }
        try {
            List<String> listDevicecode = list;
            List<String> listThingcode = new ArrayList<String>();
            listThingcode.add(thingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            if (mapMonitor != null && mapMonitor.size() > 0) {
                for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                        .entrySet()) {
                    if (tempListMonitor.getKey() > 0) {
                        if (treeMap != null) {
                            for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                                String frequetime = tempMonitor.getFrequeTime();
//								String devicename = tempMonitor.getDeviceName();
                                String deviceCode = tempMonitor.getDeviceCode();
                                Double monitorvalue = tempMonitor.getMonitorValue();
                                if (treeMap.containsKey(frequetime)) {
                                    if (treeMap.get(frequetime).containsKey(deviceCode)) {
                                        continue;
                                    } else {
                                        treeMap.get(frequetime).put(deviceCode, monitorvalue);
                                    }
                                } else {
                                    HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                    hashMaplist.put(deviceCode, monitorvalue);
                                    treeMap.put(frequetime, hashMaplist);
                                }

                                //折算值
                                if (zsFlag) {
                                    String deviceCodeZs = deviceCode + "-zs";
                                    Double zsmonitorvalue = tempMonitor.getZsMonitorValue();
                                    if (treeMap.containsKey(frequetime)) {
                                        if (treeMap.get(frequetime).containsKey(deviceCodeZs)) {
                                            continue;
                                        } else {
                                            treeMap.get(frequetime).put(deviceCodeZs, zsmonitorvalue);
                                        }
                                    } else {
                                        HashMap<String, Double> hashMaplist = new HashMap<String, Double>();
                                        hashMaplist.put(deviceCodeZs, zsmonitorvalue);
                                        treeMap.put(frequetime, hashMaplist);
                                    }
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return ConvertToJson(treeMap);
    }

    /**
     * <p>
     * [功能描述]：获取多站点单参数历史数据(图形)
     * </p>
     *
     * @param zsFlag    : 折算标识
     * @param list      ：站点列表
     * @param thingcode ：参数
     * @param starttime ：查询开始时间
     * @param endtime   ：查询结束时间
     * @return
     * @author 王垒, 2016年3月30日下午1:41:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getSingleThingStatisticsChartData", method = {RequestMethod.POST})
    public @ResponseBody
    TreeMap<String, List<String>> getSingleThingStatisticsChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]") List<String> list,
            String thingcode, String starttime, String endtime, String freque) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        if (thingcode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        try {
            List<String> listDevicecode = list;
//			List<String> listName = monitorStorageService.getDeviceNamebyCode(listDevicecode);
            List<String> listThingcode = new ArrayList<String>();
            listThingcode.add(thingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, starttime, endtime, freque);
            // 排序处理
            TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
            for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                    .entrySet()) {
                if (tempListMonitor.getKey() > 0) {
                    for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                }
            }
            for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor
                    .entrySet()) {
                String frequetime = tempTreeMonitor.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测设备
                for (String temp : listDevicecode) {
                    String devicename = null;
                    String devicenamezs = null;
                    String deviceCode;
                    String monitorvalue = null;
                    String zsmonitorvalue = null;
                    for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                        deviceCode = tempMonitor.getDeviceCode();
                        devicename = deviceService.getDeviceName(temp);
                        String areaName = deviceService.getAreaName(temp);
                        if (areaName != null && !areaName.isEmpty()) {
                            devicename = devicename + "-" + areaName;
                            devicenamezs = devicename + "-" + areaName + "-折算";
                        }
                        if (temp.equals(deviceCode)) {
                            monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                            zsmonitorvalue = String.valueOf(tempMonitor.getZsMonitorValue());
                            break;
                        }
                    }
                    if (treeMap.containsKey(devicename)) {
                        treeMap.get(devicename).add(monitorvalue);

                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(devicename, listThing);
                    }
                    //折算值
                    if (zsFlag) {
                        if (treeMap.containsKey(devicenamezs)) {
                            treeMap.get(devicenamezs).add(zsmonitorvalue);

                        } else {
                            List<String> listThing = new ArrayList<String>();
                            listThing.add(zsmonitorvalue);
                            treeMap.put(devicenamezs, listThing);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询历史数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * <p>
     * [功能描述]：判断查询的频率周期
     * </p>
     *
     * @param listdevicecode
     * @param listthingcode
     * @param starttime
     * @param endtime
     * @param freque
     * @return
     * @author 王垒, 2016年3月31日上午11:31:34
     * @since EnvDust 1.0.0
     */
    private Map<Integer, List<MonitorStorageModel>> getStatisticsDataByFreque(
            List<String> listdevicecode, List<String> listthingcode,
            String starttime, String endtime, String freque) {
        Map<Integer, List<MonitorStorageModel>> mapMonitor = new HashMap<Integer, List<MonitorStorageModel>>();
        TimeFreque timeFreque = TimeFreque.valueOf(freque.toUpperCase(Locale.ENGLISH));
        switch (timeFreque) {
            case PERMINUTE: {
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_MINUTE) {
                    mapMonitor = monitorStorageService.getPerMinuteStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerMinuteMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            case PERHOUR: {
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_HOUR) {
                    mapMonitor = monitorStorageService.getPerHourStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerHourMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }

            }
            break;
            case PERDAY: {
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_DAY) {
                    mapMonitor = monitorStorageService.getPerDayStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerDayMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            case PERMONTH: {
                starttime = starttime + "-01";
                endtime = endtime + "-01";
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_HOUR) {
                    mapMonitor = monitorStorageService.getPerMonthStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerMonthMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            case PERQUARTER: {
                {
                    switch (starttime.substring(starttime.length() - 2,
                            starttime.length())) {
                        case "01": {
                            starttime = starttime.substring(0, 4) + "-01" + "-01";
                        }
                        break;
                        case "02": {
                            starttime = starttime.substring(0, 4) + "-04" + "-01";
                        }
                        break;
                        case "03": {
                            starttime = starttime.substring(0, 4) + "-07" + "-01";
                        }
                        break;
                        case "04": {
                            starttime = starttime.substring(0, 4) + "-10" + "-01";
                        }
                        break;
                        default:
                            break;
                    }
                    switch (endtime.substring(endtime.length() - 2,
                            endtime.length())) {
                        case "01": {
                            endtime = endtime.substring(0, 4) + "-04" + "-01";
                        }
                        break;
                        case "02": {
                            endtime = endtime.substring(0, 4) + "-07" + "-01";
                        }
                        break;
                        case "03": {
                            endtime = endtime.substring(0, 4) + "-10" + "-01";
                        }
                        break;
                        case "04": {
                            endtime = (Integer.valueOf(endtime.substring(0, 4)) + 1)
                                    + "-01" + "-01";
                        }
                        break;
                        default:
                            break;
                    }
                }
                Timestamp startTime = DateUtil.StringToTimestamp(starttime);
                Timestamp endTime = DateUtil.StringToTimestamp(endtime);
                if (DefaultArgument.SYS_STA_HOUR) {
                    mapMonitor = monitorStorageService.getPerQuarterStaMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                } else {
                    mapMonitor = monitorStorageService.getPerQuarterMonitorData(
                            listdevicecode, listthingcode, startTime, endTime);
                }
            }
            break;
            default:
                break;
        }
        return mapMonitor;
    }

    /**
     * 查询实时监测物数据(图形)
     *
     * @param zsFlag
     * @param dataType
     * @param devicecode
     * @param isrepeat
     * @param httpsession
     * @return
     */
    @RequestMapping(value = "/getTimelyMonitorChartData", method = {RequestMethod.POST})
    @ResponseBody
    public LinkedHashMap<String, List<String>> getTimelyMonitorChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "dataType", defaultValue = "2011") String dataType,
            String devicecode, boolean isrepeat, HttpSession httpsession) {
        LinkedHashMap<String, List<String>> treeMap = new LinkedHashMap<String, List<String>>();
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        Timestamp nowtime = DateUtil.GetSystemDateTime(0);
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return treeMap;
            }
            if (!StringUtils.isEmpty(devicecode)) {
                List<String> listDevicecode = new ArrayList<String>();
                listDevicecode.add(devicecode);
                List<MapModel> listMap = getAthorityMonitors(listDevicecode, httpsession);
                if (listMap != null && listMap.size() > 0) {
                    List<String> listThingcode = new ArrayList<String>();
                    for (MapModel mapModel : listMap) {
                        listThingcode.add(mapModel.getCode());
                    }
                    List<String> listName = monitorStorageService
                            .getMonNamebyCode(listThingcode);
                    List<MonitorStorageModel> mapMonitor = new ArrayList<MonitorStorageModel>();
                    //				if (DefaultArgument.SYS_STA_RLD) {
                    //					mapMonitor = monitorStorageService
                    //							.getTimelyMonitorData(
                    //									listDevicecode,
                    //									listThingcode,
                    //									nowtime,
                    //									DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_DEFAULT));
                    //				} else {
                    //					mapMonitor = monitorStorageService
                    //							.getHourMonitorData(
                    //									listDevicecode,
                    //									listThingcode,
                    //									nowtime,
                    //									DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_DEFAULT));
                    //				}
                    Timestamp selectTime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR);
                    if (DefaultArgument.PRO_CN_GETMINUTE.equals(dataType)) {
                        selectTime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR * 24);
                    } else if (DefaultArgument.PRO_GET_HOUR.equals(dataType)) {
                        selectTime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR * 72);
                    } else if (DefaultArgument.PRO_GET_DAY.equals(dataType)) {
                        long millionSecond = (long)DefaultArgument.TIMELY_ONE_HOUR * 24 * DefaultArgument.RECENT_DAYS;
                        selectTime = DateUtil.GetSystemDateTime(millionSecond);
                    }
                    mapMonitor = monitorStorageService.getTimelyMonitorData(
                            listDevicecode,
                            listThingcode,
                            nowtime,
                            selectTime,
                            dataType);
                    // 排序处理
                    TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
                    for (MonitorStorageModel tempMonitor : mapMonitor) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(
                                    tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                    for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor.entrySet()) {
                        String frequetime = tempTreeMonitor.getKey();
                        // 时间
                        if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                            treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                        } else {
                            List<String> listTime = new ArrayList<String>();
                            listTime.add(frequetime);
                            treeMap.put(DefaultArgument.STA_TIME, listTime);
                        }
                        // 监测物
                        for (String temp : listName) {
                            String thingname;
                            String monitorvalue = null;
                            String zsmonitorvalue = null;
                            for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                                thingname = tempMonitor.getThingName();
                                if (temp.equals(thingname)) {
                                    monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                                    zsmonitorvalue = String.valueOf(tempMonitor.getZsMonitorValue());
                                    break;
                                }
                            }
                            if (treeMap.containsKey(temp)) {
                                treeMap.get(temp).add(monitorvalue);

                            } else {
                                List<String> listThing = new ArrayList<String>();
                                listThing.add(monitorvalue);
                                treeMap.put(temp, listThing);
                            }
                            //折算值
                            String tempzs = temp + "-折算";
                            if (zsFlag) {
                                if (treeMap.containsKey(tempzs)) {
                                    treeMap.get(tempzs).add(zsmonitorvalue);

                                } else {
                                    List<String> listThing = new ArrayList<String>();
                                    listThing.add(zsmonitorvalue);
                                    treeMap.put(tempzs, listThing);
                                }
                            }

                        }
                    }
                }
            } else {
                return treeMap;
            }

        } catch (Exception e) {
            logger.error(LOG + ":查询实时数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * <p>
     * [功能描述]：查询实时监测物数据(列表)
     * </p>
     *
     * @param zsFlag      : 折算值
     * @param dataType    : 数据类型
     * @param devicecode  ：设备编号
     * @param isrepeat    ：true:查询最新数据，false:重新查询所有数据
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午12:28:04
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getTimelyMonitorData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultAjaxPushModel getTimelyMonitorData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "dataType", defaultValue = "2011") String dataType,
            String devicecode, boolean isrepeat, HttpSession httpsession) {
        ResultAjaxPushModel resultTimelyModel = new ResultAjaxPushModel();
        TreeMap<String, LinkedHashMap<String, Double>> treeMap = new TreeMap<String, LinkedHashMap<String, Double>>(
                new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o2.toString().compareTo(o1.toString());
                    }
                }
        );
        resultTimelyModel.setSelect(devicecode);
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return resultTimelyModel;
        }
        UserModel loginuser = new UserModel();
        Timestamp nowtime = DateUtil.GetSystemDateTime(0);
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultTimelyModel;
            }
            loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            nowtime = DateUtil.GetSystemDateTime(0);
            if (loginuser != null) {
                if (!StringUtils.isEmpty(devicecode)) {
                    List<String> listDevicecode = new ArrayList<String>();
                    listDevicecode.add(devicecode);
                    List<MapModel> listMap = getAthorityMonitors(listDevicecode, httpsession);
                    if (listMap != null && listMap.size() > 0) {
                        List<String> listThingcode = new ArrayList<String>();
                        for (MapModel mapModel : listMap) {
                            listThingcode.add(mapModel.getCode());
                        }
                        // 查询24小时内数据
                        List<MonitorStorageModel> mapMonitor = new ArrayList<MonitorStorageModel>();
                        //					if (DefaultArgument.SYS_STA_RLD) {
                        //						mapMonitor = monitorStorageService.getTimelyMonitorData(
                        //										listDevicecode,
                        //										listThingcode,
                        //										nowtime,
                        //										DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_DEFAULT));
                        //					} else {
                        //						mapMonitor = monitorStorageService.getHourMonitorData(
                        //										listDevicecode,
                        //										listThingcode,
                        //										nowtime,
                        //										DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_DEFAULT));
                        //					}
                        Timestamp selectTime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR);
                        if (DefaultArgument.PRO_CN_GETMINUTE.equals(dataType)) {
                            selectTime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR * 24);
                        } else if (DefaultArgument.PRO_GET_HOUR.equals(dataType)) {
                            selectTime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR * 72);
                        } else if (DefaultArgument.PRO_GET_DAY.equals(dataType)) {
                            long millionSecond = (long)DefaultArgument.TIMELY_ONE_HOUR * 24 * DefaultArgument.RECENT_DAYS;
                            selectTime = DateUtil.GetSystemDateTime(millionSecond);
                        }
                        mapMonitor = monitorStorageService.getTimelyMonitorData(
                                listDevicecode,
                                listThingcode,
                                nowtime,
                                selectTime,
                                dataType);
                        if (mapMonitor != null && mapMonitor.size() > 0) {
                            for (MonitorStorageModel tempMonitor : mapMonitor) {
                                String frequetime = tempMonitor.getFrequeTime();
                                String thingname = tempMonitor.getThingName();
                                Double monitorvalue = tempMonitor.getMonitorValue();
                                if (treeMap.containsKey(frequetime)) {
                                    if (treeMap.get(frequetime).containsKey(thingname)) {
                                        continue;
                                    } else {
                                        treeMap.get(frequetime).put(thingname, monitorvalue);
                                    }
                                } else {
                                    LinkedHashMap<String, Double> hashMaplist = new LinkedHashMap<String, Double>();
                                    hashMaplist.put(thingname, monitorvalue);
                                    treeMap.put(frequetime, hashMaplist);
                                }
                                //添加折算
                                String thingnamezs = thingname + "-zs";
                                Double zsmonitorvalue = tempMonitor.getZsMonitorValue();
                                if (zsFlag) {
                                    if (treeMap.containsKey(frequetime)) {
                                        if (treeMap.get(frequetime).containsKey(thingnamezs)) {
                                            continue;
                                        } else {
                                            treeMap.get(frequetime).put(thingnamezs, zsmonitorvalue);
                                        }
                                    } else {
                                        LinkedHashMap<String, Double> hashMaplist = new LinkedHashMap<String, Double>();
                                        hashMaplist.put(thingnamezs, zsmonitorvalue);
                                        treeMap.put(frequetime, hashMaplist);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询实时数据错误，错误信息为：" + e.getMessage());
        } finally {
            loginuser.setSelectTime(nowtime);// 赋值本次查询时间，以便于下次查询
        }
        // 追加查询code
        resultTimelyModel.setResult(treeMap);
        return resultTimelyModel;
    }

    /**
     * <p>
     * [功能描述]：获取权限监测物
     * </p>
     *
     * @param deviceCodeList
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午2:02:13
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAthorityMonitors", method = {RequestMethod.POST})
    @ResponseBody
    public List<MapModel> getAthorityMonitors(
            @RequestParam(value = "deviceCodeList[]", required = false) List<String> deviceCodeList,
            HttpSession httpsession) {
        List<MapModel> listMap = new ArrayList<MapModel>();
        try {
            String usercode = null;
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return listMap;
            }
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            listMap = mapService.getAuthorityMonitors(usercode, DefaultArgument.INT_DEFAULT, null, deviceCodeList);

        } catch (Exception e) {
            logger.error(LOG + ":查询权限监测物数据错误，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

    /**
     * <p>
     * [功能描述]：获取权限设备监测物
     * </p>
     *
     * @param deviceCode
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午2:02:13
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAthorityDeviceMonitors", method = {RequestMethod.POST})
    public @ResponseBody
    List<MapModel> getAthorityDeviceMonitors(String deviceCode,
                                             HttpSession httpsession) {
        List<MapModel> listMap = new ArrayList<MapModel>();
        try {
            String usercode = null;
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                return listMap;
            }
            UserModel loginuser = (UserModel) httpsession
                    .getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            listMap = mapService.getAuthorityDeviceMonitors(usercode, deviceCode,
                    DefaultArgument.INT_DEFAULT, null);

        } catch (Exception e) {
            logger.error(LOG + ":查询权限设备监测物数据错误，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

    /**
     * <p>
     * [功能描述]：获取气象监测物
     * </p>
     *
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午2:02:13
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getHeWeatherMonitors", method = {RequestMethod.POST})
    public @ResponseBody
    List<MapModel> getHeWeatherMonitors(
            HttpSession httpsession) {
        List<MapModel> mapModelList = new ArrayList<MapModel>();
        for (Map.Entry<String, String> temp : EnumUtil.HeWeather.getHeweathermap().entrySet()) {
            MapModel mapModel = new MapModel();
            mapModel.setCode(temp.getKey());
            mapModel.setName(temp.getValue());
            mapModelList.add(mapModel);
        }
        return mapModelList;
    }

    /**
     * <p>
     * [功能描述]：查询实时监测物数据(ajax长链接方式)
     * </p>
     *
     * @param zsFlag
     * @param dataType
     * @param devicecode
     * @param isrepeat
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月8日上午11:10:14
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getTimelyMonitorDataConn", method = {RequestMethod.POST})
    public @ResponseBody
    ResultAjaxPushModel getTimelyMonitorDataConn(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "dataType", defaultValue = "2011") String dataType,
            String devicecode, boolean isrepeat, HttpSession httpsession) {
        try {
            ResultAjaxPushModel resultTimelyModel = new ResultAjaxPushModel();
            if (!haveTimelyResult) {
                resultTimelyModel = waittingTimely(zsFlag, dataType, devicecode, isrepeat, httpsession);

            } else {
                Thread.sleep(DefaultArgument.TIMELY_TIME);
            }
            return resultTimelyModel;
        } catch (Exception e) {
            logger.error(LOG + ":实时数据连接查询失败，原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * <p>
     * [功能描述]：实时查询数据线程
     * </p>
     *
     * @param zsFlag
     * @param dataType
     * @param devicecode
     * @param isrepeat
     * @param httpsession
     * @return
     * @throws Exception
     * @author 王垒, 2016年4月11日下午3:05:29
     * @since EnvDust 1.0.0
     */
    private ResultAjaxPushModel waittingTimely(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "dataType", defaultValue = "2011") String dataType,
            String devicecode, boolean isrepeat, HttpSession httpsession) throws Exception {
        Timestamp selecttime = DateUtil.GetSystemDateTime(0);
        Thread timely = new Thread("timely") {
            public void run() {
                while (!isInterrupted() && !haveTimelyResult) {
                    try {
                        if (SessionManager.isSessionValidate(httpsession,
                                DefaultArgument.LOGIN_USER)) {
                            return;
                        }
                        UserModel loginuser = new UserModel();
                        Timestamp nowtime = DateUtil.GetSystemDateTime(0);
                        loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                        nowtime = DateUtil.GetSystemDateTime(0);
                        if (loginuser != null) {
                            Timestamp lastselecttime = loginuser.getSelectTime();
                            if (!isrepeat || lastselecttime == null) {
                                lastselecttime = DateUtil.GetSystemDateTime(DefaultArgument.TIMELY_ONE_HOUR);
                            }
                            if (!StringUtils.isEmpty(devicecode)) {
                                List<String> listDevicecode = new ArrayList<String>();
                                listDevicecode.add(devicecode);
                                List<MapModel> listMap = getAthorityMonitors(listDevicecode, httpsession);
                                if (listMap != null && listMap.size() > 0) {
                                    List<String> listThingcode = new ArrayList<String>();
                                    for (MapModel mapModel : listMap) {
                                        listThingcode.add(mapModel.getCode());
                                    }

                                    if (DefaultArgument.SYS_STA_RLD) {
                                        if (monitorStorageService
                                                .getTimelyMonitorDataCount(
                                                        listDevicecode,
                                                        listThingcode,
                                                        nowtime,
                                                        lastselecttime,
                                                        dataType) > 0) {
                                            haveTimelyResult = true;
                                            break;
                                        }
                                    } else if (monitorStorageService
                                            .getHourMonitorDataCount(
                                                    listDevicecode, listThingcode,
                                                    nowtime, selecttime) > 0) {
                                        haveTimelyResult = true;
                                        break;
                                    }
                                    if (DateUtil.GetSystemDateTime(
                                            DefaultArgument.OUT_TIME).compareTo(
                                            selecttime) == 1) { // 连接超时
                                        break;
                                    }
                                }
                            }
                        }
                        Thread.sleep(DefaultArgument.TIMELY_TIME);

                    } catch (Exception e) {
                        logger.info(LOG + ":timely线程终止,信息为：" + e);
                        return;
                    }
                }
            }
        };
        timely.start();
        timely.join(); // 保证当线程t执行完后在执行后续任务
        if (haveTimelyResult) {
            haveTimelyResult = false;
            return getTimelyMonitorData(zsFlag, dataType, devicecode, isrepeat, httpsession);
        } else {
            return null;
        }
    }

    /**
     * <p>[功能描述]：查询网络监控状态(ajax长链接方式)</p>
     *
     * @param projectId
     * @param list
     * @param statusCode
     * @param levelflag
     * @param select
     * @param rows
     * @param page
     * @param httpsession
     * @return
     * @author 王垒, 2017年11月30日上午9:53:18
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getNetMonitorTimeConn", method = {RequestMethod.POST})
    public @ResponseBody
    String getNetMonitorTimeConn(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String statusCode,
            String levelflag, String select, int rows, int page,
            HttpSession httpsession) {
        try {
            String resultTime = waittingNetTime(projectId, list, levelflag, statusCode, select, rows,
                    page, httpsession);
            return resultTime;
        } catch (Exception e) {
            logger.error(LOG + ":网路监控查询失败，原因：" + e.getMessage());
            return null;
        }

    }

    /**
     * <p>
     * [功能描述]：查询网络监控数据(ajax长链接方式)
     * </p>
     *
     * @param projectId
     * @param listdevicecode
     * @param levelflag
     * @param statusCode
     * @param select
     * @param rows
     * @param page
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月8日上午11:10:14
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getNetMonitorDataConn", method = {RequestMethod.POST})
    public @ResponseBody
    String getNetMonitorDataConn(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> listdevicecode,
            String levelflag, String statusCode, String select, int rows, int page,
            HttpSession httpsession) {
        try {
            String resultData = waittingNetData(projectId, listdevicecode, levelflag, statusCode,
                    select, rows, page, httpsession);
            return resultData;
        } catch (Exception e) {
            logger.error(LOG + ":网路监控查询失败，原因：" + e.getMessage());
            return null;
        }

    }

    /**
     * <p>[功能描述]：网络监控查询数据线程(状态)</p>
     *
     * @param projectId
     * @param list
     * @param levelflag
     * @param statusCode
     * @param select
     * @param rows
     * @param page
     * @param httpsession
     * @return
     * @throws Exception
     * @author 王垒, 2017年11月30日上午9:52:19
     * @since EnvDust 1.0.0
     */
    private String waittingNetTime(@RequestParam(value = "projectId", required = false) String projectId,
                                   List<String> list, String levelflag, String statusCode,
                                   String select, int rows, int page, HttpSession httpsession)
            throws Exception {
        Thread nettime = new Thread("nettime") {
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(DefaultArgument.NET_TIME);
                        break;
                    } catch (Exception e) {
                        logger.info(LOG + ":nettime线程终止,信息为：" + e);
                        return;
                    }
                }
            }
        };
        nettime.start();
        nettime.join(); // 保证当线程执行完后在执行后续任务
        return getNetMonitorTime(projectId, list, levelflag, statusCode, select, rows, page,
                httpsession);
    }

    /**
     * <p>
     * [功能描述]：网络监控查询数据线程(数据)
     * </p>
     *
     * @param projectId
     * @param listdevicecode
     * @param statusCode
     * @param select
     * @param rows
     * @param page
     * @param httpsession
     * @return
     * @throws Exception
     * @author 王垒, 2016年4月11日下午3:05:29
     * @since EnvDust 1.0.0
     */
    private String waittingNetData(@RequestParam(value = "projectId", required = false) String projectId,
                                   List<String> listdevicecode, String levelflag, String statusCode, String select, int rows, int page,
                                   HttpSession httpsession) throws Exception {
        Thread netdata = new Thread("netdata") {
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(DefaultArgument.NET_TIME);
                        break;
                    } catch (Exception e) {
                        logger.info(LOG + ":netdata线程终止,信息为：" + e);
                        return;
                    }
                }
            }
        };
        netdata.start();
        netdata.join(); // 保证当线程执行完后在执行后续任务
        return getNetMonitorData(projectId, listdevicecode, statusCode, levelflag, select, rows, page, httpsession);
    }

    /**
     * <p>[功能描述]：查询网络监控数据(状态)</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param select
     * @param statusCode
     * @param rows
     * @param page
     * @param httpsession
     * @return
     * @author 王垒, 2017年11月30日上午9:51:30
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getNetMonitorTime", method = {RequestMethod.POST})
    @ResponseBody
    public String getNetMonitorTime(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]", required = false) List<String> list,
            @RequestParam(value = "levelFlag", required = false) String levelFlag,
            @RequestParam(value = "select", required = false) String select,
            String statusCode, int rows, int page, HttpSession httpsession) {
        ResultAjaxPushModel resultAjaxPushModel = new ResultAjaxPushModel();
        resultAjaxPushModel.setSelect(select);
        resultAjaxPushModel.setRows(rows);
        resultAjaxPushModel.setPage(page);
        List<NetMonitorTimeModel> listMonitorTime = new ArrayList<NetMonitorTimeModel>();
        int rowTotal = 0;
        int normalCount = 0;
        int overLineCount = 0;
        int outLinkCount = 0;
        int noCountCount = 0;
        int otherCount = 0;
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return "[]";
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<NetStatusCount> listNSCount = new ArrayList<NetStatusCount>();
            List<String> listdevicecode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                return "[]";
            } else if (list == null || list.size() == 0) {
                return "[]";
            } else {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid,
                            Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId,
                            DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listdevicecode.add(mapModel.getCode());
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(
                            userCode, new ArrayList<String>());
                } else if (listareaid != null && listareaid.size() > 0) {
                    for (Integer areaid : listareaid) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listdevicecode.add(mapModel.getCode());
                        }
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(
                            userCode, listdevicecode);
                } else {
                    for (String temp : list) {
                        listdevicecode.add(temp);
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(
                            userCode, listdevicecode);
                }

            }
            if (listNSCount != null && listNSCount.size() > 0) {
                for (NetStatusCount netStatusCount : listNSCount) {
                    switch (netStatusCount.getStatusCode()) {
                        case "N":
                            normalCount += netStatusCount.getStatusCount();
                            break;
                        case "NT":
                            overLineCount += netStatusCount.getStatusCount();
                            break;
                        case "O":
                            outLinkCount += netStatusCount.getStatusCount();
                            break;
                        case "Z":
                            noCountCount += netStatusCount.getStatusCount();
                            break;
                        default:
                            otherCount += netStatusCount.getStatusCount();
                            break;
                    }
                }
                rowTotal = normalCount + overLineCount + outLinkCount
                        + noCountCount + otherCount;
                resultAjaxPushModel.setRowTotal(rowTotal);
                resultAjaxPushModel.setNormalCount(normalCount);
                resultAjaxPushModel.setOverLineCount(overLineCount);
                resultAjaxPushModel.setOutLinkCount(outLinkCount);
                resultAjaxPushModel.setNoCountCount(noCountCount);
                resultAjaxPushModel.setOtherCount(otherCount);
            }
            if (listdevicecode != null && listdevicecode.size() > 0) {
                List<MapModel> listAhr = getAthorityMonitors(listdevicecode, httpsession);
                if (listAhr != null && listAhr.size() > 0) {
                    List<String> listThingcode = new ArrayList<String>();
                    for (MapModel mapModel : listAhr) {
                        listThingcode.add(mapModel.getCode());
                    }
                    //筛选符合状态的设备
                    if (statusCode != null && !statusCode.isEmpty()) {
                        listdevicecode = monitorStorageService.getDeviceCodeByStatus(listdevicecode, statusCode);
                    }
                    // 取分页后的设备
                    List<String> listDevCode = new ArrayList<String>();
                    if (rows != DefaultArgument.INT_DEFAULT && page != DefaultArgument.INT_DEFAULT) {
                        for (int i = (page - 1) * rows; i < page * rows; i++) {
                            if (i < listdevicecode.size()) {
                                listDevCode.add(listdevicecode.get(i));
                            } else {
                                break;
                            }
                        }
                    }
                    List<MonitorStorageModel> mapMonitor = monitorStorageService
                            .getNetMonitorRecentTime(listDevCode, listThingcode, statusCode);
                    Map<String, NetMonitorTimeModel> mapResult = new TreeMap<String, NetMonitorTimeModel>();
                    if (mapMonitor != null && mapMonitor.size() > 0) {
                        for (MonitorStorageModel tempMonitor : mapMonitor) {
                            NetMonitorTimeModel netMonitorTimeModel = new NetMonitorTimeModel();
                            String deviceCode = tempMonitor.getDeviceCode();
                            String deviceMn = tempMonitor.getDeviceMn();
                            String deviceName = tempMonitor.getDeviceName();
                            String thingName = tempMonitor.getThingName();
                            String deviceStatus = tempMonitor.getDeviceStatus();
                            String deviceStatusInfo = tempMonitor.getDeviceStatusInfo();
                            String frequeTime = tempMonitor.getFrequeTime();
                            String areaName = tempMonitor.getAreaName();
                            netMonitorTimeModel.setDeviceCode(deviceCode);
                            netMonitorTimeModel.setDeviceMn(deviceMn);
                            netMonitorTimeModel.setDeviceName(deviceName);
                            netMonitorTimeModel.setDeviceStatus(deviceStatus);
                            netMonitorTimeModel.setDeviceStatusInfo(deviceStatusInfo);
                            netMonitorTimeModel.setAreaName(areaName);
                            if (deviceCode != null && !deviceCode.isEmpty()) {
                                if (mapResult.containsKey(deviceCode)) {
                                    if (!mapResult.get(deviceCode).getMapList().containsKey(thingName)) {
                                        mapResult.get(deviceCode).getMapList().put(thingName, frequeTime);
                                    } else {
                                        logger.warn(LOG + ":网络状态监控存在相同键值的对象：" + thingName + "-" + frequeTime);
                                    }
                                } else {
                                    TreeMap<String, String> treeMap = new TreeMap<String, String>();
                                    treeMap.put(thingName, frequeTime);
                                    netMonitorTimeModel.setMapList(treeMap);
                                    mapResult.put(deviceCode, netMonitorTimeModel);
                                }
                            }
                        }
                        for (Map.Entry<String, NetMonitorTimeModel> tempMonitor : mapResult.entrySet()) {
                            listMonitorTime.add(tempMonitor.getValue());
                        }
                        resultAjaxPushModel.setResult(listMonitorTime);
                    }
                }
            } else {
                return ConvertResultAjaxPushModel(resultAjaxPushModel, "time");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询网络监控状态错误，错误信息为：" + e.getMessage());
        }
        return ConvertResultAjaxPushModel(resultAjaxPushModel, "time");
    }

    /**
     * <p>
     * [功能描述]：查询网络监控数据(数据)
     * </p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param select
     * @param statusCode
     * @param rows
     * @param page
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午12:28:04
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getNetMonitorData", method = {RequestMethod.POST})
    @ResponseBody
    public String getNetMonitorData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]", required = false) List<String> list,
            @RequestParam(value = "levelFlag", required = false) String levelFlag,
            @RequestParam(value = "select", required = false) String select,
            String statusCode, int rows, int page, HttpSession httpsession) {
        ResultAjaxPushModel resultAjaxPushModel = new ResultAjaxPushModel();
        resultAjaxPushModel.setSelect(select);
        resultAjaxPushModel.setRows(rows);
        resultAjaxPushModel.setPage(page);
        List<NetMonitorDataModel> listMonitorData = new ArrayList<NetMonitorDataModel>();
        int rowTotal = 0;
        int normalCount = 0;
        int overLineCount = 0;
        int outLinkCount = 0;
        int noCountCount = 0;
        int otherCount = 0;
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return "[]";
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<NetStatusCount> listNSCount = new ArrayList<NetStatusCount>();
//			List<String> listalldevicecode = new ArrayList<String>();
            List<String> listdevicecode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                return "[]";
            } else if (list == null || list.size() == 0) {
                return "[]";
            } else {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid,
                            Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listdevicecode.add(mapModel.getCode());
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, new ArrayList<String>());
                } else if (listareaid != null && listareaid.size() > 0) {
                    for (Integer areaid : listareaid) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listdevicecode.add(mapModel.getCode());
                        }
                    }
//					List<MapModel> listAllDev = getAuthorityDevices(DefaultArgument.INT_DEFAULT, null,httpsession);
//					for (MapModel mapModel : listAllDev) {
//						listalldevicecode.add(mapModel.getCode());
//					}
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, listdevicecode);
                } else {
                    for (String temp : list) {
                        listdevicecode.add(temp);
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, listdevicecode);
                }

            }
            if (listNSCount != null && listNSCount.size() > 0) {
                rowTotal = listNSCount.size();
                for (NetStatusCount netStatusCount : listNSCount) {
                    switch (netStatusCount.getStatusCode()) {
                        case "N":
                            normalCount += netStatusCount.getStatusCount();
                            break;
                        case "NT":
                            overLineCount += netStatusCount.getStatusCount();
                            break;
                        case "O":
                            outLinkCount += netStatusCount.getStatusCount();
                            break;
                        case "Z":
                            noCountCount += netStatusCount.getStatusCount();
                            break;
                        default:
                            otherCount += netStatusCount.getStatusCount();
                            break;
                    }
                }
                rowTotal = normalCount + overLineCount + outLinkCount
                        + noCountCount + otherCount;
                resultAjaxPushModel.setRowTotal(rowTotal);
                resultAjaxPushModel.setNormalCount(normalCount);
                resultAjaxPushModel.setOverLineCount(overLineCount);
                resultAjaxPushModel.setOutLinkCount(outLinkCount);
                resultAjaxPushModel.setNoCountCount(noCountCount);
                resultAjaxPushModel.setOtherCount(otherCount);
            }
            if (listdevicecode != null && listdevicecode.size() > 0) {
                List<MapModel> listAhr = getAthorityMonitors(listdevicecode, httpsession);
                if (listAhr != null && listAhr.size() > 0) {
                    List<String> listThingcode = new ArrayList<String>();
                    for (MapModel mapModel : listAhr) {
                        listThingcode.add(mapModel.getCode());
                    }

                    //筛选符合状态的设备
                    if (statusCode != null && !statusCode.isEmpty()) {
                        listdevicecode = monitorStorageService.getDeviceCodeByStatus(listdevicecode, statusCode);
                    }
                    // 取分页后的设备
                    List<String> listDevCode = new ArrayList<String>();
                    if (rows != DefaultArgument.INT_DEFAULT
                            && page != DefaultArgument.INT_DEFAULT) {
                        for (int i = (page - 1) * rows; i < page * rows; i++) {
                            if (i < listdevicecode.size()) {
                                listDevCode.add(listdevicecode.get(i));
                            }
                        }
                    }
                    List<MonitorStorageModel> mapMonitor = monitorStorageService.getNetMonitorRecentData(listDevCode, listThingcode, statusCode);
                    Map<String, NetMonitorDataModel> mapResult = new TreeMap<String, NetMonitorDataModel>();
                    if (mapMonitor != null && mapMonitor.size() > 0) {
                        for (MonitorStorageModel tempMonitor : mapMonitor) {
                            NetMonitorDataModel netMonitorDataModel = new NetMonitorDataModel();
                            String deviceCode = tempMonitor.getDeviceCode();
                            String deviceMn = tempMonitor.getDeviceMn();
                            String deviceName = tempMonitor.getDeviceName();
                            String thingName = tempMonitor.getThingName();
                            String deviceStatus = tempMonitor.getDeviceStatus();
                            String deviceStatusInfo = tempMonitor.getDeviceStatusInfo();
                            String areaName = tempMonitor.getAreaName();
                            String monitorValue = String.valueOf(tempMonitor.getMonitorValue());
                            netMonitorDataModel.setDeviceCode(deviceCode);
                            netMonitorDataModel.setDeviceMn(deviceMn);
                            netMonitorDataModel.setDeviceName(deviceName);
                            netMonitorDataModel.setDeviceStatus(deviceStatus);
                            netMonitorDataModel.setDeviceStatusInfo(deviceStatusInfo);
                            netMonitorDataModel.setAreaName(areaName);
                            if (deviceCode != null && !deviceCode.isEmpty()) {
                                if (mapResult.containsKey(deviceCode)) {
                                    if (!mapResult.get(deviceCode).getMapList().containsKey(thingName)) {
                                        mapResult.get(deviceCode).getMapList().put(thingName, monitorValue);
                                    } else {
                                        logger.warn(LOG + ":网络数据监控存在相同键值的对象：" + thingName + "-" + monitorValue);
                                    }
                                } else {
                                    TreeMap<String, String> treeMap = new TreeMap<String, String>();
                                    treeMap.put(thingName, monitorValue);
                                    netMonitorDataModel.setMapList(treeMap);
                                    mapResult.put(deviceCode, netMonitorDataModel);
                                }
                            }
                        }
                        for (Map.Entry<String, NetMonitorDataModel> tempMonitor : mapResult
                                .entrySet()) {
                            listMonitorData.add(tempMonitor.getValue());
                        }
                        resultAjaxPushModel.setResult(listMonitorData);
                    }
                }
            } else {
                return ConvertResultAjaxPushModel(resultAjaxPushModel, "data");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询网络监控数据错误，错误信息为：" + e.getMessage());
        }
        return ConvertResultAjaxPushModel(resultAjaxPushModel, "data");
    }

    /**
     * <p>
     * [功能描述]：获取权限设备数据
     * </p>
     *
     * @param projectId
     * @param areaid
     * @param statusCode
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午9:58:18
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAuthorityDevices", method = {RequestMethod.POST})
    public @ResponseBody
    List<MapModel> getAuthorityDevices(
            @RequestParam(value = "projectId", required = false) String projectId,
            int areaid, String statusCode, HttpSession httpsession) {
        ArrayList<MapModel> listMapModel = new ArrayList<MapModel>();
        // 获取用户code
        String usercode = null;
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return listMapModel;
        }
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (usercode != null && !usercode.isEmpty()) {
            ArrayList<TreeModel> al = treeService.getAuthorityDevices(usercode, projectId, areaid, null, statusCode, null);
            for (TreeModel treeModel : al) {
                MapModel mapModel = new MapModel();
                mapModel.setCode(treeModel.getId());
                mapModel.setName(treeModel.getText());
                listMapModel.add(mapModel);
            }
        }

        return listMapModel;
    }

    /**
     * <p>
     * [功能描述]：将treeMap结果集转换成json字符串
     * </p>
     *
     * @param treeMap
     * @return
     * @author 王垒, 2016年5月6日下午1:29:36
     * @since EnvDust 1.0.0
     */
    public String ConvertToJson(TreeMap<String, HashMap<String, Double>> treeMap) {
        String reJson = "[";
        try {
            for (Entry<String, HashMap<String, Double>> tempTreeMap : treeMap
                    .entrySet()) {
                reJson += "{\"time\":";
                reJson += "\"" + tempTreeMap.getKey() + "\"";
                for (Entry<String, Double> tempMap : tempTreeMap.getValue()
                        .entrySet()) {
                    reJson += ",\"" + tempMap.getKey() + "\":";
                    reJson += String.valueOf(tempMap.getValue());
                }
                reJson += "},";
            }
            if (reJson.lastIndexOf(",") >= 0) {
                reJson = reJson.substring(0, reJson.lastIndexOf(","));
            }
        } catch (Exception e) {
            logger.error(LOG + "：转换成json字符串失败，信息：" + e.getMessage());
        }
        reJson += "]";
        return reJson;
    }

    /**
     * <p>
     * [功能描述]：网络监控数据转换成json
     * </p>
     *
     * @param resultAjaxPushModel
     * @return
     * @author 王垒, 2016年7月11日上午10:22:45
     * @since EnvDust 1.0.0
     */
    public String ConvertResultAjaxPushModel(
            ResultAjaxPushModel resultAjaxPushModel, String type) {
        String reJson = "";
        try {
            reJson += "{\"select\":";
            reJson += "\"" + resultAjaxPushModel.getSelect() + "\"";
            reJson += ",\"rows\":";
            reJson += "\"" + resultAjaxPushModel.getRows() + "\"";
            reJson += ",\"page\":";
            reJson += "\"" + resultAjaxPushModel.getPage() + "\"";
            reJson += ",\"result\":";
            if (type != null && type.equals("time")) {
                reJson += ""
                        + ConvertToJsonNMT(resultAjaxPushModel.getResult())
                        + "";
            } else {
                reJson += ""
                        + ConvertToJsonNMD(resultAjaxPushModel.getResult())
                        + "";
            }
            // reJson += ",\"alarmLine\":";
            // reJson += "\"" + resultAjaxPushModel.getAlarmLine() + "\"";
            reJson += ",\"normalCount\":";
            reJson += "\"" + resultAjaxPushModel.getNormalCount() + "\"";
            reJson += ",\"overLineCount\":";
            reJson += "\"" + resultAjaxPushModel.getOverLineCount() + "\"";
            reJson += ",\"outLinkCount\":";
            reJson += "\"" + resultAjaxPushModel.getOutLinkCount() + "\"";
            reJson += ",\"noCountCount\":";
            reJson += "\"" + resultAjaxPushModel.getNoCountCount() + "\"";
            reJson += ",\"otherCount\":";
            reJson += "\"" + resultAjaxPushModel.getOtherCount() + "\"";
            reJson += ",\"rowTotal\":";
            reJson += "\"" + resultAjaxPushModel.getRowTotal() + "\"";
            reJson += "}";
        } catch (Exception e) {
            logger.error(LOG + "：转换成json字符串失败，信息：" + e.getMessage());
        }
        return reJson;
    }

    /**
     * <p>
     * [功能描述]：将List《NetMonitorTimeModel》结果集转换成json字符串
     * </p>
     *
     * @param object
     * @return
     * @author 王垒, 2016年5月6日下午1:29:36
     * @since EnvDust 1.0.0
     */
    public String ConvertToJsonNMT(Object object) {
        String reJson = "[";
        try {
            @SuppressWarnings("unchecked")
            List<NetMonitorTimeModel> list = (List<NetMonitorTimeModel>) object;
            if (list != null && list.size() > 0) {
                for (NetMonitorTimeModel netMonitorTimeModel : list) {
                    reJson += "{\"deviceCode\":";
                    reJson += "\"" + netMonitorTimeModel.getDeviceCode() + "\"";
                    reJson += ",\"deviceMn\":";
                    reJson += "\"" + netMonitorTimeModel.getDeviceMn() + "\"";
                    reJson += ",\"deviceName\":";
                    reJson += "\"" + netMonitorTimeModel.getDeviceName() + "\"";
                    reJson += ",\"areaName\":";
                    reJson += "\"" + netMonitorTimeModel.getAreaName() + "\"";
                    reJson += ",\"deviceStatus\":";
                    reJson += "\"" + netMonitorTimeModel.getDeviceStatus() + "\"";
                    reJson += ",\"deviceStatusInfo\":";
                    reJson += "\"" + netMonitorTimeModel.getDeviceStatusInfo()
                            + "\"";
                    Map<String, String> mapList = netMonitorTimeModel.getMapList();
                    if (mapList != null && mapList.size() > 0) {
                        for (Entry<String, String> tempMap : mapList.entrySet()) {
                            reJson += ",\"" + tempMap.getKey() + "\":";
                            reJson += "\"" + String.valueOf(tempMap.getValue())
                                    + "\"";
                        }
                    }
                    reJson += "},";
                }
            }
            if (reJson.lastIndexOf(",") >= 0) {
                reJson = reJson.substring(0, reJson.lastIndexOf(","));
            }
        } catch (Exception e) {
            logger.error(LOG + "：转换成json字符串失败，信息：" + e.getMessage());
        }
        reJson += "]";
        return reJson;
    }

    /**
     * <p>
     * [功能描述]：将List《NetMonitorDataModel》结果集转换成json字符串
     * </p>
     *
     * @param object
     * @return
     * @author 王垒, 2016年5月6日下午1:29:36
     * @since EnvDust 1.0.0
     */
    public String ConvertToJsonNMD(Object object) {
        String reJson = "[";
        try {
            @SuppressWarnings("unchecked")
            List<NetMonitorDataModel> list = (List<NetMonitorDataModel>) object;
            if (list != null && list.size() > 0) {
                for (NetMonitorDataModel netMonitorDataModel : list) {
                    reJson += "{\"deviceCode\":";
                    reJson += "\"" + netMonitorDataModel.getDeviceCode() + "\"";
                    reJson += ",\"deviceMn\":";
                    reJson += "\"" + netMonitorDataModel.getDeviceMn() + "\"";
                    reJson += ",\"deviceName\":";
                    reJson += "\"" + netMonitorDataModel.getDeviceName() + "\"";
                    reJson += ",\"areaName\":";
                    reJson += "\"" + netMonitorDataModel.getAreaName() + "\"";
                    reJson += ",\"deviceStatus\":";
                    reJson += "\"" + netMonitorDataModel.getDeviceStatus() + "\"";
                    reJson += ",\"deviceStatusInfo\":";
                    reJson += "\"" + netMonitorDataModel.getDeviceStatusInfo()
                            + "\"";
                    Map<String, String> mapList = netMonitorDataModel.getMapList();
                    if (mapList != null && mapList.size() > 0) {
                        for (Entry<String, String> tempMap : mapList.entrySet()) {
                            reJson += ",\"" + tempMap.getKey() + "\":";
                            reJson += String.valueOf(tempMap.getValue());
                        }
                    }
                    reJson += "},";
                }
            }
            if (reJson.lastIndexOf(",") >= 0) {
                reJson = reJson.substring(0, reJson.lastIndexOf(","));
            }
        } catch (Exception e) {
            logger.error(LOG + "：转换成json字符串失败，信息：" + e.getMessage());
        }
        reJson += "]";
        return reJson;
    }

    /**
     * 查询原始上传数据（中转）
     * 指向"/getOriginalData"
     */
    @RequestMapping(value = "/preGetOriginalData", method = RequestMethod.POST)
    @ResponseBody
    public ResultListModel<OriginalDataModel> preGetOriginalData(@RequestBody Map map, HttpSession session) {
        int rows = (Integer) map.get("rows");
        int page = (Integer) map.get("page");
        String deviceCode = (String) map.get("deviceCode");
        int updateType = (Integer) map.get("updateType");
        String select = (String) map.get("select");
        String thingCode = (String) map.get("thingCode");
        String beginTime = (String) map.get("beginTime");
        String endTime = (String) map.get("endTime");

        OriginalDataModel originalDataModel = new OriginalDataModel();
        originalDataModel.setPage(page);
        originalDataModel.setRows(rows);
        originalDataModel.setDeviceCode(deviceCode);
        originalDataModel.setUpdateType(String.valueOf(updateType));
        originalDataModel.setThingCode(thingCode);
        originalDataModel.setBeginTime(beginTime);
        originalDataModel.setEndTime(endTime);

        return getOriginalData(null, originalDataModel, select, session);
    }

    /**
     * <p>
     * [功能描述]：查询原始上传数据(列表)
     * </p>
     *
     * @param list
     * @param originalDataModel
     * @param select
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月22日下午2:11:25
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getOriginalData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<OriginalDataModel> getOriginalData(
            @RequestParam(value = "list[]", required = false) List<String> list,
            OriginalDataModel originalDataModel, String select,
            HttpSession httpsession) {
        ResultListModel<OriginalDataModel> resultListModel = new ResultListModel<OriginalDataModel>();
        resultListModel.setSelect(select);
        List<OriginalDataModel> listOriginal = new ArrayList<OriginalDataModel>();
        if (originalDataModel == null) {
            return resultListModel;
        }
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String devicecode = originalDataModel.getDeviceCode();
            Timestamp begintime = DateUtil.StringToTimestampSecond(originalDataModel.getBeginTime());
            Timestamp endtime = DateUtil.StringToTimestampSecond(originalDataModel.getEndTime());
            String updatetype = originalDataModel.getUpdateType();
            int rowIndex = (originalDataModel.getPage() - 1) * originalDataModel.getRows();
            int rowCount = originalDataModel.getRows();

            if (!StringUtils.isEmpty(devicecode)) {
                List<String> listDevicecode = new ArrayList<String>();
                listDevicecode.add(devicecode);
                List<String> listDeviceCode = new ArrayList<String>();
                listDeviceCode.add(devicecode);

                List<String> listThingcode = new ArrayList<String>();
                String thingcode = originalDataModel.getThingCode();
                if (thingcode != null && !thingcode.isEmpty()) {
                    listThingcode.add(thingcode);
                } else if (list != null && list.size() > 0) {
                    for (String temp : list) {
                        listThingcode.add(temp);
                    }
                } else {
                    List<MapModel> listMap = getAthorityMonitors(listDeviceCode, httpsession);
                    for (MapModel mapModel : listMap) {
                        listThingcode.add(mapModel.getCode());
                    }
                }
                if (listThingcode != null && listThingcode.size() > 0) {
                    // 查询数据
                    int count = monitorStorageService.getOriginalDataCount(
                            listDeviceCode, listThingcode, begintime, endtime,
                            updatetype, select);
                    if (count > 0) {

                        listOriginal = monitorStorageService.getOriginalData(
                                listDeviceCode, listThingcode, begintime, endtime,
                                updatetype, select, rowIndex, rowCount);
                        resultListModel.setTotal(count);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(LOG + ":查询原始数据错误，错误信息为：" + e.getMessage());
        }

        // 追加查询code
        resultListModel.setRows(listOriginal);
        return resultListModel;
    }

    /**
     * <p>[功能描述]：查询原始上传数据(图形)</p>
     *
     * @param zsFlag
     * @param list
     * @param originalDataModel
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月29日上午8:31:56
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getOriginalChartData", method = {RequestMethod.POST})
    @ResponseBody
    public TreeMap<String, List<String>> getOriginalChartData(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]", required = false) List<String> list,
            OriginalDataModel originalDataModel, HttpSession httpsession) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        List<OriginalDataModel> listOriginal = new ArrayList<OriginalDataModel>();
        if (originalDataModel == null) {
            return treeMap;
        }
        try {
            String devicecode = originalDataModel.getDeviceCode();
            Timestamp begintime = DateUtil.StringToTimestampSecond(originalDataModel.getBeginTime());
            Timestamp endtime = DateUtil.StringToTimestampSecond(originalDataModel.getEndTime());
            String updatetype = originalDataModel.getUpdateType();
            int rowIndex = DefaultArgument.INT_DEFAULT;
            int rowCount = DefaultArgument.INT_DEFAULT;
            boolean rldFlag = false;
            if (DefaultArgument.PRO_CN_REALBEGIN.endsWith(originalDataModel.getUpdateType())) {
                rldFlag = true;
            } else {
                rldFlag = false;
            }
            List<String> listThingcode = new ArrayList<String>();
            if (!StringUtils.isEmpty(devicecode)) {
                List<String> listDevicecode = new ArrayList<String>();
                listDevicecode.add(devicecode);
                List<String> listDeviceCode = new ArrayList<String>();
                listDeviceCode.add(devicecode);
                String thingcode = originalDataModel.getThingCode();
                if (thingcode != null && !thingcode.isEmpty()) {
                    listThingcode.add(thingcode);
                } else if (list != null && list.size() > 0) {
                    for (String temp : list) {
                        listThingcode.add(temp);
                    }
                } else {
                    List<MapModel> listMap = getAthorityMonitors(listDeviceCode, httpsession);
                    for (MapModel mapModel : listMap) {
                        listThingcode.add(mapModel.getCode());
                    }
                }
                if (listThingcode != null && listThingcode.size() > 0) {
                    // 查询数据
                    int count = monitorStorageService.getOriginalDataCount(
                            listDeviceCode, listThingcode, begintime, endtime,
                            updatetype, null);
                    if (count > 0) {
                        listOriginal = monitorStorageService.getOriginalData(
                                listDeviceCode, listThingcode, begintime, endtime,
                                updatetype, null, rowIndex, rowCount);
                    }
                }
            }
            List<String> listName = monitorStorageService.getMonNamebyCode(listThingcode);
            // 排序处理
            TreeMap<String, List<OriginalDataModel>> treeOriginal = new TreeMap<String, List<OriginalDataModel>>();
            for (OriginalDataModel tempOriginal : listOriginal) {
                String frequeTime = "";
                if (rldFlag) {
                    frequeTime = tempOriginal.getRtdTime();
                } else {
                    frequeTime = tempOriginal.getBeginTime();
                }
                if (treeOriginal.containsKey(frequeTime)) {
                    treeOriginal.get(frequeTime).add(tempOriginal);
                } else {
                    List<OriginalDataModel> listtemp = new ArrayList<OriginalDataModel>();
                    listtemp.add(tempOriginal);
                    treeOriginal.put(frequeTime, listtemp);
                }
            }
            for (Map.Entry<String, List<OriginalDataModel>> tempTreeOriginal : treeOriginal.entrySet()) {
                String frequetime = tempTreeOriginal.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测物
                for (String temp : listName) {
                    String thingname;
                    String monitorvalue = null;
                    String monitorzsvalue = null;
                    for (OriginalDataModel tempOriginal : tempTreeOriginal.getValue()) {
                        thingname = tempOriginal.getThingName();
                        if (temp.equals(thingname)) {
                            if (rldFlag) {
                                monitorvalue = String.valueOf(tempOriginal.getThingRtd());
                                monitorzsvalue = String.valueOf(tempOriginal.getThingZsRtd());
                            } else {
                                monitorvalue = String.valueOf(tempOriginal.getThingAvg());
                                monitorzsvalue = String.valueOf(tempOriginal.getThingZsAvg());
                            }
                            break;
                        }
                    }
                    if (treeMap.containsKey(temp)) {
                        treeMap.get(temp).add(monitorvalue);

                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(temp, listThing);
                    }
                    if (zsFlag) {
                        String tempzs = temp + "-折算";
                        if (treeMap.containsKey(tempzs)) {
                            treeMap.get(tempzs).add(monitorzsvalue);

                        } else {
                            List<String> listThing = new ArrayList<String>();
                            listThing.add(monitorzsvalue);
                            treeMap.put(tempzs, listThing);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询原始数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }


    /**
     * <p>[功能描述]：单站点多参数气象值（图形）</p>
     *
     * @param devicecode
     * @param things
     * @param weathers
     * @param begintime
     * @param endtime
     * @param freque
     * @return
     * @author 王垒, 2017年12月7日上午10:49:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getSingleStationWeatherChartData", method = {RequestMethod.POST})
    public @ResponseBody
    TreeMap<String, List<String>> getSingleStationWeatherChartData(
            String devicecode,
            @RequestParam(value = "things[]") List<String> things,
            @RequestParam(value = "weathers[]") List<String> weathers,
            String begintime, String endtime, String freque) {
        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>();
        if (devicecode.equals(DefaultArgument.INT_DEFAULT)) {
            return treeMap;
        }
        try {
            List<String> listDevicecode = new ArrayList<String>();
            listDevicecode.add(devicecode);
            List<String> listThingcode = things;
            List<String> listName = monitorStorageService.getMonNamebyCode(listThingcode);
            Map<Integer, List<MonitorStorageModel>> mapMonitor = getStatisticsDataByFreque(
                    listDevicecode, listThingcode, begintime, endtime, freque);
            // 排序处理
            TreeMap<String, List<MonitorStorageModel>> treeMonitor = new TreeMap<String, List<MonitorStorageModel>>();
            for (Map.Entry<Integer, List<MonitorStorageModel>> tempListMonitor : mapMonitor
                    .entrySet()) {
                if (tempListMonitor.getKey() > 0) {
                    for (MonitorStorageModel tempMonitor : tempListMonitor.getValue()) {
                        if (treeMonitor.containsKey(tempMonitor.getFrequeTime())) {
                            treeMonitor.get(tempMonitor.getFrequeTime()).add(tempMonitor);
                        } else {
                            List<MonitorStorageModel> listtemp = new ArrayList<MonitorStorageModel>();
                            listtemp.add(tempMonitor);
                            treeMonitor.put(tempMonitor.getFrequeTime(), listtemp);
                        }
                    }
                }
            }
            for (Map.Entry<String, List<MonitorStorageModel>> tempTreeMonitor : treeMonitor.entrySet()) {
                String frequetime = tempTreeMonitor.getKey();
                // 时间
                if (treeMap.containsKey(DefaultArgument.STA_TIME)) {
                    treeMap.get(DefaultArgument.STA_TIME).add(frequetime);

                } else {
                    List<String> listTime = new ArrayList<String>();
                    listTime.add(frequetime);
                    treeMap.put(DefaultArgument.STA_TIME, listTime);
                }
                // 监测物
                for (String temp : listName) {
                    String thingname = null;
                    String monitorvalue = null;
                    for (MonitorStorageModel tempMonitor : tempTreeMonitor.getValue()) {
                        thingname = tempMonitor.getThingName();
                        if (temp.equals(thingname)) {
                            monitorvalue = String.valueOf(tempMonitor.getMonitorValue());
                            break;
                        }
                    }
                    if (treeMap.containsKey(temp)) {
                        treeMap.get(temp).add(monitorvalue);
                    } else {
                        List<String> listThing = new ArrayList<String>();
                        listThing.add(monitorvalue);
                        treeMap.put(temp, listThing);
                    }
                }
                // 气象参数
                if (weathers != null && weathers.size() > 0) {
                    //获取气象数据
                    String frequetimeTemp = frequetime.replace("时", "");
                    //需要根据设备查找区域天气ID
                    String cid = getCid(devicecode);
                    HeWeather heWeather = new HeWeather();
                    heWeather.setCid(cid);
                    heWeather.setLoc(frequetimeTemp);
                    List<HeWeather> heweatherList = weatherService.getWeather(heWeather);
                    if (heweatherList != null && heweatherList.size() > 0) {
                        heWeather = heweatherList.get(0);
                        for (String temp : weathers) {
                            String thingname = EnumUtil.HeWeather.getName(temp);
                            String monitorvalue = null;
                            EnumUtil.HeWeather dataName = EnumUtil.HeWeather.valueOf(temp.toUpperCase(Locale.ENGLISH));
                            switch (dataName) {
                                case HUM: {
                                    monitorvalue = heWeather.getHum();
                                }
                                break;
                                case PCPN: {
                                    monitorvalue = heWeather.getPcpn();
                                }
                                break;
                                case PRES: {
                                    monitorvalue = heWeather.getPres();
                                }
                                break;
                                case TMP: {
                                    monitorvalue = heWeather.getTmp();
                                }
                                break;
                                case WIND_DEG: {
                                    monitorvalue = heWeather.getWindDeg();
                                }
                                break;
                                case WIND_SPD: {
                                    monitorvalue = heWeather.getWindSpd();
                                }
                                break;
                                default:
                                    break;
                            }
                            if (treeMap.containsKey(thingname)) {
                                treeMap.get(thingname).add(monitorvalue);
                            } else {
                                List<String> listThing = new ArrayList<String>();
                                listThing.add(monitorvalue);
                                treeMap.put(thingname, listThing);
                            }
                        }
                    } else {
                        for (String temp : weathers) {
                            String thingname = EnumUtil.HeWeather.getName(temp);
                            if (treeMap.containsKey(thingname)) {
                                treeMap.get(thingname).add("");
                            } else {
                                List<String> listThing = new ArrayList<String>();
                                listThing.add("");
                                treeMap.put(thingname, listThing);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询参数气象值数据错误，错误信息为：" + e.getMessage());
        }
        return treeMap;
    }

    /**
     * <p>[功能描述]：获取城市编码</p>
     *
     * @param deviceCode
     * @return
     * @author 王垒, 2017年12月8日上午9:15:39
     * @since EnvDust 1.0.0
     */
    private String getCid(String deviceCode) {
        String cid = null;
        try {
            if (deviceCode != null && !deviceCode.isEmpty()) {
                Device device = new Device();
                device.setDeviceCode(deviceCode);
                List<Device> deviceList = deviceService.getDevice(device);
                if (deviceList != null && deviceList.size() > 0 && deviceList.get(0).getArea() != null) {
                    Area area = new Area();
                    area.setAreaId(deviceList.get(0).getArea().getAreaId());
                    List<Area> areaList = areaService.getAreas(area, true);
                    if (areaList != null && areaList.size() > 0) {
                        cid = areaList.get(0).getCityId();
                        if (cid == null || cid.isEmpty()) {
                            cid = getCid(areaList.get(0).getParentId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询CID失败，错误信息为：" + e.getMessage());
        }
        return cid;
    }

    /**
     * <p>[功能描述]：获取城市编码</p>
     *
     * @param areaId
     * @return
     * @author 王垒, 2017年12月8日上午9:16:05
     * @since EnvDust 1.0.0
     */
    private String getCid(int areaId) {
        String cid = null;
        if (areaId > 0) {
            Area area = new Area();
            area.setAreaId(areaId);
            List<Area> areaList = areaService.getAreas(area, true);
            if (areaList != null && areaList.size() > 0) {
                cid = areaList.get(0).getCityId();
                if (cid == null || cid.isEmpty()) {
                    cid = getCid(areaList.get(0).getParentId());
                }
            }
        }
        return cid;
    }

    /**
     * <p>[功能描述]：更新原始上传数据(数据修约)</p>
     *
     * @param originalDataModel
     * @param httpSession
     * @return
     * @author 王垒, 2018年6月4日上午11:52:46
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/updateOriginalData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateOriginalData(OriginalDataModel originalDataModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (originalDataModel != null) {
            try {
                UserModel loginUser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginUser == null) {
                    resultModel.setResult(false);
                    resultModel.setDetail("无登录信息或登录超时！");
                    return resultModel;
                }
                int intresult = monitorStorageService.updateOriginalDeviceData(originalDataModel, loginUser.getUserId());
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新原始数据失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新原始数据失败！");
                logger.error(LOG + "：更新原始数据失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：删除原始上传数据(数据修约)</p>
     *
     * @param originalDataModel
     * @param httpSession
     * @return
     * @author 王垒, 2018年6月4日上午11:52:46
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteOriginalData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteOriginalData(OriginalDataModel originalDataModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (originalDataModel != null) {
            try {
                UserModel loginUser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginUser == null) {
                    resultModel.setResult(false);
                    resultModel.setDetail("无登录信息或登录超时！");
                    return resultModel;
                }
                int intresult = monitorStorageService.deleteOriginalDeviceData(originalDataModel, loginUser.getUserId());
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除原始数据失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除原始数据失败！");
                logger.error(LOG + "：删除原始数据失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

}
