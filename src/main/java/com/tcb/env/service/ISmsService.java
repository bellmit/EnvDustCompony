package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.Sms;
import com.tcb.env.pojo.SmsUser;

/**
 * 
 * <p>[功能描述]：短信设置服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年4月12日下午1:50:12
 * @since	EnvDust 1.0.0
 *
 */
public interface ISmsService {
	
	/**
	 * 
	 * <p>[功能描述]：获取短信设置记录个数</p>
	 * 
	 * @author	王垒, 2018年4月12日上午9:54:10
	 * @since	EnvDust 1.0.0
	 *
	 * @param sms
	 * @return
	 */
	int getSmsCount(Sms sms);
	
	/**
	 * 
	 * <p>[功能描述]：获取短信设置记录</p>
	 * 
	 * @author	王垒, 2018年4月12日上午9:54:10
	 * @since	EnvDust 1.0.0
	 *
	 * @param sms
	 * @return
	 */
	List<Sms> getSms(Sms sms);
	
	/**
	 * 
	 * <p>[功能描述]：插入短信设置</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:00:51
	 * @since	EnvDust 1.0.0
	 *
	 * @param sms
	 * @param smsUserList
	 * @return
	 */
	int insertSms(Sms sms,List<SmsUser> smsUserList) throws Exception;
	
	/**
	 * 
	 * <p>[功能描述]：更新短信设置</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:03:31
	 * @since	EnvDust 1.0.0
	 *
	 * @param sms
	 * @param smsUserList
	 * @return
	 */
	int updateSms(Sms sms,List<SmsUser> smsUserList) throws Exception;
	
	/**
	 * 
	 * <p>[功能描述]：删除短信设置</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:03:31
	 * @since	EnvDust 1.0.0
	 *
	 * @param idList
	 * @return
	 */
	int deleteSms(List<String> idList) throws Exception;
	
	/**
	 * 
	 * <p>[功能描述]：获取短信用户记录</p>
	 * 
	 * @author	王垒, 2018年4月12日上午9:54:10
	 * @since	EnvDust 1.0.0
	 *
	 * @param smsUser
	 * @return
	 */
	List<SmsUser> getSmsUser(SmsUser smsUser);
	
	/**
	 * 
	 * <p>[功能描述]：获取设备短信设置数量</p>
	 * 
	 * @author	王垒, 2018年4月17日上午10:22:44
	 * @since	EnvDust 1.0.0
	 *
	 * @param deviceId
	 * @param statusCode
	 * @param thingCode
	 * @return
	 */
	int getSmsExist(String deviceId,String statusCode,String thingCode);

}
