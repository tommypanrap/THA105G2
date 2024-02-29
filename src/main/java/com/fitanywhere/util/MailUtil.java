package com.fitanywhere.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailUtil {

    private String host = "smtp.gmail.com";
    private String port = "587";
    private String userName = "fitanywhere2024@gmail.com";
    private String password = "請Trello取得(USER)"; // google信箱金鑰 不上github

    // 使用預設設定的構造方法
    public MailUtil() {
        super();
    }

    // 允許自定義設定的構造方法
    public MailUtil(String host, String port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public void sendEmail(String toAddress, String subject, String htmlMessage) throws MessagingException {
        // 郵件服務器的屬性設置
        Properties properties = new Properties();
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", this.port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // 創建Session物件
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MailUtil.this.userName, MailUtil.this.password);
            }
        });

        // 創建Message物件
        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(this.userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());

        // 設置郵件內容為 HTML
        msg.setContent(htmlMessage, "text/html; charset=utf-8");

        // 發送郵件
        Transport.send(msg);
    }
}


