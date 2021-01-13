package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcb.env.model.DataCompareModel;
import com.tcb.env.model.MapModel;
import com.tcb.env.model.TreeModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.service.IDataCompareService;
import com.tcb.env.service.ITreeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SessionManager;
import com.tcb.env.util.SortListUtil;

/**
 * <p>[功能描述]：数据对比控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年4月25日下午2:19:05
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/DataCompareController")
public class DataCompareController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DataCompareController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DataCompareController.class);

    /**
     * 声明数据对比服务类
     */
    @Resource
    private IDataCompareService dataCompareService;
    /**
     * 声明树服务接口
     */
    @Resource
    private ITreeService treeService;

    /**
     * <p>[功能描述]：查询同比环比统计报表</p>
     *
     * @param projectId
     * @param convertType
     * @param listDevice
     * @param levelFlag
     * @param listThing
     * @param originalTime
     * @param compareTime
     * @param selectType
     * @param httpsession
     * @return
     * @author 王垒, 2018年5月24日下午3:00:17
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getCompareData", method = {RequestMethod.POST})
    public @ResponseBody
    List<DataCompareModel> getCompareData(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "convertType", required = false) String convertType,
            @RequestParam(value = "listDevice[]") List<String> listDevice, String levelFlag,
            @RequestParam(value = "listThing[]") List<String> listThing, String originalTime,
            String compareTime, String selectType, HttpSession httpsession) {
        List<DataCompareModel> listDcm = new ArrayList<DataCompareModel>();
        String userCode = null;
        try {
            if (SessionManager.isSessionValidate(httpsession, DefaultArgument.LOGIN_USER)) {
                return listDcm;
            } else {
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                if (loginuser != null) {
                    userCode = loginuser.getUserCode();
                }
            }
            List<String> listDeviceCode = new ArrayList<String>();
            if (DefaultArgument.NONE_DEFAULT.equals(listDevice.get(0))) {
                return listDcm;
            } else if (listDevice == null || listDevice.size() == 0) {
                return listDcm;
            } else {
                List<Integer> listAreaId = new ArrayList<Integer>();
                if (levelFlag != null && !levelFlag.isEmpty()) {
                    listAreaId = treeService.getAuthorityBottomArea(projectId, listAreaId, Integer.valueOf(listDevice.get(0)), userCode);
                }
                if (String.valueOf(DefaultArgument.INT_DEFAULT).equals(listDevice.get(0))) {
                    List<MapModel> listDev = getAuthorityDevices(projectId, DefaultArgument.INT_DEFAULT, null, httpsession);
                    for (MapModel mapModel : listDev) {
                        listDeviceCode.add(mapModel.getCode());
                    }
                    ;
                } else if (listAreaId != null && listAreaId.size() > 0) {
                    for (Integer areaid : listAreaId) {
                        List<MapModel> listDev = getAuthorityDevices(projectId, areaid, null, httpsession);
                        for (MapModel mapModel : listDev) {
                            listDeviceCode.add(mapModel.getCode());
                        }
                    }
                } else {
                    for (String temp : listDevice) {
                        listDeviceCode.add(temp);
                    }
                }

            }
            if (listDeviceCode != null && listDeviceCode.size() > 0 && listThing != null && listThing.size() > 0) {
                if ("day".equals(selectType)) {
                    listDcm = dataCompareService.getDayDataCompare(listDeviceCode, listThing, DateUtil.StringToTimestamp(originalTime),
                            DateUtil.StringToTimestamp(compareTime), convertType);
                } else if ("month".equals(selectType)) {
                    listDcm = dataCompareService.getMonthDataCompare(listDeviceCode, listThing, DateUtil.StringToTimestamp(originalTime + "-01"),
                            DateUtil.StringToTimestamp(compareTime + "-01"), convertType);
                }
                SortListUtil<DataCompareModel> sortList = new SortListUtil<DataCompareModel>();
                sortList.sortDouble(listDcm, "convertAddedRatioDouble", "desc");
            }
        } catch (Exception e) {
            logger.error(LOG + ":查询同比环比统计报表错误，错误信息为：" + e.getMessage());
        }
        return listDcm;
    }

    /**
     * <p>
     * [功能描述]：获取权限设备数据
     * </p>
     *
     * @param projectId
     * @param areaid
     * @param statusCode
     * @param httpsession
     * @return
     * @author 王垒, 2016年3月31日下午9:58:18
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/getAuthorityDevices", method = {RequestMethod.POST})
    public @ResponseBody
    List<MapModel> getAuthorityDevices(
            @RequestParam(value = "projectId", required = false) String projectId,
            int areaid, String statusCode, HttpSession httpsession) {
        ArrayList<MapModel> listMapModel = new ArrayList<MapModel>();
        // 获取用户code
        String usercode = null;
        if (SessionManager.isSessionValidate(httpsession,
                DefaultArgument.LOGIN_USER)) {
            return listMapModel;
        }
        UserModel loginuser = (UserModel) httpsession
                .getAttribute(DefaultArgument.LOGIN_USER);
        if (loginuser != null) {
            usercode = loginuser.getUserCode();
        }
        if (usercode != null && !usercode.isEmpty()) {
            ArrayList<TreeModel> al = treeService.getAuthorityDevices(usercode, projectId, areaid, null, statusCode, null);
            for (TreeModel treeModel : al) {
                MapModel mapModel = new MapModel();
                mapModel.setCode(treeModel.getId());
                mapModel.setName(treeModel.getText());
                listMapModel.add(mapModel);
            }
        }

        return listMapModel;
    }

}
