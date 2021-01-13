package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.AuthorityDetail;

/**
 * 
 * <p>
 * [功能描述]：权限组控件明细数据库操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日下午1:46:51
 * @since EnvDust 1.0.0
 *
 */
public interface IAuthorityDetailDao {

	/**
	 * 
	 * <p>
	 * [功能描述]：查询权限明细控件数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日下午1:54:08
	 * @since EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @return
	 */
	public List<AuthorityDetail> getAuthorityDetail(@Param("ahrCode") String ahrCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：新添权限明细控件数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日下午1:49:55
	 * @since EnvDust 1.0.0
	 *
	 * @param listAhrDetail
	 * @return
	 */
	public int insertAuthorityDetail(
			@Param("listAhrDetail") List<AuthorityDetail> listAhrDetail);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除权限明细控件数据
	 * </p>
	 * 
	 * @author 王垒, 2016年4月6日下午1:52:26
	 * @since EnvDust 1.0.0
	 *
	 * @param listAhrCode
	 * @return
	 */
	public int deleteAuthorityDetail(@Param("listAhrCode") List<String> listAhrCode);

}
