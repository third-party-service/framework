package com.jzg.framework.notify;

//~--- non-JDK imports --------------------------------------------------------

import com.jzg.framework.utils.ConfigUtils;
import com.jzg.framework.utils.string.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @description: 邮件工具类
 * <p>
 * 配置文件：
 * mail.from  发送邮箱
 * mail.from.password  发送邮箱密码
 * mail.html.encode HTML编码
 * mail.smtp.debug  是否调试
 * mail.content.html  是否发送HTML
 * </p>
 * @author: JZG
 * @date: 2016/12/30 10:40
 */
public final class EmailUtils {

    /**
     * 默认发送邮箱
     */
    private static final String DEFAULT_FROM_EMAIL = "monitor@jingzhengu.com";

    /**
     * 默认发送邮箱密码
     */
    private static final String DEFAULT_FROM_EMAIL_PWD = "Monitor5700";

    /**
     * 默认邮件编码，HTML内容使用
     */
    private static final String DEFAULT_CONTENT_ENCODING = "text/html;charset=utf-8";

    /**
     * 发送邮箱
     */
    private static String fromEmail;

    /**
     * 发送邮箱密码
     */
    private static String fromPassword;

    /**
     * html编码
     */
    private static String htmlEncode;

    /**
     * 是否打印调试信息
     */
    private static boolean isDebug;

    /**
     * 是否是Html
     */
    private static boolean isHtml;

    private EmailUtils() {
    }

    static {
        //发送邮箱
        fromEmail = ConfigUtils.getProperty("mail.from");
        //发送邮箱密码
        fromPassword = ConfigUtils.getProperty("mail.from.password");
        //html编码
        htmlEncode = ConfigUtils.getProperty("mail.html.encode");

        String strIsDebug = ConfigUtils.getProperty("mail.smtp.debug");
        String strIsHtml = ConfigUtils.getProperty("mail.content.html");

        if (StringUtils.isEmpty(fromEmail)) {
            fromEmail = DEFAULT_FROM_EMAIL;
        }

        if (StringUtils.isEmpty(fromPassword)) {
            fromPassword = DEFAULT_FROM_EMAIL_PWD;
        }

        if (StringUtils.isEmpty(htmlEncode)) {
            htmlEncode = DEFAULT_CONTENT_ENCODING;
        }

        if (!StringUtils.isEmpty(strIsDebug)) {
            isDebug = Boolean.parseBoolean(strIsDebug);
        }

        if (!StringUtils.isEmpty(strIsHtml)) {
            isHtml = Boolean.parseBoolean(strIsHtml);
        }
    }

    /**
     * 发送邮件
     *
     * @param emailInfo 邮件信息
     * @throws Exception Exception
     */
    public static void sendMail(EmailInfo emailInfo) throws Exception {
        final Properties props = new Properties();

        // 简单邮件传输协议
        props.put("mail.transport.protocol", "smtp");
        // 存储发送邮件服务器的信息
        props.put("mail.smtp.host", "mail.jingzhengu.com");
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        // 发送者邮箱
        props.put("mail.user", emailInfo.getFromEmail());
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", emailInfo.getPassword());

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");

                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(props, authenticator);

        if (isDebug) {
            // 打印一些调试信息。
            session.setDebug(true);
        }

        // 由邮件会话新建一个消息对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人的地址
        message.setFrom(new InternetAddress(emailInfo.getFromEmail()));
        // 设置收件人,并设置其接收类型为TO
        message.setRecipient(Message.RecipientType.TO, new InternetAddress());
        // 设置标题
        message.setSubject(emailInfo.getSubject());
        if (emailInfo.getHtml()) {
            // 发送HTML邮件，内容样式比较丰富
            message.setContent(emailInfo.getContent(), DEFAULT_CONTENT_ENCODING);
        } else {
            // 发送 纯文本 邮件
            message.setText(emailInfo.getContent());
        }
        // 设置发信时间
        message.setSentDate(new Date());
        // 存储邮件信息
        message.saveChanges();
        // 发送邮件
        Transport transport = session.getTransport();
        transport.connect();

        // 发送邮件,其中第二个参数是所有已设好的收件人地址
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * 发送邮件
     *
     * @param from     发送者邮箱
     * @param password 发送者邮箱的密码
     * @param to       接收者邮箱，允许多个，逗号分隔
     * @param subject  邮件主题（标题）
     * @param content  邮件内容
     * @param ishtml   邮件内容是否为html
     * @throws Exception Exception
     */
    public static void sendMail(String from, String password, String to, String subject, String content, boolean ishtml)
            throws Exception {
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setFromEmail(from);
        emailInfo.setPassword(password);
        emailInfo.setToEmail(to);
        emailInfo.setSubject(subject);
        emailInfo.setContent(content);
        emailInfo.setHtml(ishtml);

        sendMail(emailInfo);
    }

    /**
     * 发送电子邮件
     *
     * @param to      接收者邮箱，允许多个，逗号分隔
     * @param subject 邮件主题（标题）
     * @param content 邮件内容
     * @param ishtml  邮件内容是否为html
     * @throws Exception Exception
     */
    public static void sendMail(String to, String subject, String content, boolean ishtml) throws Exception {
        sendMail(DEFAULT_FROM_EMAIL, DEFAULT_FROM_EMAIL_PWD, to, subject, content, ishtml);
    }

    /**
     * 发送电子邮件
     *
     * @param to      接收者邮箱，允许多个，逗号分隔
     * @param subject 邮件主题（标题）
     * @param content 邮件内容
     * @throws Exception Exception
     */
    public static void sendMail(String to, String subject, String content) throws Exception {
        sendMail(to, subject, content, false);
    }

    /**
     * 异步发送邮件
     *
     * @param to      接收者邮箱，允许多个，逗号分隔
     * @param subject 邮件主题（标题）
     * @param content 邮件内容
     * @throws Exception Exception
     */
    public static void asynSendEmail(String to, String subject, String content) {
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setContent(content);
        emailInfo.setToEmail(to);
        emailInfo.setSubject(subject);

        AsyncEmail.sendMail(emailInfo);
    }


    /**
     * 异步发送邮件
     *
     * @param from     发送者邮箱
     * @param password 发送者邮箱的密码
     * @param to       接收者邮箱，允许多个，逗号分隔
     * @param subject  邮件主题（标题）
     * @param content  邮件内容
     * @param ishtml   邮件内容是否为html
     * @throws Exception Exception
     */
    public static void asynSendMail(String from, String password, String to, String subject, String content, boolean ishtml)
            throws Exception {
        EmailInfo emailInfo = new EmailInfo();
        emailInfo.setFromEmail(from);
        emailInfo.setPassword(password);
        emailInfo.setToEmail(to);
        emailInfo.setSubject(subject);
        emailInfo.setContent(content);
        emailInfo.setHtml(ishtml);

        AsyncEmail.sendMail(emailInfo);
    }

    /**
     * 异步发送邮件
     *
     * @param to      接收者邮箱，允许多个，逗号分隔
     * @param subject 邮件主题（标题）
     * @param content 邮件内容
     * @param ishtml  邮件内容是否为html
     * @throws Exception Exception
     */
    public static void asynSendMail(String to, String subject, String content, boolean ishtml) throws Exception {
        asynSendMail(DEFAULT_FROM_EMAIL, DEFAULT_FROM_EMAIL_PWD, to, subject, content, ishtml);
    }

    /**
     * 异步发送邮件
     *
     * @param to      接收者邮箱，允许多个，逗号分隔
     * @param subject 邮件主题（标题）
     * @param content 邮件内容
     * @throws Exception Exception
     */
    public static void asynSendMail(String to, String subject, String content) throws Exception {
        asynSendMail(to, subject, content, false);
    }
}
