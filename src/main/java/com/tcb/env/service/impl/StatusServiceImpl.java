/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IStatusDao;
import com.tcb.env.pojo.Status;
import com.tcb.env.service.IStatusService;

/**
 * <p>[功能描述]：系统状态服务接口实现类</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月28日上午9:11:22
 * @since	EnvDust 1.0.0
 * 
 */
@Service("statusService")
class StatusServiceImpl implements IStatusService{
	@Resource
	private IStatusDao statusDao;

	@Override
	public List<Status> getStatus(String status, String nostatus) {
		return statusDao.getStatus(status, nostatus);
	}

}
