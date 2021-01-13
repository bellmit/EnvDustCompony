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
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IOranizationDao;
import com.tcb.env.pojo.Oranization;
import com.tcb.env.service.IOranizationService;

/**
 * <p>
 * [功能描述]：组织服务接口实现类
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年3月17日下午3:32:30
 * @since EnvDust 1.0.0
 * 
 */
@Service("oranizationService")
@Transactional(rollbackFor = Exception.class)
class OranizationServiceImpl implements IOranizationService {
	@Resource
	private IOranizationDao oranizationDao;

	@Override
	public int insertOranization(Oranization oranization)
			throws Exception {

		return oranizationDao.insertOranization(oranization);
	}

	@Override
	public int deleteOranization(List<Integer> oranizationid) throws Exception {

		return oranizationDao.deleteOranization(oranizationid);
	}

	@Override
	public int updateOranization(List<Oranization> listoranization)
			throws Exception {

		return oranizationDao.updateOranization(listoranization);
	}

	@Override
	public int getCount(Oranization oranization, boolean isLike) {

		return oranizationDao.getCount(oranization, isLike);
	}

	@Override
	public List<Oranization> getOranization(Oranization oranization) {

		return oranizationDao.getOranization(oranization);
	}

	@Override
	public int getOranizationExist(int oranizationid, String oranizationname) {

		return oranizationDao.getOranizationExist(oranizationid,
				oranizationname);
	}

	@Override
	public String getOranizationPathById(int orgid) {

		return oranizationDao.getOranizationPathById(orgid);
	}

}
