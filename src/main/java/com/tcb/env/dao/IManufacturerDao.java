/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Manufacturer;



/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月24日上午9:33:21
 * @since	EnvDust 1.0.0
 * 
 */
public interface IManufacturerDao {
	/**
	 * <p>[功能描述]：是否模糊查询获得个数</p>
	 * 
	 * @author	任崇彬, 2016年3月25日下午2:42:44
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturer
	 * @param isLike
	 * @return 
	 */
	public int getCount(@Param("manufacturer")Manufacturer manufacturer,@Param("isLike")boolean isLike);
	/**
	 * <p>[功能描述]：获取厂商数据</p>
	 * 
	 * @author	任崇彬, 2016年3月25日下午2:44:02
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturer
	 * @return 
	 */
	public List<Manufacturer> getManufacturer(@Param("manufacturer")Manufacturer manufacturer);
	/**
	 * <p>[功能描述]：新增数据</p>
	 * 
	 * @author	任崇彬, 2016年3月25日下午2:44:39
	 * @since	EnvDust 1.0.0
	 *
	 * @param listmanufacturer
	 * @return 
	 */
	public int insertManufacturer(@Param("listmanufacturer")List<Manufacturer> listmanufacturer);
	/**
	 * <p>[功能描述]：删除厂商数据</p>
	 * 
	 * @author	任崇彬, 2016年3月25日下午2:44:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerid
	 * @return 
	 */
	public int deleteManufacturer(@Param("manufacturerid")List<Integer> manufacturerid);
	/**
	 * <p>[功能描述]：更新厂商数据</p>
	 * 
	 * @author	任崇彬, 2016年3月25日下午2:44:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return 
	 */
	public int updateManufacturer(@Param("list")List<Manufacturer> list);
	/**
	 * <p>[功能描述]：判断是否存在数据</p>
	 * 
	 * @author	任崇彬, 2016年3月25日下午2:45:06
	 * @since	EnvDust 1.0.0
	 *
	 * @param manufacturerid
	 * @param manufacturercode
	 * @return 
	 */
	public int getManufacturerExist(@Param("manufacturerid")int manufacturerid,@Param("manufacturercode")String manufacturercode);
}
