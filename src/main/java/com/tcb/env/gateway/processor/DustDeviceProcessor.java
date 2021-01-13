package com.tcb.env.gateway.processor;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.tcb.env.gateway.ProcessorI;

public class DustDeviceProcessor implements ProcessorI {

	@Override
	public void sessionOpenProcess(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterProcess(IoSession session, byte[] message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeProcess(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeProcess(IoSession session, Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exceptionProcess(IoSession session, Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void idleProcess(IoSession session, IdleStatus status) {
		// TODO Auto-generated method stub

	}

}
