package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.pojo.CommCn;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommStatus;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.ICommMainService;
import com.tcb.env.service.IDataIntervalService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：设置数据间隔接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年6月6日上午9:15:32
 * @since EnvDust 1.0.0
 *
 */
@Service("dataIntervalService")
@Transactional(rollbackFor = Exception.class)
class DataIntervalServiceImpl implements IDataIntervalService {

	@Resource
	private ICommMainService commMainService;

	@Override
	public int getDataIntervalCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMainCount(commMain, listDeviceCode,
				listCnCode);
	}

	@Override
	public List<CommMain> getDataInterval(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMain(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public int insertDataInterval(String cnCode, String riInterval,
			String riExcuteTime, List<String> listDeviceCode, int optId)
			throws Exception {
		int resultCount = 0;
		if (listDeviceCode != null && listDeviceCode.size() > 0
				&& cnCode != null && !cnCode.isEmpty() && riInterval != null
				&& !riInterval.isEmpty() && riExcuteTime != null
				&& !riExcuteTime.isEmpty()) {
			List<CommMain> listCommMain = new ArrayList<CommMain>();
			List<Integer> listCommId = new ArrayList<Integer>();
			for (String devCode : listDeviceCode) {
				String qn = DateUtil.GetSystemDateTime();
				Device device = new Device();
				device.setDeviceCode(devCode);
				CommMain commMain = new CommMain();
				commMain.setSt(DefaultArgument.PRO_ST_SYSTEM);
				commMain.setQn(qn);
				CommCn commCn = new CommCn();
				commCn.setCnCode(cnCode);
				commMain.setCommCn(commCn);
				commMain.setExcuteTime(DateUtil
						.StringToTimestampSecond(riExcuteTime));
				commMain.setDevice(device);
				CommStatus commStatus = new CommStatus();
				commStatus.setStatusId(commMainService
						.getCommStatusId("1", "0"));
				commMain.setCommStatus(commStatus);
				commMain.setFlag("1");
				String cp = "Value=" + riInterval;
				commMain.setCp(cp);
				commMain.setOptUser(optId);
				String commId = commMainService.getMainCommId(cnCode, devCode);
				if (commId != null && !commId.isEmpty()) {
					listCommId.add(Integer.valueOf(commId));
				}
				listCommMain.add(commMain);
				resultCount++;
				Thread.sleep(1);//防止重复
			}
			// 进行数据库操作
			if (resultCount > 0) {
				if (listCommId != null && listCommId.size() > 0) {
					commMainService.deleteCommMain(listCommId);
				}
				resultCount = commMainService.insertCommMain(listCommMain);
			}
		}
		return resultCount;
	}

	@Override
	public int deleteDataInterval(List<Integer> listCommId)
			throws Exception {
		return commMainService.deleteCommMain(listCommId);
	}

}
