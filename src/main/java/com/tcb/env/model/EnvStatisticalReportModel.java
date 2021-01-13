package com.tcb.env.model;

/**
 * <p>[功能描述]：年月日统计报表model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年5月4日下午3:02:29
 * @since EnvDust 1.0.0
 */
public class EnvStatisticalReportModel extends BaseModel {

    private String areaId;
    private String areaName;
    private String deviceCode;
    private String deviceMn;
    private String deviceName;
    private String thingCode;
    private String thingName;
    private String beginTime;
    private String endTime;
    private String thingMax;
    private String thingMin;
    private String thingAvg;
    private String thingZsMax;
    private String thingZsMin;
    private String thingZsAvg;

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
     * @return the thingMax
     */
    public String getThingMax() {
        return thingMax;
    }

    public double convertThingMaxDouble() {
        try {
            return Double.valueOf(thingMax.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
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

    public double convertThingMinDouble() {
        try {
            return Double.valueOf(thingMin.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param thingMin the thingMin to set
     */
    public void setThingMin(String thingMin) {
        this.thingMin = thingMin;
    }

    /**
     * @return the thingAvg
     */
    public String getThingAvg() {
        return thingAvg;
    }

    public double convertThingAvgDouble() {
        try {
            return Double.valueOf(thingAvg.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param thingAvg the thingAvg to set
     */
    public void setThingAvg(String thingAvg) {
        this.thingAvg = thingAvg;
    }

    public String getThingZsMax() {
        return thingZsMax;
    }

    public double convertThingZsMaxDouble() {
        try {
            return Double.valueOf(thingZsMax.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public void setThingZsMax(String thingZsMax) {
        this.thingZsMax = thingZsMax;
    }

    public String getThingZsMin() {
        return thingZsMin;
    }

    public double convertThingZsMinDouble() {
        try {
            return Double.valueOf(thingZsMin.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public void setThingZsMin(String thingZsMin) {
        this.thingZsMin = thingZsMin;
    }

    public String getThingZsAvg() {
        return thingZsAvg;
    }

    public double convertThingZsAvgDouble() {
        try {
            return Double.valueOf(thingZsAvg.replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public void setThingZsAvg(String thingZsAvg) {
        this.thingZsAvg = thingZsAvg;
    }

}
