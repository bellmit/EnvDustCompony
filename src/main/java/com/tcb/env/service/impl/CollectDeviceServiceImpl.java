package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.ICollectDeviceDao;
import com.tcb.env.pojo.CollectDevice;
import com.tcb.env.service.ICollectDeviceService;

/**
 * 
 * <p>[功能描述]：超标采样设备服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日下午2:04:09
 * @since	EnvDust 1.0.0
 *
 */
@Service("collectDeviceService")
class CollectDeviceServiceImpl implements ICollectDeviceService {

	@Resource
	private ICollectDeviceDao collectDeviceDao;
	
	@Override
	public int getCollectDeviceCount(CollectDevice collectDevice) {
		return collectDeviceDao.getCollectDeviceCount(collectDevice);
	}

	@Override
	public List<CollectDevice> getCollectDevice(CollectDevice collectDevice) {
		return collectDeviceDao.getCollectDevice(collectDevice);
	}

	@Override
	public int insertCollectDevice(CollectDevice collectDevice) {
		return collectDeviceDao.insertCollectDevice(collectDevice);
	}

	@Override
	public int updateCollectDevice(CollectDevice collectDevice) {
		return collectDeviceDao.updateCollectDevice(collectDevice);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteCollectDevice(List<String> cdIdList) throws Exception {
		return collectDeviceDao.deleteCollectDevice(cdIdList);
	}

}
