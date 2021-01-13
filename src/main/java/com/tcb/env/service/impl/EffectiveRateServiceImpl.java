package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IEffectiveRateDao;
import com.tcb.env.pojo.EffectiveRate;
import com.tcb.env.service.IEffectiveRateService;

/**
 * 
 * <p>[功能描述]：有效率服务接口实现类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年9月29日上午11:19:56
 * @since	envdust 1.0.0
 *
 */
@Service("effectiveRateService")
class EffectiveRateServiceImpl implements IEffectiveRateService {

	@Resource
	private IEffectiveRateDao effectiveRateDao;
	
	@Override
	public int getHourEffectiveRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return effectiveRateDao.getHourEffectiveRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<EffectiveRate> getHourEffectiveRate(
			List<String> deviceCodeList, Timestamp beginTime,
			Timestamp endTime, int rowIndex, int rowCount) {
		return effectiveRateDao.getHourEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

	@Override
	public int getDayEffectiveRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return effectiveRateDao.getDayEffectiveRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<EffectiveRate> getDayEffectiveRate(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime, int rowIndex, int rowCount) {
		return effectiveRateDao.getDayEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

	@Override
	public int getMonthEffectiveRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return effectiveRateDao.getMonthEffectiveRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<EffectiveRate> getMonthEffectiveRate(
			List<String> deviceCodeList, Timestamp beginTime,
			Timestamp endTime, int rowIndex, int rowCount) {
		return effectiveRateDao.getMonthEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

	@Override
	public int getYearEffectiveRateCount(List<String> deviceCodeList,
			Timestamp beginTime, Timestamp endTime) {
		return effectiveRateDao.getYearEffectiveRateCount(deviceCodeList, beginTime, endTime);
	}

	@Override
	public List<EffectiveRate> getYearEffectiveRate(
			List<String> deviceCodeList, Timestamp beginTime,
			Timestamp endTime, int rowIndex, int rowCount) {
		return effectiveRateDao.getYearEffectiveRate(deviceCodeList, beginTime, endTime, rowIndex, rowCount);
	}

}
