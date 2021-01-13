package com.tcb.env.gateway.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class IoEncoder extends ProtocolEncoderAdapter {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "IoEncoder";
	/**
	 * 声明日志对象
	 */
	private Logger logger = LogManager.getLogger(IoDecoder.class);

	/**
	 * �·����
	 * 
	 * @param ioSession
	 * @param object
	 * @param protocolEncoderOutput
	 * 
	 */
	@Override
	public void encode(IoSession ioSession, Object object,
			ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
		// TODO ��Ҫ�޸������־����
		if(object instanceof byte[])
		{
			byte[] data = (byte[])object;
			IoBuffer ioBuffer = IoBuffer.allocate(data.length,false);
			ioBuffer.put(data);
			ioBuffer.flip();
			protocolEncoderOutput.write(ioBuffer);
			protocolEncoderOutput.flush();
			logger.info(LOG+"：数据发送成功！");
		}
		else
		{
			logger.info(LOG+"：发送数据失败，发送数据只能是byte[]数组类型！");
		}

	}

}
