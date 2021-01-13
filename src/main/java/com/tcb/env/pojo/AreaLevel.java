package com.tcb.env.pojo;

import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：区域级别pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年3月23日上午10:20:17
 * @since	EnvDust 1.0.0
 *
 */
public class AreaLevel {

	private int levelId = DefaultArgument.INT_DEFAULT;
	private String levelName;
	private int levelFlag = DefaultArgument.INT_DEFAULT;
	
	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}
	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	/**
	 * @return the levelFlag
	 */
	public int getLevelFlag() {
		return levelFlag;
	}
	/**
	 * @param levelFlag the levelFlag to set
	 */
	public void setLevelFlag(int levelFlag) {
		this.levelFlag = levelFlag;
	}
	
	
}
