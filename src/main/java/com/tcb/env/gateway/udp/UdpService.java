package com.tcb.env.gateway.udp;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;

import com.tcb.env.gateway.ProcessorI;
import com.tcb.env.gateway.filter.CodecFactory;
import com.tcb.env.gateway.filter.SplitPacketAbstract;

public class UdpService {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "UdpService";
	/**
	 * 声明日志对象
	 */
	private Logger logger = LogManager.getLogger(UdpService.class);

	/**
	 * mina监听类
	 */
	private NioDatagramAcceptor acceptor;

	/**
	 * 默认端口号
	 */
	private int port = 65535;

	/**
	 * 初始化mina
	 * 
	 * @param port
	 *            监听端口
	 * @param receiveBufferSize
	 *            接收缓存大小
	 * @param readerIdleTime
	 *            读超时(单位秒)
	 * @param splitPacketAbstract
	 *            UDP不会出现粘包问题，应该不用处理
	 * @param executorThreadNum
	 *            mina处理业务逻辑线程数量
	 * @param processor
	 *            实际业务处理接口
	 * @param updGateWayManager
	 *            UdpService管理类
	 */
	public void init(int port, int receiveBufferSize, int readerIdleTime,
			SplitPacketAbstract splitPacketAbstract, int executorThreadNum,
			ProcessorI processor, UpdGateWayManager updGateWayManager) {
		try {
			acceptor = new NioDatagramAcceptor();
			this.port = port;
			acceptor.setDefaultLocalAddress(new InetSocketAddress(this.port));
			// 设置socket底层输入缓冲区的大小
			acceptor.getSessionConfig().setReceiveBufferSize(receiveBufferSize);
			// 设置每一个非主监听连接的端口可以重用
			acceptor.getSessionConfig().setReuseAddress(true);
			// 设置读取超时（需要测试）
			acceptor.getSessionConfig().setReaderIdleTime(readerIdleTime);
			splitPacketAbstract.setStickPackage(false);//UDP是否需要粘包处理
			// 设置filter
			acceptor.getFilterChain().addLast(
					"codecfilter",
					new ProtocolCodecFilter(new CodecFactory(
							splitPacketAbstract)));
			acceptor.getFilterChain().addLast(
					"executorfilter",
					new ExecutorFilter(Executors
							.newFixedThreadPool(executorThreadNum)));
			//设置handler
			acceptor.setHandler(new UdpHandler(processor, updGateWayManager));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>[功能描述]：开启网关</p>
	 * 
	 * @author	王垒, 2016年3月22日下午11:56:57
	 * @since	EnvDust 1.0.0
	 *
	 */
	public void start() {
		try {
			acceptor.bind();
			logger.info(LOG+":开启UDP网关成功");
		} catch (Exception ex) {
			logger.error(LOG+":开启UDP网关失败，失败信息为：" + ex.toString());
		}
	}

	/**
	 * 
	 * <p>[功能描述]：停止网关</p>
	 * 
	 * @author	王垒, 2016年3月22日下午11:56:33
	 * @since	EnvDust 1.0.0
	 *
	 * @return
	 */
	public boolean stop() {
		try {
			acceptor.dispose();
			return true;
		} catch (Exception ex) {
			logger.error(LOG+":关闭UDP网关失败,失败信息为：" + ex.toString());
			return false;
		}
	}

}
