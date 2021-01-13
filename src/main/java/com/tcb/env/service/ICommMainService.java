package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.CommResultModel;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommPrepare;

/**
 * 
 * <p>
 * [功能描述]：计划任务服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年6月1日上午8:14:32
 * @since EnvDust 1.0.0
 *
 */
public interface ICommMainService {

	/**
	 * 
	 * <p>
	 * [功能描述]：获取计划任务信息个数
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午3:58:56
	 * @since EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	int getCommMainCount(CommMain commMain, List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取计划任务信息
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午3:58:56
	 * @since EnvDust 1.0.0
	 *
	 * @param commMain
	 * @param listDeviceCode
	 * @param listCnCode
	 * @return
	 */
	List<CommMain> getCommMain(CommMain commMain,
			List<String> listDeviceCode, List<String> listCnCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入计划任务准备信息
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午4:07:01
	 * @since EnvDust 1.0.0
	 *
	 * @param commPrepare
	 * @return
	 */
	int insertCommPrepare(CommPrepare commPrepare);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入计划任务信息主表
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午4:02:06
	 * @since EnvDust 1.0.0
	 *
	 * @param commMain
	 * @return
	 */
	int insertCommMain(List<CommMain> listCommMain);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取发送状态信息id
	 * </p>
	 * 
	 * @author 王垒, 2016年6月1日下午3:56:20
	 * @since EnvDust 1.0.0
	 *
	 * @param type
	 * @param code
	 * @return
	 */
	int getCommStatusId(String type, String code);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除计划任务信息
	 * </p>
	 * 
	 * @author 王垒, 2016年6月2日上午11:47:39
	 * @since EnvDust 1.0.0
	 *
	 * @param listCommId
	 * @return
	 */
	int deleteCommMain(List<Integer> listCommId);
	
	/**
	 * 
	 * <p>[功能描述]：删除计划任务信息</p>
	 * 
	 * @author	王垒, 2016年6月6日下午2:04:19
	 * @since	EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param listDeviceCode
	 * @return
	 */
	int deleteCommMain(String cnCode, List<String> listDeviceCode);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取CommMain主键
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日上午10:49:03
	 * @since EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param deviceCode
	 * @return
	 */
	String getMainCommId(String cnCode, String deviceCode);
	
	/**
	 * 
	 * <p>[功能描述]：获取计划任务结果个数</p>
	 * 
	 * @author	王垒, 2018年1月17日上午9:17:18
	 * @since	EnvDust 1.0.0
	 *
	 * @param commId
	 * @return
	 */
	int getCommResultCount(String commId);
	
	/**
	 * 
	 * <p>[功能描述]：获取计划任务结果</p>
	 * 
	 * @author	王垒, 2018年1月17日上午9:17:18
	 * @since	EnvDust 1.0.0
	 *
	 * @param commId
	 * @return
	 */
	List<CommResultModel> getCommResult(String commId);

}
