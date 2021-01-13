package com.tcb.env.model;

import java.util.List;

/**
 * 
 * <p>[功能描述]：在线监测报表主model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年5月17日上午8:38:46
 * @since	EnvDust 1.0.0
 *
 */
public class OnlineMonitorReportMainModel extends ResultModel {
	
	private String deviceName;
	private String deviceCode;
	private String deviceMn;
	private String monitorDate;
	private String thingName;
	private List<OnlineMonitorReportTimeModel> omrtModelList;
	
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
	 * @return the monitorDate
	 */
	public String getMonitorDate() {
		return monitorDate;
	}
	/**
	 * @param monitorDate the monitorDate to set
	 */
	public void setMonitorDate(String monitorDate) {
		this.monitorDate = monitorDate;
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
	 * @return the omrtModelList
	 */
	public List<OnlineMonitorReportTimeModel> getOmrtModelList() {
		return omrtModelList;
	}
	/**
	 * @param omrtModelList the omrtModelList to set
	 */
	public void setOmrtModelList(List<OnlineMonitorReportTimeModel> omrtModelList) {
		this.omrtModelList = omrtModelList;
	}

}
