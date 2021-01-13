package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：权限组可访问监测物pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月1日上午11:53:55
 * @since	EnvDust 1.0.0
 *
 */
public class AccessMonitor extends BasePojo {

	private int accessAreaId;
	private Authority authority;
	private Monitor Monitor;
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
	 * @return the monitor
	 */
	public Monitor getMonitor() {
		return Monitor;
	}
	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(Monitor monitor) {
		Monitor = monitor;
	}
	
}
