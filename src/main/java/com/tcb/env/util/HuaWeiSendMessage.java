package com.tcb.env.util;
import com.smn.client.DefaultSmnClient;
import com.smn.client.SmnClient;
import com.smn.request.sms.SmsPublishRequest;
import com.smn.response.sms.SmsPublishResponse;
import com.tcb.env.config.SMSConfig;

/**
 * 
 * <p>[功能描述]：华为云发送短信类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年3月29日下午2:01:56
 * @since	EnvDust 1.0.0
 *
 */
public class HuaWeiSendMessage {
	
	/**
	 * 
	 * <p>[功能描述]：发送短信</p>
	 * 
	 * @author	王垒, 2018年3月29日下午2:07:06
	 * @since	EnvDust 1.0.0
	 *
	 * @param smsConfig
	 * @return
	 */
	public static boolean doIt(SMSConfig smsConfig){
		boolean result = false;
	    try {
	    	SmnClient smnClient= new DefaultSmnClient( "issacwl","issacwl","issac1984","cn-north-1");
	    	// 构造请求对象 
	    	SmsPublishRequest smnRequest= new SmsPublishRequest();

	    	// 设置参数,接收手机号，短信内容，短信签名ID 
	    	smnRequest.setEndpoint(smsConfig.getPhoneNumber()).setMessage(smsConfig.getContent()).setSignId("5be80bfdb26a41529979e5866dc720a9");
	    	SmsPublishResponse res = smnClient.sendRequest(smnRequest); 
	    	if(200 <= res.getHttpCode() && res.getHttpCode() < 300){
	    		result = true;
	    	}else{
		    	System.out.println("httpCode:" + res.getHttpCode()+ ",message_id:" + res.getMessageId()+ ", request_id:" + res.getRequestId()+ ", errormessage:" + res.getMessage());
		    	result = false;
	    	}
	    } catch (Exception e) {
    		// 处理异常
	    	e.printStackTrace(); 
	    	result = false;
		}
	    return result;
	}
	

}
