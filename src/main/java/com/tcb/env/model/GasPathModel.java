package com.tcb.env.model;

/**
 * <p>[功能描述]：进气状态model</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 10:40 2019/12/10
 * @since EnvDust 1.0.0
 */
public class GasPathModel {

    private String gasPath;
    private String switchTime;
    private String gasPathName;

    public String getGasPath() {
        return gasPath;
    }

    public void setGasPath(String gasPath) {
        this.gasPath = gasPath;
    }

    public String getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(String switchTime) {
        this.switchTime = switchTime;
    }

    public String getGasPathName() {
        return gasPathName;
    }

    public void setGasPathName(String gasPathName) {
        this.gasPathName = gasPathName;
    }
}
