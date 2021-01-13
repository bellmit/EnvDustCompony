package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：计划任务model</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年6月1日上午8:43:35
 * @since	EnvDust 1.0.0
 *
 */
public class CommMainModel extends BaseModel {

	private String deviceCode;
	private String deviceMn;
	private String deviceName;
	private String thingName;
	private double maxValue;
	private double minValue;
	private String statusName;
	
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
	 * @return the deviceMn
	 */
	public String getDeviceMn() {
		return deviceMn;
	}
	/**
	 * @param deviceMn the deviceMn to set
	 */
	public void setDeviceMn(String deviceMn) {
		this.deviceMn = deviceMn;
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
	 * @return the thingName
	 */
	public String getThingName() {
		return thingName;
	}
	/**
	 * @param thingName the thingName to set
	 */
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	/**
	 * @return the maxValue
	 */
	public double getMaxValue() {
		return maxValue;
	}
	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	/**
	 * @return the minValue
	 */
	public double getMinValue() {
		return minValue;
	}
	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
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
	
	
}
