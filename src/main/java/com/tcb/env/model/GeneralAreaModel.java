package com.tcb.env.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>[功能描述]：大屏区域model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月5日上午9:18:23
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralAreaModel {

	private String areaId;
	private String areaName;
	private List<GeneralAreaDeviceModel> gadmList = new ArrayList<GeneralAreaDeviceModel>();
	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * @return the gadmList
	 */
	public List<GeneralAreaDeviceModel> getGadmList() {
		return gadmList;
	}
	/**
	 * @param gadmList the gadmList to set
	 */
	public void setGadmList(List<GeneralAreaDeviceModel> gadmList) {
		this.gadmList = gadmList;
	}
	
}
