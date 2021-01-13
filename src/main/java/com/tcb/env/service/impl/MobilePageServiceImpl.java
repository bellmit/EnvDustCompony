package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IMobilePageDao;
import com.tcb.env.model.MobilePageModel;
import com.tcb.env.service.IMobilePageService;

/**
 * 
 * <p>[功能描述]：移动信息接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年2月2日下午1:44:23
 * @since	EnvDust 1.0.0
 *
 */
@Service("mobilePageService")
class MobilePageServiceImpl implements IMobilePageService {

	@Resource
	private IMobilePageDao mobilePageDao;
	
	@Override
	public List<MobilePageModel> getMobilePage() {
		return mobilePageDao.getMobilePage();
	}

}
