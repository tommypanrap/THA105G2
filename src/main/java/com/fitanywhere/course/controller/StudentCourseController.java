package com.fitanywhere.course.controller;

import com.fitanywhere.coach.model.CoachService;
import com.fitanywhere.coach.model.CoachVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.coursedetail.model.CourseDetailServiceImpl;
import com.fitanywhere.coursedetail.model.CourseDetailVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
public class StudentCourseController {

	@Autowired
	CourseService courseSvc;
	@Autowired
	UserService userSvc;
	@Autowired
	CourseDetailServiceImpl courseDetailSvc;
	@Autowired
	CoachService coachSvc;

	// single course
	@GetMapping(value = "/single_course/{crId}")
	public String singleCourse(@PathVariable Integer crId, ModelMap model, HttpSession session) {
		CourseVO courseVO = courseSvc.getOneCourse(crId);

		// Base64抓單圖片 改為fetch抓
//		courseVO.setBase64CrCover(Base64.getEncoder().encodeToString(courseVO.getCrCover()));
		UserVO userVO = userSvc.getUserDataByID(courseVO.getUserVO().getuId());
		CoachVO coachVO = coachSvc.getOneCoach(courseVO.getUserVO().getuId());

		// 獲取課程下的第一個影片ID作為預設影片URL
		Integer cdId = courseVO.getCourseDetails().stream()
				.findFirst()
				.map(CourseDetailVO::getCdId)
				.orElseThrow(() -> new IllegalStateException("找不到Id" + crId));
		String videoUrl = "/course/single_course/video/" + cdId + "/1";

		// 獲取特定課程ID下的所有影片列表
		List<CourseDetailVO> videos = courseDetailSvc.findVideosByCourseId(crId);
		// 計算單元數量
		Integer unitCount = courseDetailSvc.getunitCount(crId);

		// th:each
		List<CourseVO> allCourses = courseSvc.getAll();
		Collections.shuffle(allCourses);
		List<CourseVO> randomList = allCourses.stream()
				.limit(3)
				.collect(Collectors.toList());

		// 抓uid相關資訊
		Integer getUId = userVO.getuId();
		List<CourseVO> userCourses = courseSvc.getCourseByUId(getUId);
		List<CourseVO> relatedCourse = userCourses.stream()
				.limit(3)
				.collect(Collectors.toList());


		model.addAttribute("courseVO", courseVO);
		model.addAttribute("userVO", userVO);
		model.addAttribute("videoUrl", videoUrl);
		model.addAttribute("videosList", videos);
		model.addAttribute("coursesList", randomList);
		model.addAttribute("relatedCourse", relatedCourse);
		model.addAttribute("unitCount", unitCount);
		model.addAttribute("coachVO", coachVO);
		return "front-end/mj/course_details_2";
	}

	@GetMapping(value = "/own_course_list/{crId}")
	public String ownCourseList(@PathVariable Integer crId, ModelMap model, HttpSession session) {


		return "front-end/mj/student_enrolled_courses";
	}
}