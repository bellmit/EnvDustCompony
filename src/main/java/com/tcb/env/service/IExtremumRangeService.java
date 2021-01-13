package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.CommMain;

/**
 * 
 * <p>[功能描述]：极值范围数据服务接口</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2018年1月9日下午2:42:23
 * @since	EnvDust 1.0.0
 *
 */
public interface IExtremumRangeService {

	/**
	 * 
	 * <p>[功能描述]：获取极值范围个数</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:54:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	int getExtremumRangeCount(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：获取极值范围数据</p>
	 * 
	 * @author	王垒, 2018年1月9日上午10:54:57
	 * @since	EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	List<CommMain> getExtremumRange(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>[功能描述]：插入设置极值范围数据</p>
	 * 
	 * @author	王垒, 2018年1月9日下午2:49:28
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param xMaxState
	 * @param xMinState
	 * @param xMax
	 * @param xMin
	 * @param setExcuteTime
	 * @param listDeviceCode
	 * @param optId
	 * @return
	 * @throws Exception
	 */
	int insertExtremumRangeSet(String cnCode, String xMaxState,String xMinState,String xMax,String xMin,
			String setExcuteTime,List<String> listDeviceCode, int optId) throws Exception;

	/**
	 * 
	 * <p>[功能描述]：插入获取极值范围数据(未使用，统一用SET)</p>
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
	int insertExtremumRangeGet(String cnCode, String getExcuteTime,
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
	int deleteExtremumRange(List<Integer> listCommId) throws Exception;
}
