package com.tcb.env.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.tcb.env.model.AlarmModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Alarm;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.Status;
import com.tcb.env.service.IAlarmService;
import com.tcb.env.service.IMapService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;

/**
 * <p>
 * [功能描述]：报警控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月26日下午2:58:55
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/AlarmController")
public class AlarmController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AuthorityDetailController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AlarmController.class);

    /**
     * 声明报警服务类
     */
    @Resource
    private IAlarmService alarmService;

    /**
     * 声明键值服务类
     */
    @Resource
    private IMapService mapService;
    /**
     * 声明树服务
     */
    @Resource
    private ITreeService treeService;

    /**
     * 声明控制类
     */
    @Resource
    private DeviceController deviceController;

    /**
     * <p>
     * [功能描述]：查询报警信息
     * </p>
     *
     * @param alarmmodel
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月26日下午3:30:59
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/queryAlarms", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AlarmModel> queryAlarms(
            @RequestParam(value = "projectId", required = false) String projectId,
            AlarmModel alarmmodel, HttpSession httpsession) {
        ResultListModel<AlarmModel> resultListModel = new ResultListModel<AlarmModel>();
        if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
            return resultListModel;
        }
        Alarm alarm = ConvertAlarm(alarmmodel, httpsession);
        List<String> listdevicecode = new ArrayList<String>();
        String usercode = null;
        UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        List<TreeModel> listDev = treeService.getAuthorityDevices(usercode, projectId,
                DefaultArgument.INT_DEFAULT, null, null, null);
        for (TreeModel treeModel : listDev) {
            listdevicecode.add(treeModel.getId());
        }
        if (listdevicecode != null && listdevicecode.size() > 0) {
            List<AlarmModel> listAlarMmodel = new ArrayList<AlarmModel>();
            List<Alarm> listAlarm = new ArrayList<Alarm>();
            int count = alarmService.getAlarmCount(alarm, listdevicecode);
            if (count > 0) {
                listAlarm = alarmService.getAlarm(alarm, listdevicecode);
                for (Alarm temp : listAlarm) {
                    AlarmModel alarmModel = ConvertAlarmModel(temp);
                    if (alarmModel != null) {
                        listAlarMmodel.add(alarmModel);
                    }
                }
                resultListModel.setRows(listAlarMmodel);
            }
            resultListModel.setTotal(count);
        } else {
            resultListModel.setTotal(0);
        }
        return resultListModel;
    }

    /**
     * 查询报警信息（预处理）
     *
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/preQueryAlarms", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AlarmModel> preQueryAlarms(@RequestBody Map map, HttpSession session) {
        int rows = (Integer) map.get("rows");
        int page = (Integer) map.get("page");
        String beginAlarmTime = (String) map.get("beginAlarmTime");
        String endAlarmTime = (String) map.get("endAlarmTime");
        String deviceName = null;
        Object o = map.get("deviceName");
        if (o != null) {
            deviceName = (String) o;
        }

        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setRows(rows);
        alarmModel.setPage(page);
        alarmModel.setBeginAlarmTime(beginAlarmTime);
        alarmModel.setEndAlarmTime(endAlarmTime);
        if (deviceName != null) {
            alarmModel.setDeviceName(deviceName);
        }

        return queryAlarms(null, alarmModel, session);
    }

    @RequestMapping(value = "/updateAlarms", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateAlarms(AlarmModel alarmmodel, @RequestParam(value = "list[]") List<String> list, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (alarmmodel != null) {
            try {
                Alarm alarm = new Alarm();
                alarm = ConvertAlarm(alarmmodel, httpsession);
                int intresult = alarmService.updateAlarm(alarm, list);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新报警信息失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新报警信息失败！");
                logger.error(LOG + "：更新报警信息失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：删除报警信息
     * </p>
     *
     * @param list
     * @return
     * @author 王垒, 2016年3月22日下午12:39:32
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteAlarms", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteAlarms(@RequestParam(value = "list[]") List<Integer> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<Integer> listid = new ArrayList<Integer>();
                for (Integer alarmid : list) {
                    listid.add(alarmid);
                }
                int intresult = alarmService.deleteAlarm(listid);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除报警信息失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除报警信息失败！");
                logger.error(LOG + "：删除报警信息失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除报警信息失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * <p>
     * [功能描述]：AlarmModel转换成Alarm
     * </p>
     *
     * @param alarmModel
     * @param httpsession
     * @return
     * @author 王垒, 2016年4月26日下午3:15:19
     * @since EnvDust 1.0.0
     */
    private Alarm ConvertAlarm(AlarmModel alarmModel, HttpSession httpsession) {
        Alarm alarm = new Alarm();
        if (alarmModel != null) {
            alarm.setAlarmId(alarmModel.getAlarmId());
            Status status = new Status();
            status.setStatusCode(alarmModel.getAlarmType());
            alarm.setStatus(status);
            Device device = new Device();
            device.setDeviceCode(alarmModel.getDeviceCode());
            device.setDeviceName(alarmModel.getDeviceName());
            alarm.setDevice(device);
            alarm.setAlarmInfo(alarmModel.getAlarmInfo());
            alarm.setAlarmAction(alarmModel.getAlarmAction());
            alarm.setActionUser(alarmModel.getActionUser());
            alarm.setAlarmStatus(alarmModel.getAlarmStatus());
            String alarmTime = alarmModel.getAlarmTime();
            if (alarmTime != null && !alarmTime.isEmpty()) {
                alarm.setAlarmTime(DateUtil.StringToTimestampSecond(alarmTime));
            }
            String actionTime = alarmModel.getActionTime();
            if (actionTime != null && !actionTime.isEmpty()) {
                alarm.setActionTime(DateUtil.StringToTimestampSecond(actionTime));
            } else {
                alarm.setActionTime(DateUtil.GetSystemDateTime(0));
            }
            alarm.setBeginAlarmTime(alarmModel.getBeginAlarmTime());
            alarm.setEndAlarmTime(alarmModel.getEndAlarmTime());
            alarm.setExecuteUpdate(alarmModel.getExecuteUpdate());
            UserModel loginUser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginUser != null) {
                alarm.setOptUser(loginUser.getUserId());
            }
            alarm.setRowCount(alarmModel.getRows());
            alarm.setRowIndex((alarmModel.getPage() - 1) * alarmModel.getRows());
            String dataTime = alarmModel.getDataTime();
            if (!StringUtils.isEmpty(dataTime)) {
                alarm.setDataTime(DateUtil.StringToTimestampSecond(dataTime));
            }
        }
        return alarm;
    }

    /**
     * <p>
     * [功能描述]：Alarm转换成AlarmModel
     * </p>
     *
     * @param alarm
     * @return
     * @author 王垒, 2016年4月26日下午3:15:19
     * @since EnvDust 1.0.0
     */
    private AlarmModel ConvertAlarmModel(Alarm alarm) {
        AlarmModel alarmModel = new AlarmModel();
        if (alarm != null) {
            alarmModel.setAlarmId(alarm.getAlarmId());
            alarmModel.setAlarmType(alarm.getStatus().getStatusCode());
            alarmModel.setAlarmTypeName(alarm.getStatus().getStatusName());
            if (alarm.getDevice() != null) {
                alarmModel.setDeviceCode(alarm.getDevice().getDeviceCode());
                alarmModel.setDeviceName(alarm.getDevice().getDeviceName());
                if (alarm.getDevice().getArea() != null) {
                    alarmModel.setAreaId(String.valueOf(alarm.getDevice().getArea().getAreaId()));
                    alarmModel.setAreaName(alarm.getDevice().getArea().getAreaName());
                }
            }
            alarmModel.setLevelNo(alarm.getLevelNo());
            if (alarm.getMonitor() != null) {
                String alarmInfo = "";
                if (!StringUtils.isEmpty(alarm.getLevelNo())) {
                    if (alarm.getLevelNo().equals("1")) {
                        alarmInfo = "普通报警";
                    } else if (alarm.getLevelNo().equals("2")) {
                        alarmInfo = "紧急报警";
                    } else if (alarm.getLevelNo().equals("3")) {
                        alarmInfo = "严重报警";
                    } else {
                        alarmInfo = alarmModel.getAlarmInfo();
                    }
                } else {
                    alarmInfo = alarmModel.getAlarmInfo();
                }
                String thingName = alarm.getMonitor().getThingName();
                if (thingName != null && !thingName.isEmpty()) {
                    alarmModel.setAlarmInfo(alarm.getAlarmInfo() + ":" + thingName);
                    double thingValue = alarm.getThingValue();
                    if (thingValue != DefaultArgument.DOUBLE_DEFAULT) {
                        alarmModel.setAlarmInfo(alarmInfo + "(" + thingName + ":" + thingValue + ")");
                    }
                } else {
                    alarmModel.setAlarmInfo(alarmInfo);
                }
            } else {
                String alarmInfo = "";
                if (!StringUtils.isEmpty(alarm.getLevelNo())) {
                    if (alarm.getLevelNo().equals("1")) {
                        alarmInfo = alarm.getAlarmInfo() + "(普通报警)";
                    } else if (alarm.getLevelNo().equals("2")) {
                        alarmInfo = alarm.getAlarmInfo() + "(紧急报警)";
                    } else if (alarm.getLevelNo().equals("3")) {
                        alarmInfo = alarm.getAlarmInfo() + "(严重报警)";
                    } else {
                        alarmInfo = alarmModel.getAlarmInfo();
                    }
                } else {
                    alarmInfo = alarmModel.getAlarmInfo();
                }
                alarmModel.setAlarmInfo(alarmInfo);
            }
            alarmModel.setAlarmAction(alarm.getAlarmAction());
            alarmModel.setSendFlag(alarm.getSendFlag());
            alarmModel.setActionUser(alarm.getActionUser());
            String alarmStatus = alarm.getAlarmStatus();
            alarmModel.setAlarmStatus(alarmStatus);
            if (alarmStatus.equals("2")) {
                alarmModel.setAlarmStatusInfo("已解决");
            } else if (alarmStatus.equals("1")) {
                alarmModel.setAlarmStatusInfo("未解决");
            } else {
                alarmModel.setAlarmStatusInfo("无状态");
            }
            alarmModel.setAlarmTime(DateUtil.TimestampToString(alarm.getAlarmTime(), DateUtil.DATA_TIME));
            alarmModel.setActionTime(DateUtil.TimestampToString(alarm.getActionTime(), DateUtil.DATA_TIME));
            String dateTime = DateUtil.TimestampToString(alarm.getDataTime(), DateUtil.DEFAULT_HOUR);
            if (!StringUtils.isEmpty(dateTime)) {
                alarmModel.setDataTime(dateTime);
            }
        }
        return alarmModel;
    }

    /**
     * <p>
     * [功能描述]：获取系统状态
     * </p>
     *
     * @param status   ：状态值
     * @param nostatus ：非状态值
     * @param nostatus ：状态类型
     * @return
     * @author 王垒, 2016年4月28日下午12:36:49
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getStatus", method = {RequestMethod.POST})
    @ResponseBody
    public List<MapModel> getStatus(String status, String nostatus, String statusType) {
        List<MapModel> listMap = new ArrayList<MapModel>();
        try {
            listMap = mapService.getStatus(status, nostatus, statusType);

        } catch (Exception e) {
            logger.error(LOG + ":查询系统状态数据错误，错误信息为：" + e.getMessage());
        }
        return listMap;
    }

}
