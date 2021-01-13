package com.tcb.env.model;

/**
 * @Author: WangLei
 * @Description: 监测物等级模板类
 * @Date: Create in 2019/4/13 14:22
 * @Modify by WangLei
 */
public class MonitorLevelModel extends BaseModel{

    private String tlId;
    private String thingCode;
    private String thingName;
    private String levelMin;
    private String levelMax;
    private String levelNo;
    private String levelName;
    private String levelColor;
    private String levelVisible;

    public String getTlId() {
        return tlId;
    }

    public void setTlId(String tlId) {
        this.tlId = tlId;
    }

    public String getThingCode() {
        return thingCode;
    }

    public void setThingCode(String thingCode) {
        this.thingCode = thingCode;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public String getLevelMin() {
        return levelMin;
    }

    public void setLevelMin(String levelMin) {
        this.levelMin = levelMin;
    }

    public String getLevelMax() {
        return levelMax;
    }

    public void setLevelMax(String levelMax) {
        this.levelMax = levelMax;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(String levelColor) {
        this.levelColor = levelColor;
    }

    public String getLevelVisible() {
        return levelVisible;
    }

    public void setLevelVisible(String levelVisible) {
        this.levelVisible = levelVisible;
    }

}
