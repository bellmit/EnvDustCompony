package com.tcb.env.pojo;

import java.sql.Timestamp;

import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：映射数据库表基类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年3月18日下午1:22:40
 * @since EnvDust 1.0.0
 *
 */
public class BasePojo {

	/**
	 * 查询个数(mybatis默认int型0是空，所以使用默认值-1作为条件)
	 */
	private int rowCount = DefaultArgument.INT_DEFAULT;
	/**
	 * 开始查询位置
	 */
	private int rowIndex = DefaultArgument.INT_DEFAULT;

	/**
	 * 总条数
	 */
	private int allCount = DefaultArgument.INT_DEFAULT;
	
	/**
	 * 操作者
	 */
	private int optUser = DefaultArgument.INT_DEFAULT;
	
	/**
	 * 操作时间
	 */
	private Timestamp optTime = DateUtil.GetSystemDateTime(0);

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * @param rowCount
	 *            the rowCount to set
	 */
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	/**
	 * @return the rowIndex
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex
	 *            the rowIndex to set
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the allCount
	 */
	public int getAllCount() {
		return allCount;
	}

	/**
	 * @param allCount
	 *            the allCount to set
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	
	/**
	 * @return the optUser
	 */
	public int getOptUser() {
		return optUser;
	}
	/**
	 * @param optUser the optUser to set
	 */
	public void setOptUser(int optUser) {
		this.optUser = optUser;
	}
	/**
	 * @return the optTime
	 */
	public Timestamp getOptTime() {
		return optTime;
	}
	/**
	 * @param optTime the optTime to set
	 */
	public void setOptTime(Timestamp optTime) {
		this.optTime = optTime;
	}
	
}
