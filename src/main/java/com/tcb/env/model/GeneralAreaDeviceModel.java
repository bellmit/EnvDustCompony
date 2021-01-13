package com.tcb.env.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>[功能描述]：大屏区域设备model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月5日上午9:17:07
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralAreaDeviceModel {
	
	private String deviceCode;
	private String deviceName;
	private String deviceMn;
	private String statusCode;
	private String statusName;
	private List<GeneralAreaDeviceThingModel> gadtmList = new ArrayList<GeneralAreaDeviceThingModel>();
	
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
	 * @return the gadtmList
	 */
	public List<GeneralAreaDeviceThingModel> getGadtmList() {
		return gadtmList;
	}
	/**
	 * @param gadtmList the gadtmList to set
	 */
	public void setGadtmList(List<GeneralAreaDeviceThingModel> gadtmList) {
		this.gadtmList = gadtmList;
	}

}
