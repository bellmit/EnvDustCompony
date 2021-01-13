package com.tcb.env.pojo;

import java.sql.Timestamp;

/**
 * 
 * <p>[功能描述]：映射数据库监控数据表</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月29日下午1:28:26
 * @since	EnvDust 1.0.0
 *
 */
public class MonitorStorage extends BasePojo {

	private int storageId;
	private Device device;
	private Monitor monitor;
	private double thingValue;
	private Status status;
	private Timestamp updateTime;
	/**
	 * @return the storageId
	 */
	public int getStorageId() {
		return storageId;
	}
	/**
	 * @param storageId the storageId to set
	 */
	public void setStorageId(int storageId) {
		this.storageId = storageId;
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
	 * @return the monitor
	 */
	public Monitor getMonitor() {
		return monitor;
	}
	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}
	/**
	 * @return the thingValue
	 */
	public double getThingValue() {
		return thingValue;
	}
	/**
	 * @param thingValue the thingValue to set
	 */
	public void setThingValue(double thingValue) {
		this.thingValue = thingValue;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
