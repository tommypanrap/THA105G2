package com.fitanywhere.course.controller;

import javax.servlet.http.HttpSession;

import com.fitanywhere.coursedetail.controller.CourseDetailController;
import com.fitanywhere.coursedetail.model.CourseDetailServiceImpl;
import com.fitanywhere.coursedetail.model.CourseDetailVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import com.fitanywhere.user.model.UserService;

@Controller
@RequestMapping("/course")
public class StudentCourseController {

	@Autowired
	CourseService courseSvc;
	@Autowired
	UserService userSvc;
	@Autowired
	CourseDetailServiceImpl courseDetailSvc;


	// single course
	@GetMapping(value = "/single_course/{crId}")
	public String singleCourse(
			@PathVariable Integer crId, ModelMap model, HttpSession session
			){
		CourseVO courseVO = courseSvc.getOneCourse(crId);
		UserVO userVO = userSvc.getUserDataByID(courseVO.getUserVO().getuId());

		// 獲取課程下的第一個影片ID作為預設影片URL
		Integer cdId = courseVO.getCourseDetails().stream()
				.findFirst()
				.map(CourseDetailVO::getCdId)
				.orElseThrow( () -> new IllegalStateException("找不到Id"+ crId));
		String videoUrl = "/course/single_course/video/" + cdId + "/1";

		// 獲取特定課程ID下的所有影片列表
		List<CourseDetailVO> videos = courseDetailSvc.findVideosByCourseId(crId);

		List<CourseVO> list = courseSvc.getAll();


		model.addAttribute("courseVO", courseVO);
		model.addAttribute("userVO", userVO);
		model.addAttribute("videoUrl", videoUrl);
		model.addAttribute("videosList", videos);
		model.addAttribute("coursesList", list);
		return "front-end/mj/course_details_2";
	}


}