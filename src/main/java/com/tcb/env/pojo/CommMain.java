package com.tcb.env.pojo;

import java.sql.Timestamp;

/**
 * 
 * <p>[功能描述]：计划任务主表pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年5月31日下午2:54:26
 * @since	EnvDust 1.0.0
 *
 */
public class CommMain extends BasePojo {

	private int commId;
	private String qn;
	private String st;
	private CommCn commCn;
	private Device device;
	private String flag;
	private String cp;
	private CommStatus commStatus;
	private Timestamp excuteTime;
	
	/**
	 * @return the commId
	 */
	public int getCommId() {
		return commId;
	}
	/**
	 * @param commId the commId to set
	 */
	public void setCommId(int commId) {
		this.commId = commId;
	}
	/**
	 * @return the qn
	 */
	public String getQn() {
		return qn;
	}
	/**
	 * @param qn the qn to set
	 */
	public void setQn(String qn) {
		this.qn = qn;
	}
	/**
	 * @return the st
	 */
	public String getSt() {
		return st;
	}
	/**
	 * @param st the st to set
	 */
	public void setSt(String st) {
		this.st = st;
	}
	/**
	 * @return the commCn
	 */
	public CommCn getCommCn() {
		return commCn;
	}
	/**
	 * @param commCn the commCn to set
	 */
	public void setCommCn(CommCn commCn) {
		this.commCn = commCn;
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
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}
	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	/**
	 * @return the commStatus
	 */
	public CommStatus getCommStatus() {
		return commStatus;
	}
	/**
	 * @param commStatus the commStatus to set
	 */
	public void setCommStatus(CommStatus commStatus) {
		this.commStatus = commStatus;
	}
	/**
	 * @return the excuteTime
	 */
	public Timestamp getExcuteTime() {
		return excuteTime;
	}
	/**
	 * @param excuteTime the excuteTime to set
	 */
	public void setExcuteTime(Timestamp excuteTime) {
		this.excuteTime = excuteTime;
	}
	
}
