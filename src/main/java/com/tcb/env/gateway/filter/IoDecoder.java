package com.tcb.env.gateway.filter;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class IoDecoder extends CumulativeProtocolDecoder {

	/**
	 * 声明日志对象
	 */
//	private Logger logger = LogManager.getLogger(IoDecoder.class);
	
	/**
	 * 声明拆包规则类
	 */
	private SplitPacketAbstract splitPacketAbstract = null;

	public IoDecoder(SplitPacketAbstract splitPacketAbstract) {
		this.splitPacketAbstract = splitPacketAbstract;
	};


	/**
	 * 
	 * <p>[功能描述]：处理粘包过程</p>
	 * 
	 * @author	王垒, 2016年3月23日上午8:26:53
	 * @since	EnvDust 1.0.1
	 *
	 * @param ioSession
	 * @param ioBuffer
	 * @param protocolDecoderOutput
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean doDecode(IoSession ioSession, IoBuffer ioBuffer,
			ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
		// TODO 判断包头
		if (!splitPacketAbstract.isNoHead()
				&& splitPacketAbstract.isStickPackage()) {
			return true;
		} else {
			int len = ioBuffer.remaining();
			byte[] data = new byte[len];
			ioBuffer.get(data, 0, len);
			protocolDecoderOutput.write(data);
			return true;
		}
	}

}
