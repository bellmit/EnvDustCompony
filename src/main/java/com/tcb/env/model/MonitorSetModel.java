/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.model;


import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月10日下午9:00:32
 * @since	EnvDust 1.0.0
 * 
 */
public class MonitorSetModel extends BaseModel{
	private int monitorId = DefaultArgument.INT_DEFAULT;//递增id
	private String deviceCode;//设备编号
	private String thingCode;//监测物编号
	private double monitorMax = DefaultArgument.DOUBLE_DEFAULT;//超出最大预警值
	private double monitorMin = DefaultArgument.DOUBLE_DEFAULT;//超出最小预警值
	private String startTime;//开始时间
	private String endTime;//结束时间
	private int monitorReport = DefaultArgument.INT_DEFAULT; 
	private String alarmCode;//报警编号
	/**
	 * @return the monitorId
	 */
	public int getMonitorId() {
		return monitorId;
	}
	/**
	 * @param monitorId the monitorId to set
	 */
	public void setMonitorId(int monitorId) {
		this.monitorId = monitorId;
	}
	/**
	 * @return the deviceCode
	 */
	public String getDeviceCode() {
		return deviceCode;
	}
	/**
	 * @param deviceCode the deviceCode to set
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	/**
	 * @return the thingCode
	 */
	public String getThingCode() {
		return thingCode;
	}
	/**
	 * @param thingCode the thingCode to set
	 */
	public void setThingCode(String thingCode) {
		this.thingCode = thingCode;
	}
	/**
	 * @return the monitorMax
	 */
	public double getMonitorMax() {
		return monitorMax;
	}
	/**
	 * @param monitorMax the monitorMax to set
	 */
	public void setMonitorMax(double monitorMax) {
		this.monitorMax = monitorMax;
	}
	/**
	 * @return the monitorMin
	 */
	public double getMonitorMin() {
		return monitorMin;
	}
	/**
	 * @param monitorMin the monitorMin to set
	 */
	public void setMonitorMin(double monitorMin) {
		this.monitorMin = monitorMin;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the monitorReport
	 */
	public int getMonitorReport() {
		return monitorReport;
	}
	/**
	 * @param monitorReport the monitorReport to set
	 */
	public void setMonitorReport(int monitorReport) {
		this.monitorReport = monitorReport;
	}
	/**
	 * @return the alarmCode
	 */
	public String getAlarmCode() {
		return alarmCode;
	}
	/**
	 * @param alarmCode the alarmCode to set
	 */
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}
	
}
