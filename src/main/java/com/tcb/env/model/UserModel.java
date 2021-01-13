package com.tcb.env.model;

import java.sql.Timestamp;

import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：页面传递User
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年3月18日上午10:02:20
 * @since EnvDust 1.0.0
 *
 */
public class UserModel extends BaseModel {
	private int userId = DefaultArgument.INT_DEFAULT;
	private String userCode;
	private String userName;
	private String userPassword;
	private int orgId = DefaultArgument.INT_DEFAULT;
	private String orgName;
	private int statisticsTime = DefaultArgument.INT_DEFAULT;
	private String userTel;
	private String userEmail;
	private boolean userDelete;
	private String userDeleteName;
	private String userRemark;
	private Timestamp selectTime;
	private Timestamp alarmUpdateTime;

	
	/**
	 * @return the statisticsTime
	 */
	public int getStatisticsTime() {
		return statisticsTime;
	}

	/**
	 * @param statisticsTime the statisticsTime to set
	 */
	public void setStatisticsTime(int statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	/**
	 * @return the userTel
	 */
	public String getUserTel() {
		return userTel;
	}

	/**
	 * @param userTel the userTel to set
	 */
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 *            the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the orgId
	 */
	public int getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the userDelete
	 */
	public boolean getUserDelete() {
		return userDelete;
	}

	/**
	 * @param userDelete
	 *            the userDelete to set
	 */
	public void setUserDelete(boolean userDelete) {
		this.userDelete = userDelete;
	}

	/**
	 * @return the userDeleteName
	 */
	public String getUserDeleteName() {
		return userDeleteName;
	}

	/**
	 * @param userDeleteName the userDeleteName to set
	 */
	public void setUserDeleteName(String userDeleteName) {
		this.userDeleteName = userDeleteName;
	}

	/**
	 * @return the userRemark
	 */
	public String getUserRemark() {
		return userRemark;
	}

	/**
	 * @param userRemark
	 *            the userRemark to set
	 */
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	/**
	 * @return the selectTime
	 */
	public Timestamp getSelectTime() {
		return selectTime;
	}

	/**
	 * @param selectTime the selectTime to set
	 */
	public void setSelectTime(Timestamp selectTime) {
		this.selectTime = selectTime;
	}

	/**
	 * @return the alarmUpdateTime
	 */
	public Timestamp getAlarmUpdateTime() {
		return alarmUpdateTime;
	}

	/**
	 * @param alarmUpdateTime the alarmUpdateTime to set
	 */
	public void setAlarmUpdateTime(Timestamp alarmUpdateTime) {
		this.alarmUpdateTime = alarmUpdateTime;
	}
	
}
