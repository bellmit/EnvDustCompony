package com.tcb.env.model;

/**
 * <p>[功能描述]：DeviceProjectModel</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 *
 * @author	王垒
 * @version	1.0, 2019年2月19日上午10:19:20
 * @since	EnvDust 1.0.0
 *
 */
public class DeviceProjectModel extends BaseModel {

    private String projectId;
    private String projectCode;
    private String projectName;
    private String projectOrder;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
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

    public String getProjectOrder() {
        return projectOrder;
    }

    public void setProjectOrder(String projectOrder) {
        this.projectOrder = projectOrder;
    }

}
