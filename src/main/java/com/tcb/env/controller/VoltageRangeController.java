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
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.model.VoltageRangeModel;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.service.IVoltageRangeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：零点以及量程控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年1月9日上午10:46:13
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/VoltageRangeController")
public class VoltageRangeController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "VoltageRangeController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(VoltageRangeController.class);

    /**
     * 声明零点量程服务
     */
    @Resource
    private IVoltageRangeService voltageRangeService;

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
     * <p>[功能描述]：获取零点量程数据计划</p>
     *
     * @param projectId
     * @param voltageRangeModel
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月9日下午12:42:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getVoltageRange", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<VoltageRangeModel> getVoltageRange(
            @RequestParam(value = "projectId", required = false) String projectId,
            VoltageRangeModel voltageRangeModel, HttpSession httpsession) {
        ResultListModel<VoltageRangeModel> resultListModel = new ResultListModel<VoltageRangeModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession
                    .getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            CommMain commMain = ConvertCommMain(voltageRangeModel, httpsession);
            List<String> listDeviceCode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listDeviceCode.add(treeModel.getId());
            }
            if (listDeviceCode != null && listDeviceCode.size() > 0 && commMain != null) {
                List<String> listCnCode = new ArrayList<String>();
                listCnCode.add(commMain.getCommCn().getCnCode());
                int count = voltageRangeService.getVoltageRangeCount(commMain, listDeviceCode, listCnCode);
                if (count > 0) {
                    List<VoltageRangeModel> listModel = new ArrayList<VoltageRangeModel>();
                    List<CommMain> list = voltageRangeService.getVoltageRange(commMain, listDeviceCode, listCnCode);
                    for (CommMain commMainTemp : list) {
                        VoltageRangeModel voltageRangeModelTemp = ConvertVoltageRangeModel(commMainTemp);
                        if (voltageRangeModelTemp != null) {
                            listModel.add(voltageRangeModelTemp);
                        }
                    }
                    resultListModel.setTotal(count);
                    resultListModel.setRows(listModel);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询零点量程数据失败，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：插入设置零点量程数据</p>
     *
     * @param cnCode
     * @param xZeroVolt
     * @param xScaleVolt
     * @param xScaleConc
     * @param setExcuteTime
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月9日下午1:34:58
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertVoltageRangeSet", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertVoltageRangeSet(
            String cnCode, String xZeroVolt, String xScaleVolt, String xScaleConc, String setExcuteTime,
            @RequestParam(value = "list[]") List<String> list, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                resultModel.setDetail("登录超时，请重新登陆后操作！");
                return resultModel;
            }
            if (list != null && list.size() > 0 && cnCode != null && !cnCode.isEmpty()
                    && setExcuteTime != null && !setExcuteTime.isEmpty()) {
                int userid;
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userid = loginuser.getUserId();
                } else {
                    resultModel.setDetail("登录超时，请重新登陆后操作！");
                    return resultModel;
                }
                int count = voltageRangeService.insertVoltageRangeSet(
                        cnCode, xZeroVolt, xScaleVolt, xScaleConc, setExcuteTime, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("插入设置零点量程数据失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：插入设置零点量程数据失败，原因：" + e.getMessage());
            resultModel.setDetail("插入设置零点量程数据失败！");
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：删除计划任务数据</p>
     *
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月9日下午1:34:47
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteVoltageRange", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteVoltageRange(
            @RequestParam(value = "list[]") List<Integer> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listCommId = new ArrayList<Integer>();
                for (Integer commId : list) {
                    listCommId.add(commId);
                }
                int intresult = voltageRangeService.deleteVoltageRange(listCommId);
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
     * [功能描述]：将VoltageRangeModel转换成CommMain
     * </p>
     *
     * @param voltageRangeModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:54:40
     * @since EnvDust 1.0.0
     */
    private CommMain ConvertCommMain(VoltageRangeModel voltageRangeModel,
                                     HttpSession httpsession) {
        CommMain commMain = new CommMain();
        if (voltageRangeModel != null) {
            CommCn commCn = new CommCn();
            commCn.setCnCode(voltageRangeModel.getCnCode());
            commMain.setCommCn(commCn);
            Device device = new Device();
            //设置外键，areaid
            Area area = new Area();
            if (voltageRangeModel.getAreaId() != null && !voltageRangeModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(voltageRangeModel.getAreaId()));
            }
            device.setArea(area);
            device.setDeviceCode(voltageRangeModel.getDeviceCode());
            device.setDeviceName(voltageRangeModel.getDeviceName());
            commMain.setDevice(device);
            commMain.setRowCount(voltageRangeModel.getRows());
            commMain.setRowIndex((voltageRangeModel.getPage() - 1) * voltageRangeModel.getRows());
        }
        return commMain;
    }

    /**
     * <p>[功能描述]：将CommMain转换成VoltageRangeModel</p>
     *
     * @param commMain
     * @return
     * @author 王垒, 2018年1月9日下午12:30:40
     * @since EnvDust 1.0.0
     */
    private VoltageRangeModel ConvertVoltageRangeModel(CommMain commMain) {
        VoltageRangeModel voltageRangeModel = new VoltageRangeModel();
        if (commMain != null) {
            voltageRangeModel.setCommId(String.valueOf(commMain.getCommId()));
            Device device = commMain.getDevice();
            if (device != null) {
                voltageRangeModel.setDeviceCode(device.getDeviceCode());
                voltageRangeModel.setDeviceMn(device.getDeviceMn());
                voltageRangeModel.setDeviceName(device.getDeviceName());
                //设置areaId,areaName
                Area area = device.getArea();
                if (area != null) {
                    voltageRangeModel.setAreaId(String.valueOf(area.getAreaId()));
                    voltageRangeModel.setAreaName(area.getAreaName());
                }
                //设置projectId，projectName
                DeviceProject deviceProject = device.getDeviceProject();
                if (deviceProject != null) {
                    voltageRangeModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                    voltageRangeModel.setProjectName(deviceProject.getProjectName());
                }
            }
            CommCn commCn = commMain.getCommCn();
            if (commCn != null) {
                voltageRangeModel.setCnCode(commCn.getCnCode());
                voltageRangeModel.setCnName(commCn.getCnName());
            }
            CommStatus commStatus = commMain.getCommStatus();
            if (commStatus != null) {
                voltageRangeModel.setStatusName(commStatus.getStatusName());
            }
            String cp = commMain.getCp();
            if (cp != null && !cp.isEmpty()) {
                if (cp != null && cp.indexOf(",") >= 0) {
                    String[] cpArray = cp.split(",");
                    for (String temp : cpArray) {
                        if (temp != null && !temp.isEmpty()) {
                            if (temp.indexOf("x00001-ZeroVolt=") >= 0) {
                                voltageRangeModel.setxZeroVolt(temp.replace("x00001-ZeroVolt=", ""));
                            } else if (temp.indexOf("x00001-ScaleVolt=") >= 0) {
                                voltageRangeModel.setxScaleVolt(temp.replace("x00001-ScaleVolt=", ""));
                            } else if (temp.indexOf("x00001-ScaleConc=") >= 0) {
                                voltageRangeModel.setxScaleConc(temp.replace("x00001-ScaleConc=", ""));
                            }
                        }
                    }
                }
            }
            voltageRangeModel.setExcuteTime(DateUtil.TimestampToString(
                    commMain.getExcuteTime(), DateUtil.DATA_TIME));
            voltageRangeModel.setOptUserName(userService.getUserNameById(
                    commMain.getOptUser(), null));
            voltageRangeModel.setOptTime(DateUtil.TimestampToString(
                    commMain.getOptTime(), DateUtil.DATA_TIME));
        }
        return voltageRangeModel;
    }
}
