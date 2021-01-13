package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：热力图-百度</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年8月10日下午1:54:27
 * @since	envdust 1.0.0
 *
 */
public class ThermodynamicModel {

	private double lng;
	private double lat;
	private double count;//热力图count
	private String ws;//风速
	private String wd;//风向
	private String deviceCode;
	private String deviceName;
	private String ratio;

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the count
	 */
	public double getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(double count) {
		this.count = count;
	}
	/**
	 * @return the ws
	 */
	public String getWs() {
		return ws;
	}
	/**
	 * @param ws the ws to set
	 */
	public void setWs(String ws) {
		this.ws = ws;
	}
	/**
	 * @return the wd
	 */
	public String getWd() {
		return wd;
	}
	/**
	 * @param wd the wd to set
	 */
	public void setWd(String wd) {
		this.wd = wd;
	}
	/**
	 * @return the deviceCode
	 */
	public String getDeviceCode() {
		return deviceCode;
	}
	/**
	 * @param deviceCode the deviceCode to set
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * @return the ratio
	 */
	public String getRatio() {
		return ratio;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	
}
