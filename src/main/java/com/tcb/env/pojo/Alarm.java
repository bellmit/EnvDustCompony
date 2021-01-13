package com.tcb.env.pojo;

import java.sql.Timestamp;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：报警pojo</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年4月26日下午1:50:39
 * @since EnvDust 1.0.0
 */
public class Alarm extends BasePojo {

    private int alarmId;
    private Status status;
    private Device device;
    private String alarmInfo;
    private String alarmAction;
    private String actionUser;
    private String alarmStatus;
    private Timestamp alarmTime;
    private Timestamp actionTime;
    private String beginAlarmTime;
    private String endAlarmTime;
    private Monitor monitor;
    private double upValue = DefaultArgument.DOUBLE_DEFAULT;
    private double lowValue = DefaultArgument.DOUBLE_DEFAULT;
    private double thingValue = DefaultArgument.DOUBLE_DEFAULT;
    private boolean executeUpdate;
    private Integer receiveFlag;
    private boolean sendFlag;
    private String levelNo;
    private Timestamp dataTime;

    /**
     * @return the alarmId
     */
    public int getAlarmId() {
        return alarmId;
    }

    /**
     * @param alarmId the alarmId to set
     */
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
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
     * @return the alarmInfo
     */
    public String getAlarmInfo() {
        return alarmInfo;
    }

    /**
     * @param alarmInfo the alarmInfo to set
     */
    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    /**
     * @return the alarmAction
     */
    public String getAlarmAction() {
        return alarmAction;
    }

    /**
     * @param alarmAction the alarmAction to set
     */
    public void setAlarmAction(String alarmAction) {
        this.alarmAction = alarmAction;
    }

    /**
     * @return the actionUser
     */
    public String getActionUser() {
        return actionUser;
    }

    /**
     * @param actionUser the actionUser to set
     */
    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
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
     * @return the alarmTime
     */
    public Timestamp getAlarmTime() {
        return alarmTime;
    }

    /**
     * @param alarmTime the alarmTime to set
     */
    public void setAlarmTime(Timestamp alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * @return the actionTime
     */
    public Timestamp getActionTime() {
        return actionTime;
    }

    /**
     * @param actionTime the actionTime to set
     */
    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    /**
     * @return the beginAlarmTime
     */
    public String getBeginAlarmTime() {
        return beginAlarmTime;
    }

    /**
     * @param beginAlarmTime the beginAlarmTime to set
     */
    public void setBeginAlarmTime(String beginAlarmTime) {
        this.beginAlarmTime = beginAlarmTime;
    }

    /**
     * @return the endAlarmTime
     */
    public String getEndAlarmTime() {
        return endAlarmTime;
    }

    /**
     * @param endAlarmTime the endAlarmTime to set
     */
    public void setEndAlarmTime(String endAlarmTime) {
        this.endAlarmTime = endAlarmTime;
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
     * @return the upValue
     */
    public double getUpValue() {
        return upValue;
    }

    /**
     * @param upValue the upValue to set
     */
    public void setUpValue(double upValue) {
        this.upValue = upValue;
    }

    /**
     * @return the lowValue
     */
    public double getLowValue() {
        return lowValue;
    }

    /**
     * @param lowValue the lowValue to set
     */
    public void setLowValue(double lowValue) {
        this.lowValue = lowValue;
    }

    /**
     * @return the thingValue
     */
    public double getThingValue() {
        return thingValue;
    }

    /**
     * @param thingValue the thingValue to set
     */
    public void setThingValue(double thingValue) {
        this.thingValue = thingValue;
    }

    /**
     * @return the executeUpdate
     */
    public boolean getExecuteUpdate() {
        return executeUpdate;
    }

    /**
     * @param executeUpdate the executeUpdate to set
     */
    public void setExecuteUpdate(boolean executeUpdate) {
        this.executeUpdate = executeUpdate;
    }

    public Integer getReceiveFlag() {
        return receiveFlag;
    }

    public void setReceiveFlag(Integer receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    public boolean getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(boolean sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }
}
