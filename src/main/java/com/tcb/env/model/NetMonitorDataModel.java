package com.tcb.env.model;

import java.util.Map;

/**
 * 
 * <p>[功能描述]：网络数据监控页面映射类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月20日下午2:09:19
 * @since	EnvDust 1.0.0
 *
 */
public class NetMonitorDataModel {

	private String deviceCode;
	private String deviceMn;
	private String deviceName;
	private String areaName;
	private String deviceStatus;
	private String deviceStatusInfo;
	private Map<String,String> mapList;
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
	 * @return the deviceStatus
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}
	/**
	 * @param deviceStatus the deviceStatus to set
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	/**
	 * @return the deviceStatusInfo
	 */
	public String getDeviceStatusInfo() {
		return deviceStatusInfo;
	}
	/**
	 * @param deviceStatusInfo the deviceStatusInfo to set
	 */
	public void setDeviceStatusInfo(String deviceStatusInfo) {
		this.deviceStatusInfo = deviceStatusInfo;
	}
	/**
	 * @return the mapList
	 */
	public Map<String, String> getMapList() {
		return mapList;
	}
	/**
	 * @param mapList the mapList to set
	 */
	public void setMapList(Map<String, String> mapList) {
		this.mapList = mapList;
	}
}
