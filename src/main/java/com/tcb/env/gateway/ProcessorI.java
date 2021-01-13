package com.tcb.env.gateway;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public interface ProcessorI {
	
	public void sessionOpenProcess(IoSession session);
	
	public void afterProcess(IoSession session,byte[] message);
	
	public void closeProcess(IoSession session);
	
	public void beforeProcess(IoSession session,Object message);
	
	public void exceptionProcess(IoSession session,Throwable cause);
	
	public void idleProcess(IoSession session,IdleStatus status);
	
}
