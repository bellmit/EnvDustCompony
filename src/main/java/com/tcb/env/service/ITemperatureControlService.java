package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>[功能描述]：温度控制数据服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月9日下午2:42:23
 * @since	EnvDust 1.0.0
 *
 */
public interface ITemperatureControlService {

	/**
	 * 
	 * <p>[功能描述]：获取温度控制个数</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:54:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	int getTemperatureControlCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：获取温度控制数据</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:54:57
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	List<CommMain> getTemperatureControl(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：插入设置温度控制数据</p>
	 * 
	 * @author	王垒, 2018年1月18日上午9:56:48
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param xTpCtrlEn
	 * @param xTpCtrl
	 * @param setExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	int insertTemperatureControlSet(String cnCode, String xTpCtrlEn,String xTpCtrl,
			String setExcuteTime,List<String> listDeviceCode, int optId) throws Exception;

	/**
	 * 
	 * <p>[功能描述]：获取温度控制数据(未使用，统一用SET)</p>
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
	int insertTemperatureControlGet(String cnCode, String getExcuteTime,
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
	int deleteTemperatureControl(List<Integer> listCommId) throws Exception;
}
