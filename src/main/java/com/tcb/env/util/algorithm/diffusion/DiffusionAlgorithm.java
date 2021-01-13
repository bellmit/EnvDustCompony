package com.tcb.env.util.algorithm.diffusion;

/**
 * 用于计算 太阳倾斜角，太阳高度角，大气等级，扩散浓度
 */
public class DiffusionAlgorithm {

    /**
     * 太阳辐射等级表, 横向对应太阳高度角; 纵向对应云量（总云量/最低云量）。通过函数可获取相应的Index
     */
    private static final int[][] SOLAE_RADIATION_LEVEL_TABLE = {{-2, -1, 1, 2, 3}, {-1, 0, 1, 2, 3},
            {-1, 0, 0, 1, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 0}};

    /**
     * 大气稳定度等级列表 (0-8)
     */
    public static final String[] ATMOSPHERIC_STABILITY_LEVEL_LIST = {"A", "A~B", "B", "B~C", "C", "C~D", "D", "E", "F"};

    /**
     * 大气稳定度的等级， 横向对应太阳辐射等级：-2,-1,0,1,2,3；纵向表示风速。通过函数可获取相应的Index。Table中0-8对应 ATMOSPHERIC_STABILITY_LEVEL_LIST 中的等级
     */
    private static final int[][] ATMOSPHERIC_STABILITY_LEVEL_TABLE = {{8, 7, 6, 2, 1, 0}, {8, 7, 6, 4, 2, 1},
            {7, 6, 6, 4, 3, 2}, {6, 6, 6, 6, 5, 4}, {6, 6, 6, 6, 6, 6}};

    /**
     * 表示风速 <2, 2-3, 3-5, 5-6, >6
     */
    private static final double[] WIND_SPEED_LEVEL = {2, 3, 5, 6, 10000};

    /**
     * 表示太阳高度 <=15, 15-35, 35-65, >65
     */
    private static final double[] SUN_ANGLE_RANGE = {15, 35, 65, 180};

    /**
     * 参数 ay( α1 ) 表
     */
    private static final double[][] Y_TABLE = {{0.901074, 0.914370, 0.919325, 0.924279, 0.926849, 0.929418, 0.925118, 0.920818, 0.929418},
            {0.850934, 0.865014, 0.875086, 0.885157, 0.886940, 0.888723, 0.892794, 0.896864, 0.888723}};
    /**
     * 参数 λ1 表
     */
    private static final double[][] L1_TABLE = {{0.425809, 0.281846, 0.229500, 0.177154, 0.143940, 0.110726, 0.0985631, 0.0864001, 0.0553634},
            {0.602052, 0.396353, 0.314238, 0.232123, 0.189396, 0.146669, 0.124308, 0.101947, 0.0733348}};

    /**
     * 参数 az( α2 )  表
     */
    private static final double[][] Z_TABLE = {{1.12154, 0.964435, 0.941015, 0.917595, 0.838628, 0.826212, 0.776864, 0.788370, 0.784400},
            {1.51360, 1.09356, 1.00770, 0, 0.756410, 0.632023, 0.572347, 0.565188, 0.525969},
            {2.10881, 0, 0, 0, 0.815575, 0.55536, 0.499149, 0.414743, 0.322659}};

    /**
     * 风距离参数表，用于计算 σy
     */
    private static final String[][] WIND_DISTANCE_TABLE = {{"0~300", "0~500", "0~500", ">0", "0~2000", "1~1000", "0~2000", "0~1000", "0~1000"},
            {"300~500", ">500", ">500", "", "2000~10000", "1000~10000", "2000~10000", "1000~10000", "1000~10000"},
            {">500", "", "", "", ">10000", ">10000", ">10000", ">10000", ">10000"}};

    /**
     * 参数 λ2 表
     */
    private static final double[][] L2_TABLE = {{0.0799904, 0.127190, 0.114682, 0.106803, 0.126152, 0.104634, 0.111771, 0.0927529, 0.0620765},
            {0.00854771, 0.057025, 0.0757182, 0, 0.235667, 0.400167, 0.5289922, 0.433384, 0.370015},
            {0.000211545, 0, 0, 0, 0.136659, 0.810763, 1.03810, 1.73421, 2.40691}};

    private double latitude;
    private double longitude;
    /**
     * 一年中的哪天（0-364）
     */
    private int dayInYear;
    private double totalCloudAmount;
    private double minCloudAmount;
    private double windSpeed;
    /**
     * 当前时间(时:分)，用小时表示
     */
    private double hour;
    private boolean isNight;

    /**
     * 表示强源 单位 g/s
     */
    private double q;

    /**
     * @param latitude         纬度
     * @param longitude        精度
     * @param dayInYear        一年中的哪天（0-364）
     * @param totalCloudAmount 总云量
     * @param minCloudAmount   最大云量
     * @param windSpeed        风速 m/s
     * @param hour             当前时间(时:分)，用小时表示
     * @param q                表示强源 单位 g/s
     */
    public DiffusionAlgorithm(double latitude, double longitude, int dayInYear, double totalCloudAmount, double minCloudAmount, double windSpeed, double hour, double q) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayInYear = dayInYear;
        this.totalCloudAmount = totalCloudAmount;
        this.minCloudAmount = minCloudAmount;
        this.windSpeed = windSpeed;
        this.hour = hour;
        this.q = q;
        if (hour < 6 || hour >= 22) {
            isNight = true;
        }
    }

    /**
     * 计算太阳倾角 SunDip
     *
     * @return 计算太阳倾角 单位为 弧度
     */
    public double getSunDip() {
        double e = 2 * Math.PI * dayInYear / 365.0;
        double result = 0.006918 - 0.399912 * Math.cos(e) + 0.070257 * Math.sin(e) - 0.006758 * Math.cos(2 * e)
                + 0.000907 * Math.sin(2 * e) - 0.002697 * Math.cos(3 * e) + 0.001480 * Math.sin(3 * e);
        return result;
    }

    /**
     * 计算太阳高度角
     *
     * @return 太阳高度角 单位为 弧度
     */
    public double getSolarElevationAngle() {
        double sunDip = getSunDip();
        double sunAngle = (Math.abs(hour - 12) * 15)*Math.PI/180;
        double hs = Math.asin(
                Math.sin(sunDip) * Math.sin(latitude) + Math.cos(sunDip) * Math.cos(latitude) * Math.cos(sunAngle));
        return hs;
    }

    /**
     * 根据云量，获取 查表 对应的下标
     * @return
     */
    private int getIndexByCloudAmount() {
        if (totalCloudAmount < 5 && minCloudAmount < 5)
            return 0;
        if (totalCloudAmount >= 5 && totalCloudAmount < 8 && minCloudAmount < 5)
            return 1;
        if (totalCloudAmount >= 8 && minCloudAmount < 5)
            return 2;
        if (totalCloudAmount >= 5 && minCloudAmount >= 5 && minCloudAmount < 8)
            return 3;
        if (totalCloudAmount >= 8 && minCloudAmount >= 8)
            return 4;
        return -1;
    }

    /**
     * 根据风速，返回 查表 对应的下标
     */
    private int getIndexByWindSpeed() {
        for (int i = 0; i < WIND_SPEED_LEVEL.length; i++) {
            if (windSpeed < WIND_SPEED_LEVEL[i])
                return i;
        }
        return -1;
    }

    /**
     * 根据太阳高度角，返回 查表对应的下标
     * @param sunAngle
     * @return index
     */
    private int getIndexBySunAngle(double sunAngle) {
        if (isNight)
            return 0;
        for (int i = 0; i < SUN_ANGLE_RANGE.length; i++) {
            if (sunAngle < SUN_ANGLE_RANGE[i])
                return i + 1;
        }
        return -1;
    }

    /**
     * 幅度 转为 度
     * @param rad
     * @return
     */
    private double radianToAngle(double rad) {
        return rad * 180 / Math.PI;
    }

    /**
     * 计算大气等级，对应 ATMOSPHERIC_STABILITY_LEVEL_LIST
     * @return
     */
    private int getAtmosphericStabilityLevel() {
        double sunAngle = radianToAngle(getSolarElevationAngle());
        int sunRadLevel = SOLAE_RADIATION_LEVEL_TABLE[getIndexByCloudAmount()][getIndexBySunAngle(sunAngle)];
        int result = ATMOSPHERIC_STABILITY_LEVEL_TABLE[getIndexByWindSpeed()][sunRadLevel + 2];
        return result;
    }

    /**
     * 计算大气等级
     * @return 返回大气等级名
     */
    public String getAtmosphericStabilityLevelName() {
        int index = getAtmosphericStabilityLevel();
        return ATMOSPHERIC_STABILITY_LEVEL_LIST[index];
    }

    /**
     * 获取 σy 扩散参数
     *
     * @param distance 下风向距离
     * @return
     */
    public double getYParam(double distance) {
        int stabilityLevelIndex = getAtmosphericStabilityLevel();
        int distanceIndex = distance > 1000 ? 1 : 0;
        return L1_TABLE[distanceIndex][stabilityLevelIndex] * Math.pow(distance, Y_TABLE[distanceIndex][stabilityLevelIndex]);
    }

    /**
     * 获取 σz 扩散参数
     *
     * @param distance 下风向距离
     * @return
     */
    public double getZParam(double distance) {
        int stabilityLevelIndex = getAtmosphericStabilityLevel();
        int distanceIndex = getZDistanceIndex(distance, stabilityLevelIndex);
        return L2_TABLE[distanceIndex][stabilityLevelIndex] * Math.pow(distance, Z_TABLE[distanceIndex][stabilityLevelIndex]);
    }

    /**
     * 根据下风向距离，获取 Z 参数对应的查表 index
     * @param distance 距离
     * @param indexStabilityLevelIndex 大气等级Index
     * @return index
     */
    private static int getZDistanceIndex(double distance, int indexStabilityLevelIndex) {
        for (int i = 0; i < WIND_DISTANCE_TABLE.length; i++) {
            String windDistance = WIND_DISTANCE_TABLE[i][indexStabilityLevelIndex];
            if (windDistance.indexOf(">") != -1 && Double.parseDouble(windDistance.substring(1)) < distance) {
                return i;
            } else {
                String[] nums = windDistance.split("~");
                if (Double.parseDouble(nums[0]) < distance && Double.parseDouble(nums[1]) >= distance) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 获取 (x,y,z) 点的浓度
     * 以 采样点（扩散点/发散点） 为原点，风的方向为 x 轴，建立坐标系。单位为 m
     *
     * @param x 坐标x
     * @param y 坐标y
     * @param z 坐标z
     * @return 浓度 单位 g/s
     */
    public double getPointDensity(double x, double y, double z) {
        double paramY = getYParam(x);
        double paramZ = getZParam(x);
        double result = (q / (2 * Math.PI * windSpeed * paramY * paramZ)) * Math.exp(-(y * y / (2 * paramY * paramY) + (z * z / (2 * paramZ * paramZ))));
        return result;
    }


    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }
    @Override
    public String toString() {
        return "DiffusionAlgorithm{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", dayInYear=" + dayInYear +
                ", totalCloudAmount=" + totalCloudAmount +
                ", minCloudAmount=" + minCloudAmount +
                ", windSpeed=" + windSpeed +
                ", hour=" + hour +
                ", isNight=" + isNight +
                ", q=" + q +
                '}';
    }
}
