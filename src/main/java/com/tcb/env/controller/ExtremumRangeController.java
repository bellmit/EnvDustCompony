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

import com.tcb.env.model.ExtremumRangeModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IExtremumRangeService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：极值范围控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年1月9日上午10:46:13
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/ExtremumRangeController")
public class ExtremumRangeController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "ExtremumRangeController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(ExtremumRangeController.class);

    /**
     * 声明极值范围服务
     */
    @Resource
    private IExtremumRangeService extremumRangeService;

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
     * <p>[功能描述]：获取极值范围数据计划</p>
     *
     * @param projectId
     * @param extremumRangeModel
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月9日下午12:42:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getExtremumRange", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<ExtremumRangeModel> getExtremumRange(
            @RequestParam(value = "projectId", required = false) String projectId,
            ExtremumRangeModel extremumRangeModel, HttpSession httpsession) {
        ResultListModel<ExtremumRangeModel> resultListModel = new ResultListModel<ExtremumRangeModel>();
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
            CommMain commMain = ConvertCommMain(extremumRangeModel, httpsession);
            List<String> listDeviceCode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listDeviceCode.add(treeModel.getId());
            }
            if (listDeviceCode != null && listDeviceCode.size() > 0 && commMain != null) {
                List<String> listCnCode = new ArrayList<String>();
                listCnCode.add(commMain.getCommCn().getCnCode());
                int count = extremumRangeService.getExtremumRangeCount(commMain, listDeviceCode, listCnCode);
                if (count > 0) {
                    List<ExtremumRangeModel> listModel = new ArrayList<ExtremumRangeModel>();
                    List<CommMain> list = extremumRangeService.getExtremumRange(commMain, listDeviceCode, listCnCode);
                    for (CommMain commMainTemp : list) {
                        ExtremumRangeModel extremumRangeModelTemp = ConvertExtremumRangeModel(commMainTemp);
                        if (extremumRangeModelTemp != null) {
                            listModel.add(extremumRangeModelTemp);
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
     * <p>[功能描述]：插入设置极值范围数据</p>
     *
     * @param cnCode
     * @param xMaxState
     * @param xMinState
     * @param xMax
     * @param xMin
     * @param setExcuteTime
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月9日下午3:05:50
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertExtremumRangeSet", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertExtremumRangeSet(
            String cnCode, String xMaxState, String xMinState, String xMax, String xMin, String setExcuteTime,
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
                int count = extremumRangeService.insertExtremumRangeSet(
                        cnCode, xMaxState, xMinState, xMax, xMin, setExcuteTime, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("插入设置极值范围数据失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：插入设置极值范围数据失败，原因：" + e.getMessage());
            resultModel.setDetail("插入设置极值范围数据失败！");
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
    @RequestMapping(value = "/deleteExtremumRange", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteExtremumRange(
            @RequestParam(value = "list[]") List<Integer> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listCommId = new ArrayList<Integer>();
                for (Integer commId : list) {
                    listCommId.add(commId);
                }
                int intresult = extremumRangeService.deleteExtremumRange(listCommId);
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
     * [功能描述]：将ExtremumRangeModel转换成CommMain
     * </p>
     *
     * @param extremumRangeModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:54:40
     * @since EnvDust 1.0.0
     */
    private CommMain ConvertCommMain(ExtremumRangeModel extremumRangeModel,
                                     HttpSession httpsession) {
        CommMain commMain = new CommMain();
        if (extremumRangeModel != null) {
            CommCn commCn = new CommCn();
            commCn.setCnCode(extremumRangeModel.getCnCode());
            commMain.setCommCn(commCn);
            Device device = new Device();
            //设置外键，areaid
            Area area = new Area();
            if (extremumRangeModel.getAreaId() != null && !extremumRangeModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(extremumRangeModel.getAreaId()));
            }
            device.setArea(area);
            device.setDeviceCode(extremumRangeModel.getDeviceCode());
            device.setDeviceName(extremumRangeModel.getDeviceName());
            commMain.setDevice(device);
            commMain.setRowCount(extremumRangeModel.getRows());
            commMain.setRowIndex((extremumRangeModel.getPage() - 1) * extremumRangeModel.getRows());
        }
        return commMain;
    }

    /**
     * <p>[功能描述]：将CommMain转换成ExtremumRangeModel</p>
     *
     * @param commMain
     * @return
     * @author 王垒, 2018年1月9日下午12:30:40
     * @since EnvDust 1.0.0
     */
    private ExtremumRangeModel ConvertExtremumRangeModel(CommMain commMain) {
        ExtremumRangeModel extremumRangeModel = new ExtremumRangeModel();
        if (commMain != null) {
            extremumRangeModel.setCommId(String.valueOf(commMain.getCommId()));
            Device device = commMain.getDevice();
            if (device != null) {
                extremumRangeModel.setDeviceCode(device.getDeviceCode());
                extremumRangeModel.setDeviceMn(device.getDeviceMn());
                extremumRangeModel.setDeviceName(device.getDeviceName());
                //设置areaId,areaName
                Area area = device.getArea();
                if (area != null) {
                    extremumRangeModel.setAreaId(String.valueOf(area.getAreaId()));
                    extremumRangeModel.setAreaName(area.getAreaName());
                }
                //设置projectId，projectName
                DeviceProject deviceProject = device.getDeviceProject();
                if (deviceProject != null) {
                    extremumRangeModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                    extremumRangeModel.setProjectName(deviceProject.getProjectName());
                }
            }
            CommCn commCn = commMain.getCommCn();
            if (commCn != null) {
                extremumRangeModel.setCnCode(commCn.getCnCode());
                extremumRangeModel.setCnName(commCn.getCnName());
            }
            CommStatus commStatus = commMain.getCommStatus();
            if (commStatus != null) {
                extremumRangeModel.setStatusName(commStatus.getStatusName());
            }
            String cp = commMain.getCp();
            if (cp != null && !cp.isEmpty()) {
                if (cp != null && cp.indexOf(",") >= 0) {
                    String[] cpArray = cp.split(",");
                    for (String temp : cpArray) {
                        if (temp != null && !temp.isEmpty()) {
                            if (temp.indexOf("x00003-MaxState=") >= 0) {
                                extremumRangeModel.setxMaxState(temp.replace("x00003-MaxState=", ""));
                            } else if (temp.indexOf("x00003-MinState=") >= 0) {
                                extremumRangeModel.setxMinState(temp.replace("x00003-MinState=", ""));
                            } else if (temp.indexOf("x00003-Max=") >= 0) {
                                extremumRangeModel.setxMax(temp.replace("x00003-Max=", ""));
                            } else if (temp.indexOf("x00003-Min=") >= 0) {
                                extremumRangeModel.setxMin(temp.replace("x00003-Min=", ""));
                            }
                        }
                    }
                }
            }
            extremumRangeModel.setExcuteTime(DateUtil.TimestampToString(
                    commMain.getExcuteTime(), DateUtil.DATA_TIME));
            extremumRangeModel.setOptUserName(userService.getUserNameById(
                    commMain.getOptUser(), null));
            extremumRangeModel.setOptTime(DateUtil.TimestampToString(
                    commMain.getOptTime(), DateUtil.DATA_TIME));
        }
        return extremumRangeModel;
    }
}
