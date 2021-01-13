package com.tcb.env.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IAccessDeviceDao;
import com.tcb.env.dao.IAccessMonitorDao;
import com.tcb.env.pojo.Device;
import com.tcb.env.service.IAccessOperatorService;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：权限组访问数据操作接口实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年4月6日上午9:34:43
 * @since EnvDust 1.0.0
 *
 */
@Service("accessOperator")
@Transactional(rollbackFor = Exception.class)
class AccessOperatorServiceImpl implements IAccessOperatorService {

	@Resource
	private IAccessDeviceDao accessDeviceDao;
	@Resource
	private IAccessMonitorDao accessMonitorDao;

	@Override
	public int insertAccessDevice(String ahrCode, List<String> listDevCode,
			int optUser) throws Exception {
		return accessDeviceDao
				.insertAccessDevice(ahrCode, listDevCode, optUser);
	}

	@Override
	public int deleteAccessDevice(List<String> listAhrCode) throws Exception {
		return accessDeviceDao.deleteAccessDevice(listAhrCode);
	}

	@Override
	public int insertAccessMonitor(String ahrCode, List<String> listMonCode,
			int optUser) throws Exception {
		return accessMonitorDao.insertAccessMonitor(ahrCode, listMonCode,
				optUser);
	}

	@Override
	public int deleteAccessMonitor(List<String> listAhrCode) throws Exception {
		return accessMonitorDao.deleteAccessMonitor(listAhrCode);
	}

	@Override
	public void updateAccessDevice(String ahrCode, List<String> listDevCode,
			int optUser) throws Exception {
		List<String> listAhrCode = new ArrayList<String>();
		listAhrCode.add(ahrCode);
		accessDeviceDao.deleteAccessDevice(listAhrCode);
		if (listDevCode != null && listDevCode.size() > 0) {
			accessDeviceDao.insertAccessDevice(ahrCode, listDevCode, optUser);
		}
	}

	@Override
	public void updateAccessMonitor(String ahrCode, List<String> listMonCode,
			int optUser) throws Exception {
		List<String> listAhrCode = new ArrayList<String>();
		listAhrCode.add(ahrCode);
		accessMonitorDao.deleteAccessMonitor(listAhrCode);
		if (listMonCode != null && listMonCode.size() > 0) {
			accessMonitorDao.insertAccessMonitor(ahrCode, listMonCode, optUser);
		}
	}

	@Override
	public int getAhrDeviceCount(Device device, String ahrCode, String flag) {
		return accessDeviceDao.getAhrDeviceCount(device, ahrCode, flag);
	}

	@Override
	public List<Device> getAhrDevice(Device device, String ahrCode, String flag) {
		return accessDeviceDao.getAhrDevice(device, ahrCode, flag);
	}

	@Override
	public int getJudgeAhrDeviceCount(Device device, String ahrCode) {
		int count = DefaultArgument.INT_DEFAULT;
		if (device != null) {
			String flag = device.getHaveAhr();
			if (flag == null || flag.isEmpty()) {
				count = accessDeviceDao.getAhrDeviceCount(device, ahrCode,
						DefaultArgument.IS_AHR_DEVICE)
						+ accessDeviceDao.getAhrDeviceCount(device, ahrCode,
								DefaultArgument.NOT_AHR_DEVICE);
			} else {
				count = accessDeviceDao
						.getAhrDeviceCount(device, ahrCode, flag);
			}
		}
		return count;
	}

	@Override
	public List<Device> getJudgeAhrDevice(Device device, String ahrCode) {
		List<Device> listAll = new ArrayList<Device>();
		if (device != null) {
			String flag = device.getHaveAhr();
			List<Device> listNotAhr = new ArrayList<Device>();
			List<Device> listAhr = new ArrayList<Device>();
			if (flag == null || flag.isEmpty()) {
				listNotAhr = accessDeviceDao.getAhrDevice(device, ahrCode,
						DefaultArgument.IS_AHR_DEVICE);
				if (device.getRowCount() > listNotAhr.size()) {
					device.setRowIndex(0);
					device.setRowCount(device.getRowCount() - listNotAhr.size());
					listAhr = accessDeviceDao.getAhrDevice(device, ahrCode,
							DefaultArgument.NOT_AHR_DEVICE);
				}
			} else {
				listNotAhr = accessDeviceDao
						.getAhrDevice(device, ahrCode, flag);
			}
			if (listNotAhr != null && listNotAhr.size() > 0) {
				listAll.addAll(listNotAhr);
			}
			if (listAhr != null && listAhr.size() > 0) {
				listAll.addAll(listAhr);
			}
		}
		return listAll;
	}

	@Override
	public int deleteAccessDeviceSingle(String ahrCode, List<String> listDevCode) {
		return accessDeviceDao.deleteAccessDeviceSingle(ahrCode, listDevCode);
	}

}
