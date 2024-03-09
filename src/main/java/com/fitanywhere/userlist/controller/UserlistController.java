package com.fitanywhere.userlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fitanywhere.userlist.model.UserlistAllDataDTO;
import com.fitanywhere.userlist.model.UserlistService;

@Controller
public class UserlistController {
	@Autowired
	private UserlistService userlistService;

	// 會員一覽&後台首頁
	@GetMapping("/backend_userlist")
	public String listUsers(Model model) {
		List<UserlistAllDataDTO> users = userlistService.getAllUsersWithCoachId();
		model.addAttribute("users", users);
		return "back-end/backend_user"; // 假设您的Thymeleaf模板文件名为users.html
	}

}
