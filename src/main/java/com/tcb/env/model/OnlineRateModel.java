package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：在线率model</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月30日上午10:18:46
 * @since	EnvDust 1.0.0
 *
 */
public class OnlineRateModel extends BaseModel {

	private String orId;
	private String deviceCode;
	private String deviceName;
	private String onlineTime;
	private String onlineRate;
	private String onlineInfo;
	private String allCount;
	private String orCount;
	
	/**
	 * @return the orId
	 */
	public String getOrId() {
		return orId;
	}
	/**
	 * @param orId the orId to set
	 */
	public void setOrId(String orId) {
		this.orId = orId;
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
	 * @return the onlineTime
	 */
	public String getOnlineTime() {
		return onlineTime;
	}
	/**
	 * @param onlineTime the onlineTime to set
	 */
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	/**
	 * @return the onlineRate
	 */
	public String getOnlineRate() {
		return onlineRate;
	}
	/**
	 * @param onlineRate the onlineRate to set
	 */
	public void setOnlineRate(String onlineRate) {
		this.onlineRate = onlineRate;
	}
	/**
	 * @return the onlineInfo
	 */
	public String getOnlineInfo() {
		return onlineInfo;
	}
	/**
	 * @param onlineInfo the onlineInfo to set
	 */
	public void setOnlineInfo(String onlineInfo) {
		this.onlineInfo = onlineInfo;
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
	 * @return the orCount
	 */
	public String getOrCount() {
		return orCount;
	}
	/**
	 * @param orCount the orCount to set
	 */
	public void setOrCount(String orCount) {
		this.orCount = orCount;
	}
	
}
