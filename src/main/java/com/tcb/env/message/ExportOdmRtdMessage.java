package com.tcb.env.message;

/**
 * 
 * <p>[功能描述]：原始数据导出模板(实时)</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年5月5日下午6:12:37
 * @since	EnvDust 1.0.0
 *
 */
public class ExportOdmRtdMessage {
	
	private String deviceCode;
	private String deviceName;
	private String deviceMn;
	private String thingName;
	private String updateTypeName;
	private String thingRtd;
	private String updateTime;
	private String statusName;
	private String rtdTime;
	
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
	 * @return the thingRtd
	 */
	public String getThingRtd() {
		return thingRtd;
	}
	/**
	 * @param thingRtd the thingRtd to set
	 */
	public void setThingRtd(String thingRtd) {
		this.thingRtd = thingRtd;
	}
	/**
	 * @return the updateTypeName
	 */
	public String getUpdateTypeName() {
		return updateTypeName;
	}
	/**
	 * @param updateTypeName the updateTypeName to set
	 */
	public void setUpdateTypeName(String updateTypeName) {
		this.updateTypeName = updateTypeName;
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
	 * @return the rtdTime
	 */
	public String getRtdTime() {
		return rtdTime;
	}
	/**
	 * @param rtdTime the rtdTime to set
	 */
	public void setRtdTime(String rtdTime) {
		this.rtdTime = rtdTime;
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
