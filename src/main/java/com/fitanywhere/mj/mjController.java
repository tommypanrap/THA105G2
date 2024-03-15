package com.fitanywhere.mj;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/mj")
public class mjController {
	@GetMapping("checkout")
	public String checkout(ModelMap model){
		return "front-end/mj/checkout";
	}
//	@GetMapping("cart")
//	public String cart(ModelMap model) {
//		return "front-end/mj/cart";
//	}
	
	@GetMapping("course_details_2")
	public String courseDetail(ModelMap model) {
		return "front-end/mj/course_details_2";
	}
	
	
//	@GetMapping("course_filter_two_toggle")
//	public String courseList(ModelMap model) {
//		return "front-end/mj/course_filter_two_toggle";
//	}
	
	@GetMapping("student_enrolled_courses")
	public String studentCourses(ModelMap model) {
		
		return "front-end/mj/student_enrolled_courses";
	}	
	@GetMapping("student_gift_history")
	public String courseGiftHistory(ModelMap model) {
		
		return "front-end/mj/student_gift_history";
	}	
	@GetMapping("student_my_quiz_attempts")
	public String courseMyQuiz(ModelMap model) {
		
		return "front-end/mj/student_my_quiz_attempts";
	}	
//	@GetMapping("student_order_history")
//	public String courseOrderHistory(ModelMap model) {
//
//		return "front-end/mj/student_order_history";
//	}
	@GetMapping("student_reviews")
	public String courseReviews(ModelMap model) {
		
		return "front-end/mj/student_reviews";
	}
	@GetMapping("student_wishlist")
	public String courseWishList(ModelMap model) {
		
		return "front-end/mj/student_wishlist";
	}
}
