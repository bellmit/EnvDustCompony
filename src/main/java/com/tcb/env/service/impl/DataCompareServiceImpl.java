package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mysql.cj.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.dao.IDataCompareDao;
import com.tcb.env.model.DataCompareModel;
import com.tcb.env.service.IDataCompareService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：数据对比服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年4月25日上午11:53:45
 * @since EnvDust 1.0.0
 */
@Service("dataCompareService")
class DataCompareServiceImpl implements IDataCompareService {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DataCompareServiceImpl";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DataCompareServiceImpl.class);

    @Resource
    private IDataCompareDao dataCompareDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Override
    public List<DataCompareModel> getDayDataCompare(List<String> deviceCodeList, List<String> thingCodeList,
                                                    Timestamp selectTime, Timestamp compareTime, String convertType) {
        List<DataCompareModel> resultList = new ArrayList<DataCompareModel>();
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = "";
        if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
            dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        }
        for (String deviceCode : deviceCodeList) {
            List<DataCompareModel> dcList = dataCompareDao.getDayDataCompare(dbName, dbOldName, deviceCode, thingCodeList, selectTime, convertType);
            if (dcList != null && dcList.size() > 0) {
                for (DataCompareModel dataCompareModel : dcList) {
                    String thingCode = dataCompareModel.getThingCode();
                    String originalValue = dataCompareModel.getOriginalValue();
                    String compareValue = dataCompareDao.getDayDataValue(dbName, dbOldName, deviceCode, thingCode, compareTime, convertType);
                    dataCompareModel.setCompareTime(DateUtil.TimestampToString(compareTime, DateUtil.DEFUALT_TIME));
                    dataCompareModel.setCompareValue(compareValue);
                    String addedRatio = "";
                    if (originalValue != null && !originalValue.isEmpty() && compareValue != null && !compareValue.isEmpty()) {
                        try {
                            addedRatio = String.format("%.2f", ((Double.valueOf(compareValue) - Double.valueOf(originalValue)) / Double.valueOf(compareValue)) * 100);
                        } catch (Exception e) {
                            logger.error(LOG + "：转换增长率百分比失败，原因为：" + e.getMessage());
                        }
                    }
                    dataCompareModel.setCompareTime(DateUtil.TimestampToString(compareTime, DateUtil.YEAR_MONTH));
                    dataCompareModel.setCompareValue(compareValue);
                    dataCompareModel.setAddedRatio(addedRatio);
                    if(!StringUtils.isNullOrEmpty(convertType) && convertType.equals("zs")){
                        dataCompareModel.setThingName(dataCompareModel.getThingName()+"-折算");
                    }
                }
                resultList.addAll(dcList);
            }
        }
        return resultList;
    }

    @Override
    public List<DataCompareModel> getMonthDataCompare(List<String> deviceCodeList, List<String> thingCodeList,
                                                      Timestamp selectTime, Timestamp compareTime, String convertType) {
        List<DataCompareModel> resultList = new ArrayList<DataCompareModel>();
        String dbName = dom4jConfig.getDataBaseConfig().getDbName();
        String dbOldName = "";
        if (!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)) {
            dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
        }
        for (String deviceCode : deviceCodeList) {
            List<DataCompareModel> dcList = dataCompareDao.getMonthDataCompare(dbName, dbOldName, deviceCode, thingCodeList, selectTime, convertType);
            if (dcList != null && dcList.size() > 0) {
                for (DataCompareModel dataCompareModel : dcList) {
                    String thingCode = dataCompareModel.getThingCode();
                    String originalValue = dataCompareModel.getOriginalValue();
                    String compareValue = dataCompareDao.getMonthDataValue(dbName, dbOldName, deviceCode, thingCode, compareTime, convertType);
                    String addedRatio = "";
                    if (originalValue != null && !originalValue.isEmpty() && compareValue != null && !compareValue.isEmpty()) {
                        try {
                            addedRatio = String.format("%.2f", ((Double.valueOf(compareValue) - Double.valueOf(originalValue)) / Double.valueOf(originalValue)) * 100);
                        } catch (Exception e) {
                            logger.error(LOG + "：转换增长率百分比失败，原因为：" + e.getMessage());
                        }
                    }
                    dataCompareModel.setCompareTime(DateUtil.TimestampToString(compareTime, DateUtil.YEAR_MONTH));
                    dataCompareModel.setCompareValue(compareValue);
                    dataCompareModel.setAddedRatio(addedRatio);
                }
                resultList.addAll(dcList);
            }
        }
        return resultList;
    }

}
