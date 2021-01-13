package com.tcb.env.pojo;

/**
 * 
 * <p>[功能描述]：计划任务准备信息pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年5月31日下午4:03:38
 * @since	EnvDust 1.0.0
 *
 */
public class CommPrepare extends BasePojo {

	private int prId;
	private String qn;
	private CommCn commCn;
	private String cp;
	
	/**
	 * @return the prId
	 */
	public int getPrId() {
		return prId;
	}
	/**
	 * @param prId the prId to set
	 */
	public void setPrId(int prId) {
		this.prId = prId;
	}
	/**
	 * @return the qn
	 */
	public String getQn() {
		return qn;
	}
	/**
	 * @param qn the qn to set
	 */
	public void setQn(String qn) {
		this.qn = qn;
	}
	/**
	 * @return the commCn
	 */
	public CommCn getCommCn() {
		return commCn;
	}
	/**
	 * @param commCn the commCn to set
	 */
	public void setCommCn(CommCn commCn) {
		this.commCn = commCn;
	}
	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}
	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	
}
