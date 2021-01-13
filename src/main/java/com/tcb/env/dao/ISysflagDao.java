/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Sysflag;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月8日上午9:48:25
 * @since	EnvDust 1.0.0
 * 
 */
public interface ISysflagDao {
	/**
	 * <p>[功能描述]：获取</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:50:37
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflag
	 * @return 
	 */
	public List<Sysflag> getSysflag(@Param("sysflag")Sysflag sysflag);
	/**
	 * <p>[功能描述]：得到个数</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:50:48
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflag
	 * @return 
	 */
	public int getCount(@Param("sysflag")Sysflag sysflag);
	/**
	 * <p>[功能描述]：更新数据</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:50:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param list
	 * @return 
	 */
	public int updateSysflag(@Param("list")List<Sysflag> list);
	/**
	 * <p>[功能描述]：判断是否存在</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:51:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflagid
	 * @param sysflagcode
	 * @return 
	 */
	public int getSysflagExist(@Param("sysflagid")int sysflagid,@Param("sysflagcode")String sysflagcode);
}
