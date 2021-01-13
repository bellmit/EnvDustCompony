package com.tcb.env.pojo;

/**
 * <p>[功能描述]：设备项目pojo</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 *
 * @author	王垒
 * @version	1.0, 2019年2月19日上午09:02:31
 * @since	EnvDust 1.0.0
 *
 */
public class DeviceProject extends BasePojo {

    private int projectId;
    private String projectCode;
    private String projectName;
    private int projectOrder;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectOrder() {
        return projectOrder;
    }

    public void setProjectOrder(int projectOrder) {
        this.projectOrder = projectOrder;
    }

}
