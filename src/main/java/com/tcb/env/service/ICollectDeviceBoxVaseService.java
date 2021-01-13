package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.VaseDataModel;
import com.tcb.env.pojo.CollectDeviceBoxVase;
import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>[功能描述]：采样记录服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月26日下午3:50:04
 * @since	EnvDust 1.0.0
 *
 */
public interface ICollectDeviceBoxVaseService {
	
	/**
	 * 
	 * <p>[功能描述]：获取超标采样记录个数</p>
	 * 
	 * @author	王垒, 2018年7月23日上午11:09:59
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxVase
	 * @return
	 */
	int getCollectDeviceBoxVaseCount(CollectDeviceBoxVase collectDeviceBoxVase);
	
	/**
	 * 
	 * <p>[功能描述]：获取超标采样记录信息</p>
	 * 
	 * @author	王垒, 2018年7月23日上午11:10:55
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDeviceBoxVase
	 * @return
	 */
	List<CollectDeviceBoxVase> getCollectDeviceBoxVase(CollectDeviceBoxVase collectDeviceBoxVase);
	
	/**
	 * 
	 * <p>[功能描述]：插入采样指令</p>
	 * 
	 * @author	王垒, 2018年7月26日下午5:25:21
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param cdCode
	 * @param setExcuteTime
	 * @param userid
	 * @return
	 */
	int insertNetSample(String cnCode, String cdCode, String setExcuteTime, int userid);
	
	/**
	 * 
	 * <p>[功能描述]：查询采样指令计划个数</p>
	 * 
	 * @author	王垒, 2018年7月27日上午11:18:25
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	int getNetSampleCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：查询采样指令计划信息</p>
	 * 
	 * @author	王垒, 2018年7月27日上午11:18:25
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	List<CommMain> getNetSample(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);
	
	/**
	 * 
	 * <p>[功能描述]：获取采样详细数据</p>
	 * 
	 * @author	王垒, 2018年7月30日上午10:55:36
	 * @since	EnvDust 1.0.0
	 *
	 * @param deviceCode
	 * @param vaseNo
	 * @return
	 */
	List<VaseDataModel> getVaseDataModel(String deviceCode,String vaseNo);

}
