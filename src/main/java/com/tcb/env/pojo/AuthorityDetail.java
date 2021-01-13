package com.tcb.env.pojo;

/**
 * 
 * <p>
 * [功能描述]：权限组控件明细pojo
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日下午1:39:19
 * @since EnvDust 1.0.0
 *
 */
public class AuthorityDetail extends BasePojo {

	private int authorityDetailId;
	private Authority authority;
	private int controlId;
	private String controlName;
	private int levelId;
	private int dealType;
	private String checkStatus;

	/**
	 * @return the authorityDetailId
	 */
	public int getAuthorityDetailId() {
		return authorityDetailId;
	}

	/**
	 * @param authorityDetailId
	 *            the authorityDetailId to set
	 */
	public void setAuthorityDetailId(int authorityDetailId) {
		this.authorityDetailId = authorityDetailId;
	}

	/**
	 * @return the authority
	 */
	public Authority getAuthority() {
		return authority;
	}

	/**
	 * @param authority
	 *            the authority to set
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	/**
	 * @return the controlId
	 */
	public int getControlId() {
		return controlId;
	}

	/**
	 * @param controlId
	 *            the controlId to set
	 */
	public void setControlId(int controlId) {
		this.controlId = controlId;
	}

	/**
	 * @return the controlName
	 */
	public String getControlName() {
		return controlName;
	}

	/**
	 * @param controlName the controlName to set
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId
	 *            the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	/**
	 * @return the dealType
	 */
	public int getDealType() {
		return dealType;
	}

	/**
	 * @param dealType
	 *            the dealType to set
	 */
	public void setDealType(int dealType) {
		this.dealType = dealType;
	}

	/**
	 * @return the checkStatus
	 */
	public String getCheckStatus() {
		return checkStatus;
	}

	/**
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

}
