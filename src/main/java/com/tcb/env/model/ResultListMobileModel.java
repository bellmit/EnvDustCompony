package com.tcb.env.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>[功能描述]：移动页面模板列表类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月2日下午1:53:37
 * @since	EnvDust 1.0.0
 * 
 * @param <T>
 */
public class ResultListMobileModel<T> {
	
	/**
	 * title
	 */
	private String title;
	
	/**
	 * logoUrl
	 */
	private String logoUrl;
	
	/**
	 * 返回结果详细信息
	 */
	private List<T> rows = new ArrayList<T>();

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the logoUrl
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @param logoUrl the logoUrl to set
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
