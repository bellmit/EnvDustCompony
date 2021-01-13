package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.pojo.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.DeviceAlarmSetModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IDeviceAlarmSetService;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.IMonitorService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>
 * [功能描述]：
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午10:37:09
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/DeviceAlarmSetController")
public class DeviceAlarmSetController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AuthorityDetailController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeviceAlarmSetController.class);

    /**
     * 声明报警门限服务类
     */
    @Resource
    private IDeviceAlarmSetService deviceAlarmSetService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明查询map服务
     */
    @Resource
    private IMapService mapService;

    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    /**
     * 声明Monitor服务
     */
    @Resource
    private IMonitorService monitorService;

    /**
     * 声明设备服务
     */
    @Resource
    private IDeviceService deviceService;

    /**
     * <p>
     * [功能描述]：报警门限信息
     * </p>
     *
     * @param projectId
     * @param deviceAlarmSetModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月1日上午8:45:24
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceAlarmSet", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<DeviceAlarmSetModel> getDeviceAlarmSet(
            @RequestParam(value = "projectId", required = false) String projectId,
            DeviceAlarmSetModel deviceAlarmSetModel, HttpSession httpsession) {
        ResultListModel<DeviceAlarmSetModel> resultListModel = new ResultListModel<DeviceAlarmSetModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            DeviceAlarmSet deviceAlarmSet = ConvertDeviceAlarmSet(deviceAlarmSetModel, httpsession);
            List<String> listdevicecode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId,
                    DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listdevicecode.add(treeModel.getId());
            }
            if (listdevicecode != null && listdevicecode.size() > 0) {
                List<MapModel> listMap = mapService.getAuthorityMonitors(usercode,
                        DefaultArgument.INT_DEFAULT, null, listdevicecode);
                List<String> listThingCode = new ArrayList<String>();
                for (MapModel mapModel : listMap) {
                    listThingCode.add(mapModel.getCode());
                }
                int count = deviceAlarmSetService.getDeviceAlarmSetCount(
                        deviceAlarmSet, listdevicecode, listThingCode);
                if (count > 0) {
                    List<DeviceAlarmSetModel> listModel = new ArrayList<DeviceAlarmSetModel>();
                    List<DeviceAlarmSet> list = deviceAlarmSetService
                            .getDeviceAlarmSet(deviceAlarmSet, listdevicecode, listThingCode);
                    for (DeviceAlarmSet deviceAlarmSetTemp : list) {
                        DeviceAlarmSetModel devicealarmsetModel = ConvertDeviceAlarmSetModel(deviceAlarmSetTemp);
                        if (devicealarmsetModel != null) {
                            listModel.add(devicealarmsetModel);
                        }
                    }
                    resultListModel.setTotal(count);
                    resultListModel.setRows(listModel);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询报警门限失败，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：设置报警门限值
     * </p>
     *
     * @param thingCode
     * @param maxValue
     * @param minValue
     * @param dataFlag
     * @param excuteTime
     * @param noDown
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月1日下午1:44:34
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertDeviceAlarmSet", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertDeviceAlarmSet(String thingCode, String maxValue, String minValue, String dataFlag,
                                     String levelNo, String excuteTime, boolean noDown,
                                     @RequestParam(value = "list[]") List<String> list, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultModel.setDetail("登录超时，请重新登陆后操作！");
                return resultModel;
            }
            if (list != null && list.size() > 0 && thingCode != null
                    && !thingCode.isEmpty() && maxValue != null
                    && !maxValue.isEmpty() && minValue != null
                    && !minValue.isEmpty() && excuteTime != null
                    && !excuteTime.isEmpty()) {
                int userid;
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userid = loginuser.getUserId();
                } else {
                    resultModel.setDetail("登录超时，请重新登陆后操作！");
                    return resultModel;
                }
                int count = deviceAlarmSetService.setDeviceAlarmSet(thingCode, maxValue, minValue, dataFlag, levelNo, excuteTime, noDown, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("设置报警门限值失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：设置报警门限值失败，原因：" + e.getMessage());
            resultModel.setDetail("设置报警门限值失败！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：deviceAlarmSetModel转换成DeviceAlarmSet
     * </p>
     *
     * @param deviceAlarmSetModel
     * @return
     * @author 王垒, 2016年6月1日上午10:52:44
     * @since EnvDust 1.0.0
     */
    private DeviceAlarmSet ConvertDeviceAlarmSet(
            DeviceAlarmSetModel deviceAlarmSetModel, HttpSession httpsession) {
        DeviceAlarmSet deviceAlarmSet = new DeviceAlarmSet();
        if (deviceAlarmSetModel != null) {
            Device device = new Device();
            device.setDeviceName(deviceAlarmSetModel.getDeviceName());
            //设置外键，areaid
            Area area = new Area();
            if (deviceAlarmSetModel.getAreaId() != null && !deviceAlarmSetModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(deviceAlarmSetModel.getAreaId()));
            }
            device.setArea(area);
            deviceAlarmSet.setDevice(device);
            deviceAlarmSet.setRowCount(deviceAlarmSetModel.getRows());
            deviceAlarmSet.setRowIndex((deviceAlarmSetModel.getPage() - 1) * deviceAlarmSetModel.getRows());
        }
        return deviceAlarmSet;
    }

    /**
     * <p>
     * [功能描述]：deviceAlarmSet转换成DeviceAlarmSetModel
     * </p>
     *
     * @param deviceAlarmSet
     * @return
     * @author 王垒, 2016年6月1日上午10:52:44
     * @since EnvDust 1.0.0
     */
    private DeviceAlarmSetModel ConvertDeviceAlarmSetModel(
            DeviceAlarmSet deviceAlarmSet) {
        DeviceAlarmSetModel deviceAlarmSetModel = new DeviceAlarmSetModel();
        if (deviceAlarmSet != null) {
            deviceAlarmSetModel.setDasId(String.valueOf(deviceAlarmSet.getDasId()));

            Device device = deviceAlarmSet.getDevice();
            if (device != null) {
                deviceAlarmSetModel.setDeviceCode(device.getDeviceCode());
                deviceAlarmSetModel.setDeviceMn(device.getDeviceMn());
                deviceAlarmSetModel.setDeviceName(device.getDeviceName());
                //设置areaId,areaName
                Area area = device.getArea();
                if (area != null) {
                    deviceAlarmSetModel.setAreaId(String.valueOf(area.getAreaId()));
                    deviceAlarmSetModel.setAreaName(area.getAreaName());
                }
            }
            Monitor monitor = deviceAlarmSet.getMonitor();
            if (monitor != null) {
                deviceAlarmSetModel.setThingName(monitor.getThingName());
            }
            deviceAlarmSetModel.setMaxValue(String.valueOf(deviceAlarmSet.getMaxValue()));
            deviceAlarmSetModel.setMinValue(String.valueOf(deviceAlarmSet.getMinValue()));
            deviceAlarmSetModel.setDataFlag(String.valueOf(deviceAlarmSet.getDataFlag()));
            deviceAlarmSetModel.setLevelNo(String.valueOf(deviceAlarmSet.getLevelNo()));
            CommMain commMain = deviceAlarmSet.getCommMain();
            if (commMain != null) {
                if (commMain.getCommStatus() != null) {
                    deviceAlarmSetModel.setStatusName(commMain.getCommStatus().getStatusName());
                    deviceAlarmSetModel.setExcuteTime(DateUtil.TimestampToString(commMain.getExcuteTime(), DateUtil.DATA_TIME));
                    deviceAlarmSetModel.setCnName(commMain.getCommCn().getCnName());
                }
            }
            DeviceProject deviceProject = deviceAlarmSet.getDeviceProject();
            if (deviceProject != null) {
                deviceAlarmSetModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                deviceAlarmSetModel.setProjectName(deviceProject.getProjectName());
            }
            deviceAlarmSetModel.setOptUserName(userService.getUserNameById(deviceAlarmSet.getOptUser(), null));
            deviceAlarmSetModel.setOptTime(DateUtil.TimestampToString(deviceAlarmSet.getOptTime(), DateUtil.DATA_TIME));
        }
        return deviceAlarmSetModel;
    }

    /**
     * <p>
     * [功能描述]：获取设备监测物报警门限(thingcode)
     * </p>
     *
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年6月2日下午3:40:11
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceAlarmLine", method = {RequestMethod.POST})
    public @ResponseBody
    HashMap<String, HashMap<String, HashMap<String, Double>>> getDeviceAlarmLine(
            @RequestParam(value = "listdev[]") List<String> listDeviceCode,
            @RequestParam(value = "listthg[]") List<String> listThingCode) {
        HashMap<String, HashMap<String, HashMap<String, Double>>> hashMap = new HashMap<String, HashMap<String, HashMap<String, Double>>>();
        if (listDeviceCode != null && listDeviceCode.size() > 0
                && listThingCode != null && listThingCode.size() > 0
                && !listThingCode.get(0).equals(DefaultArgument.INT_DEFAULT)) {

            for (String deviceCode : listDeviceCode) {
                HashMap<String, HashMap<String, Double>> hashMapThing = new HashMap<String, HashMap<String, Double>>();
                List<DeviceAlarmSet> deviceAlarmSetList = deviceAlarmSetService.getDeviceAlarmSetSingle(deviceCode, listThingCode);
                for (DeviceAlarmSet deviceAlarmSet : deviceAlarmSetList) {
                    HashMap<String, Double> hashMapValue = new HashMap<String, Double>();
                    if (deviceAlarmSet != null) {
                        String thingCode = deviceAlarmSet.getMonitor().getThingCode();
                        double maxValue = deviceAlarmSet.getMaxValue();
                        double minValue = deviceAlarmSet.getMinValue();
                        if (!hashMapValue.containsKey("max")) {
                            hashMapValue.put("max", maxValue);
                        }
                        if (!hashMapValue.containsKey("min")) {
                            hashMapValue.put("min", minValue);
                        }
                        if (!hashMapThing.containsKey(thingCode)) {
                            hashMapThing.put(thingCode, hashMapValue);
                        }
                    }
                }
                if (!hashMap.containsKey(deviceCode)) {
                    hashMap.put(deviceCode, hashMapThing);
                }
            }
        }
        return hashMap;
    }

    /**
     * <p>
     * [功能描述]：获取设备监测物报警门限(thingname)
     * </p>
     *
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年6月2日下午3:40:11
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceAlarmLineThgName", method = {RequestMethod.POST})
    public @ResponseBody
    HashMap<String, HashMap<String, List<HashMap<String, Object>>>> getDeviceAlarmLineThgName(
            @RequestParam(value = "listdev[]") List<String> listDeviceCode,
            @RequestParam(value = "listthg[]") List<String> listThingCode) {
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> hashMap = new HashMap<String, HashMap<String, List<HashMap<String, Object>>>>();
        if (listDeviceCode != null && listDeviceCode.size() > 0
                && listThingCode != null && listThingCode.size() > 0
                && !listThingCode.get(0).equals(DefaultArgument.INT_DEFAULT)) {
            for (String deviceCode : listDeviceCode) {
                HashMap<String, List<HashMap<String, Object>>> hashMapThing = new HashMap<String, List<HashMap<String, Object>>>();
                List<DeviceAlarmSet> deviceAlarmSetList = deviceAlarmSetService.getDeviceAlarmSetSingle(deviceCode, listThingCode);
                for (DeviceAlarmSet deviceAlarmSet : deviceAlarmSetList) {
                    if (deviceAlarmSet != null) {
                        HashMap<String, Object> hashMapValue = new HashMap<String, Object>();
                        String thingCode = deviceAlarmSet.getMonitor().getThingCode();
                        double maxValue = deviceAlarmSet.getMaxValue();
                        double minValue = deviceAlarmSet.getMinValue();
                        int levelNo = deviceAlarmSet.getLevelNo();
                        if (!hashMapValue.containsKey("max")) {
                            hashMapValue.put("max", maxValue);
                        }
                        if (!hashMapValue.containsKey("min")) {
                            hashMapValue.put("min", minValue);
                        }
                        if (!hashMapValue.containsKey("lev")) {
                            hashMapValue.put("lev", levelNo);
                        }
                        String thingName = monitorService.getMonitorName(thingCode);
                        if (!hashMapThing.containsKey(thingName)) {
                            List<HashMap<String, Object>> hashMapList = new ArrayList<HashMap<String, Object>>();
                            hashMapList.add(hashMapValue);
                            hashMapThing.put(thingName, hashMapList);
                        } else {
                            hashMapThing.get(thingName).add(hashMapValue);
                        }
                    }
                }
                if (!hashMap.containsKey(deviceCode)) {
                    hashMap.put(deviceCode, hashMapThing);
                }
            }
        }
        return hashMap;
    }

    /**
     * <p>
     * [功能描述]：获取设备监测物报警门限(devicename-areaname)
     * </p>
     *
     * @param listDeviceCode
     * @param listThingCode
     * @return
     * @author 王垒, 2016年6月2日下午3:40:11
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDeviceAlarmLineDevName", method = {RequestMethod.POST})
    public @ResponseBody
    HashMap<String, HashMap<String, List<HashMap<String, Object>>>> getDeviceAlarmLineDevName(
            @RequestParam(value = "listdev[]") List<String> listDeviceCode,
            @RequestParam(value = "listthg[]") List<String> listThingCode) {
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> hashMap = new HashMap<String, HashMap<String, List<HashMap<String, Object>>>>();
        if (listDeviceCode != null && listDeviceCode.size() > 0
                && listThingCode != null && listThingCode.size() > 0
                && !listThingCode.get(0).equals(DefaultArgument.INT_DEFAULT)) {

            for (String deviceCode : listDeviceCode) {
                String areaName = deviceService.getAreaName(deviceCode);
                String deviceName = deviceService.getDeviceName(deviceCode);
                HashMap<String, List<HashMap<String, Object>>> hashMapThing = new HashMap<String, List<HashMap<String, Object>>>();
                List<DeviceAlarmSet> deviceAlarmSetList = deviceAlarmSetService.getDeviceAlarmSetSingle(deviceCode, listThingCode);
                for (DeviceAlarmSet deviceAlarmSet : deviceAlarmSetList) {
                    HashMap<String, Object> hashMapValue = new HashMap<String, Object>();
                    if (deviceAlarmSet != null) {
                        String thingCode = deviceAlarmSet.getMonitor().getThingCode();
                        double maxValue = deviceAlarmSet.getMaxValue();
                        double minValue = deviceAlarmSet.getMinValue();
                        int levelNo = deviceAlarmSet.getLevelNo();
                        if (!hashMapValue.containsKey("max")) {
                            hashMapValue.put("max", maxValue);
                        }
                        if (!hashMapValue.containsKey("min")) {
                            hashMapValue.put("min", minValue);
                        }
                        if (!hashMapValue.containsKey("lev")) {
                            hashMapValue.put("lev", levelNo);
                        }
                        if (!hashMapThing.containsKey(thingCode)) {
                            List<HashMap<String, Object>> hashMapList = new ArrayList<HashMap<String, Object>>();
                            hashMapList.add(hashMapValue);
                            hashMapThing.put(thingCode, hashMapList);
                        } else {
                            hashMapThing.get(thingCode).add(hashMapValue);
                        }
                    }
                }
                String hashKey = deviceName;
                if (areaName != null && !areaName.isEmpty()) {
                    hashKey += "-" + areaName;
                }
                if (!hashMap.containsKey(hashKey)) {
                    hashMap.put(hashKey, hashMapThing);
                }
            }
        }
        return hashMap;
    }

    /**
     * <p>[功能描述]：删除报警门限设置</p>
     *
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日下午2:15:31
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteDeviceAlarmSet", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteDeviceAlarmSet(
            @RequestParam(value = "list[]") List<Integer> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listDasId = new ArrayList<Integer>();
                for (Integer dasId : list) {
                    listDasId.add(dasId);
                }
                int intresult = deviceAlarmSetService.deleteDeviceAlarmSet(listDasId);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除计划任务失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除计划任务失败！");
                logger.error(LOG + "：删除计划任务失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除计划任务失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

}
