package com.tcb.env.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * <p>[功能描述]：视频数据库操作接口</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2016年11月22日下午2:55:46
 * @since	EnvDust 1.0.0
 *
 */
public interface IVideoDeviceDao {

	/**
	 * 
	 * <p>[功能描述]：获取设备摄像头IP</p>
	 * 
	 * @author	王垒, 2016年11月22日下午2:58:21
	 * @since	EnvDust 1.0.0
	 *
	 * @param deviceMn:设备MN号
	 * @return
	 */
	public String getVideoDeviceIpByMn(@Param("deviceMn") String deviceMn);
	
}
