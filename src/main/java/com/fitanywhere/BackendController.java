package com.fitanywhere;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackendController {

	
	@GetMapping("/backend_course")
	public String backend_course(Model model)  {
        return "back-end/backend_course"; 
    }
	
//	@GetMapping("/backend_ad")
//	public String backend_ad(Model model)  {
//		AdVO adVO = new AdVO();
//		model.addAttribute("adVO", adVO);
//        return "back-end/backend_ad"; 
//	}
//	
//	@GetMapping("/backend_ad_list")
//	public String backend_ad_list(Model model)  {
//        return "back-end/backend_ad_list"; 
//    }
//	

	@GetMapping("/backend_course_discount")
	public String backend_course_discount(Model model)  {
        return "back-end/backend_course_discount"; 
    }
	

	
}
