package com.tcb.env.model;

/**
 * <p>[功能描述]：移动监测物模板</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 *
 * @author WangLei
 * @version 1.0, 2019年4月31日上午16:58:45
 * @since EnvDust 1.0.0
 */
public class MobileMonitorModel extends BaseModel {

    private String thingId;
    private String thingCode;
    private String thingName;
    private String thingValue;
    private String thingUnit;
    private String thingOrder;
    private String thingColor;
    private String thingLevelName;
    private String thingLevelNo;
    private String thingLevelColor;

    /**
     * @return the thingUnit
     */
    public String getThingUnit() {
        return thingUnit;
    }

    /**
     * @param thingUnit the thingUnit to set
     */
    public void setThingUnit(String thingUnit) {
        this.thingUnit = thingUnit;
    }

    /**
     * @return the thingId
     */
    public String getThingId() {
        return thingId;
    }

    /**
     * @param thingId the thingId to set
     */
    public void setThingId(String thingId) {
        this.thingId = thingId;
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

    public String getThingValue() {
        return thingValue;
    }

    public void setThingValue(String thingValue) {
        this.thingValue = thingValue;
    }

    /**
     * @return the thingOrder
     */
    public String getThingOrder() {
        return thingOrder;
    }

    /**
     * @param thingOrder the thingOrder to set
     */
    public void setThingOrder(String thingOrder) {
        this.thingOrder = thingOrder;
    }

    public String getThingColor() {
        return thingColor;
    }

    public void setThingColor(String thingColor) {
        this.thingColor = thingColor;
    }

    public String getThingLevelName() {
        return thingLevelName;
    }

    public void setThingLevelName(String thingLevelName) {
        this.thingLevelName = thingLevelName;
    }

    public String getThingLevelNo() {
        return thingLevelNo;
    }

    public void setThingLevelNo(String thingLevelNo) {
        this.thingLevelNo = thingLevelNo;
    }

    public String getThingLevelColor() {
        return thingLevelColor;
    }

    public void setThingLevelColor(String thingLevelColor) {
        this.thingLevelColor = thingLevelColor;
    }
}
