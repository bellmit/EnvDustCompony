package com.tcb.env.message;

/**
 * <p>[功能描述]：原始数据导出模板(平均)</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月5日下午6:12:37
 * @since EnvDust 1.0.0
 */
public class ExportOdmAvgMessage {

    private String deviceCode;
    private String deviceName;
    private String deviceMn;
    private String thingName;
    private String updateTypeName;
    private String thingAvg;
    private String thingMax;
    private String thingMin;
    private String beginTime;
    private String endTime;
    private String statusName;
    private String updateTime;

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
     * @return the thingAvg
     */
    public String getThingAvg() {
        return thingAvg;
    }

    /**
     * @param thingAvg the thingAvg to set
     */
    public void setThingAvg(String thingAvg) {
        this.thingAvg = thingAvg;
    }

    /**
     * @return the thingMax
     */
    public String getThingMax() {
        return thingMax;
    }

    /**
     * @param thingMax the thingMax to set
     */
    public void setThingMax(String thingMax) {
        this.thingMax = thingMax;
    }

    /**
     * @return the thingMin
     */
    public String getThingMin() {
        return thingMin;
    }

    /**
     * @param thingMin the thingMin to set
     */
    public void setThingMin(String thingMin) {
        this.thingMin = thingMin;
    }

    /**
     * @return the updateTypeName
     */
    public String getUpdateTypeName() {
        return updateTypeName;
    }

    /**
     * @param updateTypeName the updateTypeName to set
     */
    public void setUpdateTypeName(String updateTypeName) {
        this.updateTypeName = updateTypeName;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the beginTime
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
