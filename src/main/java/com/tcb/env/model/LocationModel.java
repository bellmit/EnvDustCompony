package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：坐标信息</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年8月28日下午2:25:13
 * @since	EnvDust 1.0.0
 *
 */
public class LocationModel {

	private String code;//设备编码
	private double lng;//经度
	private double lat;//纬度
	private double val;//数值
	private double wd;//风向
	private double ws;//风速
	private double rad;//半径
	private String info;//信息
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
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
	 * @return the val
	 */
	public double getVal() {
		return val;
	}
	/**
	 * @param val the val to set
	 */
	public void setVal(double val) {
		this.val = val;
	}
	/**
	 * @return the wd
	 */
	public double getWd() {
		return wd;
	}
	/**
	 * @param wd the wd to set
	 */
	public void setWd(double wd) {
		this.wd = wd;
	}
	/**
	 * @return the ws
	 */
	public double getWs() {
		return ws;
	}
	/**
	 * @param ws the ws to set
	 */
	public void setWs(double ws) {
		this.ws = ws;
	}
	/**
	 * @return the rad
	 */
	public double getRad() {
		return rad;
	}
	/**
	 * @param rad the rad to set
	 */
	public void setRad(double rad) {
		this.rad = rad;
	}
	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
}
