package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IAuthorityDao;
import com.tcb.env.pojo.Authority;
import com.tcb.env.service.IAuthorityService;

/**
 * 
 * <p>
 * [功能描述]：权限组操作服务类接口实现
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月5日下午2:59:25
 * @since EnvDust 1.0.0
 *
 */
@Service("authorityService")
@Transactional(rollbackFor = Exception.class)
class AuthorityServiceImpl implements IAuthorityService {

	@Resource
	private IAuthorityDao authorityDao;
	
	@Override
	public int getAuthorityCount(Authority authority) {
		return authorityDao.getAuthorityCount(authority);
	}

	@Override
	public List<Authority> getAuthority(Authority authority) {
		return authorityDao.getAuthority(authority);
	}

	@Override
	public int insertAuthority(List<Authority> listAuthority) throws Exception {
		return authorityDao.insertAuthority(listAuthority);
	}

	@Override
	public int updateAuthority(List<Authority> listAuthority) throws Exception {
		return authorityDao.updateAuthority(listAuthority);
	}

	@Override
	public int deleteAuthority(List<String> listid) throws Exception {
		return authorityDao.deleteAuthority(listid);
	}

	@Override
	public int getAuthorityExist(String authotiryCode, int authotiryId) {
		return authorityDao.getAuthorityExist(authotiryCode, authotiryId);
	}

	@Override
	public String getAuthorityCodeById(String authorityId) {
		return authorityDao.getAuthorityCodeById(authorityId);
	}

}
