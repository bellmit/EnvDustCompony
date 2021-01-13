package com.tcb.env.controller.api;

import com.alibaba.fastjson.JSON;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * [功能描述]：大华视频系统接口
 * </p>
 * <p>
 * Copyright (c) 2000-2020 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2020年04月13日下午10:13:06
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping(value = "/AjHuaApi")
public class AjHuaAPI {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AjHuaAPI";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AjHuaAPI.class);

    private String charset = "utf-8";

    /**
     * 获取直播地址和直播状态
     *
     * @param token
     * @param deviceId
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/getLiveStreamInfo", method = {RequestMethod.POST})
    @ResponseBody
    public String getLiveStreamInfo(String token, String deviceId,
                                    @RequestParam(defaultValue = "0") String channelId) {
        String result = "";
        String host = "";
        String path = "https://openapi.lechange.cn:443/openapi/getLiveStreamInfo";
        Map<String, String> headerList = new HashMap<>();
        headerList.put("Content-Type", "application/json");
        Map<String, String> queryList = new HashMap<>();
        //请求参数
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", token);
        paramsMap.put("deviceId", deviceId);
        paramsMap.put("channelId", channelId);
        Map<String, Object> map = paramsInit(paramsMap);
        String json = JSON.toJSONString(map);
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, json);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：获取直播地址和直播状态失败，异常信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 云台移动控制
     * @param token
     * @param deviceId
     * @param operation
     * @param duration
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/controlMovePTZ", method = {RequestMethod.POST})
    @ResponseBody
    public String controlMovePTZ(String token, String deviceId, String operation,
                                 @RequestParam(defaultValue = "1000") String duration,
                                 @RequestParam(defaultValue = "0") String channelId) {
        String result = "";
        String host = "";
        String path = "https://openapi.lechange.cn:443/openapi/controlMovePTZ";
        Map<String, String> headerList = new HashMap<>();
        headerList.put("Content-Type", "application/json");
        Map<String, String> queryList = new HashMap<>();
        //请求参数
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", token);
        paramsMap.put("deviceId", deviceId);
        paramsMap.put("channelId", channelId);
        paramsMap.put("operation", operation);//0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-停止
        paramsMap.put("duration", duration);//移动持续时间，单位毫秒
        Map<String, Object> map = paramsInit(paramsMap);
        String json = JSON.toJSONString(map);
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, json);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：云台移动控制失败，异常信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 设备抓图
     * @param token
     * @param deviceId
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/setDeviceSnapEnhanced", method = {RequestMethod.POST})
    @ResponseBody
    public String setDeviceSnapEnhanced(String token, String deviceId,
                                 @RequestParam(defaultValue = "0") String channelId) {
        String result = "";
        String host = "";
        String path = "https://openapi.lechange.cn:443/openapi/setDeviceSnapEnhanced";
        Map<String, String> headerList = new HashMap<>();
        headerList.put("Content-Type", "application/json");
        Map<String, String> queryList = new HashMap<>();
        //请求参数
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", token);
        paramsMap.put("deviceId", deviceId);
        paramsMap.put("channelId", channelId);
        Map<String, Object> map = paramsInit(paramsMap);
        String json = JSON.toJSONString(map);
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, json);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：设备抓图失败，异常信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 查询设备本地录像片段
     * @param token
     * @param deviceId
     * @param beginTime
     * @param endTime
     * @param type
     * @param queryRange
     * @param channelId
     * @return
     */
    @RequestMapping(value = "/queryLocalRecords", method = {RequestMethod.POST})
    @ResponseBody
    public String queryLocalRecords(String token, String deviceId,String beginTime,String endTime,
                                    @RequestParam(defaultValue = "All") String type,
                                    @RequestParam(defaultValue = "1-30") String queryRange,
                                    @RequestParam(defaultValue = "0") String channelId) {
        String result = "";
        String host = "";
        String path = "https://openapi.lechange.cn:443/openapi/queryLocalRecords";
        Map<String, String> headerList = new HashMap<>();
        headerList.put("Content-Type", "application/json");
        Map<String, String> queryList = new HashMap<>();
        //请求参数
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", token);
        paramsMap.put("deviceId", deviceId);
        paramsMap.put("channelId", channelId);
        paramsMap.put("beginTime", beginTime);// [String][Not Null]开始时间
        paramsMap.put("endTime", endTime);// [String][Not Null]结束时间
        paramsMap.put("type", type);// [String]类型
        paramsMap.put("queryRange", queryRange); //[String][Not Null]单次查询上限30,1-30表示第1条到第30条,包含30
        Map<String, Object> map = paramsInit(paramsMap);
        String json = JSON.toJSONString(map);
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, json);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：查询设备本地录像片段失败，异常信息为：" + e.getMessage());
        }
        return result;
    }

    protected static Map<String, Object> paramsInit(Map<String, Object> paramsMap) {
        long time = System.currentTimeMillis() / 1000;
        String nonce = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();

        StringBuilder paramString = new StringBuilder();
        paramString.append("time:").append(time).append(",");
        paramString.append("nonce:").append(nonce).append(",");
        paramString.append("appSecret:").append(DefaultArgument.AJHUA_SECRET);

        String sign = "";
        // 计算MD5得值
        try {
            System.out.println("传入参数：" + paramString.toString().trim());
            sign = DigestUtils.md5Hex(paramString.toString().trim().getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> systemMap = new HashMap<String, Object>();
        systemMap.put("ver", "1.0");
        systemMap.put("sign", sign);
        systemMap.put("appId", DefaultArgument.AJHUA_APPID);
        systemMap.put("nonce", nonce);
        systemMap.put("time", time);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("system", systemMap);
        map.put("params", paramsMap);
        map.put("id", id);
        return map;
    }

}
