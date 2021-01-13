package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Authority;

/**
 * 
 * <p>
 * [功能描述]：权限组数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月5日下午1:00:06
 * @since EnvDust 1.0.0
 *
 */
public interface IAuthorityDao {

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
	public int getAuthorityCount(@Param("authority") Authority authority);

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
	public List<Authority> getAuthority(@Param("authority") Authority authority);

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
	 * @return
	 */
	public int insertAuthority(
			@Param("listAuthority") List<Authority> listAuthority);

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
	 * @return
	 */
	public int updateAuthority(
			@Param("listAuthority") List<Authority> listAuthority);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限组数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月5日下午1:36:08
	 * @since EnvDust 1.0.0
	 *
	 * @param listAuthority
	 * @return
	 */
	public int deleteAuthority(@Param("listid") List<String> listid);

	/**
	 * 
	 * <p>[功能描述]：通过条件（非ID的）获取符合结果个数</p>
	 * 
	 * @author	王垒, 2016年4月5日下午2:51:33
	 * @since	EnvDust 1.0.0
	 *
	 * @param authorityCode
	 * @param authorityId
	 * @return
	 */
	public int getAuthorityExist(@Param("authorityCode") String authorityCode,
			@Param("authorityId") int authorityId);
	
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
	public String getAuthorityCodeById(@Param("authorityId") String authorityId);

}
