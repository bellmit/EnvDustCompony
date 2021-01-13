/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.dao;

import java.util.List;

import com.tcb.env.model.MonitorLevelModel;
import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Monitor;

/**
 * <p>
 * [功能描述]：监测物数据库接口
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月30日上午11:39:03
 * @since EnvDust 1.0.0
 */
public interface IMonitorDao {

    /**
     * <p>
     * [功能描述]：查询检测物
     * </p>
     *
     * @param monitor
     * @return
     * @author 任崇彬, 2016年3月30日上午11:40:13
     * @since EnvDust 1.0.0
     */
    List<Monitor> getMonitor(@Param("monitor") Monitor monitor);

    /**
     * <p>
     * [功能描述]：得到数量
     * </p>
     *
     * @param monitor
     * @return
     * @author 任崇彬, 2016年3月30日下午2:41:38
     * @since EnvDust 1.0.0
     */
    int getCount(@Param("monitor") Monitor monitor);

    /**
     * <p>
     * [功能描述]：增加数据
     * </p>
     *
     * @param monitor
     * @return
     * @author 任崇彬, 2016年3月30日下午3:10:33
     * @since EnvDust 1.0.0
     */
    int insertMonitor(@Param("monitor") Monitor monitor);

    /**
     * <p>
     * [功能描述]：删除数据
     * </p>
     *
     * @param monitorid
     * @return
     * @author 任崇彬, 2016年3月30日下午3:33:32
     * @since EnvDust 1.0.0
     */
    int deleteMonitor(@Param("monitorid") List<Integer> monitorid);

    /**
     * <p>
     * [功能描述]：判断是否存在
     * </p>
     *
     * @param monitorid
     * @param monitorcode
     * @param monitorname
     * @return
     * @author 任崇彬, 2016年3月30日下午3:54:12
     * @since EnvDust 1.0.0
     */
    int getMonitorExist(@Param("monitorid") int monitorid,
                        @Param("monitorcode") String monitorcode,
                        @Param("monitorname") String monitorname);

    /**
     * <p>
     * [功能描述]：更新数据
     * </p>
     *
     * @param list
     * @return
     * @author 任崇彬, 2016年3月30日下午4:06:45
     * @since EnvDust 1.0.0
     */
    int updateMonitor(@Param("list") List<Monitor> list);

    /**
     * <p>
     * [功能描述]：获取监测物名称（通过编号）
     * </p>
     *
     * @param thingCode
     * @return
     * @author 王垒, 2016年6月3日上午9:39:38
     * @since EnvDust 1.0.0
     */
    String getMonitorName(@Param("thingCode") String thingCode);

    /**
     * 查询监测物等级信息
     *
     * @param thingCode
     * @return
     */
    List<MonitorLevelModel> getMonitorLevel(@Param("thingCode") String thingCode);

    /**
     * 查询监测物等级信息(根据数值)
     *
     * @param thingCode
     * @param thingValue
     * @return
     */
    MonitorLevelModel getMonitorValueLevel(
            @Param("thingCode") String thingCode,
            @Param("thingValue") Double thingValue);

}
