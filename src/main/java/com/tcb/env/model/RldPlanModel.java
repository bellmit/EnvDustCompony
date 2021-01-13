package com.tcb.env.model;

/**
 * <p>[功能描述]：设置获取实时数据Model</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月7日上午8:51:14
 * @since EnvDust 1.0.0
 */
public class RldPlanModel extends BaseModel {

    private String commId;
    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private String areaId;
    private String areaName;
    private String cnCode;
    private String cnName;
    private String statusName;
    private String excuteTime;
    private String projectId;
    private String projectName;

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
     * @return the commId
     */
    public String getCommId() {
        return commId;
    }

    /**
     * @param commId the commId to set
     */
    public void setCommId(String commId) {
        this.commId = commId;
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
     * @return the cnCode
     */
    public String getCnCode() {
        return cnCode;
    }

    /**
     * @param cnCode the cnCode to set
     */
    public void setCnCode(String cnCode) {
        this.cnCode = cnCode;
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
