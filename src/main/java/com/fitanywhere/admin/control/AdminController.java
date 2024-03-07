package com.fitanywhere.admin.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	// 後台登入頁面
	@GetMapping("/backend_login")
	public String backend_login(Model model)  {
        return "back-end/backend_login"; 
    }
	
	// 會員一覽&後台首頁
	@GetMapping("/backend_user")
	public String backend_user(Model model)  {
        return "back-end/backend_user"; 
    }

}
