package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：有效率model</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年10月13日下午2:43:01
 * @since	envdust 1.0.0
 *
 */
public class EffectiveRateModel extends BaseModel {

	private String erId;
	private String deviceCode;
	private String deviceName;
	private String effectiveTime;
	private String effectiveRate;
	private String effectiveInfo;
	private String allCount;
	private String erCount;
	
	/**
	 * @return the erId
	 */
	public String getErId() {
		return erId;
	}
	/**
	 * @param erId the erId to set
	 */
	public void setErId(String erId) {
		this.erId = erId;
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
	 * @return the effectiveTime
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * @param effectiveTime the effectiveTime to set
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	/**
	 * @return the effectiveRate
	 */
	public String getEffectiveRate() {
		return effectiveRate;
	}
	/**
	 * @param effectiveRate the effectiveRate to set
	 */
	public void setEffectiveRate(String effectiveRate) {
		this.effectiveRate = effectiveRate;
	}
	/**
	 * @return the effectiveInfo
	 */
	public String getEffectiveInfo() {
		return effectiveInfo;
	}
	/**
	 * @param effectiveInfo the effectiveInfo to set
	 */
	public void setEffectiveInfo(String effectiveInfo) {
		this.effectiveInfo = effectiveInfo;
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
	 * @return the erCount
	 */
	public String getErCount() {
		return erCount;
	}
	/**
	 * @param erCount the erCount to set
	 */
	public void setErCount(String erCount) {
		this.erCount = erCount;
	}
	
}
