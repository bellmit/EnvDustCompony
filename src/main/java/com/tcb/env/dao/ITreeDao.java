package com.tcb.env.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.tcb.env.model.TreeModel;

/**
 * <p>
 * [功能描述]：树形结构操作接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月25日上午8:53:56
 * @since EnvDust 1.0.0
 */
public interface ITreeDao {

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
    ArrayList<TreeModel> getAreaChildrenTree(
            @Param("parentid") String parentid,
            @Param("manuName") String manuName,
            @Param("levelFlag") int levelFlag);

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
    int getSubItemCount(@Param("parentid") String parentid);

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
    int getAuthorityDeviceCount(
            @Param("usercode") String usercode,
            @Param("projectid") String projectid,
            @Param("areaid") int areaid,
            @Param("devicename") String devicename,
            @Param("status") String status,
            @Param("nostatus") String nostatus);

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
    ArrayList<TreeModel> getAuthorityDevice(
            @Param("usercode") String usercode,
            @Param("projectid") String projectid,
            @Param("areaid") int areaid,
            @Param("devicename") String devicename,
            @Param("status") String status,
            @Param("nostatus") String nostatus);

    /**
     * <p>
     * [功能描述]：获取用户区域权限设备数量(通过权限组编码)
     * </p>
     *
     * @param ahrCode
     * @param areaid
     * @param devicename
     * @return
     * @author 王垒, 2016年3月28日上午9:30:52
     * @since EnvDust 1.0.0
     */
    int getAuthorityDeviceCountByAhrCode(@Param("ahrCode") String ahrCode,
                                         @Param("areaid") int areaid, @Param("devicename") String devicename);

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
    ArrayList<TreeModel> getAuthorityDeviceByAhrCode(
            @Param("ahrCode") String ahrCode, @Param("areaid") int areaid,
            @Param("devicename") String devicename);

    /**
     * <p>
     * [功能描述]：获取所有设备信息(权限组对应选中状态)(数量)
     * </p>
     *
     * @param ahrcode
     * @param areaid
     * @param devicename
     * @return
     * @author 王垒, 2016年4月6日下午3:52:22
     * @since EnvDust 1.0.0
     */
    int getAllDeviceCount(@Param("ahrcode") String ahrcode,
                          @Param("areaid") int areaid, @Param("devicename") String devicename);

    /**
     * <p>
     * [功能描述]：获取所有设备信息(权限组对应选中状态)
     * </p>
     *
     * @param ahrcode
     * @param areaid
     * @param devicename
     * @return
     * @author 王垒, 2016年4月6日下午3:52:22
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAllDevice(@Param("ahrcode") String ahrcode,
                                      @Param("areaid") int areaid, @Param("devicename") String devicename);

    /**
     * <p>
     * [功能描述]：获取所有监测物信息(权限组对应选中状态)
     * </p>
     *
     * @param ahrcode
     * @return
     * @author 王垒, 2016年4月6日下午3:52:22
     * @since EnvDust 1.0.0
     */
    ArrayList<TreeModel> getAllMonitor(@Param("ahrcode") String ahrcode);

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
    ArrayList<TreeModel> getAllAuthority(@Param("ahrcode") String ahrcode);

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
    public ArrayList<TreeModel> getUserByAhrCode(
            @Param("ahrcode") String ahrcode);

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
    ArrayList<TreeModel> getModule(@Param("ahrcode") String ahrcode);

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
    ArrayList<TreeModel> getPage(@Param("ahrcode") String ahrcode,
                                 @Param("moduleid") String moduleid);

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
    ArrayList<TreeModel> getControl(@Param("ahrcode") String ahrcode,
                                    @Param("pageid") String pageid);

    /**
     * 获取设备第一个
     *
     * @param userCode
     * @param deviceName
     * @param statusCodeList
     * @return
     */
    ArrayList<TreeModel> getDeviceFirst(
            @Param("userCode") String userCode,
            @Param("deviceName") String deviceName,
            @Param("statusCodeList") List<String> statusCodeList);

    ArrayList<TreeModel> getAllAreaDevices();
}
