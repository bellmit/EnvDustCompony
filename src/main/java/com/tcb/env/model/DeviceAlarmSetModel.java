package com.tcb.env.model;

/**
 * <p>[功能描述]：报警门限任务model</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月1日上午8:43:35
 * @since EnvDust 1.0.0
 */
public class DeviceAlarmSetModel extends BaseModel {

    private String dasId;
    private String areaId;//区域id，外键
    private String areaName;//区域名称
    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private String thingName;
    private String maxValue;
    private String minValue;
    private String dataFlag;
    private String levelNo;
    private String cnName;
    private String statusName;
    private String excuteTime;
    private String projectId;
    private String projectName;

    /**
     * @return the dasId
     */
    public String getDasId() {
        return dasId;
    }

    /**
     * @param dasId the dasId to set
     */
    public void setDasId(String dasId) {
        this.dasId = dasId;
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
     * @return the maxValue
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the minValue
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    /**
     * @return the cnName
     */
    public String getCnName() {
        return cnName;
    }

    /**
     * @param cnName the cnName to set
     */
    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * @return the excuteTime
     */
    public String getExcuteTime() {
        return excuteTime;
    }

    /**
     * @param excuteTime the excuteTime to set
     */
    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
