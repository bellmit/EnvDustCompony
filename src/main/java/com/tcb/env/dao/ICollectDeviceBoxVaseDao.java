package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.VaseDataModel;
import com.tcb.env.pojo.CollectDeviceBoxVase;

/**
 * 
 * <p>[功能描述]：超标采样记录</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日上午11:08:30
 * @since	EnvDust 1.0.0
 *
 */
public interface ICollectDeviceBoxVaseDao {
	
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
	int getCollectDeviceBoxVaseCount(@Param("collectDeviceBoxVase")CollectDeviceBoxVase collectDeviceBoxVase);
	
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
	List<CollectDeviceBoxVase> getCollectDeviceBoxVase(@Param("collectDeviceBoxVase")CollectDeviceBoxVase collectDeviceBoxVase);

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
	List<VaseDataModel> getVaseDataModel(
			@Param("deviceCode")String deviceCode,
			@Param("vaseNo")String vaseNo);
	
	
}
