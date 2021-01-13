package com.tcb.env.model;

import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：设备连接状态数量模板</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月8日下午4:18:40
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralMonitorCountModel extends ResultModel {

	private String select;
	private Object object;
	private int normalCount = DefaultArgument.INT_DEFAULT;
	private int overCount = DefaultArgument.INT_DEFAULT;
	private int disconnectCount = DefaultArgument.INT_DEFAULT;
	private int nodataCount = DefaultArgument.INT_DEFAULT;
	private int otherCount = DefaultArgument.INT_DEFAULT;
	private int onlineCount = DefaultArgument.INT_DEFAULT;
	private int offlineCount = DefaultArgument.INT_DEFAULT;
	
	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}
	/**
	 * @param select the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}
	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}
	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}
	/**
	 * @return the normalCount
	 */
	public int getNormalCount() {
		return normalCount;
	}
	/**
	 * @param normalCount the normalCount to set
	 */
	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}
	/**
	 * @return the overCount
	 */
	public int getOverCount() {
		return overCount;
	}
	/**
	 * @param overCount the overCount to set
	 */
	public void setOverCount(int overCount) {
		this.overCount = overCount;
	}
	/**
	 * @return the disconnectCount
	 */
	public int getDisconnectCount() {
		return disconnectCount;
	}
	/**
	 * @param disconnectCount the disconnectCount to set
	 */
	public void setDisconnectCount(int disconnectCount) {
		this.disconnectCount = disconnectCount;
	}
	/**
	 * @return the nodataCount
	 */
	public int getNodataCount() {
		return nodataCount;
	}
	/**
	 * @param nodataCount the nodataCount to set
	 */
	public void setNodataCount(int nodataCount) {
		this.nodataCount = nodataCount;
	}
	/**
	 * @return the otherCount
	 */
	public int getOtherCount() {
		return otherCount;
	}
	/**
	 * @param otherCount the otherCount to set
	 */
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
	/**
	 * @return the onlineCount
	 */
	public int getOnlineCount() {
		return onlineCount;
	}
	/**
	 * @param onlineCount the onlineCount to set
	 */
	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}
	/**
	 * @return the offlineCount
	 */
	public int getOfflineCount() {
		return offlineCount;
	}
	/**
	 * @param offlineCount the offlineCount to set
	 */
	public void setOfflineCount(int offlineCount) {
		this.offlineCount = offlineCount;
	}
	
}
