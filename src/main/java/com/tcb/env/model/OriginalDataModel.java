package com.tcb.env.model;

import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：原始数据页面传递类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年6月22日上午11:14:29
 * @since EnvDust 1.0.0
 */
public class OriginalDataModel extends BaseModel {

    private int storageId;
    private String deviceCode;
    private String deviceName;
    private String deviceMn;
    private String thingCode;
    private String thingName;
    private String thingUnit;
    private String thingRtd;
    private String thingAvg;
    private String thingMax;
    private String thingMin;
    private String thingZsRtd;
    private String thingZsAvg;
    private String thingZsMax;
    private String thingZsMin;
    private String updateType;
    private String updateTypeName;
    private String updateTime;
    private String rtdTime;
    private String beginTime;
    private String endTime;
    private String statusCode;
    private String statusName;
    private int rowIndex = DefaultArgument.INT_DEFAULT;
    private int rowCount = DefaultArgument.INT_DEFAULT;

    /**
     * @return the storageId
     */
    public int getStorageId() {
        return storageId;
    }

    /**
     * @param storageId the storageId to set
     */
    public void setStorageId(int storageId) {
        this.storageId = storageId;
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
     * @return the thingRtd
     */
    public String getThingRtd() {
        return thingRtd;
    }

    /**
     * @param thingRtd the thingRtd to set
     */
    public void setThingRtd(String thingRtd) {
        this.thingRtd = thingRtd;
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

    public String getThingZsRtd() {
        return thingZsRtd;
    }

    public void setThingZsRtd(String thingZsRtd) {
        this.thingZsRtd = thingZsRtd;
    }

    public String getThingZsAvg() {
        return thingZsAvg;
    }

    public void setThingZsAvg(String thingZsAvg) {
        this.thingZsAvg = thingZsAvg;
    }

    public String getThingZsMax() {
        return thingZsMax;
    }

    public void setThingZsMax(String thingZsMax) {
        this.thingZsMax = thingZsMax;
    }

    public String getThingZsMin() {
        return thingZsMin;
    }

    public void setThingZsMin(String thingZsMin) {
        this.thingZsMin = thingZsMin;
    }

    /**
     * @return the updateType
     */
    public String getUpdateType() {
        return updateType;
    }

    /**
     * @param updateType the updateType to set
     */
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
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
     * @return the rtdTime
     */
    public String getRtdTime() {
        return rtdTime;
    }

    /**
     * @param rtdTime the rtdTime to set
     */
    public void setRtdTime(String rtdTime) {
        this.rtdTime = rtdTime;
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
     * @return the rowIndex
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * @param rowIndex the rowIndex to set
     */
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * @param rowCount the rowCount to set
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getThingUnit() {
        return thingUnit;
    }

    public void setThingUnit(String thingUnit) {
        this.thingUnit = thingUnit;
    }
}
