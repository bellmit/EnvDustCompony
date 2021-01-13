package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.ISystemDao;
import com.tcb.env.model.SystemModel;
import com.tcb.env.service.ISystemService;

/**
 * 
 * <p>[功能描述]：系统版本参数接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月6日下午2:27:23
 * @since	EnvDust 1.0.0
 *
 */
@Service("systemService")
class SystemServiceImpl implements ISystemService {

	@Resource
	private ISystemDao systemDao;
	
	@Override
	public List<SystemModel> getNewSystem(String sysCode) {
		return systemDao.getNewSystem(sysCode);
	}

}
