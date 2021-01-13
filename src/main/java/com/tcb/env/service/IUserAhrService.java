package com.tcb.env.service;

import java.util.List;
import com.tcb.env.pojo.User;
import com.tcb.env.pojo.UserAhr;

/**
 * 
 * <p>
 * [功能描述]：用户与权限组关系查询服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月5日下午4:09:17
 * @since EnvDust 1.0.0
 *
 */
public interface IUserAhrService {

	/**
	 * 
	 * <p>
	 * [功能描述]：获取用户权限组
	 * </p>
	 * 
	 * @author 王垒, 2016年4月19日下午3:42:49
	 * @since EnvDust 1.0.0
	 *
	 * @param userCode
	 * @return
	 */
	public String getUserAhrCode(String userCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：是否存在用户
	 * </p>
	 * 
	 * @author 王垒, 2016年4月19日下午2:57:43
	 * @since EnvDust 1.0.0
	 *
	 * @param userCode
	 * @return
	 */
	public int getUserAhrCount(String userCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取权限组内用户数量
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午3:24:52
	 * @since EnvDust 1.0.0
	 *
	 * @param userAhr
	 * @return
	 */
	public int getOtherUserAhrCount(UserAhr userAhr);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取权限组内用户数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午3:25:42
	 * @since EnvDust 1.0.0
	 *
	 * @param userAhr
	 * @return
	 */
	public List<User> getOtherUserAhr(UserAhr userAhr);

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
	public int insertUserAhr(List<UserAhr> listUserAhr) throws Exception;

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
	public int deleteUserAhr(String ahrCode, List<String> listUserCode)
			throws Exception;

}
