package com.tcb.env.model;


/**
 * <p>[功能描述]：地图数据映射类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月20日下午2:08:56
 * @since EnvDust 1.0.0
 */
public class MapDeviceModel extends BaseModel {
    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private double deviceX;
    private double deviceY;
    private String statusCode;
    private String statusInfo;
    private String alarmStatus;
    private String userId;//负责人,外键
    private String userName;
    private String userTel;//负责人电话
    private String maxAlarmId;//最大报警ID
    private String levelNo;//报警级别
    private String iconCls;//设备图片
    private String areaName;

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
     * @return the deviceX
     */
    public double getDeviceX() {
        return deviceX;
    }

    /**
     * @param deviceX the deviceX to set
     */
    public void setDeviceX(double deviceX) {
        this.deviceX = deviceX;
    }

    /**
     * @return the deviceY
     */
    public double getDeviceY() {
        return deviceY;
    }

    /**
     * @param deviceY the deviceY to set
     */
    public void setDeviceY(double deviceY) {
        this.deviceY = deviceY;
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
     * @return the statusInfo
     */
    public String getStatusInfo() {
        return statusInfo;
    }

    /**
     * @param statusInfo the statusInfo to set
     */
    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    /**
     * @return the alarmStatus
     */
    public String getAlarmStatus() {
        return alarmStatus;
    }

    /**
     * @param alarmStatus the alarmStatus to set
     */
    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userTel
     */
    public String getUserTel() {
        return userTel;
    }

    /**
     * @param userTel the userTel to set
     */
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getMaxAlarmId() {
        return maxAlarmId;
    }

    public void setMaxAlarmId(String maxAlarmId) {
        this.maxAlarmId = maxAlarmId;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
