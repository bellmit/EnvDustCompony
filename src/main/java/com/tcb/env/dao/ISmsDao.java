package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Sms;
import com.tcb.env.pojo.SmsUser;

/**
 * 
 * <p>[功能描述]：短信设置dao</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年4月12日上午9:47:53
 * @since	EnvDust 1.0.0
 *
 */
public interface ISmsDao {
	
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
	int getSmsCount(@Param("sms")Sms sms);
	
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
	List<Sms> getSms(@Param("sms")Sms sms);
	
	/**
	 * 
	 * <p>[功能描述]：插入短信设置</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:00:51
	 * @since	EnvDust 1.0.0
	 *
	 * @param sms
	 * @return
	 */
	int insertSms(@Param("sms")Sms sms);
	
	/**
	 * 
	 * <p>[功能描述]：更新短信设置</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:03:31
	 * @since	EnvDust 1.0.0
	 *
	 * @param sms
	 * @return
	 */
	int updateSms(@Param("sms")Sms sms);
	
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
	int deleteSms(@Param("idList")List<String> idList);
	
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
	List<SmsUser> getSmsUser(@Param("smsUser")SmsUser smsUser);
	
	/**
	 * 
	 * <p>[功能描述]：插入短信用户</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:01:23
	 * @since	EnvDust 1.0.0
	 *
	 * @param smsUserList
	 * @return
	 */
	int insertSmsUser(@Param("smsUserList")List<SmsUser> smsUserList);

	/**
	 * 
	 * <p>[功能描述]：删除短信用户</p>
	 * 
	 * @author	王垒, 2018年4月12日上午10:03:31
	 * @since	EnvDust 1.0.0
	 *
	 * @param smsId
	 * @return
	 */
	int deleteSmsUser(@Param("smsId")String smsId);
	
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
	int getSmsExist(
			@Param("deviceId")String deviceId,
			@Param("statusCode")String statusCode,
			@Param("thingCode")String thingCode);

}
