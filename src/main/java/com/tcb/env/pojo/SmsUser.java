package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：短信用户</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年4月12日上午9:59:01
 * @since	EnvDust 1.0.0
 *
 */
public class SmsUser extends BasePojo {
	
	private long smsuId;
	private Sms sms;
	private User user;
	
	/**
	 * @return the smsuId
	 */
	public long getSmsuId() {
		return smsuId;
	}
	/**
	 * @param smsuId the smsuId to set
	 */
	public void setSmsuId(long smsuId) {
		this.smsuId = smsuId;
	}
	/**
	 * @return the sms
	 */
	public Sms getSms() {
		return sms;
	}
	/**
	 * @param sms the sms to set
	 */
	public void setSms(Sms sms) {
		this.sms = sms;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
