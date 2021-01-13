package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：设备采样指令model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月27日上午11:07:29
 * @since	EnvDust 1.0.0
 *
 */
public class NetSamplePlanModel extends BaseModel {
	
	private String commId;
	private String vaseQn;
	private String cdCode;
	private String cdMn;
	private String cdName;
	private String cnCode;
	private String cnName;
	private String statusName;
	private String excuteTime;
	private String optUserName;
	private String optTime;

	
	public String getVaseQn() {
		return vaseQn;
	}
	public void setVaseQn(String vaseQn) {
		this.vaseQn = vaseQn;
	}
	/**
	 * @return the commId
	 */
	public String getCommId() {
		return commId;
	}
	/**
	 * @param commId the commId to set
	 */
	public void setCommId(String commId) {
		this.commId = commId;
	}
	/**
	 * @return the cdCode
	 */
	public String getCdCode() {
		return cdCode;
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
	 * @param cdCode the cdCode to set
	 */
	public void setCdCode(String cdCode) {
		this.cdCode = cdCode;
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
	 * @return the cnCode
	 */
	public String getCnCode() {
		return cnCode;
	}
	/**
	 * @param cnCode the cnCode to set
	 */
	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	/**
	 * @return the cnName
	 */
	public String getCnName() {
		return cnName;
	}
	/**
	 * @param cnName the cnName to set
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
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
	 * @return the excuteTime
	 */
	public String getExcuteTime() {
		return excuteTime;
	}
	/**
	 * @param excuteTime the excuteTime to set
	 */
	public void setExcuteTime(String excuteTime) {
		this.excuteTime = excuteTime;
	}
	/**
	 * @return the optUserName
	 */
	public String getOptUserName() {
		return optUserName;
	}
	/**
	 * @param optUserName the optUserName to set
	 */
	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}
	/**
	 * @return the optTime
	 */
	public String getOptTime() {
		return optTime;
	}
	/**
	 * @param optTime the optTime to set
	 */
	public void setOptTime(String optTime) {
		this.optTime = optTime;
	}

}
