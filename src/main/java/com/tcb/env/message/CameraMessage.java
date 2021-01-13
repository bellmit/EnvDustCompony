package com.tcb.env.message;

/**
 * 视频拍照消息类
 */
public class CameraMessage {
    private Double alarmId;
    private Integer videoId;
    private String videoCode;
    private String videoToken;
    private int channelNo = 1;
    private String path = "D:\\images";

    public Double getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Double alarmId) {
        this.alarmId = alarmId;
    }

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

    public String getVideoToken() {
        return videoToken;
    }

    public void setVideoToken(String videoToken) {
        this.videoToken = videoToken;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
