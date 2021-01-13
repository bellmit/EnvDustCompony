package com.tcb.env.util.algorithm.diffusion;

/**
 * 经纬度计算工具类
 */
public class LatLngUtils {

    /**
     * 角度转换成弧度
     *
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.00;
    }

    /**
     * 计算两个点之间的距离
     *
     * @param longitude1 a点经度
     * @param latitude1  a点纬度
     * @param longitude2 b点经度
     * @param latitude2  b点纬度
     * @return 距离 （单位为米）
     */
    public static double distanceBetweenPoint(double longitude1, double latitude1, double longitude2, double latitude2) {
        // 纬度
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        // 两点纬度之差
        double a = Lat1 - Lat2;
        // 经度之差
        double b = rad(longitude1) - rad(longitude2);
        // 计算两点距离的公式
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));
        // 弧长乘地球半径（半径为米）
        s = s * 6378137.0;
        return s;
    }

    /**
     * 计算两个点之间的方位角，a点 指向 b点.
     * b点在a点的什么角度。 以正北为0度起点，由东向南向西顺时针旋转360度
     *
     * @param aLng a点经度
     * @param aLat a点纬度
     * @param bLng b点经度
     * @param bLat b点纬度
     * @return 方位角（0-360）
     */
    public static double angleBetweenPoint(double aLng, double aLat, double bLng, double bLat) {
        MyLatLng point1 = new MyLatLng(aLng, aLat);
        MyLatLng point2 = new MyLatLng(bLng, bLat);
        return getAngle(point1, point2);
    }

    /**
     * 获取AB连线与正北方向的角度
     *
     * @param A A点的经纬度
     * @param B B点的经纬度
     * @return AB连线与正北方向的角度（0~360）
     */
    public static double getAngle(MyLatLng A, MyLatLng B) {
        double dx = (B.m_RadLo - A.m_RadLo) * A.Ed;
        double dy = (B.m_RadLa - A.m_RadLa) * A.Ec;
        double angle = Math.atan(Math.abs(dx / dy)) * 180. / Math.PI;
        double dLo = B.m_Longitude - A.m_Longitude;
        double dLa = B.m_Latitude - A.m_Latitude;
        if (dLo > 0 && dLa <= 0) {
            angle = (90. - angle) + 90;
        } else if (dLo <= 0 && dLa < 0) {
            angle = angle + 180.;
        } else if (dLo < 0 && dLa >= 0) {
            angle = (90. - angle) + 270;
        }
        return angle;
    }

    /**
     * 经纬度类
     */
    static class MyLatLng {
        final static double Rc = 6378137;
        final static double Rj = 6356725;
        double m_LoDeg, m_LoMin, m_LoSec;
        double m_LaDeg, m_LaMin, m_LaSec;
        double m_Longitude, m_Latitude;
        double m_RadLo, m_RadLa;
        double Ec;
        double Ed;

        public MyLatLng(double longitude, double latitude) {
            m_LoDeg = (int) longitude;
            m_LoMin = (int) ((longitude - m_LoDeg) * 60);
            m_LoSec = (longitude - m_LoDeg - m_LoMin / 60.) * 3600;

            m_LaDeg = (int) latitude;
            m_LaMin = (int) ((latitude - m_LaDeg) * 60);
            m_LaSec = (latitude - m_LaDeg - m_LaMin / 60.) * 3600;

            m_Longitude = longitude;
            m_Latitude = latitude;
            m_RadLo = longitude * Math.PI / 180.;
            m_RadLa = latitude * Math.PI / 180.;
            Ec = Rj + (Rc - Rj) * (90. - m_Latitude) / 90.;
            Ed = Ec * Math.cos(m_RadLa);
        }
    }

    /**
     * 方向取反
     *
     * @param direction
     * @return
     */
    public static double reverseDirection(double direction) {
        return (direction + 180) % 360;
    }

    public void testDistance() {
        double alng = 117.560208;
        double alat = 39.096048;
        double blng = 117.560918;
        double blat = 39.096037;
        double distance = distanceBetweenPoint(alng, alat, blng, blat);
        System.out.println("true : 61m");
        System.out.println(distance);
    }

    public void testAngle() {
        double alng = 117.560806;
        double alat = 39.096048;
        double blng = 117.560208;//117.560806,39.098078
        double blat = 39.096040;
        double angle = angleBetweenPoint(alng, alat, blng, blat);
        System.out.println(angle);
        System.out.println("上风向：" + (angle + 180) % 360);
    }
}
