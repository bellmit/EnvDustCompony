package com.tcb.env.gateway;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

public class NetSessionManager {

	private ConcurrentHashMap<String, IoSession> sessionCHM = null;

	public NetSessionManager(int count) {

		sessionCHM = new ConcurrentHashMap<String, IoSession>(count);

	}
}
