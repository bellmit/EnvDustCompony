package com.tcb.env.gateway.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class CodecFactory implements ProtocolCodecFactory {

	/**
	 * ���
	 */
	private ProtocolEncoder encoder;
	/**
	 * �������
	 */
	private ProtocolDecoder decoder;
	
	
	public CodecFactory(SplitPacketAbstract splitPacketAbstract)
	{
		this.encoder = new IoEncoder();
		this.decoder = new IoDecoder(splitPacketAbstract);
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

}
