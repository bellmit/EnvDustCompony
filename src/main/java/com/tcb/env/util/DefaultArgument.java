package com.tcb.env.util;

/**
 * <p>[功能描述]：默认参数值</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月18日下午3:51:32
 * @since EnvDust 1.0.0
 */
public class DefaultArgument {

    /**
     * 禁止被实例化
     */
    private DefaultArgument() {
    }

    ;
    /**
     * 登录session标识
     */
    public static final String LOGIN_USER = "loginUser";
    /**
     * int默认值:-1
     */
    public static final int INT_DEFAULT = -1;
    /**
     * 无选择默认值:0
     */
    public static final String NONE_DEFAULT = "0";
    /**
     * double默认值:-1
     */
    public static final double DOUBLE_DEFAULT = -1;

    /**
     * 小时内实时数据个数
     */
    public static final int HOUR_COUNT = 60;

    /**
     * pwd默认值:111111
     */
    public static final String PWD_DEFAULT = "111111";

    /**
     * 删除标记默认值:false
     */
    public static final boolean DEL_DEFAULT = false;

    /**
     * 系统最底层级别
     */
    public static final String BOTTOM_LEVEL_FALG = "4";

    /**
     * 默认实时查询时间段（单位：毫秒）
     */
    public static final int TIMELY_ONE_HOUR = 1000 * 60 * 60;

    /**
     * 统计时间字段名称
     */
    public static final String STA_TIME = "time";

    /**
     * 实时查询数据间隔时间（毫秒）
     */
    public static final int TIMELY_TIME = 10000;

    /**
     * 长连接超时（毫秒），需要测试是够还会出现长链接502错误
     */
    public static final int OUT_TIME = 60000;

    /**
     * 网络监控间隔（毫秒）
     */
    public static final int NET_TIME = 60000;

    /**
     * 地图设备状态间隔（毫秒）
     */
    public static final int MAP_TIME = 10000;

    /**
     * 请求天气时间间隔（毫秒）
     */
    public static final int WEATHER_TIME = 10000;

    /**
     * 发送短信（毫秒）
     */
    public static final int SMS_TIME = 5000;

    /**
     * 查询控件级别：1（模块）
     */
    public static final String MODULE = "1";
    /**
     * 查询控件级别：2（页面）
     */
    public static final String PAGE = "2";
    /**
     * 查询控件级别：3（控件）
     */
    public static final String CONTROL = "3";

    /**
     * 控件处理方式：可用
     */
    public static final int DEAL_TYPE = 1;

    /**
     * 是否服务器计算统计实时数据：true,false
     */
    public static final boolean SYS_STA_RLD = true;

    /**
     * 是否服务器计算统计小时数据：true,false
     */
    public static final boolean SYS_STA_MINUTE = false;

    /**
     * 是否服务器计算统计小时数据：true,false
     */
    public static final boolean SYS_STA_HOUR = false;

    /**
     * 是否服务器计算统计每日数据：true,false
     */
    public static final boolean SYS_STA_DAY = false;

    /**
     * 设备站点图片名称(正常)
     */
    public static final String DEV_NORMAL = "icon-stationlink";

    /**
     * 设备站点图片名称(超标)
     */
    public static final String DEV_ALARM = "icon-stationalarm";

    /**
     * 设备站点图片名称(一级)
     */
    public static final String DEV_ALARM1 = "icon-stationalarm1";

    /**
     * 设备站点图片名称(二级)
     */
    public static final String DEV_ALARM2 = "icon-stationalarm2";

    /**
     * 设备站点图片名称(三级)
     */
    public static final String DEV_ALARM3 = "icon-stationalarm3";

    /**
     * 设备站点图片名称(故障)
     */
    public static final String DEV_FAULT = "icon-stationfault";

    /**
     * 设备站点图片名称(断连)
     */
    public static final String DEV_UNLINK = "icon-stationunlink";

    /**
     * 报警更新系统标识
     */
    public static final String ALARM_UPD = "alarmupd";

    /**
     * 系统交互
     */
    public static final String PRO_ST_SYSTEM = "91";

    /**
     * 空气质量监测
     */
    public static final String PRO_ST_WEV = "22";

    /**
     * 大气环境污染源
     */
    public static final String PRO_ST_ATM = "31";

    /**
     * 设置监测参数报警门限值
     */
    public static final String PRO_CN_ALARMSET = "1022";

    /**
     * 设置实时数据间隔
     */
    public static final String PRO_CN_REALINTERVAL = "1062";

    /**
     * 取监测参数实时数据
     */
    public static final String PRO_CN_REALBEGIN = "2011";

    /**
     * 停止查看实时数据
     */
    public static final String PRO_CN_REALSTOP = "2012";

    /**
     * 取监测参数分钟数据
     */
    public static final String PRO_CN_GETMINUTE = "2051";

    /**
     * 取监测参数小时数据
     */
    public static final String PRO_GET_HOUR = "2061";

    /**
     * 取监测参数日数据
     */
    public static final String PRO_GET_DAY = "2031";

    /**
     * 超标采样
     */
    public static final String PRO_NET_SAMPLE = "3015";

    /**
     * 权限组最大容量
     */
    public static final int MAX_AHR_COUNT = 5000;

    /**
     * 已在权限组中添加此设备
     */
    public static final String IS_AHR_DEVICE = "2";

    /**
     * 未在权限组中添加此设备
     */
    public static final String NOT_AHR_DEVICE = "1";

    /**
     * 在线设备标识
     */
    public static final String ON_LINE_FLAG = "online";

    /**
     * 主数据库保存数据天数
     */
    public static final int RECENT_DAYS = 30;

    /**
     * 一度等于111322.2222米
     */
    public static final double ONE_DEGREE = 111322.2222;

    /**
     * 烟气流量编码
     */
    public static final String GAS_FLOW_CODE = "a19003";

    /**
     * 风速编码
     */
    public static final String WS = "WS";

    /**
     * 风向编码
     */
    public static final String WD = "WD";

    /**
     * 小时数据编码
     */
    public static final String HOUR_CODE = "2061";

    /**
     * 请求天气正确编码:ok
     */
    public static final String HE_OK = "ok";

    /**
     * 年月日时报表模板
     */
    public static final String REPORT_ENVSTA_DMY = "envstaDMY";

    /**
     * 时间段报表模板
     */
    public static final String REPORT_ENVSTA_TIMES = "envstaTimes";

    /**
     * 连续监测报表日模板
     */
    public static final String REPORT_ENVSTA_OLRD = "envstaOLRD";

    /**
     * 连续监测报表月模板
     */
    public static final String REPORT_ENVSTA_OLRM = "envstaOLRM";

    /**
     * 连续监测报表年模板
     */
    public static final String REPORT_ENVSTA_OLRY = "envstaOLRY";

    /**
     * 连续监测报表时间段模板
     */
    public static final String REPORT_ENVSTA_OLRT = "envstaOLRT";

    /**
     * 同比环比报表模板
     */
    public static final String REPORT_ENVSTA_COMPARE = "compare";

    /**
     * 超标统计报表模板
     */
    public static final String REPORT_ENVSTA_OVER = "overAlarm";

    /**
     * 污染排放量统计报表模板
     */
    public static final String REPORT_ENVSTA_DIS = "discharge";

    /**
     * 212协议废气编码
     */
    public static final String PRO_EXHOUST_GAS_CODE = "a00000";//a00000//B02

    /**
     * 212协议烟气流速编码
     */
    public static final String PRO_GAS_VELOCITY_CODE = "a01011";//a01011//S02

    /**
     * 212协议烟气温度编码
     */
    public static final String PRO_GAS_TEMPERATURE_CODE = "a01012";//a01012//S03

    /**
     * 212协议烟气湿度编码
     */
    public static final String PRO_GAS_HUMIDITY_CODE = "a01014";//a01014//S05

    /**
     * 212协议风向编码
     */
    public static final String PRO_2017_WD_CODE = "a01008";//a01008

    /**
     * 212协议风速编码
     */
    public static final String PRO_2017_WS_CODE = "a01007";//a01007

    /**
     * 上传文件夹名称
     */
    public static final String UPLOAD_FOLDER = "upload";

    /**
     * 默认报警级别
     */
    public static final String ALARM_LEVEL_NO = "3";

    /**
     * 默认视频token更新标志
     */
    public static final boolean ACCESS_TOKEN_UPDATE = true;

    // 如果不知道appid，请登录open.lechange.com，开发者服务模块中创建应用
    public static String AJHUA_APPID = "lc86e152ca10734957";

    // 如果不知道appsecret，请登录open.lechange.com，开发者服务模块中创建应用
    public static String AJHUA_SECRET = "18d7e0a002434d9187700fdb5a2191";

}
