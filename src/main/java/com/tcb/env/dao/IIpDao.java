/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.tcb.env.pojo.Ip;



/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月1日下午3:01:20
 * @since	EnvDust 1.0.0
 * 
 */
public interface IIpDao {
	/**
	 * <p>[功能描述]：查询个数</p>
	 * 
	 * @author	任崇彬, 2016年4月1日下午3:02:07
	 * @since	EnvDust 1.0.0
	 *
	 * @param ip
	 * @param isLike
	 * @return 
	 */
	public int getCount(@Param("ip")Ip ip);
	/**
	 * <p>[功能描述]：查询Ip</p>
	 * 
	 * @author	任崇彬, 2016年4月1日下午4:02:08
	 * @since	EnvDust 1.0.0
	 *
	 * @param ip
	 * @return 
	 */
	public List<Ip> getIp(@Param("ip")Ip ip);
	/**
	 * <p>[功能描述]：新增IP</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午3:13:23
	 * @since	EnvDust 1.0.0
	 *
	 * @param listip
	 * @return 
	 */
	public int insertIp(@Param("listip") List<Ip> listip);
	/**
	 * <p>[功能描述]：删除Ip</p>
	 * 
	 * @author	任崇彬, 2016年4月5日下午4:07:59
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipid
	 * @return 
	 */
	public int deleteIp(@Param("ipid")List<Integer> ipid);
	/**
	 * <p>[功能描述]：判断IP 是否存在</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午8:33:01
	 * @since	EnvDust 1.0.0
	 *
	 * @param ipid
	 * @param ipaddress
	 * @return 
	 */
	public int getIpExist(@Param("ipid")int ipid,@Param("ipaddress")String ipaddress);
	/**
	 * <p>[功能描述]：修改IP</p>
	 * 
	 * @author	任崇彬, 2016年4月6日上午8:39:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return 
	 */
	public int updateIp(@Param("list")List<Ip> list);
}
