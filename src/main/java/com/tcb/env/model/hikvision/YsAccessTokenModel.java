package com.tcb.env.model.hikvision;

/**
 * @Author: WangLei
 * @Description: 获取萤石云AccessToken
 * @Date: Create in 2019/7/3 15:01
 * @Modify by WangLei
 */
public class YsAccessTokenModel {

    private String accessToken;
    private String expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
