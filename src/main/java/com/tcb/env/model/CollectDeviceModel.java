package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：超标采样设备model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日下午2:15:09
 * @since	EnvDust 1.0.0
 *
 */
public class CollectDeviceModel extends BaseModel {

	private String cdId;
	private String cdCode;
	private String cdMn;
	private String cdName;
	private String cdIp;
	private String cdPort;
	private String cdPwd;
	
	/**
	 * @return the cdId
	 */
	public String getCdId() {
		return cdId;
	}
	/**
	 * @param cdId the cdId to set
	 */
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	/**
	 * @return the cdCode
	 */
	public String getCdCode() {
		return cdCode;
	}
	/**
	 * @param cdCode the cdCode to set
	 */
	public void setCdCode(String cdCode) {
		this.cdCode = cdCode;
	}
	/**
	 * @return the cdMn
	 */
	public String getCdMn() {
		return cdMn;
	}
	/**
	 * @param cdMn the cdMn to set
	 */
	public void setCdMn(String cdMn) {
		this.cdMn = cdMn;
	}
	/**
	 * @return the cdName
	 */
	public String getCdName() {
		return cdName;
	}
	/**
	 * @param cdName the cdName to set
	 */
	public void setCdName(String cdName) {
		this.cdName = cdName;
	}
	/**
	 * @return the cdIp
	 */
	public String getCdIp() {
		return cdIp;
	}
	/**
	 * @param cdIp the cdIp to set
	 */
	public void setCdIp(String cdIp) {
		this.cdIp = cdIp;
	}
	/**
	 * @return the cdPort
	 */
	public String getCdPort() {
		return cdPort;
	}
	/**
	 * @param cdPort the cdPort to set
	 */
	public void setCdPort(String cdPort) {
		this.cdPort = cdPort;
	}
	/**
	 * @return the cdPwd
	 */
	public String getCdPwd() {
		return cdPwd;
	}
	/**
	 * @param cdPwd the cdPwd to set
	 */
	public void setCdPwd(String cdPwd) {
		this.cdPwd = cdPwd;
	}
	
}
