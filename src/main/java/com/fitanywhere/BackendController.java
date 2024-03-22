package com.fitanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.ann.model.AnnVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseStatus1DTO;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.coursedetail.model.CourseDetailService;

import java.io.IOException;
import java.util.*;

import javax.validation.Valid;

@Controller
public class BackendController {

	@Autowired
	CourseService courseSvc;
	
	@Autowired
	CourseDetailService courseDetailSvc;
	
	
	@GetMapping("/backend_course")
	public String backend_course(Model model)  {
        return "back-end/backend_course"; 
    }
	
	@PostMapping("/check_course")
	public String check_course(Model model,Integer crId)  {
		
		CourseVO courseVO = courseSvc.getOneCourse(crId);
		String base64String = Base64.getEncoder().encodeToString(courseVO.getCrCover());
		List<String> cdVideosList = courseDetailSvc.getCourseVideoPathByCourseId(crId);
		
		model.addAttribute("courseVO", courseVO);
		model.addAttribute("cdVideosList", cdVideosList);
		model.addAttribute("base64Cover", base64String);
		return "back-end/check_course"; 
	}
	
	@PostMapping("/approval")
	public String approval(@RequestParam("crId")Integer crId,@RequestParam("crState")Integer crState,Model model) {
		courseSvc.checkCourse(crId, crState);
		return "back-end/backend_course";
	}
	
	@GetMapping("/backend_ad")
	public String backend_ad(Model model)  {
        return "back-end/backend_ad"; 
	}
	
	@GetMapping("/backend_ad_list")
	public String backend_ad_list(Model model)  {
        return "back-end/backend_ad_list"; 
    }
	
	@GetMapping("/backend_course_order")
	public String backend_course_order(Model model)  {
        return "back-end/backend_course_order"; 
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
	

	
	@ModelAttribute("courseList")
	public List<CourseVO> getcourse1(Integer uId) {
		List<CourseVO> courseList = courseSvc.getAll();
		return courseList;
	}
	
}
