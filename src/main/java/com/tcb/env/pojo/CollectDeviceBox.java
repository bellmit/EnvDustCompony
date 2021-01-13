package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：超标采样设备箱子pojo</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日上午9:22:32
 * @since	EnvDust 1.0.0
 *
 */
public class CollectDeviceBox extends BasePojo {
	
	private long boxId;
	private String boxNo;
	private String boxName;
	private String boxStatus;
	private CollectDevice collectDevice;
	
	/**
	 * @return the boxId
	 */
	public long getBoxId() {
		return boxId;
	}
	/**
	 * @param boxId the boxId to set
	 */
	public void setBoxId(long boxId) {
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
	 * @return the collectDevice
	 */
	public CollectDevice getCollectDevice() {
		return collectDevice;
	}
	/**
	 * @param collectDevice the collectDevice to set
	 */
	public void setCollectDevice(CollectDevice collectDevice) {
		this.collectDevice = collectDevice;
	}
	

}
