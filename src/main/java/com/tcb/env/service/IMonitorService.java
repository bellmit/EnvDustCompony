/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service;

import java.util.List;

import com.tcb.env.model.MonitorLevelModel;
import com.tcb.env.pojo.Monitor;

/**
 * <p>[功能描述]：J检测服务接口</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月30日上午11:31:56
 * @since EnvDust 1.0.0
 */
public interface IMonitorService {

    /**
     * <p>[功能描述]：查询检测物</p>
     *
     * @param monitor
     * @return
     * @author 任崇彬, 2016年3月30日上午11:35:41
     * @since EnvDust 1.0.0
     */
    List<Monitor> getMonitor(Monitor monitor);

    /**
     * <p>[功能描述]：得到数量</p>
     *
     * @param monitor
     * @return
     * @author 任崇彬, 2016年3月30日下午2:39:43
     * @since EnvDust 1.0.0
     */
    int getCount(Monitor monitor);

    /**
     * <p>[功能描述]：增加数据</p>
     *
     * @param monitor
     * @return
     * @throws Exception
     * @author 任崇彬, 2016年3月30日下午3:09:04
     * @since EnvDust 1.0.0
     */
    int insertMonitor(Monitor monitor) throws Exception;

    /**
     * <p>[功能描述]：删除数据</p>
     *
     * @param monitorid
     * @return
     * @throws Exception
     * @author 任崇彬, 2016年3月30日下午3:31:45
     * @since EnvDust 1.0.0
     */
    int deleteMonitor(List<Integer> monitorid) throws Exception;

    /**
     * <p>[功能描述]：判断名称是否存在</p>
     *
     * @param monitorid
     * @param monitorcode
     * @param monitorname
     * @return
     * @author 任崇彬, 2016年3月30日下午3:51:47
     * @since EnvDust 1.0.0
     */
    int getMonitorExist(int monitorid, String monitorcode, String monitorname);

    /**
     * <p>[功能描述]：更改数据</p>
     *
     * @param listMonitor
     * @return
     * @throws Exception
     * @author 任崇彬, 2016年3月30日下午4:05:16
     * @since EnvDust 1.0.0
     */
    int updateMonitor(List<Monitor> listMonitor) throws Exception;

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
    String getMonitorName(String thingCode);

    /**
     * 查询监测物等级信息
     *
     * @param thingCode
     * @return
     */
    List<MonitorLevelModel> getMonitorLevel(String thingCode);

    /**
     * 查询监测物等级信息(根据数值)
     *
     * @param thingCode
     * @param thingValue
     * @return
     */
    MonitorLevelModel getMonitorValueLevel(String thingCode, Double thingValue);

}
