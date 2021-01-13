package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.Authority;

/**
 * 
 * <p>
 * [功能描述]：权限组操作服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月5日下午2:53:45
 * @since EnvDust 1.0.0
 *
 */
public interface IAuthorityService {

	/**
	 * 
	 * <p>
	 * [功能描述]：查询权限组个数
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午1:30:29
	 * @since EnvDust 1.0.0
	 *
	 * @param authority
	 * @return
	 */
	public int getAuthorityCount(Authority authority);

	/**
	 * 
	 * <p>
	 * [功能描述]：查询权限组数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午1:31:47
	 * @since EnvDust 1.0.0
	 *
	 * @param authority
	 * @return
	 */
	public List<Authority> getAuthority(Authority authority);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入权限组数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午1:34:58
	 * @since EnvDust 1.0.0
	 *
	 * @param listAuthority
	 * @throws Exception
	 * @return
	 */
	public int insertAuthority(List<Authority> listAuthority) throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：更新权限组数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午1:35:38
	 * @since EnvDust 1.0.0
	 *
	 * @param listAuthority
	 * @throws Exception
	 * @return
	 */
	public int updateAuthority(List<Authority> listAuthority) throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限组数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午1:36:08
	 * @since EnvDust 1.0.0
	 *
	 * @param listid
	 * @throws Exception
	 * @return
	 */
	public int deleteAuthority(List<String> listid) throws Exception;

	/**
	 * 
	 * <p>
	 * [功能描述]：通过条件（非ID的）获取符合结果个数
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午2:51:33
	 * @since EnvDust 1.0.0
	 *
	 * @param authotiryCode
	 * @param authotiryId
	 * @return
	 */
	public int getAuthorityExist(String authotiryCode, int authotiryId);
	
	/**
	 * 
	 * <p>[功能描述]：通过Id获取编码</p>
	 * 
	 * @author	王垒, 2017年11月22日下午3:35:15
	 * @since	EnvDust 1.0.0
	 *
	 * @param authorityId
	 * @return
	 */
	public String getAuthorityCodeById(String authorityId);
	
}
