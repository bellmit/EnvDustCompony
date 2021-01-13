package com.tcb.env.pojo;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：设备报警pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年5月31日下午2:04:33
 * @since EnvDust 1.0.0
 */
public class DeviceAlarmSet extends BasePojo {

    private int dasId;
    private Device device;
    private Monitor monitor;
    private double maxValue = DefaultArgument.DOUBLE_DEFAULT;
    private double minValue = DefaultArgument.DOUBLE_DEFAULT;
    private int dataFlag = 1;//默认实测值
    private int levelNo;
    private CommMain commMain;
    private DeviceProject deviceProject;

    /**
     * @return the dasId
     */
    public int getDasId() {
        return dasId;
    }

    /**
     * @param dasId the dasId to set
     */
    public void setDasId(int dasId) {
        this.dasId = dasId;
    }

    /**
     * @return the device
     */
    public Device getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * @return the monitor
     */
    public Monitor getMonitor() {
        return monitor;
    }

    /**
     * @param monitor the monitor to set
     */
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    /**
     * @return the maxValue
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the minValue
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the commMain
     */
    public CommMain getCommMain() {
        return commMain;
    }

    /**
     * @param commMain the commMain to set
     */
    public void setCommMain(CommMain commMain) {
        this.commMain = commMain;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public int getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(int dataFlag) {
        this.dataFlag = dataFlag;
    }

    public DeviceProject getDeviceProject() {
        return deviceProject;
    }

    public void setDeviceProject(DeviceProject deviceProject) {
        this.deviceProject = deviceProject;
    }

}
