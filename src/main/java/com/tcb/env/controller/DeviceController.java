/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.controller;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.model.*;
import com.tcb.env.pojo.*;
import com.tcb.env.util.*;
import org.apache.log4j.Logger;
//import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tcb.env.Handler.WebsocketHandler;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.service.IAlarmService;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.ISysflagService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.service.IVideoDeviceService;

/**
 * <p>
 * [功能描述]：设备控制器
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月28日上午9:46:07
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/DeviceController")
public class DeviceController {
    /**
     * 日志输出标记
     */
    private static final String LOG = "DeviceController";

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeviceController.class);
    /**
     * 声明gson对象
     */
    Gson gson = new Gson();
    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;
    /**
     * 声明device对象</p>
     */
    @Resource
    private IDeviceService deviceService;

    /**
     * 声明查询map服务
     */
    @Resource
    private IMapService mapService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明报警服务
     */
    @Resource
    private IAlarmService alarmService;

    /**
     * 声明系统标识服务
     */
    @Resource
    private ISysflagService sysflagService;

    /**
     * 声明设备摄像头服务
     */
    @Resource
    private IVideoDeviceService videoDeviceService;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    /**
     *
     */
    @Resource
    private WebsocketHandler websocketHandler;

    /**
     * 用来存储session对应的消息,并且用来停止线程
     */
    public static Map<String, MapMonitorData> monitorMap;

    /**
     *  定义同时会有20个人访问百度地图
     *
     * @Param monitorMap
     */
    static {
        monitorMap = new ConcurrentHashMap<>(20);
    }


    /**
     * <p>[功能描述]：查询区域下权限设备</p>
     *
     * @param areaId
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月22日上午11:05:58
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAreaAuthDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<PhoneDeviceModel> queryAreaAuthDevice(
            String areaId, HttpSession httpsession) {
        ResultListModel<PhoneDeviceModel> resultListModel = new ResultListModel<PhoneDeviceModel>();
        List<PhoneDeviceModel> listPhoneDeviceModel = new ArrayList<PhoneDeviceModel>();
        List<Device> listDevice = new ArrayList<Device>();
        String userCode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            userCode = loginuser.getUserCode();
        }
        listDevice = deviceService.getAreaAuthDevice(areaId, userCode);
        for (Device temp : listDevice) {
            PhoneDeviceModel phoneDeviceModell = ConvertPhoneDeviceModel(temp);
            if (phoneDeviceModell != null) {
                listPhoneDeviceModel.add(phoneDeviceModell);
            }
        }
        resultListModel.setRows(listPhoneDeviceModel);
        resultListModel.setTotal(listPhoneDeviceModel.size());
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：获取地图显示数据
     * </p>
     *
     * @param projectId
     * @param list
     * @param levelflag
     * @param nostatus
     * @param select
     * @param maxsize
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月20日上午9:17:25
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceMapData", method = {RequestMethod.POST})
    @ResponseBody
    public ResultAjaxPushModel getDeviceMapData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelflag, String nostatus, String select, int maxsize,
            HttpSession httpsession) {
        /* 存储到monitorMap中 */
        String sessionId = getSessionId(httpsession);
        if (sessionId != null && !"".equals(sessionId)) {
            /* 用来监控数量的请求 */
            MapMonitorData mapMonitorData = new
                    MapMonitorData(list, levelflag, nostatus, select, maxsize, httpsession);
            monitorMap.put(sessionId, mapMonitorData);
        }
        ResultAjaxPushModel resultAjaxPushModel = new ResultAjaxPushModel();
        resultAjaxPushModel.setSelect(select);
        if (SessionManager.isSessionValidate(httpsession,
                DefaultArgument.LOGIN_USER)) {
            return resultAjaxPushModel;
        }
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        List<MapDeviceModel> listMapDev = new ArrayList<MapDeviceModel>();
        if (list != null && list.size() > 0) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return resultAjaxPushModel;
                } else {
                    List<String> listdevicecode = new ArrayList<String>();
                    List<Integer> listareaid = new ArrayList<Integer>();
                    if (levelflag != null && !levelflag.isEmpty()) {
                        listareaid = treeService.getAuthorityBottomArea(projectId,
                                listareaid, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, nostatus);
                        for (TreeModel treeModel : listDev) {
                            if (maxsize > listdevicecode.size()) {
                                listdevicecode.add(treeModel.getId());
                            } else {
                                break;
                            }
                        }
                    } else if (listareaid != null && listareaid.size() > 0) {
                        for (Integer areaid : listareaid) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, nostatus);
                            for (TreeModel treeModel : listDev) {
                                if (maxsize > listdevicecode.size()) {
                                    listdevicecode.add(treeModel.getId());
                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        for (String temp : list) {
                            if (maxsize > listdevicecode.size()) {
                                listdevicecode.add(temp);
                            } else {
                                break;
                            }
                        }
                    }
                    if (listdevicecode == null || listdevicecode.size() == 0) {
                        return resultAjaxPushModel;
                    }
                    listMapDev = deviceService.getMapDevice(listdevicecode, null, null, null,
                            null, null, DefaultArgument.INT_DEFAULT, DefaultArgument.INT_DEFAULT);
                    //设置设备图片
                    if (listMapDev != null && listMapDev.size() > 0) {
                        for (MapDeviceModel mapDeviceModel : listMapDev) {
                            String deviceStatus = mapDeviceModel.getStatusCode();
                            if ("N".equals(deviceStatus)) {
                                mapDeviceModel.setIconCls(DefaultArgument.DEV_NORMAL);
                            } else if ("NT".equals(deviceStatus)) {
                                //判断超标等级
                                String levelNo = alarmService.getAlarmLevel(mapDeviceModel.getDeviceCode());
                                mapDeviceModel.setLevelNo(levelNo);
                                if (!StringUtils.isEmpty(levelNo)) {
                                    switch (levelNo) {
                                        case "1": {
                                            mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM1);
                                            break;
                                        }
                                        case "2": {
                                            mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM2);
                                            break;
                                        }
                                        case "3": {
                                            mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM3);
                                            break;
                                        }
                                        default: {
                                            mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM);
                                        }
                                    }
                                } else {
                                    mapDeviceModel.setIconCls(DefaultArgument.DEV_ALARM);
                                }
                            } else if ("O".equals(deviceStatus) || "Z".equals(deviceStatus)) {
                                mapDeviceModel.setIconCls(DefaultArgument.DEV_UNLINK);
                            } else {
                                mapDeviceModel.setIconCls(DefaultArgument.DEV_FAULT);
                            }
                        }
                    }

                    resultAjaxPushModel.setResult(listMapDev);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询地图信息失败，信息为：" + e.getMessage());
            }
        }
        return resultAjaxPushModel;
    }

    /**
     * <p>
     * [功能描述]：获取报警详细信息
     * </p>
     *
     * @param projectId
     * @param list
     * @param nostatus
     * @param httpsession
     * @return
     * @author 王垒, 2016年5月13日下午4:14:36
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceMapAlarmDetail", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapDeviceModel> getDeviceMapAlarmDetail(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelflag,
            String nostatus, int rows, int page, HttpSession httpsession) {
        ResultListModel<MapDeviceModel> resultListModel = new ResultListModel<MapDeviceModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            List<String> listdevicecode = new ArrayList<String>();
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                List<TreeModel> listDev = treeService.getAuthorityDevices(
                        usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                for (TreeModel treeModel : listDev) {
                    listdevicecode.add(treeModel.getId());
                }
            } else if (levelflag != null && !levelflag.isEmpty()) {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelflag.equals(DefaultArgument.BOTTOM_LEVEL_FALG)) {
                    listareaid.add(Integer.valueOf(list.get(0)));
                } else {
                    listareaid = treeService.getAuthorityBottomArea(projectId,
                            listareaid, Integer.valueOf(list.get(0)), usercode);
                }
                for (Integer areaid : listareaid) {
                    List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, nostatus);
                    for (TreeModel treeModel : listDev) {
                        listdevicecode.add(treeModel.getId());
                    }
                }
            } else {
                for (String temp : list) {
                    listdevicecode.add(temp);
                }
            }
            int count = 0;
            List<MapDeviceModel> listMapDev = new ArrayList<MapDeviceModel>();
            if (listdevicecode != null && listdevicecode.size() > 0) {
                count = deviceService.getMapDeviceCount(listdevicecode, null, nostatus, null, null, null);
                if (count > 0) {
                    listMapDev = deviceService.getMapDevice(listdevicecode, null, nostatus, null, null, null, (page - 1) * rows, rows);
                    // 设置设备状态
                    if (listMapDev != null && listMapDev.size() > 0) {
                        for (MapDeviceModel mapDeviceModel : listMapDev) {
                            if (mapDeviceModel != null) {
//                                if (mapDeviceModel.getStatusInfo() == null || mapDeviceModel.getStatusInfo().isEmpty()
//                                        || mapDeviceModel.getStatusCode() == "NT") {
//
//                                }
                                List<Map> statusInfoList = alarmService.getRecentlyAlarmInfo(mapDeviceModel.getDeviceCode(),
                                        mapDeviceModel.getStatusCode());
                                if (statusInfoList != null && statusInfoList.size() > 0) {
                                    String levelNo = "";
                                    String statusInfo = "";
                                    for (Map<String, Object> statusInfoMap : statusInfoList) {
                                        levelNo = String.valueOf(statusInfoMap.get("levelNo"));
                                        statusInfo += statusInfoMap.get("alarmInfo") + "\r\n";
                                    }
                                    mapDeviceModel.setLevelNo(levelNo);
                                    mapDeviceModel.setStatusInfo(statusInfo);
                                }
                            }
                        }
                        resultListModel.setRows(listMapDev);
                    }
                }
            }
            resultListModel.setTotal(count);
        } catch (Exception e) {
            logger.error(LOG + ":查询报警信息失败，信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * 获取24小时内报警信息
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param status
     * @param noStatus
     * @param rows
     * @param page
     * @param httpsession
     * @return
     */
    @RequestMapping(value = "/getDeviceMapAlarmWithinDay", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapDeviceModel> getDeviceMapAlarmWithinDay(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String status, String noStatus, int rows, int page, HttpSession httpsession) {
        ResultListModel<MapDeviceModel> resultListModel = new ResultListModel<MapDeviceModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            List<String> listdevicecode = new ArrayList<String>();
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                List<TreeModel> listDev = treeService.getAuthorityDevices(
                        usercode, projectId, DefaultArgument.INT_DEFAULT, null, status, noStatus);
                for (TreeModel treeModel : listDev) {
                    if ("N".equals(treeModel.getState())) {
                        //筛选掉正常设备
                        continue;
                    } else {
                        listdevicecode.add(treeModel.getId());
                    }
                }
            } else if (levelFlag != null && !levelFlag.isEmpty()) {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag.equals(DefaultArgument.BOTTOM_LEVEL_FALG)) {
                    listareaid.add(Integer.valueOf(list.get(0)));
                } else {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid, Integer.valueOf(list.get(0)), usercode);
                }
                for (Integer areaid : listareaid) {
                    List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, status, noStatus);
                    for (TreeModel treeModel : listDev) {
                        if ("N".equals(treeModel.getState())) {
                            //筛选掉正常设备
                            continue;
                        } else {
                            listdevicecode.add(treeModel.getId());
                        }
                    }
                }
            } else {
                for (String temp : list) {
                    listdevicecode.add(temp);
                }
            }
            int count = 0;
            List<MapDeviceModel> listMapDev = new ArrayList<MapDeviceModel>();
            if (listdevicecode != null && listdevicecode.size() > 0) {
                Timestamp beginAlarmTime = DateUtil.GetSystemDateTime(24 * 60 * 60 * 1000);
                Timestamp endAlarmTime = DateUtil.GetSystemDateTime(0);
                count = deviceService.getMapDeviceCount(listdevicecode, status, noStatus, beginAlarmTime, endAlarmTime, null);
                if (count > 0) {
                    listMapDev = deviceService.getMapDevice(listdevicecode, status, noStatus, beginAlarmTime, endAlarmTime, null, (page - 1) * rows, rows);
                    // 设置设备状态
                    if (listMapDev != null && listMapDev.size() > 0) {
                        for (MapDeviceModel mapDeviceModel : listMapDev) {
                            if (mapDeviceModel != null) {
                                List<Map> statusInfoList = alarmService.getRecentlyAlarmInfo(mapDeviceModel.getDeviceCode(),
                                        mapDeviceModel.getStatusCode());
                                if (statusInfoList != null && statusInfoList.size() > 0) {
                                    String levelNo = "";
                                    String statusInfo = "";
                                    for (Map<String, Object> statusInfoMap : statusInfoList) {
                                        levelNo = String.valueOf(statusInfoMap.get("levelNo"));
                                        statusInfo += statusInfoMap.get("alarmInfo") + "\r\n";
                                    }
                                    mapDeviceModel.setLevelNo(levelNo);
                                    mapDeviceModel.setStatusInfo(statusInfo);
                                }
                            }
                        }
                        resultListModel.setRows(listMapDev);
                    }
                }
            }
            resultListModel.setTotal(listMapDev.size());
        } catch (Exception e) {
            logger.error(LOG + ":查询报警信息失败，信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：获取地图报警显示数据
     * </p>
     *
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月20日上午9:17:25
     * @since EnvDust 1.0.0
     */
    public ResultAjaxPushModel getDeviceMapAlarmData(
            List<String> listdevicecode, String nostatus, String select, int maxsize, HttpSession httpsession) {
        ResultAjaxPushModel resultAjaxPushModel = new ResultAjaxPushModel();
        resultAjaxPushModel.setSelect(select);
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultAjaxPushModel;
        }
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        List<MapDeviceModel> listMapDev = new ArrayList<MapDeviceModel>();
        if (listdevicecode != null && listdevicecode.size() > 0) {
            try {
                listMapDev = deviceService.getMapAlarmDevice(listdevicecode, null);
                resultAjaxPushModel.setResult(listMapDev);
            } catch (Exception e) {
                logger.error(LOG + ":查询地图信息失败，信息为：" + e.getMessage());
            } finally {
                loginuser.setAlarmUpdateTime(DateUtil.StringToTimestampSecond(sysflagService
                        .getSysFlagValue(DefaultArgument.ALARM_UPD)));// 赋值本次查询时间，以便于下次查询
            }
        }
        return resultAjaxPushModel;
    }

    /**
     * <p>
     * [功能描述]：新增设备
     * </p>
     *
     * @param deviceModel
     * @param httpsession
     * @return
     * @author 任崇彬, 2016年3月28日上午11:51:39
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertDevice(DeviceModel deviceModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        Device device = new Device();
        if (deviceModel != null) {
            try {
                device.setDeviceCode(deviceModel.getDeviceCode());
                device.setDeviceMn(deviceModel.getDeviceMn());
                if (deviceService.getCount(device) == 0) {
                    device = ConvertDevice(deviceModel, httpsession);
                    // 创建表
                    boolean flag = deviceService.createStorageTable(device);
                    // 插入数据
                    int intresult = 0;
                    if (flag) {
                        intresult = deviceService.insertDevice(device);
                    }
                    if (intresult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("新增设备成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增设备失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此设备编号或MN号，请使用其他设备编号或MN号！");
                }

            } catch (Exception e) {
                // 删除表
                try {
                    deviceService.dropStorageTable(device);
                } catch (Exception e1) {
                    logger.error("删除新建表失败，原因：" + e1.getMessage());
                } finally {
                    resultModel.setResult(false);
                    resultModel.setDetail("新增设备失败!");
                    logger.error(LOG + ":" + e.getMessage());
                }
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：查询设备
     * </p>
     *
     * @param deviceModel
     * @param httpsession
     * @return
     * @author 任崇彬, 2016年3月29日上午10:44:57
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceModel> queryDevice(DeviceModel deviceModel, HttpSession httpsession) {
        ResultListModel<DeviceModel> resultListModel = new ResultListModel<DeviceModel>();
        List<DeviceModel> listDeviceModel = new ArrayList<DeviceModel>();
        List<Device> listDevice = new ArrayList<Device>();
        Device device = ConvertDevice(deviceModel, httpsession);
        int count = deviceService.getCount(device);

        if (count > 0) {
            listDevice = deviceService.getDevice(device);
            for (Device temp : listDevice) {
                DeviceModel deviceModell = ConvertDeviceModel(temp);
                if (deviceModell != null) {
                    listDeviceModel.add(deviceModell);
                }
            }
            resultListModel.setRows(listDeviceModel);
        }
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * 查询设备气路状态
     *
     * @param deviceCode
     * @param httpsession
     * @return
     */
    @RequestMapping(value = "/getGapPath", method = {RequestMethod.POST})
    @ResponseBody
    public ResultDataModel getGapPath(String deviceCode, HttpSession httpsession) {
        ResultDataModel resultListModel = new ResultDataModel();
        GasPathModel gasPathModel = deviceService.getGapPath(deviceCode);
        if (Objects.nonNull(gasPathModel)) {
            String gasPath = gasPathModel.getGasPath();
            String gasPathName = EnumUtil.GasPath.getName(gasPath);
            gasPathModel.setGasPathName(gasPathName);
            resultListModel.setData(gasPathModel);
            resultListModel.setResult(true);
        } else {
            resultListModel.setResult(false);
        }
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：删除设备
     * </p>
     *
     * @param list
     * @return
     * @author 任崇彬, 2016年3月29日上午11:14:19
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteDevice(@RequestParam(value = "list[]") List<Integer> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listid = new ArrayList<Integer>();
                for (Integer userid : list) {
                    listid.add(userid);
                }
                List<Device> listDevice = new ArrayList<Device>();
                for (Integer id : listid) {
                    Device device = new Device();
                    device.setDeviceId(id);
                    device.setInspectTime(DateUtil.GetSystemDateTime(0));
                    List<Device> listtemp = deviceService.getDevice(device);
                    for (Device devicetemp : listtemp) {
                        listDevice.add(devicetemp);
                    }
                }
                int intresult = deviceService.deleteDevice(listid);
                if (intresult > 0) {
                    for (Device device : listDevice) {
                        try {
                            if (device != null) {
                                //删除报警信息
                                alarmService.deleteDeviceAlarm(device.getDeviceCode());
                                //删除设备表
                                deviceService.dropStorageTable(device);
                            }
                        } catch (Exception ed) {
                            logger.error(LOG + "：删除设备表失败，设备表为：" + device.getDeviceCode());
                            continue;
                        }
                    }
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除设备失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除设备失败！");
                logger.error(LOG + ":" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除设备失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：更新设备
     * </p>
     *
     * @param deviceModel
     * @param httpsession
     * @return
     * @author 任崇彬, 2016年3月30日上午9:00:50
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/updateDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDevice(DeviceModel deviceModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (deviceModel != null) {
            try {
                int codeExist = deviceService.getDeviceCodeExist(
                        deviceModel.getDeviceId(), deviceModel.getDeviceCode());
                int mnExist = deviceService.getDeviceMnExist(
                        deviceModel.getDeviceId(), deviceModel.getDeviceMn());
                // 如果两个都等于0说明不存在则可以修改
                if (codeExist == 0 && mnExist == 0) {
                    List<Device> listdevice = new ArrayList<Device>();
                    listdevice.add(ConvertDevice(deviceModel, httpsession));
                    int intresult = deviceService.updateDevice(listdevice);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新设备失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此设备编号或MN号，请使用其他设备编号或MN号！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新设备失败!");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：更新站点地图位置</p>
     *
     * @param deviceModel
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月10日上午11:48:35
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/updateDeviceLocation", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDeviceLocation(DeviceModel deviceModel, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (deviceModel != null) {
            try {
                String deviceCode = deviceModel.getDeviceCode();
                String deviceX = String.valueOf(deviceModel.getDeviceX());
                String deviceY = String.valueOf(deviceModel.getDeviceY());
                if (deviceX != null && !deviceX.isEmpty() && deviceY != null && !deviceY.isEmpty()) {
                    int intresult = deviceService.updateDeviceLocation(deviceCode, deviceX, deviceY);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("更新站点地图位置成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新站点地图位置失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("服务器未接收到更新数据，请重新登陆后再尝试！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新站点失败!");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：下拉框查询，设备厂商code-name
     * </p>
     *
     * @param mfrCode
     * @return
     * @author 任崇彬, 2016年3月29日上午8:43:34
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDevicemfrCodeDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDevicemfrCodeDropDown(String mfrCode) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceMfrCode(mfrCode);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：下拉框查询，设备状态code-name
     * </p>
     *
     * @param statusCode
     * @return
     * @author 任崇彬, 2016年3月29日上午8:45:14
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDevicestatusCodeDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDevicestatusCodeDropDown(String statusCode) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceStatusCode(statusCode);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：下拉框查询，设备区域id-name
     * </p>
     *
     * @param id
     * @param levelFlag
     * @return
     * @author 任崇彬, 2016年3月29日上午9:00:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDeviceAreaDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDeviceAreaDropDown(int id, int levelFlag) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getAreaMap(id, Integer.valueOf(DefaultArgument.BOTTOM_LEVEL_FALG));
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：下拉框查询，负责人id-name
     * </p>
     *
     * @param devicePrinciple
     * @return
     * @author 任崇彬, 2016年3月31日上午11:34:56
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDevicePrincipleDropDown", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDevicePrincipleDropDown(int devicePrinciple) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceprinciple(devicePrinciple);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：下拉框查询，监督单位id-name
     * </p>
     *
     * @param orgId
     * @return
     * @author 任崇彬, 2016年3月31日上午11:50:13
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDeviceOversightUnit", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapModel> queryDeviceOversightUnit(int orgId) {
        ResultListModel<MapModel> resultListModel = new ResultListModel<MapModel>();
        List<MapModel> listmapmodel = new ArrayList<MapModel>();
        listmapmodel = mapService.getDeviceOversightUnit(orgId);
        resultListModel.setTotal(listmapmodel.size());
        resultListModel.setRows(listmapmodel);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：model转成device
     * </p>
     *
     * @param deviceModel
     * @param httpsession
     * @return
     * @author 任崇彬, 2016年3月28日上午11:05:57
     * @since EnvDust 1.0.0
     */
    private Device ConvertDevice(DeviceModel deviceModel, HttpSession httpsession) {
        Device device = new Device();
        if (deviceModel != null) {
            device.setDeviceId(deviceModel.getDeviceId());
            device.setDeviceCode(deviceModel.getDeviceCode());
            device.setDeviceMn(deviceModel.getDeviceMn());
            device.setDeviceName(deviceModel.getDeviceName());
            device.setDeviceIp(deviceModel.getDeviceIp());
            device.setDevicePort(deviceModel.getDevicePort());
            device.setDevicePwd(deviceModel.getDevicePwd());
            device.setDeviceX(deviceModel.getDeviceX());
            device.setDeviceY(deviceModel.getDeviceY());
            device.setSystemVersion(deviceModel.getSystemVersion());
            device.setInspectTime(DateUtil.StringToTimestamp(deviceModel.getInspectTime()));
            device.setBuildFirm(deviceModel.getBuildFirm());
            device.setDeviceAddress(deviceModel.getDeviceAddress());
            // 设置外键mfrcode
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setMfrCode(deviceModel.getMfrCode());
            device.setManufacturer(manufacturer);
            // 设置外键，statuscode
            Status status = new Status();
            status.setStatusCode(deviceModel.getStatusCode());
            device.setStatus(status);
            // 设置外键，areaid。查看区域，区域是否null || empty
            Area area = new Area();
            if (deviceModel.getAreaId() != null && !deviceModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(deviceModel.getAreaId()));
            }
            device.setArea(area);
            // 设置外键，device_principal. 查看  负责人  是否存在
            User user = new User();
            String userId = deviceModel.getUserId();
            if (userId == null || userId.isEmpty()) {//不存在直接使用默认值-1
                user.setUserId(DefaultArgument.INT_DEFAULT);
            } else {//存在直接赋值
                user.setUserId(Integer.valueOf(deviceModel.getUserId()));
            }
            device.setUser(user);
            // 设置外键，oversight_unit.查看监督单位是否存在，同上
            Oranization oranization = new Oranization();
            String orgId = deviceModel.getOrgId();
            if (orgId == null || orgId.isEmpty()) {
                oranization.setOrgId(DefaultArgument.INT_DEFAULT);
            } else {
                oranization.setOrgId(Integer.valueOf(deviceModel.getOrgId()));
            }
            device.setOranization(oranization);
            //设置外键，project_id.设备项目
            DeviceProject deviceProject = new DeviceProject();
            String projectId = deviceModel.getProjectId();
            if (!StringUtils.isEmpty(projectId)) {
                deviceProject.setProjectId(Integer.parseInt(projectId));
                deviceProject.setProjectCode(deviceModel.getProjectCode());
                deviceProject.setProjectName(deviceModel.getProjectName());
                if (!StringUtils.isEmpty(deviceModel.getProjectOrder())) {
                    deviceProject.setProjectId(Integer.parseInt(deviceModel.getProjectOrder()));
                }
            }
            device.setDeviceProject(deviceProject);
            // 设置设备辐射面积
            device.setDeviceKm(deviceModel.getDeviceKm());
            //设置平台统计
            device.setStaMinute(deviceModel.getStaMinute());
            device.setStaHour(deviceModel.getStaHour());
            device.setStaDay(deviceModel.getStaDay());
            //强制数据回复标识
            if (!DefaultArgument.NONE_DEFAULT.equals(deviceModel.getReplyFlag())) {
                device.setReplyFlag(deviceModel.getReplyFlag());
            }
            device.setForceReply(deviceModel.getForceReply());
            device.setSmsFlag(deviceModel.getSmsFlag());
            device.setStaFlow(deviceModel.getStaFlow());
            device.setPipeArea(deviceModel.getPipeArea());
            if (!StringUtils.isEmpty(deviceModel.getHourCount())) {
                device.setHourCount(Integer.valueOf(deviceModel.getHourCount()));
            }
            // 获取操作者
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                device.setOptUser(loginuser.getUserId());
            } else {
                device.setOptUser(deviceModel.getDeviceId());
            }
            device.setRowCount(deviceModel.getRows());
            device.setRowIndex((deviceModel.getPage() - 1) * deviceModel.getRows());
        }
        return device;
    }

    /**
     * <p>
     * [功能描述]：device转成model
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月28日上午11:49:23
     * @since EnvDust 1.0.0
     */
    private DeviceModel ConvertDeviceModel(Device device) {
        DeviceModel deviceModel = new DeviceModel();
        if (device != null) {
            deviceModel.setDeviceId(device.getDeviceId());
            deviceModel.setDeviceCode(device.getDeviceCode());
            deviceModel.setDeviceMn(device.getDeviceMn());
            deviceModel.setDeviceName(device.getDeviceName());
            deviceModel.setDeviceIp(device.getDeviceIp());
            deviceModel.setDevicePort(device.getDevicePort());
            deviceModel.setDevicePwd(device.getDevicePwd());
            deviceModel.setDeviceX(device.getDeviceX());
            deviceModel.setDeviceY(device.getDeviceY());
            deviceModel.setSystemVersion(device.getSystemVersion());
            if (device.getInspectTime() != null) {
                deviceModel.setInspectTime(String.valueOf(device.getInspectTime()));
            }
            deviceModel.setBuildFirm(device.getBuildFirm());
            deviceModel.setDeviceAddress(device.getDeviceAddress());
            // 外键mfr
            deviceModel.setMfrCode(device.getManufacturer().getMfrCode());
            deviceModel.setMfrName(device.getManufacturer().getMfrName());
            // 外键status
            if (device.getStatus() != null) {
                deviceModel.setStatusCode(device.getStatus().getStatusCode());
                deviceModel.setStatusName(device.getStatus().getStatusName());
            }
            // 外键area
            deviceModel.setAreaId(String.valueOf(device.getArea().getAreaId()));
            deviceModel.setAreaName(device.getArea().getAreaName());
            // 外键user
            int userId = device.getUser().getUserId();
            if (userId != DefaultArgument.INT_DEFAULT) {
                deviceModel.setUserId(String.valueOf(userId));
                deviceModel.setUserName(device.getUser().getUserName());
                deviceModel.setUserTel(device.getUser().getUserTel());
                deviceModel.setUserRemark(device.getUser().getUserRemark());
            }
            // 外键ORG
            int orgId = device.getOranization().getOrgId();
            if (orgId != DefaultArgument.INT_DEFAULT) {
                deviceModel.setOrgId(String.valueOf(orgId));
                deviceModel.setOrgName(device.getOranization().getOrgName());
                deviceModel.setOrgLiaison(device.getOranization().getOrgLiaison());
            }
            //外键Project
            DeviceProject deviceProject = device.getDeviceProject();
            if (deviceProject != null) {
                deviceModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                deviceModel.setProjectCode(deviceProject.getProjectCode());
                deviceModel.setProjectName(deviceProject.getProjectName());
                deviceModel.setProjectOrder(String.valueOf(deviceProject.getProjectOrder()));
            }
            // 设置设备辐射面积
            deviceModel.setDeviceKm(device.getDeviceKm());
            //设置平台统计
            deviceModel.setStaMinute(device.getStaMinute());
            deviceModel.setStaHour(device.getStaHour());
            deviceModel.setStaDay(device.getStaDay());
            //前置数据回复标识
            if (device.getReplyFlag() != null && !device.getReplyFlag().isEmpty()) {
                deviceModel.setReplyFlag(device.getReplyFlag());
            } else {
                deviceModel.setReplyFlag(DefaultArgument.NONE_DEFAULT);
            }
            deviceModel.setForceReply(device.getForceReply());
            deviceModel.setSmsFlag(device.getSmsFlag());
            deviceModel.setStaFlow(device.getStaFlow());
            deviceModel.setPipeArea(device.getPipeArea());
            deviceModel.setHourCount(String.valueOf(device.getHourCount()));
            // 操作者
            deviceModel.setOptUserName(userService.getUserNameById(device.getOptUser(), null));
            deviceModel.setOptTime(DateUtil.TimestampToString(device.getOptTime(), DateUtil.DATA_TIME));
        }
        return deviceModel;
    }

    /**
     * <p>
     * [功能描述]：Device转成PhoneDeviceModel
     * </p>
     *
     * @param device
     * @return
     * @author 任崇彬, 2016年3月28日上午11:49:23
     * @since EnvDust 1.0.0
     */
    private PhoneDeviceModel ConvertPhoneDeviceModel(Device device) {
        PhoneDeviceModel phoneDeviceModel = new PhoneDeviceModel();
        if (device != null) {
            phoneDeviceModel.setDeviceId(device.getDeviceId());
            phoneDeviceModel.setDeviceCode(device.getDeviceCode());
            phoneDeviceModel.setDeviceMn(device.getDeviceMn());
            phoneDeviceModel.setDeviceName(device.getDeviceName());
            // 操作者
            phoneDeviceModel.setOptUserName(userService.getUserNameById(device.getOptUser(), null));
            phoneDeviceModel.setOptTime(DateUtil.TimestampToString(device.getOptTime(), DateUtil.DATA_TIME));
        }
        return phoneDeviceModel;
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
    public List<MapModel> getAthorityMonitors(List<String> deviceCodeList, HttpSession httpsession) {
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
     * <p>[功能描述]：获取设备摄像头URL（VPNURL）</p>
     *
     * @param deviceMn
     * @param httpsession
     * @return
     * @author 王垒, 2016年11月22日下午3:32:14
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getVideoDeviceUrlByMn", method = {RequestMethod.POST})
    @ResponseBody
    @Deprecated
    public ResultModel getVideoDeviceUrlByMn(String deviceMn, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (SessionManager.isSessionValidate(httpsession,
                DefaultArgument.LOGIN_USER)) {
            return resultModel;
        } else {
            if (deviceMn != null && !deviceMn.isEmpty()) {
                String deviceIP = videoDeviceService.getVideoDeviceIpByMn(deviceMn);
                if (deviceIP != null && !deviceIP.isEmpty()) {
                    resultModel.setResult(true);
                    String url = "http://" + deviceIP + ":8080";
                    resultModel.setDetail(url);
                }
            }
        }
        return resultModel;

    }

    /**
     * <p>[功能描述]：获取设备摄像头URL（摄像头URL）</p>
     *
     * @param deviceMn
     * @param httpsession
     * @return
     * @author 王垒, 2016年11月22日下午3:32:14
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getVideoDeviceProxyUrlByMn", method = {RequestMethod.POST})
    @ResponseBody
    @Deprecated
    public ResultModel getVideoDeviceProxyUrlByMn(String deviceMn, HttpSession httpsession) {

        int videoFlag = dom4jConfig.getDeDevConfig().getVideoFlag();
        ResultModel resultModel = new ResultModel();
        if (SessionManager.isSessionValidate(httpsession,
                DefaultArgument.LOGIN_USER) || videoFlag == 0) {
            return resultModel;
        } else {
            if (deviceMn != null && !deviceMn.isEmpty()) {

                String deviceIP = videoDeviceService.getVideoDeviceIpByMn(deviceMn);
                if (deviceIP != null && !deviceIP.isEmpty()) {
                    resultModel.setResult(true);
                    String url = "";
                    if (videoFlag == 1) {
                        url = "rtsp://admin:tjtcb712@" + deviceIP + ":554/h264/ch1/main/av_stream";
                    } else if (videoFlag == 2) {
                        String proxyUrl = "RTSP://172.17.80.217/EasyRelayModule?name=" + deviceMn;
                        url = proxyUrl + "&url=" + "'rtsp://admin:tjtcb712@" + deviceIP + ":554/h264/ch1/main/av_stream'";
                    }

                    resultModel.setDetail(url);
                }
            }
        }
        return resultModel;

    }

    /**
     * <p>
     * [功能描述]：获取热力图显示数据
     * </p>
     *
     * @param projectId
     * @param list
     * @param levelflag
     * @param dataType
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param wFlag
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月20日上午9:17:25
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getThermodynamicData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, List<ThermodynamicModel>> getThermodynamicData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelflag,
            String dataType, String thingCode, String beginTime, String endTime, boolean wFlag,
            HttpSession httpsession) {
        Map<String, List<ThermodynamicModel>> mapList = new TreeMap<String, List<ThermodynamicModel>>();
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return mapList;
                } else {
                    List<String> listdevicecode = new ArrayList<String>();
                    List<Integer> listareaid = new ArrayList<Integer>();
                    if (levelflag != null && !levelflag.isEmpty()) {
                        listareaid = treeService.getAuthorityBottomArea(projectId,
                                listareaid, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                        for (TreeModel treeModel : listDev) {
                            listdevicecode.add(treeModel.getId());
                        }
                    } else if (listareaid != null && listareaid.size() > 0) {
                        for (Integer areaid : listareaid) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, null);
                            for (TreeModel treeModel : listDev) {
                                listdevicecode.add(treeModel.getId());
                            }
                        }
                    } else {
                        for (String temp : list) {
                            listdevicecode.add(temp);
                        }
                    }
                    if (listdevicecode == null || listdevicecode.size() == 0) {
                        return mapList;
                    }
                    mapList = deviceService.getThermodynamic(listdevicecode, dataType, thingCode, beginTime, endTime, wFlag);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询热力图信息失败，信息为：" + e.getMessage());
            }
        }
        return mapList;
    }

    /**
     * <p>[功能描述]：获取热力图最近显示数据</p>
     *
     * @param projectId
     * @param list
     * @param levelflag
     * @param dataType
     * @param thingCode
     * @param httpsession
     * @return
     * @author 王垒, 2017年8月16日下午4:45:59
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getThermRecently", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, List<ThermodynamicModel>> getThermRecently(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelflag, String dataType, String thingCode,
            HttpSession httpsession) {

        Map<String, List<ThermodynamicModel>> mapList = new TreeMap<String, List<ThermodynamicModel>>();
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return mapList;
                } else {
                    List<String> listdevicecode = new ArrayList<String>();
                    List<Integer> listareaid = new ArrayList<Integer>();
                    if (levelflag != null && !levelflag.isEmpty()) {
                        listareaid = treeService.getAuthorityBottomArea(projectId,
                                listareaid, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                        for (TreeModel treeModel : listDev) {
                            listdevicecode.add(treeModel.getId());
                        }
                    } else if (listareaid != null && listareaid.size() > 0) {
                        for (Integer areaid : listareaid) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, null);
                            for (TreeModel treeModel : listDev) {
                                listdevicecode.add(treeModel.getId());
                            }
                        }
                    } else {
                        for (String temp : list) {
                            listdevicecode.add(temp);
                        }
                    }
                    if (listdevicecode == null || listdevicecode.size() == 0) {
                        return mapList;
                    }
                    mapList = deviceService.getThermRecently(listdevicecode, thingCode);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询热力图最近显示数据失败，信息为：" + e.getMessage());
            }
        }
        return mapList;
    }

    /**
     * <p>[功能描述]：查询扩散数据</p>
     *
     * @param projectId
     * @param list
     * @param levelflag
     * @param dataType
     * @param thingCode
     * @param cloud
     * @param count
     * @param httpsession
     * @return
     * @author 王垒, 2017年9月4日下午2:54:34
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getThermSpread", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, List<ThermodynamicModel>> getThermSpread(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelflag, String dataType, String thingCode, int cloud, int count,
            HttpSession httpsession) {
        Map<String, List<ThermodynamicModel>> mapList = new TreeMap<String, List<ThermodynamicModel>>();
        Map<String, List<LocationModel>> tempMapList = new TreeMap<String, List<LocationModel>>();
        List<LocationModel> allLocationList = new ArrayList<LocationModel>();
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return mapList;
                } else {
                    List<String> listdevicecode = new ArrayList<String>();
                    List<Integer> listareaid = new ArrayList<Integer>();
                    if (levelflag != null && !levelflag.isEmpty()) {
                        listareaid = treeService.getAuthorityBottomArea(projectId,
                                listareaid, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService
                                .getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                        for (TreeModel treeModel : listDev) {
                            listdevicecode.add(treeModel.getId());
                        }
                    } else if (listareaid != null && listareaid.size() > 0) {
                        for (Integer areaid : listareaid) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, null);
                            for (TreeModel treeModel : listDev) {
                                listdevicecode.add(treeModel.getId());
                            }
                        }
                    } else {
                        for (String temp : list) {
                            listdevicecode.add(temp);
                        }
                    }
                    if (listdevicecode == null || listdevicecode.size() == 0) {
                        return mapList;
                    }
                    tempMapList = deviceService.getSpreadRecently(listdevicecode, "WD", "WS", thingCode);
                    if (tempMapList != null && tempMapList.size() > 0) {
                        for (Map.Entry<String, List<LocationModel>> tempEntry : tempMapList.entrySet()) {
                            String hour = "";
                            String dateTime = tempEntry.getKey();
                            if (dateTime != null && dateTime.indexOf(" ") >= 0 && dateTime.length() >= (dateTime.indexOf(" ") + 2)) {
                                hour = dateTime.substring(dateTime.indexOf(" ") + 1, dateTime.indexOf(" ") + 3);
                            }
                            //60秒追加一次数据，60秒=count=1
                            List<LocationModel> spreadLocationModel = tempEntry.getValue();
                            if (spreadLocationModel != null && spreadLocationModel.size() > 0) {

                                allLocationList.addAll(spreadLocationModel);
                                int n = count;
                                for (int i = 0; i < n; i++) {
                                    for (LocationModel locationModel : spreadLocationModel) {
                                        LocationModel locationModelNew = OriginSpreadLocation.processSpread(locationModel, Integer.valueOf(hour), 60 * (i + 1), cloud);
                                        if (locationModelNew != null) {
                                            allLocationList.add(locationModelNew);
                                        }
                                    }
                                }
                                //装入结果
                                List<ThermodynamicModel> listThermodynamicModel = new ArrayList<ThermodynamicModel>();
                                for (LocationModel locationModel : allLocationList) {
                                    ThermodynamicModel thermodynamicModel = new ThermodynamicModel();
                                    thermodynamicModel.setLat(locationModel.getLat());
                                    thermodynamicModel.setLng(locationModel.getLng());
                                    thermodynamicModel.setCount(locationModel.getVal());
                                    listThermodynamicModel.add(thermodynamicModel);
                                }
                                if (!mapList.containsKey(tempEntry.getKey())) {
                                    mapList.put(tempEntry.getKey(), listThermodynamicModel);
                                } else {
                                    mapList.get(tempEntry.getKey()).addAll(listThermodynamicModel);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询扩散显示数据失败，信息为：" + e.getMessage());
            }
        }
        return mapList;
    }

    @RequestMapping(value = "/getAreaStatisticData", method = {RequestMethod.POST})
    @ResponseBody
    public List<AreaStatisticModel> getAreaStatisticData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelflag, String dataType, String thingCode, String beginTime, String endTime,
            String thingUtil, HttpSession httpsession) {
        List<AreaStatisticModel> asmList = new ArrayList<AreaStatisticModel>();
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return asmList;
                } else {
                    List<Integer> listAreaId = new ArrayList<Integer>();
                    if (levelflag != null && !levelflag.isEmpty()) {
                        listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(list.get(0)), usercode);
                    } else if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        listAreaId = treeService.getAuthorityAreaId(usercode);
                    }
                    List<AreaStatisticModel> asmTempList = new ArrayList<AreaStatisticModel>();
                    int totalDis = 0;
                    for (Integer areaId : listAreaId) {
                        AreaStatisticModel areaStatisticModel = deviceService.getAreaValue(areaId, dataType, thingCode, beginTime, endTime);
                        if (areaStatisticModel != null) {
                            double totalValue = Double.valueOf(areaStatisticModel.getTotalValue());
                            totalDis += totalValue;
                        }
                        asmTempList.add(areaStatisticModel);
                    }
                    //赋值排放比例
                    for (AreaStatisticModel temp : asmTempList) {
                        double totalValueTemp = Double.valueOf(temp.getTotalValue());
                        double disRatioTemp = 0;
                        if (totalDis > 0) {
                            disRatioTemp = totalValueTemp / totalDis * 100;
                        }
                        temp.setDisRatio(String.format("%.2f", disRatioTemp) + " %");
                        temp.setAvgValue(temp.getAvgValue() + " " + thingUtil);
                        double utilConvertRate = 1;
                        if (thingUtil != null && thingUtil.equals("mg/m3")) {
                            utilConvertRate = 1000 * 1000;
                            temp.setTotalValue(String.format("%.2f", totalValueTemp / utilConvertRate) + " kg");//mg->kg
                        } else if (thingUtil != null && thingUtil.equals("ug/m3")) {
                            utilConvertRate = 1000 * 1000;
                            temp.setTotalValue(String.format("%.2f", totalValueTemp / utilConvertRate) + " g");//mg->g
                        } else {
                            temp.setTotalValue(String.format("%.2f", totalValueTemp / utilConvertRate));//不转换
                        }

                        //排序存入
                        asmList.add(temp);
                    }
                    SortListUtil<AreaStatisticModel> sortList = new SortListUtil<AreaStatisticModel>();
                    sortList.sort(asmList, "getDisRatio", "desc");
                }
            } catch (Exception e) {
                logger.error(LOG + ":获取工地园区排放统计信息数据失败，信息为：" + e.getMessage());
            }
        }
        return asmList;
    }

    /**
     * <p>[功能描述]：用来得到session的sessionId</p>
     *
     * @author 王坤, 2018年9月5日 下午15:44:34
     * @since EnvDust 1.0.0
     */
    public String getSessionId(HttpSession httpSession) {
        String sessionId = null;
        if (httpSession != null) {
            sessionId = httpSession.getId();
        }
        return sessionId;
    }

    /**
     * <p>[功能描述]：用来剔除监控map的sessionId</p>
     *
     * @author 王坤, 2018年9月5日 下午16:02:34
     * @since EnvDust 1.0.0
     */
    public void removeSessionId(String sessionId) {
        if (sessionId != null && !sessionId.isEmpty() && monitorMap.containsKey(sessionId)) {
            monitorMap.remove(sessionId);
        }
    }
}