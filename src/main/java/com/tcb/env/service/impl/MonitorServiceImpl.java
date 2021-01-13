/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.tcb.env.model.MonitorLevelModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IMonitorDao;
import com.tcb.env.pojo.Monitor;
import com.tcb.env.service.IMonitorService;

/**
 * <p>
 * [功能描述]：监测物服务接口实现类
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月30日上午11:37:09
 * @since EnvDust 1.0.0
 */
@Service("monitorService")
@Transactional(rollbackFor = Exception.class)
class MonitorServiceImpl implements IMonitorService {

    @Resource
    private IMonitorDao monitorDao;

    @Override
    public List<Monitor> getMonitor(Monitor monitor) {

        return monitorDao.getMonitor(monitor);
    }

    @Override
    public int getCount(Monitor monitor) {

        return monitorDao.getCount(monitor);
    }

    @Override
    public int insertMonitor(Monitor monitor) throws Exception {

        return monitorDao.insertMonitor(monitor);
    }

    @Override
    public int deleteMonitor(List<Integer> monitorid) throws Exception {

        return monitorDao.deleteMonitor(monitorid);
    }

    @Override
    public int getMonitorExist(int monitorid, String monitorcode, String monitorname) {

        return monitorDao.getMonitorExist(monitorid, monitorcode, monitorname);
    }

    @Override
    public int updateMonitor(List<Monitor> listMonitor) throws Exception {

        return monitorDao.updateMonitor(listMonitor);
    }

    @Override
    public String getMonitorName(String thingCode) {
        return monitorDao.getMonitorName(thingCode);
    }

    @Override
    public List<MonitorLevelModel> getMonitorLevel(String thingCode) {
        return monitorDao.getMonitorLevel(thingCode);
    }

    @Override
    public MonitorLevelModel getMonitorValueLevel(String thingCode, Double thingValue) {
        return monitorDao.getMonitorValueLevel(thingCode, thingValue);
    }

}
