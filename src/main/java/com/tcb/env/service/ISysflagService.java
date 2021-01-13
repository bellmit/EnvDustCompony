/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service;

import java.util.List;
import com.tcb.env.pojo.Sysflag;

/**
 * <p>[功能描述]：</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年4月8日上午9:42:49
 * @since	EnvDust 1.0.0
 * 
 */
public interface ISysflagService {
	/**
	 * <p>[功能描述]：获得数据</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:43:07
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflag
	 * @return 
	 */
	public int getCount(Sysflag sysflag);
	/**
	 * <p>[功能描述]：获取SYsflag</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:43:21
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflag
	 * @return 
	 */
	public List<Sysflag> getSysflag(Sysflag sysflag);
	/**
	 * <p>[功能描述]：修改sysflag</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:43:39
	 * @since	EnvDust 1.0.0
	 *
	 * @param listsysflag
	 * @return
	 * @throws Exception 
	 */
	public int updateSysflag(List<Sysflag> listsysflag) throws Exception;
	/**
	 * <p>[功能描述]：判断sysflag是否存在</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:43:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflagid
	 * @param sysflagcode
	 * @return 
	 */
	public int getSysflagExist(int sysflagid,String sysflagcode);
	
	/**
	 * <p>[功能描述]：获取系统标识值</p>
	 * 
	 * @author	任崇彬, 2016年4月8日上午10:43:47
	 * @since	EnvDust 1.0.0
	 *
	 * @param sysflagcode
	 * @return 
	 */
	public String getSysFlagValue(String sysflagcode);

}
