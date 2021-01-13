package com.tcb.env.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>[功能描述]：枚举帮助类</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月28日上午11:06:06
 * @since EnvDust 1.0.0
 */
public class EnumUtil {

    /**
     * <p>[功能描述]：树形结构状态值</p>
     * <p>Copyright (c) 1993-2016 TCB Corporation</p>
     *
     * @author 王垒
     * @version 1.0, 2016年3月28日上午11:05:50
     * @since EnvDust 1.0.0
     */
    public enum TreeStatus {
        open,
        closed
    }

    /**
     * <p>[功能描述]：查询时间频率</p>
     * <p>Copyright (c) 1993-2016 TCB Corporation</p>
     *
     * @author 王垒
     * @version 1.0, 2016年3月28日上午11:05:50
     * @since EnvDust 1.0.0
     */
    public enum TimeFreque {
        PERMINUTE,
        PERHOUR,
        PERDAY,
        PERMONTH,
        PERQUARTER
    }

    /**
     * <p>[功能描述]：气象参数</p>
     * <p>Copyright (c) 1997-2017 TCB Corporation</p>
     *
     * @author 王垒
     * @version 1.0, 2017年12月7日下午1:45:19
     * @since EnvDust 1.0.0
     */
    public enum HeWeather {

        HUM("hum", "相对湿度"), PCPN("pcpn", "降水量"), PRES("pres", "大气压强"),
        TMP("tmp", "温度"), WIND_DEG("wind_deg", "风向"), WIND_SPD("wind_spd", "风速");

        // 构造方法
        private HeWeather(String code, String name) {
            this.code = code;
            this.name = name;
        }

        // 成员变量
        private String code;
        private String name;
        private static final Map<String, String> heWeatherMap = new HashMap<String, String>();

        static {
            for (HeWeather s : EnumSet.allOf(HeWeather.class)) {
                heWeatherMap.put(s.getCode(), s.getName());
            }
        }

        /**
         * @return the code
         */
        public String getCode() {
            return code;
        }

        /**
         * @param code the code to set
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the name
         */
        public static String getName(String code) {
            for (HeWeather c : HeWeather.values()) {
                if (c.getCode().equalsIgnoreCase(code)) {
                    return c.name;
                }
            }
            return null;
        }

        /**
         * @return the heweathermap
         */
        public static Map<String, String> getHeweathermap() {
            return heWeatherMap;
        }
    }

    public enum GasPath {

        SI("SampleIn", "进样分析状态"),
        GB("GasBalance", "气态平衡状态"),
        BF("BackFlush", "反吹状态"),
        SC("StdCali", "标定状态"),
        FC("FullCali", "全流路反标状态");

        // 构造方法
        GasPath(String code, String name) {
            this.code = code;
            this.name = name;
        }

        // 成员变量
        private String code;
        private String name;
        private static final Map<String, String> gasPathMap = new HashMap<String, String>();

        static {
            for (GasPath s : EnumSet.allOf(GasPath.class)) {
                gasPathMap.put(s.getCode(), s.getName());
            }
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        /**
         * @return the name
         */
        public static String getName(String code) {
            for (GasPath c : GasPath.values()) {
                if (c.getCode().equalsIgnoreCase(code)) {
                    return c.name;
                }
            }
            return null;
        }

    }

}
