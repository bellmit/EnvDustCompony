package com.tcb.env.service;

import java.util.List;
import com.tcb.env.pojo.HeWeather;

/**
 * 
 * <p>[功能描述]：天气服务接口</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年12月7日下午3:05:45
 * @since	EnvDust 1.0.0
 *
 */
public interface IWeatherService {
	
	/**
	 * 
	 * <p>[功能描述]：查询区域天气信息个数</p>
	 * 
	 * @author	王垒, 2016年5月20日上午11:39:20
	 * @since	EnvDust 1.0.0
	 *
	 * @param weather
	 * @return
	 */
	int getWeatherCount(HeWeather weather);
	
	/**
	 * 
	 * <p>[功能描述]：查询区域天气信息</p>
	 * 
	 * @author	王垒, 2016年5月20日上午11:39:20
	 * @since	EnvDust 1.0.0
	 *
	 * @param weather
	 * @return
	 */
	List<HeWeather> getWeather(HeWeather weather);
	
}
