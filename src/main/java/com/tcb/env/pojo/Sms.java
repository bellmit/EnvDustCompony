package com.tcb.env.pojo;

import java.sql.Timestamp;

/**
 * <p>[功能描述]：短信设置pojo</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年4月12日上午9:49:21
 * @since EnvDust 1.0.0
 */
public class Sms extends BasePojo {

    private long smsId;
    private Area area;
    private Device device;
    private Status status;
    private String thingCode;
    private String thingName;
    private Timestamp beginTime;
    private Timestamp endTime;
    private boolean effectiveFlag;
    private Integer receiveFlag;

    /**
     * @return the smsId
     */
    public long getSmsId() {
        return smsId;
    }

    /**
     * @param smsId the smsId to set
     */
    public void setSmsId(long smsId) {
        this.smsId = smsId;
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
     * @return the beginTime
     */
    public Timestamp getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return the endTime
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the effectiveFlag
     */
    public boolean getEffectiveFlag() {
        return effectiveFlag;
    }

    /**
     * @param effectiveFlag the effectiveFlag to set
     */
    public void setEffectiveFlag(boolean effectiveFlag) {
        this.effectiveFlag = effectiveFlag;
    }

    public Integer getReceiveFlag() {
        return receiveFlag;
    }

    public void setReceiveFlag(Integer receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

}
