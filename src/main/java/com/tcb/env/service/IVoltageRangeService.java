package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>[功能描述]：零点量程数据服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月9日上午10:54:26
 * @since	EnvDust 1.0.0
 *
 */
public interface IVoltageRangeService {

	/**
	 * 
	 * <p>[功能描述]：获取零点量程个数</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:54:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	int getVoltageRangeCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：获取零点量程数据</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:54:57
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	List<CommMain> getVoltageRange(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：插入设置零点量程数据</p>
	 * 
	 * @author	王垒, 2018年1月9日上午11:32:27
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param xZeroVolt
	 * @param xScaleVolt
	 * @param xScaleConc
	 * @param setExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	int insertVoltageRangeSet(String cnCode, String xZeroVolt,String xScaleVolt,String xScaleConc,
			String setExcuteTime,List<String> listDeviceCode, int optId) throws Exception;

	/**
	 * 
	 * <p>[功能描述]：插入获取零点量程数据(未使用，统一用SET)</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:57:10
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param getExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	int insertVoltageRangeGet(String cnCode, String getExcuteTime,
			List<String> listDeviceCode, int optId) throws Exception;

	/**
	 * 
	 * <p>[功能描述]：删除数据计划</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:57:52
	 * @since	EnvDust 1.0.0
	 *
	 * @param listCommId
	 * @return
	 * @throws Exception
	 */
	int deleteVoltageRange(List<Integer> listCommId) throws Exception;

}
