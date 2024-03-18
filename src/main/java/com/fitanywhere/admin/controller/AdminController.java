package com.fitanywhere.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	// 後台登入頁面
	@GetMapping("/backend_login")
	public String backend_login(Model model)  {
        return "back-end/backend_login"; 
    }	
	

}
