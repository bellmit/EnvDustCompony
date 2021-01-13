/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Status;

/**
 * <p>
 * [功能描述]：系统状态数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年3月28日上午9:04:55
 * @since EnvDust 1.0.0
 * 
 */
public interface IStatusDao {
	
	/**
	 * 
	 * <p>[功能描述]：获取系统状态值</p>
	 * 
	 * @author	王垒, 2016年4月28日上午11:40:07
	 * @since	EnvDust 1.0.0
	 *
	 * @param status：状态值
	 * @param nostatus：非状态值
	 * @return
	 */
	public List<Status> getStatus(@Param("status") String status,
			@Param("nostatus") String nostatus);

}
