package com.tcb.env.service;

import java.util.List;
import java.util.Map;

import com.tcb.env.pojo.Area;
import com.tcb.env.pojo.HeWeather;

/**
 * <p>[功能描述]：Area操作服务类接口</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月23日上午11:07:48
 * @since EnvDust 1.0.0
 */
public interface IAreaService {

    /**
     * <p>[功能描述]：查询结果个数</p>
     *
     * @param area
     * @param ignorecountry
     * @return
     * @author 王垒, 2016年3月23日上午10:08:36
     * @since EnvDust 1.0.0
     */
    int getCount(Area area, boolean ignorecountry);

    /**
     * <p>[功能描述]：查询Area数据，包括所有国家</p>
     *
     * @param area
     * @param ignorecountry
     * @return
     * @author 王垒, 2016年3月23日上午10:09:38
     * @since EnvDust 1.0.0
     */
    List<Area> getAreas(Area area, boolean ignorecountry);

    /**
     * <p>[功能描述]：新增Area数据</p>
     *
     * @param area
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月23日上午10:11:05
     * @since EnvDust 1.0.0
     */
    int insertAreas(Area area) throws Exception;

    /**
     * <p>[功能描述]：更新Area数据</p>
     *
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月23日上午10:11:57
     * @since EnvDust 1.0.0
     */
    int updateAreas(List<Area> listarea) throws Exception;

    /**
     * <p>[功能描述]：物理删除User数据</p>
     *
     * @param listid
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月21日下午2:24:56
     * @since EnvDust 1.0.0
     */
    int deleteAreas(List<Integer> listid) throws Exception;

    /**
     * <p>[功能描述]：获取区域路径名称</p>
     *
     * @param areaid:区域id
     * @param areaName:当前区域名称
     * @return
     * @author 王垒, 2016年3月24日上午8:43:55
     * @since EnvDust 1.0.0
     */
    String getAreaPath(int areaid, String areaName);

    /**
     * <p>[功能描述]：获取区域名称</p>
     *
     * @param areaId:区域id
     * @return
     * @author 王垒, 2016年3月24日上午8:43:55
     * @since EnvDust 1.0.0
     */
    String getAreaName(int areaId);

    /**
     * 获取区域级别
     *
     * @param areaid
     * @return
     */
    String getAreaLevelFlag(int areaid);

    /**
     * <p>[功能描述]：查询是否存在同名区域 </p>
     *
     * @param areaid
     * @param areaname
     * @return
     * @author 王垒, 2016年3月24日上午11:14:08
     * @since EnvDust 1.0.0
     */
    int getAreaExist(int areaid, String areaname);

    /**
     * <p>[功能描述]：插入区域天气信息</p>
     *
     * @param weather
     * @return
     * @author 王垒, 2016年5月20日上午10:27:33
     * @since EnvDust 1.0.0
     */
    int insertAreaWeather(HeWeather weather);

    /**
     * <p>[功能描述]：查询区域天气信息个数</p>
     *
     * @param weather
     * @return
     * @author 王垒, 2016年5月20日上午11:39:20
     * @since EnvDust 1.0.0
     */
    int getAreaWeatherCount(HeWeather weather);

    /**
     * <p>[功能描述]：查询区域天气信息</p>
     *
     * @param weather
     * @return
     * @author 王垒, 2016年5月20日上午11:39:20
     * @since EnvDust 1.0.0
     */
    List<HeWeather> getAreaWeather(HeWeather weather);

    /**
     * <p>[功能描述]：获取区域平均数据</p>
     *
     * @param listAreaId
     * @param dataType
     * @param thingCode
     * @param beginTime
     * @param endTime
     * @return
     * @author 王垒, 2017年9月8日下午3:52:11
     * @since EnvDust 1.0.0
     */
    double getAreaAvgValue(
            List<Integer> listAreaId,
            String dataType,
            String thingCode,
            String beginTime,
            String endTime);

    /**
     * <p>[功能描述]：查询地方/城市ID</p>
     *
     * @param
     * @return
     * @author 王垒, 2016年5月20日上午11:39:20
     * @since EnvDust 1.0.0
     */
    List<String> getAreaCityId();

    /**
     * <p>[功能描述]：查询层级权限区域</p>
     *
     * @param userCode
     * @param levelFlag
     * @return
     * @author 王垒, 2016年3月23日上午10:09:38
     * @since EnvDust 1.0.0
     */
    List<Area> getBottomAuthorityAreas(String userCode, String levelFlag);

    Area getTopAuthorityAreas(String userCode);

    /**
     * 获取第四级区域信息
     *
     * @return
     */
    List<Map<String, String>> queryFourthAreaInfo();
}
