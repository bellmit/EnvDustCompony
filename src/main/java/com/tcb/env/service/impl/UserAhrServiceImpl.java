package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IUserAhrDao;
import com.tcb.env.pojo.User;
import com.tcb.env.pojo.UserAhr;
import com.tcb.env.service.IUserAhrService;

/**
 * 
 * <p>
 * [功能描述]：用户与权限组关系查询服务类接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月5日下午4:11:56
 * @since EnvDust 1.0.0
 *
 */
@Service("userAhrService")
@Transactional(rollbackFor = Exception.class)
class UserAhrServiceImpl implements IUserAhrService {

	@Resource
	private IUserAhrDao userAhrDao;

	@Override
	public String getUserAhrCode(String userCode) {
		return userAhrDao.getUserAhrCode(userCode);
	}

	@Override
	public int getUserAhrCount(String userCode) {
		return userAhrDao.getUserAhrCount(userCode);
	}

	@Override
	public int getOtherUserAhrCount(UserAhr userAhr) {
		return userAhrDao.getOtherUserAhrCount(userAhr);
	}

	@Override
	public List<User> getOtherUserAhr(UserAhr userAhr) {
		return userAhrDao.getOtherUserAhr(userAhr);
	}

	@Override
	public int insertUserAhr(List<UserAhr> listUserAhr) throws Exception {
		return userAhrDao.insertUserAhr(listUserAhr);
	}

	@Override
	public int deleteUserAhr(String ahrCode, List<String> listUserCode)
			throws Exception {
		return userAhrDao.deleteUserAhr(ahrCode, listUserCode);
	}

}
