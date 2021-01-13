package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：排放量model</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年10月13日下午2:42:49
 * @since	envdust 1.0.0
 *
 */
public class DischargeModel extends BaseModel {

	private String areaId;
	private String areaName;
	private String deviceCode;
	private String deviceName;
	private String thingCode;
	private String thingName;
	private String beginTime;
	private String endTime;
	private String thingCou;
	private String thingZsCou;

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
	 * @return the thingCode
	 */
	public String getThingCode() {
		return thingCode;
	}
	/**
	 * @param thingCode the thingCode to set
	 */
	public void setThingCode(String thingCode) {
		this.thingCode = thingCode;
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
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}
	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the thingCou
	 */
	public String getThingCou() {
		return thingCou;
	}
	public double convertThingCouDouble() {
		try{
			return Double.valueOf(thingCou.replace(",", ""));
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * @param thingCou the thingCou to set
	 */
	public void setThingCou(String thingCou) {
		this.thingCou = thingCou;
	}
	/**
	 * @return the thingZsCou
	 */
	public String getThingZsCou() {
		return thingZsCou;
	}
	public double convertThingZsCouDouble() {
		try{
			return Double.valueOf(thingZsCou.replace(",", ""));
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * @param thingZsCou the thingZsCou to set
	 */
	public void setThingZsCou(String thingZsCou) {
		this.thingZsCou = thingZsCou;
	}

}
