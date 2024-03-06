package com.fitanywhere.user.model;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.service.MailService;
import com.fitanywhere.service.PasswordEncryptionService;

@Service
public class UserService {


// =============================================    
//    註冊-寄送信箱驗證信功能
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
	// 註冊-生成驗證碼並寫入Redis
	@Autowired
	private StringRedisTemplate redisTemplate;

	public String getVerificationCode(String email) {
		// 使用下方的方法生成驗證碼
		String verificationCode = generateRandomCode();

		// 將驗證碼與電子郵件地址關聯後存入Redis，並設置存活時間為12分鐘
		// 使用"MailVerificationCode:"作為key的一部分來確保唯一性
		String key = "MailVerificationCode:" + email;
		redisTemplate.opsForValue().set(key, verificationCode, 12, TimeUnit.MINUTES);

		return verificationCode;
	}

	// 使用SecureRandom來生成一個六位數字的驗證碼
	private String generateRandomCode() {
		// 用SecureRandom生成一個範圍在000000到999999之間的隨機數
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(1000000);
		// 使用String.format來保證六位數，不足位數以0填充
		return String.format("%06d", num);
	}

// =============================================    
	// 註冊-基本資料重複性檢查和註冊資料處理
	@Autowired
	private UserJpaRepository userJpaRepository;

	// 檢查信箱是否註冊
	@Transactional(readOnly = true)
	public boolean isEmailRegistered(String uMail) {
		return userJpaRepository.existsByuMail(uMail);
	}

	// 檢查暱稱是否註冊
	@Transactional(readOnly = true)
	public boolean isNicknameRegistered(String uNickname) {
		return userJpaRepository.existsByuNickname(uNickname);
	}

	// 檢查手機是否註冊
	@Transactional(readOnly = true)
	public boolean isPhoneRegistered(String uPhone) {
		return userJpaRepository.existsByuPhone(uPhone);
	}

	// 單向將密碼加密
	@Transactional
	public String encryptNewPassword(String uPassword) {
		String encryptedPassword = PasswordEncryptionService.encryptPassword(uPassword);
		return encryptedPassword;
	}

// =============================================
	// 登入-核對登入密碼是否正確
	@Transactional(readOnly = true)
	public UserReadDataDTO userLogin(String uMail, String inputPassword) {
		String savedPassword = userJpaRepository.findOnlyPasswordByuMail(uMail);
		Integer savedId = userJpaRepository.findOnlyIdByuMail(uMail);
		if (PasswordEncryptionService.checkPassword(inputPassword, savedPassword)) {
			return userJpaRepository.findUserDataDTOById(savedId);
		}
		System.out.println("Service <userLogin>  處理登入失敗!");
		return null; // 登錄失敗
	}
// =============================================
	// 可供調用的共用Service
// =============================================
// 讀取類Service	

	// 讀取User照片
	@Transactional(readOnly = true)
	public UserHeadshotOnlyDTO getUserHeadshotDTOById(Integer uId) {
		return userJpaRepository.findUserHeadshotDTOById(uId);
	}

	// 讀取User除照片以外的所有非敏感資訊
	@Transactional(readOnly = true)
	public UserReadDataDTO getUserDataDTOByID(Integer uId) {
		return userJpaRepository.findUserDataDTOById(uId);
	}

// =============================================
// 寫入類Service

	@Autowired
	public UserService(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	// 負責接收uId和照片封裝DTO寫入DB並返回Boolean
	@Transactional
	public boolean updateUserHeadshot(UserHeadshotOnlyDTO headshotDTO) {
		Optional<UserVO> userOptional = userJpaRepository.findById(headshotDTO.getuId());
		if (userOptional.isPresent()) {
			UserVO user = userOptional.get();
			user.setuHeadshot(headshotDTO.getuHeadshot());
			userJpaRepository.save(user);
			return true; // 更新成功
			// 可另外在controller呼叫getUserDTOWithHeadshotById(Integer uId)取得資料庫的更新後照片
		} else {
			System.out.println("Service <updateUserHeadshot> 處理uHeadshot更新失敗!");
			return false; // 更新失敗
		}
	}

	// 負責接收uId和DATA封裝DTO寫入DB並返回Boolean
	@Transactional
	public boolean updateUserData(UserWriteDataDTO userDTO) {
		Optional<UserVO> userOptional = userJpaRepository.findById(userDTO.getuId());
		if (userOptional.isPresent()) {
			UserVO user = userOptional.get();

			// 使用Optional來避免null檢查，並允許部分更新
			Optional.ofNullable(userDTO.getuName()).ifPresent(user::setuName);
			Optional.ofNullable(userDTO.getuPhone()).ifPresent(user::setuPhone);
			Optional.ofNullable(userDTO.getuGender()).ifPresent(user::setuGender);
			Optional.ofNullable(userDTO.getuBirth()).ifPresent(user::setuBirth);

			userJpaRepository.save(user);
			return true; // 更新成功
		} else {
			System.out.println("Service <updateUserData> 處理UserDataDTO更新失敗!");
			return false; // 更新失敗
		}
	}

// =============================================
// 舊產物區 未來可能移除

	// 返回uID對應的整筆MySQL資料 (不建議使用 若大家都不用 未來考慮移除)
	@Transactional(readOnly = true)
	public UserVO getUserDataByID(Integer uId) {

		UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
	}

	// Andy
	@Transactional(readOnly = true)
	public UserVO getUser(Integer uId) {

		UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
	}

    
    //andy 單取出user大頭照
    public byte[] getUserHeadshot(Integer uId) {
    	byte[] uHeadshot = userJpaRepository.getUserHeadshotByUserId(uId);
    	return uHeadshot;
    }

 // Tommy
 	public List<UserVO> getAll() {
 		return userJpaRepository.findAll();
 	}
 	
 	//ROY
	public Set<ForumPostVO> getForumPostByuId(Integer uId){
	return getUser(uId).getForumPost();
	}

}