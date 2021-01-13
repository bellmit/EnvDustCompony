package com.tcb.env.service.impl;

import com.tcb.env.service.IUdpService;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.gateway.filter.DefaultSplitPacket;
import com.tcb.env.gateway.processor.DustDeviceProcessor;
import com.tcb.env.gateway.udp.UpdGateWayManager;

/**
 * 
 * <p>
 * [功能描述]：Udp网关服务实现类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年3月22日下午1:59:53
 * @since EnvDust 1.0.0
 *
 */
class UdpServiceImpl implements IUdpService {

	private UpdGateWayManager updGateWayManager;
	private Dom4jConfig dom4jConfig;
	private DustDeviceProcessor dustDeviceProcessor;

	/**
	 * @return the updGateWayManager
	 */
	public UpdGateWayManager getUpdGateWayManager() {
		return updGateWayManager;
	}

	/**
	 * @param updGateWayManager the updGateWayManager to set
	 */
	public void setUpdGateWayManager(UpdGateWayManager updGateWayManager) {
		this.updGateWayManager = updGateWayManager;
	}

	/**
	 * @return the dom4jConfig
	 */
	public Dom4jConfig getDom4jConfig() {
		return dom4jConfig;
	}

	/**
	 * @param dom4jConfig the dom4jConfig to set
	 */
	public void setDom4jConfig(Dom4jConfig dom4jConfig) {
		this.dom4jConfig = dom4jConfig;
	}

	/**
	 * @return the dustDeviceProcessor
	 */
	public DustDeviceProcessor getDustDeviceProcessor() {
		return dustDeviceProcessor;
	}

	/**
	 * @param dustDeviceProcessor the dustDeviceProcessor to set
	 */
	public void setDustDeviceProcessor(DustDeviceProcessor dustDeviceProcessor) {
		this.dustDeviceProcessor = dustDeviceProcessor;
	}

	@Override
	public void startUdp() {
		// 开启默认设备网关
		dom4jConfig.init();
		updGateWayManager.startService(dom4jConfig.getDeDevConfig().getPort(),
				dom4jConfig.getDeDevConfig().getReceiveBufferSize(),
				dom4jConfig.getDeDevConfig().getReaderIdleTime(),
				new DefaultSplitPacket(), dom4jConfig.getDeDevConfig()
						.getExecutorThreadNum(), dustDeviceProcessor, dom4jConfig
						.getDeDevConfig().getGateWayName(), dom4jConfig
						.getDeDevConfig().getSessionCount());
	}

}
