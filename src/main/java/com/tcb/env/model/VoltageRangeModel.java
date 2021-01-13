package com.tcb.env.model;

/**
 * <p>[功能描述]：零点量程model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年1月9日上午11:50:25
 * @since EnvDust 1.0.0
 */
public class VoltageRangeModel extends BaseModel {

    private String commId;
    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private String xZeroVolt;
    private String xScaleVolt;
    private String xScaleConc;
    private String cnCode;
    private String cnName;
    private String statusCode;
    private String statusName;
    private String excuteTime;
    private String areaId;        //区域id，外键
    private String areaName;    //区域名称
    private String projectId;
    private String projectName;

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
     * @return the xZeroVolt
     */
    public String getxZeroVolt() {
        return xZeroVolt;
    }

    /**
     * @param xZeroVolt the xZeroVolt to set
     */
    public void setxZeroVolt(String xZeroVolt) {
        this.xZeroVolt = xZeroVolt;
    }

    /**
     * @return the xScaleVolt
     */
    public String getxScaleVolt() {
        return xScaleVolt;
    }

    /**
     * @param xScaleVolt the xScaleVolt to set
     */
    public void setxScaleVolt(String xScaleVolt) {
        this.xScaleVolt = xScaleVolt;
    }

    /**
     * @return the xScaleConc
     */
    public String getxScaleConc() {
        return xScaleConc;
    }

    /**
     * @param xScaleConc the xScaleConc to set
     */
    public void setxScaleConc(String xScaleConc) {
        this.xScaleConc = xScaleConc;
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
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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
