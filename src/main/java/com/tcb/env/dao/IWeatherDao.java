package com.tcb.env.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.HeWeather;

/**
 * <p>[功能描述]：天气数据库操作接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年12月6日下午2:19:05
 * @since EnvDust 1.0.0
 */
public interface IWeatherDao {

    /**
     * <p>[功能描述]：查询区域天气信息个数</p>
     *
     * @param weather
     * @return
     * @author 王垒, 2016年5月20日上午11:39:20
     * @since EnvDust 1.0.0
     */
    int getWeatherCount(@Param("weather") HeWeather weather);

    /**
     * <p>[功能描述]：查询区域天气信息</p>
     *
     * @param weather
     * @return
     * @author 王垒, 2016年5月20日上午11:39:20
     * @since EnvDust 1.0.0
     */
    List<HeWeather> getWeather(@Param("weather") HeWeather weather);


    /**
     * 获取天气最低云量和最大云量数据
     *
     * @param areaId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getCloudDataByDay(@Param("areaId") int areaId,
                                                @Param("beginTime") String beginTime,
                                                @Param("endTime") String endTime);

    /**
     * 获取某时间天气 风速和风向
     *
     * @param areaId
     * @param datetime
     * @return
     */
    List<Map<String, Object>> getWindDataByDay(@Param("areaId") int areaId, @Param("datatime") String datetime);

    /**
     * 获取风向
     *
     * @param areaId
     * @param dateTime
     * @param windSc
     * @param days
     * @return
     */
    List<Integer> getRoseWindData(@Param("areaId") int areaId, @Param("dateTime") String dateTime, @Param("windSc") int windSc, @Param("days") int days);

}
