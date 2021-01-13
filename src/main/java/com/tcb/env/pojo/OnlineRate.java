package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：在线率pojo</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月28日上午11:05:04
 * @since	EnvDust 1.0.0
 *
 */
public class OnlineRate extends BasePojo {

	private long orId;
	private Device device;
	private String onlineTime;
	private double onlineRate;
	private String onlineInfo;
	private int allCount;
	private int orCount;
	
	/**
	 * @return the orId
	 */
	public long getOrId() {
		return orId;
	}
	/**
	 * @param orId the orId to set
	 */
	public void setOrId(long orId) {
		this.orId = orId;
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
	public double getOnlineRate() {
		return onlineRate;
	}
	/**
	 * @param onlineRate the onlineRate to set
	 */
	public void setOnlineRate(double onlineRate) {
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
	 * @return the orCount
	 */
	public int getOrCount() {
		return orCount;
	}
	/**
	 * @param orCount the orCount to set
	 */
	public void setOrCount(int orCount) {
		this.orCount = orCount;
	}
	
}
