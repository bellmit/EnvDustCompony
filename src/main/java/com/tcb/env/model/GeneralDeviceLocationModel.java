package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：大屏地图站点model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月4日上午9:14:29
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralDeviceLocationModel {
	
	private String deviceCode;
	private String deviceName;
	private String statusCode;
	private String statusName;
	private String updateTime;//最新更新时间
	private double deviceX ;//设备坐标X
	private double deviceY ;//设备坐标Y
	
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
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the deviceX
	 */
	public double getDeviceX() {
		return deviceX;
	}
	/**
	 * @param deviceX the deviceX to set
	 */
	public void setDeviceX(double deviceX) {
		this.deviceX = deviceX;
	}
	/**
	 * @return the deviceY
	 */
	public double getDeviceY() {
		return deviceY;
	}
	/**
	 * @param deviceY the deviceY to set
	 */
	public void setDeviceY(double deviceY) {
		this.deviceY = deviceY;
	}

}
