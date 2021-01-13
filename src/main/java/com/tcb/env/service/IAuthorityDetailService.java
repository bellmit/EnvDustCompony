package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.TreeModel;
import com.tcb.env.pojo.AuthorityDetail;

/**
 * 
 * <p>
 * [功能描述]：权限组控件明细操作服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日下午2:30:04
 * @since EnvDust 1.0.0
 *
 */
public interface IAuthorityDetailService {

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
	public List<AuthorityDetail> getAuthorityDetail(String ahrCode);

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
	public int insertAuthorityDetail(List<AuthorityDetail> listAhrDetail)
			throws Exception;

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
	public int deleteAuthorityDetail(List<String> listAhrCode)
			throws Exception;

	/**
	 * 
	 * <p>[功能描述]：更新权限组控件数据</p>
	 * 
	 * @author	王垒, 2016年4月19日上午8:19:51
	 * @since	EnvDust 1.0.0
	 *
	 * @param ahrCode
	 * @param listTree
	 * @param optuser
	 * @return
	 * @throws Exception
	 */
	public int updateAuthorityDetail(String ahrCode, List<TreeModel> listTree,int optuser)
			throws Exception;

}
