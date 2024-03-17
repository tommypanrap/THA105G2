package com.fitanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.ad.model.AdVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;

import java.io.IOException;
import java.util.*;

import javax.validation.Valid;

@Controller
public class BackendController {


	
	@GetMapping("/backend_news")
	public String backend_news(Model model)  {
        return "back-end/backend_news"; 
    }
	
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
	@GetMapping("/backend_course_order")
	public String backend_course_order(Model model)  {
        return "back-end/backend_course_order"; 
    }
	
	@GetMapping("/backend_course_discount")
	public String backend_course_discount(Model model)  {
        return "back-end/backend_course_discount"; 
    }
	
	@GetMapping("/backend_social")
	public String backend_social(Model model)  {
        return "back-end/backend_social"; 
    }
	
}
