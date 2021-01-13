package com.tcb.env.init;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcb.env.controller.AlarmDateMonitor;

import com.tcb.env.util.DefaultArgument;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class StartServlet extends HttpServlet {

	/**
	 * <p>
	 * [内容描述]：唯一ID
	 * </p>
	 */
	private static final long serialVersionUID = 6100012679205027249L;
	/**
	 * 日志输出标记
	 */
	private static final String LOG = "StartServlet";
	/**
	 * 声明日志对象
	 */
	private static Logger logger = Logger.getLogger(StartServlet.class);

	/**
	 * Constructor of the object.
	 */
	public StartServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 获得AlarmDateMonitor
	 */
	private AlarmDateMonitor alarmDateMonitor;

	/**
	 * 保护线程
	 */
	private Thread projectThread;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		try {
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(this.getServletContext());
//			udpServiceImpl = (UdpServiceImpl)ctx.getBean("udpServiceImpl");
//			udpServiceImpl.startUdp();
			alarmDateMonitor = (AlarmDateMonitor) ctx.getBean("alarmDateMonitor");
			alarmDateMonitor.start();

			/* 启动保护线程 */
			projectThread = new Thread(){
				@Override
				public void run() {
					while(true){
						try {
							if(!alarmDateMonitor.isAlive()){
								alarmDateMonitor.start();
							}
						} catch (Exception e) {
							logger.error(LOG+"websocket数据保护线程异常，信息为："+e.getMessage());
						}finally {
							try {
								Thread.sleep(DefaultArgument.OUT_TIME);
							} catch (InterruptedException e) {
								logger.error(LOG+"休眠websocket数据保护线程异常，信息为："+e.getMessage());
							}
						}
					}
				}
			};
			/* 启动保护线程 */
			projectThread.start();
			logger.info(LOG+":开启websocket数据线程成功！");
		} catch (Exception e) {
			logger.error(LOG + ":开启websocket数据线程失败，错误信息：" + e.getMessage());
		}
	}

}
