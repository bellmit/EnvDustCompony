package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.ICollectDeviceBoxDao;
import com.tcb.env.pojo.CollectDeviceBox;
import com.tcb.env.service.ICollectDeviceBoxService;

/**
 * 
 * <p>[功能描述]：超标采样箱子服务接口服务类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月24日下午1:50:19
 * @since	EnvDust 1.0.0
 *
 */
@Service("collectDeviceBoxService")
class CollectDeviceBoxServiceImpl implements ICollectDeviceBoxService {

	@Resource
	private ICollectDeviceBoxDao collectDeviceBoxDao;
	
	@Override
	public int getCollectDeviceBoxCount(CollectDeviceBox collectDeviceBox) {
		return collectDeviceBoxDao.getCollectDeviceBoxCount(collectDeviceBox);
	}

	@Override
	public List<CollectDeviceBox> getCollectDeviceBox(
			CollectDeviceBox collectDeviceBox) {
		return collectDeviceBoxDao.getCollectDeviceBox(collectDeviceBox);
	}

	@Override
	public int insertCollectDeviceBox(CollectDeviceBox collectDeviceBox) {
		return collectDeviceBoxDao.insertCollectDeviceBox(collectDeviceBox);
	}

	@Override
	public int updateCollectDeviceBox(CollectDeviceBox collectDeviceBox) {
		return collectDeviceBoxDao.updateCollectDeviceBox(collectDeviceBox);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteCollectDeviceBox(List<String> boxIdList) throws Exception {
		return collectDeviceBoxDao.deleteCollectDeviceBox(boxIdList);
	}

	@Override
	public int getSampleBoxExist(String boxId, String boxNo, String cdId) {
		// TODO Auto-generated method stub
		return collectDeviceBoxDao.getSampleBoxExist(boxId, boxNo, cdId);
	}

}
