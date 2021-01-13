package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.dao.IAreaDao;
import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.HeWeather;
import com.tcb.env.service.IAreaService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>
 * [功能描述]：Area操作服务类实现
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月23日上午11:10:14
 * @since EnvDust 1.0.0
 */
@Service("areaService")
@Transactional(rollbackFor = Exception.class)
class AreaServiceImpl implements IAreaService {

    @Resource
    private IAreaDao areaDao;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Override
    public int getCount(Area area, boolean ignorecountry) {
        if (ignorecountry) {
            return areaDao.getCount(area);
        } else {
            return (areaDao.getCount(area) + areaDao.getCountryCount(area));
        }
    }

    @Override
    public List<Area> getAreas(Area area, boolean ignorecountry) {
        return areaDao.getAreas(area, ignorecountry);
    }

    @Override
    public int insertAreas(Area area) throws Exception {
        return areaDao.insertAreas(area);
    }

    @Override
    public int updateAreas(List<Area> listarea) throws Exception {
        return areaDao.updateAreas(listarea);
    }

    @Override
    public int deleteAreas(List<Integer> listid) throws Exception {
        for (Integer temp : listid) {
            if (areaDao.getRelationCount(temp) > 0) {
                return 0;
            }
        }
        return areaDao.deleteAreas(listid);
    }

    @Override
    public String getAreaPath(int areaid, String areaName) {
        if (areaName == null || areaName.isEmpty()) {
            return areaDao.getAreaPath(areaid);
        } else if (areaid == DefaultArgument.INT_DEFAULT) {
            return areaName;
        } else {
            return areaDao.getAreaPath(areaid).isEmpty() ? areaName : (areaDao
                    .getAreaPath(areaid) + "/" + areaName);
        }
    }

    @Override
    public int getAreaExist(int areaid, String areaname) {
        return areaDao.getAreaExist(areaid, areaname);
    }

    @Override
    public int insertAreaWeather(HeWeather weather) {
        try {
            return areaDao.insertAreaWeather(weather);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getAreaWeatherCount(HeWeather weather) {
        return areaDao.getAreaWeatherCount(weather);
    }

    @Override
    public List<HeWeather> getAreaWeather(HeWeather weather) {
        return areaDao.getAreaWeather(weather);
    }

    @Override
    public double getAreaAvgValue(List<Integer> listAreaId, String dataType,
                                  String thingCode, String beginTime, String endTime) {
        double avgValue = 0;
        try {
            //TODO 需要计算，目前采用简单平均算法
            double totalWd = 0;//总计
            int i = 0;//时间个数
            Timestamp beginStampTime = DateUtil.StringToTimestampSecond(beginTime);
            Timestamp endStampTime = DateUtil.StringToTimestampSecond(endTime);
            while (endStampTime.compareTo(beginStampTime) >= 0) {
                i++;
                String dbName = dom4jConfig.getDataBaseConfig().getDbName();
                String dbOldName = "";
                if (!DateUtil.isRecentlyData(beginStampTime, DefaultArgument.RECENT_DAYS)) {
                    dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
                }
                double value = areaDao.getAreaAvgValue(dbName, dbOldName, listAreaId, dataType, thingCode, beginStampTime);
                totalWd += value;
                beginStampTime = DateUtil.getAddTime(beginStampTime, dataType);
            }
            if (i > 0) {
                avgValue = totalWd / i;
            }
        } catch (Exception e) {

        }
        return avgValue;
    }

    @Override
    public String getAreaName(int areaId) {
        return areaDao.getAreaName(areaId);
    }

    @Override
    public String getAreaLevelFlag(int areaid) {
        return areaDao.getAreaLevelFlag(areaid);
    }

    @Override
    public List<String> getAreaCityId() {
        return areaDao.getAreaCityId();
    }

    @Override
    public List<Area> getBottomAuthorityAreas(String userCode, String levelFlag) {
        return areaDao.getBottomAuthorityAreas(userCode, levelFlag);
    }

    @Override
    public Area getTopAuthorityAreas(String userCode) {
        List<Area> authorityAreas = areaDao.getAuthorityAreas(userCode);
        if (authorityAreas != null && authorityAreas.size() != 0) {
            return authorityAreas.get(0);
        }
        return null;
    }

    @Override
    public List<Map<String, String>> queryFourthAreaInfo() {
        return areaDao.queryFourthAreaInfo();
    }

}
