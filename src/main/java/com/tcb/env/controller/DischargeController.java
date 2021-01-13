package com.tcb.env.controller;

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

import com.tcb.env.model.DischargeModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IDischargeService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：排放量控制器</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年10月16日下午2:06:56
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/DischargeController")
public class DischargeController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DischargeController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DischargeController.class);

    /**
     * 声明排放量服务
     */
    @Resource
    private IDischargeService dischargeService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明键值查询服务类
     */
    @Resource
    private IMapService mapService;

    /**
     * <p>[功能描述]：获取污染排放统计数据</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param selectTime
     * @param selectType
     * @param httpsession
     * @return
     * @author 王垒, 2018年8月10日上午11:54:09
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDeviceDischarge", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<DischargeModel> queryDeviceDischarge(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag, String thingCode,
            String selectTime, String selectType, HttpSession httpsession) {
        ResultListModel<DischargeModel> resultListModel = new ResultListModel<DischargeModel>();
        List<DischargeModel> listDcm = new ArrayList<DischargeModel>();
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
            if (deviceCodeList != null && deviceCodeList.size() > 0 && thingCode != null && !thingCode.isEmpty()) {
                if ("hour".equals(selectType)) {
                    listDcm = dischargeService.getDeviceHourDischarge(deviceCodeList, thingCode, DateUtil.StringToTimestampSecond(selectTime + ":00:00"));
                } else if ("day".equals(selectType)) {
                    listDcm = dischargeService.getDeviceDayDischarge(deviceCodeList, thingCode, DateUtil.StringToTimestamp(selectTime));
                } else if ("month".equals(selectType)) {
                    listDcm = dischargeService.getDeviceMonthDischarge(deviceCodeList, thingCode, DateUtil.StringToTimestamp(selectTime + "-01"));
                } else if ("year".equals(selectType)) {
                    listDcm = dischargeService.getDeviceYearDischarge(deviceCodeList, thingCode, DateUtil.StringToTimestamp(selectTime + "-01-01"));
                }
                resultListModel.setResult(true);
                resultListModel.setRows(listDcm);
                resultListModel.setTotal(listDcm.size());
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询污染排放统计报表错误，错误信息为：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取污染排放统计数据-时间段</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param httpsession
     * @return
     * @author 王垒, 2018年8月10日上午11:57:19
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDeviceTimesDischarge", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<DischargeModel> queryDeviceTimesDischarge(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag, String thingCode,
            String beginTime, String endTime, HttpSession httpsession) {
        ResultListModel<DischargeModel> resultListModel = new ResultListModel<DischargeModel>();
        List<DischargeModel> listDcm = new ArrayList<DischargeModel>();
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
            if (deviceCodeList != null && deviceCodeList.size() > 0 && thingCode != null && !thingCode.isEmpty()) {
                listDcm = dischargeService.getDeviceTimesDischarge(deviceCodeList, thingCode,
                        DateUtil.StringToTimestamp(beginTime), DateUtil.StringToTimestamp(endTime));
                resultListModel.setResult(true);
                resultListModel.setRows(listDcm);
                resultListModel.setTotal(listDcm.size());
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询污染排放统计报表错误，错误信息为：" + e.getMessage());
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
