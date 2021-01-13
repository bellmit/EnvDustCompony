package com.tcb.env.service;

import com.tcb.env.pojo.DeviceProject;

import java.util.List;

/**
 * <p>
 * [功能描述]：设备项目服务接口
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
public interface IDeviceProjectService {

    /**
     * 获取设备项目个数
     * @return
     */
    int getDeviceProjectCount();

    /**
     * 获取设备项目信息
     * @return
     */
    List<DeviceProject> getDeviceProject();

}
