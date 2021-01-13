package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.SystemModel;

/**
 * 
 * <p>[功能描述]：系统版本参数数据库接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月6日下午2:05:02
 * @since	EnvDust 1.0.0
 *
 */
public interface ISystemDao {
	
	/**
	 * 
	 * <p>[功能描述]：获取最新系统版本参数</p>
	 * 
	 * @author	王垒, 2018年2月6日下午2:25:26
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysCode
	 * @return
	 */
	List<SystemModel> getNewSystem(@Param("sysCode")String sysCode);

}
