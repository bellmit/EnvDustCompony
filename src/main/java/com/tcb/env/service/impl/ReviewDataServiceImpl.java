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
import com.tcb.env.service.IReviewDataService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：计划上传数据服务接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年6月7日上午9:04:53
 * @since EnvDust 1.0.0
 *
 */
@Service("reviewDataService")
@Transactional(rollbackFor = Exception.class)
class ReviewDataServiceImpl implements IReviewDataService {

	@Resource
	private ICommMainService commMainService;

	@Override
	public int getReviewDataCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMainCount(commMain, listDeviceCode,listCnCode);
	}

	@Override
	public List<CommMain> getReviewData(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMain(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public int insertRldData(String cnCode, String rldExcuteTime,
			List<String> listDeviceCode, int optId) throws Exception {
		int resultCount = 0;
		if (listDeviceCode != null && listDeviceCode.size() > 0
				&& cnCode != null && !cnCode.isEmpty() 
				&& rldExcuteTime != null && !rldExcuteTime.isEmpty()) {
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
						.StringToTimestampSecond(rldExcuteTime));
				commMain.setDevice(device);
				CommStatus commStatus = new CommStatus();
				commStatus.setStatusId(commMainService
						.getCommStatusId("1", "0"));
				commMain.setCommStatus(commStatus);
				commMain.setFlag("1");
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
	public int deleteReviewData(List<Integer> listCommId) throws Exception {
		return commMainService.deleteCommMain(listCommId);
	}

	@Override
	public int insertSpanTimeData(String cnCode, String beginTime,
			String endTime, String stExcuteTime, List<String> listDeviceCode,
			int optId) throws Exception {
		int resultCount = 0;
		if (listDeviceCode != null && listDeviceCode.size() > 0
				&& cnCode != null && !cnCode.isEmpty() && stExcuteTime != null
				&& !stExcuteTime.isEmpty()) {
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
						.StringToTimestampSecond(stExcuteTime));
				commMain.setDevice(device);
				CommStatus commStatus = new CommStatus();
				commStatus.setStatusId(commMainService
						.getCommStatusId("1", "0"));
				commMain.setCommStatus(commStatus);
				commMain.setFlag("1");
				String beginTimeFormat = DateUtil.TimestampToString(DateUtil.StringToTimestampSecond(beginTime), DateUtil.DATA_TIME_SER);
				String endTimeFormat = DateUtil.TimestampToString(DateUtil.StringToTimestampSecond(endTime), DateUtil.DATA_TIME_SER);
				String cp = "BeginTime=" + beginTimeFormat + "," + "EndTime=" + endTimeFormat;
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

}
