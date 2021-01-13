package com.tcb.env.gateway.udp;

import com.tcb.env.gateway.NetSessionManager;
import com.tcb.env.gateway.ProcessorI;
import com.tcb.env.gateway.filter.SplitPacketAbstract;

public class UpdGateWayManager {

	/**
	 * 声明udp服务
	 */
	private UdpService udpService;
	/**
	 * 声明Session
	 */
	private NetSessionManager netSessionManager;
	
	/**
	 * @return the udpService
	 */
	public UdpService getUdpService() {
		return udpService;
	}

	/**
	 * @param udpService the udpService to set
	 */
	public void setUdpService(UdpService udpService) {
		this.udpService = udpService;
	}

	/**
	 * ��ʼ����UDP
	 * 
	 * @param port
	 *            监听端口
	 * @param receiveBufferSize
	 *            接收缓存大小
	 * @param readerIdleTime
	 *            读超时(单位秒)
	 * @param splitPacketAbstract
	 *            UDP不会出现粘包问题，应该不用处理，使用时不用开启此接口
	 * @param executorThreadNum
	 *            mina处理业务逻辑线程数量
	 * @param processor
	 *            实际业务处理接口
	 * @param gateWayName
	 *            网关名称
	 * @param sessionCount
	 *            netSessionManager大小
	 */
	public void startService(int port, int receiveBufferSize,
			int readerIdleTime, SplitPacketAbstract splitPacketAbstract,
			int executorThreadNum, ProcessorI processor, String gateWayName,
			int sessionCount) {
		netSessionManager = new NetSessionManager(sessionCount);
		udpService = new UdpService();
		udpService.init(port, receiveBufferSize, readerIdleTime,
				splitPacketAbstract, executorThreadNum, processor, this);
		udpService.start();
	}

	/**
	 * ֹͣ停止网关
	 * 
	 * @return ־是否关闭成功
	 */
	public boolean stopService() {
		if (udpService.stop()) {
			//TODO 清空netSessionManager
			return true;
		} else {
			return false;
		}
	}
}
