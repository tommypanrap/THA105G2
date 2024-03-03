package com.fitanywhere.user.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fitanywhere.service.PasswordEncryptionService;
import com.fitanywhere.service.MailService;

@Service
public class UserService {

// =============================================    
//    寄送信箱驗證信功能
	@Autowired
	private MailService mailService;

	public void sendVerificationMail(String registerEmail, String uNickname, String verificationCode) {
		try {
			String subject = "FitAnyWhere帳號註冊驗證信";
			LocalDateTime validUntil = LocalDateTime.now().plusMinutes(10);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			String content = String.format("<html>" + "<body>"
					+ "<p>親愛的 <strong style=\\\"color: blue\\\">%s</strong> 先生/小姐您好:<br></p>"
					+ "<p>感謝您註冊本站\"FitAnyWhere\"的一般會員資格。<br></p>"
					+ "<p>請在驗證碼輸入欄輸入以下六位數字：<br><strong style=\"color: red; font-size: 20px;\">%s</strong><br></p>"
					+ "<p>請在下列時間限制前完成驗證：<br><strong style=\"color: red; font-size: 16px;\">%s</strong><br>(驗證碼有效期限)</p>"
					+ "<p>若驗證信已過期請使用重新取得驗證信功能重新取得有效的驗證碼。<br></p>" + "<p>本信為系統自動發信，請勿直接回覆本信。<br></p>"
					+ "<p>若有任何問題歡迎隨時另外來信至<br> <a href='mailto:FitAnyWhere2024@gmail.com'>FitAnyWhere2024@gmail.com</a><br>"
					+ "我們將竭誠為您服務!</p>" + "</body>" + "</html>", uNickname, verificationCode,
					validUntil.format(formatter));

			mailService.sendEmail(registerEmail, subject, content);
		} catch (MessagingException e) {
			System.out.println("驗證碼寄信過程異常!");
			e.printStackTrace();
		}
	}

// =============================================	
	@Autowired
	private StringRedisTemplate redisTemplate;

	public String getVerificationCode(String email) {
		// 生成隨機驗證碼的方法，這裡使用SecureRandom來生成一個六位數字的驗證碼
		String verificationCode = generateRandomCode();

		// 將驗證碼與電子郵件地址關聯後存入Redis，並設置存活時間為12分鐘
		// 使用"MailVerificationCode:"作為key的一部分來確保唯一性
		String key = "MailVerificationCode:" + email;
		redisTemplate.opsForValue().set(key, verificationCode, 12, TimeUnit.MINUTES);

		return verificationCode;
	}

	private String generateRandomCode() {
		// 使用java.security.SecureRandom來生成一個範圍在000000到999999之間的隨機數
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(1000000);
		// 使用String.format來保證格式為六位數，不足位數以0填充
		return String.format("%06d", num);
	}

// =============================================    

	@Autowired
	private UserJpaRepository userJpaRepository;

	// 檢查信箱是否註冊
	public boolean isEmailRegistered(String uMail) {
		UserVO user = userJpaRepository.findByuMail(uMail);
		return user != null;
	}

	// 檢查信箱是否註冊
	public boolean isNicknameRegistered(String uNickname) {
		UserVO user = userJpaRepository.findByuNickname(uNickname);
		return user != null;
	}

	// 檢查信箱是否註冊
	public boolean isPhoneRegistered(String uPhone) {
		UserVO user = userJpaRepository.findByuPhone(uPhone);
		return user != null;
	}

	// 單向將密碼加密

	public String encryptNewPassword(String uPassword) {
		String encryptedPassword = PasswordEncryptionService.encryptPassword(uPassword);
		return encryptedPassword;
	}

	// 核對登入密碼是否正確
	public UserVO userLogin(String uMail, String inputPassword) {
		UserVO user = userJpaRepository.findByuMail(uMail);
		if (user != null && PasswordEncryptionService.checkPassword(inputPassword, user.getuPassword())) {
			return user; // 登入成功 返回會員資料
		}
		return null; // 登錄失敗
	}

	// 返回uID對應的整筆MySQL資料 (不建議使用 若大家都不用 未來考慮移除)
	public UserVO getUserDataByID(Integer uId) {

		UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
	}	

	
	// 讀取User照片
	public UserHeadshotOnlyDTO getUserDTOWithHeadshotById(Integer uId) {
		return userJpaRepository.findUserHeadshotById(uId);
	}
	
	public UserReadDataDTO getUserDataExcludeUHeadshot(Integer uId) {
		return userJpaRepository.findUserDataById(uId);
	}

	// =============================================

	@Autowired
	public UserService(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	// 負責接收uId和照片封裝DTO寫入DB並返回Boolean
	public boolean updateUserHeadshot(UserHeadshotOnlyDTO headshotDTO) {
		Optional<UserVO> userOptional = userJpaRepository.findById(headshotDTO.getuId());
		if (userOptional.isPresent()) {
			UserVO user = userOptional.get();
			user.setuHeadshot(headshotDTO.getuHeadshot());
			userJpaRepository.save(user);
			return true; // 更新成功
			// 可另外在controller呼叫getUserDTOWithHeadshotById(Integer uId)取得資料庫的更新後照片
		} else {
			return false; // 更新失敗
		}
	}
	
	// 負責接收uId和DATA封裝DTO寫入DB並返回Boolean
	public boolean updateUserData(UserWriteDataDTO userDTO) {
        Optional<UserVO> userOptional = userJpaRepository.findById(userDTO.getuId());
        if (userOptional.isPresent()) {
            UserVO user = userOptional.get();
            
            // 使用Java 8的Optional來避免null檢查，並允許部分更新
            Optional.ofNullable(userDTO.getuName()).ifPresent(user::setuName);
            Optional.ofNullable(userDTO.getuPhone()).ifPresent(user::setuPhone);
            Optional.ofNullable(userDTO.getuGender()).ifPresent(user::setuGender);
            Optional.ofNullable(userDTO.getuBirth()).ifPresent(user::setuBirth);
            
            userJpaRepository.save(user);
            return true; // 更新成功
        } else {
            return false; // 更新失敗
        }
    }
	// =============================================

//	Andy
	public UserVO getUser(Integer uId) {

		UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
	}
	
	
}
