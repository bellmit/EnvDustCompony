package com.tcb.env.gateway.tcp;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.tcb.env.gateway.ProcessorI;

public class TcpHandler extends IoHandlerAdapter {

	private ProcessorI processor = null;

	private TcpGateWayManager tcpGateWayManager = null;

	//为spring注入追加的，正确解决办法此类应该无构造函数
	public TcpHandler() {
	}
	public TcpHandler(ProcessorI processor, TcpGateWayManager tcpGateWayManager) {
		this.processor = processor;
		this.tcpGateWayManager = tcpGateWayManager;
	};

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		if (message instanceof byte[]) {
			byte[] data = (byte[]) message;
			processor.afterProcess(session, data);
		}
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
