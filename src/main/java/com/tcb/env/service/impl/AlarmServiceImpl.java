package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.dao.IAlarmDao;
import com.tcb.env.dao.IDeviceDao;
import com.tcb.env.pojo.Alarm;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.IAlarmService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import org.springframework.util.StringUtils;

/**
 * <p>
 * [功能描述]：报警操作服务类接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月26日下午2:53:53
 * @since EnvDust 1.0.0
 */
@Service("alarmService")
@Transactional(rollbackFor = Exception.class)
class AlarmServiceImpl implements IAlarmService {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AlarmServiceImpl";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AlarmServiceImpl.class);

    /**
     * 声明报警数据库操作接口
     */
    @Resource
    private IAlarmDao alarmDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    /**
     * 声明设备数据库操作接口
     */
    @Resource
    private IDeviceDao deviceDao;

    @Override
    public int getAlarmCount(Alarm alarm, List<String> listDevCode) {
        int count = 0;
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                String dbOldName = "";
                Timestamp begintime = DateUtil.StringToTimestampSecond(alarm.getBeginAlarmTime());
                if (!DateUtil.isRecentlyData(begintime, DefaultArgument.RECENT_DAYS)) {
                    dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                count = alarmDao.getAlarmCount(dbName, dbOldName, alarm, listDevCode);
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询报警数据个数失败，信息为：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<Alarm> getAlarm(Alarm alarm, List<String> listDevCode) {
        List<Alarm> alarmList = new ArrayList<Alarm>();
        try {
            if (listDevCode != null && listDevCode.size() > 0) {
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                String dbOldName = "";
                Timestamp begintime = DateUtil.StringToTimestampSecond(alarm.getBeginAlarmTime());
                if (!DateUtil.isRecentlyData(begintime, DefaultArgument.RECENT_DAYS)) {
                    dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                alarmList = alarmDao.getAlarm(dbName, dbOldName, alarm, listDevCode);
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询报警数据失败，信息为：" + e.getMessage());
        }
        return alarmList;
    }

    @Override
    public int updateAlarm(Alarm alarm, List<String> listId) throws Exception {
        int resultCount = 0;
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        if (listId != null && listId.size() > 0) {
            for (String temp : listId) {
                if (temp != null && !temp.isEmpty()) {
                    alarm.setAlarmId(Integer.valueOf(temp));
                    if (alarmDao.updateAlarm(dbName, alarm) > 0 || alarmDao.updateAlarm(dbOldName, alarm) > 0) {
                        resultCount += 1;
                        if (alarm.getExecuteUpdate()) {
                            String deviceCode = alarmDao.getDeviceCode(dbName, dbOldName, temp);
                            Device device = new Device();
                            device.setDeviceCode(deviceCode);
                            alarm.setDevice(device);
                            resultCount = deviceDao.updateDeviceStatus(alarm.getDevice().getDeviceCode(), "N");
                            if (resultCount <= 0) {
                                throw new Exception("更新设备状态失败");
                            }
                        }
                    }
                }
            }
        }
        return resultCount;
    }

    @Override
    public int deleteAlarm(List<Integer> listid) throws Exception {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        int countDb = alarmDao.deleteAlarm(dbName, listid);
        int countOldDb = alarmDao.deleteAlarm(dbOldName, listid);
        return countDb + countOldDb;
    }

    @Override
    public int deleteDeviceAlarm(String deviceCode) throws Exception {
        int result = 0;
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        if (!StringUtils.isEmpty(dbName) && !StringUtils.isEmpty(deviceCode)
                && deviceDao.extStorageTable(dbName, deviceCode) != null
                && !deviceDao.extStorageTable(dbName, deviceCode).isEmpty()) {
            result = alarmDao.deleteDeviceAlarm(dbName, deviceCode);
        }
        if (!StringUtils.isEmpty(dbOldName) && !StringUtils.isEmpty(deviceCode)
                && deviceDao.extStorageTable(dbOldName, deviceCode) != null
                && !deviceDao.extStorageTable(dbOldName, deviceCode).isEmpty()) {
            result = alarmDao.deleteDeviceAlarm(dbOldName, deviceCode);
        }
        return result;
    }

    @Override
    public List<Map> getRecentlyAlarmInfo(String deviceCode, String alarmType) {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        List<Map> list = alarmDao.getRecentlyAlarmInfo(dbName, deviceCode, alarmType);
        if (list == null || list.size() == 0) {
            String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            list = alarmDao.getRecentlyAlarmInfo(dbOldName, deviceCode, alarmType);
        }
        return list;
    }

    @Override
    public List<Alarm> getRecentlyAlarmIdInfo(String deviceCode, String alarmType) {
        List<Alarm> list = new ArrayList<Alarm>();
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        list = alarmDao.getRecentlyAlarmIdInfo(dbName, deviceCode, alarmType);
        if (list == null || list.size() == 0) {
            String dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
            list = alarmDao.getRecentlyAlarmIdInfo(dbOldName, deviceCode, alarmType);
        }
        return list;
    }

    @Override
    public List<Alarm> getSmsAlarmInfo() {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        return alarmDao.getSmsAlarmInfo(dbName);
    }

    @Override
    public int updateSmsAlarmFlag(String alarmId, boolean sendFlag) {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        return alarmDao.updateSmsAlarmFlag(dbName, alarmId, sendFlag);
    }

    @Override
    public List<String> getSmsPhone(String deviceId, String statusCode, String thingCode) {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        return alarmDao.getSmsPhone(dbName, deviceId, statusCode, thingCode);
    }

    @Override
    public List<String> getSmsMail(String deviceId, String statusCode, String thingCode) {
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        return alarmDao.getSmsMail(dbName, deviceId, statusCode, thingCode);
    }

    @Override
    public String getAlarmLevel(String deviceCode) {
        String levelNo = "";
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        levelNo = alarmDao.getAlarmLevel(dbName, deviceCode);
        if (StringUtils.isEmpty(levelNo)) {
            dbName = dom4jConfig.getDataBaseConfig().getDbOldName();
            levelNo = alarmDao.getAlarmLevel(dbName, deviceCode);
        }
        return levelNo;
    }

}
