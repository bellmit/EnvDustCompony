package com.tcb.env.config;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>[功能描述]：短信端口配置类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年2月23日上午8:50:37
 * @since EnvDust 1.0.0
 */
@Component("smsConfig")
public class SMSConfig {

    private String phoneNumber;//多个手机号以,分隔
    private String Content;
    private List<String> mailAddress;
    private String title;
    private String areaName;
    private String deviceName;
    private String typeName;
    private String alarmInfo;
    private String thingName;
    private String thingValue;
    private String dateString;
    private String timeString;

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return Content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        Content = content;
    }

    public List<String> getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(List<String> mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public String getThingValue() {
        return thingValue;
    }

    public void setThingValue(String thingValue) {
        this.thingValue = thingValue;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

}
