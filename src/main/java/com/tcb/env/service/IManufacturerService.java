/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.Manufacturer;


/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月24日上午9:06:35
 * @since	EnvDust 1.0.0
 * 
 */
public interface IManufacturerService {
	/**
	 * <p>[功能描述]：是否模糊查询符合条件个数</p>
	 * 
	 * @author	任崇彬, 2016年3月24日上午9:14:26
	 * @since	EnvDust 1.0.0
	 *
	 * @param oranization
	 * @param ignoredel
	 * @return 
	 */
	public int getCount(Manufacturer manufacturer,boolean isLike);
	/**
	 * <p>[功能描述]：得到厂商数据</p>
	 * 
	 * @author	任崇彬, 2016年3月24日上午9:55:37
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturer
	 * @param ignoredel
	 * @return 
	 */
	public List<Manufacturer> getManufacturer(Manufacturer manufacturer);
	/**
	 * <p>[功能描述]：新增厂商数据</p>
	 * 
	 * @author	任崇彬, 2016年3月24日上午11:42:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param listmanufacturer
	 * @return 
	 * @throws Exception 
	 */
	public int insertManufacturer(List<Manufacturer> listmanufacturer) throws Exception;
	/**
	 * <p>[功能描述]：删除数据</p>
	 * 
	 * @author	任崇彬, 2016年3月24日下午2:08:09
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerid
	 * @return 
	 * @throws Exception 
	 */
	public int deleteManufacturer(List<Integer>  manufacturerid) throws Exception;
	/**
	 * <p>[功能描述]：更新厂商信息</p>
	 * 
	 * @author	任崇彬, 2016年3月24日下午2:24:02
	 * @since	EnvDust 1.0.0
	 *
	 * @param listmanufacturer
	 * @return 
	 * @throws Exception 
	 */
	public int updateManufacturer(List<Manufacturer> listmanufacturer) throws Exception;
	/**
	 * <p>[功能描述]：更新用户数据时候判断用户名是否存在</p>
	 * 
	 * @author	任崇彬, 2016年3月24日下午3:32:39
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerid
	 * @param manufacturername
	 * @return 
	 */
	public int getManufacturerExist(int manufacturerid,String manufacturercode);
}
