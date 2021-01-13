package com.tcb.env.config;

import org.springframework.stereotype.Component;

/**
 * 
 * <p>[功能描述]：扬尘系统默认数据库名称参数</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月22日下午2:20:30
 * @since	EnvDust 1.0.0
 *
 */
@Component("DataBaseConfig")
public class DataBaseConfig {

	private String dbName="envdust";
	private String dbOldName="envdust_old";
	
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}
	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	/**
	 * @return the dbOldName
	 */
	public String getDbOldName() {
		return dbOldName;
	}
	/**
	 * @param dbOldName the dbOldName to set
	 */
	public void setDbOldName(String dbOldName) {
		this.dbOldName = dbOldName;
	}
		
}
