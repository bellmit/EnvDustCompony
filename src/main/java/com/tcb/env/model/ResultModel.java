package com.tcb.env.model;

/**
 * 
 * <p>[功能描述]：返回结果类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月18日上午11:56:00
 * @since	EnvDust 1.0.0
 *
 */
public class ResultModel {

	/**
	 * 是否成功标志
	 */
	private boolean result = false;
	
	/**
	 * 详细信息（错误时不为空）
	 */
	private String detail = null;

	/**
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
