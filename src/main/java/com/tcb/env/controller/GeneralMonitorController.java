package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tcb.env.service.*;
import com.tcb.env.util.SortListUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.AreaModel;
import com.tcb.env.model.DeviceValueModel;
import com.tcb.env.model.GeneralAlarmModel;
import com.tcb.env.model.GeneralAreaDeviceModel;
import com.tcb.env.model.GeneralAreaDeviceThingModel;
import com.tcb.env.model.GeneralAreaModel;
import com.tcb.env.model.GeneralCountModel;
import com.tcb.env.model.GeneralDeviceDataModel;
import com.tcb.env.model.GeneralDeviceLocationModel;
import com.tcb.env.model.GeneralModel;
import com.tcb.env.model.GeneralMonitorCountModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.NetStatusCount;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：查询面板数据控制器</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年12月22日上午10:04:56
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/GeneralMonitorController")
public class GeneralMonitorController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "GeneralMonitorController";

    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(GeneralMonitorController.class);
    /**
     * 声明监测物查询服务类
     */
    @Resource
    private IMonitorStorageService monitorStorageService;
    /**
     * 声明监控数据服务类
     */
    @Resource
    private IGeneralMonitorService generalMonitorService;
    /**
     * 声明树服务接口
     */
    @Resource
    private ITreeService treeService;
    /**
     * 声明查询map服务
     */
    @Resource
    private IMapService mapService;
    /**
     * 声明控制器
     */
    @Resource
    private MonitorStorageController monitorStorageController;

    @Resource
    private AreaController areaController;

    @Resource
    private IAreaService areaService;

    @Resource
    private IMonitorService monitorService;

    /**
     * <p>[功能描述]：查询设备状态数据</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param httpsession
     * @return
     * @author 王垒, 2017年12月22日下午2:23:52
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getGenaralMonitorCount", method = {RequestMethod.POST})
    public @ResponseBody
    GeneralMonitorCountModel getGenaralMonitorCount(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list,
            String levelFlag, HttpSession httpsession) {
        GeneralMonitorCountModel generalMonitorCountModel = new GeneralMonitorCountModel();
        int normalCount = 0;
        int overCount = 0;
        int disconnectCount = 0;
        int nodataCount = 0;
        int otherCount = 0;
        int onlineCount = 0;
        int offlineCount = 0;
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return generalMonitorCountModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<NetStatusCount> listNSCount = new ArrayList<NetStatusCount>();
            List<String> listdevicecode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                return generalMonitorCountModel;
            } else if (list == null || list.size() == 0) {
                return generalMonitorCountModel;
            } else {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid,
                            Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        list.get(0))) {
                    List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId,
                            DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listdevicecode.add(mapModel.getCode());
                    }
                    ;
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, new ArrayList<String>());
                } else if (listareaid != null && listareaid.size() > 0) {
                    for (Integer areaid : listareaid) {
                        List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listdevicecode.add(mapModel.getCode());
                        }
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, listdevicecode);
                } else {
                    for (String temp : list) {
                        listdevicecode.add(temp);
                    }
                    listNSCount = monitorStorageService.getNetStatusCount(userCode, listdevicecode);
                }

            }
            if (listNSCount != null && listNSCount.size() > 0) {
                for (NetStatusCount netStatusCount : listNSCount) {
                    switch (netStatusCount.getStatusCode()) {
                        case "N":
                            normalCount += netStatusCount.getStatusCount();
                            break;
                        case "NT":
                            overCount += netStatusCount.getStatusCount();
                            break;
                        case "O":
                            disconnectCount += netStatusCount.getStatusCount();
                            break;
                        case "Z":
                            nodataCount += netStatusCount.getStatusCount();
                            break;
                        default:
                            otherCount += netStatusCount.getStatusCount();
                            break;
                    }
                }
                onlineCount = normalCount + overCount;
                offlineCount = disconnectCount + nodataCount + otherCount;
                generalMonitorCountModel.setNormalCount(normalCount);
                generalMonitorCountModel.setOverCount(overCount);
                generalMonitorCountModel.setDisconnectCount(disconnectCount);
                generalMonitorCountModel.setNodataCount(nodataCount);
                generalMonitorCountModel.setOtherCount(otherCount);
                generalMonitorCountModel.setOnlineCount(onlineCount);
                generalMonitorCountModel.setOfflineCount(offlineCount);
                generalMonitorCountModel.setResult(true);
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询设备状态数据错误，错误信息为：" + e.getMessage());
        }
        return generalMonitorCountModel;
    }

    /**
     * 获取区域或设备排名
     *
     * @param projectId
     * @param areaId
     * @param thingCode
     * @param dataType
     * @param order
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAreaSubMonitorValueRanking", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceValueModel> getAreaSubMonitorValueRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "areaId") String areaId, String thingCode, String dataType,
            String order, int limit, HttpSession httpSession) {
        ResultListModel<DeviceValueModel> resultListModel = new ResultListModel<>();
        List<DeviceValueModel> deviceValueList = new ArrayList<>();
        String userCode = null;
        try {
            String thingName = monitorService.getMonitorName(thingCode);
            String levelFlag = areaService.getAreaLevelFlag(Integer.valueOf(areaId));
            if (SessionManager.isSessionValidate(httpSession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            if (DefaultArgument.BOTTOM_LEVEL_FALG.equals(levelFlag)) {
                List<String> list = new ArrayList<>();
                list.add(areaId);
                return getGenaralMonitorValueRanking(projectId, list, thingCode, dataType, order, limit, levelFlag, httpSession);
            } else {
                ResultListModel<MapModel> subAreaModelList = areaController.queryAreaDropDownSub(Integer.valueOf(areaId), httpSession);
                if (subAreaModelList != null && subAreaModelList.getTotal() > 0) {
                    for (MapModel temp : subAreaModelList.getRows()) {
                        String areaNameSub = areaService.getAreaName(temp.getId());
                        List<String> listDeviceCode = new ArrayList<>();
                        if (DefaultArgument.BOTTOM_LEVEL_FALG.equals(temp.getLevelFlag())) {
                            //正常
                            List<TreeModel> listDev = treeService.getAuthorityDevices(userCode, projectId, temp.getId(), null, "N", null);
                            for (TreeModel treeModel : listDev) {
                                listDeviceCode.add(treeModel.getId());
                            }
                            //超标
                            listDev = treeService.getAuthorityDevices(userCode, projectId, temp.getId(), null, "NT", null);
                            for (TreeModel treeModel : listDev) {
                                listDeviceCode.add(treeModel.getId());
                            }
                        } else {
                            List<Integer> listAreaIdSub = new ArrayList<Integer>();
                            listAreaIdSub = treeService.getAuthorityBottomArea(projectId, listAreaIdSub, Integer.valueOf(temp.getId()), userCode);
                            for (Integer areaIdTemp : listAreaIdSub) {
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
                        }
                        String thingValue = generalMonitorService.getDeviceListAvgValue(listDeviceCode, thingCode, dataType);
                        DeviceValueModel deviceValueModel = new DeviceValueModel();
                        deviceValueModel.setDeviceCode(String.valueOf(temp.getId()));
                        deviceValueModel.setDeviceName(areaNameSub);
                        deviceValueModel.setThingCode(thingCode);
                        deviceValueModel.setThingName(thingName);
                        deviceValueModel.setThingValue(thingValue);
                        deviceValueList.add(deviceValueModel);
                    }
                }
                //排序
                if (deviceValueList != null && deviceValueList.size() > 0) {
                    if ("asc".equals(order)) {
                        order = "asc";
                    } else {
                        order = "desc";
                    }
                    SortListUtil<DeviceValueModel> sortList = new SortListUtil<>();
                    sortList.sortDouble(deviceValueList, "convertThingValueDouble", order);
                    if (deviceValueList != null && deviceValueList.size() > 0) {
                        int i = 1;
                        for (DeviceValueModel deviceValueModel : deviceValueList) {
                            deviceValueModel.setDataOrder(String.valueOf(i));
                            i++;
                        }
                    }
                }
                resultListModel.setRows(deviceValueList);
                resultListModel.setTotal(deviceValueList.size());
                resultListModel.setResult(true);
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取设备浓度排名错误，错误信息为：" + e.getMessage());
            resultListModel.setResult(false);
        }
        return resultListModel;
    }

    /**
     * 获取全区域设备排名
     *
     * @param thingCode
     * @param dataType
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getALLAreaSubMonitorValueRanking", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceValueModel> getALLAreaSubMonitorValueRanking(String thingCode, String dataType, String order, HttpSession httpSession) {
        ResultListModel<DeviceValueModel> resultListModel = new ResultListModel<>();
        List<DeviceValueModel> deviceValueList = new ArrayList<>();
        String userCode = null;

        if (SessionManager.isSessionValidate(httpSession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        } else {
            UserModel loginuser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                userCode = loginuser.getUserCode();
            }
        }
        try {
            List<Integer> areaIds = mapService.getAreaIdFromFlag(4);
            for (Integer areaId : areaIds) {
                String areaName = areaService.getAreaName(areaId);
                List<String> listDeviceCode = new ArrayList<>();
                //正常
                List<TreeModel> listDev = treeService.getAuthorityDevices(userCode, null, areaId, null, "N", null);
                for (TreeModel treeModel : listDev) {
                    listDeviceCode.add(treeModel.getId());
                }
                //超标
                listDev = treeService.getAuthorityDevices(userCode, null, areaId, null, "NT", null);
                for (TreeModel treeModel : listDev) {
                    listDeviceCode.add(treeModel.getId());
                }
                String thingValue = generalMonitorService.getDeviceListAvgValue(listDeviceCode, thingCode, dataType);
                DeviceValueModel deviceValueModel = new DeviceValueModel();
                deviceValueModel.setDeviceName(areaName);
                deviceValueModel.setThingCode(thingCode);
                deviceValueModel.setThingValue(thingValue);
                deviceValueList.add(deviceValueModel);
            }
            //排序
            if (deviceValueList != null && deviceValueList.size() > 0) {
                if ("asc".equals(order)) {
                    order = "asc";
                } else {
                    order = "desc";
                }
                SortListUtil<DeviceValueModel> sortList = new SortListUtil<>();
                sortList.sortDouble(deviceValueList, "convertThingValueDouble", order);
                if (deviceValueList != null && deviceValueList.size() > 0) {
                    int i = 1;
                    for (DeviceValueModel deviceValueModel : deviceValueList) {
                        deviceValueModel.setDataOrder(String.valueOf(i));
                        i++;
                    }
                }
                resultListModel.setRows(deviceValueList);
                resultListModel.setTotal(deviceValueList.size());
                resultListModel.setResult(true);
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取设备浓度排名错误，错误信息为：" + e.getMessage());
            resultListModel.setResult(false);
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备浓度排名</p>
     *
     * @param projectId
     * @param list
     * @param thingCode
     * @param dataType
     * @param order
     * @param limit
     * @param levelFlag
     * @param httpsession
     * @return
     * @author 王垒, 2017年12月22日下午2:22:20
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getGenaralMonitorValueRanking", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceValueModel> getGenaralMonitorValueRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String thingCode, String dataType, String order,
            int limit, String levelFlag, HttpSession httpsession) {
        ResultListModel<DeviceValueModel> resultListModel = new ResultListModel<DeviceValueModel>();
        List<DeviceValueModel> deviceValueList = new ArrayList<DeviceValueModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> listDeviceCode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                return resultListModel;
            } else if (list == null || list.size() == 0) {
                return resultListModel;
            } else {
                List<Integer> listareaid = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listareaid = treeService.getAuthorityBottomArea(projectId, listareaid,
                            Integer.valueOf(list.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        list.get(0))) {
                    List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId,
                            DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listDeviceCode.add(mapModel.getCode());
                    }
                    ;
                } else if (listareaid != null && listareaid.size() > 0) {
                    for (Integer areaid : listareaid) {
                        List<MapModel> listDev = monitorStorageController.getAuthorityDevices(projectId, areaid, null, httpsession);
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
            if (listDeviceCode != null && listDeviceCode.size() > 0) {
                deviceValueList = generalMonitorService.getGenaralMonitorValueRanking(
                        listDeviceCode, thingCode, dataType, DefaultArgument.ON_LINE_FLAG, order, limit);
                if (deviceValueList != null && deviceValueList.size() > 0) {
                    int i = 1;
                    for (DeviceValueModel deviceValueModel : deviceValueList) {
                        deviceValueModel.setDataOrder(String.valueOf(i));
                        i++;
                    }
                }
                resultListModel.setRows(deviceValueList);
            }
            resultListModel.setResult(true);
        } catch (Exception e) {
            logger.error(LOG + ":获取设备浓度排名错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取大屏监控相关数据</p>
     *
     * @param projectId
     * @param status
     * @param areaId
     * @param alarmCount
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月4日下午2:07:33
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getGenaral", method = {RequestMethod.POST})
    public @ResponseBody
    GeneralModel getGenaral(
            @RequestParam(value = "projectId", required = false) String projectId,
            String status, int areaId, int alarmCount, HttpSession httpsession) {
        GeneralModel generalModel = new GeneralModel();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                generalModel.setResult(false);
                generalModel.setDetail("未查询到登录信息");
                return generalModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            //查询区域状态权限设备
            List<String> deviceCodeList = new ArrayList<String>();
            if (areaId > 0) {
                String statusCode = null;
                if (status != null && !status.isEmpty() && !status.equals("other")) {
                    statusCode = status;
                }
                List<TreeModel> listDev = treeService.getAuthorityDevices(userCode, projectId, areaId, null, statusCode, null);
                for (TreeModel treeModel : listDev) {
                    deviceCodeList.add(treeModel.getId());
                }
            }
            //获取所有站点状态个数
            List<NetStatusCount> listNSCount = monitorStorageService.getNetStatusCount(userCode, new ArrayList<String>());
            if (listNSCount != null && listNSCount.size() > 0) {
                GeneralCountModel gcm = new GeneralCountModel();
                int normalCount = 0;
                int overCount = 0;
                int disconnectCount = 0;
                int nodataCount = 0;
                int otherCount = 0;
                int allCount = 0;
                for (NetStatusCount netStatusCount : listNSCount) {
                    switch (netStatusCount.getStatusCode()) {
                        case "N":
                            normalCount += netStatusCount.getStatusCount();
                            break;
                        case "NT":
                            overCount += netStatusCount.getStatusCount();
                            break;
                        case "O":
                            disconnectCount += netStatusCount.getStatusCount();
                            break;
                        case "Z":
                            nodataCount += netStatusCount.getStatusCount();
                            break;
                        default:
                            otherCount += netStatusCount.getStatusCount();
                            break;
                    }
                }
                allCount = normalCount + overCount + disconnectCount + nodataCount + otherCount;
                gcm.setNormalCount(normalCount);
                gcm.setOverCount(overCount);
                gcm.setDisconnectCount(disconnectCount);
                gcm.setNodataCount(nodataCount);
                gcm.setOtherCount(otherCount);
                gcm.setAllCount(allCount);
                generalModel.setGcm(gcm);
            }
            //设置查询状态
            List<String> dataTypeList = new ArrayList<String>();
            List<String> noDataTypeList = new ArrayList<String>();
            if (status != null && !status.isEmpty()) {
                if (status.equals("other")) {
                    noDataTypeList.add("N");
                    noDataTypeList.add("NT");
                    noDataTypeList.add("O");
                    noDataTypeList.add("Z");
                } else {
                    dataTypeList.add(status);
                }
            }
            //获取站点位置信息
            List<GeneralDeviceLocationModel> gdlmList = generalMonitorService.getGeneralDeviceLocation(
                    deviceCodeList, dataTypeList, noDataTypeList);
            generalModel.setGdlmList(gdlmList);
            //获取站点报警信息
            List<GeneralAlarmModel> gamList = generalMonitorService.getGeneralAlarm(
                    deviceCodeList, dataTypeList, noDataTypeList, alarmCount);
            generalModel.setGamList(gamList);
            //获取站点数据信息
            List<GeneralDeviceDataModel> gddmList = generalMonitorService.getGeneralDeviceData(
                    deviceCodeList, null, null, dataTypeList, noDataTypeList);
            generalModel.setGddmList(gddmList);
            generalModel.setResult(true);
        } catch (Exception e) {
            logger.error(LOG + ":获取大屏监控相关数据错误，错误信息为：" + e.getMessage());
        }
        return generalModel;
    }

    /**
     * <p>[功能描述]：获取用户权限设备的区域列表</p>
     *
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月4日下午2:44:39
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAuthorityDeviceArea", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<AreaModel> getAuthorityDeviceArea(HttpSession httpsession) {
        ResultListModel<AreaModel> resultListModel = new ResultListModel<AreaModel>();
        List<AreaModel> areaModelList = new ArrayList<AreaModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultListModel.setResult(false);
                resultListModel.setDetail("未查询到登录信息");
                return resultListModel;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
                areaModelList = generalMonitorService.getAuthorityDeviceArea(userCode);
                if (areaModelList != null && areaModelList.size() > 0) {
                    resultListModel.setRows(areaModelList);
                    resultListModel.setTotal(areaModelList.size());
                } else {
                    resultListModel.setTotal(0);
                }
                resultListModel.setResult(true);
            }
        } catch (Exception e) {
            logger.error(LOG + ":获取用户权限设备的区域列表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取大屏监控相关数据</p>
     *
     * @param projectId
     * @param select
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月4日下午2:07:33
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getGenaralAreaData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<GeneralAreaModel> getGenaralAreaData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(required = false) String select, HttpSession httpsession) {
        ResultListModel<GeneralAreaModel> resultListModel = new ResultListModel<GeneralAreaModel>();
        LinkedList<GeneralAreaModel> gamList = new LinkedList<GeneralAreaModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                if (select != null && !select.isEmpty()) {
                    userCode = select;
                } else {
                    resultListModel.setResult(false);
                    resultListModel.setDetail("无登录信息，请关闭网页重新登录系统！");
                    return resultListModel;
                }
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                    resultListModel.setSelect(userCode);
                }
            }
            //查询权限设备
            List<String> deviceCodeList = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(userCode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                deviceCodeList.add(treeModel.getId());
            }
            if (deviceCodeList == null || deviceCodeList.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("无权限数据，请联系系统管理员！");
                return resultListModel;
            }
            //查询权限监测物
            List<String> thingCodeList = new ArrayList<String>();
            List<MapModel> listMap = mapService.getAuthorityMonitors(userCode, DefaultArgument.INT_DEFAULT, null, deviceCodeList);
            if (listMap != null && listMap.size() > 0) {
                for (MapModel mapModel : listMap) {
                    thingCodeList.add(mapModel.getCode());
                }
            }
            if (thingCodeList == null || thingCodeList.size() == 0) {
                resultListModel.setResult(false);
                resultListModel.setDetail("无权限数据，请联系系统管理员！");
                return resultListModel;
            }
            List<String> dataTypeList = new ArrayList<String>();
            dataTypeList.add("2011");//实时数据
            //获取站点数据信息
            List<GeneralDeviceDataModel> gddmList = generalMonitorService.getGeneralDeviceData(
                    deviceCodeList, thingCodeList, dataTypeList, null, null);
            List<GeneralDeviceDataModel> gddmNoDataList = generalMonitorService.getGeneralDeviceNoData(
                    deviceCodeList, null, null);
            List<String> gamListExit = new ArrayList<String>();//区域
            List<String> gadmListExit = new ArrayList<String>();//区域-设备
            List<String> gadtmListExit = new ArrayList<String>();//区域-设备-监测物

            //存在监测数据
            for (GeneralDeviceDataModel generalDeviceDataModel : gddmList) {
                String areaId = generalDeviceDataModel.getAreaId();
                String areaName = generalDeviceDataModel.getAreaName();
                String deviceCode = generalDeviceDataModel.getDeviceCode();
                String deviceName = generalDeviceDataModel.getDeviceName();
                String deviceMn = generalDeviceDataModel.getDeviceMn();
                String statusCode = generalDeviceDataModel.getStatusCode();
                String statusName = generalDeviceDataModel.getStatusName();
                String thingCode = generalDeviceDataModel.getThingCode();
                String thingName = generalDeviceDataModel.getThingName();
                String thingValue = generalDeviceDataModel.getRecentData();
                String thingUnit = generalDeviceDataModel.getThingUnit();
                String updateTime = generalDeviceDataModel.getUpdateTime();
                String maxValue = generalDeviceDataModel.getMaxValue();
                String minValue = generalDeviceDataModel.getMinValue();
                if (deviceCode == null || deviceCode.isEmpty()) {
                    continue;
                }
                //填充
                if (gamListExit.contains(areaId)) {
                    for (GeneralAreaModel gamTemp : gamList) {
                        if (gamTemp.getAreaId().equals(areaId)) {
                            if (gadmListExit.contains(areaId + deviceCode)) {
                                for (GeneralAreaDeviceModel gadmTemp : gamTemp.getGadmList()) {
                                    if (gadmTemp.getDeviceCode().equals(deviceCode)) {
                                        if (gadtmListExit.contains(areaId + deviceCode + thingCode)) {
                                            break;
                                        } else {
                                            GeneralAreaDeviceThingModel generalAreaDeviceThingModel = new GeneralAreaDeviceThingModel();
                                            generalAreaDeviceThingModel.setThingCode(thingCode);
                                            generalAreaDeviceThingModel.setThingName(thingName);
                                            generalAreaDeviceThingModel.setThingValue(thingValue);
                                            generalAreaDeviceThingModel.setThingUnit(thingUnit);
                                            generalAreaDeviceThingModel.setUpdateTime(updateTime);
                                            generalAreaDeviceThingModel.setMaxValue(maxValue);
                                            generalAreaDeviceThingModel.setMinValue(minValue);
                                            gadmTemp.getGadtmList().add(generalAreaDeviceThingModel);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                GeneralAreaDeviceThingModel generalAreaDeviceThingModel = new GeneralAreaDeviceThingModel();
                                generalAreaDeviceThingModel.setThingCode(thingCode);
                                generalAreaDeviceThingModel.setThingName(thingName);
                                generalAreaDeviceThingModel.setThingValue(thingValue);
                                generalAreaDeviceThingModel.setThingUnit(thingUnit);
                                generalAreaDeviceThingModel.setUpdateTime(updateTime);
                                generalAreaDeviceThingModel.setMaxValue(maxValue);
                                generalAreaDeviceThingModel.setMinValue(minValue);
                                List<GeneralAreaDeviceThingModel> gadtmList = new ArrayList<GeneralAreaDeviceThingModel>();
                                gadtmList.add(generalAreaDeviceThingModel);
                                GeneralAreaDeviceModel generalAreaDeviceModel = new GeneralAreaDeviceModel();
                                generalAreaDeviceModel.setDeviceCode(deviceCode);
                                generalAreaDeviceModel.setDeviceName(deviceName);
                                generalAreaDeviceModel.setDeviceMn(deviceMn);
                                generalAreaDeviceModel.setStatusCode(statusCode);
                                generalAreaDeviceModel.setStatusName(statusName);
                                generalAreaDeviceModel.setGadtmList(gadtmList);
                                gamTemp.getGadmList().add(generalAreaDeviceModel);
                            }
                            break;
                        }
                    }
                } else {
                    GeneralAreaDeviceThingModel generalAreaDeviceThingModel = new GeneralAreaDeviceThingModel();
                    generalAreaDeviceThingModel.setThingCode(thingCode);
                    generalAreaDeviceThingModel.setThingName(thingName);
                    generalAreaDeviceThingModel.setThingValue(thingValue);
                    generalAreaDeviceThingModel.setThingUnit(thingUnit);
                    generalAreaDeviceThingModel.setUpdateTime(updateTime);
                    generalAreaDeviceThingModel.setMaxValue(maxValue);
                    generalAreaDeviceThingModel.setMinValue(minValue);
                    List<GeneralAreaDeviceThingModel> gadtmList = new ArrayList<GeneralAreaDeviceThingModel>();
                    gadtmList.add(generalAreaDeviceThingModel);
                    GeneralAreaDeviceModel generalAreaDeviceModel = new GeneralAreaDeviceModel();
                    generalAreaDeviceModel.setDeviceCode(deviceCode);
                    generalAreaDeviceModel.setDeviceName(deviceName);
                    generalAreaDeviceModel.setDeviceMn(deviceMn);
                    generalAreaDeviceModel.setStatusCode(statusCode);
                    generalAreaDeviceModel.setStatusName(statusName);
                    generalAreaDeviceModel.setGadtmList(gadtmList);
                    List<GeneralAreaDeviceModel> gadmList = new ArrayList<GeneralAreaDeviceModel>();
                    gadmList.add(generalAreaDeviceModel);
                    GeneralAreaModel generalAreaModel = new GeneralAreaModel();
                    generalAreaModel.setAreaId(areaId);
                    generalAreaModel.setAreaName(areaName);
                    generalAreaModel.setGadmList(gadmList);
                    gamList.add(generalAreaModel);

                }
                //填充区域
                if (!gamListExit.contains(areaId)) {
                    gamListExit.add(areaId);
                }

                //填充设备
                if (!gadmListExit.contains(areaId + deviceCode)) {
                    gadmListExit.add(areaId + deviceCode);
                }
                //填充监测物数据
                if (!gadtmListExit.contains(areaId + deviceCode + thingCode)) {
                    gadtmListExit.add(areaId + deviceCode + thingCode);
                }
            }
            //无监测数据
            for (GeneralDeviceDataModel generalDeviceDataModel : gddmNoDataList) {

                String areaId = generalDeviceDataModel.getAreaId();
                String areaName = generalDeviceDataModel.getAreaName();
                String deviceCode = generalDeviceDataModel.getDeviceCode();
                String deviceName = generalDeviceDataModel.getDeviceName();
                String deviceMn = generalDeviceDataModel.getDeviceMn();
                String statusCode = generalDeviceDataModel.getStatusCode();
                String statusName = generalDeviceDataModel.getStatusName();
                if (deviceCode == null || deviceCode.isEmpty()) {
                    continue;
                }
                //填充
                if (gamListExit.contains(areaId)) {
                    for (GeneralAreaModel gamTemp : gamList) {
                        if (gamTemp.getAreaId().equals(areaId)) {
                            if (!gadmListExit.contains(areaId + deviceCode)) {
                                GeneralAreaDeviceModel generalAreaDeviceModel = new GeneralAreaDeviceModel();
                                generalAreaDeviceModel.setDeviceCode(deviceCode);
                                generalAreaDeviceModel.setDeviceName(deviceName);
                                generalAreaDeviceModel.setDeviceMn(deviceMn);
                                generalAreaDeviceModel.setStatusCode(statusCode);
                                generalAreaDeviceModel.setStatusName(statusName);
                                gamTemp.getGadmList().add(generalAreaDeviceModel);
                            }
                            break;
                        }
                    }
                } else {
                    GeneralAreaDeviceModel generalAreaDeviceModel = new GeneralAreaDeviceModel();
                    generalAreaDeviceModel.setDeviceCode(deviceCode);
                    generalAreaDeviceModel.setDeviceName(deviceName);
                    generalAreaDeviceModel.setDeviceMn(deviceMn);
                    generalAreaDeviceModel.setStatusCode(statusCode);
                    generalAreaDeviceModel.setStatusName(statusName);
                    List<GeneralAreaDeviceModel> gadmList = new ArrayList<GeneralAreaDeviceModel>();
                    gadmList.add(generalAreaDeviceModel);
                    GeneralAreaModel generalAreaModel = new GeneralAreaModel();
                    generalAreaModel.setAreaId(areaId);
                    generalAreaModel.setAreaName(areaName);
                    generalAreaModel.setGadmList(gadmList);
                    gamList.add(generalAreaModel);

                }
                //填充区域
                if (!gamListExit.contains(areaId)) {
                    gamListExit.add(areaId);
                }

                //填充设备
                if (!gadmListExit.contains(areaId + deviceCode)) {
                    gadmListExit.add(areaId + deviceCode);
                }
            }
            gamListExit = null;
            gadmListExit = null;
            gadtmListExit = null;
            resultListModel.setResult(true);
            resultListModel.setRows(gamList);
            resultListModel.setTotal(gamList.size());
        } catch (Exception e) {
            resultListModel.setResult(false);
            resultListModel.setDetail("获取监控数据失败，原因:" + e.getMessage());
            logger.error(LOG + ":获取大屏监控相关数据错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

}
