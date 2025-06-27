package org.tinycloud.tinyurl.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件工具类，只支持smtp协议
 *
 * @author liuxingyu01
 * @version 2023-04-07 12:38
 **/
public class EmailUtils {
    private static final Logger log = LoggerFactory.getLogger(EmailUtils.class);

    /**
     * 发送邮件（不带附件）
     *
     * @param account            发件邮箱账号
     * @param password           密码
     * @param host               服务器地址
     * @param port               服务器端口
     * @param receiveMailAccount 收件人邮箱
     * @param title              主题
     * @param content            内容
     * @return true成功，false失败
     */
    public static boolean sendMsg(String account, String password, String host, String port, boolean isSSL,
                                  String[] receiveMailAccount, String title, String content) {
        return sendMsg(account, password, host, port, isSSL, receiveMailAccount, title, content, null);
    }


    /**
     * 发送邮件（带附件）
     *
     * @param account            发件邮箱账号
     * @param password           密码
     * @param host               服务器地址
     * @param port               服务器端口
     * @param receiveMailAccount 收件人邮箱
     * @param title              邮件主题
     * @param content            邮件内容
     * @param fileList           邮件附件列表
     * @return true成功，false失败
     */
    public static boolean sendMsg(String account, String password, String host, String port, boolean isSSL,
                                  String[] receiveMailAccount, String title, String content, List<File> fileList) {
        try {
            // 1. 创建参数配置, 用于连接邮件服务器的参数配置
            Properties props = new Properties();                    // 参数配置
            props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", host);   // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.connectiontimeout", "15000");
            props.setProperty("mail.smtp.timeout", "15000");
            props.setProperty("mail.smtp.writetimeout", "15000");
            if (isSSL) {
                MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
                sslSocketFactory.setTrustAllHosts(true);
                props.setProperty("mail.smtp.ssl.enable", "true"); // 是否开启ssl，一般开启ssl后使用465端口，否则使用25端口
                props.put("mail.smtp.ssl.socketFactory", sslSocketFactory);
            }

            // 2. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props);
            // 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);
            // 3. 创建一封邮件
            MimeMessage message = createMimeMessage(session, account, receiveMailAccount, title, content, fileList);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 如果连接服务器失败, 都会在控制台输出相应失败原因的log。
            //    仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接,
            //    根据给出的错误类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            transport.connect(account, password);
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();

            return true;
        } catch (Exception e) {
            log.error("sendMsg is error: ", e);
            return false;
        }
    }


    /**
     * 创建一封只包含文本和附件的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @param title       邮件主题
     * @param content     邮件内容
     * @param fileList    邮件附件列表
     * @return MimeMessage对象
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String[] receiveMail,
                                                String title, String content, List<File> fileList) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        //message.setFrom(new InternetAddress(sendMail, "昵称", "UTF-8"));
        message.setFrom(new InternetAddress(sendMail, null, "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        InternetAddress[] adds = new InternetAddress[receiveMail.length];
        for (int x = 0; x < receiveMail.length; x++) {
            adds[x] = new InternetAddress(receiveMail[x]);
        }
        message.setRecipients(MimeMessage.RecipientType.TO, adds);
        // 4. Subject: 邮件主题
        message.setSubject(title, "UTF-8");
        // 添加附件功能 新增A 开始{
        // A.1 设置邮件内容，混合模式
        MimeMultipart msgMultipart = new MimeMultipart("mixed");
        message.setContent(msgMultipart);
        // A.2 设置消息正文
        MimeBodyPart contentPart = new MimeBodyPart();
        msgMultipart.addBodyPart(contentPart);
        // A.3 设置正文格式
        MimeMultipart bodyMultipart = new MimeMultipart("related");
        contentPart.setContent(bodyMultipart);
        // A.4 设置正文内容
        MimeBodyPart htmlPart = new MimeBodyPart();
        bodyMultipart.addBodyPart(htmlPart);
        htmlPart.setContent(content, "text/html;charset=UTF-8");
        // A.5设置附件
        if (fileList != null && fileList.size() > 0) {
            fileList.forEach(file -> {
                if (file == null || !file.exists()) {
                    return;
                }
                try {
                    // 设置相关文件
                    MimeBodyPart filePart = new MimeBodyPart();
                    FileDataSource dataSource = new FileDataSource(file);
                    DataHandler dataHandler = new DataHandler(dataSource);
                    // 文件处理
                    filePart.setDataHandler(dataHandler);
                    // 附件名称
                    filePart.setFileName(MimeUtility.encodeWord(file.getName()));
                    // 放入正文（有先后顺序，所以在正文后面）
                    msgMultipart.addBodyPart(filePart);
                } catch (Exception e) {
                    log.error("send mail error fileName={}", file.getName(), e);
                }
            });
        }
        //新增A 结束} 注释掉第 5 步

        // 5. Content: 邮件正文（可以使用html标签）
        //message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
}