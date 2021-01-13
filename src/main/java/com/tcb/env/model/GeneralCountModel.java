package com.tcb.env.model;

import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：大屏设备连接状态数量model</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月8日下午4:18:40
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralCountModel{

	private int normalCount = DefaultArgument.INT_DEFAULT;
	private int overCount = DefaultArgument.INT_DEFAULT;
	private int disconnectCount = DefaultArgument.INT_DEFAULT;
	private int nodataCount = DefaultArgument.INT_DEFAULT;
	private int otherCount = DefaultArgument.INT_DEFAULT;
	private int allCount = DefaultArgument.INT_DEFAULT;
	
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
	 * @return the allCount
	 */
	public int getAllCount() {
		return allCount;
	}
	/**
	 * @param allCount the allCount to set
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	
}
