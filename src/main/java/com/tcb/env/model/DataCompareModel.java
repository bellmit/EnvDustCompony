package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：数据对比model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年4月24日上午10:48:53
 * @since	EnvDust 1.0.0
 *
 */
public class DataCompareModel extends BaseModel {
	
	private String areaId;
	private String areaName;
	private String deviceCode;
	private String deviceName;
	private String thingCode;
	private String thingName;
	private String updateType;
	private String updateTypeName;
	private String originalTime;
	private String originalValue;
	private String compareTime;
	private String compareValue;
	private String addedRatio;
	
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
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	 * @return the updateType
	 */
	public String getUpdateType() {
		return updateType;
	}
	/**
	 * @param updateType the updateType to set
	 */
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
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
	 * @return the originalTime
	 */
	public String getOriginalTime() {
		return originalTime;
	}
	/**
	 * @param originalTime the originalTime to set
	 */
	public void setOriginalTime(String originalTime) {
		this.originalTime = originalTime;
	}
	/**
	 * @return the originalValue
	 */
	public String getOriginalValue() {
		return originalValue;
	}
	/**
	 * @param originalValue the originalValue to set
	 */
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	/**
	 * @return the compareTime
	 */
	public String getCompareTime() {
		return compareTime;
	}
	/**
	 * @param compareTime the compareTime to set
	 */
	public void setCompareTime(String compareTime) {
		this.compareTime = compareTime;
	}
	/**
	 * @return the compareValue
	 */
	public String getCompareValue() {
		return compareValue;
	}
	/**
	 * @param compareValue the compareValue to set
	 */
	public void setCompareValue(String compareValue) {
		this.compareValue = compareValue;
	}
	/**
	 * @return the addedRatio
	 */
	public String getAddedRatio() {
		return addedRatio;
	}
	/**
	 * @param addedRatio the addedRatio to set
	 */
	public void setAddedRatio(String addedRatio) {
		this.addedRatio = addedRatio;
	}
	/**
	 * @return the addedRatioDouble
	 */
	public double convertAddedRatioDouble() {
		try{
			return Double.valueOf(this.addedRatio);
		}catch(Exception e){
			return 0;
		}
	}

}
