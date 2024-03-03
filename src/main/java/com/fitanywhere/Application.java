package com.fitanywhere;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fitanywhere.service.MailService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
//	以Java Application啟動時會寄送一封信到指定信箱(測試信件功能正常用)	
//	 @Bean
//	    CommandLineRunner testMailService(MailService mailService) {
//	        return args -> {
//	        	String uNickname = "XXX";
//	        	String verificationCode = "123456";
//	        	
//	        	String subject = "FitAnyWhere帳號註冊驗證信";
//	            LocalDateTime validUntil = LocalDateTime.now().plusMinutes(10);
//	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//	            String content = String.format(
//	            	    "<html>" +
//	            	    "<body>" +
//	            	    "<p>親愛的%s先生/小姐您好:<br></p>" +
//	            	    "<p>感謝您註冊本站\"FitAnyWhere\"的一般會員資格。<br></p>" +
//	            	    "<p>請在驗證碼輸入欄輸入以下六位數字：<br><strong style='color: red; font-size: 20px;'>%s</strong><br></p>" +
//	            	    "<p>請在下列時間限制前完成驗證：<br><strong style='color: red; font-size: 16px;'>%s</strong><br>(驗證碼有效期限)</p>" +
//	            	    "<p>若驗證信已過期請使用重新取得驗證信功能重新取得有效的驗證碼。<br></p>" +
//	            	    "<p>若有任何問題歡迎隨時來信至<br> <a href='mailto:FitAnyWhere2024@gmail.com'>FitAnyWhere2024@gmail.com</a><br>" +
//	            	    "我們將竭誠為您服務!</p>" +
//	            	    "</body>" +
//	            	    "</html>",
//	            	    uNickname, verificationCode, validUntil.format(formatter));
//	        	
//	            mailService.sendEmail("收件人@gmail.com",subject, content);
//	        };
//	    }

}
