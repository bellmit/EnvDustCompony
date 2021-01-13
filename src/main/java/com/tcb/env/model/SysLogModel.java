package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：系统日志页面传递类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年5月28日下午3:30:14
 * @since	EnvDust 1.0.0
 *
 */
public class SysLogModel extends BaseModel {

	private String logId;
	private String logTitle;
	private String logContent;
	private String logMemo;
	private String logTime;
	
	/**
	 * @return the logId
	 */
	public String getLogId() {
		return logId;
	}
	/**
	 * @param logId the logId to set
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}
	/**
	 * @return the logTitle
	 */
	public String getLogTitle() {
		return logTitle;
	}
	/**
	 * @param logTitle the logTitle to set
	 */
	public void setLogTitle(String logTitle) {
		this.logTitle = logTitle;
	}
	/**
	 * @return the logContent
	 */
	public String getLogContent() {
		return logContent;
	}
	/**
	 * @param logContent the logContent to set
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	/**
	 * @return the logMemo
	 */
	public String getLogMemo() {
		return logMemo;
	}
	/**
	 * @param logMemo the logMemo to set
	 */
	public void setLogMemo(String logMemo) {
		this.logMemo = logMemo;
	}
	/**
	 * @return the logTime
	 */
	public String getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	
}
