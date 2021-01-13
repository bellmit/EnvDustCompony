package com.tcb.env.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.EnvStatisticalReportModel;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.util.SortListUtil;

/**
 * <p>[功能描述]：站点排名统计报表控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月23日下午3:54:37
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/EnvStatisticalRankingController")
public class EnvStatisticalRankingController {

    /**
     * 日志输出标记
     */
//	private static final String LOG = "EnvStatisticalRankingController";
    /**
     * 声明日志对象
     */
//	private static Logger logger = Logger.getLogger(EnvStatisticalRankingController.class);

    @Resource
    private EnvStatisticalReportController envStatisticalReportController;

    /**
     * <p>[功能描述]：查询站点排名统计报表</p>
     *
     * @param projectId
     * @param zsFlag
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param selectTime
     * @param selectType
     * @param orderType
     * @param dataType
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月23日下午4:00:31
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getEsgData", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EnvStatisticalReportModel> getEsgData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]") List<String> list, String levelFlag, String thingCode,
            String selectTime, String selectType, String orderType, String dataType, HttpSession httpsession) {

        ResultListModel<EnvStatisticalReportModel> resultListModel = envStatisticalReportController.getEsrData
                (projectId, list, levelFlag, thingCode, selectTime, selectType, httpsession);

        if (resultListModel.isResult()) {
            List<EnvStatisticalReportModel> listEsr = resultListModel.getRows();
            if (listEsr != null && listEsr.size() > 0) {
                if ("desc".endsWith(orderType)) {
                    orderType = "desc";
                } else {
                    orderType = "asc";
                }
                String method = getMethod(dataType, zsFlag);
                if (zsFlag && listEsr != null && listEsr.size() > 0) {
                    for (EnvStatisticalReportModel temp : listEsr) {
                        temp.setThingName(temp.getThingName() + "-折算");
                    }
                }
                SortListUtil<EnvStatisticalReportModel> sortList = new SortListUtil<EnvStatisticalReportModel>();
                sortList.sortDouble(listEsr, method, orderType);
                resultListModel.setRows(listEsr);
                resultListModel.setTotal(listEsr.size());
            }
        }

        return resultListModel;

    }

    /**
     * <p>[功能描述]：查询站点排名统计报表-时间段</p>
     *
     * @param projectId
     * @param zsFlag
     * @param list
     * @param levelFlag
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @param orderType
     * @param dataType
     * @param httpsession
     * @return
     * @author 王垒, 2018年8月3日上午9:58:35
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getEsgDataTimes", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<EnvStatisticalReportModel> getEsgDataTimes(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "zsFlag", defaultValue = "false") boolean zsFlag,
            @RequestParam(value = "list[]") List<String> list, String levelFlag, String thingCode,
            String beginTime, String endTime, String orderType, String dataType, HttpSession httpsession) {
        ResultListModel<EnvStatisticalReportModel> resultListModel = envStatisticalReportController.getEsrDataTimes
                (projectId, list, levelFlag, thingCode, beginTime, endTime, httpsession);

        if (resultListModel.isResult()) {
            List<EnvStatisticalReportModel> listEsr = resultListModel.getRows();
            if (listEsr != null && listEsr.size() > 0) {
                if ("desc".endsWith(orderType)) {
                    orderType = "desc";
                } else {
                    orderType = "asc";
                }
                String method = getMethod(dataType, zsFlag);
                SortListUtil<EnvStatisticalReportModel> sortList = new SortListUtil<EnvStatisticalReportModel>();
                sortList.sortDouble(listEsr, method, orderType);
                resultListModel.setRows(listEsr);
                resultListModel.setTotal(listEsr.size());
            }
        }

        return resultListModel;
    }

    private String getMethod(String dataType, Boolean zsFlag) {
        String method;
        switch (dataType) {
            case "min": {
                if (!zsFlag) {
                    method = "convertThingMinDouble";
                } else {
                    method = "convertThingZsMinDouble";
                }
                break;
            }
            case "max": {
                if (!zsFlag) {
                    method = "convertThingMaxDouble";
                } else {
                    method = "convertThingZsMaxDouble";
                }
                break;
            }
            case "avg": {
                if (!zsFlag) {
                    method = "convertThingAvgDouble";
                } else {
                    method = "convertThingZsAvgDouble";
                }
                break;
            }
            default: {
                method = "convertThingAvgDouble";
                break;
            }
        }
        return method;
    }


}
