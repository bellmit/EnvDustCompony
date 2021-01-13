/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.pojo;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：IP</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月1日下午2:46:12
 * @since	EnvDust 1.0.0
 * 
 */
public class Ip extends BasePojo{
	private int ipId = DefaultArgument.INT_DEFAULT;//递增id
	private String ipAddress;//ip地址
	private String remark;//备注
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the ipId
	 */
	public int getIpId() {
		return ipId;
	}
	/**
	 * @param ipId the ipId to set
	 */
	public void setIpId(int ipId) {
		this.ipId = ipId;
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
