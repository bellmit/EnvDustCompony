package com.tcb.env.gateway.tcp;

import com.tcb.env.gateway.NetSessionManager;
import com.tcb.env.gateway.ProcessorI;
import com.tcb.env.gateway.filter.SplitPacketAbstract;

public class TcpGateWayManager {

	private TcpService tcpService;
	private NetSessionManager netSessionManager;
	private String gateWayName = "Unknow";

	public TcpService getTcpService() {
		return tcpService;
	}

	public void setTcpService(TcpService tcpService) {
		this.tcpService = tcpService;
	}

	public NetSessionManager getNetSessionManager() {
		return netSessionManager;
	}

	public void setNetSessionManager(NetSessionManager netSessionManager) {
		this.netSessionManager = netSessionManager;
	}

	public String getGateWayName() {
		return gateWayName;
	}

	public void setGateWayName(String gateWayName) {
		this.gateWayName = gateWayName;
	}

	public void startService(int port, int backlog, int readerIdleTime,
			SplitPacketAbstract splitPacketAbstract, int executorThreadNum,
			ProcessorI processor, String gateWayName, int sessionCount) {

		netSessionManager = new NetSessionManager(sessionCount);
		tcpService = new TcpService();
		tcpService.init(port, backlog, readerIdleTime, splitPacketAbstract,
				executorThreadNum, processor, this);
		this.gateWayName = gateWayName;
		tcpService.start();
	}
}
