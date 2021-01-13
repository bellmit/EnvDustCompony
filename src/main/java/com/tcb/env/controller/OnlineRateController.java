package com.tcb.env.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.OnlineRateModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.OnlineRate;
import com.tcb.env.service.IOnlineRateService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;
import com.tcb.env.util.SortListUtil;

/**
 * <p>[功能描述]：在线率控制器</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年9月29日下午3:39:22
 * @since envdust 1.0.0
 */
@Controller
@RequestMapping("/OnlineRateController")
public class OnlineRateController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "OnlineRateController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(OnlineRateController.class);

    /**
     * 声明在线率服务
     */
    @Resource
    private IOnlineRateService onlineRateService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * <p>[功能描述]：获取设备小时在线率</p>
     *
     * @param deviceCode
     * @param dayTime
     * @param page
     * @param rows
     * @param httpsession
     * @return
     * @author 王垒, 2017年9月30日上午10:40:30
     * @since envdust 1.0.0
     */
    @RequestMapping(value = "/queryHourOnlineRate", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<OnlineRateModel> queryHourOnlineRate(
            String deviceCode, String dayTime, int page, int rows, HttpSession httpsession) {
        ResultListModel<OnlineRateModel> resultListModel = new ResultListModel<OnlineRateModel>();
        List<OnlineRateModel> listOnlineRateModel = new ArrayList<OnlineRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && dayTime != null && !dayTime.isEmpty()) {
                Timestamp beginTime = DateUtil.StringToTimestampSecond(dayTime + " 00:00:00");
                Timestamp endTime = DateUtil.StringToTimestampSecond(dayTime + " 23:59:59");
                List<String> deviceCodeList = new ArrayList<String>();
                deviceCodeList.add(deviceCode);
                int count = onlineRateService.getHourOnlineRateCount(deviceCodeList, beginTime, endTime);
                if (count > 0) {
                    int rowIndex = DefaultArgument.INT_DEFAULT;
                    int rowCount = DefaultArgument.INT_DEFAULT;
                    if (page >= 0 && rows >= 0) {
                        rowIndex = (page - 1) * rows;
                        rowCount = rows;
                    }
                    List<OnlineRate> listOnlineRate = onlineRateService.getHourOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                    for (OnlineRate temp : listOnlineRate) {
                        OnlineRateModel onlineRateModel = ConvertOnlineRateModel(temp);
                        if (onlineRateModel != null) {
                            listOnlineRateModel.add(onlineRateModel);
                        }
                    }
                    resultListModel.setRows(listOnlineRateModel);
                    resultListModel.setTotal(count);
                }
                resultListModel.setResult(true);
            } else {
                resultListModel.setResult(false);
                resultListModel.setDetail("请选择一个监测点和时间！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询小时在线率错误，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备日在线率</p>
     *
     * @param deviceCode
     * @param monthTime
     * @param page
     * @param rows
     * @param httpsession
     * @return
     * @author 王垒, 2017年9月30日上午10:40:30
     * @since envdust 1.0.0
     */
    @RequestMapping(value = "/queryDayOnlineRate", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<OnlineRateModel> queryDayOnlineRate(
            String deviceCode, String monthTime, int page, int rows, HttpSession httpsession) {
        ResultListModel<OnlineRateModel> resultListModel = new ResultListModel<OnlineRateModel>();
        List<OnlineRateModel> listOnlineRateModel = new ArrayList<OnlineRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && monthTime != null && !monthTime.isEmpty()) {
                Timestamp beginTime = DateUtil.StringToTimestampSecond(monthTime + "-01 00:00:00");
                Calendar calendar = DateUtil.StringToCalendar(DateUtil.TimestampToString(beginTime, DateUtil.DATA_TIME));
                int maxDate = DateUtil.getMonthDays(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                Timestamp endTime = DateUtil.StringToTimestampSecond(monthTime + "-" + maxDate + " 23:59:59");
                List<String> deviceCodeList = new ArrayList<String>();
                deviceCodeList.add(deviceCode);
                int count = onlineRateService.getDayOnlineRateCount(deviceCodeList, beginTime, endTime);
                if (count > 0) {
                    int rowIndex = (page - 1) * rows;
                    int rowCount = rows;
                    List<OnlineRate> listOnlineRate = onlineRateService.getDayOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                    for (OnlineRate temp : listOnlineRate) {
                        OnlineRateModel onlineRateModel = ConvertOnlineRateModel(temp);
                        if (onlineRateModel != null) {
                            listOnlineRateModel.add(onlineRateModel);
                        }
                    }
                    resultListModel.setRows(listOnlineRateModel);
                    resultListModel.setTotal(count);
                }
                resultListModel.setResult(true);
            } else {
                resultListModel.setResult(false);
                resultListModel.setDetail("请选择一个监测点和时间！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询日在线率错误，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备月在线率</p>
     *
     * @param deviceCode
     * @param yearTime
     * @param page
     * @param rows
     * @param httpsession
     * @return
     * @author 王垒, 2017年9月30日上午10:40:30
     * @since envdust 1.0.0
     */
    @RequestMapping(value = "/queryMonthOnlineRate", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<OnlineRateModel> queryMonthOnlineRate(
            String deviceCode, String yearTime, int page, int rows, HttpSession httpsession) {
        ResultListModel<OnlineRateModel> resultListModel = new ResultListModel<OnlineRateModel>();
        List<OnlineRateModel> listOnlineRateModel = new ArrayList<OnlineRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && yearTime != null && !yearTime.isEmpty()) {
                Timestamp beginTime = DateUtil.StringToTimestampSecond(yearTime + "-01-01 00:00:00");
                Timestamp endTime = DateUtil.StringToTimestampSecond(yearTime + "-12-31 23:59:59");
                List<String> deviceCodeList = new ArrayList<String>();
                deviceCodeList.add(deviceCode);
                int count = onlineRateService.getMonthOnlineRateCount(deviceCodeList, beginTime, endTime);
                if (count > 0) {
                    int rowIndex = (page - 1) * rows;
                    int rowCount = rows;
                    List<OnlineRate> listOnlineRate = onlineRateService.getMonthOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                    for (OnlineRate temp : listOnlineRate) {
                        OnlineRateModel onlineRateModel = ConvertOnlineRateModel(temp);
                        if (onlineRateModel != null) {
                            listOnlineRateModel.add(onlineRateModel);
                        }
                    }
                    resultListModel.setRows(listOnlineRateModel);
                    resultListModel.setTotal(count);
                }
                resultListModel.setResult(true);
            } else {
                resultListModel.setResult(false);
                resultListModel.setDetail("请选择一个监测点和时间！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询月在线率错误，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备日在线率排名</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param time
     * @param page
     * @param rows
     * @param httpsession
     * @return
     * @author 王垒, 2017年10月11日上午8:27:38
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryDayOnlineRateRanking", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<OnlineRateModel> queryDayOnlineRateRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String time, int page, int rows, HttpSession httpsession) {
        ResultListModel<OnlineRateModel> resultListModel = new ResultListModel<OnlineRateModel>();
        List<OnlineRateModel> listOnlineRateModel = new ArrayList<OnlineRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0 && time != null && !time.isEmpty()) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return resultListModel;
                } else {
                    List<String> deviceCodeList = new ArrayList<String>();
                    List<Integer> areaIdList = new ArrayList<Integer>();
                    if (levelFlag != null && !levelFlag.isEmpty()) {
                        areaIdList = treeService.getAuthorityBottomArea(projectId, areaIdList, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                        for (TreeModel treeModel : listDev) {
                            deviceCodeList.add(treeModel.getId());
                        }
                    } else if (areaIdList != null && areaIdList.size() > 0) {
                        for (Integer areaid : areaIdList) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, null);
                            for (TreeModel treeModel : listDev) {
                                deviceCodeList.add(treeModel.getId());
                            }
                        }
                    } else {
                        for (String temp : list) {
                            deviceCodeList.add(temp);
                        }
                    }
                    Timestamp beginTime = DateUtil.StringToTimestampSecond(time + " 00:00:00");
                    Timestamp endTime = DateUtil.StringToTimestampSecond(time + " 23:59:59");
                    int count = onlineRateService.getDayOnlineRateCount(deviceCodeList, beginTime, endTime);
                    if (count > 0) {
                        int rowIndex = (page - 1) * rows;
                        int rowCount = rows;
                        List<OnlineRate> listOnlineRate = onlineRateService.getDayOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                        for (OnlineRate temp : listOnlineRate) {
                            OnlineRateModel onlineRateModel = ConvertOnlineRateModel(temp);
                            if (onlineRateModel != null) {
                                listOnlineRateModel.add(onlineRateModel);
                            }
                        }
                        SortListUtil<OnlineRateModel> sortList = new SortListUtil<OnlineRateModel>();
                        sortList.sort(listOnlineRateModel, "getOnlineRate", "desc");
                        resultListModel.setRows(listOnlineRateModel);
                        resultListModel.setTotal(count);
                    }
                    resultListModel.setResult(true);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询日在线率排序错误，原因：" + e.getMessage());
            }
        } else {
            resultListModel.setResult(false);
            resultListModel.setDetail("请选择一个监控区域或设备！");
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备月在线率排名</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param time
     * @param page
     * @param rows
     * @param httpsession
     * @return
     * @author 王垒, 2017年10月11日上午8:27:51
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryMonthOnlineRateRanking", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<OnlineRateModel> queryMonthOnlineRateRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String time, int page, int rows, HttpSession httpsession) {
        ResultListModel<OnlineRateModel> resultListModel = new ResultListModel<OnlineRateModel>();
        List<OnlineRateModel> listOnlineRateModel = new ArrayList<OnlineRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0 && time != null && !time.isEmpty()) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return resultListModel;
                } else {
                    List<String> deviceCodeList = new ArrayList<String>();
                    List<Integer> areaIdList = new ArrayList<Integer>();
                    if (levelFlag != null && !levelFlag.isEmpty()) {
                        areaIdList = treeService.getAuthorityBottomArea(projectId, areaIdList, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                        for (TreeModel treeModel : listDev) {
                            deviceCodeList.add(treeModel.getId());
                        }
                    } else if (areaIdList != null && areaIdList.size() > 0) {
                        for (Integer areaid : areaIdList) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, null);
                            for (TreeModel treeModel : listDev) {
                                deviceCodeList.add(treeModel.getId());
                            }
                        }
                    } else {
                        for (String temp : list) {
                            deviceCodeList.add(temp);
                        }
                    }
                    Timestamp beginTime = DateUtil.StringToTimestampSecond(time + "-01 00:00:00");
                    Calendar calendar = DateUtil.StringToCalendar(DateUtil.TimestampToString(beginTime, DateUtil.DATA_TIME));
                    int maxDate = DateUtil.getMonthDays(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
                    Timestamp endTime = DateUtil.StringToTimestampSecond(time + "-" + maxDate + " 23:59:59");
                    int count = onlineRateService.getMonthOnlineRateCount(deviceCodeList, beginTime, endTime);
                    if (count > 0) {
                        int rowIndex = (page - 1) * rows;
                        int rowCount = rows;
                        List<OnlineRate> listOnlineRate = onlineRateService.getMonthOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                        for (OnlineRate temp : listOnlineRate) {
                            OnlineRateModel onlineRateModel = ConvertOnlineRateModel(temp);
                            if (onlineRateModel != null) {
                                listOnlineRateModel.add(onlineRateModel);
                            }
                        }
                        SortListUtil<OnlineRateModel> sortList = new SortListUtil<OnlineRateModel>();
                        sortList.sort(listOnlineRateModel, "getOnlineRate", "desc");
                        resultListModel.setRows(listOnlineRateModel);
                        resultListModel.setTotal(count);
                    }
                    resultListModel.setResult(true);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询月在线率排序错误，原因：" + e.getMessage());
            }
        } else {
            resultListModel.setResult(false);
            resultListModel.setDetail("请选择一个监控区域或设备！");
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备年在线率排名</p>
     *
     * @param projectId
     * @param list
     * @param levelFlag
     * @param time
     * @param page
     * @param rows
     * @param httpsession
     * @return
     * @author 王垒, 2017年10月11日上午8:27:51
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryYearOnlineRateRanking", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<OnlineRateModel> queryYearOnlineRateRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String time, int page, int rows, HttpSession httpsession) {
        ResultListModel<OnlineRateModel> resultListModel = new ResultListModel<OnlineRateModel>();
        List<OnlineRateModel> listOnlineRateModel = new ArrayList<OnlineRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (list != null && list.size() > 0 && time != null && !time.isEmpty()) {
            try {
                if (DefaultArgument.NONE_DEFAULT.equals(list.get(0))) {
                    return resultListModel;
                } else {
                    List<String> deviceCodeList = new ArrayList<String>();
                    List<Integer> areaIdList = new ArrayList<Integer>();
                    if (levelFlag != null && !levelFlag.isEmpty()) {
                        areaIdList = treeService.getAuthorityBottomArea(projectId, areaIdList, Integer.valueOf(list.get(0)), usercode);
                    }
                    if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(list.get(0))) {
                        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
                        for (TreeModel treeModel : listDev) {
                            deviceCodeList.add(treeModel.getId());
                        }
                    } else if (areaIdList != null && areaIdList.size() > 0) {
                        for (Integer areaid : areaIdList) {
                            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, areaid, null, null, null);
                            for (TreeModel treeModel : listDev) {
                                deviceCodeList.add(treeModel.getId());
                            }
                        }
                    } else {
                        for (String temp : list) {
                            deviceCodeList.add(temp);
                        }
                    }
                    Timestamp beginTime = DateUtil.StringToTimestampSecond(time + "-01-01 00:00:00");
                    Timestamp endTime = DateUtil.StringToTimestampSecond(time + "-12-31 23:59:59");
                    int count = onlineRateService.getYearOnlineRateCount(deviceCodeList, beginTime, endTime);
                    if (count > 0) {
                        int rowIndex = (page - 1) * rows;
                        int rowCount = rows;
                        List<OnlineRate> listOnlineRate = onlineRateService.getYearOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                        for (OnlineRate temp : listOnlineRate) {
                            OnlineRateModel onlineRateModel = ConvertOnlineRateModel(temp);
                            if (onlineRateModel != null) {
                                listOnlineRateModel.add(onlineRateModel);
                            }
                        }
                        SortListUtil<OnlineRateModel> sortList = new SortListUtil<OnlineRateModel>();
                        sortList.sort(listOnlineRateModel, "getOnlineRate", "desc");
                        resultListModel.setRows(listOnlineRateModel);
                        resultListModel.setTotal(count);
                    }
                    resultListModel.setResult(true);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询年在线率排序错误，原因：" + e.getMessage());
            }
        } else {
            resultListModel.setResult(false);
            resultListModel.setDetail("请选择一个监控区域或设备！");
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：OnlineRate转换为OnlineRateModel</p>
     *
     * @param onlineRate
     * @return
     * @author 王垒, 2017年9月30日上午10:31:13
     * @since envdust 1.0.0
     */
    private OnlineRateModel ConvertOnlineRateModel(OnlineRate onlineRate) {
        OnlineRateModel onlineRateModel = new OnlineRateModel();
        if (onlineRate != null) {
            onlineRateModel.setOrId(String.valueOf(onlineRate.getOrId()));
            if (onlineRate.getDevice() != null) {
                onlineRateModel.setDeviceCode(onlineRate.getDevice().getDeviceCode());
                onlineRateModel.setDeviceName(onlineRate.getDevice().getDeviceName());
            }
            onlineRateModel.setOnlineTime(onlineRate.getOnlineTime());
            onlineRateModel.setOnlineRate(String.format("%.2f", (onlineRate.getOnlineRate() > 1 ? 1 : onlineRate.getOnlineRate()) * 100));
            onlineRateModel.setOnlineInfo(onlineRate.getOnlineInfo());
            onlineRateModel.setAllCount(String.valueOf(onlineRate.getAllCount()));
            onlineRateModel.setOrCount(String.valueOf(onlineRate.getOnlineRate()));
        }
        return onlineRateModel;
    }

}
