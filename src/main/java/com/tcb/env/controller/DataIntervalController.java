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

import com.tcb.env.model.DataIntervalModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IDataIntervalService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>
 * [功能描述]：设置数据间隔控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月6日上午8:50:07
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/DataIntervalController")
public class DataIntervalController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AuthorityDetailController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger
            .getLogger(DataIntervalController.class);

    /**
     * 声明数据上传间隔服务
     */
    @Resource
    private IDataIntervalService dataIntervalService;

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
     * [功能描述]：获取数据上传间隔
     * </p>
     *
     * @param projectId
     * @param dataIntervalModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月1日上午8:45:24
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getDataInterval", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<DataIntervalModel> getDataInterval(
            @RequestParam(value = "projectId", required = false) String projectId,
            DataIntervalModel dataIntervalModel, HttpSession httpsession) {
        ResultListModel<DataIntervalModel> resultListModel = new ResultListModel<DataIntervalModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession
                    .getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            CommMain commMain = ConvertCommMain(dataIntervalModel, httpsession);
            List<String> listdevicecode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId,
                    DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listdevicecode.add(treeModel.getId());
            }
            if (listdevicecode != null && listdevicecode.size() > 0
                    && dataIntervalModel != null) {
                List<String> listCnCode = new ArrayList<String>();
                if (dataIntervalModel.getCnCode() == null
                        || dataIntervalModel.getCnCode().isEmpty()) {
                    listCnCode.add(DefaultArgument.PRO_CN_REALINTERVAL);
                } else {
                    listCnCode.add(dataIntervalModel.getCnCode());
                }
                int count = dataIntervalService.getDataIntervalCount(commMain,
                        listdevicecode, listCnCode);
                if (count > 0) {
                    List<DataIntervalModel> listModel = new ArrayList<DataIntervalModel>();
                    List<CommMain> list = dataIntervalService.getDataInterval(commMain, listdevicecode, listCnCode);
                    for (CommMain commMainTemp : list) {
                        DataIntervalModel dataIntervalModelTemp = ConvertDataIntervalModel(commMainTemp);
                        if (dataIntervalModelTemp != null) {
                            listModel.add(dataIntervalModelTemp);
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
     * [功能描述]：插入数据上传间隔
     * </p>
     *
     * @param riCnCode
     * @param riInterval
     * @param riExcuteTime
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:24:20
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertDataInterval", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertDataInterval(String riCnCode,
                                   String riInterval, String riExcuteTime,
                                   @RequestParam(value = "list[]") List<String> list,
                                   HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        try {
            if (SessionManager.isSessionValidate(httpsession,
                    DefaultArgument.LOGIN_USER)) {
                resultModel.setDetail("登录超时，请重新登陆后操作！");
                return resultModel;
            }
            if (list != null && list.size() > 0 && riCnCode != null
                    && !riCnCode.isEmpty() && riInterval != null
                    && !riInterval.isEmpty() && riExcuteTime != null
                    && !riExcuteTime.isEmpty()) {
                if (!riCnCode.equals(DefaultArgument.PRO_CN_REALINTERVAL)) {
                    resultModel.setDetail("暂时不支持此数据间隔设置！");
                    return resultModel;
                }
                int userid;
                UserModel loginuser = (UserModel) httpsession
                        .getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userid = loginuser.getUserId();
                } else {
                    resultModel.setDetail("登录超时，请重新登陆后操作！");
                    return resultModel;
                }
                int count = dataIntervalService.insertDataInterval(riCnCode,
                        riInterval, riExcuteTime, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("设置数据间隔失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：设置数据间隔失败，原因：" + e.getMessage());
            resultModel.setDetail("设置数据间隔失败！");
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：删除数据上传间隔</p>
     *
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日下午2:15:31
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteDataInterval", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteDataInterval(
            @RequestParam(value = "list[]") List<Integer> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listCommId = new ArrayList<Integer>();
                for (Integer commId : list) {
                    listCommId.add(commId);
                }
                int intresult = dataIntervalService.deleteDataInterval(listCommId);
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
     * [功能描述]：将DataIntervalModel转换成CommMain
     * </p>
     *
     * @param dataIntervalModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:54:40
     * @since EnvDust 1.0.0
     */
    private CommMain ConvertCommMain(DataIntervalModel dataIntervalModel,
                                     HttpSession httpsession) {
        CommMain commMain = new CommMain();
        if (dataIntervalModel != null) {
            CommCn commCn = new CommCn();
            commCn.setCnCode(dataIntervalModel.getCnCode());
            commMain.setCommCn(commCn);
            Device device = new Device();
            //设置外键，areaid
            Area area = new Area();
            if (dataIntervalModel.getAreaId() != null && !dataIntervalModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(dataIntervalModel.getAreaId()));
            }
            device.setArea(area);
            device.setDeviceName(dataIntervalModel.getDeviceName());
            commMain.setDevice(device);
            commMain.setRowCount(dataIntervalModel.getRows());
            commMain.setRowIndex((dataIntervalModel.getPage() - 1)
                    * dataIntervalModel.getRows());
        }
        return commMain;
    }

    /**
     * <p>
     * [功能描述]：将CommMain转换成DataIntervalModel
     * </p>
     *
     * @param commMain
     * @return
     * @author 王垒, 2016年6月6日上午10:00:54
     * @since EnvDust 1.0.0
     */
    private DataIntervalModel ConvertDataIntervalModel(CommMain commMain) {
        DataIntervalModel dataIntervalModel = new DataIntervalModel();
        if (commMain != null) {
            dataIntervalModel.setCommId(String.valueOf(commMain.getCommId()));
            Device device = commMain.getDevice();
            if (device != null) {
                dataIntervalModel.setDeviceCode(device.getDeviceCode());
                dataIntervalModel.setDeviceMn(device.getDeviceMn());
                dataIntervalModel.setDeviceName(device.getDeviceName());
                //设置areaId,areaName
                Area area = device.getArea();
                if (area != null) {
                    dataIntervalModel.setAreaId(String.valueOf(area.getAreaId()));
                    dataIntervalModel.setAreaName(area.getAreaName());
                }
                //设置projectId,projectName
                DeviceProject deviceProject = device.getDeviceProject();
                if (deviceProject != null) {
                    dataIntervalModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                    dataIntervalModel.setProjectName(deviceProject.getProjectName());
                }
            }
            CommCn commCn = commMain.getCommCn();
            if (commCn != null) {
                dataIntervalModel.setCnCode(commCn.getCnCode());
                dataIntervalModel.setCnName(commCn.getCnName());
            }
            CommStatus commStatus = commMain.getCommStatus();
            if (commStatus != null) {
                dataIntervalModel.setStatusName(commStatus.getStatusName());
            }
            String intervalValue = commMain.getCp();
            if (intervalValue != null && intervalValue.indexOf("Value=") >= 0) {
                dataIntervalModel.setIntervalValue(intervalValue.replace(
                        "Value=", ""));
            }
            dataIntervalModel.setExcuteTime(DateUtil.TimestampToString(
                    commMain.getExcuteTime(), DateUtil.DATA_TIME));
            dataIntervalModel.setOptUserName(userService.getUserNameById(
                    commMain.getOptUser(), null));
            dataIntervalModel.setOptTime(DateUtil.TimestampToString(
                    commMain.getOptTime(), DateUtil.DATA_TIME));
        }
        return dataIntervalModel;
    }

}
