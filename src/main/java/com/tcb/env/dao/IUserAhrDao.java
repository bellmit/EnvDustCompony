package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.User;
import com.tcb.env.pojo.UserAhr;

/**
 * 
 * <p>
 * [功能描述]：用户与权限组关系数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月5日下午3:05:40
 * @since EnvDust 1.0.0
 *
 */
public interface IUserAhrDao {

	/**
	 * 
	 * <p>[功能描述]：获取用户权限组</p>
	 * 
	 * @author	王垒, 2016年4月19日下午3:42:49
	 * @since	EnvDust 1.0.0
	 *
	 * @param userCode
	 * @return
	 */
	public String getUserAhrCode(@Param("userCode") String userCode);
	
	/**
	 * 
	 * <p>[功能描述]：是否存在用户</p>
	 * 
	 * @author	王垒, 2016年4月19日下午2:57:43
	 * @since	EnvDust 1.0.0
	 *
	 * @param userCode
	 * @return
	 */
	public int getUserAhrCount(@Param("userCode") String userCode);
	
	/**
	 * 
	 * <p>
	 * [功能描述]：获取权限组外用户数量
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午3:24:52
	 * @since EnvDust 1.0.0
	 *
	 * @param userAhr
	 * @return
	 */
	public int getOtherUserAhrCount(@Param("userAhr") UserAhr userAhr);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取权限组外用户数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午3:25:42
	 * @since EnvDust 1.0.0
	 *
	 * @param userAhr
	 * @return
	 */
	public List<User> getOtherUserAhr(@Param("userAhr") UserAhr userAhr);

	/**
	 * 
	 * <p>
	 * [功能描述]：新增用户权限组关系数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午3:14:32
	 * @since EnvDust 1.0.0
	 *
	 * @param listUserAhr
	 * @return
	 */
	public int insertUserAhr(@Param("listUserAhr") List<UserAhr> listUserAhr);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除用户权限组关系数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午3:21:34
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listUserCode
	 * @return
	 */
	public int deleteUserAhr(@Param("ahrCode") String ahrCode,
			@Param("listUserCode") List<String> listUserCode);

}
