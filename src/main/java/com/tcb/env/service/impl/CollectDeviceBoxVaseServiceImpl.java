package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.ICollectDeviceBoxVaseDao;
import com.tcb.env.dao.IDeviceDao;
import com.tcb.env.model.VaseDataModel;
import com.tcb.env.pojo.CollectDeviceBoxVase;
import com.tcb.env.pojo.CommCn;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommStatus;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.ICollectDeviceBoxVaseService;
import com.tcb.env.service.ICommMainService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：采样记录服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月26日下午3:52:05
 * @since	EnvDust 1.0.0
 *
 */
@Service("collectDeviceBoxVaseService")
class CollectDeviceBoxVaseServiceImpl implements
		ICollectDeviceBoxVaseService {

	@Resource
	private ICollectDeviceBoxVaseDao collectDeviceBoxVaseDao;
	
	@Resource
	private IDeviceDao deviceDao;
	
	@Resource
	private ICommMainService commMainService;
	
	@Override
	public int getCollectDeviceBoxVaseCount(
			CollectDeviceBoxVase collectDeviceBoxVase) {
		return collectDeviceBoxVaseDao.getCollectDeviceBoxVaseCount(collectDeviceBoxVase);
	}

	@Override
	public List<CollectDeviceBoxVase> getCollectDeviceBoxVase(
			CollectDeviceBoxVase collectDeviceBoxVase) {
		return collectDeviceBoxVaseDao.getCollectDeviceBoxVase(collectDeviceBoxVase);
	}

	@Override
	public int insertNetSample(String cnCode,String cdCode,
			String setExcuteTime, int userid) {
		int resultCount = 0;
		if (cdCode != null && !cdCode.isEmpty()
				&& cnCode != null && !cnCode.isEmpty() 
				&& setExcuteTime != null&& !setExcuteTime.isEmpty()) {
			List<CommMain> listCommMain = new ArrayList<CommMain>();
			List<Integer> listCommId = new ArrayList<Integer>();
			String qn = DateUtil.GetSystemDateTime();
			Device device = new Device();
			device.setDeviceCode(cdCode);
			CommMain commMain = new CommMain();
			commMain.setSt(DefaultArgument.PRO_ST_ATM);
			commMain.setQn(qn);
			CommCn commCn = new CommCn();
			commCn.setCnCode(cnCode);
			commMain.setCommCn(commCn);
			commMain.setExcuteTime(DateUtil.StringToTimestampSecond(setExcuteTime));
			commMain.setDevice(device);
			CommStatus commStatus = new CommStatus();
			commStatus.setStatusId(commMainService.getCommStatusId("1", "0"));
			commMain.setCommStatus(commStatus);
			commMain.setFlag("5");
			commMain.setOptUser(userid);
			String commId = commMainService.getMainCommId(cnCode, cdCode);
			if (commId != null && !commId.isEmpty()) {
				listCommId.add(Integer.valueOf(commId));
			}
			listCommMain.add(commMain);
			// 进行数据库操作
			if (listCommMain != null && listCommMain.size()>0) {
				if (listCommId != null && listCommId.size() > 0) {
					commMainService.deleteCommMain(listCommId);
				}
				resultCount = commMainService.insertCommMain(listCommMain);
			}
		}
		return resultCount;
	}

	@Override
	public int getNetSampleCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMainCount(commMain, listDeviceCode,listCnCode);
	}

	@Override
	public List<CommMain> getNetSample(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMain(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public List<VaseDataModel> getVaseDataModel(String deviceCode, String vaseNo) {
		return collectDeviceBoxVaseDao.getVaseDataModel(deviceCode, vaseNo);
	}

}
