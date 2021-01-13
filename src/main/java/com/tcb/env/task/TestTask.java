package com.tcb.env.task;

import com.tcb.env.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WangLei
 * @Description: 测试任务（用于测试）
 * @Date: Create in 2019/7/3 14:03
 * @Modify by WangLei
 */
@Component
public class TestTask {

    /**
     * 日志输出标记
     */
    private static final String LOG = "TestTask";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(TestTask.class);

    private String charset = "utf-8";

    /**
     * 定时更新海康AccessToken
     */
//    @Scheduled(cron = "0 45 * * * ?")
    public void taskCycle() {
        String host = "";
        String path = "http://api.map.baidu.com/reverse_geocoding/v3/";
        Map<String, String> headerList = new HashMap<String, String>();
//        headerList.put("Content-Type", "application/x-www-form-urlencoded");
        //请求参数
//        String snStr = SnCalUtil.getBaiDuSn("天津","yufIvSTXqecRGPL2hGXrwgS1Wkqk0kGW");
        Map<String, String> queryList = new HashMap<String, String>();
        queryList.put("ak", "yufIvSTXqecRGPL2hGXrwgS1Wkqk0kGW");
        queryList.put("output", "json");
        queryList.put("coordtype", "bd09ll");//wgs84ll
        queryList.put("location", "39.092486,117.564874");
        queryList.put("extensions_road", "true");
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, "");
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, charset);
                    System.out.println("result:" + result);
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：测试任务失败，异常信息为：" + e.getMessage());
        }
    }
}
