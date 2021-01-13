package com.tcb.env.controller;

import java.util.ArrayList;
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

import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.RldPlanModel;
import com.tcb.env.model.SpanTimeModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IReviewDataService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>
 * [功能描述]：计划上传数据控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月7日上午8:39:22
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/ReviewDataController")
public class ReviewDataController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "ReviewDataController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(ReviewDataController.class);

    /**
     * 声明计划上传数据服务
     */
    @Resource
    private IReviewDataService reviewDataService;

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    /**
     * <p>
     * [功能描述]：获取实时数据计划
     * </p>
     *
     * @param projectId
     * @param rldPlanModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月7日上午8:43:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getRldPlanData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<RldPlanModel> getRldPlanData(
            @RequestParam(value = "projectId", required = false) String projectId,
            RldPlanModel rldPlanModel, HttpSession httpsession) {
        ResultListModel<RldPlanModel> resultListModel = new ResultListModel<RldPlanModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            //ResultListModel 向 CommMain进行类型转换
            CommMain commMain = ConvertCommMain(rldPlanModel, httpsession);
            List<String> listdevicecode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listdevicecode.add(treeModel.getId());
            }
            if (listdevicecode != null && listdevicecode.size() > 0
                    && rldPlanModel != null) {
                List<String> listCnCode = new ArrayList<String>();
                if (rldPlanModel.getCnCode() == null
                        || rldPlanModel.getCnCode().isEmpty()) {
                    listCnCode.add(DefaultArgument.PRO_CN_REALBEGIN);
                    listCnCode.add(DefaultArgument.PRO_CN_REALSTOP);
                } else {
                    listCnCode.add(rldPlanModel.getCnCode());
                }
                int count = reviewDataService.getReviewDataCount(commMain,
                        listdevicecode, listCnCode);
                if (count > 0) {
                    List<RldPlanModel> listModel = new ArrayList<RldPlanModel>();
                    List<CommMain> list = reviewDataService.getReviewData(
                            commMain, listdevicecode, listCnCode);
                    for (CommMain commMainTemp : list) {
                        //CommMain 向  RldPlanModel进行数据转换
                        RldPlanModel rldPlanModelTemp = ConvertRldPlanModel(commMainTemp);
                        if (rldPlanModelTemp != null) {
                            listModel.add(rldPlanModelTemp);
                        }
                    }
                    resultListModel.setTotal(count);
                    resultListModel.setRows(listModel);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询提取实时数据计划失败，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：获取分段数据计划
     * </p>
     *
     * @param projectId
     * @param spanTimeModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月7日上午8:43:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getSpanTimeData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<SpanTimeModel> getSpanTimeData(
            @RequestParam(value = "projectId", required = false) String projectId,
            SpanTimeModel spanTimeModel, HttpSession httpsession) {
        ResultListModel<SpanTimeModel> resultListModel = new ResultListModel<SpanTimeModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            //SpanTimeModel 转换为 CommMain 类型
            CommMain commMain = ConvertCommMain(spanTimeModel, httpsession);
            List<String> listdevicecode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listdevicecode.add(treeModel.getId());
            }
            if (listdevicecode != null && listdevicecode.size() > 0 && spanTimeModel != null) {
                List<String> listCnCode = new ArrayList<String>();
                if (spanTimeModel.getCnCode() == null || spanTimeModel.getCnCode().isEmpty()) {
                    listCnCode.add(DefaultArgument.PRO_CN_GETMINUTE);
                    listCnCode.add(DefaultArgument.PRO_GET_HOUR);
                } else {
                    listCnCode.add(spanTimeModel.getCnCode());
                }
                int count = reviewDataService.getReviewDataCount(commMain, listdevicecode, listCnCode);
                if (count > 0) {
                    List<SpanTimeModel> listModel = new ArrayList<SpanTimeModel>();
                    List<CommMain> list = reviewDataService.getReviewData(commMain, listdevicecode, listCnCode);
                    for (CommMain commMainTemp : list) {
                        //CommMain 转换为 SpanTimeModel 类型
                        SpanTimeModel spanTimeModelTemp = ConvertSpanTimeModel(commMainTemp);
                        if (spanTimeModelTemp != null) {
                            listModel.add(spanTimeModelTemp);
                        }
                    }
                    resultListModel.setTotal(count);
                    resultListModel.setRows(listModel);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询提取分段数据计划失败，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：插入数据上传间隔
     * </p>
     *
     * @param rldCnCode
     * @param rldExcuteTime
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:24:20
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertRldPlanData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertRldPlanData(
            String rldCnCode, String rldExcuteTime,
            @RequestParam(value = "list[]") List<String> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        try {
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                resultModel.setDetail("登录超时，请重新登陆后操作！");
                return resultModel;
            }
            if (list != null && list.size() > 0 && rldCnCode != null
                    && !rldCnCode.isEmpty() && rldExcuteTime != null
                    && !rldExcuteTime.isEmpty()) {
                int userid;
                UserModel loginuser = (UserModel) httpsession
                        .getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userid = loginuser.getUserId();
                } else {
                    resultModel.setDetail("登录超时，请重新登陆后操作！");
                    return resultModel;
                }
                int count = reviewDataService.insertRldData(rldCnCode,
                        rldExcuteTime, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("设置获取实时数据失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：设置获取实时数据失败，原因：" + e.getMessage());
            resultModel.setDetail("设置获取实时数据失败！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：插入分段数据
     * </p>
     *
     * @param stCnCode
     * @param stExcuteTime
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:24:20
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertSpanTimeData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertSpanTimeData(String stCnCode,
                                   String stBeginTime, String stEndTime, String stExcuteTime,
                                   @RequestParam(value = "list[]") List<String> list,
                                   HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        try {
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                resultModel.setDetail("登录超时，请重新登陆后操作！");
                return resultModel;
            }
            if (list != null && list.size() > 0 && stCnCode != null
                    && !stCnCode.isEmpty() && stBeginTime != null
                    && !stBeginTime.isEmpty() && stEndTime != null
                    && !stEndTime.isEmpty() && stExcuteTime != null
                    && !stExcuteTime.isEmpty()) {
                int userid;
                UserModel loginuser = (UserModel) httpsession
                        .getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userid = loginuser.getUserId();
                } else {
                    resultModel.setDetail("登录超时，请重新登陆后操作！");
                    return resultModel;
                }
                int count = reviewDataService.insertSpanTimeData(stCnCode,
                        stBeginTime, stEndTime, stExcuteTime, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("设置获取实时数据失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：设置获取实时数据失败，原因：" + e.getMessage());
            resultModel.setDetail("设置获取实时数据失败！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：删除获取数据
     * </p>
     *
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日下午2:15:31
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteReviewData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteReviewData(
            @RequestParam(value = "list[]") List<Integer> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listCommId = new ArrayList<Integer>();
                for (Integer commId : list) {
                    listCommId.add(commId);
                }
                int intresult = reviewDataService.deleteReviewData(listCommId);
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

    /**
     * <p>
     * [功能描述]：将RldPlanModel转换成CommMain
     * </p>
     *
     * @param rldPlanModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:54:40
     * @since EnvDust 1.0.0
     */
    private CommMain ConvertCommMain(RldPlanModel rldPlanModel,
                                     HttpSession httpsession) {
        CommMain commMain = new CommMain();
        if (rldPlanModel != null) {
            CommCn commCn = new CommCn();
            commCn.setCnCode(rldPlanModel.getCnCode());
            commMain.setCommCn(commCn);
            Device device = new Device();
            device.setDeviceName(rldPlanModel.getDeviceName());
            Area area = new Area();
            if (rldPlanModel.getAreaId() != null && !rldPlanModel.getAreaId().isEmpty())
                area.setAreaId(Integer.valueOf(rldPlanModel.getAreaId()));
            area.setAreaName(rldPlanModel.getAreaName());
            device.setArea(area);
            commMain.setDevice(device);
            commMain.setRowCount(rldPlanModel.getRows());
            commMain.setRowIndex((rldPlanModel.getPage() - 1)
                    * rldPlanModel.getRows());
        }
        return commMain;
    }

    /**
     * <p>
     * [功能描述]：将CommMain转换成rldPlanModel
     * </p>
     *
     * @param commMain
     * @return
     * @author 王垒, 2016年6月6日上午10:00:54
     * @since EnvDust 1.0.0
     */
    private RldPlanModel ConvertRldPlanModel(CommMain commMain) {
        RldPlanModel rldPlanModel = new RldPlanModel();
        if (commMain != null) {
            rldPlanModel.setCommId(String.valueOf(commMain.getCommId()));
            Device device = commMain.getDevice();
            if (device != null) {
                rldPlanModel.setDeviceCode(device.getDeviceCode());
                rldPlanModel.setDeviceMn(device.getDeviceMn());
                rldPlanModel.setDeviceName(device.getDeviceName());
                Area area = device.getArea();
                if (area != null) {
                    rldPlanModel.setAreaId(String.valueOf(area.getAreaId()));
                    rldPlanModel.setAreaName(area.getAreaName());
                }
                DeviceProject deviceProject = device.getDeviceProject();
                if (deviceProject != null) {
                    rldPlanModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                    rldPlanModel.setProjectName(deviceProject.getProjectName());
                }
            }
            CommCn commCn = commMain.getCommCn();
            if (commCn != null) {
                rldPlanModel.setCnCode(commCn.getCnCode());
                rldPlanModel.setCnName(commCn.getCnName());
            }
            CommStatus commStatus = commMain.getCommStatus();
            if (commStatus != null) {
                rldPlanModel.setStatusName(commStatus.getStatusName());
            }
            rldPlanModel.setExcuteTime(DateUtil.TimestampToString(commMain.getExcuteTime(), DateUtil.DATA_TIME));
            rldPlanModel.setOptUserName(userService.getUserNameById(commMain.getOptUser(), null));
            rldPlanModel.setOptTime(DateUtil.TimestampToString(commMain.getOptTime(), DateUtil.DATA_TIME));
        }
        return rldPlanModel;
    }

    /**
     * <p>
     * [功能描述]：将SpanTimeModel转换成CommMain
     * </p>
     *
     * @param spanTimeModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:54:40
     * @since EnvDust 1.0.0
     */
    private CommMain ConvertCommMain(SpanTimeModel spanTimeModel,
                                     HttpSession httpsession) {
        CommMain commMain = new CommMain();
        if (spanTimeModel != null) {
            CommCn commCn = new CommCn();
            commCn.setCnCode(spanTimeModel.getCnCode());
            commMain.setCommCn(commCn);
            Device device = new Device();
            device.setDeviceName(spanTimeModel.getDeviceName());
            Area area = new Area();
            if (spanTimeModel.getAreaId() != null && !spanTimeModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(spanTimeModel.getAreaId()));
            }
            area.setAreaName(spanTimeModel.getAreaName());
            device.setArea(area);
            commMain.setDevice(device);
            commMain.setRowCount(spanTimeModel.getRows());
            commMain.setRowIndex((spanTimeModel.getPage() - 1)
                    * spanTimeModel.getRows());
        }
        return commMain;
    }

    /**
     * <p>
     * [功能描述]：将CommMain转换成SpanTimeModel
     * </p>
     *
     * @param commMain
     * @return
     * @author 王垒, 2016年6月6日上午10:00:54
     * @since EnvDust 1.0.0
     */
    private SpanTimeModel ConvertSpanTimeModel(CommMain commMain) {
        SpanTimeModel spanTimeModel = new SpanTimeModel();
        if (commMain != null) {
            spanTimeModel.setCommId(String.valueOf(commMain.getCommId()));
            Device device = commMain.getDevice();
            if (device != null) {
                spanTimeModel.setDeviceCode(device.getDeviceCode());
                spanTimeModel.setDeviceMn(device.getDeviceMn());
                spanTimeModel.setDeviceName(device.getDeviceName());
                Area area = device.getArea();
                if (area != null) {
                    spanTimeModel.setAreaId(String.valueOf(area.getAreaId()));
                    spanTimeModel.setAreaName(area.getAreaName());
                }
                DeviceProject deviceProject = device.getDeviceProject();
                if (deviceProject != null) {
                    spanTimeModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                    spanTimeModel.setProjectName(deviceProject.getProjectName());
                }
            }
            CommCn commCn = commMain.getCommCn();
            if (commCn != null) {
                spanTimeModel.setCnCode(commCn.getCnCode());
                spanTimeModel.setCnName(commCn.getCnName());
            }
            CommStatus commStatus = commMain.getCommStatus();
            if (commStatus != null) {
                spanTimeModel.setStatusName(commStatus.getStatusName());
            }
            String cp = commMain.getCp();
            if (cp != null && !cp.isEmpty()) {
                if (cp != null && cp.indexOf(",") >= 0) {
                    String[] cpArray = cp.split(",");
                    for (String temp : cpArray) {
                        if (temp != null && temp.indexOf("BeginTime=") >= 0) {
                            String beginTime = temp.replace("BeginTime=", "");
                            beginTime = DateUtil.TimestampToString(DateUtil.StringToTimestampFormat(beginTime, DateUtil.DATA_TIME_SER), DateUtil.DATA_TIME);
                            spanTimeModel.setBeginTime(beginTime);

                        } else if (temp != null && temp.indexOf("EndTime=") >= 0) {
                            String endTime = temp.replace("EndTime=", "");
                            endTime = DateUtil.TimestampToString(DateUtil.StringToTimestampFormat(endTime, DateUtil.DATA_TIME_SER), DateUtil.DATA_TIME);
                            spanTimeModel.setEndTime(endTime);
                        }
                    }
                }
            }
            spanTimeModel.setExcuteTime(DateUtil.TimestampToString(
                    commMain.getExcuteTime(), DateUtil.DATA_TIME));
            spanTimeModel.setOptUserName(userService.getUserNameById(
                    commMain.getOptUser(), null));
            spanTimeModel.setOptTime(DateUtil.TimestampToString(
                    commMain.getOptTime(), DateUtil.DATA_TIME));
        }
        return spanTimeModel;
    }
}
