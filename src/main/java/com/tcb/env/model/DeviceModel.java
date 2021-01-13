/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.model;


import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：DeviceModel</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月28日上午9:57:20
 * @since EnvDust 1.0.0
 */
public class DeviceModel extends BaseModel {
    private int deviceId = DefaultArgument.INT_DEFAULT;//递增id
    private String deviceCode;//设备编号
    private String deviceName;//设备名称
    private String deviceMn;//设备mn号
    private String mfrCode;//所属厂商编号
    private String mfrName;//所属厂商名称
    private String statusCode;//设备状态编号
    private String statusName;//状态名称
    private String deviceIp;//设备IP
    private int devicePort;//设备端口
    private String devicePwd;//设备访问密码
    private double deviceX;//设备坐标X
    private double deviceY;//设备坐标Y
    private String areaId;//区域id，外键
    private String areaName;//区域名称
    private String systemVersion;//系统版本
    private String inspectTime;//巡检时间
    private String buildFirm;//施工单位
    private String userId;//负责人,外键
    private String userName;
    private String userTel;//负责人电话
    private String userRemark;//负责人备注
    private String deviceAddress;//设备地址
    private String orgId;//监督单位,外键
    private String orgName;
    private String orgLiaison;//监督单位负责人
    private String deviceKm;//设备监测面积
    private boolean staMinute;//平台统计分钟数据（10分钟）
    private boolean staHour;//平台统计小时数据
    private boolean staDay;//平台统计每日数据
    private String replyFlag;//协议版本
    private boolean forceReply;//强制数据回复
    private String haveAhr;//是够权限设备
    private String haveAhrInfo;//是够权限设备显示文字
    private boolean smsFlag;//是否发送报警短信
    private double pipeArea;//烟筒面积
    private boolean staFlow;//平台统计标态流量
    private String projectId;//设备项目ID
    private String projectCode;//设备项目编码
    private String projectName;//设备项目名称
    private String projectOrder;//设备项目序号
    private String hourCount;//小时内实时数据个数

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
     * @return the buildFirm
     */
    public String getBuildFirm() {
        return buildFirm;
    }

    /**
     * @param buildFirm the buildFirm to set
     */
    public void setBuildFirm(String buildFirm) {
        this.buildFirm = buildFirm;
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

    /**
     * @return the userRemark
     */
    public String getUserRemark() {
        return userRemark;
    }

    /**
     * @param userRemark the userRemark to set
     */
    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    /**
     * @return the deviceAddress
     */
    public String getDeviceAddress() {
        return deviceAddress;
    }

    /**
     * @param deviceAddress the deviceAddress to set
     */
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    /**
     * @return the orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the deviceId
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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
     * @return the mfrCode
     */
    public String getMfrCode() {
        return mfrCode;
    }

    /**
     * @param mfrCode the mfrCode to set
     */
    public void setMfrCode(String mfrCode) {
        this.mfrCode = mfrCode;
    }

    /**
     * @return the mfrName
     */
    public String getMfrName() {
        return mfrName;
    }

    /**
     * @param mfrName the mfrName to set
     */
    public void setMfrName(String mfrName) {
        this.mfrName = mfrName;
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
     * @return the deviceIp
     */
    public String getDeviceIp() {
        return deviceIp;
    }

    /**
     * @param deviceIp the deviceIp to set
     */
    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    /**
     * @return the devicePort
     */
    public int getDevicePort() {
        return devicePort;
    }

    /**
     * @param devicePort the devicePort to set
     */
    public void setDevicePort(int devicePort) {
        this.devicePort = devicePort;
    }

    /**
     * @return the devicePwd
     */
    public String getDevicePwd() {
        return devicePwd;
    }

    /**
     * @param devicePwd the devicePwd to set
     */
    public void setDevicePwd(String devicePwd) {
        this.devicePwd = devicePwd;
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
     * @return the systemVersion
     */
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * @param systemVersion the systemVersion to set
     */
    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    /**
     * @return the inspectTime
     */
    public String getInspectTime() {
        return inspectTime;
    }

    /**
     * @param inspectTime the inspectTime to set
     */
    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }

    /**
     * @return the orgLiaison
     */
    public String getOrgLiaison() {
        return orgLiaison;
    }

    /**
     * @param orgLiaison the orgLiaison to set
     */
    public void setOrgLiaison(String orgLiaison) {
        this.orgLiaison = orgLiaison;
    }

    /**
     * @return the deviceKm
     */
    public String getDeviceKm() {
        return deviceKm;
    }

    /**
     * @param deviceKm the deviceKm to set
     */
    public void setDeviceKm(String deviceKm) {
        this.deviceKm = deviceKm;
    }

    /**
     * @return the staMinute
     */
    public boolean getStaMinute() {
        return staMinute;
    }

    /**
     * @param staMinute the staMinute to set
     */
    public void setStaMinute(boolean staMinute) {
        this.staMinute = staMinute;
    }

    /**
     * @return the staHour
     */
    public boolean getStaHour() {
        return staHour;
    }

    /**
     * @param staHour the staHour to set
     */
    public void setStaHour(boolean staHour) {
        this.staHour = staHour;
    }

    /**
     * @return the replyFlag
     */
    public String getReplyFlag() {
        return replyFlag;
    }

    /**
     * @param replyFlag the replyFlag to set
     */
    public void setReplyFlag(String replyFlag) {
        this.replyFlag = replyFlag;
    }

    /**
     * @return the forceReply
     */
    public boolean getForceReply() {
        return forceReply;
    }

    /**
     * @param forceReply the forceReply to set
     */
    public void setForceReply(boolean forceReply) {
        this.forceReply = forceReply;
    }

    /**
     * @return the staDay
     */
    public boolean getStaDay() {
        return staDay;
    }

    /**
     * @param staDay the staDay to set
     */
    public void setStaDay(boolean staDay) {
        this.staDay = staDay;
    }

    /**
     * @return the haveAhr
     */
    public String getHaveAhr() {
        return haveAhr;
    }

    /**
     * @param haveAhr the haveAhr to set
     */
    public void setHaveAhr(String haveAhr) {
        this.haveAhr = haveAhr;
    }

    /**
     * @return the haveAhrInfo
     */
    public String getHaveAhrInfo() {
        return haveAhrInfo;
    }

    /**
     * @param haveAhrInfo the haveAhrInfo to set
     */
    public void setHaveAhrInfo(String haveAhrInfo) {
        this.haveAhrInfo = haveAhrInfo;
    }

    /**
     * @return the smsFlag
     */
    public boolean getSmsFlag() {
        return smsFlag;
    }

    /**
     * @param smsFlag the smsFlag to set
     */
    public void setSmsFlag(boolean smsFlag) {
        this.smsFlag = smsFlag;
    }

    /**
     * @return the pipeArea
     */
    public double getPipeArea() {
        return pipeArea;
    }

    /**
     * @param pipeArea the pipeArea to set
     */
    public void setPipeArea(double pipeArea) {
        this.pipeArea = pipeArea;
    }

    /**
     * @return the staFlow
     */
    public boolean getStaFlow() {
        return staFlow;
    }

    /**
     * @param staFlow the staFlow to set
     */
    public void setStaFlow(boolean staFlow) {
        this.staFlow = staFlow;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectOrder() {
        return projectOrder;
    }

    public void setProjectOrder(String projectOrder) {
        this.projectOrder = projectOrder;
    }

    public String getHourCount() {
        return hourCount;
    }

    public void setHourCount(String hourCount) {
        this.hourCount = hourCount;
    }
}
