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
import com.tcb.env.model.TemperatureControlModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.ITemperatureControlService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>[功能描述]：温度控制控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年1月9日上午10:46:13
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/TemperatureControlController")
public class TemperatureControlController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "TemperatureControlController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(TemperatureControlController.class);

    /**
     * 声明极值范围服务
     */
    @Resource
    private ITemperatureControlService temperatureControlService;

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
     * <p>[功能描述]：获取温度控制数据计划</p>
     *
     * @param projectId
     * @param temperatureControlModel
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月9日下午12:42:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getTemperatureControl", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<TemperatureControlModel> getTemperatureControl(
            @RequestParam(value = "projectId", required = false) String projectId,
            TemperatureControlModel temperatureControlModel, HttpSession httpsession) {
        ResultListModel<TemperatureControlModel> resultListModel = new ResultListModel<TemperatureControlModel>();
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return resultListModel;
            }
            String usercode = null;
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                usercode = loginuser.getUserCode();
            }
            CommMain commMain = ConvertCommMain(temperatureControlModel, httpsession);
            List<String> listDeviceCode = new ArrayList<String>();
            List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId, DefaultArgument.INT_DEFAULT, null, null, null);
            for (TreeModel treeModel : listDev) {
                listDeviceCode.add(treeModel.getId());
            }
            if (listDeviceCode != null && listDeviceCode.size() > 0 && commMain != null) {
                List<String> listCnCode = new ArrayList<String>();
                listCnCode.add(commMain.getCommCn().getCnCode());
                int count = temperatureControlService.getTemperatureControlCount(commMain, listDeviceCode, listCnCode);
                if (count > 0) {
                    List<TemperatureControlModel> listModel = new ArrayList<TemperatureControlModel>();
                    List<CommMain> list = temperatureControlService.getTemperatureControl(commMain, listDeviceCode, listCnCode);
                    for (CommMain commMainTemp : list) {
                        TemperatureControlModel temperatureControlModelTemp = ConvertTemperatureControlModel(commMainTemp);
                        if (temperatureControlModelTemp != null) {
                            listModel.add(temperatureControlModelTemp);
                        }
                    }
                    resultListModel.setTotal(count);
                    resultListModel.setRows(listModel);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询温度控制数据失败，原因：" + e.getMessage());
        }
        return resultListModel;
    }

    /**
     * <p>[功能描述]：插入温度控制数据</p>
     *
     * @param cnCode
     * @param xTpCtrlEn
     * @param xTpCtrl
     * @param setExcuteTime
     * @param list
     * @param httpsession
     * @return
     * @author 王垒, 2018年1月18日上午10:18:03
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertTemperatureControlSet", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertTemperatureControlSet(
            String cnCode, String xTpCtrlEn, String xTpCtrl, String setExcuteTime,
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
                int count = temperatureControlService.insertTemperatureControlSet(cnCode, xTpCtrlEn, xTpCtrl, setExcuteTime, list, userid);
                if (count > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setDetail("插入温度控制数据失败！");
                    return resultModel;
                }
            } else {
                resultModel.setDetail("无操作数据！");
            }
        } catch (Exception e) {
            logger.error(LOG + "：插入温度控制数据失败，原因：" + e.getMessage());
            resultModel.setDetail("插入温度控制数据失败！");
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
    @RequestMapping(value = "/deleteTemperatureControl", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteTemperatureControl(
            @RequestParam(value = "list[]") List<Integer> list,
            HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listCommId = new ArrayList<Integer>();
                for (Integer commId : list) {
                    listCommId.add(commId);
                }
                int intresult = temperatureControlService.deleteTemperatureControl(listCommId);
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
     * [功能描述]：将TemperatureControlModel转换成CommMain
     * </p>
     *
     * @param temperatureControlModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年6月6日上午9:54:40
     * @since EnvDust 1.0.0
     */
    private CommMain ConvertCommMain(TemperatureControlModel temperatureControlModel,
                                     HttpSession httpsession) {
        CommMain commMain = new CommMain();
        if (temperatureControlModel != null) {
            CommCn commCn = new CommCn();
            commCn.setCnCode(temperatureControlModel.getCnCode());
            commMain.setCommCn(commCn);
            Device device = new Device();
            device.setDeviceCode(temperatureControlModel.getDeviceCode());
            device.setDeviceName(temperatureControlModel.getDeviceName());
            Area area = new Area();
            if (temperatureControlModel.getAreaId() != null && !temperatureControlModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(temperatureControlModel.getAreaId()));
            }
            area.setAreaName(temperatureControlModel.getAreaName());
            device.setArea(area);
            commMain.setDevice(device);
            commMain.setRowCount(temperatureControlModel.getRows());
            commMain.setRowIndex((temperatureControlModel.getPage() - 1) * temperatureControlModel.getRows());
        }
        return commMain;
    }

    /**
     * <p>[功能描述]：将CommMain转换成TemperatureControlModel</p>
     *
     * @param commMain
     * @return
     * @author 王垒, 2018年1月9日下午12:30:40
     * @since EnvDust 1.0.0
     */
    private TemperatureControlModel ConvertTemperatureControlModel(CommMain commMain) {
        TemperatureControlModel temperatureControlModel = new TemperatureControlModel();
        if (commMain != null) {
            temperatureControlModel.setCommId(String.valueOf(commMain.getCommId()));
            Device device = commMain.getDevice();
            if (device != null) {
                temperatureControlModel.setDeviceCode(device.getDeviceCode());
                temperatureControlModel.setDeviceMn(device.getDeviceMn());
                temperatureControlModel.setDeviceName(device.getDeviceName());
                Area area = device.getArea();
                if (area != null) {
                    temperatureControlModel.setAreaId(String.valueOf(area.getAreaId()));
                    temperatureControlModel.setAreaName(area.getAreaName());
                }
                DeviceProject deviceProject = device.getDeviceProject();
                if (deviceProject != null) {
                    temperatureControlModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
                    temperatureControlModel.setProjectName(deviceProject.getProjectName());
                }
            }
            CommCn commCn = commMain.getCommCn();
            if (commCn != null) {
                temperatureControlModel.setCnCode(commCn.getCnCode());
                temperatureControlModel.setCnName(commCn.getCnName());
            }
            CommStatus commStatus = commMain.getCommStatus();
            if (commStatus != null) {
                temperatureControlModel.setStatusName(commStatus.getStatusName());
            }
            String cp = commMain.getCp();
            if (cp != null && !cp.isEmpty()) {
                if (cp != null && cp.indexOf(",") >= 0) {
                    String[] cpArray = cp.split(",");
                    for (String temp : cpArray) {
                        if (temp != null && !temp.isEmpty()) {
                            if (temp.indexOf("x00004-TpCtrlEn=") >= 0) {
                                temperatureControlModel.setxTpCtrlEn(temp.replace("x00004-TpCtrlEn=", ""));
                            } else if (temp.indexOf("x00004-TpCtrl=") >= 0) {
                                temperatureControlModel.setxTpCtrl(temp.replace("x00004-TpCtrl=", ""));
                            }
                        }
                    }
                }
            }
            temperatureControlModel.setExcuteTime(DateUtil.TimestampToString(
                    commMain.getExcuteTime(), DateUtil.DATA_TIME));
            temperatureControlModel.setOptUserName(userService.getUserNameById(
                    commMain.getOptUser(), null));
            temperatureControlModel.setOptTime(DateUtil.TimestampToString(
                    commMain.getOptTime(), DateUtil.DATA_TIME));
        }
        return temperatureControlModel;
    }
}
