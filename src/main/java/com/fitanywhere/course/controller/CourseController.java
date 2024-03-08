package com.fitanywhere.course.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.fitanywhere.ann.model.AnnService;
import com.fitanywhere.ann.model.AnnVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/course")
public class CourseController {

	@Autowired
	CourseService courseSvc;
	
	@Autowired
	AnnService annSvc;
	
	@Autowired
	UserService userSvc;



	// 購物車功能-課程資訊新增到 model
	@GetMapping("courses")
	public String getCourses(ModelMap model) {
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		return "front-end/mj/course_filter_two_toggle";
	}

	@GetMapping("create_course")
	public String addCourse(ModelMap model) {
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		return "front-end/course/create_course";
	}
	@GetMapping("coach_dashboard")
	public String dashboard(ModelMap model,HttpSession session) {
		Integer uId = 10001;//先寫死等登入
//		Integer uId = (Integer)session.getAttribute("uId");
		byte [] uHeadshot = userSvc.getUserHeadshot(uId);
		Integer coursecount = courseSvc.getCourseCount(uId);
		System.out.println(uId);
		UserVO userVO = userSvc.getUser(uId);
		String uName = userVO.getuName();
		List<CourseVO> courseList = courseSvc.getAll();
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		model.addAttribute("uHeadshot", Base64.getEncoder().encodeToString(uHeadshot));
		model.addAttribute("coursecount", coursecount);
		model.addAttribute("uName", uName);
		model.addAttribute("courseList", courseList);
        return "front-end/course/coach_dashboard";
	}
	@GetMapping("coach_profile")
	public String profile(ModelMap model) {
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
        return "front-end/course/coach_profile";
	}
	@GetMapping("course_announcement")
	public String announcement(ModelMap model,HttpSession session) {
		Integer uId = 10001;//先寫死等登入
//		Integer uId = (Integer)session.getAttribute("uId");
		byte [] uHeadshot = userSvc.getUserHeadshot(uId);
		model.addAttribute("uHeadshot", Base64.getEncoder().encodeToString(uHeadshot));
		List<AnnVO> announcementList = annSvc.getAll();
	    model.addAttribute("announcements", announcementList);
		return "front-end/course/course_announcement";
	}
	@GetMapping("course_announce")
	public String announce(ModelMap model) {
		Integer uId = 10001;//先寫死等登入
		byte [] uHeadshot = userSvc.getUserHeadshot(uId);
		model.addAttribute("uHeadshot", Base64.getEncoder().encodeToString(uHeadshot));

		AnnVO annVO = new AnnVO();
		List<CourseVO> list2 = courseSvc.getAll();
		model.addAttribute("courseListData", list2);
		model.addAttribute("annVO", annVO);
		return "front-end/course/course_announce";
	}
	@GetMapping("coach_settings")
	public String settings(ModelMap model) {
		Integer uId = 10001 ;//先寫死等登入uId
		byte [] uHeadshot = userSvc.getUserHeadshot(uId);
		model.addAttribute("uHeadshot", Base64.getEncoder().encodeToString(uHeadshot));

		UserVO userVO = userSvc.getUser(uId);
		model.addAttribute("userVO", userVO);
		return "front-end/course/coach_settings";
	}
	
	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insertcourse(@Valid CourseVO courseVO, BindingResult result, ModelMap model) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(courseVO, result, "crId");

//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
//			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				empVO.setUpFiles(buf);
//			}
//		}|| parts[0].isEmpty()
		if (result.hasErrors() ) {
			return "front-end/course/coach_dashboard";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		courseVO.setCrCreateDate(currentTimestamp);
		courseVO.setCrEditDate(currentTimestamp);
		courseSvc.addCourse(courseVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//		List<AnnVO> list = annSvc.getAll();
//		model.addAttribute("annListData", list);
//		model.addAttribute("success", "- (新增成功)");
		return "front-end/course/coach_dashboard"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("crId") String crId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		CourseVO courseVO = courseSvc.getOneCourse(Integer.valueOf(crId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("courseVO", courseVO);
		return "back-end/course/update_course_input"; // 查詢完成後轉交update_emp_input.html
	}
	@PostMapping("course_announcement_update")
	public String getAnnoucement_For_Update(@RequestParam("anId") String anId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		AnnVO annVO = annSvc.getOneAnn(Integer.valueOf(anId));
		Integer crId = annVO.getCrId();
		CourseVO courseVO = courseSvc.getOneCourse(crId);
		String crTitle = courseVO.getCrTitle();
		Integer uId = 10001;//先寫死等登入
		byte [] uHeadshot = userSvc.getUserHeadshot(uId);
		model.addAttribute("uHeadshot", Base64.getEncoder().encodeToString(uHeadshot));


		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("anId", anId);
		model.addAttribute("crTitle", crTitle);
		model.addAttribute("annVO", annVO);
		return "front-end/course/course_announcement_update"; // 查詢完成後轉交update_emp_input.html
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid AnnVO annVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(courseVO, result, "upFiles");

//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
//			// EmpService empSvc = new EmpService();
//			byte[] upFiles = empSvc.getOneEmp(empVO.getEmpno()).getUpFiles();
//			empVO.setUpFiles(upFiles);
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] upFiles = multipartFile.getBytes();
//				empVO.setUpFiles(upFiles);
//			}
//		}
//		if (result.hasErrors()) {
//			return "back-end/course/update_course_input";
//		}
		/*************************** 2.開始修改資料 *****************************************/
		annSvc.updateAnn(annVO);
		
		List<AnnVO> announcementList = annSvc.getAll();
	    model.addAttribute("announcements", announcementList);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
//		courseVO = courseSvc.getOneCourse(Integer.valueOf(courseVO.getCrId()));
//		model.addAttribute("courseVO", courseVO);
		return "front-end/course/course_announcement"; // 修改成功後轉交listOneEmp.html
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("crId") String crId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		courseSvc.deleteCourse(Integer.valueOf(crId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/course/listAllCourse"; // 刪除完成後轉交listAllEmp.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
//	@ModelAttribute("deptListData")
//	protected List<DeptVO> referenceListData() {
//		// DeptService deptSvc = new DeptService();
//		List<DeptVO> list = deptSvc.getAll();
//		return list;
//	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${depMapData}" />
	 */
//	@ModelAttribute("deptMapData") //
//	protected Map<Integer, String> referenceMapData() {
//		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
//		map.put(10, "財務部");
//		map.put(20, "研發部");
//		map.put(30, "業務部");
//		map.put(40, "生管部");
//		return map;
//	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(CourseVO courseVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(courseVO, "courseVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}