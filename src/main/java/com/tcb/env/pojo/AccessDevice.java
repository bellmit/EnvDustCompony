package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：权限组可访问设备pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月1日上午11:46:51
 * @since	EnvDust 1.0.0
 *
 */
public class AccessDevice extends BasePojo {

	private int accessAreaId;
	private Authority authority;
	private Device device;
	/**
	 * @return the accessAreaId
	 */
	public int getAccessAreaId() {
		return accessAreaId;
	}
	/**
	 * @param accessAreaId the accessAreaId to set
	 */
	public void setAccessAreaId(int accessAreaId) {
		this.accessAreaId = accessAreaId;
	}
	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
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
	
}
