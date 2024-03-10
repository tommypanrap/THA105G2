package com.fitanywhere.userlist.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.userlist.model.UserlistAllDataDTO;
import com.fitanywhere.userlist.model.UserlistService;

@Controller
public class UserlistController {

	@Autowired
	private UserlistService userlistService;

	// 會員一覽&後台首頁
	@GetMapping("/backend_userlist")
	public String listUsers(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "none") String filterStatus) {
		Page<UserlistAllDataDTO> userPage;

		switch (filterStatus) {
		case "1":
			userPage = userlistService.getAllUsersByStatus(Arrays.asList(0, 1), PageRequest.of(page, size));
			break;
		case "2":
			userPage = userlistService.getAllUsersByStatus(Arrays.asList(2, 3), PageRequest.of(page, size));
			break;
		default:
			userPage = userlistService.getAllUsersWithCoachId(PageRequest.of(page, size));
		}

		model.addAttribute("userPage", userPage);
		return "back-end/backend_userlist_with_page";
	}

	// 使用Email查詢特定會員
	@GetMapping("/backend_userlist_searchByEmail")	
	public String searchUserByEmail(Model model, @RequestParam String email) {
	    UserlistAllDataDTO userDTO = userlistService.findSingleUserByEmail(email);
	    if (userDTO != null) {
	        model.addAttribute("user", userDTO);
	    } else {
	        model.addAttribute("searchError", "沒有找到相關的用戶資料");
	    }
	    return "back-end/backend_userlist_without_page";
	}


}
