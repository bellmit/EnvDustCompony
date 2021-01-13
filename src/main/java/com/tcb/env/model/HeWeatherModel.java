package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：天气信息明细类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年5月19日下午7:12:51
 * @since	EnvDust 1.0.0
 *
 */
public class HeWeatherModel {

	private HeWeatherBasicModel basic;
	private HeWeatherUpdateModel update;
	private String status;
	private HeWeatherNowModel now;
	
	/**
	 * @return the basic
	 */
	public HeWeatherBasicModel getBasic() {
		return basic;
	}
	/**
	 * @param basic the basic to set
	 */
	public void setBasic(HeWeatherBasicModel basic) {
		this.basic = basic;
	}
	/**
	 * @return the update
	 */
	public HeWeatherUpdateModel getUpdate() {
		return update;
	}
	/**
	 * @param update the update to set
	 */
	public void setUpdate(HeWeatherUpdateModel update) {
		this.update = update;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the now
	 */
	public HeWeatherNowModel getNow() {
		return now;
	}
	/**
	 * @param now the now to set
	 */
	public void setNow(HeWeatherNowModel now) {
		this.now = now;
	}
	
}
