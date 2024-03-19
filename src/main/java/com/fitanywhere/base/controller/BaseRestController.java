package com.fitanywhere.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Eugen
@RestController
@RequestMapping("/base_api")
public class BaseRestController {

	// 檢查使用者是否有登入會員並返回HTTP狀態碼
	@PostMapping("/check_user_login")
	public ResponseEntity<?> checkUserLoggedInOrNot(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // 讀取Session並禁止發新Session

		if (session != null && session.getAttribute("loginStatus") != null) {
			return ResponseEntity.ok().build(); // 200
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
		}
	}

	// 取得已登入的會員暱稱
	@PostMapping("/get_user_nickname")
	public String getLoggedInUserNickname(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // 讀取Session並禁止發新Session

		if (session != null && session.getAttribute("loginStatus") != null) {
			return (String) session.getAttribute("uNickname"); // 若有登入則返回會員暱稱
		} else {
			return "Not logged in!"; // 若未登入則返回未登入文字
		}
	}

	// 取得已登入的會員Id
	@PostMapping("/get_user_id")
	public String getLoggedInUserId(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // 讀取Session並禁止發新Session

		if (session != null && session.getAttribute("loginStatus") != null) {
			return (String) session.getAttribute("uId"); // 若有登入則返回會員Id
		} else {
			return "Not logged in!"; // 若未登入則返回未登入文字
		}
	}

	// 取得已登入的會員狀態
	@PostMapping("/get_user_status")
	public String getLoggedInUserStatus(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // 讀取Session並禁止發新Session

		if (session != null && session.getAttribute("loginStatus") != null) {
			return (String) session.getAttribute("uStatus"); // 若有登入則返回會員狀態
		} else {
			return "Not logged in!"; // 若未登入則返回未登入文字
		}
	}

	// 取得已登入的會員的教練Id
	@PostMapping("/get_user_coach_id")
	public String getLoggedInUserCoachStatus(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // 讀取Session並禁止發新Session

		if (session != null && session.getAttribute("loginStatus") != null) {
			return (String) session.getAttribute("cId"); // 若為0表時無教練身分; 若有教練身分 寫入教練Id
		} else {
			return "Not logged in!"; // 若未登入則返回未登入文字
		}
	}
	
	// Tommy header user 渲染
//	@ModelAttribute("sessionHeaderUId")
//	public String sessionHeaderUId(HttpServletRequest request) {
//		HttpSession session = request.getSession(false); // 讀取Session並禁止發新Session
//
//		if (session != null && session.getAttribute("loginStatus") != null) {
//			return (String) session.getAttribute("uNickname"); // 若有登入則返回會員暱稱
//		} else {
//			return "Not logged in!"; // 若未登入則返回未登入文字
//		}
//	}
	

}
