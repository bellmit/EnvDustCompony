package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：超标采样设备箱子model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日下午4:06:22
 * @since	EnvDust 1.0.0
 *
 */
public class CollectDeviceBoxModel extends BaseModel {
	
	private String boxId;
	private String boxNo;
	private String boxName;
	private String boxStatus;//0-就绪（绿色），1-充满（黄色），2-故障（红色）
	private String boxStatusName;//0-就绪（绿色），1-充满（黄色），2-故障（红色）
	private String cdId;
	private String cdCode;
	private String cdMn;
	private String cdName;
	private String netStatus;
	
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
	/**
	 * @return the boxStatusName
	 */
	public String getBoxStatusName() {
		return boxStatusName;
	}
	/**
	 * @param boxStatusName the boxStatusName to set
	 */
	public void setBoxStatusName(String boxStatusName) {
		this.boxStatusName = boxStatusName;
	}
	/**
	 * @return the cdId
	 */
	public String getCdId() {
		return cdId;
	}
	/**
	 * @param cdId the cdId to set
	 */
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	/**
	 * @return the cdCode
	 */
	public String getCdCode() {
		return cdCode;
	}
	/**
	 * @param cdCode the cdCode to set
	 */
	public void setCdCode(String cdCode) {
		this.cdCode = cdCode;
	}
	/**
	 * @return the cdMn
	 */
	public String getCdMn() {
		return cdMn;
	}
	/**
	 * @param cdMn the cdMn to set
	 */
	public void setCdMn(String cdMn) {
		this.cdMn = cdMn;
	}
	/**
	 * @return the cdName
	 */
	public String getCdName() {
		return cdName;
	}
	/**
	 * @param cdName the cdName to set
	 */
	public void setCdName(String cdName) {
		this.cdName = cdName;
	}
	/**
	 * @return the netStatus
	 */
	public String getNetStatus() {
		return netStatus;
	}
	/**
	 * @param netStatus the netStatus to set
	 */
	public void setNetStatus(String netStatus) {
		this.netStatus = netStatus;
	}

}
