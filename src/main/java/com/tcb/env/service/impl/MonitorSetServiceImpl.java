/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tcb.env.pojo.MonitorSet;
import com.tcb.env.service.IMonitorSetService;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月10日下午9:21:58
 * @since	EnvDust 1.0.0
 * 
 */
@Service("monitorSetService")
@Transactional(rollbackFor = Exception.class)
class MonitorSetServiceImpl implements IMonitorSetService{

	@Override
	public int getCount(MonitorSet monitorSet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MonitorSet> getMonitorSet(MonitorSet monitorSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertMonitorSet(List<MonitorSet> listMonitorSet)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMonitorSet(List<Integer> monitorSetid) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMonitorSet(List<MonitorSet> listMonitorSet)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMonitorSetExist(int monitorSetid, String monitorSetname) {
		// TODO Auto-generated method stub
		return 0;
	}



}
