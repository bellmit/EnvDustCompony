package com.tcb.env.pojo;


/**
 * 
 * <p>[功能描述]：有效率pojo</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月28日上午11:09:01
 * @since	EnvDust 1.0.0
 *
 */
public class EffectiveRate extends BasePojo {

	private long erId;
	private Device device;
	private String effectiveTime;
	private double effectiveRate;
	private String effectiveInfo;
	private int allCount;
	private int erCount;
	
	/**
	 * @return the erId
	 */
	public long getErId() {
		return erId;
	}
	/**
	 * @param erId the erId to set
	 */
	public void setErId(long erId) {
		this.erId = erId;
	}
	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
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
	public double getEffectiveRate() {
		return effectiveRate;
	}
	/**
	 * @param effectiveRate the effectiveRate to set
	 */
	public void setEffectiveRate(double effectiveRate) {
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
	public int getAllCount() {
		return allCount;
	}
	/**
	 * @param allCount the allCount to set
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	/**
	 * @return the erCount
	 */
	public int getErCount() {
		return erCount;
	}
	/**
	 * @param erCount the erCount to set
	 */
	public void setErCount(int erCount) {
		this.erCount = erCount;
	}
	
}
