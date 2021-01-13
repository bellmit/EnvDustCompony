package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IAuthorityDetailDao;
import com.tcb.env.model.TreeModel;
import com.tcb.env.pojo.Authority;
import com.tcb.env.pojo.AuthorityDetail;
import com.tcb.env.service.IAuthorityDetailService;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：权限组控件明细操作服务类接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日下午2:32:24
 * @since EnvDust 1.0.0
 *
 */
@Service("authorityDetailService")
@Transactional(rollbackFor = Exception.class)
class AuthorityDetailServiceImpl implements IAuthorityDetailService {

	@Resource
	private IAuthorityDetailDao authorityDetailDao;

	@Override
	public List<AuthorityDetail> getAuthorityDetail(String ahrCode) {
		return authorityDetailDao.getAuthorityDetail(ahrCode);
	}

	@Override
	public int insertAuthorityDetail(List<AuthorityDetail> listAhrDetail)
			throws Exception {
		return authorityDetailDao.insertAuthorityDetail(listAhrDetail);
	}

	@Override
	public int deleteAuthorityDetail(List<String> listAhrCode) throws Exception {
		return authorityDetailDao.deleteAuthorityDetail(listAhrCode);
	}

	@Override
	public int updateAuthorityDetail(String ahrCode, List<TreeModel> listTree,
			int optuser) throws Exception {
		int result = 0;
		if (ahrCode != null && !ahrCode.isEmpty()) {
			List<String> listDel = new ArrayList<String>();
			listDel.add(ahrCode);
			result = authorityDetailDao.deleteAuthorityDetail(listDel);
			List<AuthorityDetail> listDetail = new ArrayList<AuthorityDetail>();
			if (listTree != null && listTree.size() > 0) {
				for (TreeModel treeModel : listTree) {
					AuthorityDetail authorityDetail = new AuthorityDetail();
					Authority authority = new Authority();
					authority.setAuthorityCode(ahrCode);
					authorityDetail.setAuthority(authority);
					authorityDetail.setControlId(Integer.valueOf(treeModel
							.getId()));
					authorityDetail.setLevelId(Integer.valueOf(treeModel
							.getLevelFlag()));
					authorityDetail.setDealType(DefaultArgument.DEAL_TYPE);
					authorityDetail
							.setCheckStatus(treeModel.getCheckedStatus());
					authorityDetail.setOptUser(optuser);
					listDetail.add(authorityDetail);
				}
				result = authorityDetailDao.insertAuthorityDetail(listDetail);
			}
		}
		return result;
	}

}
