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

import com.tcb.env.dao.ISysflagDao;
import com.tcb.env.pojo.Sysflag;
import com.tcb.env.service.ISysflagService;

/**
 * <p>
 * [功能描述]：
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年4月8日上午9:46:10
 * @since EnvDust 1.0.0
 * 
 */
@Service("sysflagService")
class SysflagServiceImpl implements ISysflagService {
	@Resource
	private ISysflagDao sysflagDao;

	@Override
	public List<Sysflag> getSysflag(Sysflag sysflag) {
		return sysflagDao.getSysflag(sysflag);
	}

	@Override
	public int getCount(Sysflag sysflag) {

		return sysflagDao.getCount(sysflag);
	}

	@Override
	public int updateSysflag(List<Sysflag> listsysflag) throws Exception {

		return sysflagDao.updateSysflag(listsysflag);
	}

	@Override
	public int getSysflagExist(int sysflagid, String sysflagcode) {

		return sysflagDao.getSysflagExist(sysflagid, sysflagcode);
	}

	@Override
	public String getSysFlagValue(String sysflagcode) {
		String sysFlagValue = null;
		if (sysflagcode != null && !sysflagcode.isEmpty()) {
			Sysflag sysflag = new Sysflag();
			sysflag.setSysFlagCode(sysflagcode);
			List<Sysflag> list = sysflagDao.getSysflag(sysflag);
			if (list != null && list.size() > 0) {
				sysFlagValue = list.get(0).getSysValue();
			}
		}
		return sysFlagValue;
	}

}
