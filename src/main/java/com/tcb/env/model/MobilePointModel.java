package com.tcb.env.model;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 移动点位信息模板类
 * @Date: Create in 2019/4/10 13:26
 * @Modify by WangLei
 */
public class MobilePointModel extends BaseModel {

    private String areaId;//区域ID
    private String areaName;//区域名称
    private String pointId; //坐标点ID
    private String pointCode;//坐标点编码
    private String pointName;//坐标点名称
    private String pointLat; //坐标点x坐标
    private String pointLng; //坐标点y坐标
    private String pointStatus; //坐标点状态
    private String pointStatusName; //坐标点状态
    private String pointLevelName;//坐标级别名称
    private String pointLevelNo; //坐标级别序号
    private String pointLevelColor; //坐标级别颜色
    private Object pointData; //坐标点数据
    private List<MobileMonitorModel> pointListData; //坐标点数据

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointLat() {
        return pointLat;
    }

    public void setPointLat(String pointLat) {
        this.pointLat = pointLat;
    }

    public String getPointLng() {
        return pointLng;
    }

    public void setPointLng(String pointLng) {
        this.pointLng = pointLng;
    }

    public String getPointStatus() {
        return pointStatus;
    }

    public void setPointStatus(String pointStatus) {
        this.pointStatus = pointStatus;
    }

    public String getPointStatusName() {
        return pointStatusName;
    }

    public void setPointStatusName(String pointStatusName) {
        this.pointStatusName = pointStatusName;
    }

    public String getPointLevelName() {
        return pointLevelName;
    }

    public void setPointLevelName(String pointLevelName) {
        this.pointLevelName = pointLevelName;
    }

    public String getPointLevelNo() {
        return pointLevelNo;
    }

    public void setPointLevelNo(String pointLevelNo) {
        this.pointLevelNo = pointLevelNo;
    }

    public String getPointLevelColor() {
        return pointLevelColor;
    }

    public void setPointLevelColor(String pointLevelColor) {
        this.pointLevelColor = pointLevelColor;
    }

    public Object getPointData() {
        return pointData;
    }

    public void setPointData(Object pointData) {
        this.pointData = pointData;
    }

    public List<MobileMonitorModel> getPointListData() {
        return pointListData;
    }

    public void setPointListData(List<MobileMonitorModel> pointListData) {
        this.pointListData = pointListData;
    }
}
