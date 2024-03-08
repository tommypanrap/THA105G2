package com.fitanywhere.admin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.admin.model.AdminService;

@RestController
@RequestMapping("/backend_login_api")
public class AdminRestController {

	@Autowired
	private AdminService adminService;

// ==================================
// 管理員登入

	// 預查輸入工號是否存在
	@PostMapping("/check_admin_id")
	public ResponseEntity<?> checkAccountbyAdminId(@RequestBody Map<String, String> requestBody) {
		String stringAdminId = requestBody.get("admin_id");
		Integer adminId = null;
		try {
			adminId = Integer.parseInt(stringAdminId);
			// 轉換Integer成功
		} catch (NumberFormatException e) {
			System.out.println("adminId轉換Interger失敗!");
		}

		boolean isAdminIdExist = adminService.isAdminIdCorrect(adminId);

		if (isAdminIdExist) {
			return ResponseEntity.ok().build(); // 200
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
		}
	}

	// 預查輸入管理員姓名是否存在
	@PostMapping("/check_admin_name")
	public ResponseEntity<?> checkAccountbyAdminName(@RequestBody Map<String, String> requestBody) {
		String adminName = requestBody.get("admin_name");

		boolean isAdminNameExist = adminService.isAdminNameCorrect(adminName);

		if (isAdminNameExist) {
			return ResponseEntity.ok().build(); // 200
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
		}
	}

	// 管理員登入
	@PostMapping("/admin_login")
	public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
		String stringAdminId = loginData.get("am_id");
		Integer adminId = null;
		try {
			adminId = Integer.parseInt(stringAdminId);
			// 轉換Integer成功
		} catch (NumberFormatException e) {
			System.out.println("adminId轉換Interger失敗!");
		}

		String adminName = loginData.get("am_name");
		String adminPassword = loginData.get("am_password");

		Integer loginResult = adminService.adminLoginVerification(adminId, adminName, adminPassword);

		// 根據不同的登入結果返回不同的HTTP狀態
		switch (loginResult) {
		case 0:
			request.getSession().invalidate(); // 刪除舊Session
			HttpSession session = request.getSession(true); // 建立新Session
			session.setAttribute("adminId", adminId);
			session.setAttribute("adminName", adminName);

			// 0 = admin; 1 = manager; 2 = editor; (紙面分級 不用這項也沒差)
			session.setAttribute("adminLevel", adminService.getAdminLevel(adminId));

			session.setAttribute("adminLoginStatus", "admin_logged_in"); // 登入狀態

			session.setAttribute("loginDate", new Date()); // 登入時間
			session.setAttribute("lastActiveTime", new Date()); // 最後活動時間
			session.setMaxInactiveInterval(60 * 60 * 2); // Session保存期限(秒)

			// System.out.println("adminLevel: " + session.getAttribute("adminLevel"));

			// 成功登入
			return ResponseEntity.ok().build();
		case 1:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		case 2:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		default:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	// 登出後台
	@PostMapping("/admin_logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    
		request.getSession().invalidate(); // 使當前Session無效
		
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/backend_login"); // 重定向到登入頁面
	}

}
