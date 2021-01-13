package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.CommResultModel;
import com.tcb.env.pojo.CommMain;
import com.tcb.env.pojo.CommPrepare;
import com.tcb.env.pojo.CommStatus;

/**
 * 
 * <p>
 * [功能描述]：计划任务信息Dao
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月31日下午3:56:38
 * @since EnvDust 1.0.0
 *
 */
public interface ICommMainDao {

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
	int getCommMainCount(@Param("commMain") CommMain commMain,
			@Param("listDeviceCode") List<String> listDeviceCode,
			@Param("listCnCode") List<String> listCnCode);

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
	List<CommMain> getCommMain(@Param("commMain") CommMain commMain,
			@Param("listDeviceCode") List<String> listDeviceCode,
			@Param("listCnCode") List<String> listCnCode);

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
	int insertCommPrepare(@Param("commPrepare") CommPrepare commPrepare);

	/**
	 * 
	 * <p>
	 * [功能描述]：插入计划任务信息主表
	 * </p>
	 * 
	 * @author 王垒, 2016年5月31日下午4:02:06
	 * @since EnvDust 1.0.0
	 *
	 * @param listCommMain
	 * @return
	 */
	int insertCommMain(@Param("listCommMain") List<CommMain> listCommMain);

	/**
	 * 
	 * <p>
	 * [功能描述]：获取发送状态信息
	 * </p>
	 * 
	 * @author 王垒, 2016年6月1日下午3:56:20
	 * @since EnvDust 1.0.0
	 *
	 * @param type
	 * @param code
	 * @return
	 */
	CommStatus getCommStatus(@Param("type") String type,
			@Param("code") String code);

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
	int deleteCommMain(@Param("listCommId") List<Integer> listCommId);

	/**
	 * 
	 * <p>
	 * [功能描述]：删除计划任务信息
	 * </p>
	 * 
	 * @author 王垒, 2016年6月6日下午2:05:31
	 * @since EnvDust 1.0.0
	 *
	 * @param cnCode
	 * @param listDeviceCode
	 * @return
	 */
	int deleteCommMainCn(@Param("cnCode") String cnCode,
			@Param("listDeviceCode") List<String> listDeviceCode);

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
	String getMainCommId(@Param("cnCode") String cnCode,
			@Param("deviceCode") String deviceCode);
	
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
	int getCommResultCount(@Param("commId") String commId);
	
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
	List<CommResultModel> getCommResult(@Param("commId") String commId);

}
