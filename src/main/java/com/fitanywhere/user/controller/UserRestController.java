package com.fitanywhere.user.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fitanywhere.order.model.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.coach.model.CoachService;
import com.fitanywhere.user.model.UserHeadshotOnlyDTO;
import com.fitanywhere.user.model.UserReadDataDTO;
import com.fitanywhere.user.model.UserRegisterDataDTO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserWriteDataDTO;

@RestController
//在路徑上用"/user_api"做為區別
@RequestMapping("/user_api")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private CoachService coachService;

	// Joy
	@Autowired
	private CartService cartService;

// ===================================================================
	// 註冊-檢查信箱是否重複註冊
	@PostMapping("/check_register_duplicate")
	public String checkRegisterDuplicate(HttpServletRequest request) {
		boolean isRegisterDuplicate = false;
		String testItem = request.getParameter("fieldId");
		// 根據不同數據資料呼叫對應的方法判斷
		switch (testItem) {
		case "u_mail":
			isRegisterDuplicate = userService.isEmailRegistered(request.getParameter("fieldValue"));
			break;
		case "u_nickname":
			isRegisterDuplicate = userService.isNicknameRegistered(request.getParameter("fieldValue"));
			break;
		case "u_phone":
			isRegisterDuplicate = userService.isPhoneRegistered(request.getParameter("fieldValue"));
			break;
		default:
			break;
		}
		return isRegisterDuplicate ? "true" : "false";
	}

	// 註冊-接收註冊資料表單並暫存到Session
	@PostMapping("/hold_register_form")
	public String holdRegisterForm(@RequestParam("u_name") String uName, @RequestParam("u_gender") String uGender,
			@RequestParam("u_nickname") String uNickname, @RequestParam("u_phone") String uPhone,
			@RequestParam("u_mail") String uMail, @RequestParam("u_password") String uPassword,
			@RequestParam("u_birth") String uBirth, HttpServletRequest request) {
		try {
			request.getSession().invalidate(); // 刪除舊Session
			HttpSession session = request.getSession(true); // 建立新Session
			String encryptedPassword = userService.encryptNewPassword(uPassword); // 將密碼明碼先加密
			// 將表單數據寫入Session
			session.setAttribute("u_name", uName);
			session.setAttribute("u_gender", uGender);
			session.setAttribute("u_nickname", uNickname);
			session.setAttribute("u_phone", uPhone);
			session.setAttribute("u_mail", uMail);
			session.setAttribute("u_password", encryptedPassword);
			session.setAttribute("u_birth", uBirth);
			// 生成六位數驗證碼並同時寫入Redis 並返回驗證碼
			String verificationCode = userService.getVerificationCode(uMail);
			// 寄送驗證信
			userService.sendVerificationMail(uMail, uNickname, verificationCode);
			// 返回成功的文字0
			return "0";
		} catch (Exception e) {
			System.out.println("驗證碼寄信過程異常!");
			e.printStackTrace();
			// 返回失敗的文字1
			return "1";
		}
	}

	// 註冊-重新發送驗證信
	@PostMapping("/resend_verification_mail")
	public void resendVerificationMail(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				String uMail = (String) session.getAttribute("u_mail");
				String uNickname = (String) session.getAttribute("u_nickname");
				// 生成六位數驗證碼並同時寫入Redis 並返回驗證碼
				String verificationCode = userService.getVerificationCode(uMail);
				// 寄送驗證信
				userService.sendVerificationMail(uMail, uNickname, verificationCode);
			} else {
				System.out.println("Session不存在或已過期");
			}
		} catch (Exception e) {
			System.out.println("驗證碼重新寄信過程異常!");
			e.printStackTrace();
		}
	}

	// 註冊-接收會員輸入的驗證碼&核對&完成註冊
	@PostMapping("/check_verifivation_code")
	public String verifyMailInReigsterData(HttpServletRequest request) {
		// 確認Session中註冊資料是否仍在
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("Session已過期!");
			return "3"; // Session已過期
		}
		try {
			String uMail = (String) session.getAttribute("u_mail");
			String inputVerificationCode = request.getParameter("verificationCode");
			String savedVerificationCode = userService.getVerifiactionCodeInRedis(uMail);
			// 處理Redis返回的驗證碼
			switch (savedVerificationCode) {
			case "noDataFound":
				return "2"; // 驗證碼過期
			case "RedisError":
				return "3"; // 系統錯誤
			default:
				if (!inputVerificationCode.equals(savedVerificationCode)) {
					return "1"; // 驗證碼錯誤
				}
				// 驗證碼正確並處理註冊
				return handleRegistration(session, uMail) ? "0" : "3";
			}
		} catch (Exception e) {
			System.out.println("驗證碼核對與註冊系統錯誤!");
			e.printStackTrace();
			return "3"; // 系統錯誤
		}
	}

	// 處理從Session讀取資料封裝DTO和註冊流程
	private boolean handleRegistration(HttpSession session, String uMail) {
		// 從Session讀取註冊資料準備傳入DTO
		String uName = (String) session.getAttribute("u_name");
		String uGenderString = (String) session.getAttribute("u_gender");
		Integer uGender = null;
		try {
			uGender = Integer.parseInt(uGenderString);
			// 轉換Integer成功
		} catch (NumberFormatException e) {
			System.out.println("uGender轉換Interger失敗!");
			e.printStackTrace();
		}
		String uNickname = (String) session.getAttribute("u_nickname");
		String uPhone = (String) session.getAttribute("u_phone");
		String uPassword = (String) session.getAttribute("u_password");
		String uBirthString = (String) session.getAttribute("u_birth");
		Date uBirth = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			uBirth = dateFormat.parse(uBirthString);
			// 轉Date成功
		} catch (ParseException e) {
			System.out.println("uBirth轉換Date失敗!");
			e.printStackTrace();
		}
		Integer uStatus = 0; // "0"代表正常帳號
		LocalDate uRegisterDate = LocalDate.now();
		// 註冊資料封裝DTO
		UserRegisterDataDTO userRegisterDataDTO = new UserRegisterDataDTO(uNickname, uName, uMail, uPhone, uGender,
				uBirth, uPassword, uStatus, uRegisterDate);
		// 呼叫Service處理DTO寫入
		if (userService.isRegisterUserSuccess(userRegisterDataDTO) == 0) {
			// 驗證碼通過並完成註冊並清除Session中的註冊資料
			session.invalidate();
			return true;
		} else {
			// 系統錯誤
			return false;
		}
	}

// ===================================================================		
	// 登入-依據會員輸入的信箱檢查此帳戶是否存在
	 @PostMapping("/find_account_by_mail")
	 public int checkAccountbyMail(@RequestBody Map<String, String> requestBody) {
	  String uMail = requestBody.get("u_email");
	  boolean isUserExist = userService.isEmailRegistered(uMail);
	  if (!isUserExist) {
	   return 1; // 會員不存在
	  } 
	  return userService.userStatusCheck(uMail); // 依據uStatus返回不同狀態 
	 }

	// 登入-處理會員的登入，並在登入後重發Session並寫入常用資料
	@PostMapping("/process_user_login")
	public int processUserLogin(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
		String uMail = loginData.get("u_email");
		String password = loginData.get("u_password");
		UserReadDataDTO user = userService.userLogin(uMail, password);
		// 檢查user是否為null
		if (user != null) {
			int uStatus = user.getuStatus(); // 取得uStatus
			int uId = user.getuId();
			Integer coachId = coachService.getCoachIdById(uId);
			// 檢查uStatus是否允許登入
			if (uStatus == 0 || uStatus == 1) {
				// 登入成功的Session處理
				request.getSession().invalidate(); // 刪除舊Session
				HttpSession newSession = request.getSession(true); // 建立新Session
				// 在新Session寫入已登入會員資訊
				newSession.setAttribute("uId", uId);
				newSession.setAttribute("uNickname", user.getuNickname());
				newSession.setAttribute("uStatus", uStatus);
				newSession.setAttribute("cId", coachId); // 若為0表時無教練身分; 若有教練身分 寫入教練Id
				// 有登入的Session才有"loginStatus" 直接確認Session有沒有"loginStatus"這個項目就能判斷有無登入
				// "logged_in"值可不用比對
				newSession.setAttribute("loginStatus", "logged_in"); // 登入狀態
				newSession.setAttribute("loginDate", new Date()); // 登入時間
				newSession.setAttribute("lastActiveTime", new Date()); // 最後活動時間
				newSession.setMaxInactiveInterval(60 * 60); // Session保存期限(秒)
				// Joy - 將使用者已擁有課程存入Redis
				cartService.storeOwnedCoursesInRedis(uId);
				return 0; // 登入成功
			} else {
				return 1; // 登入失敗
			}
		} else {
			return 1; // 登入失敗
		}
	}

// ===================================================================
	// 忘記密碼-檢查信箱和寄送驗證碼

	@PostMapping("/send_password_mail")
	public int sendMailForChangingPassword(@RequestBody Map<String, String> requestBody) {
		String uMail = requestBody.get("u_email");
		if (userService.isEmailRegistered(uMail)) {
			return userService.sendChangePasswordMail(uMail);
			// return為0表示信件寄送成功 為2表示系統異常
		} else {
			// 會員不存在
			return 1;
		}
	}

	@PostMapping("/user_forget_password")
	public int userForgetPassword(@RequestBody Map<String, String> requestBody) {
		String uMail = requestBody.get("u_email");
		String uPassword = requestBody.get("u_password");
		String inputVarificationCode = requestBody.get("verification_code");
		try {
			return userService.changeUserPassword(uMail, uPassword, inputVarificationCode);
			// 依據處理結果返回代碼
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
			// 系統異常
		}
	}

	// 會員登出-Post
	@PostMapping("/user_logout_post")
	public Map<String, Object> logoutByPost(HttpServletRequest request) {
		request.getSession().invalidate(); // 刪除當前Session(登出)
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("redirect", "/"); // 重新定向到首頁
		return response;
	}


	//----------------------------------------------------------------------
	// 測試開發用
	@PostMapping("/user_update_photo_test")
	public ResponseEntity<?> updateUserHeadshot(@RequestParam("u_id") Integer uId,
												@RequestParam("photo") MultipartFile photo) {
		try {
			byte[] photoBytes = photo.getBytes();
			UserHeadshotOnlyDTO headshotDTO = new UserHeadshotOnlyDTO();
			headshotDTO.setuId(uId);
			headshotDTO.setuHeadshot(photoBytes);

			boolean updated = userService.updateUserHeadshot(headshotDTO);
			if (updated) {
				return ResponseEntity.ok().body("Photo updated successfully");
			} else {
				return ResponseEntity.badRequest().body("Failed to update photo");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error updating photo");
		}
	}

	@PostMapping("/user_headshot_test")
	public ResponseEntity<byte[]> getUserHeadshot(@RequestBody Map<String, Integer> body) {
		Integer uId = body.get("u_id");
		UserHeadshotOnlyDTO headshotDTO = userService.getUserHeadshotDTOById(uId);
		if (headshotDTO != null && headshotDTO.getuHeadshot() != null) {
			byte[] photoBytes = headshotDTO.getuHeadshot();

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

			return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/get_user_all_data_test")
	public UserReadDataDTO getUserAllData(@RequestBody Map<String, Integer> request) {
		Integer uId = request.get("uId");
		UserReadDataDTO userData = userService.getUserDataDTOByID(uId);
		return userData;
	}

	@PostMapping("/update_user_data_test")
	public ResponseEntity<?> updateUserData(@RequestBody UserWriteDataDTO userDTO) {
		boolean updateResult = userService.updateUserData(userDTO);
		if (updateResult) {
			return ResponseEntity.ok().body("資料更新成功");
		} else {
			return ResponseEntity.badRequest().body("資料更新失敗");
		}
	}

	@PostMapping("/set_user_mood_test/")
	public ResponseEntity<?> setUserMood(@RequestBody Map<String, Integer> requestBody) {
		Integer uId = requestBody.get("u_id");
		Integer moodId = requestBody.get("mood_id");
		boolean updateResult = userService.updateUserMood(uId, moodId);

		if (updateResult) {
			// 操作成功，返回JSON格式的成功消息
			return ResponseEntity.ok().body(Map.of("message", "心情更新成功"));
		} else {
			// 操作失敗，返回JSON格式的錯誤消息
			return ResponseEntity.badRequest().body(Map.of("error", "心情更新失敗"));
		}
	}

	@PostMapping("/get_user_mood_test/")
	public ResponseEntity<?> getUserMood(@RequestBody Map<String, Integer> requestBody) {
		Integer uId = requestBody.get("u_id");
		Integer mood = userService.getUserMoodById(uId);
		System.out.println("uId = " + uId);
		System.out.println("mood = " + mood);
		if (mood != null) {
			return ResponseEntity.ok().body(Map.of("mood", mood)); // 確保這裡的Map包含了mood這個鍵
		} else {
			return ResponseEntity.badRequest().body("找不到指定用戶的心情");
		}
	}

	@PostMapping("/user_update_password_test")
	public ResponseEntity<?> updateUserPassword(@RequestParam Integer uId, @RequestParam String oldPassword,
												@RequestParam String newPassword) {
		try {
			boolean updateResult = userService.updateUserPassword(uId, oldPassword, newPassword);
			if (updateResult) {
				return ResponseEntity.ok().body("Password updated successfully");
			} else {
				return ResponseEntity.badRequest().body("Failed to update password");
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("An error occurred");
		}
	}

	@PostMapping("/user_find_saved_password")
	public ResponseEntity<?> getSavedPassword(@RequestParam Integer uId) {
		try {
			String savedPassword = userService.getSavedPasswordInMySQL(uId);
			// 注意：直接返回密碼是不安全的。這裡只是為了示範。
			return ResponseEntity.ok().body(savedPassword);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("An error occurred");
		}
	}

}
