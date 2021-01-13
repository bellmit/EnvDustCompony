package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.Oranization;



/**
 * <p>[功能描述]：Orgnization接口</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月17日下午3:29:49
 * @since	EnvDust 1.0.0
 * 
 */
public interface IOranizationService {
	/**
	 * <p>[功能描述]：获得符合条件数据个数</p>
	 * 
	 * @author	任崇彬, 2016年3月21日上午9:07:06
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranization
	 * @return 
	 */
	public int getCount(Oranization oranization,boolean isLike);
	/**
	 * <p>[功能描述]：获取组织数据,获取到的所有符合条件的值都放在list中</p>
	 * 
	 * @author	任崇彬, 2016年3月18日上午9:54:59
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranization
	 * @return 
	 */
	public List<Oranization> getOranization(Oranization oranization);
	/**
	 * <p>[功能描述]：增加组织数据</p>
	 * 
	 * @author	任崇彬, 2016年3月18日上午9:55:04
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranization
	 * @return 
	 * @throws Exception 
	 */
	public int insertOranization(Oranization oranization) throws Exception;
	/**
	 * <p>[功能描述]：删除组织数据</p>
	 * 
	 * @author	任崇彬, 2016年3月18日下午3:04:45
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranization
	 * @return 
	 * @throws Exception 
	 */
	public int deleteOranization(List<Integer>  oranizationid) throws Exception;
	/**
	 * <p>[功能描述]：更新组织数据</p>
	 * 
	 * @author	任崇彬, 2016年3月22日上午8:36:54
	 * @since	EnvDust 1.0.0
	 *
	 * @param listoranization
	 * @return 
	 * @throws Exception 
	 */
	public int updateOranization(List<Oranization> listoranization) throws Exception;
	/**
	 * <p>[功能描述]：获取是否存在相同的组织名称</p>
	 * 
	 * @author	任崇彬, 2016年3月22日下午4:05:34
	 * @since	EnvDust 1.0.0
	 *
	 * @param userid
	 * @param usercode
	 * @return 
	 */
	public int getOranizationExist(int oranizationid,String oranizationname);
	
	/**
	 * <p>[功能描述]：通过组织Id得到相应组织名称</p>
	 * 
	 * @author	任崇彬, 2016年3月23日上午10:32:52
	 * @since	EnvDust 1.0.0
	 *
	 * @param orgid
	 * @param orgname
	 * @return 
	 */
	public String getOranizationPathById(int orgid);
}
