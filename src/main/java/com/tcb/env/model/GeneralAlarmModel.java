package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：大屏报警model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月4日上午9:09:55
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralAlarmModel {

	private String alarmType;
	private String alarmTypeName;
	private String deviceCode;
	private String deviceName;
	private String alarmInfo;
	private String alarmTime;
	
	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		return alarmType;
	}
	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	/**
	 * @return the alarmTypeName
	 */
	public String getAlarmTypeName() {
		return alarmTypeName;
	}
	/**
	 * @param alarmTypeName the alarmTypeName to set
	 */
	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
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
	 * @return the alarmInfo
	 */
	public String getAlarmInfo() {
		return alarmInfo;
	}
	/**
	 * @param alarmInfo the alarmInfo to set
	 */
	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}
	/**
	 * @return the alarmTime
	 */
	public String getAlarmTime() {
		return alarmTime;
	}
	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
}
