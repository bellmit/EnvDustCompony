package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：超标采样设备箱子记录pojo</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日上午9:25:32
 * @since	EnvDust 1.0.0
 *
 */
public class CollectDeviceBoxVase extends BasePojo {
	
	private long vaseId;
	private String vaseNo;
	private String vaseTime;
	private CollectDeviceBox collectDeviceBox;
	private String vaseQn;
	
	/**
	 * @return the vaseId
	 */
	public long getVaseId() {
		return vaseId;
	}
	/**
	 * @param vaseId the vaseId to set
	 */
	public void setVaseId(long vaseId) {
		this.vaseId = vaseId;
	}
	/**
	 * @return the vaseNo
	 */
	public String getVaseNo() {
		return vaseNo;
	}
	/**
	 * @param vaseNo the vaseNo to set
	 */
	public void setVaseNo(String vaseNo) {
		this.vaseNo = vaseNo;
	}
	/**
	 * @return the vaseTime
	 */
	public String getVaseTime() {
		return vaseTime;
	}
	/**
	 * @param vaseTime the vaseTime to set
	 */
	public void setVaseTime(String vaseTime) {
		this.vaseTime = vaseTime;
	}
	/**
	 * @return the collectDeviceBox
	 */
	public CollectDeviceBox getCollectDeviceBox() {
		return collectDeviceBox;
	}
	/**
	 * @param collectDeviceBox the collectDeviceBox to set
	 */
	public void setCollectDeviceBox(CollectDeviceBox collectDeviceBox) {
		this.collectDeviceBox = collectDeviceBox;
	}
	/**
	 * @return the vaseQn
	 */
	public String getVaseQn() {
		return vaseQn;
	}
	/**
	 * @param vaseQn the vaseQn to set
	 */
	public void setVaseQn(String vaseQn) {
		this.vaseQn = vaseQn;
	}

}
