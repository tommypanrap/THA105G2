package com.fitanywhere.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

// Eugen
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom("fitanywhere2024@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        
        // 第二參數為ture表示使用HTML格式
        helper.setText(content, true);
        
        mailSender.send(message);
    }
}
