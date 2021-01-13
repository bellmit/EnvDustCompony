package com.tcb.env.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.util.HuaWeiSendMessage2;
import com.tcb.env.util.SendMailUtil;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tcb.env.config.SMSConfig;
import com.tcb.env.pojo.Alarm;
import com.tcb.env.service.IAlarmService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.HuaWeiSendMessage;
import org.springframework.util.StringUtils;

/**
 * <p>
 * [功能描述]：发送短信
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2016年5月19日下午5:58:34
 * @since EnvDust 1.0.0
 */
@Component
public class SmsTask {

    /**
     * 日志输出标记
     */
    private static final String LOG = "SmsTask";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(SmsTask.class);

    /**
     * 声明Alarm服务
     */
    @Resource
    private IAlarmService alarmService;

    /**
     * 配置文件服务类
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    @Scheduled(cron = "0 0/5 * * * ?")
//	@Scheduled(cron = "0 08 * * * ?")
    public void taskCycle() {
        try {
//			SMSConfig smsConfig = new SMSConfig();
//			smsConfig.setPhoneNumer("15900243937");
//			smsConfig.setContent("这个是个程序发起的测试短信");
//			HuaWeiSendMessage.doIt(smsConfig);
//            HuaWeiSendMessage2.sengMessage(new SMSConfig());
//			SendMailUtil.sendEmail("wanglei3178@163.com","报警通知","测试邮件");

            List<Alarm> alarmList = alarmService.getSmsAlarmInfo();
            String alarmId = "";
            if (alarmList != null && alarmList.size() > 0) {
                for (Alarm alarm : alarmList) {
                    try {
                        if (alarm != null) {
                            alarmId = String.valueOf(alarm.getAlarmId());
                            String deviceId = String.valueOf(alarm.getDevice().getDeviceId());
                            String statusCode = alarm.getStatus().getStatusCode();
                            String thingCode = null;
                            if (alarm.getMonitor() != null) {
                                thingCode = alarm.getMonitor().getThingCode();
                            }
                            Integer receiveFlag = alarm.getReceiveFlag();
                            switch (receiveFlag) {
                                case 1: {
                                    //全部
                                    boolean resultPhone = sendMessage2Phone(alarm, deviceId, statusCode, thingCode);
                                    boolean resultMail = sendMail(alarm, deviceId, statusCode, thingCode);
                                    if (resultPhone || resultMail) {
                                        alarmService.updateSmsAlarmFlag(alarmId, true);
                                    }
                                    break;
                                }
                                case 2: {
                                    //邮件
                                    boolean resultMail = sendMail(alarm, deviceId, statusCode, thingCode);
                                    if (resultMail) {
                                        alarmService.updateSmsAlarmFlag(alarmId, true);
                                    }
                                    break;
                                }
                                case 3: {
                                    //短信
                                    boolean resultPhone = sendMessage2Phone(alarm, deviceId, statusCode, thingCode);
                                    if (resultPhone) {
                                        alarmService.updateSmsAlarmFlag(alarmId, true);
                                    }
                                    break;
                                }
                                default:
                                    break;
                            }
                        } else {
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error(LOG + "：发送通知失败，alarmId为：" + alarmId + "：异常信息为：" + e.getMessage());
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：发送通知失败，异常信息为：" + e.getMessage());
        }
    }

    /**
     * 发送短信通知
     *
     * @param alarm
     * @param deviceId
     * @param statusCode
     * @param thingCode
     */
    private boolean sendMessagePhone(Alarm alarm, String deviceId, String statusCode, String thingCode) {
        boolean sendSuccess = false;
        String alarmId = String.valueOf(alarm.getAlarmId());
        try {
            //获取手机号
            List<String> phoneNumberList = alarmService.getSmsPhone(deviceId, statusCode, thingCode);
            for (String phoneNumber : phoneNumberList) {
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    String sendInfo;
                    if (alarm.getStatus().getStatusCode().equals("NT")) {
                        sendInfo = "报警设备:[" + alarm.getDevice().getArea().getAreaName() + "]" + "[" + alarm.getDevice().getDeviceName() + "],"
                                + "报警类型:[" + alarm.getStatus().getStatusName() + "],"
                                + "报警信息:[" + alarm.getAlarmInfo() + "],"
                                + "报警物质:[" + alarm.getMonitor().getThingName() + "],"
                                + "超标数值:[" + alarm.getThingValue() + "],"
                                + "报警时间:[" + DateUtil.TimestampToString(alarm.getAlarmTime(), DateUtil.DATA_TIME) + "].";
                    } else {
                        sendInfo = "报警设备:[" + alarm.getDevice().getArea().getAreaName() + "]" + "[" + alarm.getDevice().getDeviceName() + "],"
                                + "报警类型:[" + alarm.getStatus().getStatusName() + "],"
                                + "报警信息:[" + alarm.getAlarmInfo() + "],"
                                + "报警时间:[" + DateUtil.TimestampToString(alarm.getAlarmTime(), DateUtil.DATA_TIME) + "].";
                    }
                    SMSConfig smsConfig = new SMSConfig();
                    smsConfig.setPhoneNumber(phoneNumber);//需要追加国家编码
                    smsConfig.setContent(sendInfo);
                    if (HuaWeiSendMessage.doIt(smsConfig)) {
                        sendSuccess = true;
                    } else {
                        logger.warn(LOG + "：发送短信失败，alarmId为：" + alarmId + "：请求短信发送失败");
                    }

                } else {
                    logger.warn(LOG + "：发送短信失败，alarmId为：" + alarmId + "：原因为：没有查询到接收人电话或无此监测物报警");
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：发送短信失败，alarmId为：" + alarmId + "：异常信息为：" + e.getMessage());
        }
        return sendSuccess;
    }

    /**
     * 发送短信通知（2019年迁移）
     *
     * @param alarm
     * @param deviceId
     * @param statusCode
     * @param thingCode
     */
    private boolean sendMessage2Phone(Alarm alarm, String deviceId, String statusCode, String thingCode) {
        boolean sendSuccess = false;
        String alarmId = String.valueOf(alarm.getAlarmId());
        try {
            //获取手机号
            List<String> phoneNumberList = alarmService.getSmsPhone(deviceId, statusCode, thingCode);
            String phoneNumbers = "";
            for (String phoneNumber : phoneNumberList) {
                if (!StringUtils.isEmpty(phoneNumber)) {
                    phoneNumbers += ("+86" + phoneNumber) + ",";//需要追加国家编码
                }
            }
            if (!StringUtils.isEmpty(phoneNumbers) && phoneNumbers.indexOf(",") >= 0) {
                phoneNumbers = phoneNumbers.substring(0, phoneNumbers.length() - 1);
            }
            if (!StringUtils.isEmpty(phoneNumbers)) {
                String areaName = alarm.getDevice().getArea().getAreaName();
                String deviceName = alarm.getDevice().getDeviceName();
                String typeName = alarm.getStatus().getStatusName();
                String alarmInfo = alarm.getAlarmInfo();
                String levelNo = alarm.getLevelNo();
                if (!StringUtils.isEmpty(levelNo)) {
                    if (levelNo.equals("1")) {
                        alarmInfo = "普通报警";
                    } else if (levelNo.equals("2")) {
                        alarmInfo = "紧急报警";
                    } else if (levelNo.equals("3")) {
                        alarmInfo = "严重报警";
                    }
                }
                String thingName = alarm.getMonitor().getThingName();
                String thingValue = String.valueOf(alarm.getThingValue());
                String dateString = DateUtil.TimestampToString(alarm.getAlarmTime(), "yyyy/MM/dd");
                String timeString = DateUtil.TimestampToString(alarm.getAlarmTime(), " HH:mm:ss");
                String sendInfo;
                if (alarm.getStatus().getStatusCode().equals("NT")) {
                    sendInfo = thingName + "[" + thingValue + "]";
                } else {
                    sendInfo = "无";
                }
                SMSConfig smsConfig = new SMSConfig();
                smsConfig.setPhoneNumber(phoneNumbers);
                smsConfig.setContent(sendInfo);
                smsConfig.setAreaName(areaName);
                smsConfig.setDeviceName(deviceName);
                smsConfig.setTypeName(typeName);
                smsConfig.setAlarmInfo(alarmInfo);
                smsConfig.setThingName(thingName);
                smsConfig.setThingValue(thingValue);
                smsConfig.setDateString(dateString);
                smsConfig.setTimeString(timeString);
                if (HuaWeiSendMessage2.sengMessage(smsConfig)) {
                    sendSuccess = true;
                } else {
                    logger.warn(LOG + "：发送短信报警，alarmId为：" + alarmId + "：请求短信发送失败");
                }

            } else {
                logger.info(LOG + "：发送短信通知，alarmId为：" + alarmId + "：原因为：没有查询到接收人电话或无此监测物报警");
            }
        } catch (Exception e) {
            logger.error(LOG + "：发送短信失败，alarmId为：" + alarmId + "：异常信息为：" + e.getMessage());
        }
        return sendSuccess;
    }

    /**
     * 发送邮件通知
     *
     * @param alarm
     * @param deviceId
     * @param statusCode
     * @param thingCode
     */
    private boolean sendMail(Alarm alarm, String deviceId, String statusCode, String thingCode) {
        boolean sendSuccess = false;
        String alarmId = String.valueOf(alarm.getAlarmId());
        try {
            //获取邮件地址
            List<String> mailAddressList = alarmService.getSmsMail(deviceId, statusCode, thingCode);
            SMSConfig smsConfig = new SMSConfig();
            String sendInfo;
            String levelNo = alarm.getLevelNo();
            String levelInfo = "";
            if (alarm.getStatus().getStatusCode().equals("NT")) {
                if (!StringUtils.isEmpty(levelNo)) {
                    if (levelNo.equals("1")) {
                        levelInfo = "(普通报警)";
                    } else if (levelNo.equals("2")) {
                        levelInfo = "(紧急报警)";
                    } else if (levelNo.equals("3")) {
                        levelInfo = "(严重报警)";
                    }
                }
                sendInfo = "尊敬的客户，您好，存在一条报警信息，内容如下：报警设备:[" + alarm.getDevice().getArea().getAreaName() + "]" + "[" + alarm.getDevice().getDeviceName() + "]，"
                        + "报警类型:[" + alarm.getStatus().getStatusName() + "]，"
                        + "报警信息:[" + alarm.getAlarmInfo() + levelInfo + "]，"
                        + "报警物质:[" + alarm.getMonitor().getThingName() + "]，"
                        + "超标数值:[" + alarm.getThingValue() + "]，"
                        + "报警时间:[" + DateUtil.TimestampToString(alarm.getAlarmTime(), DateUtil.DATA_TIME) + "]。";
            } else {
                sendInfo = "尊敬的客户，您好：存在一条报警信息，内容如下：报警设备:[" + alarm.getDevice().getArea().getAreaName() + "]" + "[" + alarm.getDevice().getDeviceName() + "]，"
                        + "报警类型:[" + alarm.getStatus().getStatusName() + "]，"
                        + "报警信息:[" + alarm.getAlarmInfo() + "]，"
                        + "报警时间:[" + DateUtil.TimestampToString(alarm.getAlarmTime(), DateUtil.DATA_TIME) + "]。";
            }
            smsConfig.setContent(sendInfo);
            List<String> addressList = new ArrayList<>();
            for (String mailAddress : mailAddressList) {
                if (!StringUtils.isEmpty(mailAddress)) {
                    addressList.add(mailAddress);
                }
            }
            smsConfig.setMailAddress(addressList);
            smsConfig.setTitle("监测平台报警通知");
            if (smsConfig.getMailAddress() != null && smsConfig.getMailAddress().size() > 0) {
                SendMailUtil.sendEmail(smsConfig, dom4jConfig.getDeDevConfig().getMailPath());
                sendSuccess = true;
            } else {
                logger.warn(LOG + "：发送邮件失败，alarmId为：" + alarmId + "：无收件人地址或无此监测物报警");
            }
        } catch (Exception e) {
            logger.error(LOG + "：发送邮件失败，alarmId为：" + alarmId + "：异常信息为：" + e.getMessage());
        }
        return sendSuccess;
    }

}
