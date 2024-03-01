package com.fitanywhere.course.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
//import com.fitanywhere.user.model.UserService;

@Controller
@RequestMapping("/student_course")
public class StudentCourseController {

	@Autowired
	CourseService courseSvc;
//	@Autowired
//	UserService userSvc;

	@GetMapping("add_course")
	public String addCourse(ModelMap model) {
		CourseVO courseVO = new CourseVO();
		model.addAttribute("courseVO", courseVO);
		return "back-end/student_course/add_course";
	}

	@PostMapping("insert")
	public String insert(
			@Valid CourseVO courseVO, BindingResult result, ModelMap model,
			@RequestParam("crCover") MultipartFile[] parts) throws IOException {

		// 1.接收請求參數 - 輸入格式的錯誤處理
		// 去除BindingResult中 crCover 欄位的 FieldError 紀錄
		result = removeFieldError(courseVO, result, "crCover");

		if (parts[0].isEmpty()) {
			model.addAttribute("errorMessage", "課程照片:請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				courseVO.setCrCover(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/student_course/add_course";	
		}
		// 2.開始新增資料
		courseSvc.addCourse(courseVO);
		// 3.新增完成,準備轉交(Send the Success view)
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		model.addAttribute("success", "-(新增成功)");
		return "redirect:/student_course/list_all_course";
	}

	// called on listAllEmp.html form submission
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("crId") String crId, ModelMap model) {
		// 2.開始查詢資料
		CourseVO courseVO = courseSvc.getOneCourse(Integer.valueOf(crId));
		// 3.查詢完成,準備轉交(Send the Success view)
		model.addAttribute("courseVO", courseVO);
		// 查詢完成後轉交update_emp_input.html
		return "back-end/student_course/update_course_input";
	}
	
	@PostMapping("update")
	public String update(
			@Valid CourseVO courseVO, BindingResult result, ModelMap model,
			@RequestParam("crCover") MultipartFile[] parts
			) throws IOException{
		
		// 1.接收請求參數
		result = removeFieldError(courseVO, result, "crCover");
		
		if(parts[0].isEmpty()) {
			byte[] crCover = courseSvc.getOneCourse(courseVO.getCrId()).getCrCover();
			courseVO.setCrCover(crCover);
		}else {
			for(MultipartFile multipartFile : parts) {
				byte[] crCover = multipartFile.getBytes();
				courseVO.setCrCover(crCover);
			}
		}
		if(result.hasErrors()) {
		return "back-end/student_course/update_course_input";
		}
		
		// 2.開始修改資料
		courseSvc.updateCourse(courseVO);
		// 3.修改完成,準備轉交
		model.addAttribute("success", " - (修改成功)");
		courseVO = courseSvc.getOneCourse(Integer.valueOf(courseVO.getCrId()));
		model.addAttribute("courseVO", courseVO);
		return "back-end/student_course/list_one_course";
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
// 

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
	
	/*
	 * This method will be called on select_page.html form submission, handling POST request
	 */
	// 複合查詢
//	@PostMapping("listEmps_ByCompositeQuery")
//	public String listAllEmp(HttpServletRequest req, Model model) {
//		Map<String, String[]> map = req.getParameterMap();
//		List<EmpVO> list = empSvc.getAll(map);
//		model.addAttribute("empListData", list); // for listAllEmp.html 第85行用
//		return "back-end/emp/listAllEmp";
//	}

}