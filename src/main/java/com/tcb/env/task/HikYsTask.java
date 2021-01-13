package com.tcb.env.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tcb.env.model.hikvision.YsAccessTokenModel;
import com.tcb.env.model.hikvision.YsResultModel;
import com.tcb.env.service.IDeviceVideoService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WangLei
 * @Description: 海康萤石请求任务
 * @Date: Create in 2019/7/3 14:03
 * @Modify by WangLei
 */
@Component
public class HikYsTask {

    /**
     * 日志输出标记
     */
    private static final String LOG = "HikYsTask";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(HikYsTask.class);

    private String charset = "utf-8";

    private String videoBrand = "1";

    @Resource
    private IDeviceVideoService deviceVideoService;

    /**
     * 定时更新海康AccessToken
     */
    @Scheduled(cron = "0 5 06 * * ?")
    public void taskHikCycle() {
        if (!DefaultArgument.ACCESS_TOKEN_UPDATE) {
            return;
        } else {
            String host = "";
            String path = "https://open.ys7.com/api/lapp/token/get";
            Map<String, String> headerList = new HashMap<String, String>();
            headerList.put("Content-Type", "application/x-www-form-urlencoded");
            //请求参数
            Map<String, String> queryList = new HashMap<String, String>();
            queryList.put("appKey", "241d54cfe3b44af9aac148216fd8e99a");
            queryList.put("appSecret", "c1ee1566146cdc7e480cdbd1ed6b58bb");
            try {
                HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, "");
                if (httpResponse != null) {
                    HttpEntity resEntity = httpResponse.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, charset);
                        System.out.println("result:" + result);
                        Gson gson = new GsonBuilder().create();
                        YsResultModel<YsAccessTokenModel> res = gson.fromJson(
                                result, new TypeToken<YsResultModel<YsAccessTokenModel>>() {
                                }.getType());
                        if (res != null && res.getCode().equals("200")) {
                            //更新AccessToken
                            String accessToken = res.getData().getAccessToken();
                            deviceVideoService.updateVideoToken(videoBrand, accessToken);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(LOG + "：更新AccessToken失败，异常信息为：" + e.getMessage());
            }
        }
    }
}
