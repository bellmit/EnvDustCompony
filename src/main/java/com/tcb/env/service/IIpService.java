/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service;

import java.util.List;


import com.tcb.env.pojo.Ip;



/**
 * <p>[功能描述]：IPserviese</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月1日下午2:56:19
 * @since	EnvDust 1.0.0
 * 
 */
public interface IIpService {
	/**
	 * <p>[功能描述]：查询个数</p>
	 * 
	 * @author	任崇彬, 2016年4月1日下午3:04:44
	 * @since	EnvDust 1.0.0
	 *
	 * @param ip
	 * @param isLike
	 * @return 
	 */
	public int getCount(Ip ip);
	/**
	 * <p>[功能描述]：查询Ip</p>
	 * 
	 * @author	任崇彬, 2016年4月1日下午4:00:22
	 * @since	EnvDust 1.0.0
	 *
	 * @param ip
	 * @return 
	 */
	public List<Ip> getIp(Ip ip);
	/**
	 * <p>[功能描述]：新增IP地址</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午3:09:49
	 * @since	EnvDust 1.0.0
	 *
	 * @param listIp
	 * @return
	 * @throws Exception 
	 */
	public int insertIp(List<Ip> listIp) throws Exception;
	/**
	 * <p>[功能描述]：删除IP地址</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午3:51:42
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipid
	 * @return
	 * @throws Exception 
	 */
	public int deleteIp(List<Integer>  ipid) throws Exception;
	/**
	 * <p>[功能描述]：判断是否存在相同的IP地址</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午8:26:45
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipid
	 * @param ipaddress
	 * @return 
	 */
	public int getIpExist(int ipid,String ipaddress);
	/**
	 * <p>[功能描述]：修改IP</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午8:38:28
	 * @since	EnvDust 1.0.0
	 *
	 * @param listip
	 * @return
	 * @throws Exception 
	 */
	public int updateIp(List<Ip> listip) throws Exception;
}
