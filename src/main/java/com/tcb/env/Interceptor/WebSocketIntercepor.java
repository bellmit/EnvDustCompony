package com.tcb.env.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：WebSocket握手拦截器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王坤
 * @version 1.0, 2018年8月28日上午9:54:00
 * @since EnvDust 1.0.0
 *
 */
@Component
public class WebSocketIntercepor implements HandshakeInterceptor {
	/* 获取拦截器日志 */
	Logger logger = LoggerFactory.getLogger(WebSocketIntercepor.class);
	
	 /**
	  * 握手之后
	  * 
	  */
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse arg1,
			WebSocketHandler arg2, Exception arg3) {
//		logger.info("websocket握手之后  :");
		
	}
	
	/**
	 * 握手之前
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> map) throws Exception {
//		logger.info("websocket开始握手  :");
		if(request instanceof ServletServerHttpRequest){
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			if (session != null) {
	            map.put(DefaultArgument.LOGIN_USER, session.getId());
	        }
		}
		return true;
	}

}
