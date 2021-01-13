package com.tcb.env.util.algorithm.diffusion;

public class SamplingPoint  {

    private String deviceCode;
    private String deviceName;

    private double latitude;
    private double longitude;

    private double windSpeed;
    private double windAngle;

    private double distance;

    private DiffusionAlgorithm diffusionAlgorithm;

    public SamplingPoint(String deviceCode, String deviceName, double latitude, double longitude) {
        this.deviceCode = deviceCode;
        this.deviceName = deviceName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @param latitude         纬度
     * @param longitude        精度
     * @param dayInYear        一年中的哪天（0-364）
     * @param totalCloudAmount 总云量
     * @param minCloudAmount   最大云量
     * @param windSpeed        风速
     * @param hour             当前时间(时:分)，用小时表示
     * @param q                表示强源 单位 g/s
     */
    public SamplingPoint(double latitude, double longitude, int dayInYear, double hour, double totalCloudAmount, double minCloudAmount, double windSpeed, double windAngle, double q) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.windSpeed = windSpeed;
        this.windAngle = windAngle;
        diffusionAlgorithm = new DiffusionAlgorithm(latitude, longitude, dayInYear, totalCloudAmount, minCloudAmount, windSpeed, hour, q);
    }

    /**
     * 设置强源
     * @param q 表示强源 单位 g/s
     */
    public void setQ(double q){
        diffusionAlgorithm.setQ(q);
    }

    /**
     * @param dayInYear        一年中的哪天（0-364）
     * @param totalCloudAmount 总云量
     * @param minCloudAmount   最大云量
     * @param windSpeed        风速
     * @param hour             当前时间(时:分)，用小时表示
     * @param q                表示强源 单位 g/s
     */
    public void setParamsValue(int dayInYear, double hour, double totalCloudAmount, double minCloudAmount, double windSpeed, double windAngle, double q){
        this.windSpeed = windSpeed;
        this.windAngle = windAngle;
        diffusionAlgorithm = new DiffusionAlgorithm(latitude, longitude, dayInYear, totalCloudAmount, minCloudAmount, windSpeed, hour, q);
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    /**
     * 获取某点指向该点的角度
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 角度 单位度
     */
    public double angle(double latitude, double longitude) {
        return LatLngUtils.angleBetweenPoint(longitude, latitude, this.longitude, this.latitude);
    }

    /**
     * 某点到该点的距离
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 距离 单位米
     */
    public double distance(double latitude, double longitude) {
        return LatLngUtils.distanceBetweenPoint(longitude, latitude, this.longitude, this.latitude);
    }

    private double angleToRad(double angle) {
        return angle * Math.PI / 180;
    }

    /**
     * 计算到某点的浓度
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param height    高度
     * @return 浓度 单位 g
     */
    public double toPointDensity(double longitude, double latitude, double height) throws Exception {
        if(diffusionAlgorithm == null) {
            throw new Exception("请设置求浓度所需的数值。调用 setParamsvalue 方法设置");
        }
        double angle = LatLngUtils.angleBetweenPoint(longitude, latitude,this.longitude, this.latitude);
        double rad = angleToRad(windAngle - angle);
        double d = distance(latitude, longitude);
        double x = d * Math.cos(rad);
        double y = d * Math.sin(rad);
        if(x<0){
            return 0.0;
        }
        return diffusionAlgorithm.getPointDensity(x, y, height);
    }

    public void minusQInterference(double q) {
        diffusionAlgorithm.setQ(diffusionAlgorithm.getQ()-q);
    }
}
