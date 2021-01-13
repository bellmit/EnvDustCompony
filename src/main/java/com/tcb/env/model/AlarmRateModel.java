package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：超标报警model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年8月6日上午11:01:40
 * @since	EnvDust 1.0.0
 *
 */
public class AlarmRateModel {
	
	private String areaId;
	private String areaName;
	private String deviceCode;
	private String deviceName;
	private String alarmCount;
	private String allCount;
	private	String alarmRate;
	
	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	 * @return the alarmCount
	 */
	public String getAlarmCount() {
		return alarmCount;
	}
	/**
	 * @param alarmCount the alarmCount to set
	 */
	public void setAlarmCount(String alarmCount) {
		this.alarmCount = alarmCount;
	}
	/**
	 * @return the allCount
	 */
	public String getAllCount() {
		return allCount;
	}
	/**
	 * @param allCount the allCount to set
	 */
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	/**
	 * @return the alarmRate
	 */
	public String getAlarmRate() {
		return alarmRate;
	}
	/**
	 * @param alarmRate the alarmRate to set
	 */
	public void setAlarmRate(String alarmRate) {
		this.alarmRate = alarmRate;
	}

}
