package com.fitanywhere.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	//註冊頁面
	@GetMapping("/user_register")
	public String userRegister() {
		return "front-end/user/user_register";
	}
	
	//登入頁面
	@GetMapping("/user_login")
	public String userLogin() {
		return "front-end/user/user_login";
	}
	
	//登入頁面
	@GetMapping("/user_forget_password")
	public String userForgetPassword() {
		return "front-end/user/user_forget_password";
	}
	
	//過濾器將未登入遊客跳轉登入頁面
	@GetMapping("/force_user_login")
	public ModelAndView userForceLogin() {
		ModelAndView modelAndView = new ModelAndView("front-end/user/user_login");
		modelAndView.addObject("loginMessage", "您需要登入才能訪問此頁面!");
		System.out.println("loginMessage: " + modelAndView.getModel().get("loginMessage"));
		return modelAndView;
	}
	
	//過濾器將未登入跳轉登入頁面
	@GetMapping("/user_login_after_register")
	public ModelAndView userLoginAfterRegister() {
		ModelAndView modelAndView = new ModelAndView("front-end/user/user_login");
		modelAndView.addObject("loginMessage", "您已完成註冊，請輸入信箱和密碼登入!");
		System.out.println("loginMessage: " + modelAndView.getModel().get("loginMessage"));
		return modelAndView;
	}

// ==================================
	// 開發測試用
	
	// uHeadshot讀寫
	@GetMapping("/test1")
	public String test1Page() {
		return "front-end/user/test-only/test1";
	}
	// 讀取user資料
	@GetMapping("/test2")
	public String test2Page() {
		return "front-end/user/test-only/test2";
	}
	// 寫入user資料
	@GetMapping("/test3")
	public String test3Page() {
		return "front-end/user/test-only/test3";
	}
	// 讀寫user心情
	@GetMapping("/test4")
	public String test4Page() {
		return "front-end/user/test-only/test4";
	}
	
	// 自行更新密碼
	@GetMapping("/test5")
	public String test5Page() {
		return "front-end/user/test-only/test5";
	}

//	Andy
	@GetMapping("/coach_profile")
	public UserVO profile(ModelMap model) {

		Integer uId = 10001;
		UserVO userVO = userService.getUser(uId);
		return userVO;
	}


}
