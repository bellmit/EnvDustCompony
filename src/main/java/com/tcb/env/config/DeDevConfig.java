package com.tcb.env.config;

import org.springframework.stereotype.Component;

/**
 * <p>[功能描述]：在线监测系统默认设备参数</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月22日下午2:20:30
 * @since EnvDust 1.0.0
 */
@Component("DeDevConfig")
public class DeDevConfig {

    private int port;
    private int receiveBufferSize;
    private int readerIdleTime;
    private int executorThreadNum;
    private String gateWayName;
    private int sessionCount;
    private int VideoFlag;
    private String proxyUrl;
    private String rootUser;
    private String rootAuthority;
    private String sysUrl;
    private String mailPath;
    private String externalApiUrl;

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the receiveBufferSize
     */
    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    /**
     * @param receiveBufferSize the receiveBufferSize to set
     */
    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    /**
     * @return the readerIdleTime
     */
    public int getReaderIdleTime() {
        return readerIdleTime;
    }

    /**
     * @param readerIdleTime the readerIdleTime to set
     */
    public void setReaderIdleTime(int readerIdleTime) {
        this.readerIdleTime = readerIdleTime;
    }

    /**
     * @return the executorThreadNum
     */
    public int getExecutorThreadNum() {
        return executorThreadNum;
    }

    /**
     * @param executorThreadNum the executorThreadNum to set
     */
    public void setExecutorThreadNum(int executorThreadNum) {
        this.executorThreadNum = executorThreadNum;
    }

    /**
     * @return the gateWayName
     */
    public String getGateWayName() {
        return gateWayName;
    }

    /**
     * @param gateWayName the gateWayName to set
     */
    public void setGateWayName(String gateWayName) {
        this.gateWayName = gateWayName;
    }

    /**
     * @return the sessionCount
     */
    public int getSessionCount() {
        return sessionCount;
    }

    /**
     * @param sessionCount the sessionCount to set
     */
    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    /**
     * @return the videoFlag
     */
    public int getVideoFlag() {
        return VideoFlag;
    }

    /**
     * @param videoFlag the videoFlag to set
     */
    public void setVideoFlag(int videoFlag) {
        VideoFlag = videoFlag;
    }

    /**
     * @return the proxyUrl
     */
    public String getProxyUrl() {
        return proxyUrl;
    }

    /**
     * @param proxyUrl the proxyUrl to set
     */
    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    /**
     * @return the rootUser
     */
    public String getRootUser() {
        return rootUser;
    }

    /**
     * @param rootUser the rootUser to set
     */
    public void setRootUser(String rootUser) {
        this.rootUser = rootUser;
    }

    /**
     * @return the rootAuthority
     */
    public String getRootAuthority() {
        return rootAuthority;
    }

    /**
     * @param rootAuthority the rootAuthority to set
     */
    public void setRootAuthority(String rootAuthority) {
        this.rootAuthority = rootAuthority;
    }

    /**
     * @return the sysUrl
     */
    public String getSysUrl() {
        return sysUrl;
    }

    /**
     * @param sysUrl the sysUrl to set
     */
    public void setSysUrl(String sysUrl) {
        this.sysUrl = sysUrl;
    }

    public String getMailPath() {
        return mailPath;
    }

    public void setMailPath(String mailPath) {
        this.mailPath = mailPath;
    }

    public String getExternalApiUrl() {
        return externalApiUrl;
    }

    public void setExternalApiUrl(String externalApiUrl) {
        this.externalApiUrl = externalApiUrl;
    }

}
