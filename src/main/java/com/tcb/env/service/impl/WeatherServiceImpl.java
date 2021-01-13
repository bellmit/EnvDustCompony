package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IWeatherDao;
import com.tcb.env.pojo.HeWeather;
import com.tcb.env.service.IWeatherService;

/**
 * 
 * <p>[功能描述]：天气数据库操作接口实现类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年12月7日下午3:07:43
 * @since	EnvDust 1.0.0
 *
 */
@Service("weatherService")
class WeatherServiceImpl implements IWeatherService {

	@Resource
	private IWeatherDao weatherDao;
	
	@Override
	public int getWeatherCount(HeWeather weather) {
		return weatherDao.getWeatherCount(weather);
	}

	@Override
	public List<HeWeather> getWeather(HeWeather weather) {
		return weatherDao.getWeather(weather);
	}

}
