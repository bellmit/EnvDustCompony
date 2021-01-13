package com.tcb.env.model;

/**
 * <p>[功能描述]：设备数据值模板</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年12月22日上午10:32:51
 * @since EnvDust 1.0.0
 */
public class DeviceValueModel extends BaseModel {

    private String deviceCode;
    private String deviceName;
    private String dataType;
    private String dataTypeName;
    private String thingCode;
    private String thingName;
    private String thingValue;
    private String dataOrder;

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
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the dataTypeName
     */
    public String getDataTypeName() {
        return dataTypeName;
    }

    /**
     * @param dataTypeName the dataTypeName to set
     */
    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
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
     * @return the thingValue
     */
    public String getThingValue() {
        return thingValue;
    }

    /**
     * @param thingValue the thingValue to set
     */
    public void setThingValue(String thingValue) {
        this.thingValue = thingValue;
    }

    public double convertThingValueDouble() {
        try{
            return Double.valueOf(this.thingValue);
        }catch(Exception e){
            return 0;
        }
    }

    public String getDataOrder() {
        return dataOrder;
    }

    public void setDataOrder(String dataOrder) {
        this.dataOrder = dataOrder;
    }
}
