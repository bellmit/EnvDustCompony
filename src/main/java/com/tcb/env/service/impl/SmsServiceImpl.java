package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.ISmsDao;
import com.tcb.env.pojo.Sms;
import com.tcb.env.pojo.SmsUser;
import com.tcb.env.service.ISmsService;

/**
 * 
 * <p>[功能描述]：短信设置服务接口实现类</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年4月12日下午1:52:38
 * @since	EnvDust 1.0.0
 *
 */
@Service("smsService")
@Transactional(rollbackFor = Exception.class)
class SmsServiceImpl implements ISmsService {
	
	@Resource
	private ISmsDao smsDao;

	@Override
	public int getSmsCount(Sms sms) {
		return smsDao.getSmsCount(sms);
	}

	@Override
	public List<Sms> getSms(Sms sms) {
		return smsDao.getSms(sms);
	}

	@Override
	public int insertSms(Sms sms,List<SmsUser> smsUserList) throws Exception {
		int result = 0;
		if(sms != null){
			result = smsDao.insertSms(sms);
			if(result>0 && smsUserList != null && smsUserList.size()>0){
				for (SmsUser smsUser : smsUserList) {
					smsUser.getSms().setSmsId(sms.getSmsId());
				}
				smsDao.insertSmsUser(smsUserList);
			}
		}
		return result;
	}

	@Override
	public int updateSms(Sms sms,List<SmsUser> smsUserList) throws Exception {
		int result = 0;
		if(sms != null){
			result = smsDao.updateSms(sms);
			if(result>0 && smsUserList != null && smsUserList.size()>0){
				smsDao.deleteSmsUser(String.valueOf(sms.getSmsId()));
				smsDao.insertSmsUser(smsUserList);
			}
		}
		return result;
	}

	@Override
	public int deleteSms(List<String> idList) throws Exception {
		return smsDao.deleteSms(idList);
	}

	@Override
	public List<SmsUser> getSmsUser(SmsUser smsUser) {
		return smsDao.getSmsUser(smsUser);
	}

	@Override
	public int getSmsExist(String deviceId,String statusCode,String thingCode) {
		return smsDao.getSmsExist(deviceId,statusCode,thingCode);
	}

}
