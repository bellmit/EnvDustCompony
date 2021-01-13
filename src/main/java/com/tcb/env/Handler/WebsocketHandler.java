package com.tcb.env.Handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.tcb.env.controller.DeviceController;
import com.tcb.env.util.DefaultArgument;

/**
 * 
 * <p>
 * [功能描述]：WebSocket处理器
 * 		          主要用来进行数据的及时更新(在后台将数据向前台发送数据，进行数据的更新)
 * </p>
 * <p>
 * 		Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王坤
 * @version 1.0, 2018年9月28日上午10:07:29
 * @since EnvDust 1.0.0
 * 
 */
@Component
public class WebsocketHandler extends AbstractWebSocketHandler {
	/* WebsocketHandler日志 */
	private Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);
	
	/* 在线用户列表 */
    private static final Map<String,WebSocketSession> users;
    
    static {
        users = new ConcurrentHashMap<>();
    }
 
	@Resource
	private DeviceController deviceController;
	
	/* 连接成功后 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String uesrId = this.getSessionId(session);
		if(uesrId != null && !users.containsKey(uesrId)){
			users.put(uesrId, session);
			logger.info("websocket服务器已经连接成功!  连接数   :" + users.size() );
		}
	}

	/* 接收文本消息 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		if(message != null){
			if(message.getPayload().equals("1000")){
				//回复客户端
				try {
//				    logger.info("心跳                 "  + message.getPayload());
					synchronized(session){
						session.sendMessage(message);
					}
				} catch (IOException e) {
					logger.error("发送心跳消息异常错误 :" + e.getMessage());
				}
			}
		}
	}
	
	/* 发生错误进行的处理 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		/* 断开session，去除users中的sessionId */
		if(session != null){
			String sessionId = getSessionId(session);
			if(sessionId != null){
				users.remove(sessionId);
				/* 除去存储DeviceController中的sessionId */
				deviceController.removeSessionId(sessionId);
			}
		}
		/* 关闭session */
		if (session.isOpen()) {
            session.close();
		}
	}
	
	@Override
    public boolean supportsPartialMessages() {
        return false;
    }

	/* 连接断开之后 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("websocket服务器已经断开连接!" + users.size() );
		/* 断开session，去除users中的sessionId */
		if(session != null){
			String sessionId = getSessionId(session);
			if(sessionId != null){
				users.remove(sessionId);
				/* 除去存储DeviceController中的userId */
				deviceController.removeSessionId(sessionId);
			}
		}
	}
	
	/**
     * 通知单个用户数据改变
     * 
     * @param message
     * @return
     */
    public void sendMessage(String sessionId,String message) {
        TextMessage textMessage = new TextMessage(message);
        WebSocketSession session = users.get(sessionId);
        try {
        	if(session != null && session.isOpen()){
        		synchronized(session){
        			session.sendMessage(textMessage);
        		}
        	}
		} catch (IOException e) {
			logger.error("发送消息异常警报  :" + e.getMessage());
		}
    }
    
    /**
     * 获取sessionId的信息
     */
    public String getSessionId(WebSocketSession session){
    	try{
    		String sessionId = (String)session.getAttributes().get(DefaultArgument.LOGIN_USER);
        	return sessionId;
    	}catch(Exception e){
    		logger.error("获取用户编码异常警报 :" + e.getMessage());
    		return null;
    	}
    }
    
    /**
     * 用户退出
     */
    public void removeSessionId(String sessionId){
    	boolean isExist = users.containsKey(sessionId);

			try {
				if(isExist){
					WebSocketSession session = users.get(sessionId);
					/* 关闭session */
					if (session != null && session.isOpen()) {
						session.close();
					}
				}
			} catch (IOException e) {
				logger.error("session关闭异常警报 : " + e.getMessage());
			}finally {
				/* 剔除sessionId */
				users.remove(sessionId);
				/* 剔除DeviceController中的userId */
				deviceController.removeSessionId(sessionId);
				logger.info("session清除成功 : " + sessionId);
			}
    }
}
