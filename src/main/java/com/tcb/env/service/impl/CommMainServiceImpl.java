package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.ICommMainDao;
import com.tcb.env.model.CommResultModel;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommPrepare;
import com.tcb.env.pojo.CommStatus;
import com.tcb.env.service.ICommMainService;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：计划任务服务接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年6月1日上午8:19:37
 * @since EnvDust 1.0.0
 *
 */
@Service("commMainService")
@Transactional(rollbackFor = Exception.class)
class CommMainServiceImpl implements ICommMainService {

	@Resource
	private ICommMainDao commMainDao;

	@Override
	public int getCommMainCount(CommMain commMain, List<String> listDeviceCode,
			List<String> listCnCode) {
		return commMainDao.getCommMainCount(commMain, listDeviceCode,
				listCnCode);
	}

	@Override
	public List<CommMain> getCommMain(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainDao.getCommMain(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public int insertCommPrepare(CommPrepare commPrepare) {
		return commMainDao.insertCommPrepare(commPrepare);
	}

	@Override
	public int insertCommMain(List<CommMain> listCommMain) {
		return commMainDao.insertCommMain(listCommMain);
	}

	@Override
	public int getCommStatusId(String type, String code) {
		int result = DefaultArgument.INT_DEFAULT;
		CommStatus commStatus = commMainDao.getCommStatus(type, code);
		if (commStatus != null) {
			result = commStatus.getStatusId();
		}
		return result;
	}

	@Override
	public int deleteCommMain(List<Integer> listCommId) {
		return commMainDao.deleteCommMain(listCommId);
	}

	@Override
	public int deleteCommMain(String cnCode, List<String> listDeviceCode) {
		return commMainDao.deleteCommMainCn(cnCode,listDeviceCode);
	}
	
	@Override
	public String getMainCommId(String cnCode, String deviceCode) {
		return commMainDao.getMainCommId(cnCode, deviceCode);
	}

	@Override
	public int getCommResultCount(String commId) {
		return commMainDao.getCommResultCount(commId);
	}
	
	@Override
	public List<CommResultModel> getCommResult(String commId) {
		return commMainDao.getCommResult(commId);
	}

}
