package com.tcb.env.util;

import java.util.Calendar;

import com.tcb.env.model.LocationModel;

/**
 * <p>[功能描述]：溯源/扩散定位算法</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2017年8月28日下午2:17:27
 * @since EnvDust 1.0.0
 */
public class OriginSpreadLocation {

    /**
     * <p>[功能描述]：污染溯源</p>
     *
     * @param locationModel
     * @param second
     * @return
     * @author 王垒, 2017年8月28日下午2:30:48
     * @since EnvDust 1.0.0
     */
    public LocationModel processOrigin(LocationModel locationModel, int second) {

        if (locationModel != null) {
            double lng = locationModel.getLng();//经度
            double lat = locationModel.getLat();//纬度
            double wd = locationModel.getWd();
            double ws = locationModel.getWs();
            if (wd == 0) {
                lat = lat - (ws * second) / DefaultArgument.ONE_DEGREE;
            } else if (wd == 90) {
                lng = lng - (ws * second) / DefaultArgument.ONE_DEGREE;
            } else if (wd == 180) {
                lat = lat + (ws * second) / DefaultArgument.ONE_DEGREE;
            } else if (wd == 270) {
                lng = lng + (ws * second) / DefaultArgument.ONE_DEGREE;
            } else if (wd > 359 && wd <= 360) {
                lat = lat - (ws * second) / DefaultArgument.ONE_DEGREE;
            } else if (wd > 0 && wd < 90) {//tan(wd) = x/y;x*x+y*y = ws*second*ws*second
                double y = Math.sqrt((ws * second * ws * second) / (1 + Math.tan(wd) * Math.tan(wd)));
                double x = Math.tan(wd) * y;
                lng = lng - x / DefaultArgument.ONE_DEGREE;
                lat = lat - y / DefaultArgument.ONE_DEGREE;
            } else if (wd > 90 && wd < 180) {//tan(wd-90) = y/x;x*x+y*y = ws*second*ws*second
                double x = Math.sqrt((ws * second * ws * second) / (1 + Math.tan(wd - 90) * Math.tan(wd - 90)));
                double y = Math.tan(wd - 90) * x;
                lng = lng - x / DefaultArgument.ONE_DEGREE;
                lat = lat + y / DefaultArgument.ONE_DEGREE;
            } else if (wd > 180 && wd < 270) {//tan(wd-180) = x/y;x*x+y*y = ws*second*ws*second
                double y = Math.sqrt((ws * second * ws * second) / (1 + Math.tan(wd - 180) * Math.tan(wd - 180)));
                double x = Math.tan(wd - 180) * y;
                lng = lng + x / DefaultArgument.ONE_DEGREE;
                lat = lat + y / DefaultArgument.ONE_DEGREE;
            } else if (wd > 270 && wd < 380) {//tan(wd-270) = y/x;x*x+y*y = ws*second*ws*second
                double x = Math.sqrt((ws * second * ws * second) / (1 + Math.tan(wd - 270) * Math.tan(wd - 270)));
                double y = Math.tan(wd - 270) * x;
                lng = lng + x / DefaultArgument.ONE_DEGREE;
                lat = lat - y / DefaultArgument.ONE_DEGREE;
            }
            locationModel.setLng(lng);
            locationModel.setLat(lat);
        }
        return locationModel;
    }


    /**
     * <p>[功能描述]：污染扩散</p>
     *
     * @param locationModel
     * @param hour          :当前小时
     * @param second        :计算时间
     * @param cloud         :当前总云量/低云量
     * @return
     * @author 王垒, 2017年8月28日下午2:30:48
     * @since EnvDust 1.0.0
     */
    public static LocationModel processSpread(LocationModel locationModel, int hour, int second, int cloud) {
        LocationModel locationModelNew = new LocationModel();
        if (locationModel != null) {
            double lng = locationModel.getLng();//经度
            double lat = locationModel.getLat();//纬度
            double wd = locationModel.getWd();
            double ws = locationModel.getWs();
            double distance = ws * second;
            double x = 0;
            double y = 0;
            if (wd == 0) {
                lat = lat + (distance) / DefaultArgument.ONE_DEGREE;
            } else if (wd == 90) {
                lng = lng + (distance) / DefaultArgument.ONE_DEGREE;
            } else if (wd == 180) {
                lat = lat - (distance) / DefaultArgument.ONE_DEGREE;
            } else if (wd == 270) {
                lng = lng - (distance) / DefaultArgument.ONE_DEGREE;
            } else if (wd > 359 && wd <= 360) {
                lat = lat + (distance) / DefaultArgument.ONE_DEGREE;
            } else if (wd > 0 && wd < 90) {//tan(wd) = x/y;x*x+y*y = ws*second*ws*second
                y = Math.sqrt((distance * distance) / (1 + Math.tan(wd) * Math.tan(wd)));
                x = Math.tan(wd) * y;
                lng = lng + x / DefaultArgument.ONE_DEGREE;
                lat = lat + y / DefaultArgument.ONE_DEGREE;
            } else if (wd > 90 && wd < 180) {//tan(wd-90) = y/x;x*x+y*y = ws*second*ws*second
                x = Math.sqrt((distance * distance) / (1 + Math.tan(wd - 90) * Math.tan(wd - 90)));
                y = Math.tan(wd - 90) * x;
                lng = lng + x / DefaultArgument.ONE_DEGREE;
                lat = lat - y / DefaultArgument.ONE_DEGREE;
            } else if (wd > 180 && wd < 270) {//tan(wd-180) = x/y;x*x+y*y = ws*second*ws*second
                y = Math.sqrt((distance * distance) / (1 + Math.tan(wd - 180) * Math.tan(wd - 180)));
                x = Math.tan(wd - 180) * y;
                lng = lng - x / DefaultArgument.ONE_DEGREE;
                lat = lat - y / DefaultArgument.ONE_DEGREE;
            } else if (wd > 270 && wd < 380) {//tan(wd-270) = y/x;x*x+y*y = ws*second*ws*second
                x = Math.sqrt((distance * distance) / (1 + Math.tan(wd - 270) * Math.tan(wd - 270)));
                y = Math.tan(wd - 270) * x;
                lng = lng - x / DefaultArgument.ONE_DEGREE;
                lat = lat + y / DefaultArgument.ONE_DEGREE;
            }
            locationModelNew.setLng(lng);
            locationModelNew.setLat(lat);
            locationModelNew.setCode(locationModel.getCode());
            locationModelNew.setRad(locationModel.getRad());
            locationModelNew.setWd(locationModel.getWd());
            locationModelNew.setWs(locationModel.getWs());
            locationModelNew.setInfo(locationModel.getInfo());
            //获取扩散值(地面轴线最大浓度模式：y=0,z=0)
            double spreadValue = getSpreadValue(locationModel, hour, cloud, distance, 0, 0);
            if (spreadValue == Double.POSITIVE_INFINITY || spreadValue == Double.NEGATIVE_INFINITY) {//无穷大时赋原值
                spreadValue = locationModel.getVal();
            }
            locationModelNew.setVal(spreadValue);
        }
        return locationModelNew;
    }

    /**
     * <p>[功能描述]：获取大气稳定度</p>
     *
     * @param locationModel
     * @param hour          :小时
     * @param cloud         :云量
     * @param distance      :下风向距离
     * @param y             :扩散y轴值
     * @param z             :扩散z轴值
     * @return
     * @author 王垒, 2017年9月1日下午3:59:00
     * @since EnvDust 1.0.0
     */
    public static double getSpreadValue(LocationModel locationModel, int hour, int cloud, double distance, double y, double z) {
        //获取太阳倾斜角度
        Calendar cc = Calendar.getInstance();
        int dn = cc.get(Calendar.DAY_OF_YEAR) - 1;
        double δ = δ(dn);
        //获取太阳高度角
        double φ = locationModel.getLat();//当地纬度
        int t = hour;//小时数
        double λ = locationModel.getLng();//当地经度
        double ho = ho(φ, t, δ, λ);
        //根据高度角和当前云量获取太阳辐射等级
        int sunRadiationLevel = getSunRadiationLevel(ho, cloud);
        //根据风速和太阳辐射等级获取大气稳定度等级
        String atmosphericStabilityLevel = getAtmosphericStabilityLevel(locationModel.getWs(), sunRadiationLevel);
        //获取σy
        double σy = getσy(distance, atmosphericStabilityLevel);
        //获取σz
        double σz = getσz(distance, atmosphericStabilityLevel);
        //计算浓度
        double q = locationModel.getVal();//源强
        double u = locationModel.getWs();//平均风速
        //计算扩散浓度
        double q1 = q / (2 * Math.PI * u * σy * σz);
        double exp = Math.exp(-((y * y) / (2 * σy * σy) + (z * z) / (2 * σz * σz)));
        double spreadValue = q1 * exp;
        return spreadValue;
    }

    /**
     * <p>[功能描述]：计算太阳高度角ho</p>
     *
     * @param φ :当地纬度，度(如34.64)
     * @param t :北京时间，时(如9)
     * @param δ :太阳倾角，度(如-23.1)
     * @param λ :当地经度，度(如34.64)
     * @return 返回太阳高度角，度
     * @author 王垒, 2017年9月5日上午8:55:28
     * @since EnvDust 1.0.0
     */
    public static double ho(double φ, int t, double δ, double λ) {
        //公式：ho=arcsin[sinφsinδ+cosφcosδcos(15t+λ-300)]
        double a = 15 * t + λ - 300;//时角
        double b = Math.cos(φ * Math.PI / 180d) * Math.cos(δ * Math.PI / 180d) * Math.cos(a * Math.PI / 180d);
        double c = Math.sin(φ * Math.PI / 180) * Math.sin(δ * Math.PI / 180);
        double d = c + b;
        double ho = Math.asin(d) / (Math.PI / 180d);
        return ho;
    }

    /**
     * <p>[功能描述]：计算太阳倾角δ</p>
     *
     * @param dn :一年中日期序数(0,1,2......364)
     * @return 返回太阳倾角，度
     * @author 王垒, 2017年9月5日上午8:55:48
     * @since EnvDust 1.0.0
     */
    public static double δ(int dn) {
        //公式：δ=[0.006918-0.399912cosQo+0.0702578sinQo-0.006758cosQo+0.000907sin2Qo-0.002697cos3Qo+0.001480sin3Qo]*180/π  
        //Qo=360dn/365，度  
        double Qo = Double.valueOf((Double.valueOf(360 * dn) / Double.valueOf(365)) * (Math.PI / 180d));
        double a = 0.399912 * Math.cos(Qo);
        double b = 0.0702578 * Math.sin(Qo);
        double c = 0.006758 * Math.cos(Qo);
        double d = 0.000907 * Math.sin(2 * Qo);
        double e = 0.002697 * Math.cos(3 * Qo);
        double f = 0.001480 * Math.sin(3 * Qo);
        double g = 180 / Math.PI;
        double h = 0.006918 - a + b - c + d - e + f;
        double δ = Math.round(h * g);
        return δ;
    }

    /**
     * <p>[功能描述]：获取太阳辐射等级</p>
     *
     * @param sunHighAngle
     * @param cloud
     * @return
     * @author 王垒, 2017年9月4日上午9:03:01
     * @since EnvDust 1.0.0
     */
    public static int getSunRadiationLevel(double sunHighAngle, int cloud) {
        int sunRadiationLevel = 0;
        switch (cloud) {//总运量/低云量
            case 1://<=4/<=4
                if (sunHighAngle <= 0) {//夜间
                    sunRadiationLevel = -2;
                } else if (sunHighAngle <= 15) {
                    sunRadiationLevel = -1;
                } else if (sunHighAngle > 15 && sunHighAngle <= 35) {
                    sunRadiationLevel = 1;
                } else if (sunHighAngle > 35 && sunHighAngle <= 65) {
                    sunRadiationLevel = 2;
                } else {
                    sunRadiationLevel = 3;
                }
                break;
            case 2://5~7/<=4
                if (sunHighAngle <= 0) {//夜间
                    sunRadiationLevel = -1;
                } else if (sunHighAngle <= 15) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 15 && sunHighAngle <= 35) {
                    sunRadiationLevel = 1;
                } else if (sunHighAngle > 35 && sunHighAngle <= 65) {
                    sunRadiationLevel = 2;
                } else {
                    sunRadiationLevel = 3;
                }
                break;
            case 3://>=8/<=4
                if (sunHighAngle <= 0) {//夜间
                    sunRadiationLevel = -1;
                } else if (sunHighAngle <= 15) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 15 && sunHighAngle <= 35) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 35 && sunHighAngle <= 65) {
                    sunRadiationLevel = 1;
                } else {
                    sunRadiationLevel = 1;
                }
                break;
            case 4://>=5/5~7
                if (sunHighAngle <= 0) {//夜间
                    sunRadiationLevel = 0;
                } else if (sunHighAngle <= 15) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 15 && sunHighAngle <= 35) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 35 && sunHighAngle <= 65) {
                    sunRadiationLevel = 0;
                } else {
                    sunRadiationLevel = 1;
                }
                break;
            case 5://>=8/>=8
                if (sunHighAngle <= 0) {//夜间
                    sunRadiationLevel = 0;
                } else if (sunHighAngle <= 15) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 15 && sunHighAngle <= 35) {
                    sunRadiationLevel = 0;
                } else if (sunHighAngle > 35 && sunHighAngle <= 65) {
                    sunRadiationLevel = 0;
                } else {
                    sunRadiationLevel = 0;
                }
                break;
            default:
                break;
        }
        return sunRadiationLevel;
    }

    /**
     * <p>[功能描述]：获取大气稳定度的等级</p>
     *
     * @param windSpeed
     * @param sunRadiationLevel
     * @return
     * @author 王垒, 2017年9月4日上午9:13:56
     * @since EnvDust 1.0.0
     */
    public static String getAtmosphericStabilityLevel(double windSpeed, int sunRadiationLevel) {
        String atmosphericStabilityLevel = "";
        switch (sunRadiationLevel) {//太阳辐射等级
            case 3:
                if (windSpeed < 2) {
                    atmosphericStabilityLevel = "A";
                } else if (windSpeed >= 2 && windSpeed < 3) {
                    atmosphericStabilityLevel = "A~B";
                } else if (windSpeed >= 3 && windSpeed < 5) {
                    atmosphericStabilityLevel = "B";
                } else if (windSpeed >= 5 && windSpeed < 6) {
                    atmosphericStabilityLevel = "C";
                } else {
                    atmosphericStabilityLevel = "D";
                }
                break;
            case 2:
                if (windSpeed < 2) {
                    atmosphericStabilityLevel = "A~B";
                } else if (windSpeed >= 2 && windSpeed < 3) {
                    atmosphericStabilityLevel = "B";
                } else if (windSpeed >= 3 && windSpeed < 5) {
                    atmosphericStabilityLevel = "B~C";
                } else if (windSpeed >= 5 && windSpeed < 6) {
                    atmosphericStabilityLevel = "C~D";
                } else {
                    atmosphericStabilityLevel = "D";
                }
                break;
            case 1:
                if (windSpeed < 2) {
                    atmosphericStabilityLevel = "B";
                } else if (windSpeed >= 2 && windSpeed < 3) {
                    atmosphericStabilityLevel = "C";
                } else if (windSpeed >= 3 && windSpeed < 5) {
                    atmosphericStabilityLevel = "C";
                } else if (windSpeed >= 5 && windSpeed < 6) {
                    atmosphericStabilityLevel = "D";
                } else {
                    atmosphericStabilityLevel = "D";
                }
                break;
            case 0:
                if (windSpeed < 2) {
                    atmosphericStabilityLevel = "D";
                } else if (windSpeed >= 2 && windSpeed < 3) {
                    atmosphericStabilityLevel = "D";
                } else if (windSpeed >= 3 && windSpeed < 5) {
                    atmosphericStabilityLevel = "D";
                } else if (windSpeed >= 5 && windSpeed < 6) {
                    atmosphericStabilityLevel = "D";
                } else {
                    atmosphericStabilityLevel = "D";
                }
                break;
            case -1:
                if (windSpeed < 2) {
                    atmosphericStabilityLevel = "E";
                } else if (windSpeed >= 2 && windSpeed < 3) {
                    atmosphericStabilityLevel = "E";
                } else if (windSpeed >= 3 && windSpeed < 5) {
                    atmosphericStabilityLevel = "D";
                } else if (windSpeed >= 5 && windSpeed < 6) {
                    atmosphericStabilityLevel = "D";
                } else {
                    atmosphericStabilityLevel = "D";
                }
                break;
            case -2:
                if (windSpeed < 2) {
                    atmosphericStabilityLevel = "F";
                } else if (windSpeed >= 2 && windSpeed < 3) {
                    atmosphericStabilityLevel = "F";
                } else if (windSpeed >= 3 && windSpeed < 5) {
                    atmosphericStabilityLevel = "E";
                } else if (windSpeed >= 5 && windSpeed < 6) {
                    atmosphericStabilityLevel = "D";
                } else {
                    atmosphericStabilityLevel = "D";
                }
                break;
            default:
                break;
        }
        return atmosphericStabilityLevel;
    }

    /**
     * <p>[功能描述]：获取σy</p>
     *
     * @param distance
     * @param atmosphericStabilityLevel
     * @return
     * @author 王垒, 2017年9月4日上午9:41:20
     * @since EnvDust 1.0.0
     */
    public static double getσy(double distance, String atmosphericStabilityLevel) {
        double σy = 0;
        switch (atmosphericStabilityLevel) {//太阳辐射等级
            case "A":
            case "A~B":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.901074;
                } else if (distance > 1000) {
                    σy = 0.850934;
                }
                break;
            case "B":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.914370;
                } else if (distance > 1000) {
                    σy = 0.865014;
                }
                break;
            case "B~C":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.919325;
                } else if (distance > 1000) {
                    σy = 0.875086;
                }
                break;
            case "C":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.924279;
                } else if (distance > 1000) {
                    σy = 0.885157;
                }
                break;
            case "C~D":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.926849;
                } else if (distance > 1000) {
                    σy = 0.886940;
                }
                break;
            case "D":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.929418;
                } else if (distance > 1000) {
                    σy = 0.888723;
                }
                break;
            case "D~E":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.925118;
                } else if (distance > 1000) {
                    σy = 0.982794;
                }
                break;
            case "E":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.920818;
                } else if (distance > 1000) {
                    σy = 0.896864;
                }
                break;
            case "F":
                if (distance >= 0 && distance <= 1000) {
                    σy = 0.929418;
                } else if (distance > 1000) {
                    σy = 0.888723;
                }
                break;
            default:
                break;
        }
        return σy;
    }

    /**
     * <p>[功能描述]：获取σz</p>
     *
     * @param distance
     * @param atmosphericStabilityLevel
     * @return
     * @author 王垒, 2017年9月4日上午9:52:06
     * @since EnvDust 1.0.0
     */
    public static double getσz(double distance, String atmosphericStabilityLevel) {
        double σz = 0;
        switch (atmosphericStabilityLevel) {//太阳辐射等级
            case "A":
            case "A~B":
                if (distance >= 0 && distance <= 300) {
                    σz = 1.12154;
                } else if (distance > 300 && distance <= 500) {
                    σz = 1.51360;
                } else if (distance > 500) {
                    σz = 2.10881;
                }
                break;
            case "B":
                if (distance >= 0 && distance <= 500) {
                    σz = 0.964435;
                } else if (distance > 500) {
                    σz = 1.09356;
                }
                break;
            case "B~C":
                if (distance >= 0 && distance <= 500) {
                    σz = 0.941015;
                } else if (distance > 500) {
                    σz = 1.00770;
                }
                break;
            case "C":
                if (distance > 0) {
                    σz = 0.917595;
                }
                break;
            case "C~D":
                if (distance >= 0 && distance <= 2000) {
                    σz = 0.838628;
                } else if (distance > 2000 && distance <= 10000) {
                    σz = 0.756410;
                } else if (distance > 10000) {
                    σz = 0.815575;
                }
                break;
            case "D":
                if (distance >= 0 && distance <= 1000) {
                    σz = 0.826212;
                } else if (distance > 1000 && distance <= 10000) {
                    σz = 0.632023;
                } else if (distance > 10000) {
                    σz = 0.55536;
                }
                break;
            case "D~E":
                if (distance >= 0 && distance <= 2000) {
                    σz = 0.776864;
                } else if (distance > 2000 && distance <= 10000) {
                    σz = 0.572347;
                } else if (distance > 10000) {
                    σz = 0.499149;
                }
                break;
            case "E":
                if (distance >= 0 && distance <= 1000) {
                    σz = 0.788370;
                } else if (distance > 1000 && distance <= 10000) {
                    σz = 0.565188;
                } else if (distance > 10000) {
                    σz = 0.414743;
                }
                break;
            case "F":
                if (distance >= 0 && distance <= 1000) {
                    σz = 0.784400;
                } else if (distance > 1000 && distance <= 10000) {
                    σz = 0.525969;
                } else if (distance > 10000) {
                    σz = 0.322659;
                }
                break;
            default:
                break;
        }
        return σz;
    }

}
