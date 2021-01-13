package com.tcb.env.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tcb.env.message.ExportAlarmMessage;
import com.tcb.env.message.ExportOdmAvgMessage;
import com.tcb.env.message.ExportOdmRtdMessage;
import com.tcb.env.model.AlarmModel;
import com.tcb.env.model.OriginalDataModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.service.IDeviceService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.ExportExcel;
import com.tcb.env.util.EnumUtil.TimeFreque;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * [功能描述]：导出excel控制器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月7日下午9:49:33
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping(value = "/ExportController")
public class ExportController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "ExportController";
    /**
     * 声明日志对象
     */
    private static Log logger = LogFactory.getLog(ExportController.class);

    /**
     * 声明监测物查询控制器
     */
    @Resource
    private MonitorStorageController monitorStorageController;

    /**
     * 声明报警控制器
     */
    @Resource
    private AlarmController alarmController;

    /**
     * 声明设备服务
     */
    @Resource
    private IDeviceService deviceService;

    /**
     * @return the monitorStorageController
     */
    public MonitorStorageController getMonitorStorageController() {
        return monitorStorageController;
    }

    /**
     * @param monitorStorageController the monitorStorageController to set
     */
    public void setMonitorStorageController(
            MonitorStorageController monitorStorageController) {
        this.monitorStorageController = monitorStorageController;
    }

    /**
     * <p>
     * [功能描述]：导出excel文件(单站点多参数)
     * </p>
     *
     * @param zsFlag
     * @param devicecode
     * @param thingcode
     * @param starttime
     * @param endtime
     * @param freque
     * @param request
     * @param response
     * @throws Exception
     * @author 王垒, 2016年4月9日上午12:31:01
     * @since EnvDust 1.0.0
     */
    @SuppressWarnings({"rawtypes"})
    @RequestMapping(value = "/exportMultiThings", method = {RequestMethod.GET,
            RequestMethod.POST})
    public void exportMultiThings(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, String thingcode, String starttime, String endtime, String freque,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String[] arrayThingCode = thingcode.split(",");
        List<String> list = Arrays.asList(arrayThingCode);
        TreeMap<String, List<String>> treeMap = monitorStorageController
                .getSingleDeviceStatisticsChartData(zsFlag, devicecode, list, starttime, endtime, freque);
        Set keySet = treeMap.keySet();
        String headers[] = new String[keySet.size()];
        int i = 0;
        for (Object key : keySet) {
            if (DefaultArgument.STA_TIME.equals(key)) {
                headers[0] = key.toString();//time放在第一栏
            } else {
                i++;
                headers[i] = key.toString();
            }
        }
        String title = "历史数据导出";
        TimeFreque timeFreque = TimeFreque.valueOf(freque
                .toUpperCase(Locale.ENGLISH));
        switch (timeFreque) {
            case PERHOUR: {
                title = "小时历史数据导出";
            }
            break;
            case PERDAY: {
                title = "天历史数据导出";
            }
            break;
            case PERMONTH: {
                title = "月历史数据导出";
            }
            break;
            case PERQUARTER: {
                title = "季度历史数据导出";
            }
            break;
            default:
                break;
        }

        String filename = System.currentTimeMillis() + ".xls";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
        try (OutputStream out = response.getOutputStream()) {
            ExportExcel<T> excelUtil = new ExportExcel<T>();
            excelUtil.exportExcel(title, headers, treeMap, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LOG + "：导出失败，原因：" + e.getMessage());
        }
    }

    /**
     * <p>
     * [功能描述]：导出excel文件(多站点单参数)
     * </p>
     *
     * @param zsFlag
     * @param devicecode
     * @param thingcode
     * @param starttime
     * @param endtime
     * @param freque
     * @param request
     * @param response
     * @throws Exception
     * @author 王垒, 2016年4月9日上午12:31:01
     * @since EnvDust 1.0.0
     */
    @SuppressWarnings({"rawtypes"})
    @RequestMapping(value = "/exportMultiDevices", method = {RequestMethod.GET,
            RequestMethod.POST})
    public void exportMultiDevices(
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String devicecode, String thingcode, String starttime, String endtime, String freque,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String[] arrayDeviceCode = devicecode.split(",");
        List<String> list = Arrays.asList(arrayDeviceCode);
        TreeMap<String, List<String>> treeMap = monitorStorageController
                .getSingleThingStatisticsChartData(zsFlag, list, thingcode, starttime, endtime, freque);
        Set keySet = treeMap.keySet();
        String headers[] = new String[keySet.size()];
        int i = 0;
        for (Object key : keySet) {
            if (DefaultArgument.STA_TIME.equals(key)) {
                headers[0] = key.toString();//time放在第一栏
            } else {
                i++;
                headers[i] = key.toString();
            }
        }
        String title = "历史数据导出";
        TimeFreque timeFreque = TimeFreque.valueOf(freque
                .toUpperCase(Locale.ENGLISH));
        switch (timeFreque) {
            case PERHOUR: {
                title = "小时历史数据导出";
            }
            break;
            case PERDAY: {
                title = "天历史数据导出";
            }
            break;
            case PERMONTH: {
                title = "月历史数据导出";
            }
            break;
            case PERQUARTER: {
                title = "季度历史数据导出";
            }
            break;
            default:
                break;
        }

        String filename = System.currentTimeMillis() + ".xls";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
        try (OutputStream out = response.getOutputStream()) {
            ExportExcel<T> excelUtil = new ExportExcel<T>();
            excelUtil.exportExcel(title, headers, treeMap, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LOG + "：导出失败，原因：" + e.getMessage());
        }
    }

    /**
     * <p>[功能描述]：导出Excel文件(报警)</p>
     *
     * @param projectId
     * @param devicename
     * @param alarmtype
     * @param alarmstatus
     * @param startalarmtime
     * @param endalarmtime
     * @param request
     * @param response
     * @param httpsession
     * @throws Exception
     * @author 王垒, 2018年1月30日上午8:36:50
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/exportAlarms", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportAlarms(
            @RequestParam(value = "projectId", required = false) String projectId,
            String devicename, String alarmtype, String alarmstatus, String startalarmtime, String endalarmtime,
            HttpServletRequest request, HttpServletResponse response, HttpSession httpsession)
            throws Exception {
        List<AlarmModel> listAlarMmodel = new ArrayList<AlarmModel>();
        AlarmModel alarmModel = new AlarmModel();
        alarmModel.setDeviceName(devicename);
        alarmModel.setAlarmType(alarmtype);
        alarmModel.setAlarmStatus(alarmstatus);
        alarmModel.setBeginAlarmTime(startalarmtime);
        alarmModel.setEndAlarmTime(endalarmtime);
        ResultListModel<AlarmModel> resultListModel = alarmController.queryAlarms(projectId, alarmModel, httpsession);
        if (resultListModel.getTotal() > 0) {
            listAlarMmodel = resultListModel.getRows();
        }
        String headers[] = new String[]{"报警类型", "站点名称", "所属区域", "报警信息", "报警状态", "报警时间", "报警处理", "处理人", "处理时间"};
        String title = "报警数据导出";
        //将数据重新组合成导出的类与列对齐
        List<ExportAlarmMessage> listexportalarm = new ArrayList<ExportAlarmMessage>();
        for (AlarmModel temp : listAlarMmodel) {
            ExportAlarmMessage exportAlarmMessage = new ExportAlarmMessage();
            exportAlarmMessage.setAlarmTypeName(temp.getAlarmTypeName());
            exportAlarmMessage.setDeviceName(temp.getDeviceName());
            exportAlarmMessage.setAreaName(temp.getAreaName());
            exportAlarmMessage.setAlarmInfo(temp.getAlarmInfo());
            exportAlarmMessage.setAlarmStatusInfo(temp.getAlarmStatusInfo());
            exportAlarmMessage.setAlarmTime(temp.getAlarmTime());
            exportAlarmMessage.setAlarmAction(temp.getAlarmAction());
            exportAlarmMessage.setActionUser(temp.getActionUser());
            exportAlarmMessage.setActionTime(temp.getActionTime());
            listexportalarm.add(exportAlarmMessage);
        }
        String filename = System.currentTimeMillis() + ".xls";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
        ExportExcel<ExportAlarmMessage> excelUtil = new ExportExcel<ExportAlarmMessage>();
        try (OutputStream out = response.getOutputStream()) {
            excelUtil.exportExcel(title, headers, listexportalarm, out);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LOG + "：导出失败，原因：" + e.getMessage());
        }
    }

    /**
     * 导出excel文件(原始数据)
     *
     * @param list
     * @param zsFlag
     * @param deviceCode
     * @param thingCode
     * @param updateType
     * @param beginTime
     * @param endTime
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportOriginalData", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportOriginalData(
            @RequestParam(value = "list", required = false) String list,
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            String deviceCode, String thingCode, String updateType, String beginTime, String endTime,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<OriginalDataModel> listODM = new ArrayList<OriginalDataModel>();
        OriginalDataModel originalDataModel = new OriginalDataModel();
        originalDataModel.setDeviceCode(deviceCode);
        originalDataModel.setThingCode(thingCode);
        originalDataModel.setUpdateType(updateType);
        originalDataModel.setBeginTime(beginTime);
        originalDataModel.setEndTime(endTime);
        List<String> listDeviceCode = new ArrayList<>();
        if (!StringUtils.isEmpty(list)) {
            if (list.contains(",")) {
                listDeviceCode = Arrays.asList(list.split(","));
            } else {
                listDeviceCode = Arrays.asList(new String[]{list});
            }
        }
        ResultListModel<OriginalDataModel> resultListModel = monitorStorageController.getOriginalData(listDeviceCode, originalDataModel, deviceCode, request.getSession());
        if (resultListModel.getTotal() > 0) {
            listODM = resultListModel.getRows();
        }
        String headers[];
        if (updateType != null) {
            if (updateType.equals("2011")) {
                headers = new String[]{"设备编码", "设备MN号", "设备名称", "监测物名称", "数据类型", "实时值", "实时数据采集时间", "数据标识", "系统录入时间"};
            } else {
                headers = new String[]{"设备编码", "设备MN号", "设备名称", "监测物名称", "数据类型", "平均值", "最大值", "最小值", "开始采集时间", "结束采集时间", "数据标识", "系统录入时间"};
            }
        } else {
            return;
        }
        String areaName = deviceService.getAreaName(deviceCode);
        String title = areaName;
        //将数据重新组合成导出的类与列对齐
        if (updateType.equals("2011")) {
            List<ExportOdmRtdMessage> listExportOdm = new ArrayList<ExportOdmRtdMessage>();
            for (OriginalDataModel temp : listODM) {
                ExportOdmRtdMessage exportOdmRtdMessage = new ExportOdmRtdMessage();
                exportOdmRtdMessage.setDeviceCode(temp.getDeviceCode());
                exportOdmRtdMessage.setDeviceMn(temp.getDeviceMn());
                exportOdmRtdMessage.setDeviceName(temp.getDeviceName());
                exportOdmRtdMessage.setThingName(temp.getThingName());
                exportOdmRtdMessage.setUpdateTypeName(temp.getUpdateTypeName());
                if (zsFlag) {
                    String thingZsRtd = temp.getThingZsRtd();
                    if (StringUtils.isEmpty(thingZsRtd)) {
                        thingZsRtd = "---";
                    }
                    exportOdmRtdMessage.setThingRtd(temp.getThingRtd() + "/" + thingZsRtd);
                } else {
                    exportOdmRtdMessage.setThingRtd(temp.getThingRtd());
                }
                exportOdmRtdMessage.setRtdTime(temp.getRtdTime());
                exportOdmRtdMessage.setStatusName(temp.getStatusName());
                exportOdmRtdMessage.setUpdateTime(temp.getUpdateTime());
                listExportOdm.add(exportOdmRtdMessage);
            }
            String filename = System.currentTimeMillis() + ".xls";
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            OutputStream out = response.getOutputStream();
            ExportExcel<ExportOdmRtdMessage> excelUtil = new ExportExcel<ExportOdmRtdMessage>();
            try {
                excelUtil.exportExcel(title, headers, listExportOdm, out);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(LOG + "：导出失败，原因：" + e.getMessage());
            } finally {
                out.flush();
                out.close();
            }
        } else {
            List<ExportOdmAvgMessage> listExportOdm = new ArrayList<ExportOdmAvgMessage>();
            for (OriginalDataModel temp : listODM) {
                ExportOdmAvgMessage exportOdmAvgMessage = new ExportOdmAvgMessage();
                exportOdmAvgMessage.setDeviceCode(temp.getDeviceCode());
                exportOdmAvgMessage.setDeviceMn(temp.getDeviceMn());
                exportOdmAvgMessage.setDeviceName(temp.getDeviceName());
                exportOdmAvgMessage.setThingName(temp.getThingName());
                exportOdmAvgMessage.setUpdateTypeName(temp.getUpdateTypeName());
                if (zsFlag) {
                    String thingZsAvg = temp.getThingZsAvg();
                    String thingZsMax = temp.getThingZsMax();
                    String thingZsMin = temp.getThingZsMin();
                    if (StringUtils.isEmpty(thingZsAvg)) {
                        thingZsAvg = "---";
                    }
                    if (StringUtils.isEmpty(thingZsMax)) {
                        thingZsMax = "---";
                    }
                    if (StringUtils.isEmpty(thingZsMin)) {
                        thingZsMin = "---";
                    }
                    exportOdmAvgMessage.setThingAvg(temp.getThingAvg() + "/" + thingZsAvg);
                    exportOdmAvgMessage.setThingMax(temp.getThingMax() + "/" + thingZsMax);
                    exportOdmAvgMessage.setThingMin(temp.getThingMin() + "/" + thingZsMin);
                } else {
                    exportOdmAvgMessage.setThingAvg(temp.getThingAvg());
                    exportOdmAvgMessage.setThingMax(temp.getThingMax());
                    exportOdmAvgMessage.setThingMin(temp.getThingMin());
                }
                exportOdmAvgMessage.setBeginTime(temp.getBeginTime());
                exportOdmAvgMessage.setEndTime(temp.getEndTime());
                exportOdmAvgMessage.setStatusName(temp.getStatusName());
                exportOdmAvgMessage.setUpdateTime(temp.getUpdateTime());
                listExportOdm.add(exportOdmAvgMessage);
            }
            String filename = System.currentTimeMillis() + ".xls";
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            ExportExcel<ExportOdmAvgMessage> excelUtil = new ExportExcel<ExportOdmAvgMessage>();
            try (OutputStream out = response.getOutputStream()) {
                excelUtil.exportExcel(title, headers, listExportOdm, out);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(LOG + "：导出失败，原因：" + e.getMessage());
            }
        }
    }

}
