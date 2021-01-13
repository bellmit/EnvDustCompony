package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.CollectDevice;

/**
 * 
 * <p>[功能描述]：超标采样设备Dao</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年7月23日上午9:35:19
 * @since	EnvDust 1.0.0
 *
 */
public interface ICollectDeviceDao {
	
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
	int getCollectDeviceCount(@Param("collectDevice")CollectDevice collectDevice);
	
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
	List<CollectDevice> getCollectDevice(@Param("collectDevice")CollectDevice collectDevice);
	
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
	int insertCollectDevice(@Param("collectDevice")CollectDevice collectDevice);
	
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
	int updateCollectDevice(@Param("collectDevice")CollectDevice collectDevice);
	
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
	int deleteCollectDevice(@Param("cdIdList")List<String> cdIdList);

}
