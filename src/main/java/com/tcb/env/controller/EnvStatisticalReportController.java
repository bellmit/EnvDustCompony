package com.tcb.env.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.AlarmRateModel;
import com.tcb.env.model.EnvStatisticalReportModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.OnlineMonitorReportMainModel;
import com.tcb.env.model.OnlineMonitorReportTimeModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IEnvStatisticalReportService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：年月日统计报表控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月4日下午4:15:43
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/EnvStatisticalReportController")
public class EnvStatisticalReportController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "EnvStatisticalReportController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(EnvStatisticalReportController.class);
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

    @Resource
    private IEnvStatisticalReportService envStatisticalReportService;

    /**
     * <p>[功能描述]：查询年月日统计报表</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param selectTime
     * @param selectType
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月5日下午10:35:32
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getEsrData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<EnvStatisticalReportModel> getEsrData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag, String thingCode,
            String selectTime, String selectType, HttpSession httpsession) {
        ResultListModel<EnvStatisticalReportModel> resultListModel = new ResultListModel<EnvStatisticalReportModel>();
        List<EnvStatisticalReportModel> listEsr = new ArrayList<EnvStatisticalReportModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请重新登录后再尝试！");
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> listDeviceCode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else {
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listDeviceCode.add(mapModel.getCode());
                    }
                    ;
                } else if (listAreaId != null && listAreaId.size() > 0) {
                    for (Integer areaid : listAreaId) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listDeviceCode.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : list) {
                        listDeviceCode.add(temp);
                    }
                }
            }
            List<String> listThingcode = new ArrayList<String>();
            if (thingCode != null && !thingCode.isEmpty()) {
                listThingcode.add(thingCode);
            } else {
                List<MapModel> listAhr = getAthorityMonitors(listDeviceCode, httpsession);
                for (MapModel mapModel : listAhr) {
                    listThingcode.add(mapModel.getCode());
                }
            }
            if (listDeviceCode != null && listDeviceCode.size() > 0 && listThingcode != null && listThingcode.size() > 0) {
                int count = 0;
                if ("hour".equals(selectType)) {
                    count = envStatisticalReportService.getEsrHourCount(listDeviceCode, listThingcode,
                            DateUtil.StringToTimestampSecond(selectTime + ":00:00"));
                } else if ("day".equals(selectType)) {
                    count = envStatisticalReportService.getEsrDayCount(listDeviceCode, listThingcode,
                            DateUtil.StringToTimestamp(selectTime));
                } else if ("month".equals(selectType)) {
                    count = envStatisticalReportService.getEsrMonthCount(listDeviceCode, listThingcode,
                            DateUtil.StringToTimestamp(selectTime + "-01"));
                } else if ("year".equals(selectType)) {
                    count = envStatisticalReportService.getEsrYearCount(listDeviceCode, listThingcode,
                            DateUtil.StringToTimestamp(selectTime + "-01-01"));
                }
                if (count > 0) {
                    int rowIndex = DefaultArgument.INT_DEFAULT;
                    int rowCount = DefaultArgument.INT_DEFAULT;
                    if ("hour".equals(selectType)) {
                        listEsr = envStatisticalReportService.getEsrHour(listDeviceCode, listThingcode,
                                DateUtil.StringToTimestampSecond(selectTime + ":00:00"), rowIndex, rowCount);
                    } else if ("day".equals(selectType)) {
                        listEsr = envStatisticalReportService.getEsrDay(listDeviceCode, listThingcode,
                                DateUtil.StringToTimestamp(selectTime), rowIndex, rowCount);
                    } else if ("month".equals(selectType)) {
                        listEsr = envStatisticalReportService.getEsrMonth(listDeviceCode, listThingcode,
                                DateUtil.StringToTimestamp(selectTime + "-01"), rowIndex, rowCount);
                    } else if ("year".equals(selectType)) {
                        listEsr = envStatisticalReportService.getEsrYear(listDeviceCode, listThingcode,
                                DateUtil.StringToTimestamp(selectTime + "-01-01"), rowIndex, rowCount);
                    }
                }
                resultListModel.setResult(true);
                resultListModel.setRows(listEsr);
                resultListModel.setTotal(count);
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询年月日时统计报表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：查询时间段统计报表</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月8日下午1:41:49
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getEsrDataTimes", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EnvStatisticalReportModel> getEsrDataTimes(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag, String thingCode,
            String beginTime, String endTime, HttpSession httpsession) {
        ResultListModel<EnvStatisticalReportModel> resultListModel = new ResultListModel<EnvStatisticalReportModel>();
        List<EnvStatisticalReportModel> listEsr = new ArrayList<EnvStatisticalReportModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请重新登录后再尝试！");
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> listDeviceCode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else {
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listDeviceCode.add(mapModel.getCode());
                    }
                    ;
                } else if (listAreaId != null && listAreaId.size() > 0) {
                    for (Integer areaid : listAreaId) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listDeviceCode.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : list) {
                        listDeviceCode.add(temp);
                    }
                }

            }
            List<String> listThingcode = new ArrayList<String>();
            if (thingCode != null && !thingCode.isEmpty()) {
                listThingcode.add(thingCode);
            } else {
                List<MapModel> listAhr = getAthorityMonitors(listDeviceCode, httpsession);
                for (MapModel mapModel : listAhr) {
                    listThingcode.add(mapModel.getCode());
                }
            }
            if (listDeviceCode != null && listDeviceCode.size() > 0 && listThingcode != null && listThingcode.size() > 0) {
                int count = envStatisticalReportService.getEsrTimesCount(listDeviceCode, listThingcode,
                        DateUtil.StringToTimestamp(beginTime), DateUtil.StringToTimestamp(endTime));
                if (count > 0) {
                    int rowIndex = DefaultArgument.INT_DEFAULT;
                    int rowCount = DefaultArgument.INT_DEFAULT;
                    listEsr = envStatisticalReportService.getEsrTimes(listDeviceCode, listThingcode,
                            DateUtil.StringToTimestamp(beginTime), DateUtil.StringToTimestamp(endTime), rowIndex, rowCount);
                }
                resultListModel.setResult(true);
                resultListModel.setRows(listEsr);
                resultListModel.setTotal(count);
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询时间段统计报表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：查询连续监测统计报表</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @param selectType
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月18日上午9:18:01
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getOlrZsData", method = {RequestMethod.POST})
    public @ResponseBody
    List<OnlineMonitorReportTimeModel> getOlrZsData(
            String deviceCode, String thingCode, String selectTime, String selectType, HttpSession httpsession) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && thingCode != null && !thingCode.isEmpty()
                    && selectTime != null && !selectTime.isEmpty()) {
                if ("day".equals(selectType)) {
                    onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsDay(deviceCode, thingCode,
                            selectTime);
                } else if ("month".equals(selectType)) {
                    onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsMonth(deviceCode, thingCode,
                            selectTime);
                } else if ("year".equals(selectType)) {
                    onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsYear(deviceCode, thingCode,
                            selectTime);
                }
                onlineMonitorReportMainModel.setResult(true);
            } else {
                onlineMonitorReportMainModel.setResult(false);
                onlineMonitorReportMainModel.setDetail("参数不能为空！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询连续监测统计报表错误，错误信息为：" + e.getMessage());
        }
        return onlineMonitorReportMainModel.getOmrtModelList();
    }

    /**
     * <p>[功能描述]：查询连续监测统计报表-时间段</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月18日上午9:18:01
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getOlrZsDataTimes", method = {RequestMethod.POST})
    public @ResponseBody
    List<OnlineMonitorReportTimeModel> getOlrZsDataTimes(
            String deviceCode, String thingCode, String beginTime, String endTime, HttpSession httpsession) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && thingCode != null && !thingCode.isEmpty()) {
                onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsTimes(deviceCode, thingCode,
                        beginTime, endTime);
                onlineMonitorReportMainModel.setResult(true);
            } else {
                onlineMonitorReportMainModel.setResult(false);
                onlineMonitorReportMainModel.setDetail("参数不能为空！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询连续监测统计报表错误，错误信息为：" + e.getMessage());
        }
        return onlineMonitorReportMainModel.getOmrtModelList();
    }

    /**
     * <p>[功能描述]：查询连续监测统计报表(包含设备信息)</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param selectTime
     * @param selectType
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月18日下午1:19:44
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getOlrZsDataMain", method = {RequestMethod.POST})
    public @ResponseBody
    OnlineMonitorReportMainModel getOlrZsDataMain(
            String deviceCode, String thingCode, String selectTime, String selectType, HttpSession httpsession) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && thingCode != null && !thingCode.isEmpty()
                    && selectTime != null && !selectTime.isEmpty()) {
                if ("day".equals(selectType)) {
                    onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsDay(deviceCode, thingCode,
                            selectTime);
                } else if ("month".equals(selectType)) {
                    onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsMonth(deviceCode, thingCode,
                            selectTime);
                } else if ("year".equals(selectType)) {
                    onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsYear(deviceCode, thingCode,
                            selectTime);
                }
                onlineMonitorReportMainModel.setResult(true);
            } else {
                onlineMonitorReportMainModel.setResult(false);
                onlineMonitorReportMainModel.setDetail("参数不能为空！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询连续监测统计报表错误，错误信息为：" + e.getMessage());
        }
        return onlineMonitorReportMainModel;
    }

    /**
     * <p>[功能描述]：查询连续监测统计报表(包含设备信息)-时间段</p>
     *
     * @param deviceCode
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月18日下午1:19:44
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getOlrZsDataMainTimes", method = {RequestMethod.POST})
    public @ResponseBody
    OnlineMonitorReportMainModel getOlrZsDataMainTimes(
            String deviceCode, String thingCode, String beginTime, String endTime, HttpSession httpsession) {
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = new OnlineMonitorReportMainModel();
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && thingCode != null && !thingCode.isEmpty()) {
                onlineMonitorReportMainModel = envStatisticalReportService.getOlrZsTimes(deviceCode, thingCode,
                        beginTime, endTime);
                onlineMonitorReportMainModel.setResult(true);
                onlineMonitorReportMainModel.setResult(true);
            } else {
                onlineMonitorReportMainModel.setResult(false);
                onlineMonitorReportMainModel.setDetail("参数不能为空！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询连续监测统计报表错误，错误信息为：" + e.getMessage());
        }
        return onlineMonitorReportMainModel;
    }

    /**
     * <p>[功能描述]：查询报警率统计报表-年月日</p>
     *
     * @param projectId
     * @param levelNo
     * @param convertType
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param selectTime
     * @param dataType
     * @param httpsession
     * @return
     * @author 王垒, 2018年8月6日下午4:00:15
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAlarmRate", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AlarmRateModel> queryAlarmRate(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "levelNo", defaultValue = DefaultArgument.ALARM_LEVEL_NO) String levelNo,
            @RequestParam(value = "convertType", required = false) String convertType,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String thingCode, String selectTime, String updateType, String dataType, HttpSession httpsession) {
        ResultListModel<AlarmRateModel> resultListModel = new ResultListModel<AlarmRateModel>();
        List<AlarmRateModel> armList = new ArrayList<AlarmRateModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请重新登录后再尝试！");
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> deviceCodeList = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else {
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        deviceCodeList.add(mapModel.getCode());
                    }
                    ;
                } else if (listAreaId != null && listAreaId.size() > 0) {
                    for (Integer areaid : listAreaId) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            deviceCodeList.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : list) {
                        deviceCodeList.add(temp);
                    }
                }

            }
            if (deviceCodeList != null && deviceCodeList.size() > 0 && selectTime != null && !selectTime.isEmpty()) {
                if ("day".equals(dataType)) {
                    armList = envStatisticalReportService.getAlarmDayRate(deviceCodeList, thingCode, updateType, levelNo, convertType, selectTime);
                } else if ("month".equals(dataType)) {
                    armList = envStatisticalReportService.getAlarmMonthRate(deviceCodeList, thingCode, updateType, levelNo, convertType, selectTime);
                } else if ("year".equals(dataType)) {
                    armList = envStatisticalReportService.getAlarmYearRate(deviceCodeList, thingCode, updateType, levelNo, convertType, selectTime);
                }
                resultListModel.setResult(true);
                resultListModel.setRows(armList);
                resultListModel.setTotal(armList.size());
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询报警统计报表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：查询报警率统计报表-时间段</p>
     *
     * @param projectId
     * @param levelNo
     * @param convertType
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param httpsession
     * @return
     * @author 王垒, 2018年8月6日下午3:59:43
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAlarmTimesRate", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AlarmRateModel> queryAlarmTimesRate(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "levelNo", defaultValue = DefaultArgument.ALARM_LEVEL_NO) String levelNo,
            @RequestParam(value = "convertType", required = false) String convertType,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String thingCode, String updateType, String beginTime, String endTime, HttpSession httpsession) {
        ResultListModel<AlarmRateModel> resultListModel = new ResultListModel<AlarmRateModel>();
        List<AlarmRateModel> armList = new ArrayList<AlarmRateModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请重新登录后再尝试！");
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> deviceCodeList = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else {
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        deviceCodeList.add(mapModel.getCode());
                    }
                    ;
                } else if (listAreaId != null && listAreaId.size() > 0) {
                    for (Integer areaid : listAreaId) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            deviceCodeList.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : list) {
                        deviceCodeList.add(temp);
                    }
                }

            }
            if (deviceCodeList != null && deviceCodeList.size() > 0
                    && beginTime != null && !beginTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
                armList = envStatisticalReportService.getAlarmTimesRate(deviceCodeList, thingCode, updateType, levelNo, convertType, beginTime, endTime);
                resultListModel.setResult(true);
                resultListModel.setRows(armList);
                resultListModel.setTotal(armList.size());
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询报警统计报表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * 查询超标报警率统计报表-24小时内
     *
     * @param projectId
     * @param levelNo
     * @param convertType
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param updateType
     * @param httpsession
     * @return
     */
    @RequestMapping(value = "/queryExcessTimesRateWithinDay", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AlarmRateModel> queryExcessTimesRateWithinDay(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "levelNo", defaultValue = DefaultArgument.ALARM_LEVEL_NO) String levelNo,
            @RequestParam(value = "convertType", required = false) String convertType,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String thingCode, String updateType, HttpSession httpsession) {
        ResultListModel<AlarmRateModel> resultListModel = new ResultListModel<AlarmRateModel>();
        List<AlarmRateModel> armList = new ArrayList<AlarmRateModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请重新登录后再尝试！");
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> deviceCodeList = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("请至少选择一个监测区域或监测站点！");
                return resultListModel;
            } else {
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, "NT", httpsession);
                    for (MapModel mapModel : listDev) {
                        deviceCodeList.add(mapModel.getCode());
                    }
                    ;
                } else if (listAreaId != null && listAreaId.size() > 0) {
                    for (Integer areaid : listAreaId) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, "NT", httpsession);
                        for (MapModel mapModel : listDev) {
                            deviceCodeList.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : list) {
                        deviceCodeList.add(temp);
                    }
                }

            }
            if (deviceCodeList != null && deviceCodeList.size() > 0) {
                Timestamp beginTime = DateUtil.GetSystemDateTime(24 * 60 * 60 * 1000);
                Timestamp endTime = DateUtil.GetSystemDateTime(0);
                armList = envStatisticalReportService.getAlarmTimesRate(deviceCodeList, thingCode, updateType, levelNo, convertType, beginTime, endTime);
            }
            if (armList == null || armList.size() == 0) {
                AlarmRateModel alarmRateModel = new AlarmRateModel();
                alarmRateModel.setDeviceName("无超标设备");
                alarmRateModel.setAlarmRate("0");
                alarmRateModel.setAlarmCount("0");
                armList.add(alarmRateModel);
            }
            resultListModel.setResult(true);
            resultListModel.setRows(armList);
            resultListModel.setTotal(armList.size());
        } catch (Exception e) {
            logger.error(LOG + ":查询报警统计报表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
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
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                return listMap;
            }
            UserModel loginuser = (UserModel) httpsession
                    .getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            listMap = mapService.getAuthorityMonitors(usercode,
                    DefaultArgument.INT_DEFAULT, null, deviceCodeList);

        } catch (Exception e) {
            logger.error(LOG + ":查询权限监测物数据错误，错误信息为：" + e.getMessage());
        }
        return listMap;
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
        if (SessionManager.isSessionValidate(httpsession,
                DefaultArgument.LOGIN_USER)) {
            return listMapModel;
        }
        UserModel loginuser = (UserModel) httpsession
                .getAttribute(DefaultArgument.LOGIN_USER);
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

}
