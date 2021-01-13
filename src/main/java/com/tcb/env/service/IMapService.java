package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.MapModel;

/**
 * <p>[功能描述]：查询id-name形式操作类接口</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月23日下午2:00:11
 * @since EnvDust 1.0.0
 */
public interface IMapService {

    /**
     * <p>[功能描述]：获取区域id-name</p>
     *
     * @param id
     * @param levelFlag
     * @return
     * @author 王垒, 2016年3月23日下午1:58:41
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAreaMap(int id, int levelFlag);

    /**
     * <p>[功能描述]：获取区域级别flag-name</p>
     *
     * @param id
     * @return
     * @author 王垒, 2016年3月23日下午1:59:02
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAreaLevelFlag(int id);

    List<Integer> getAreaIdFromFlag(Integer id);

    /**
     * <p>[功能描述]：获取区域级别id-name</p>
     *
     * @param id
     * @param levelflag
     * @return
     * @author 王垒, 2016年3月23日下午1:59:02
     * @since EnvDust 1.0.0
     */
    List<MapModel> getAreaLevelId(int id, int levelflag);

    /**
     * <p>[功能描述]：设备获取厂商名称</p>
     *
     * @param mfrCode
     * @return
     * @author 任崇彬, 2016年3月28日下午3:36:22
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceMfrCode(String mfrCode);

    /**
     * <p>[功能描述]：设备获取状态code-name</p>
     *
     * @param statusCode
     * @return
     * @author 任崇彬, 2016年3月29日上午8:34:47
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceStatusCode(String statusCode);

    /**
     * <p>[功能描述]：设备项目负责人Id-name</p>
     *
     * @param devicePrinciple
     * @return
     * @author 任崇彬, 2016年3月31日上午11:21:41
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceprinciple(int devicePrinciple);

    /**
     * <p>[功能描述]：设备监督单位id-name</p>
     *
     * @param orgId
     * @return
     * @author 任崇彬, 2016年3月31日上午11:38:35
     * @since EnvDust 1.0.0
     */
    List<MapModel> getDeviceOversightUnit(int orgId);

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
    List<MapModel> getAuthorityMonitors(String usercode, int thingid,
                                        String thingname, List<String> deviceCodeList);

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
            String usercode,
            String devicecode,
            int thingid,
            String thingname);

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
    List<MapModel> getStatus(String status, String nostatus, String statusType);

    /**
     * 获取下级区域
     * @param id
     * @return
     */
    List<MapModel> getSubAreaMap(int id);
}
