package com.tcb.env.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.tcb.env.Handler.WebsocketHandler;
import com.tcb.env.Interceptor.WebSocketIntercepor;

/**
 * 
 * <p>
 * [功能描述]：WebSocket拦截器
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王坤
 * @version 1.0, 2018年9月28日上午9:55:27
 * @since EnvDust 1.0.0
 * 
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	/**
	 * 注册Websocket
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		/* 1.注册兼容websocket的配置 */
		String websocketUrl = "/websocket";
		registry.addHandler(this.websocketHandler, websocketUrl).
				 addInterceptors(this.webSocketIntercepor).
				 setAllowedOrigins("*");
		
		/* 2.注册socketJS的配置 */
		String socketUrl = "/socketJS";
		registry.addHandler(this.websocketHandler, socketUrl).
				 addInterceptors(this.webSocketIntercepor).
				 withSockJS();
	}
	
	@Resource
	private WebsocketHandler websocketHandler;
	
	@Resource
	private WebSocketIntercepor webSocketIntercepor;
//	
//	@Bean
//	public WebsocketHandler websocketHandler(){
//		return new WebsocketHandler();
//	}
//	
//	public WebSocketIntercepor webSocketIntercepor(){
//		return new WebSocketIntercepor();
//	}
	
}
