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

import com.tcb.env.model.DeviceModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.Manufacturer;
import com.tcb.env.pojo.Status;
import com.tcb.env.service.IAccessOperatorService;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：AccessOperator控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月6日下午3:28:31
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/AccessOperatorController")
public class AccessOperatorController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AreaController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger
            .getLogger(AccessOperatorController.class);
    /**
     * 声明权限组数据服务类
     */
    @Resource
    private IAccessOperatorService accessOperatorService;

    /**
     * <p>
     * [功能描述]：权限画面查询设备
     * </p>
     *
     * @param deviceModel
     * @param ahrCode
     * @param httpsession
     * @return
     * @author 王垒, 2016年7月15日上午9:55:19
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAhrDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeviceModel> queryAhrDevice(DeviceModel deviceModel, String ahrCode, HttpSession httpsession) {
        ResultListModel<DeviceModel> resultListModel = new ResultListModel<DeviceModel>();
        List<DeviceModel> listusermodel = new ArrayList<DeviceModel>();
        List<Device> listuser = new ArrayList<Device>();
        Device device = ConvertAhrDevice(deviceModel, httpsession);
        int count = accessOperatorService.getJudgeAhrDeviceCount(device,
                ahrCode);
        if (count > 0) {
            listuser = accessOperatorService.getJudgeAhrDevice(device, ahrCode);
            for (Device temp : listuser) {
                DeviceModel deviceModell = ConvertAhrDeviceModel(temp);
                if (deviceModell != null) {
                    listusermodel.add(deviceModell);
                }
            }
            resultListModel.setRows(listusermodel);
        }
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：更新权限组监测物数据
     * </p>
     *
     * @param ahrCode
     * @param listMonCode
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月7日上午12:44:24
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertAccessMonitor", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertAccessMonitor(String ahrCode, @RequestParam(value = "list[]") List<String> listMonCode, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (ahrCode != null && !ahrCode.isEmpty()) {
            try {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                int optUser = loginuser.getUserId();
                if (listMonCode == null || listMonCode.size() == 0 || String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        listMonCode.get(0))) {
                    accessOperatorService.updateAccessMonitor(ahrCode, null, optUser);
                } else {
                    accessOperatorService.updateAccessMonitor(ahrCode, listMonCode, optUser);
                }
                resultModel.setResult(true);
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新权限组失败！");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：更新权限组设备数据
     * </p>
     *
     * @param ahrCode
     * @param listDevCode
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月7日上午12:44:00
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertAccessDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertAccessDevice(String ahrCode, @RequestParam(value = "list[]") List<String> listDevCode, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (ahrCode != null && !ahrCode.isEmpty()) {
            try {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                int optUser = loginuser.getUserId();
                Device device = new Device();
                device.setHaveAhr(DefaultArgument.IS_AHR_DEVICE);
                int count = accessOperatorService.getJudgeAhrDeviceCount(device, ahrCode);
                int surplusCount = (DefaultArgument.MAX_AHR_COUNT - count);
                if ((surplusCount - listDevCode.size()) >= 0) {
                    accessOperatorService.insertAccessDevice(ahrCode, listDevCode, optUser);
                    resultModel.setResult(true);
                } else {
                    if (surplusCount < 0) {
                        surplusCount = 0;
                    }
                    resultModel.setResult(false);
                    resultModel.setDetail("已超出权限组容量，最大容量为：" + DefaultArgument.MAX_AHR_COUNT);
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新权限组失败！");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    @RequestMapping(value = "/deleteAccessDevice", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteAccessDevice(String ahrCode, @RequestParam(value = "list[]") List<String> listDevCode, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (ahrCode != null && !ahrCode.isEmpty()) {
            try {
                List<String> listAhrCode = new ArrayList<String>();
                listAhrCode.add(ahrCode);
                if (listDevCode == null || listDevCode.size() == 0 || String.valueOf(DefaultArgument.INT_DEFAULT).equals(
                        listDevCode.get(0))) {
                    accessOperatorService.deleteAccessDevice(listAhrCode);
                } else {
                    accessOperatorService.deleteAccessDeviceSingle(ahrCode, listDevCode);
                }

                resultModel.setResult(true);
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新权限组失败！");
                logger.error(LOG + ":" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：DeviceModel转换成Device
     * </p>
     *
     * @param deviceModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年7月15日上午10:21:39
     * @since EnvDust 1.0.0
     */
    private Device ConvertAhrDevice(DeviceModel deviceModel, HttpSession httpsession) {
        Device device = new Device();
        if (deviceModel != null) {
            device.setDeviceId(deviceModel.getDeviceId());
            device.setDeviceCode(deviceModel.getDeviceCode());
            device.setDeviceMn(deviceModel.getDeviceMn());
            device.setDeviceName(deviceModel.getDeviceName());
            // 设置外键mfrcode
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setMfrCode(deviceModel.getMfrCode());
            device.setManufacturer(manufacturer);
            // 设置外键，areaid
            Area area = new Area();
            if (deviceModel.getAreaId() != null && !deviceModel.getAreaId().isEmpty()) {
                area.setAreaId(Integer.valueOf(deviceModel.getAreaId()));
            }
            device.setArea(area);
            // 设置外statuscode
            Status status = new Status();
            status.setStatusCode(deviceModel.getStatusCode());
            device.setStatus(status);
            device.setHaveAhr(deviceModel.getHaveAhr());
            device.setRowCount(deviceModel.getRows());
            device.setRowIndex((deviceModel.getPage() - 1)
                    * deviceModel.getRows());
        }
        return device;
    }

    /**
     * <p>
     * [功能描述]：Device转换成DeviceModel
     * </p>
     *
     * @param device
     * @return
     * @author 王垒, 2016年7月15日上午10:21:14
     * @since EnvDust 1.0.0
     */
    private DeviceModel ConvertAhrDeviceModel(Device device) {
        DeviceModel deviceModel = new DeviceModel();
        if (device != null) {
            deviceModel.setDeviceId(device.getDeviceId());
            deviceModel.setDeviceCode(device.getDeviceCode());
            deviceModel.setDeviceMn(device.getDeviceMn());
            deviceModel.setDeviceName(device.getDeviceName());
            deviceModel.setBuildFirm(device.getBuildFirm());
            deviceModel.setDeviceAddress(device.getDeviceAddress());
            // 外键mfr
            deviceModel.setMfrCode(device.getManufacturer().getMfrCode());
            deviceModel.setMfrName(device.getManufacturer().getMfrName());
            // 外键status
            if (device.getStatus() != null) {
                deviceModel.setStatusCode(device.getStatus().getStatusCode());
                deviceModel.setStatusName(device.getStatus().getStatusName());
            }
            // 外键area
            if (device.getArea() != null && DefaultArgument.INT_DEFAULT != device.getArea().getAreaId()) {
                deviceModel.setAreaId(String.valueOf(device.getArea().getAreaId()));
            }
            deviceModel.setAreaName(device.getArea().getAreaName());
            // 外键user
            int userId = device.getUser().getUserId();
            if (userId != DefaultArgument.INT_DEFAULT) {
                deviceModel.setUserId(String.valueOf(userId));
                deviceModel.setUserName(device.getUser().getUserName());
                deviceModel.setUserTel(device.getUser().getUserTel());
                deviceModel.setUserRemark(device.getUser().getUserRemark());
            }
            String haveAhr = device.getHaveAhr();
            deviceModel.setHaveAhr(haveAhr);
            if (haveAhr != null) {
                if (haveAhr.equals("1")) {
                    deviceModel.setHaveAhrInfo("未添加");
                } else if (haveAhr.equals("2")) {
                    deviceModel.setHaveAhrInfo("已添加");
                }
            } else {
                deviceModel.setHaveAhrInfo("未知状态");
            }
        }
        return deviceModel;
    }

}
