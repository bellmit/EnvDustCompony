package com.tcb.env.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tcb.env.dao.IVideoDeviceDao;
import com.tcb.env.service.IVideoDeviceService;
import com.tcb.env.util.DataSourceContextHolder;

@Service("videoDeviceService")
class VideoDeviceServiceImpl implements IVideoDeviceService {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "VideoDeviceServiceImpl";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(VideoDeviceServiceImpl.class);
	
	@Resource
	private IVideoDeviceDao videoDeviceDao;
	
	@Override
	public String getVideoDeviceIpByMn(String deviceMn) {
		try {
			// 设置数据源
			DataSourceContextHolder.setDbType("dbvideo");
			return videoDeviceDao.getVideoDeviceIpByMn(deviceMn);
		} catch (Exception e) {
			logger.error(LOG + ":获取设备摄像头IP失败，失败信息为：" + e.getMessage());
			return null;
		} finally {
			DataSourceContextHolder.setDbType("db");
		}
	}

}
