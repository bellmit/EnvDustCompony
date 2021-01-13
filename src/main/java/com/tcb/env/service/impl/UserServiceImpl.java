package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IUserDao;
import com.tcb.env.pojo.User;
import com.tcb.env.service.IUserService;

/**
 * <p>
 * [功能描述]：User操作服务类实现
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年3月16日下午2:09:23
 * @since EnvDust 1.0.0
 * 
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;

	@Override
	public int getCount(User user, boolean ignoredel) {
		return userDao.getCount(user, ignoredel);
	}

	@Override
	public List<User> getUser(User user, boolean ignoredel) {
		return userDao.getUser(user, ignoredel);
	}

	@Override
	public int insertUser(List<User> listuser) throws Exception {
		return userDao.insertUser(listuser);
	}

	@Override
	public int updateUser(List<User> listuser) throws Exception {
		return userDao.updateUser(listuser);
	}

	@Override
	public int deleteUser(List<String> listid) throws Exception {
		return userDao.deleteUser(listid);
	}

	@Override
	public int updateUserDelete(String userId,String userDelete) throws Exception {
		return userDao.updateUserDelete(userId,userDelete);
	}

	@Override
	public String getUserNameById(int userid, String usercode) {
		return userDao.getUserNameById(userid, usercode);
	}

	@Override
	public String getUserCodeById(String userId) {
		return userDao.getUserCodeById(userId);
	}
	
	@Override
	public int getUserExist(int userid, String usercode) {
		return userDao.getUserExist(userid, usercode);
	}

	@Override
	public int updateUserPwd(int userid, String userpwd) {
		return userDao.updateUserPwd(userid, userpwd);
	}

}
