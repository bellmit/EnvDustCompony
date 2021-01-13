/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.pojo;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：manufacturer  pojo</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月24日上午9:15:08
 * @since	EnvDust 1.0.0
 * 
 */
public class Manufacturer extends BasePojo{
	private int mfrId = DefaultArgument.INT_DEFAULT;//递增ID
	private String mfrCode;//厂商编号
	private String mfrName;//厂商名称
	private String mfrAddress;//厂商地址
	private String mfrRemark;//厂商备注
	/**
	 * @return the mfrId
	 */
	public int getMfrId() {
		return mfrId;
	}
	/**
	 * @param mfrId the mfrId to set
	 */
	public void setMfrId(int mfrId) {
		this.mfrId = mfrId;
	}
	/**
	 * @return the mfrCode
	 */
	public String getMfrCode() {
		return mfrCode;
	}
	/**
	 * @param mfrCode the mfrCode to set
	 */
	public void setMfrCode(String mfrCode) {
		this.mfrCode = mfrCode;
	}
	/**
	 * @return the mfrName
	 */
	public String getMfrName() {
		return mfrName;
	}
	/**
	 * @param mfrName the mfrName to set
	 */
	public void setMfrName(String mfrName) {
		this.mfrName = mfrName;
	}
	/**
	 * @return the mfrAddress
	 */
	public String getMfrAddress() {
		return mfrAddress;
	}
	/**
	 * @param mfrAddress the mfrAddress to set
	 */
	public void setMfrAddress(String mfrAddress) {
		this.mfrAddress = mfrAddress;
	}
	/**
	 * @return the mfrRemark
	 */
	public String getMfrRemark() {
		return mfrRemark;
	}
	/**
	 * @param mfrRemark the mfrRemark to set
	 */
	public void setMfrRemark(String mfrRemark) {
		this.mfrRemark = mfrRemark;
	}
	
}
