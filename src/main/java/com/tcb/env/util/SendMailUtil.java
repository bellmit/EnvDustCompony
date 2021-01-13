package com.tcb.env.util;

import com.sun.mail.util.MailSSLSocketFactory;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.config.SMSConfig;
import org.springframework.util.StringUtils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @Author: WangLei
 * @Description: 发送邮件工具类
 * @Date: Create in 2019/4/11 10:06
 * @Modify by WangLei
 */
public class SendMailUtil {

    //邮件服务器主机名
    private static String myEmailSMTPHost = "smtp.712.cn";//smtp.qq.com

    //发件人邮箱
    private static String myEmailAccount = "wanglei@712.cn";//475501585@qq.com

    //发件人邮箱密码（授权码）
    //在开启SMTP服务时会获取到一个授权码，把授权码填在这里
    private static String myEmailPassword = "issac19840417";//dvvxmzsfvytwbjgf

    /**
     * 邮件单发（自由编辑短信，并发送，适用于私信）
     *
     * @param smsConfig
     * @param mailPath
     * @throws Exception
     */
    public static void sendEmail(SMSConfig smsConfig, String mailPath) throws Exception {
        Properties props = new Properties();
        //开启debug调试
        props.setProperty("mail.debug", "true");
        //发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        //端口号
        props.put("mail.smtp.port", 25);//25,465
        //设置邮件服务器主机名
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        //发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        /**SSL认证，注意腾讯邮箱是基于SSL加密的，所以需要开启才可以使用**/
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        //设置是否使用ssl安全连接（一般都使用）
        props.put("mail.smtp.ssl.enable", "false");//true
        props.put("mail.smtp.ssl.socketFactory", sf);
        //创建会话
        Session session = Session.getInstance(props);
        //获取邮件对象
        //发送的消息，基于观察者模式进行设计的
        Message msg = new MimeMessage(session);
        //设置邮件标题
        msg.setSubject(smsConfig.getTitle());
        //设置邮件内容
        //使用StringBuilder，因为StringBuilder加载速度会比String快，而且线程安全性也不错
        StringBuilder builder = new StringBuilder();
        //写入内容
        builder.append(smsConfig.getContent() + "\n");
        //写入我的官网
        builder.append("\n监控网址：" + "http://114.115.217.241");
        //定义要输出日期字符串的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //在内容后加入邮件发送的时间
        builder.append("\n发送时间：" + sdf.format(new Date()));
        //设置显示的发件时间
        msg.setSentDate(new Date());
        //设置邮件内容
        msg.setText(builder.toString());
        //设置发件人邮箱
        // InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        msg.setFrom(new InternetAddress(myEmailAccount, "监控平台", "UTF-8"));
        //得到邮差对象
        Transport transport = session.getTransport();
        //连接自己的邮箱账户
        //密码不是自己QQ邮箱的密码，而是在开启SMTP服务时所获取到的授权码
        //connect(host, user, password)
        transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);
        //发送邮件
        List<String> addressList = smsConfig.getMailAddress();
        for (String toEmailAddress : addressList) {
            if (!StringUtils.isEmpty(toEmailAddress)) {
                transport.sendMessage(msg, new Address[]{new InternetAddress(toEmailAddress)});
            }
        }
        //将该邮件保存到本地
        File file = new File(mailPath + DateUtil.GetSystemDateTime() + ".eml");
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        OutputStream out = new FileOutputStream(file);
        msg.writeTo(out);
        out.flush();
        out.close();
        transport.close();
    }

}
