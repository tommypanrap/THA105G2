package com.fitanywhere.ann.controller;

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

@Controller
@RequestMapping("/ann")
public class AnnController {

	@Autowired
	AnnService annSvc;
	
	@Autowired
	CourseService courseSvc;

//	@Autowired
//	DeptService deptSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
//	@GetMapping("addAnn")
//	public String addAnn(ModelMap model) {
//		AnnVO annVO = new AnnVO();
//		model.addAttribute("annVO", annVO);
//		return "back-end/ann/addAnn";
//	}
	
	@GetMapping("G2_instructor_announce")
	public String addAnn(ModelMap model) {
		AnnVO annVO = new AnnVO();
		List<AnnVO> list = annSvc.getAll();
		List<CourseVO> list2 = courseSvc.getAll();
		model.addAttribute("courseListData", list2);
		model.addAttribute("annListData", list);
		model.addAttribute("annVO", annVO);
		return "back-end/ann/G2_instructor_announce";
	}
	
	

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid AnnVO annVO, BindingResult result, ModelMap model) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(annVO, result, "anId");

//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
//			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				empVO.setUpFiles(buf);
//			}
//		}|| parts[0].isEmpty()
		if (result.hasErrors() ) {
			return "back-end/ann/addAnn";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		long currentTimeMillis = System.currentTimeMillis();

		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		annVO.setAnDate(currentTimestamp);
		annVO.setAnEditDate(currentTimestamp);
		annSvc.addAnn(annVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<AnnVO> list = annSvc.getAll();
		model.addAttribute("annListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "back-end/ann/select_page"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
	}
	
	

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("anId") String anId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AnnVO annVO = annSvc.getOneAnn(Integer.valueOf(anId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("annVO", annVO);
		return "back-end/ann/update_ann_input"; // 查詢完成後轉交update_emp_input.html
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid AnnVO annVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(annVO, result, "upFiles");

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
		if (result.hasErrors()) {
			return "back-end/ann/update_ann_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		annSvc.updateAnn(annVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		annVO = annSvc.getOneAnn(Integer.valueOf(annVO.getAnId()));
		model.addAttribute("annVO", annVO);
		return "back-end/ann/listOneAnn"; // 修改成功後轉交listOneEmp.html
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("anId") String anId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		annSvc.deleteAnn(Integer.valueOf(anId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<AnnVO> list = annSvc.getAll();
		model.addAttribute("annListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/ann/listAllAnn"; // 刪除完成後轉交listAllEmp.html
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
	public BindingResult removeFieldError(AnnVO annVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(annVO, "annVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}