package com.tcb.env.model;

import java.util.List;

/**
 * 
 * <p>[功能描述]：天气信息主类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年5月19日下午7:12:30
 * @since	EnvDust 1.0.0
 *
 */
public class WeatherModel {
	
	//和天气
	private List<HeWeatherModel> HeWeather6;

	/**
	 * @return the heWeather6
	 */
	public List<HeWeatherModel> getHeWeather6() {
		return HeWeather6;
	}

	/**
	 * @param heWeather6 the heWeather6 to set
	 */
	public void setHeWeather6(List<HeWeatherModel> heWeather6) {
		HeWeather6 = heWeather6;
	}	

}
