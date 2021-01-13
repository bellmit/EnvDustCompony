package com.tcb.env.model;

import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * 
 * <p>[功能描述]：用来暂存报警数据的信息</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王坤
 * @version	1.0, 2018年8月28日下午3:26:37
 * @since	EnvDust 1.0.0
 *
 */
public class MapMonitorData {
	private List<String> list;
	private String levelFlag;
	private String noStatus;
	private String select;
	private int maxSize;
	private HttpSession httpSession;
	
	public MapMonitorData(){
		
	}
	
	public MapMonitorData(List<String> list, String levelFlag, String noStatus,
			String select, int maxSize, HttpSession httpSession) {
		super();
		this.list = list;
		this.levelFlag = levelFlag;
		this.noStatus = noStatus;
		this.select = select;
		this.maxSize = maxSize;
		this.httpSession = httpSession;
	}



	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}

	public String getNoStatus() {
		return noStatus;
	}

	public void setNoStatus(String noStatus) {
		this.noStatus = noStatus;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
}
