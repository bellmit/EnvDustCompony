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

import com.tcb.env.model.EffectiveRateModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.EffectiveRate;
import com.tcb.env.service.IEffectiveRateService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;
import com.tcb.env.util.SortListUtil;

/**
 * <p>[功能描述]：有效率控制器</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年9月29日下午3:35:45
 * @since envdust 1.0.0
 */
@Controller
@RequestMapping("/EffectiveRateController")
public class EffectiveRateController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "EffectiveRateController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(EffectiveRateController.class);

    /**
     * 声明有效率服务
     */
    @Resource
    private IEffectiveRateService effectiveRateService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * <p>[功能描述]：获取设备小时有效率</p>
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
    @RequestMapping(value = "/queryHourEffectiveRate", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EffectiveRateModel> queryHourEffectiveRate(
            String deviceCode, String dayTime, int page, int rows, HttpSession httpsession) {
        ResultListModel<EffectiveRateModel> resultListModel = new ResultListModel<EffectiveRateModel>();
        List<EffectiveRateModel> listEffectiveRateModel = new ArrayList<EffectiveRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && dayTime != null && !dayTime.isEmpty()) {
                Timestamp beginTime = DateUtil.StringToTimestampSecond(dayTime + " 00:00:00");
                Timestamp endTime = DateUtil.StringToTimestampSecond(dayTime + " 23:59:59");
                List<String> deviceCodeList = new ArrayList<String>();
                deviceCodeList.add(deviceCode);
                int count = effectiveRateService.getHourEffectiveRateCount(deviceCodeList, beginTime, endTime);
                if (count > 0) {
                    int rowIndex = (page - 1) * rows;
                    int rowCount = rows;
                    List<EffectiveRate> listEffectiveRate = effectiveRateService.getHourEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                    for (EffectiveRate temp : listEffectiveRate) {
                        EffectiveRateModel effectiveRateModel = ConvertEffectiveRateModel(temp);
                        if (effectiveRateModel != null) {
                            listEffectiveRateModel.add(effectiveRateModel);
                        }
                    }
                    resultListModel.setRows(listEffectiveRateModel);
                    resultListModel.setTotal(count);
                }
                resultListModel.setResult(true);
            } else {
                resultListModel.setResult(false);
                resultListModel.setDetail("请选择一个监测点和时间！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询小时有效率错误，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备日有效率</p>
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
    @RequestMapping(value = "/queryDayEffectiveRate", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EffectiveRateModel> queryDayEffectiveRate(
            String deviceCode, String monthTime, int page, int rows, HttpSession httpsession) {
        ResultListModel<EffectiveRateModel> resultListModel = new ResultListModel<EffectiveRateModel>();
        List<EffectiveRateModel> listEffectiveRateModel = new ArrayList<EffectiveRateModel>();
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
                int count = effectiveRateService.getDayEffectiveRateCount(deviceCodeList, beginTime, endTime);
                if (count > 0) {
                    int rowIndex = (page - 1) * rows;
                    int rowCount = rows;
                    List<EffectiveRate> listEffectiveRate = effectiveRateService.getDayEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                    for (EffectiveRate temp : listEffectiveRate) {
                        EffectiveRateModel effectiveRateModel = ConvertEffectiveRateModel(temp);
                        if (effectiveRateModel != null) {
                            listEffectiveRateModel.add(effectiveRateModel);
                        }
                    }
                    resultListModel.setRows(listEffectiveRateModel);
                    resultListModel.setTotal(count);
                }
                resultListModel.setResult(true);
            } else {
                resultListModel.setResult(false);
                resultListModel.setDetail("请选择一个监测点和时间！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询日有效率错误，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备月有效率</p>
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
    @RequestMapping(value = "/queryMonthEffectiveRate", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EffectiveRateModel> queryMonthEffectiveRate(
            String deviceCode, String yearTime, int page, int rows, HttpSession httpsession) {
        ResultListModel<EffectiveRateModel> resultListModel = new ResultListModel<EffectiveRateModel>();
        List<EffectiveRateModel> listEffectiveRateModel = new ArrayList<EffectiveRateModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        try {
            if (deviceCode != null && !deviceCode.isEmpty() && yearTime != null && !yearTime.isEmpty()) {
                Timestamp beginTime = DateUtil.StringToTimestampSecond(yearTime + "-01-01 00:00:00");
                Timestamp endTime = DateUtil.StringToTimestampSecond(yearTime + "-12-31 23:59:59");
                List<String> deviceCodeList = new ArrayList<String>();
                deviceCodeList.add(deviceCode);
                int count = effectiveRateService.getMonthEffectiveRateCount(deviceCodeList, beginTime, endTime);
                if (count > 0) {
                    int rowIndex = (page - 1) * rows;
                    int rowCount = rows;
                    List<EffectiveRate> listEffectiveRate = effectiveRateService.getMonthEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                    for (EffectiveRate temp : listEffectiveRate) {
                        EffectiveRateModel effectiveRateModel = ConvertEffectiveRateModel(temp);
                        if (effectiveRateModel != null) {
                            listEffectiveRateModel.add(effectiveRateModel);
                        }
                    }
                    resultListModel.setRows(listEffectiveRateModel);
                    resultListModel.setTotal(count);
                }
                resultListModel.setResult(true);
            } else {
                resultListModel.setResult(false);
                resultListModel.setDetail("请选择一个监测点和时间！");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询月有效率错误，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备日有效率排名</p>
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
    @RequestMapping(value = "/queryDayEffectiveRateRanking", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EffectiveRateModel> queryDayEffectiveRateRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String time, int page, int rows, HttpSession httpsession) {
        ResultListModel<EffectiveRateModel> resultListModel = new ResultListModel<EffectiveRateModel>();
        List<EffectiveRateModel> listEffectiveRateModel = new ArrayList<EffectiveRateModel>();
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
                    int count = effectiveRateService.getDayEffectiveRateCount(deviceCodeList, beginTime, endTime);
                    if (count > 0) {
                        int rowIndex = (page - 1) * rows;
                        int rowCount = rows;
                        List<EffectiveRate> listEffectiveRate = effectiveRateService.getDayEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                        for (EffectiveRate temp : listEffectiveRate) {
                            EffectiveRateModel effectiveRateModel = ConvertEffectiveRateModel(temp);
                            if (effectiveRateModel != null) {
                                listEffectiveRateModel.add(effectiveRateModel);
                            }
                        }
                        SortListUtil<EffectiveRateModel> sortList = new SortListUtil<EffectiveRateModel>();
                        sortList.sort(listEffectiveRateModel, "getEffectiveRate", "desc");
                        resultListModel.setRows(listEffectiveRateModel);
                        resultListModel.setTotal(count);
                    }
                    resultListModel.setResult(true);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询日有效率排序错误，原因：" + e.getMessage());
            }
        } else {
            resultListModel.setResult(false);
            resultListModel.setDetail("请选择一个监控区域或设备！");
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备月有效率排名</p>
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
    @RequestMapping(value = "/queryMonthEffectiveRateRanking", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EffectiveRateModel> queryMonthEffectiveRateRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String time, int page, int rows, HttpSession httpsession) {
        ResultListModel<EffectiveRateModel> resultListModel = new ResultListModel<EffectiveRateModel>();
        List<EffectiveRateModel> listEffectiveRateModel = new ArrayList<EffectiveRateModel>();
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
                    int count = effectiveRateService.getMonthEffectiveRateCount(deviceCodeList, beginTime, endTime);
                    if (count > 0) {
                        int rowIndex = (page - 1) * rows;
                        int rowCount = rows;
                        List<EffectiveRate> listEffectiveRate = effectiveRateService.getMonthEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                        for (EffectiveRate temp : listEffectiveRate) {
                            EffectiveRateModel effectiveRateModel = ConvertEffectiveRateModel(temp);
                            if (effectiveRateModel != null) {
                                listEffectiveRateModel.add(effectiveRateModel);
                            }
                        }
                        SortListUtil<EffectiveRateModel> sortList = new SortListUtil<EffectiveRateModel>();
                        sortList.sort(listEffectiveRateModel, "getEffectiveRate", "desc");
                        resultListModel.setRows(listEffectiveRateModel);
                        resultListModel.setTotal(count);
                    }
                    resultListModel.setResult(true);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询月有效率排序错误，原因：" + e.getMessage());
            }
        } else {
            resultListModel.setResult(false);
            resultListModel.setDetail("请选择一个监控区域或设备！");
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：获取设备年有效率排名</p>
     *
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
    @RequestMapping(value = "/queryYearEffectiveRateRanking", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EffectiveRateModel> queryYearEffectiveRateRanking(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "list[]") List<String> list, String levelFlag,
            String time, int page, int rows, HttpSession httpsession) {
        ResultListModel<EffectiveRateModel> resultListModel = new ResultListModel<EffectiveRateModel>();
        List<EffectiveRateModel> listEffectiveRateModel = new ArrayList<EffectiveRateModel>();
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
                    int count = effectiveRateService.getYearEffectiveRateCount(deviceCodeList, beginTime, endTime);
                    if (count > 0) {
                        int rowIndex = (page - 1) * rows;
                        int rowCount = rows;
                        List<EffectiveRate> listEffectiveRate = effectiveRateService.getYearEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
                        for (EffectiveRate temp : listEffectiveRate) {
                            EffectiveRateModel effectiveRateModel = ConvertEffectiveRateModel(temp);
                            if (effectiveRateModel != null) {
                                listEffectiveRateModel.add(effectiveRateModel);
                            }
                        }
                        SortListUtil<EffectiveRateModel> sortList = new SortListUtil<EffectiveRateModel>();
                        sortList.sort(listEffectiveRateModel, "getEffectiveRate", "desc");
                        resultListModel.setRows(listEffectiveRateModel);
                        resultListModel.setTotal(count);
                    }
                    resultListModel.setResult(true);
                }
            } catch (Exception e) {
                logger.error(LOG + ":查询年有效率排序错误，原因：" + e.getMessage());
            }
        } else {
            resultListModel.setResult(false);
            resultListModel.setDetail("请选择一个监控区域或设备！");
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：EffectiveRate转换为EffectiveRateModel</p>
     *
     * @param effectiveRate
     * @return
     * @author 王垒, 2017年9月30日上午10:31:13
     * @since envdust 1.0.0
     */
    private EffectiveRateModel ConvertEffectiveRateModel(EffectiveRate effectiveRate) {
        EffectiveRateModel effectiveRateModel = new EffectiveRateModel();
        if (effectiveRate != null) {
            effectiveRateModel.setErId(String.valueOf(effectiveRate.getErId()));
            if (effectiveRate.getDevice() != null) {
                effectiveRateModel.setDeviceCode(effectiveRate.getDevice().getDeviceCode());
                effectiveRateModel.setDeviceName(effectiveRate.getDevice().getDeviceName());
            }
            effectiveRateModel.setEffectiveTime(effectiveRate.getEffectiveTime());
            effectiveRateModel.setEffectiveRate(String.format("%.2f", effectiveRate.getEffectiveRate() * 100));
            effectiveRateModel.setEffectiveInfo(effectiveRate.getEffectiveInfo());
            effectiveRateModel.setAllCount(String.valueOf(effectiveRate.getAllCount()));
            effectiveRateModel.setErCount(String.valueOf(effectiveRate.getEffectiveRate()));
        }
        return effectiveRateModel;
    }

}
