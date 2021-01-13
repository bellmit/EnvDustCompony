package com.tcb.env.task;

import com.tcb.env.message.CameraMessage;
import com.tcb.env.service.IDeviceVideoService;
import com.tcb.env.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangLei
 * @Description: 超标抓拍
 * @Date: Create in 2019/7/4 9:24
 * @Modify by WangLei
 */
public class PhotographQuartz {

    @Autowired
    private IDeviceVideoService deviceVideoService;

    private static Timestamp beginTime;
    private static Timestamp endTime;

    public void execute() {
        try {
            Date now = new Date();
            if (beginTime == null) {
                beginTime = new Timestamp(now.getTime() - 60 * 1000);
            }
            endTime = new Timestamp(now.getTime());
            List<CameraMessage> cameraMessageList = deviceVideoService.getPhotographCamera(getBeginTime(), getEndTime());
            if (cameraMessageList == null || cameraMessageList.size() == 0) {
                System.out.println("无告警无需抓图");
                return;
            } else {
                beginTime = endTime;
                for (CameraMessage cameraMessage : cameraMessageList) {
                    getCameraPic(cameraMessage);
                    //TODO 需要做解析以及记录存入功能
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCameraPic(CameraMessage cameraMessage) {
        String host = "";
        String path = "https://open.ys7.com/api/lapp/device/capture";
        Map<String, String> headerList = new HashMap<String, String>();
        headerList.put("Content-Type", "application/x-www-form-urlencoded");
        //请求参数
        Map<String, String> queryList = new HashMap<String, String>();
        queryList.put("accessToken", cameraMessage.getVideoToken());
        queryList.put("deviceSerial", cameraMessage.getVideoCode());
        queryList.put("channelNo", String.valueOf(cameraMessage.getChannelNo()));
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, "");
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                    System.out.println("result:" + result);
//                    Gson gson = new GsonBuilder().create();
//                    YsResultModel<YsAccessTokenModel> res = gson.fromJson(
//                            result, new TypeToken<YsResultModel<YsAccessTokenModel>>() {
//                            }.getType());
//                    if (res != null) {
//                        //更新AccessToken
//                        String accessToken = res.getData().getAccessToken();
//                        deviceVideoService.updateVideoToken(accessToken);
//                    }
                }
            }
        } catch (Exception e) {
//            logger.error(LOG + "：更新AccessToken失败，异常信息为：" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Timestamp getBeginTime() {
        return beginTime;
    }

    public static void setBeginTime(Timestamp beginTime) {
        PhotographQuartz.beginTime = beginTime;
    }

    public static Timestamp getEndTime() {
        return endTime;
    }

    public static void setEndTime(Timestamp endTime) {
        PhotographQuartz.endTime = endTime;
    }

}
