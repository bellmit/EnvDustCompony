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
import com.tcb.env.service.IExtremumRangeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：极值范围数据服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月9日下午2:51:07
 * @since	EnvDust 1.0.0
 *
 */
@Service("extremumRangeService")
@Transactional(rollbackFor = Exception.class)
class ExtremumRangeServiceImpl implements IExtremumRangeService {

	@Resource
	private ICommMainService commMainService;
	
	@Override
	public int getExtremumRangeCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMainCount(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public List<CommMain> getExtremumRange(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMain(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public int insertExtremumRangeSet(String cnCode, String xMaxState,
			String xMinState, String xMax, String xMin, String setExcuteTime,
			List<String> listDeviceCode, int optId) throws Exception {
		int resultCount = 0;
		if (listDeviceCode != null && listDeviceCode.size() > 0
				&& cnCode != null && !cnCode.isEmpty() 
				&& setExcuteTime != null&& !setExcuteTime.isEmpty()) {
			List<CommMain> listCommMain = new ArrayList<CommMain>();
			List<Integer> listCommId = new ArrayList<Integer>();
			for (String devCode : listDeviceCode) {
				String qn = DateUtil.GetSystemDateTime();
				Device device = new Device();
				device.setDeviceCode(devCode);
				CommMain commMain = new CommMain();
				commMain.setSt(DefaultArgument.PRO_ST_WEV);
				commMain.setQn(qn);
				CommCn commCn = new CommCn();
				commCn.setCnCode(cnCode);
				commMain.setCommCn(commCn);
				commMain.setExcuteTime(DateUtil.StringToTimestampSecond(setExcuteTime));
				commMain.setDevice(device);
				CommStatus commStatus = new CommStatus();
				commStatus.setStatusId(commMainService.getCommStatusId("1", "0"));
				commMain.setCommStatus(commStatus);
				commMain.setFlag("1");
				String cp = cnCode.equals("4005") == true ? ("x00003-MaxState=" + xMaxState 
							+ ","+"x00003-MinState=" + xMinState
							+ (xMaxState.equals("1") == true ? (","+"x00003-Max=" + xMax) : "")
							+ (xMinState.equals("1") == true ? (","+"x00003-Min=" + xMin) : "")
							) : "";
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
	public int insertExtremumRangeGet(String cnCode, String getExcuteTime,
			List<String> listDeviceCode, int optId) throws Exception {
		int resultCount = 0;
		if (listDeviceCode != null && listDeviceCode.size() > 0
				&& cnCode != null && !cnCode.isEmpty() 
				&& getExcuteTime != null&& !getExcuteTime.isEmpty()) {
			List<CommMain> listCommMain = new ArrayList<CommMain>();
			List<Integer> listCommId = new ArrayList<Integer>();
			for (String devCode : listDeviceCode) {
				String qn = DateUtil.GetSystemDateTime();
				Device device = new Device();
				device.setDeviceCode(devCode);
				CommMain commMain = new CommMain();
				commMain.setSt(DefaultArgument.PRO_ST_WEV);
				commMain.setQn(qn);
				CommCn commCn = new CommCn();
				commCn.setCnCode(cnCode);
				commMain.setCommCn(commCn);
				commMain.setExcuteTime(DateUtil.StringToTimestampSecond(getExcuteTime));
				commMain.setDevice(device);
				CommStatus commStatus = new CommStatus();
				commStatus.setStatusId(commMainService.getCommStatusId("1", "0"));
				commMain.setCommStatus(commStatus);
				commMain.setFlag("1");
				commMain.setOptUser(optId);
				String commId = commMainService.getMainCommId(cnCode, devCode);
				if (commId != null && !commId.isEmpty()) {
					listCommId.add(Integer.valueOf(commId));
				}
				listCommMain.add(commMain);
				resultCount++;
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
	public int deleteExtremumRange(List<Integer> listCommId) throws Exception {
		return commMainService.deleteCommMain(listCommId);
	}

}
