package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.CollectDevice;

/**
 * 
 * <p>[功能描述]：超标采样设备服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日下午1:48:31
 * @since	EnvDust 1.0.0
 *
 */
public interface ICollectDeviceService {
	
	/**
	 * 
	 * <p>[功能描述]：获取超标采样设备个数</p>
	 * 
	 * @author	王垒, 2018年7月23日上午9:37:43
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDevice
	 * @return
	 */
	int getCollectDeviceCount(CollectDevice collectDevice);
	
	/**
	 * 
	 * <p>[功能描述]：获取超标采样设备信息</p>
	 * 
	 * @author	王垒, 2018年7月23日上午9:37:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDevice
	 * @return
	 */
	List<CollectDevice> getCollectDevice(CollectDevice collectDevice);
	
	/**
	 * 
	 * <p>[功能描述]：新增超标采样设备</p>
	 * 
	 * @author	王垒, 2018年7月23日上午9:38:57
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDevice
	 * @return
	 */
	int insertCollectDevice(CollectDevice collectDevice);
	
	/**
	 * 
	 * <p>[功能描述]：更新超标采样设备</p>
	 * 
	 * @author	王垒, 2018年7月23日上午9:40:04
	 * @since	EnvDust 1.0.0
	 *
	 * @param collectDevice
	 * @return
	 */
	int updateCollectDevice(CollectDevice collectDevice);
	
	/**
	 * 
	 * <p>[功能描述]：删除超标采样设备</p>
	 * 
	 * @author	王垒, 2018年7月23日上午9:40:04
	 * @since	EnvDust 1.0.0
	 *
	 * @param cdIdList
	 * @return
	 */
	int deleteCollectDevice(List<String> cdIdList) throws Exception;

}
