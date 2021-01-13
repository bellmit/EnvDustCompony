package com.tcb.env.model;

import java.util.List;

/**
 * 
 * <p>[功能描述]：全屏监控页面model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月4日上午8:54:34
 * @since	EnvDust 1.0.0
 *
 */
public class GeneralModel extends ResultModel {

	private GeneralCountModel gcm;
	private List<GeneralAlarmModel> gamList;
	private List<GeneralDeviceLocationModel> gdlmList;
	private List<GeneralDeviceDataModel> gddmList;
	
	/**
	 * @return the gcm
	 */
	public GeneralCountModel getGcm() {
		return gcm;
	}
	/**
	 * @param gcm the gcm to set
	 */
	public void setGcm(GeneralCountModel gcm) {
		this.gcm = gcm;
	}
	/**
	 * @return the gamList
	 */
	public List<GeneralAlarmModel> getGamList() {
		return gamList;
	}
	/**
	 * @param gamList the gamList to set
	 */
	public void setGamList(List<GeneralAlarmModel> gamList) {
		this.gamList = gamList;
	}
	/**
	 * @return the gdlmList
	 */
	public List<GeneralDeviceLocationModel> getGdlmList() {
		return gdlmList;
	}
	/**
	 * @param gdlmList the gdlmList to set
	 */
	public void setGdlmList(List<GeneralDeviceLocationModel> gdlmList) {
		this.gdlmList = gdlmList;
	}
	/**
	 * @return the gddmList
	 */
	public List<GeneralDeviceDataModel> getGddmList() {
		return gddmList;
	}
	/**
	 * @param gddmList the gddmList to set
	 */
	public void setGddmList(List<GeneralDeviceDataModel> gddmList) {
		this.gddmList = gddmList;
	}
	
}
