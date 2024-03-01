package com.fitanywhere.course.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;

@Controller
@Validated
@RequestMapping("/student_course")
public class StudentCrIdController {

	@Autowired
	CourseService courseSvc;

	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
			// 1.請求參數
			@NotEmpty(message = "課程編號請勿空白") 
			@RequestParam("crId") String crId, ModelMap model) {
		
		// 2.開始查詢資料
		CourseVO courseVO = courseSvc.getOneCourse(Integer.valueOf(crId));
		
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		
		if(courseVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/student_course/select_page";
		}
		// 3.完成轉交
		model.addAttribute("courseVO", courseVO);
		model.addAttribute("getOne_For_Display", "true");
		return "back-end/student_course/select_page";
	}
	
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for(ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		String message = strBuilder.toString();
		return new ModelAndView("back-end/student_course/select_page", "errorMessage","請修正以下錯誤:<br>" + message);
		
	}
}
