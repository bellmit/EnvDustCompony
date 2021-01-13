package com.tcb.env.model;

/**
 * <p>[功能描述]：监测物页面传递类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月29日下午2:43:24
 * @since EnvDust 1.0.0
 */
public class MonitorStorageModel extends BaseModel {

    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private String areaId;
    private String areaName;
    private String thingCode;
    private String thingName;
    private String deviceStatus;
    private String deviceStatusInfo;
    private Double monitorValue;
    private Double zsMonitorValue;
    private String frequeTime;

    /**
     * @return the deviceCode
     */
    public String getDeviceCode() {
        return deviceCode;
    }

    /**
     * @param deviceCode the deviceCode to set
     */
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    /**
     * @return the deviceMn
     */
    public String getDeviceMn() {
        return deviceMn;
    }

    /**
     * @param deviceMn the deviceMn to set
     */
    public void setDeviceMn(String deviceMn) {
        this.deviceMn = deviceMn;
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the thingCode
     */
    public String getThingCode() {
        return thingCode;
    }

    /**
     * @param thingCode the thingCode to set
     */
    public void setThingCode(String thingCode) {
        this.thingCode = thingCode;
    }

    /**
     * @return the thingName
     */
    public String getThingName() {
        return thingName;
    }

    /**
     * @param thingName the thingName to set
     */
    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    /**
     * @return the deviceStatus
     */
    public String getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * @param deviceStatus the deviceStatus to set
     */
    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    /**
     * @return the deviceStatusInfo
     */
    public String getDeviceStatusInfo() {
        return deviceStatusInfo;
    }

    /**
     * @param deviceStatusInfo the deviceStatusInfo to set
     */
    public void setDeviceStatusInfo(String deviceStatusInfo) {
        this.deviceStatusInfo = deviceStatusInfo;
    }

    /**
     * @return the monitorValue
     */
    public Double getMonitorValue() {
        return monitorValue;
    }

    /**
     * @param monitorValue the monitorValue to set
     */
    public void setMonitorValue(Double monitorValue) {
        this.monitorValue = monitorValue;
    }

    public Double getZsMonitorValue() {
        return zsMonitorValue;
    }

    public void setZsMonitorValue(Double zsMonitorValue) {
        this.zsMonitorValue = zsMonitorValue;
    }

    /**
     * @return the frequeTime
     */
    public String getFrequeTime() {
        return frequeTime;
    }

    /**
     * @param frequeTime the frequeTime to set
     */
    public void setFrequeTime(String frequeTime) {
        this.frequeTime = frequeTime;
    }


}
