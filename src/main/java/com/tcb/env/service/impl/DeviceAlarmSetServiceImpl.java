package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IDeviceAlarmSetDao;
import com.tcb.env.pojo.CommCn;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommStatus;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.DeviceAlarmSet;
import com.tcb.env.pojo.Monitor;
import com.tcb.env.service.ICommMainService;
import com.tcb.env.service.IDeviceAlarmSetService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.springframework.util.StringUtils;

/**
 * <p>
 * [功能描述]：报警门限服务接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午10:38:25
 * @since EnvDust 1.0.0
 */
@Service("deviceAlarmSetService")
@Transactional(rollbackFor = Exception.class)
class DeviceAlarmSetServiceImpl implements IDeviceAlarmSetService {

    @Resource
    private IDeviceAlarmSetDao deviceAlarmSetDao;

    @Resource
    private ICommMainService commMainService;

    @Override
    public int exitDeviceAlarmSet(DeviceAlarmSet deviceAlarmSet) {
        return deviceAlarmSetDao.exitDeviceAlarmSet(deviceAlarmSet);
    }

    @Override
    public int getDeviceAlarmSetCount(DeviceAlarmSet deviceAlarmSet,
                                      List<String> listDeviceCode, List<String> listThingCode) {
        return deviceAlarmSetDao.getDeviceAlarmSetCount(deviceAlarmSet,
                listDeviceCode, listThingCode);
    }

    @Override
    public List<DeviceAlarmSet> getDeviceAlarmSet(
            DeviceAlarmSet deviceAlarmSet, List<String> listDeviceCode,
            List<String> listThingCode) {
        return deviceAlarmSetDao.getDeviceAlarmSet(deviceAlarmSet,
                listDeviceCode, listThingCode);
    }

    @Override
    public int updateDeviceAlarmSet(DeviceAlarmSet deviceAlarmSet)
            throws Exception {
        return deviceAlarmSetDao.updateDeviceAlarmSet(deviceAlarmSet);
    }

    @Override
    public int setDeviceAlarmSet(String thingCode, String maxValue, String minValue, String dataFlag, String levelNo,
                                 String excuteTime, boolean noDown, List<String> listDevCode,
                                 int optId) throws Exception {
        int resultCount = 0;
        if (listDevCode != null && listDevCode.size() > 0 && thingCode != null && !thingCode.isEmpty()) {
            List<DeviceAlarmSet> listDeviceAlarmSet = new ArrayList<DeviceAlarmSet>();
            List<CommMain> listCommMain = new ArrayList<CommMain>();
            List<Integer> listCommId = new ArrayList<Integer>();
            for (String devCode : listDevCode) {
                String qn = DateUtil.GetSystemDateTime();
                Device device = new Device();
                device.setDeviceCode(devCode);
                DeviceAlarmSet deviceAlarmSet = new DeviceAlarmSet();
                deviceAlarmSet.setDevice(device);
                Monitor monitor = new Monitor();
                monitor.setThingCode(thingCode);
                deviceAlarmSet.setMonitor(monitor);
                deviceAlarmSet.setMaxValue(Double.valueOf(maxValue));
                deviceAlarmSet.setMinValue(Double.valueOf(minValue));
                if (!StringUtils.isEmpty(dataFlag)) {
                    deviceAlarmSet.setDataFlag(Integer.valueOf(dataFlag));
                }
                if (!StringUtils.isEmpty(levelNo)) {
                    deviceAlarmSet.setLevelNo(Integer.valueOf(levelNo));
                }
                deviceAlarmSet.setOptUser(optId);
                CommMain commMain = new CommMain();
                commMain.setSt(DefaultArgument.PRO_ST_SYSTEM);
                commMain.setQn(qn);
                CommCn commCn = new CommCn();
                commCn.setCnCode(DefaultArgument.PRO_CN_ALARMSET);
                commMain.setCommCn(commCn);
                commMain.setExcuteTime(DateUtil.StringToTimestampSecond(excuteTime));
                commMain.setDevice(device);
                CommStatus commStatus = new CommStatus();
                if (noDown) {
                    commStatus.setStatusId(commMainService.getCommStatusId("1", "10"));
                } else {
                    commStatus.setStatusId(commMainService.getCommStatusId("1", "0"));
                }
                commMain.setCommStatus(commStatus);
                if (noDown) {
                    commMain.setFlag("0");
                } else {
                    commMain.setFlag("1");
                }
                String cp = thingCode + "-UpValue=" + maxValue + "," + thingCode + "-LowValue=" + minValue;
                commMain.setCp(cp);
                commMain.setOptUser(optId);
                deviceAlarmSet.setCommMain(commMain);
                List<String> commIdList = deviceAlarmSetDao.getAlarmMainCommId(devCode, thingCode, commCn.getCnCode());
                if (listCommId != null && listCommId.size() > 0) {
                    for (String commId : commIdList) {
                        listCommId.add(Integer.valueOf(commId));
                    }
                }
                listDeviceAlarmSet.add(deviceAlarmSet);
                listCommMain.add(commMain);
                resultCount++;
                Thread.sleep(1);//防止重复
            }
            // 进行数据库操作
            if (resultCount > 0) {
                if (listCommId != null && listCommId.size() > 0) {
                    commMainService.deleteCommMain(listCommId);
                }
                deviceAlarmSetDao.deleteDeviceAlarmSet(listDeviceAlarmSet);
                deviceAlarmSetDao.insertDeviceAlarmSet(listDeviceAlarmSet);
                commMainService.insertCommMain(listCommMain);
            }
        }
        return resultCount;
    }

    @Override
    public List<DeviceAlarmSet> getDeviceAlarmSetSingle(String deviceCode,
                                                        List<String> thingCodeList) {
        return deviceAlarmSetDao.getDeviceAlarmSetSingle(deviceCode, thingCodeList);
    }

    @Override
    public int deleteDeviceAlarmSet(List<Integer> listDasId) {
        int resultCount = 0;
        if (listDasId != null && listDasId.size() > 0) {
            List<Integer> listCommId = new ArrayList<Integer>();
            // 获取计划主表id
            List<CommMain> listCommMain = deviceAlarmSetDao
                    .getCommMainIdByDasId(listDasId);
            if (listCommMain != null && listCommMain.size() > 0) {
                for (CommMain commMain : listCommMain) {
                    listCommId.add(commMain.getCommId());
                }
            }
            // 删除数据
            resultCount = deviceAlarmSetDao
                    .deleteDeviceAlarmSetByDasId(listDasId);
            if (resultCount > 0) {
                commMainService.deleteCommMain(listCommId);
            }
        }
        return resultCount;
    }

}
