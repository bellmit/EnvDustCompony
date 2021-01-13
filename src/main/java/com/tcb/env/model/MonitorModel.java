/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.model;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月30日上午11:28:45
 * @since	EnvDust 1.0.0
 * 
 */
public class MonitorModel extends BaseModel{
	
	private int thingId = DefaultArgument.INT_DEFAULT;//递增ID
	private String thingCode;//监测物编号
	private String thingName;//监测物名称
	private String thingUnit;
	private String thingOrder;
	
	/**
	 * @return the thingUnit
	 */
	public String getThingUnit() {
		return thingUnit;
	}
	/**
	 * @param thingUnit the thingUnit to set
	 */
	public void setThingUnit(String thingUnit) {
		this.thingUnit = thingUnit;
	}
	/**
	 * @return the thingId
	 */
	public int getThingId() {
		return thingId;
	}
	/**
	 * @param thingId the thingId to set
	 */
	public void setThingId(int thingId) {
		this.thingId = thingId;
	}
	/**
	 * @return the thingCode
	 */
	public String getThingCode() {
		return thingCode;
	}
	/**
	 * @param thingCode the thingCode to set
	 */
	public void setThingCode(String thingCode) {
		this.thingCode = thingCode;
	}
	/**
	 * @return the thingName
	 */
	public String getThingName() {
		return thingName;
	}
	/**
	 * @param thingName the thingName to set
	 */
	public void setThingName(String thingName) {
		this.thingName = thingName;
	}
	/**
	 * @return the thingOrder
	 */
	public String getThingOrder() {
		return thingOrder;
	}
	/**
	 * @param thingOrder the thingOrder to set
	 */
	public void setThingOrder(String thingOrder) {
		this.thingOrder = thingOrder;
	}
	
}
