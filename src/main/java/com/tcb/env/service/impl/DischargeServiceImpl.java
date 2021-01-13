package com.tcb.env.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.dao.IDischargeDao;
import com.tcb.env.model.DischargeModel;
import com.tcb.env.service.IDischargeService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.SortListUtil;

/**
 * 
 * <p>[功能描述]：设备排放量接口实现类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年10月16日上午11:04:25
 * @since	envdust 1.0.0
 *
 */
@Service("dischargeService")
class DischargeServiceImpl implements IDischargeService {
	
	@Resource
	private IDischargeDao dischargeDao;
	
	/**
	 * 配置文件服务类
	 */
	@Resource
	private Dom4jConfig dom4jConfig;

	@Override
	public List<DischargeModel> getDeviceHourDischarge(
			List<String> deviceCodeList, String thingCode,Timestamp selectTime) {
		List<DischargeModel> dischargeModelList = new ArrayList<DischargeModel>();
		if(deviceCodeList != null && deviceCodeList.size()>0){
			String dbName = dom4jConfig.getDataBaseConfig().getDbName();
			String dbOldName = "";
			if(!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)){
				dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
			}
			for (String deviceCode : deviceCodeList) {
				List<DischargeModel> dmtList = dischargeDao.getDeviceHourDischarge(dbName, dbOldName, deviceCode, thingCode, selectTime);
				if(dmtList != null && dmtList.size()>0){
					dischargeModelList.addAll(dmtList);
				}
			}
			//排序
			SortListUtil<DischargeModel> sortList = new SortListUtil<DischargeModel>();
			sortList.sortDouble(dischargeModelList,"convertThingCouDouble", "desc");
			//追加合计
			DischargeModel dischargeModel = new DischargeModel();
			dischargeModel.setDeviceName("合计");
			double allCou = 0;
			double allZsCou = 0;
			for (DischargeModel temp : dischargeModelList) {
				allCou += temp.convertThingCouDouble();
				allZsCou += temp.convertThingZsCouDouble();
			}
			dischargeModel.setThingCou(String.format("%.6f", allCou));
			dischargeModel.setThingZsCou(String.format("%.6f", allZsCou));
			dischargeModelList.add(dischargeModel);
		}
		return dischargeModelList;
	}

	@Override
	public List<DischargeModel> getDeviceDayDischarge(
			List<String> deviceCodeList, String thingCode,Timestamp selectTime) {
		List<DischargeModel> dischargeModelList = new ArrayList<DischargeModel>();
		if(deviceCodeList != null && deviceCodeList.size()>0){
			String dbName = dom4jConfig.getDataBaseConfig().getDbName();
			String dbOldName = "";
			if(!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)){
				dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
			}
			for (String deviceCode : deviceCodeList) {
				List<DischargeModel> dmtList = dischargeDao.getDeviceDayDischarge(dbName, dbOldName, deviceCode, thingCode, selectTime);
				if(dmtList != null && dmtList.size()>0){
					dischargeModelList.addAll(dmtList);
				}
			}
			//排序
			SortListUtil<DischargeModel> sortList = new SortListUtil<DischargeModel>();
			sortList.sortDouble(dischargeModelList,"convertThingCouDouble", "desc");
			//追加合计
			DischargeModel dischargeModel = new DischargeModel();
			dischargeModel.setDeviceName("合计");
			double allCou = 0;
			double allZsCou = 0;
			for (DischargeModel temp : dischargeModelList) {
				allCou += temp.convertThingCouDouble();
				allZsCou += temp.convertThingZsCouDouble();
			}
			dischargeModel.setThingCou(String.format("%.6f", allCou));
			dischargeModel.setThingZsCou(String.format("%.6f", allZsCou));
			dischargeModelList.add(dischargeModel);
		}
		return dischargeModelList;
	}

	@Override
	public List<DischargeModel> getDeviceMonthDischarge(
			List<String> deviceCodeList, String thingCode,Timestamp selectTime) {
		List<DischargeModel> dischargeModelList = new ArrayList<DischargeModel>();
		if(deviceCodeList != null && deviceCodeList.size()>0){
			String dbName = dom4jConfig.getDataBaseConfig().getDbName();
			String dbOldName = "";
			if(!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)){
				dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
			}
			for (String deviceCode : deviceCodeList) {
				List<DischargeModel> dmtList = dischargeDao.getDeviceMonthDischarge(dbName, dbOldName, deviceCode, thingCode, selectTime);
				if(dmtList != null && dmtList.size()>0){
					dischargeModelList.addAll(dmtList);
				}
			}
			//排序
			SortListUtil<DischargeModel> sortList = new SortListUtil<DischargeModel>();
			sortList.sortDouble(dischargeModelList,"convertThingCouDouble", "desc");
			//追加合计
			DischargeModel dischargeModel = new DischargeModel();
			dischargeModel.setDeviceName("合计");
			double allCou = 0;
			double allZsCou = 0;
			for (DischargeModel temp : dischargeModelList) {
				allCou += temp.convertThingCouDouble();
				allZsCou += temp.convertThingZsCouDouble();
			}
			dischargeModel.setThingCou(String.format("%.6f", allCou));
			dischargeModel.setThingZsCou(String.format("%.6f", allZsCou));
			dischargeModelList.add(dischargeModel);
		}
		return dischargeModelList;
	}

	@Override
	public List<DischargeModel> getDeviceYearDischarge(
			List<String> deviceCodeList, String thingCode,Timestamp selectTime) {
		List<DischargeModel> dischargeModelList = new ArrayList<DischargeModel>();
		if(deviceCodeList != null && deviceCodeList.size()>0){
			String dbName = dom4jConfig.getDataBaseConfig().getDbName();
			String dbOldName = "";
			if(!DateUtil.isRecentlyData(selectTime, DefaultArgument.RECENT_DAYS)){
				dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
			}
			for (String deviceCode : deviceCodeList) {
				List<DischargeModel> dmtList = dischargeDao.getDeviceYearDischarge(dbName, dbOldName, deviceCode, thingCode, selectTime);
				if(dmtList != null && dmtList.size()>0){
					dischargeModelList.addAll(dmtList);
				}
			}
			//排序
			SortListUtil<DischargeModel> sortList = new SortListUtil<DischargeModel>();
			sortList.sortDouble(dischargeModelList,"convertThingCouDouble", "desc");
			//追加合计
			DischargeModel dischargeModel = new DischargeModel();
			dischargeModel.setDeviceName("合计");
			double allCou = 0;
			double allZsCou = 0;
			for (DischargeModel temp : dischargeModelList) {
				allCou += temp.convertThingCouDouble();
				allZsCou += temp.convertThingZsCouDouble();
			}
			dischargeModel.setThingCou(String.format("%.6f", allCou));
			dischargeModel.setThingZsCou(String.format("%.6f", allZsCou));
			dischargeModelList.add(dischargeModel);
		}
		return dischargeModelList;
	}
	
	@Override
	public List<DischargeModel> getDeviceTimesDischarge(
			List<String> deviceCodeList, String thingCode,Timestamp beginTime,Timestamp endTime) {
		List<DischargeModel> dischargeModelList = new ArrayList<DischargeModel>();
		if(deviceCodeList != null && deviceCodeList.size()>0){
			String dbName = dom4jConfig.getDataBaseConfig().getDbName();
			String dbOldName = "";
			if(!DateUtil.isRecentlyData(beginTime, DefaultArgument.RECENT_DAYS)){
				dbOldName = dom4jConfig.getDataBaseConfig().getDbOldName();
			}
			for (String deviceCode : deviceCodeList) {
				List<DischargeModel> dmtList = dischargeDao.getDeviceTimesDischarge(dbName, dbOldName, deviceCode, thingCode, beginTime,endTime);
				if(dmtList != null && dmtList.size()>0){
					dischargeModelList.addAll(dmtList);
				}
			}
			//排序
			SortListUtil<DischargeModel> sortList = new SortListUtil<DischargeModel>();
			sortList.sortDouble(dischargeModelList,"convertThingCouDouble", "desc");
			//追加合计
			DischargeModel dischargeModel = new DischargeModel();
			dischargeModel.setDeviceName("合计");
			double allCou = 0;
			double allZsCou = 0;
			for (DischargeModel temp : dischargeModelList) {
				allCou += temp.convertThingCouDouble();
				allZsCou += temp.convertThingZsCouDouble();
			}
			dischargeModel.setThingCou(String.format("%.6f", allCou));
			dischargeModel.setThingZsCou(String.format("%.6f", allZsCou));
			dischargeModelList.add(dischargeModel);
		}
		return dischargeModelList;
	}
	
}
