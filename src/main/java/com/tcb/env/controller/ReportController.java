package com.tcb.env.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tcb.env.model.AlarmRateModel;
import com.tcb.env.model.DataCompareModel;
import com.tcb.env.model.DischargeModel;
import com.tcb.env.model.EnvStatisticalReportModel;
import com.tcb.env.model.OnlineMonitorReportMainModel;
import com.tcb.env.model.OnlineMonitorReportTimeModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.freemarker.OutPutExcel;

/**
 * <p>[功能描述]：freemarker报表控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月7日上午9:55:27
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/ReportController")
public class ReportController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "ReportController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(ReportController.class);

    /**
     * 声明年月日时统计报表控制器
     */
    @Resource
    private EnvStatisticalReportController envStatisticalReportController;

    /**
     * 声明站点排名统计报表控制器
     */
    @Resource
    private EnvStatisticalRankingController envStatisticalRankingController;


    /**
     * 声明同比环比统计报表控制器
     */
    @Resource
    private DataCompareController dataCompareController;

    /**
     * 污染排放统计报表控制器
     */
    @Resource
    private DischargeController dischargeController;

    /**
     * <p>[功能描述]：导出年月日时统计报表excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年5月7日上午10:25:16
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaExcel")
    public String downEnvStaExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String envTitle = "";
        String outTime = "";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String levelFlag = request.getParameter("levelFlag");
        String thingCode = request.getParameter("thingCode");
        String selectTime = request.getParameter("selectTime");
        String selectType = request.getParameter("selectType");
        String projectId = request.getParameter("projectId");
        String zsFlag = request.getParameter("zsFlag");
        List<String> list = new ArrayList<String>();
        list.add(selected);
        if ("hour".equals(selectType)) {
            envTitle = "小时报表";
        } else if ("day".equals(selectType)) {
            envTitle = "每日报表";
        } else if ("month".equals(selectType)) {
            envTitle = "月度报表";
        } else if ("year".equals(selectType)) {
            envTitle = "年度报表";
        }
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<EnvStatisticalReportModel> envStaList = new ArrayList<EnvStatisticalReportModel>();
        ResultListModel<EnvStatisticalReportModel> resultListModel = envStatisticalReportController.getEsrData
                (projectId, list, levelFlag, thingCode, selectTime, selectType, request.getSession());
        if (resultListModel.isResult()) {
            envStaList = resultListModel.getRows();
        }
        if (!StringUtils.isEmpty(zsFlag) && Boolean.valueOf(zsFlag) && envStaList != null && envStaList.size() > 0) {
            for (EnvStatisticalReportModel temp : envStaList) {
                if (!StringUtils.isEmpty(temp.getThingZsAvg())) {
                    temp.setThingAvg(temp.getThingAvg() + "/" + temp.getThingZsAvg());
                } else {
                    temp.setThingAvg(temp.getThingAvg() + "/---");
                }
                if (!StringUtils.isEmpty(temp.getThingZsMin())) {
                    temp.setThingMin(temp.getThingMin() + "/" + temp.getThingZsMin());
                } else {
                    temp.setThingMin(temp.getThingMin() + "/---");
                }
                if (!StringUtils.isEmpty(temp.getThingZsMax())) {
                    temp.setThingMax(temp.getThingMax() + "/" + temp.getThingZsMax());
                } else {
                    temp.setThingMax(temp.getThingMax() + "/---");
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("envTitle", envTitle);
        map.put("outTime", outTime);
        map.put("envStaList", envStaList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        //调用工具类OutPutExcel的createExcel方法生成Excel文档
        OutPutExcel outPutExcel = new OutPutExcel();
        File file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_DMY, map);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msexcel");
        String sfileName = envTitle + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

        try (InputStream fin = new FileInputStream(file);
             ServletOutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
//            ex.printStackTrace();
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }

    /**
     * <p>[功能描述]：导出时间段统计报表excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年5月7日上午10:25:16
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaTimesExcel")
    public String downEnvStaTimesExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String envTitle = "时间段报表";
        String outTime = "";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String levelFlag = request.getParameter("levelFlag");
        String thingCode = request.getParameter("thingCode");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String projectId = request.getParameter("projectId");
        List<String> list = new ArrayList<String>();
        list.add(selected);
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<EnvStatisticalReportModel> envStaList = new ArrayList<EnvStatisticalReportModel>();
        ResultListModel<EnvStatisticalReportModel> resultListModel = envStatisticalReportController.getEsrDataTimes
                (projectId, list, levelFlag, thingCode, beginTime, endTime, request.getSession());
        if (resultListModel.isResult()) {
            envStaList = resultListModel.getRows();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("envTitle", envTitle);
        map.put("outTime", outTime);
        map.put("envStaList", envStaList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        //调用工具类OutPutExcel的createExcel方法生成Excel文档
        OutPutExcel outPutExcel = new OutPutExcel();
        File file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_TIMES, map);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msexcel");
        String sfileName = envTitle + ".xls";
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

        try (InputStream fin = new FileInputStream(file);
             ServletOutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
//            ex.printStackTrace();
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }

    /**
     * <p>[功能描述]：导出连续监测统计报表excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年5月18日下午1:26:42
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaOlrExcel")
    public String downEnvStaOlrExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String olrTitle = "";
        request.setCharacterEncoding("utf-8");
        String deviceCode = request.getParameter("deviceCode");
        String thingCode = request.getParameter("thingCode");
        String selectTime = request.getParameter("selectTime");
        String selectType = request.getParameter("selectType");
        if ("day".equals(selectType)) {
            olrTitle = "挥发性有机物连续排放监测小时平均值日报表";
        } else if ("month".equals(selectType)) {
            olrTitle = "挥发性有机物连续排放监测日平均值月报表";
        } else if ("year".equals(selectType)) {
            olrTitle = "挥发性有机物连续排放监测月平均值年报表";
        }
        List<OnlineMonitorReportTimeModel> omrtModelList = new ArrayList<OnlineMonitorReportTimeModel>();
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = envStatisticalReportController.getOlrZsDataMain
                (deviceCode, thingCode, selectTime, selectType, request.getSession());
        if (onlineMonitorReportMainModel.isResult()) {
            omrtModelList = onlineMonitorReportMainModel.getOmrtModelList();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("olrTitle", olrTitle);
        map.put("deviceName", onlineMonitorReportMainModel.getDeviceName());
        map.put("deviceCode", onlineMonitorReportMainModel.getDeviceCode());
        map.put("deviceMn", onlineMonitorReportMainModel.getDeviceMn());
        map.put("monitorDate", onlineMonitorReportMainModel.getMonitorDate());
        map.put("thingName", onlineMonitorReportMainModel.getThingName());
        map.put("omrtModelList", omrtModelList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            if ("day".equals(selectType)) {
                file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_OLRD, map);
            } else if ("month".equals(selectType)) {
                file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_OLRM, map);
            } else if ("year".equals(selectType)) {
                file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_OLRY, map);
            }
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = olrTitle + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
//            ex.printStackTrace();
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }


    /**
     * <p>[功能描述]：导出连续监测统计报表excel-时间段</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年5月18日下午1:26:42
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaOlrTimesExcel")
    public String downEnvStaOlrTimesExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String olrTitle = "挥发性有机物连续排放监测日平均值时间段报表";
        request.setCharacterEncoding("utf-8");
        String deviceCode = request.getParameter("deviceCode");
        String thingCode = request.getParameter("thingCode");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        List<OnlineMonitorReportTimeModel> omrtModelList = new ArrayList<OnlineMonitorReportTimeModel>();
        OnlineMonitorReportMainModel onlineMonitorReportMainModel = envStatisticalReportController.getOlrZsDataMainTimes
                (deviceCode, thingCode, beginTime, endTime, request.getSession());
        if (onlineMonitorReportMainModel.isResult()) {
            omrtModelList = onlineMonitorReportMainModel.getOmrtModelList();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("olrTitle", olrTitle);
        map.put("deviceName", onlineMonitorReportMainModel.getDeviceName());
        map.put("deviceCode", onlineMonitorReportMainModel.getDeviceCode());
        map.put("deviceMn", onlineMonitorReportMainModel.getDeviceMn());
        map.put("monitorDate", beginTime + "至" + endTime);
        map.put("thingName", onlineMonitorReportMainModel.getThingName());
        map.put("omrtModelList", omrtModelList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_OLRT, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = olrTitle + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
//            ex.printStackTrace();
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }


    /**
     * <p>[功能描述]：导出站点排名统计报表excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年5月7日上午10:25:16
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaEsgExcel")
    public String downEnvStaEsgExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String envTitle = "";
        String outTime = "";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String levelFlag = request.getParameter("levelFlag");
        String thingCode = request.getParameter("thingCode");
        String selectTime = request.getParameter("selectTime");
        String selectType = request.getParameter("selectType");
        String orderType = request.getParameter("orderType");
        String dataType = request.getParameter("dataType");
        String projectId = request.getParameter("projectId");
        String zsFlag = request.getParameter("zsFlag");
        if (!StringUtils.isEmpty(zsFlag) && zsFlag.equals("true")) {
            zsFlag = "true";
        } else {
            zsFlag = "false";
        }
        List<String> list = new ArrayList<String>();
        list.add(selected);
        if ("hour".equals(selectType)) {
            envTitle = "站点小时排名报表";
        } else if ("day".equals(selectType)) {
            envTitle = "站点日排名报表";
        } else if ("month".equals(selectType)) {
            envTitle = "站点月排名报表";
        } else if ("year".equals(selectType)) {
            envTitle = "站点年排名报表";
        }
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<EnvStatisticalReportModel> envStaList = new ArrayList<EnvStatisticalReportModel>();
        ResultListModel<EnvStatisticalReportModel> resultListModel = envStatisticalRankingController.getEsgData
                (projectId, Boolean.parseBoolean(zsFlag), list, levelFlag, thingCode, selectTime, selectType, orderType, dataType, request.getSession());
        if (resultListModel.isResult()) {
            envStaList = resultListModel.getRows();
        }
        if (Boolean.parseBoolean(zsFlag) && envStaList != null && envStaList.size() > 0) {
            for (EnvStatisticalReportModel temp : envStaList) {
                temp.setThingAvg(temp.getThingZsAvg());
                temp.setThingMax(temp.getThingZsMax());
                temp.setThingMin(temp.getThingZsMin());
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("envTitle", envTitle);
        map.put("outTime", outTime);
        map.put("envStaList", envStaList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_DMY, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = envTitle + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }

    /**
     * <p>[功能描述]：导出站点排名统计报表excel-时间段</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年8月3日上午11:30:55
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaTimesEgsExcel")
    public String downEnvStaTimesEgsExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String envTitle = "站点排名时间段报表";
        String outTime = "";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String levelFlag = request.getParameter("levelFlag");
        String thingCode = request.getParameter("thingCode");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String orderType = request.getParameter("orderType");
        String dataType = request.getParameter("dataType");
        String projectId = request.getParameter("projectId");
        String zsFlag = request.getParameter("zsFlag");
        if (!StringUtils.isEmpty(zsFlag) && zsFlag.equals("true")) {
            zsFlag = "true";
        } else {
            zsFlag = "false";
        }
        List<String> list = new ArrayList<String>();
        list.add(selected);
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<EnvStatisticalReportModel> envStaList = new ArrayList<EnvStatisticalReportModel>();
        ResultListModel<EnvStatisticalReportModel> resultListModel = envStatisticalRankingController.getEsgDataTimes
                (projectId, Boolean.valueOf(zsFlag), list, levelFlag, thingCode, beginTime, endTime, orderType, dataType, request.getSession());
        if (resultListModel.isResult()) {
            envStaList = resultListModel.getRows();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("envTitle", envTitle);
        map.put("outTime", outTime);
        map.put("envStaList", envStaList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_TIMES, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = envTitle + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }

    /**
     * <p>[功能描述]：导出同比环比统计报表excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年5月7日上午10:25:16
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaDcExcel")
    public String downEnvStaDcExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String compareTitle = "";
        String outTime = "";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String listThing = request.getParameter("listThing");
        String levelFlag = request.getParameter("levelFlag");
        String originalTime = request.getParameter("originalTime");
        String compareTime = request.getParameter("compareTime");
        String selectType = request.getParameter("selectType");
        String projectId = request.getParameter("projectId");
        String convertType = request.getParameter("convertType");
        List<String> listThingCode = new ArrayList<String>();
        if (listThing != null && !listThing.isEmpty()) {
            listThingCode = Arrays.asList(listThing.split(","));
        }
        if ("month".equals(selectType)) {
            compareTitle = "月同(环)比统计报表";
        } else {
            compareTitle = "日同(环)比统计报表";
        }
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<String> listDevice = new ArrayList<String>();
        listDevice.add(selected);
        List<DataCompareModel> dataCompareList = dataCompareController.getCompareData
                (projectId, convertType, listDevice, levelFlag, listThingCode, originalTime, compareTime, selectType, request.getSession());

        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("compareTitle", compareTitle);
        map.put("outTime", outTime);
        map.put("originalTime", originalTime);
        map.put("compareTime", compareTime);
        map.put("dataCompareList", dataCompareList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_COMPARE, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = compareTitle + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }


    /**
     * <p>[功能描述]：导出超标统计报表excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年8月9日上午10:35:31
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvStaOaExcel")
    public String downEnvStaOaExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String envTitle = "数据超标统计报表";
        String staTime = "";
        String outTime = "";
        String allTitle = "未知类型数据总量";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String levelFlag = request.getParameter("levelFlag");
        String thingCode = request.getParameter("thingCode");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selectTime = request.getParameter("selectTime");
        String dataType = request.getParameter("dataType");
        String updateType = request.getParameter("updateType");
        String projectId = request.getParameter("projectId");
        String levelNo = request.getParameter("levelNo");
        if (StringUtils.isEmpty(levelNo)) {
            levelNo = DefaultArgument.ALARM_LEVEL_NO;
        }
        String convertType = request.getParameter("convertType");
        List<String> list = new ArrayList<String>();
        list.add(selected);
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<AlarmRateModel> alarmRateList = new ArrayList<AlarmRateModel>();
        ResultListModel<AlarmRateModel> resultListModel = new ResultListModel<AlarmRateModel>();
        if (!"times".equals(dataType)) {
            staTime = selectTime;
            resultListModel = envStatisticalReportController.queryAlarmRate
                    (projectId, levelNo, convertType, list, levelFlag, thingCode, selectTime, updateType, dataType, request.getSession());
        } else {
            staTime = beginTime + "至" + endTime;
            resultListModel = envStatisticalReportController.queryAlarmTimesRate
                    (projectId, levelNo, convertType, list, levelFlag, thingCode, updateType, beginTime, endTime, request.getSession());
        }
        if (resultListModel.isResult()) {
            alarmRateList = resultListModel.getRows();
        }
        if (DefaultArgument.PRO_CN_REALBEGIN.equals(updateType)) {
            allTitle = "实时数据总量";
        } else if (DefaultArgument.PRO_GET_DAY.equals(updateType)) {
            allTitle = "每日数据总量";
        } else if (DefaultArgument.PRO_CN_GETMINUTE.equals(updateType)) {
            allTitle = "分钟数据总量";
        } else if (DefaultArgument.PRO_GET_HOUR.equals(updateType)) {
            allTitle = "小时数据总量";
        } else {
            allTitle = "未知类型数据总量";
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("envTitle", envTitle);
        map.put("staTime", staTime);
        map.put("outTime", outTime);
        map.put("allTitle", allTitle);
        map.put("alarmRateList", alarmRateList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_OVER, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = "数据超标统计报表.xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }

    /**
     * <p>[功能描述]：污染排放统计报表导出excel</p>
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author 王垒, 2018年8月13日上午9:13:35
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/downEnvDischargeExcel")
    public String downEnvDischargeExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String envTitle = "污染排放量统计报表";
        String staTime = "";
        String outTime = "";
        request.setCharacterEncoding("utf-8");
        String selected = request.getParameter("selected");
        String levelFlag = request.getParameter("levelFlag");
        String thingCode = request.getParameter("thingCode");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selectTime = request.getParameter("selectTime");
        String selectType = request.getParameter("selectType");
        String projectId = request.getParameter("projectId");
        String convertType = request.getParameter("convertType");
        List<String> list = new ArrayList<String>();
        list.add(selected);
        outTime = DateUtil.TimestampToString(DateUtil.GetSystemDateTime(0), DateUtil.DATA_TIME);
        List<DischargeModel> dcmList = new ArrayList<DischargeModel>();
        ResultListModel<DischargeModel> resultListModel = new ResultListModel<DischargeModel>();
        if ("times".equals(selectType)) {
            staTime = beginTime + "至" + endTime;
            resultListModel = dischargeController.queryDeviceTimesDischarge
                    (projectId, list, levelFlag, thingCode, beginTime, endTime, request.getSession());
        } else {
            staTime = selectTime;
            resultListModel = dischargeController.queryDeviceDischarge
                    (projectId, list, levelFlag, thingCode, selectTime, selectType, request.getSession());
        }
        if (resultListModel.isResult()) {
            dcmList = resultListModel.getRows();
        }
        if (!StringUtils.isEmpty(convertType) && convertType.equals("zs")) {
            for (DischargeModel temp : dcmList) {
                if (!StringUtils.isEmpty(temp.getThingZsCou())) {
                    temp.setThingCou(temp.getThingCou() + "/" + temp.getThingZsCou());
                } else {
                    temp.setThingCou(temp.getThingCou() + "/---");
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //给map填充数据
        map.put("envTitle", envTitle);
        map.put("staTime", staTime);
        map.put("outTime", outTime);
        map.put("dcmList", dcmList);
        //提示：在调用工具类生成Excel文档之前应当检查所有字段是否完整
        //否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错，这里暂时忽略这个步骤
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            //调用工具类OutPutExcel的createExcel方法生成Excel文档
            OutPutExcel outPutExcel = new OutPutExcel();
            file = outPutExcel.createExcel(DefaultArgument.REPORT_ENVSTA_DIS, map);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            String sfileName = "污染排放统计报表.xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(sfileName.getBytes("GB2312"), "iso8859-1"));

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];//缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中  
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }

        } catch (Exception ex) {
            out.print("下载失败！");
            logger.error(LOG + "：下载失败，原因：" + ex.getMessage());
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
        return null;

    }


}
