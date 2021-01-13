/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.pojo;

import java.sql.Timestamp;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：设备pojo</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 *
 * @author 任崇彬
 * @version 1.0, 2016年3月25日上午11:07:42
 * @since EnvDust 1.0.0
 */
public class Device extends BasePojo {
    private int deviceId = DefaultArgument.INT_DEFAULT;//递增id
    private String deviceCode;//设备编号
    private String deviceMn;//设备mn号
    private String deviceName;//设备名称
    private Manufacturer manufacturer;//所属厂商编号,外键
    private Status status;//设备状态，外键
    private String deviceIp;//设备IP
    private int devicePort = DefaultArgument.INT_DEFAULT;//设备端口
    private String devicePwd;//设备访问密码
    private double deviceX = DefaultArgument.DOUBLE_DEFAULT;//百度设备坐标X
    private double deviceY = DefaultArgument.DOUBLE_DEFAULT;//百度设备坐标Y
    private double gpsX = DefaultArgument.DOUBLE_DEFAULT;//上传设备坐标X
    private double gpsY = DefaultArgument.DOUBLE_DEFAULT;//上传设备坐标Y
    private Area area;//区域id，外键
    private String systemVersion;//系统版本
    private Timestamp inspectTime;//巡检时间
    private String buildFirm;//施工单位
    private User user;//负责人,外键
    private String deviceAddress;//设备地址
    private Oranization oranization;//监督单位,外键
    private String deviceKm;//设备辐射面积
    private boolean staMinute = DefaultArgument.DEL_DEFAULT;//平台统计分钟数据（10分钟）
    private boolean staHour = DefaultArgument.DEL_DEFAULT;//平台统计小时数据
    private boolean staDay = DefaultArgument.DEL_DEFAULT;//平台统计每日数据
    private String replyFlag;//协议版本
    private boolean forceReply = DefaultArgument.DEL_DEFAULT;//强制数据回复
    private String haveAhr;//是否在指定权限组内
    private boolean smsFlag = DefaultArgument.DEL_DEFAULT;//是否发送报警短信
    private double pipeArea;//烟筒直径
    private boolean staFlow;//平台统计标态流量
    private DeviceProject deviceProject;//设备项目
    private int hourCount = DefaultArgument.HOUR_COUNT;//小时内实时数据个数

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
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the oranization
     */
    public Oranization getOranization() {
        return oranization;
    }

    /**
     * @param oranization the oranization to set
     */
    public void setOranization(Oranization oranization) {
        this.oranization = oranization;
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
     * @return the manufacturer
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
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
     * @return the gpsX
     */
    public double getGpsX() {
        return gpsX;
    }

    /**
     * @param gpsX the gpsX to set
     */
    public void setGpsX(double gpsX) {
        this.gpsX = gpsX;
    }

    /**
     * @return the gpsY
     */
    public double getGpsY() {
        return gpsY;
    }

    /**
     * @param gpsY the gpsY to set
     */
    public void setGpsY(double gpsY) {
        this.gpsY = gpsY;
    }

    /**
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Area area) {
        this.area = area;
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
    public Timestamp getInspectTime() {
        return inspectTime;
    }

    /**
     * @param inspectTime the inspectTime to set
     */
    public void setInspectTime(Timestamp inspectTime) {
        this.inspectTime = inspectTime;
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

    public DeviceProject getDeviceProject() {
        return deviceProject;
    }

    public void setDeviceProject(DeviceProject deviceProject) {
        this.deviceProject = deviceProject;
    }

    public int getHourCount() {
        return hourCount;
    }

    public void setHourCount(int hourCount) {
        this.hourCount = hourCount;
    }
}
