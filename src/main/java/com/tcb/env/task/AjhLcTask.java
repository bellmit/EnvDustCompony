package com.tcb.env.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tcb.env.model.hikvision.YsAccessTokenModel;
import com.tcb.env.model.hikvision.YsResultModel;
import com.tcb.env.pojo.DeviceVideo;
import com.tcb.env.service.IDeviceVideoService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: WangLei
 * @Description: 大华乐橙请求任务
 * @Date: Create in 2020/4/13 14:03
 * @Modify by WangLei
 */
@Component
public class AjhLcTask {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AjhLcTask";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AjhLcTask.class);

    private String charset = "utf-8";

    private String videoBrand = "2";

    @Resource
    private IDeviceVideoService deviceVideoService;

    /**
     * 定时更新大华AccessToken
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void taskAhjCycle() {
        if (!DefaultArgument.ACCESS_TOKEN_UPDATE) {
            return;
        } else {
            String host = "";
            String path = "https://openapi.lechange.cn:443/openapi/accessToken";
            Map<String, String> headerList = new HashMap<String, String>();
            headerList.put("Content-Type", "application/json");
            Map<String, String> queryList = new HashMap<String, String>();
            //请求参数
            HashMap<String, Object> paramsMap = new HashMap<String, Object>();
            Map<String, Object> map = paramsInit(paramsMap);
            String json = JSON.toJSONString(map);
            try {
                HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, json);
                if (httpResponse != null) {
                    HttpEntity resEntity = httpResponse.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println("result:" + result);
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        JSONObject jsonResult = jsonObject.getJSONObject("result");
                        JSONObject jsonData = jsonResult.getJSONObject("data");
                        String jsonCode = jsonResult.getString("code");
                        if (jsonCode != null && jsonCode.equals("0")) {
                            //更新AccessToken
                            String accessToken = jsonData.getString("accessToken");
                            deviceVideoService.updateVideoToken(videoBrand, accessToken);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(LOG + "：更新AccessToken失败，异常信息为：" + e.getMessage());
            }
        }
    }

    /**
     * 定时更新大华kitToken
     */
    @Scheduled(cron = "0 05 0/1 * * ?")
    public void taskAhjCycle_Kit() {
        if (!DefaultArgument.ACCESS_TOKEN_UPDATE) {
            return;
        } else {
            DeviceVideo deviceVideo = new DeviceVideo();
            deviceVideo.setVideoBrand(2);
            List<DeviceVideo> deviceVideoList = deviceVideoService.getDeviceVideo(deviceVideo);
            if (deviceVideoList != null && deviceVideoList.size() > 0) {
                for (DeviceVideo temp : deviceVideoList) {
                    String videoCode = temp.getVideoCode();
                    String token = temp.getVideoToken();
                    if (!StringUtils.isEmpty(videoCode) && !StringUtils.isEmpty(token)) {
                        String host = "";
                        String path = "https://openapi.lechange.cn:443/openapi/getKitToken";
                        Map<String, String> headerList = new HashMap<String, String>();
                        headerList.put("Content-Type", "application/json");
                        Map<String, String> queryList = new HashMap<String, String>();
                        //请求参数
                        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
                        paramsMap.put("token", token);
                        paramsMap.put("deviceId", videoCode);
                        paramsMap.put("channelId", "0");
                        paramsMap.put("type", "1");
                        Map<String, Object> map = paramsInit(paramsMap);
                        String json = JSON.toJSONString(map);
                        try {
                            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, json);
                            if (httpResponse != null) {
                                HttpEntity resEntity = httpResponse.getEntity();
                                if (resEntity != null) {
                                    String result = EntityUtils.toString(resEntity, charset);
                                    System.out.println("result:" + result);
                                    JSONObject jsonObject = JSONObject.parseObject(result);
                                    JSONObject jsonResult = jsonObject.getJSONObject("result");
                                    JSONObject jsonData = jsonResult.getJSONObject("data");
                                    String jsonCode = jsonResult.getString("code");
                                    if (jsonCode != null && jsonCode.equals("0")) {
                                        //更新kitToken
                                        String kitToken = jsonData.getString("kitToken");
                                        deviceVideoService.updateVideoKitToken(videoCode, kitToken);
                                        System.out.println("kitToken:" + kitToken);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            logger.error(LOG + "：更新kitToken失败，异常信息为：" + e.getMessage());
                        }
                    }
                }
            }

        }
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
