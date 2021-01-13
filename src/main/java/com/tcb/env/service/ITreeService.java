package com.tcb.env.service;

import java.util.ArrayList;
import java.util.List;

import com.tcb.env.model.TreeModel;

/**
 * <p>
 * [功能描述]：Tree操作服务类接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月25日上午9:14:27
 * @since EnvDust 1.0.0
 */
public interface ITreeService {

    /**
     * <p>
     * [功能描述]：获取子类区域数据
     * </p>
     *
     * @param parentid
     * @return
     * @author 王垒, 2016年3月25日上午8:56:23
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAreaChildrenTree(String parentid, String manuName, int levelFlag);

    /**
     * <p>
     * [功能描述]：获取子节点个数
     * </p>
     *
     * @param parentid
     * @return
     * @author 王垒, 2016年3月25日上午9:30:54
     * @since EnvDust 1.0.0
     */
    int getSubItemCount(String parentid);

    /**
     * <p>
     * [功能描述]：获取用户区域权限设备数量
     * </p>
     *
     * @param usercode
     * @param areaid
     * @param devicename
     * @param status
     * @param nostatus
     * @return
     * @author 王垒, 2016年3月28日上午9:30:52
     * @since EnvDust 1.0.0
     */
    int getAuthorityDeviceCount(String usercode, String projectid, int areaid,
                                String devicename, String status, String nostatus);

    /**
     * <p>
     * [功能描述]：获取用户区域权限设备信息
     * </p>
     *
     * @param usercode
     * @param projectid
     * @param areaid
     * @param devicename
     * @param status
     * @param nostatus
     * @return
     * @author 王垒, 2016年3月28日上午9:30:52
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAuthorityDevices(String usercode, String projectid,
                                             int areaid, String devicename, String status, String nostatus);

    /**
     * 获取全区域N和NT状态设备信息
     * @return
     */
    ArrayList<TreeModel> getAllAreaDevices();

    /**
     * <p>
     * [功能描述]：获取用户区域权限设备信息数量(通过权限组编码)
     * </p>
     *
     * @param ahrCode
     * @param areaid
     * @param devicename
     * @return
     * @author 王垒, 2016年3月28日上午9:30:52
     * @since EnvDust 1.0.0
     */
    int getAuthorityDeviceCountByAhrCode(String ahrCode, int areaid,
                                         String devicename);

    /**
     * <p>
     * [功能描述]：获取用户区域权限设备信息(通过权限组编码)
     * </p>
     *
     * @param ahrCode
     * @param areaid
     * @param devicename
     * @return
     * @author 王垒, 2016年3月28日上午9:30:52
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAuthorityDeviceByAhrCode(String ahrCode,
                                                     int areaid, String devicename);

    /**
     * <p>
     * [功能描述]：获取所有设备信息(权限组对应选中状态)(数量)
     * </p>
     *
     * @param ahrCode
     * @param areaId
     * @param deviceName
     * @return
     * @author 王垒, 2016年4月6日下午3:52:22
     * @since EnvDust 1.0.0
     */
    int getAllDevicesCount(String ahrCode, int areaId, String deviceName);

    /**
     * <p>
     * [功能描述]：获取所有设备信息(权限组对应选中状态)
     * </p>
     *
     * @param ahrCode
     * @param areaId
     * @param deviceName
     * @return
     * @author 王垒, 2016年4月6日下午3:52:22
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAllDevices(String ahrCode, int areaId,
                                       String deviceName);

    /**
     * <p>
     * [功能描述]：获取所有监测物信息(用户对应选中状态)
     * </p>
     *
     * @param ahrCode
     * @return
     * @author 王垒, 2016年4月6日下午3:52:22
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAllMonitor(String ahrCode);

    /**
     * <p>
     * [功能描述]：查询所有权限组数据
     * </p>
     *
     * @param ahrcode
     * @return
     * @author 王垒, 2016年4月15日下午11:26:31
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAllAuthority(String ahrcode);

    /**
     * <p>
     * [功能描述]：查询权限在组用户数据
     * </p>
     *
     * @param ahrcode
     * @return
     * @author 王垒, 2016年4月15日下午11:28:14
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getUserByAhrCode(String ahrcode);

    /**
     * <p>
     * [功能描述]：获取所有系统模块
     * </p>
     *
     * @param ahrcode
     * @return
     * @author 王垒, 2016年4月18日上午9:13:21
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getModule(String ahrcode);

    /**
     * <p>
     * [功能描述]：获取模块下所有页面
     * </p>
     *
     * @param ahrcode
     * @param moduleid
     * @return
     * @author 王垒, 2016年4月18日上午9:13:46
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getPage(String ahrcode, String moduleid);

    /**
     * <p>
     * [功能描述]：获取页面下所有控件
     * </p>
     *
     * @param ahrcode
     * @param pageid
     * @return
     * @author 王垒, 2016年4月18日上午9:14:11
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getControl(String ahrcode, String pageid);

    /**
     * <p>
     * [功能描述]：获取权限底层结点区域
     * </p>
     *
     * @param projectId
     * @param listAreaId
     * @param parentAreaId
     * @param usercode
     * @return
     * @author 王垒, 2016年7月1日下午1:45:26
     * @since EnvDust 1.0.0
     */
    List<Integer> getAuthorityBottomArea(String projectId, List<Integer> listAreaId,
                                         int parentAreaId, String usercode);

    /**
     * <p>
     * [功能描述]：获取用户权限区域
     * </p>
     *
     * @param userCode
     * @return
     * @author 王垒, 2016年3月28日上午9:30:52
     * @since EnvDust 1.0.0
     */
    List<Integer> getAuthorityAreaId(String userCode);

    /**
     * 获取在线设备第一个
     *
     * @param userCode
     * @param deviceName
     * @param statusCodeList
     * @return
     */
    TreeModel getDeviceFirst(String userCode, String deviceName,List<String> statusCodeList);

}
