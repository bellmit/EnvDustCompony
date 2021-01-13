package com.tcb.env.pojo;

import com.tcb.env.util.DefaultArgument;

/**
 * @Author: WangLei
 * @Description: 设备视频实体类
 * @Date: Create in 2019/6/21 14:58
 * @Modify by WangLei
 */
public class DeviceVideo extends BasePojo {

    private Integer videoId;
    private String videoCode;
    private String videoName;
    private String videoIp;
    private String videoPort;
    private String videoUrl;
    private String videoRec;
    private String videoToken;
    private Device device;
    private String userCode;
    private String userPassword;
    private double videoX = DefaultArgument.INT_DEFAULT;
    private double videoY = DefaultArgument.INT_DEFAULT;
    private int videoFlag;
    private int videoType;
    private int videoBrand;
    private String kitToken;

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoIp() {
        return videoIp;
    }

    public void setVideoIp(String videoIp) {
        this.videoIp = videoIp;
    }

    public String getVideoPort() {
        return videoPort;
    }

    public void setVideoPort(String videoPort) {
        this.videoPort = videoPort;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoRec() {
        return videoRec;
    }

    public void setVideoRec(String videoRec) {
        this.videoRec = videoRec;
    }

    public String getVideoToken() {
        return videoToken;
    }

    public void setVideoToken(String videoToken) {
        this.videoToken = videoToken;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public double getVideoX() {
        return videoX;
    }

    public void setVideoX(double videoX) {
        this.videoX = videoX;
    }

    public double getVideoY() {
        return videoY;
    }

    public void setVideoY(double videoY) {
        this.videoY = videoY;
    }

    public int getVideoFlag() {
        return videoFlag;
    }

    public void setVideoFlag(int videoFlag) {
        this.videoFlag = videoFlag;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public int getVideoBrand() {
        return videoBrand;
    }

    public void setVideoBrand(int videoBrand) {
        this.videoBrand = videoBrand;
    }

    public String getKitToken() {
        return kitToken;
    }

    public void setKitToken(String kitToken) {
        this.kitToken = kitToken;
    }

}
