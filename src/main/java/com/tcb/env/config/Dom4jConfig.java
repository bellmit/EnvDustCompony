package com.tcb.env.config;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * <p>
 * [功能描述]：解析XML配置信息帮助类
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月22日下午2:23:59
 * @since EnvDust 1.0.0
 */
public class Dom4jConfig implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 声明日志对象
     */
    private Logger logger = LogManager.getLogger(Dom4jConfig.class);

    /**
     * 声明默认设备对象
     */
    @Resource
    private DeDevConfig deDevConfig;

    /**
     * 声明默认数据库名称对象
     */
    @Resource
    private DataBaseConfig dataBaseConfig;

    /**
     * @return the deDevConfig
     */
    public DeDevConfig getDeDevConfig() {
        return deDevConfig;
    }

    /**
     * @param deDevConfig the deDevConfig to set
     */
    public void setDeDevConfig(DeDevConfig deDevConfig) {
        this.deDevConfig = deDevConfig;
    }

    /**
     * @return the dataBaseConfig
     */
    public DataBaseConfig getDataBaseConfig() {
        return dataBaseConfig;
    }

    /**
     * @param dataBaseConfig the dataBaseConfig to set
     */
    public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    public void init() {
        InputStream fileURL = null;
        try {
            fileURL = getClass().getClassLoader().getResourceAsStream("dom4j-config.xml");
            parseXML(fileURL);
            fileURL.close();
        } catch (IOException ex) {
            logger.error("解析dom4j-config.xml配置信息失败，错误信息：" + ex.toString());
        } catch (Exception ex) {
            logger.error("解析dom4j-config.xml配置信息失败，错误信息：" + ex.toString());
        } finally {
            if (fileURL != null) {
                try {
                    fileURL.close();
                } catch (IOException ex) {
                    logger.error("关闭dom4j-config.xml失败，错误信息：" + ex.toString());
                }
            }
        }

    }

    private void parseXML(InputStream file) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element root = document.getRootElement();

        // 解析DeDev的XML
        try {
            Element dedev = root.element("DeDev-Config");
            deDevConfig.setPort(Integer.parseInt(dedev.elementTextTrim("Port")));
            deDevConfig.setReceiveBufferSize(Integer.parseInt(dedev.elementTextTrim("ReceiveBufferSize")));
            deDevConfig.setReaderIdleTime(Integer.parseInt(dedev.elementText("ReaderIdleTime")));
            deDevConfig.setExecutorThreadNum(Integer.parseInt(dedev.elementTextTrim("ExecutorThreadNum")));
            deDevConfig.setGateWayName(dedev.elementText("GateWayName"));
            deDevConfig.setSessionCount(Integer.parseInt(dedev.elementTextTrim("SessionCount")));
            deDevConfig.setVideoFlag(Integer.parseInt(dedev.elementTextTrim("VideoFlag")));
            deDevConfig.setProxyUrl(dedev.elementTextTrim("ProxyUrl"));
            deDevConfig.setRootUser(dedev.elementTextTrim("RootUser"));
            deDevConfig.setRootAuthority(dedev.elementTextTrim("RootAuthority"));
            deDevConfig.setSysUrl(dedev.elementTextTrim("SysUrl"));
            deDevConfig.setMailPath(dedev.elementTextTrim("MailPath"));
            deDevConfig.setExternalApiUrl(dedev.elementTextTrim("ExternalApiUrl"));

        } catch (Exception ex) {
            logger.error("解析默认设备网关信息失败，错误信息：" + ex.toString());
        }

        // 解析DataBase的XML
        try {
            Element dataBase = root.element("DataBase-Config");
            dataBaseConfig.setDbName(dataBase.elementTextTrim("DbName"));
            dataBaseConfig.setDbOldName(dataBase.elementTextTrim("DbOldName"));
        } catch (Exception ex) {
            logger.error("解析默认数据库名称信息失败，错误信息：" + ex.toString());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            init();
        }
    }
}
