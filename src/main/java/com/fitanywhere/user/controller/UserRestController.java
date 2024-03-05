package com.fitanywhere.user.controller;

import java.awt.PageAttributes.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Map;
import java.util.Enumeration;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.service.PasswordEncryptionService;
import com.fitanywhere.user.model.UserHeadshotOnlyDTO;
import com.fitanywhere.user.model.UserReadDataDTO;
import com.fitanywhere.user.model.UserRegisterDataDTO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;
import com.fitanywhere.user.model.UserWriteDataDTO;

@RestController
//在路徑上用"/user_api"做為區別
@RequestMapping("/user_api")
public class UserRestController {

	@Autowired
	private UserService userService;

// ===================================================================
	// 註冊-檢查信箱是否重複註冊
	@PostMapping("/check_register_duplicate")
	public String checkRegisterDuplicate(HttpServletRequest request) {
		boolean isRegisterDuplicate = false;

		String testItem = request.getParameter("fieldId");
//	    System.out.println("requestType =" + testItem);

		// 根據不同數據資料呼叫對應的方法判斷
		switch (testItem) {
		case "u_mail":
			isRegisterDuplicate = userService.isEmailRegistered(request.getParameter("fieldValue"));
//            System.out.println("mail =" + isRegisterDuplicate);
			break;
		case "u_nickname":
			isRegisterDuplicate = userService.isNicknameRegistered(request.getParameter("fieldValue"));
//            System.out.println("nickname =" + isRegisterDuplicate);
			break;
		case "u_phone":
			isRegisterDuplicate = userService.isPhoneRegistered(request.getParameter("fieldValue"));
//            System.out.println("phone =" + isRegisterDuplicate);
			break;
		default:
//        	System.out.println("Wrong Test!");
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
		try {
			HttpSession session = request.getSession(false);
			String uMail = (String) session.getAttribute("u_mail");
			String inputVerificationCode = request.getParameter("verificationCode");
			String savedVerificationCode = userService.getVerifiactionCodeInRedis(uMail);

			if (savedVerificationCode.equals("noDataFound")) {
				// Redis查無資料返回"2"表示驗證碼已逾期
				return "2";
			} else if (savedVerificationCode.equals("RedisError")) {
				// Redis執行錯誤返回"3"表示系統錯誤
				return "3";
			} else {
				if (inputVerificationCode.equals(savedVerificationCode)) {
					// 驗證碼通過並開始處理註冊
					String uName = (String) session.getAttribute("u_name");

					String uGenderString = (String) session.getAttribute("u_gender");
					Integer uGender = null;
					try {
						uGender = Integer.parseInt(uGenderString);
						// 轉換Integer成功
					} catch (NumberFormatException e) {
						System.out.println("uGender轉換Interger失敗!");
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
					}

					Integer uStatus = 0;
					LocalDate uRegisterDate = LocalDate.now();

					// 註冊資料封裝DTO
					UserRegisterDataDTO userRegisterDataDTO = new UserRegisterDataDTO(uNickname, uName, uMail, uPhone,
							uGender, uBirth, uPassword, uStatus, uRegisterDate);

					// 呼叫Service傳入DTO
					if (userService.isRegisterUserSuccess(userRegisterDataDTO) == 0) {
						// 驗證碼通過並完成註冊
						request.getSession().invalidate();
						// 透過銷毀現有Session刪除註冊資料
						return "0";
					} else {
						// 寫入執行錯誤返回"3"表示系統錯誤
						return "3";
					}

				} else {
					// 驗證碼錯誤並未完成註冊
					return "1";
				}
			}
		} catch (Exception e) {
			System.out.println("驗證碼核對過程異常!");
			e.printStackTrace();
			// Controller執行錯誤返回"3"表示系統錯誤
			return "3";
		}
	}

// ===================================================================			

	// 登入-依據會員輸入的信箱檢查此帳戶是否存在
	@PostMapping("/find_account_by_mail")
	public int checkAccountbyMail(@RequestBody Map<String, String> requestBody) {
		String uMail = requestBody.get("u_email");
		boolean isUserExist = userService.isEmailRegistered(uMail);

		// 返回數字表示賬戶是否存在：0表示存在，1表示不存在
		return isUserExist ? 0 : 1;
	}

	// 登入-處理會員的登入，並在登入後重發Session並寫入常用資料
	@PostMapping("/process_user_login")
	public int processUserLogin(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
		String uMail = loginData.get("u_email");
		String password = loginData.get("u_password");
		UserReadDataDTO user = userService.userLogin(uMail, password);

		if (user != null) {
			// 登入成功的Session處理
			request.getSession().invalidate(); // 刪除舊Session
			HttpSession newSession = request.getSession(true); // 建立新Session

			// 在新Session寫入已登入會員資訊
			newSession.setAttribute("uId", user.getuId());
			newSession.setAttribute("uNickname", user.getuNickname());
			newSession.setAttribute("uStatus", user.getuStatus());

			// 有登入的Session才有"loginStatus" 直接確認Session有沒有"loginStatus"這個項目就能判斷有無登入
			// "logged_in"值到是可不用比對
			newSession.setAttribute("loginStatus", "logged_in"); // 登入狀態
			newSession.setAttribute("uPerm", 9); // 暫定9代表一般會員

			newSession.setAttribute("loginDate", new Date()); // 登入時間
			newSession.setAttribute("lastActiveTime", new Date()); // 最後活動時間
			newSession.setMaxInactiveInterval(60 * 60); // Session保存期限(秒)

			System.out.println("uId: " + newSession.getAttribute("uId"));
			System.out.println("uNickname: " + newSession.getAttribute("uNickname"));
			System.out.println("uStatus: " + newSession.getAttribute("uStatus"));

			return 0; // 登入成功
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
			return 3;
			// 系統異常
		}

	}

//	======================================
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

}
