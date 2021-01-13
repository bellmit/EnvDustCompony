/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tcb.env.dao.IIpDao;
import com.tcb.env.pojo.Ip;
import com.tcb.env.service.IIpService;


/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月1日下午2:59:11
 * @since	EnvDust 1.0.0
 * 
 */
@Service("ipService")
class IpServiceImpl implements IIpService{
	@Resource
	private IIpDao ipDao;
	@Override
	public int getCount(Ip ip) {
		return ipDao.getCount(ip);
	}
	@Override
	public List<Ip> getIp(Ip ip) {
		return ipDao.getIp(ip);
	}
	@Override
	public int insertIp(List<Ip> listIp) throws Exception {
		int row = 0;
		row = ipDao.insertIp(listIp);
		return row;
	}
	@Override
	public int deleteIp(List<Integer> ipid) throws Exception {
		return ipDao.deleteIp(ipid);
	}
	@Override
	public int getIpExist(int ipid, String ipaddress) {
		return ipDao.getIpExist(ipid,ipaddress);
	}
	@Override
	public int updateIp(List<Ip> listip) throws Exception {
		
		return ipDao.updateIp(listip);
	}
	

}
