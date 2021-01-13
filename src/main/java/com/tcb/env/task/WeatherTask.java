package com.tcb.env.task;

//import java.net.URLEncoder;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tcb.env.model.HeWeatherModel;
import com.tcb.env.model.WeatherModel;
import com.tcb.env.pojo.HeWeather;
import com.tcb.env.service.IAreaService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.GetWeatherUtil;

/**
 * 
 * <p>
 * [功能描述]：获取天气计划
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月19日下午5:58:34
 * @since EnvDust 1.0.0
 *
 */
@Component
public class WeatherTask {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "WeatherTask";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(WeatherTask.class);

	/**
	 * 声明Area服务
	 */
	@Resource
	private IAreaService areaService;

//	 @Scheduled(cron = "0 45 12 * * ?")
	@Scheduled(cron = "0 45 * * * ?")
	public void taskCycle() {
		try {
			List<String> listCityId = areaService.getAreaCityId();
			if (listCityId != null && listCityId.size() > 0) {
				for (String cityId : listCityId) {
					try {
						if (cityId != null && !cityId.isEmpty()) {
							cityId = "location=" + cityId;
							String wResult = GetWeatherUtil.request(cityId);
							Gson gson = new GsonBuilder().create();
							WeatherModel res = (WeatherModel) gson.fromJson(
									wResult, new TypeToken<WeatherModel>() {}.getType());
							if (res.getHeWeather6() != null && res.getHeWeather6().size()>0) {
								for (HeWeatherModel heWeatherModel : res.getHeWeather6()) {
									if(DefaultArgument.HE_OK.equals(heWeatherModel.getStatus())){
										// 插入数据
										HeWeather weather = ConvertToHeWeather(heWeatherModel);
										if (weather != null) {
											int count = areaService.getAreaWeatherCount(weather);
											if (count == 0) {
												areaService.insertAreaWeather(weather);
											}
										} else {
											logger.error(LOG + "：插入天气失败，cityid为：" + cityId);
										}
									}
								}
							} else {
								logger.error(LOG + "：请求天气失败，cityid为：" + cityId);
							}
						} else {
							continue;
						}
					} catch (Exception e) {
						logger.error(LOG + "：请求天气失败，cityid为：" + cityId + "：异常信息为：" + e.getMessage());
						continue;
					} finally {
						Thread.sleep(DefaultArgument.WEATHER_TIME);
					}
				}
			}
		} catch (Exception e) {
			logger.error(LOG + "：请求天气失败，异常信息为：" + e.getMessage());
		}
	}

	/**
	 * 
	 * <p>
	 * [功能描述]：将WeatherDetailModel转换成Weather
	 * </p>
	 * 
	 * @author 王垒, 2016年5月20日上午10:10:10
	 * @since EnvDust 1.0.0
	 *
	 * @param heWeatherModel
	 * @return
	 */
	private HeWeather ConvertToHeWeather(HeWeatherModel heWeatherModel) {
		HeWeather weather = new HeWeather();
		if (heWeatherModel != null) {
			//基础信息
			if(heWeatherModel.getBasic() != null){
				weather.setCid(heWeatherModel.getBasic().getCid());
				weather.setLocation(heWeatherModel.getBasic().getLocation());
				weather.setParentCity(heWeatherModel.getBasic().getParent_city());
				weather.setAdminArea(heWeatherModel.getBasic().getAdmin_area());
				weather.setCnty(heWeatherModel.getBasic().getCnty());
				weather.setLat(heWeatherModel.getBasic().getLat());
				weather.setLon(heWeatherModel.getBasic().getLon());
				weather.setTz(heWeatherModel.getBasic().getTz());
			}
			//接口状态
			weather.setStatus(heWeatherModel.getStatus());
			//接口更新时间
			if(heWeatherModel.getUpdate() != null){
				weather.setLoc(heWeatherModel.getUpdate().getLoc());
				weather.setUtc(heWeatherModel.getUpdate().getUtc());
			}
			//实况天气
			if(heWeatherModel.getNow() != null){
				weather.setCloud(heWeatherModel.getNow().getCloud());
				weather.setCondCode(heWeatherModel.getNow().getCond_code());
				weather.setCondTxt(heWeatherModel.getNow().getCond_txt());
				weather.setFl(heWeatherModel.getNow().getFl());
				weather.setHum(heWeatherModel.getNow().getHum());
				weather.setPcpn(heWeatherModel.getNow().getPcpn());
				weather.setPres(heWeatherModel.getNow().getPres());
				weather.setTmp(heWeatherModel.getNow().getTmp());
				weather.setVis(heWeatherModel.getNow().getVis());
				weather.setWindDeg(heWeatherModel.getNow().getWind_deg());
				weather.setWindDir(heWeatherModel.getNow().getWind_dir());
				weather.setWindSc(heWeatherModel.getNow().getWind_sc());
				weather.setWindSpd(heWeatherModel.getNow().getWind_spd());
			}
		}

		return weather;
	}

}
