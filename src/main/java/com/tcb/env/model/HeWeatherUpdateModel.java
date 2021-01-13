package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：和天气更新时间信息</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年11月29日上午10:29:34
 * @since	EnvDust 1.0.0
 *
 */
public class HeWeatherUpdateModel {

	private String loc;//当地时间，24小时制，格式hh:mm
	private String utc;//UTC时间，24小时制，格式hh:mm
	
	/**
	 * @return the loc
	 */
	public String getLoc() {
		return loc;
	}
	/**
	 * @param loc the loc to set
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}
	/**
	 * @return the utc
	 */
	public String getUtc() {
		return utc;
	}
	/**
	 * @param utc the utc to set
	 */
	public void setUtc(String utc) {
		this.utc = utc;
	}
	
}
