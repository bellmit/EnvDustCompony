package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.model.MapModel;

/**
 * <p>[功能描述]：查询id-name值</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月23日下午1:56:08
 * @since EnvDust 1.0.0
 */
public interface IMapDao {

    /**
     * <p>[功能描述]：获取区域id-name</p>
     *
     * @param id
     * @param levelflag
     * @return
     * @author 王垒, 2016年3月23日下午1:58:41
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAreaMap(@Param("id") int id, @Param("levelflag") int levelflag);

    /**
     * <p>[功能描述]：获取区域级别flag-name</p>
     *
     * @param id
     * @return
     * @author 王垒, 2016年3月23日下午1:59:02
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAreaLevelFlag(@Param("id") int id);

    /**
     * <p>[功能描述]：获取区域级别flag-name</p>
     *
     * @param id
     * @return
     * @author 王垒, 2016年3月23日下午1:59:02
     * @since EnvDust 1.0.0
     */
    List<Integer> getAreaIdFromFlag(@Param("id") Integer id);

    /**
     * <p>[功能描述]：获取区域级别id-name</p>
     *
     * @param id
     * @param levelflag
     * @return
     * @author 王垒, 2016年3月23日下午1:59:02
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAreaLevelId(@Param("id") int id, @Param("levelflag") int levelflag);

    /**
     * <p>[功能描述]：设备获取厂商，编号-名称</p>
     *
     * @param mfrCode
     * @return
     * @author 任崇彬, 2016年3月28日下午3:40:16
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceMfrCode(@Param("mfrCode") String mfrCode);

    /**
     * <p>[功能描述]设备获取状态，编号-名称：</p>
     *
     * @param statusCode
     * @return
     * @author 任崇彬, 2016年3月29日上午8:36:57
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceStatusCode(@Param("statusCode") String statusCode);

    /**
     * <p>[功能描述]：设备获取负责人，ID-名称</p>
     *
     * @param devicePrinciple
     * @return
     * @author 任崇彬, 2016年3月31日上午11:24:08
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceprinciple(@Param("devicePrinciple") int devicePrinciple);

    /**
     * <p>[功能描述]：设备获取组织iD-name</p>
     *
     * @param orgId
     * @return
     * @author 任崇彬, 2016年3月31日上午11:40:55
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceOversightUnit(@Param("orgId") int orgId);

    /**
     * <p>
     * [功能描述]：获取权限监测物，编号-名称（单位）
     * </p>
     *
     * @param usercode
     * @param thingid
     * @param thingname
     * @param deviceCodeList
     * @return
     * @author 王垒, 2016年3月31日下午12:45:10
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAuthorityMonitors(
            @Param("usercode") String usercode,
            @Param("thingid") int thingid,
            @Param("thingname") String thingname,
            @Param("deviceCodeList") List<String> deviceCodeList);


    /**
     * <p>
     * [功能描述]：获取权限设备监测物，编号-名称（单位）
     * </p>
     *
     * @param usercode
     * @param devicecode
     * @param thingid
     * @param thingname
     * @return
     * @author 王垒, 2016年3月31日下午12:45:10
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAuthorityDeviceMonitors(
            @Param("usercode") String usercode,
            @Param("devicecode") String devicecode,
            @Param("thingid") int thingid,
            @Param("thingname") String thingname);

    /**
     * <p>[功能描述]：获取系统状态值,编号-名称</p>
     *
     * @param status：状态值
     * @param nostatus：非状态值
     * @param statusType：状态类型
     * @return
     * @author 王垒, 2016年4月28日上午11:40:07
     * @since EnvDust 1.0.0
     */
    List<MapModel> getStatus(
            @Param("status") String status,
            @Param("nostatus") String nostatus,
            @Param("statusType") String statusType);

    /**
     * 获取下级区域
     * @param id
     * @return
     */
    List<MapModel> getSubAreaMap(@Param("id") int id);
}
