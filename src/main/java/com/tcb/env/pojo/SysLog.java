package com.tcb.env.pojo;

import java.sql.Timestamp;

/**
 * 
 * <p>[功能描述]：系统日志</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年5月28日下午3:30:14
 * @since	EnvDust 1.0.0
 *
 */
public class SysLog extends BasePojo {

	private long logId;
	private String logTitle;
	private String logContent;
	private String logMemo;
	private Timestamp logTime;
	
	/**
	 * @return the logId
	 */
	public long getLogId() {
		return logId;
	}
	/**
	 * @param logId the logId to set
	 */
	public void setLogId(long logId) {
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
	public Timestamp getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}
	
}
