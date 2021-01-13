package com.tcb.env.model;

public class PollutionSourceModel {

    private String deviceCode;
    private String deviceName;

    private double latitude;
    private double longitude;

    private double density;

    private double percent;

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void calculatePercent(double totalDensity) {
        if (totalDensity > 0) {
            this.percent = this.density / totalDensity*100;
        }
    }

    @Override
    public String toString() {
        return "PollutionSourceModel{" +
                "deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", density=" + density +
                ", percent=" + percent +
                "}\n";
    }
}
