package com.tcb.env.message;

/**
 * 
 * <p>[功能描述]：导出EXCEL报警类，属性顺序要与EXCEL列对应</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月28日下午11:53:12
 * @since	EnvDust 1.0.0
 *
 */
public class ExportAlarmMessage extends BaseMessage{
	
	private String alarmTypeName;
	private String deviceName;
	private String areaName;
	private String alarmInfo;
	private String alarmStatusInfo;
	private String alarmTime;
	private String alarmAction;
	private String actionUser;
	private String actionTime;
	/**
	 * @return the alarmTypeName
	 */
	public String getAlarmTypeName() {
		return alarmTypeName;
	}
	/**
	 * @param alarmTypeName the alarmTypeName to set
	 */
	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
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
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * @return the alarmInfo
	 */
	public String getAlarmInfo() {
		return alarmInfo;
	}
	/**
	 * @param alarmInfo the alarmInfo to set
	 */
	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}
	/**
	 * @return the alarmAction
	 */
	public String getAlarmAction() {
		return alarmAction;
	}
	/**
	 * @param alarmAction the alarmAction to set
	 */
	public void setAlarmAction(String alarmAction) {
		this.alarmAction = alarmAction;
	}
	/**
	 * @return the actionUser
	 */
	public String getActionUser() {
		return actionUser;
	}
	/**
	 * @param actionUser the actionUser to set
	 */
	public void setActionUser(String actionUser) {
		this.actionUser = actionUser;
	}
	/**
	 * @return the alarmStatusInfo
	 */
	public String getAlarmStatusInfo() {
		return alarmStatusInfo;
	}
	/**
	 * @param alarmStatusInfo the alarmStatusInfo to set
	 */
	public void setAlarmStatusInfo(String alarmStatusInfo) {
		this.alarmStatusInfo = alarmStatusInfo;
	}
	/**
	 * @return the alarmTime
	 */
	public String getAlarmTime() {
		return alarmTime;
	}
	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
	/**
	 * @return the actionTime
	 */
	public String getActionTime() {
		return actionTime;
	}
	/**
	 * @param actionTime the actionTime to set
	 */
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	
}
