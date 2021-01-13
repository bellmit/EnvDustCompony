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
import com.tcb.env.service.ITemperatureControlService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>[功能描述]：温度控制数据服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月18日上午10:06:56
 * @since	EnvDust 1.0.0
 *
 */
@Service("temperatureControlService")
@Transactional(rollbackFor = Exception.class)
class TemperatureControlServiceImpl implements ITemperatureControlService {

	@Resource
	private ICommMainService commMainService;
	
	@Override
	public int getTemperatureControlCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMainCount(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public List<CommMain> getTemperatureControl(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode) {
		return commMainService.getCommMain(commMain, listDeviceCode, listCnCode);
	}

	@Override
	public int insertTemperatureControlSet(String cnCode, String xTpCtrlEn,
			String xTpCtrl, String setExcuteTime, List<String> listDeviceCode,
			int optId) throws Exception {
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
				String cp = cnCode.equals("4003") == true ? ("x00004-TpCtrlEn=" + xTpCtrlEn
							+ (xTpCtrlEn.equals("1") == true ? (","+"x00004-TpCtrl=" + xTpCtrl) : "")
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
	public int insertTemperatureControlGet(String cnCode, String getExcuteTime,
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
	public int deleteTemperatureControl(List<Integer> listCommId)
			throws Exception {
		return commMainService.deleteCommMain(listCommId);
	}

}
