package com.tcb.env.gateway.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.stereotype.Component;

import com.tcb.env.gateway.ProcessorI;
import com.tcb.env.gateway.filter.CodecFactory;
import com.tcb.env.gateway.filter.SplitPacketAbstract;

public class TcpService {

	/**
	 * 日志输出标记
	 */
	private static final String LOG = "TcpService";
	/**
	 * 声明日志对象
	 */
	private Logger logger = LogManager.getLogger(TcpService.class);
	/**
	 * mina监听类
	 */
	private NioSocketAcceptor acceptor = null;
	/**
	 * 默认端口号
	 */
	private int port = 65535;


	/**
	 * 初始化mina
	 * 
	 * @param port
	 *            监听端口
	 * @param backlog
	 *            socket接入时队列
	 * @param readerIdleTime
	 *            读超时(单位秒)
	 * @param splitPacketAbstract
	 *            UDP不会出现粘包问题，应该不用处理
	 * @param executorThreadNum
	 *            mina处理业务逻辑线程数量
	 * @param processor
	 *            实际业务处理接口
	 * @param tcpGateWayManager
	 *            TcpIoService管理类
	 */
	public void init(int port, int backlog, int readerIdleTime,
			SplitPacketAbstract splitPacketAbstract, int executorThreadNum,
			ProcessorI processor, TcpGateWayManager tcpGateWayManager) {
		try {
			acceptor = new NioSocketAcceptor();
			this.port = port;
			acceptor.setDefaultLocalAddress(new InetSocketAddress(this.port));
			// socket接入时候的队列，指那些想要发起socket的SYN,500能过的时候的参数是20000
			acceptor.setBacklog(backlog);
			// 读超时
			acceptor.getSessionConfig().setReaderIdleTime(readerIdleTime);
			// 设置filter
			acceptor.getFilterChain().addLast(
					"codecfilter",
					new ProtocolCodecFilter(new CodecFactory(
							splitPacketAbstract)));
			acceptor.getFilterChain().addLast(
					"executorfilter",
					new ExecutorFilter(Executors
							.newFixedThreadPool(executorThreadNum)));
			// 设置Handler
			acceptor.setHandler(new TcpHandler(processor, tcpGateWayManager));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��������
	 */
	public void start() {
		try {
			acceptor.bind();
			logger.info("����TCP������!");
		} catch (Exception ex) {
			logger.error("��ʼ����TCPʧ�ܣ�������Ϣ��" + ex.toString());
		}
	}

	/**
	 * ֹͣ����
	 * 
	 * @return �����ɹ���־
	 */
	public boolean stop() {
		try {
			acceptor.dispose();
			return true;
		} catch (Exception ex) {
			logger.error("�ر�TCP�������,������Ϣ��" + ex.toString());
			return false;
		}
	}
}
