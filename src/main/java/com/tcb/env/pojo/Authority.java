package com.tcb.env.pojo;

import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：权限组pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年4月1日上午11:26:27
 * @since	EnvDust 1.0.0
 *
 */
public class Authority extends BasePojo {

	private int authorityId = DefaultArgument.INT_DEFAULT;
	private String authorityCode;
	private String authorityName;
	private String remark;
	/**
	 * @return the authorityId
	 */
	public int getAuthorityId() {
		return authorityId;
	}
	/**
	 * @param authorityId the authorityId to set
	 */
	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}
	/**
	 * @return the authorityCode
	 */
	public String getAuthorityCode() {
		return authorityCode;
	}
	/**
	 * @param authorityCode the authorityCode to set
	 */
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	/**
	 * @return the authorityName
	 */
	public String getAuthorityName() {
		return authorityName;
	}
	/**
	 * @param authorityName the authorityName to set
	 */
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
