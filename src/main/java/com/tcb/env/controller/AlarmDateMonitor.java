package com.tcb.env.controller;

import com.tcb.env.Handler.WebsocketHandler;
import com.tcb.env.model.MapMonitorData;
//import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.service.ISysflagService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Set;

/**
 * <p>
 * [功能描述]：警报数据监控器
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 王坤
 * @version 1.0, 2018年9月14日下午14:37:07
 * @since EnvDust 1.0.0
 */
@Component
public class AlarmDateMonitor {
    /**
     * 日志输出标记
     */
    private static final String LOG = "AlarmDateMonitor";

    /**
     * 警报数据监控器日志
     */
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AlarmDateMonitor.class);

    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明系统标识服务
     */
    @Resource
    private ISysflagService sysflagService;

    /**
     * 声明device对象
     */
    @Resource
    private IDeviceService deviceService;

    /**
     *
     */
    @Resource
    private WebsocketHandler websocketHandler;

    /**
     * 数据查询的线程
     */
    private Thread mapdev;

    /**
     * <p>
     * [功能描述]：查询地图设备状态线程
     * </p>
     *
     * @return
     * @throws Exception
     * @author 王垒, 2016年4月11日下午3:05:29
     * @since EnvDust 1.0.0
     */
    private void waittingMapDev() throws Exception {
        mapdev = new Thread("mapdev") {
            public void run() {
//                String usercode = null;
                while (true) {
//                    logger.info("循环" + DeviceController.monitorMap.size());
                    if (!DeviceController.monitorMap.isEmpty()) {
                        Set<String> set = DeviceController.monitorMap.keySet();
                        for (String sessionId : set) {
                            try {
                                /* 所有要查询的设备号 */
//	                            List<String> listdevicecode = new ArrayList<>();
                                MapMonitorData mapMonitorData = DeviceController.monitorMap.get(sessionId);
                                HttpSession httpSession = mapMonitorData.getHttpSession();
                                UserModel loginuser = (UserModel) httpSession.getAttribute(DefaultArgument.LOGIN_USER);
                                /* 警报更新时间 */
                                Timestamp alarmUpdateTime = loginuser.getAlarmUpdateTime();
                                if (SessionManager.isSessionValidate(mapMonitorData.getHttpSession(), DefaultArgument.LOGIN_USER)) {
                                    return;
                                }
//                                else{
//                                    if (loginuser != null) {
//                                        usercode = loginuser.getUserCode();
//                                    }
//                                }
                                /* 获取去更新系统标识 */
                                Timestamp alarmUpdateTime_New = DateUtil
                                        .StringToTimestampSecond(sysflagService.getSysFlagValue(DefaultArgument.ALARM_UPD));
                                if (alarmUpdateTime_New == null) {
                                    return;
                                }
                                /* 查看更新时间与警报时间不同 */
                                if (!alarmUpdateTime_New.equals(alarmUpdateTime)) {
//                                    if (DefaultArgument.NONE_DEFAULT.equals(mapMonitorData.getList().get(0))) {
//                                        return;
//                                    } else {
//                                        /* 查询所有的数据 */
//                                        if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(mapMonitorData.getList().get(0))) {
//                                            List<TreeModel> listDev = treeService.getAuthorityDevices(
//                                                    usercode,
//                                                    DefaultArgument.INT_DEFAULT,
//                                                    null,
//                                                    null,
//                                                    mapMonitorData.getNoStatus());
//                                            for (TreeModel treeModel : listDev) {
//                                                listdevicecode.add(treeModel.getId());
//                                            }
//                                        } else {
//                                            if (mapMonitorData.getLevelFlag() != null && !mapMonitorData.getLevelFlag().isEmpty()) {
//                                                List<Integer> listareaid = new ArrayList<>();
//                                                /* 查询所有的区域id */
//                                                listareaid = treeService.getAuthorityBottomArea(
//                                                        listareaid, Integer.valueOf(mapMonitorData.getList().get(0)),
//                                                        usercode);
//                                                if (listareaid != null && listareaid.size() > 0) {
//                                                    for (Integer areaid : listareaid) {
//                                                        List<TreeModel> listDev = treeService
//                                                                .getAuthorityDevices(usercode, areaid,null,null,mapMonitorData.getNoStatus());
//                                                        for (TreeModel treeModel : listDev) {
//                                                            if (mapMonitorData.getMaxSize() > listdevicecode.size()) {
//                                                                listdevicecode.add(treeModel.getId());
//                                                            } else {
//                                                                break;
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }else{
//                                                for (String temp : mapMonitorData.getList()) {
//                                                    listdevicecode.add(temp);
//                                                }
//                                            }
//                                        }
//                                        int count = deviceService.getMapAlarmDeviceCount(listdevicecode,alarmUpdateTime,null);
//                                        if (count > 0) {
//                                            /* 改变警报更新时间 */
//                                            loginuser.setAlarmUpdateTime(alarmUpdateTime_New);
//                                            /* 向变化的客户端发送消息 */
//                                            if(DeviceController.monitorMap.containsKey(sessionId)){
//                                                websocketHandler.sendMessage(sessionId,"dateUpdate");
//                                            }
//                                        }
//                                    }
                                    /* 改变警报更新时间 */
                                    loginuser.setAlarmUpdateTime(alarmUpdateTime_New);
                                    /* 向变化的客户端发送消息 */
                                    if (DeviceController.monitorMap.containsKey(sessionId)) {
                                        websocketHandler.sendMessage(sessionId, "dateUpdate");
                                    }
                                }
                            } catch (IllegalStateException e) {
                                logger.error(LOG + ":sessoin过期,sessoin ID为:" + sessionId + ",错误信息为：" + e);
                                websocketHandler.removeSessionId(sessionId);
                            } catch (Exception e) {
                                logger.error(LOG + ":mapdev线程终止,信息为：" + e);
                                websocketHandler.removeSessionId(sessionId);
                            }
                        }
                    }
                    try {
                        sleep(DefaultArgument.TIMELY_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mapdev.start();
        /* 效率低 */
        mapdev.join();// 保证当线程执行完后在执行后续任务
    }

    /**
     * 判断警报数据监控是否存活
     *
     * @return
     */
    public boolean isAlive() {
        boolean flag = false;
        if (mapdev != null) {
            flag = mapdev.isAlive();
        }
        return flag;
    }

    /**
     * 开启警报数据监控
     */
    public void start() {
        try {
            Thread websocketThread = new Thread("websocket数据线程") {
                public void run() {
                    try {
                        waittingMapDev();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            websocketThread.start();
        } catch (Exception e) {
            logger.error(LOG + "警报数据监控器异常，信息为：" + e.getMessage());
        }
    }
}