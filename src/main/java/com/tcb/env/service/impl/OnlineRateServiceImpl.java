package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IOnlineRateDao;
import com.tcb.env.pojo.OnlineRate;
import com.tcb.env.service.IOnlineRateService;

/**
 * 
 * <p>[功能描述]：在线率服务接口实现类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月29日上午11:21:03
 * @since	envdust 1.0.0
 *
 */
@Service("onlineRateServiceImpl")
class OnlineRateServiceImpl implements IOnlineRateService {

	@Resource
	private IOnlineRateDao onlineRateDao;
	
	@Override
	public int getHourOnlineRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return onlineRateDao.getHourOnlineRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<OnlineRate> getHourOnlineRate(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime, int rowIndex, int rowCount) {
		return onlineRateDao.getHourOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

	@Override
	public int getDayOnlineRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return onlineRateDao.getDayOnlineRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<OnlineRate> getDayOnlineRate(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime, int rowIndex, int rowCount) {
		return onlineRateDao.getDayOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

	@Override
	public int getMonthOnlineRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return onlineRateDao.getMonthOnlineRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<OnlineRate> getMonthOnlineRate(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime, int rowIndex, int rowCount) {
		return onlineRateDao.getMonthOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

	@Override
	public int getYearOnlineRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return onlineRateDao.getYearOnlineRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<OnlineRate> getYearOnlineRate(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime, int rowIndex, int rowCount) {
		return onlineRateDao.getYearOnlineRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

}
