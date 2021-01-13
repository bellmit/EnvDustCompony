package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：超标采样记录model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日下午4:09:21
 * @since	EnvDust 1.0.0
 *
 */
public class CollectDeviceBoxVaseModel extends BaseModel {
	
	private String vaseId;
	private String vaseNo;
	private String vaseTime;
	private String vaseQn;
	private String boxId;
	private String boxNo;
	private String boxName;
	private String boxStatus;//1-就绪（绿色），2-充满（黄色），3-故障（红色）
	
	/**
	 * @return the vaseId
	 */
	public String getVaseId() {
		return vaseId;
	}
	/**
	 * @param vaseId the vaseId to set
	 */
	public void setVaseId(String vaseId) {
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
	/**
	 * @return the boxId
	 */
	public String getBoxId() {
		return boxId;
	}
	/**
	 * @param boxId the boxId to set
	 */
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	/**
	 * @return the boxNo
	 */
	public String getBoxNo() {
		return boxNo;
	}
	/**
	 * @param boxNo the boxNo to set
	 */
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	/**
	 * @return the boxName
	 */
	public String getBoxName() {
		return boxName;
	}
	/**
	 * @param boxName the boxName to set
	 */
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	/**
	 * @return the boxStatus
	 */
	public String getBoxStatus() {
		return boxStatus;
	}
	/**
	 * @param boxStatus the boxStatus to set
	 */
	public void setBoxStatus(String boxStatus) {
		this.boxStatus = boxStatus;
	}

}
