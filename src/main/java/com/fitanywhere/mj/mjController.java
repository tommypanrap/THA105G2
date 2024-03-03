package com.fitanywhere.mj;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/mj")
public class mjController {
	@GetMapping("cart")
	public String cart(ModelMap model) {
		return "front-end/mj/cart";
	}
	
	@GetMapping("course-details-2")
	public String courseDetail(ModelMap model) {
		return "front-end/mj/course-details-2";
	}
	
	
	@GetMapping("course-filter-two-toggle")
	public String courseList(ModelMap model) {
		return "front-end/mj/course-filter-two-toggle";
	}	
	
	@GetMapping("student-enrolled-courses")
	public String studentCourses(ModelMap model) {
		
		return "front-end/mj/student-enrolled-courses";
	}	
	@GetMapping("student-gift-history")
	public String courseGiftHistory(ModelMap model) {
		
		return "front-end/mj/student-gift-history";
	}	
	@GetMapping("student-my-quiz-attempts")
	public String courseMyQuiz(ModelMap model) {
		
		return "front-end/mj/student-my-quiz-attempts";
	}	
	@GetMapping("student-order-history")
	public String courseOrderHistory(ModelMap model) {
		
		return "front-end/mj/student-order-history";
	}	
	@GetMapping("student-reviews")
	public String courseReviews(ModelMap model) {
		
		return "front-end/mj/student-reviews";
	}
	@GetMapping("student-wishlist")
	public String courseWishList(ModelMap model) {
		
		return "front-end/mj/student-wishlist";
	}
}
