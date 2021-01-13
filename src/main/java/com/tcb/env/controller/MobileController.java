package com.tcb.env.controller;

import com.tcb.env.model.*;
import com.tcb.env.pojo.Alarm;
import com.tcb.env.service.*;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 移动端控制器
 * @Date: Create in 2019/4/10 13:21
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/MobileController")
public class MobileController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "MobileController";

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(MobileController.class);

    @Resource
    private IMobileService mobileService;

    @Resource
    private IDeviceService deviceService;

    @Resource
    private IAlarmService alarmService;

    @Resource
    private ITreeService treeService;

    @Resource
    private IMonitorService monitorService;

    @Resource
    private MonitorStorageController monitorStorageController;

    @Resource
    private IAreaService areaService;

    /**
     * 查询坐标点信息
     *
     * @param projectId
     * @param list
     * @param dataType
     * @param thingCode
     * @param httpSession
     * @return
     */
    @RequestMapping("/getMapPoint")
    @ResponseBody
    public List<MobilePointModel> getMapPoint(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]", required = false) List<String> list,
            @RequestParam(defaultValue = "2011") String dataType,
            @RequestParam(required = false) String levelNo,
            String thingCode, HttpSession httpSession) {
        List<MobilePointModel> mobilePointModelList = new ArrayList<MobilePointModel>();
        try {
            List<String> deviceCodeList = new ArrayList<String>();
            if (list == null || list.size() == 0) {
                List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId,
                        DefaultArgument.INT_DEFAULT, null, httpSession);
                for (MapModel mapModel : listDev) {
                    deviceCodeList.add(mapModel.getCode());
                }
            } else {
                deviceCodeList = list;
            }
            mobilePointModelList = mobileService.getMobilePoint(deviceCodeList, thingCode, dataType, levelNo);
        } catch (Exception e) {
            logger.error(LOG + "获取坐标点信息异常:" + e.getMessage());
        }
        return mobilePointModelList;
    }

    /**
     * 获取设备最新上传数据
     *
     * @param deviceCode
     * @param dataType
     * @param deviceName
     * @param httpSession
     * @return
     */
    @RequestMapping("/getPointRecentData")
    @ResponseBody
    public MobilePointModel getPointRecentData(
            @RequestParam(required = false) String deviceCode,
            @RequestParam(defaultValue = "2011") String dataType,
            String deviceName, HttpSession httpSession) {
        MobilePointModel mobilePointModel = new MobilePointModel();
        if (StringUtils.isEmpty(deviceCode)) {
            // 获取用户code
            String userCode = null;
            if (SessionManager.isSessionValidate(httpSession, DefaultArgument.LOGIN_USER)) {
                return mobilePointModel;
            }
            UserModel loginUser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginUser != null) {
                userCode = loginUser.getUserCode();
            }
            //查询在线设备
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add("N");
            statusCodeList.add("NT");
            statusCodeList.add("D");
            TreeModel treeModel = treeService.getDeviceFirst(userCode, deviceName, statusCodeList);
            if (treeModel != null) {
                deviceCode = treeModel.getId();
                mobilePointModel = mobileService.getPointRecentData(deviceCode, dataType);
            } else {
                //查询短线设备
                statusCodeList.clear();
                statusCodeList.add("O");
                treeModel = treeService.getDeviceFirst(userCode, deviceName, statusCodeList);
                if (treeModel != null) {
                    deviceCode = treeModel.getId();
                    mobilePointModel = mobileService.getPointRecentData(deviceCode, dataType);
                } else {
                    //查询全部设备
                    statusCodeList.clear();
                    treeModel = treeService.getDeviceFirst(userCode, deviceName, statusCodeList);
                    if (treeModel != null) {
                        deviceCode = treeModel.getId();
                        mobilePointModel = mobileService.getPointRecentData(deviceCode, dataType);
                    }
                }
            }
        } else {
            mobilePointModel = mobileService.getPointRecentData(deviceCode, dataType);
        }
        return mobilePointModel;
    }

    /**
     * 获取区域最新上传数据
     *
     * @param areaId
     * @param dataType
     * @param httpSession
     * @return
     */
    @RequestMapping("/getAreaRecentData")
    @ResponseBody
    public MobilePointModel getAreaRecentData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "areaId") String areaId,
            @RequestParam(defaultValue = "2011") String dataType, String thingCode, HttpSession httpSession) {
        MobilePointModel mobilePointModel = new MobilePointModel();
        if (!StringUtils.isEmpty(areaId)) {
            String areaName = areaService.getAreaName(Integer.valueOf(areaId));
            List<String> listDeviceCode = new ArrayList<String>();
            // 获取用户code
            String userCode = null;
            if (SessionManager.isSessionValidate(httpSession, DefaultArgument.LOGIN_USER)) {
                return mobilePointModel;
            }
            UserModel loginUser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginUser != null) {
                userCode = loginUser.getUserCode();
            }
            List<Integer> listAreaId = new ArrayList<Integer>();
            listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(areaId), userCode);
            for (Integer areaIdTemp : listAreaId) {
                //正常
                List<TreeModel> listDev = treeService.getAuthorityDevices(userCode, projectId, areaIdTemp, null, "N", null);
                for (TreeModel treeModel : listDev) {
                    listDeviceCode.add(treeModel.getId());
                }
                //超标
                listDev = treeService.getAuthorityDevices(userCode, projectId, areaIdTemp, null, "NT", null);
                for (TreeModel treeModel : listDev) {
                    listDeviceCode.add(treeModel.getId());
                }
            }
            List<String> listThingCode = new ArrayList<String>();
            List<MapModel> listMap = monitorStorageController.getAthorityMonitors(listDeviceCode, httpSession);
            if (listMap != null && listMap.size() > 0) {
                for (MapModel mapModel : listMap) {
                    listThingCode.add(mapModel.getCode());
                }
            }
            mobilePointModel = mobileService.getPointsRecentData(listDeviceCode, listThingCode, dataType);
            if (mobilePointModel == null) {
                mobilePointModel = new MobilePointModel();
            }
            mobilePointModel.setAreaId(areaId);
            mobilePointModel.setAreaName(areaName);
        }
        return mobilePointModel;
    }

    /**
     * 获取用户所含设备所有监测物监测数据
     *
     * @param dataType
     * @param httpSession
     * @return
     */
    @RequestMapping("/getRecentDataFromDevicesForUser")
    @ResponseBody
    public MobilePointModel getRecentDataFromDevicesForUser(@RequestParam(defaultValue = "2011") String dataType, HttpSession httpSession) {
        MobilePointModel mobilePointModel = new MobilePointModel();
        List<String> listDeviceCode = new ArrayList<String>();
        // 获取用户code
        String userCode = null;
        if (SessionManager.isSessionValidate(httpSession, DefaultArgument.LOGIN_USER)) {
            return mobilePointModel;
        }
        UserModel loginUser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginUser != null) {
            userCode = loginUser.getUserCode();
        }

        List<TreeModel> listDev = treeService.getAuthorityDevices(userCode, null, -1, null, "N", null);
        for (TreeModel treeModel : listDev) {
            listDeviceCode.add(treeModel.getId());
        }
        //超标
        listDev = treeService.getAuthorityDevices(userCode, null, -1, null, "NT", null);
        for (TreeModel treeModel : listDev) {
            listDeviceCode.add(treeModel.getId());
        }


        List<String> listThingCode = new ArrayList<String>();
        List<MapModel> listMap = monitorStorageController.getAthorityMonitors(listDeviceCode, httpSession);
        if (listMap != null && listMap.size() > 0) {
            for (MapModel mapModel : listMap) {
                listThingCode.add(mapModel.getCode());
            }
        }

        mobilePointModel = mobileService.getPointsRecentData(listDeviceCode, listThingCode, dataType);
        if (mobilePointModel == null) {
            mobilePointModel = new MobilePointModel();
        }
        return mobilePointModel;
    }

    /**
     * 获取全区域某监测物数据
     *
     * @param dataType
     * @param thingCode
     * @return
     */
    @RequestMapping("/getAllAreaByThingCode")
    @ResponseBody
    public MobilePointModel getAllAreaByThingCode(
            @RequestParam(defaultValue = "2011") String dataType, String thingCode) {
        MobilePointModel mobilePointModel;
        List<String> listDeviceCode = new ArrayList<>();
        List<TreeModel> listDev = treeService.getAllAreaDevices();
        for (TreeModel treeModel : listDev) {
            listDeviceCode.add(treeModel.getId());
        }

        List<String> listThingCode = new ArrayList<>();
        listThingCode.add(thingCode);

        mobilePointModel = mobileService.getPointsRecentData(listDeviceCode, listThingCode, dataType);
        if (mobilePointModel == null) {
            mobilePointModel = new MobilePointModel();
        }
        return mobilePointModel;
    }

    /**
     * 获取监测物等级信息
     *
     * @param thingCode
     * @param httpSession
     * @return
     */
    @RequestMapping("/getThingLevel")
    @ResponseBody
    public List<MonitorLevelModel> getThingLevel(String thingCode, HttpSession httpSession) {
        List<MonitorLevelModel> monitorLevelModelList = new ArrayList<MonitorLevelModel>();
        try {
            monitorLevelModelList = monitorService.getMonitorLevel(thingCode);
        } catch (Exception e) {
            logger.error(LOG + "获取监测物等级信息异常:" + e.getMessage());
        }
        return monitorLevelModelList;
    }

    /**
     * 查询移动端报警推送数据（24小时内）
     *
     * @param alarmId
     * @param httpsession
     * @return
     */
    @RequestMapping(value = "/getDeviceMobileAlarmWithinDay", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<MapDeviceModel> getDeviceMobileAlarmWithinDay(
            @RequestParam(value = "alarmId", required = false) String alarmId, HttpSession httpsession) {
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
            List<TreeModel> listDev = treeService.getAuthorityDevices(
                    usercode, null, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                if ("N".equals(treeModel.getState())) {
                    //筛选掉正常设备
                    continue;
                } else {
                    listdevicecode.add(treeModel.getId());
                }
            }
            int count = 0;
            List<MapDeviceModel> listMapDev = new ArrayList<MapDeviceModel>();
            if (listdevicecode != null && listdevicecode.size() > 0) {
                Timestamp beginAlarmTime = DateUtil.GetSystemDateTime(24 * 60 * 60 * 1000);
                Timestamp endAlarmTime = DateUtil.GetSystemDateTime(0);
                count = deviceService.getMapDeviceCount(listdevicecode, null, null, beginAlarmTime, endAlarmTime, alarmId);
                if (count > 0) {
                    listMapDev = deviceService.getMapDevice(listdevicecode, null, null, beginAlarmTime, endAlarmTime, alarmId,
                            DefaultArgument.INT_DEFAULT, DefaultArgument.INT_DEFAULT);
                    // 设置设备状态
                    if (listMapDev != null && listMapDev.size() > 0) {
                        int maxAlarmId = 0;
                        for (MapDeviceModel mapDeviceModel : listMapDev) {
                            if (mapDeviceModel != null) {
                                List<Alarm> statusInfoList = alarmService.getRecentlyAlarmIdInfo(mapDeviceModel.getDeviceCode(),
                                        mapDeviceModel.getStatusCode());
                                if (statusInfoList != null && statusInfoList.size() > 0) {
                                    String statusInfo = "";
                                    for (Alarm tempAlarm : statusInfoList) {
                                        if (tempAlarm != null) {
                                            String tempInfo = tempAlarm.getAlarmInfo();
                                            int tempId = tempAlarm.getAlarmId();
                                            if (tempId > Double.valueOf(maxAlarmId)) {
                                                maxAlarmId = tempId;
                                            }
                                            statusInfo += tempInfo + "\r\n";
                                        }
                                    }
                                    if (maxAlarmId > 0) {
                                        mapDeviceModel.setMaxAlarmId(String.valueOf(maxAlarmId));
                                    }
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

}
