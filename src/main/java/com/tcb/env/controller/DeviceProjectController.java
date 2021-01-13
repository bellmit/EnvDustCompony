package com.tcb.env.controller;

import com.tcb.env.model.DeviceProjectModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.pojo.DeviceProject;
import com.tcb.env.service.IDeviceProjectService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * [功能描述]：设备项目控制器
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2019年2月19日上午09:02:31
 * @since EnvDust 1.0.0
 *
 */
@Controller
@RequestMapping("/DeviceProjectController")
public class DeviceProjectController {

    @Resource
    private IDeviceProjectService deviceProjectService;

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/queryDeviceProject", method = { RequestMethod.POST })
    @ResponseBody
    public ResultListModel<DeviceProjectModel> queryDeviceProject(HttpSession httpsession) {
        ResultListModel<DeviceProjectModel> resultListModel = new ResultListModel<DeviceProjectModel>();
        List<DeviceProjectModel> listDeviceProjectModel = new ArrayList<DeviceProjectModel>();
        List<DeviceProject> listDeviceProject = new ArrayList<DeviceProject>();
        int count = deviceProjectService.getDeviceProjectCount();
        if(count>0){
            listDeviceProject = deviceProjectService.getDeviceProject();
            for (DeviceProject temp : listDeviceProject) {
                DeviceProjectModel deviceProjectModel = ConvertDeviceProjectModel(temp);
                if (deviceProjectModel != null) {
                    listDeviceProjectModel.add(deviceProjectModel);
                }
            }
        }
        resultListModel.setRows(listDeviceProjectModel);
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * <p>
     * [功能描述]：DeviceProject转成DeviceProjectModel
     * </p>
     *
     * @author 王垒, 2019年2月19日上午10:28:23
     * @since EnvDust 1.0.0
     *
     * @param deviceProject
     * @return
     */
    private DeviceProjectModel ConvertDeviceProjectModel(DeviceProject deviceProject) {
        DeviceProjectModel deviceProjectModel = new DeviceProjectModel();
        if (deviceProject != null) {
            deviceProjectModel.setProjectId(String.valueOf(deviceProject.getProjectId()));
            deviceProjectModel.setProjectCode(deviceProject.getProjectCode());
            deviceProjectModel.setProjectName(deviceProject.getProjectName());
            deviceProjectModel.setProjectOrder(String.valueOf(deviceProject.getProjectOrder()));
            // 操作者
            if(deviceProject.getOptUser() != DefaultArgument.INT_DEFAULT){
                deviceProjectModel.setOptUserName(userService.getUserNameById(deviceProject.getOptUser(), null));
            }
            deviceProjectModel.setOptTime(DateUtil.TimestampToString(deviceProject.getOptTime(), DateUtil.DATA_TIME));
        }
        return deviceProjectModel;
    }

}
