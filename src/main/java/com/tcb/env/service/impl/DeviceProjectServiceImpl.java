package com.tcb.env.service.impl;

import com.tcb.env.dao.IDeviceProjectDao;
import com.tcb.env.pojo.DeviceProject;
import com.tcb.env.service.IDeviceProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * [功能描述]：设备项目服务接口实现类
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
@Service("deviceProjectService")
class DeviceProjectServiceImpl implements IDeviceProjectService {

    @Resource
    private IDeviceProjectDao deviceProjectDao;

    @Override
    public int getDeviceProjectCount() {
        return deviceProjectDao.getDeviceProjectCount();
    }

    @Override
    public List<DeviceProject> getDeviceProject() {
        return deviceProjectDao.getDeviceProject();
    }

}
