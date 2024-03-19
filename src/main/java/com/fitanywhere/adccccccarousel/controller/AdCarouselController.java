package com.fitanywhere.adccccccarousel.controller;

import javax.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.ad.model.AdService;
import com.fitanywhere.adccccccarousel.model.AdCarouselService;
import com.fitanywhere.adccccccarousel.model.AdCarouselVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/adCarousel")
public class AdCarouselController {

	
	@Autowired
    private AdCarouselService AdCarSvc;
	
	@Autowired
    private AdService adSvc;

//	關聯user表格
//	@Autowired
//	private UserService userSvc;
	
//	關聯課程表格
//	@Autowired
//	private CourseService courseSvc;

	/*
	 * This method will serve as addEmp.html handler.
	 */
//	@GetMapping("addAdCarousel")
//	public String addAdCarousel(ModelMap model) {
//		AdCarouselVO adcarVO = new AdCarouselVO();
//		model.addAttribute("adcarVO", adcarVO);
//		return "back-end/adCarousel/addAdCarousel";
//	}
//	
//	前端畫面
	@GetMapping("addAdCarousel")
	public String addAdCarousel(ModelMap model) {
		AdCarouselVO adcarVO = new AdCarouselVO();
		model.addAttribute("adcarVO", adcarVO);
		return "front-end/ad/instructor-advertising";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid AdCarouselVO adcarVO, BindingResult result, ModelMap model,
			@RequestParam("adcUpdatePic") MultipartFile[] parts) throws IOException {
		System.out.println(adcarVO);

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(adcarVO, result, "adcUpdatePic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "輪播方案: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				adcarVO.setAdcUpdatePic(buf);
			}
		}
		if (result.hasErrors()) {
			return "back-end/adCarousel/addAdCarousel";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdCarSvc.addAdCarousel(adcarVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<AdCarouselVO> list = AdCarSvc.getAll();
		model.addAttribute("AdCarouselListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/adCarousel/listAllAdCarousel"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("adcId") String adcId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdCarouselVO adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("adcarVO", adcarVO);
		return "back-end/adCarousel/update_adCarousel_input"; // 查詢完成後轉交update_emp_input.html
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid AdCarouselVO adcarVO, BindingResult result, ModelMap model,
			@RequestParam("adcUpdatePic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(adcarVO, result, "adcUpdatePic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] upFiles = AdCarSvc.getOneAdcId(adcarVO.getAdcId()).getAdcUpdatePic();
			adcarVO.setAdcUpdatePic(upFiles);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] upFiles = multipartFile.getBytes();
				adcarVO.setAdcUpdatePic(upFiles);
			}
		}
		if (result.hasErrors()) {
			return "back-end/adCarousel/update_adCarousel_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdCarSvc.updateAdCarousel(adcarVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcarVO.getAdcId()));
		model.addAttribute("adcarVO", adcarVO);
		return "back-end/adCarousel/listOneAdCarousel"; // 修改成功後轉交listOneEmp.html
	}
	
    @GetMapping("select_page")
	public String select_pageadCarousel(Model model) {
		return "back-end/adCarousel/select_page";
	}
    
    @GetMapping("listAllAdCarousel")
	public String listAlladCarousel(Model model) {
		return "back-end/adCarousel/listAllAdCarousel";
	}
    
	@ModelAttribute("AdCarouselListData")
	protected List<AdCarouselVO> referenceAdCarouselListData() {

		List<AdCarouselVO> list = AdCarSvc.getAll();
		return list;
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST request
	 */
//	@PostMapping("delete")
//	public String delete(@RequestParam("empno") String empno, ModelMap model) {
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		/*************************** 2.開始刪除資料 *****************************************/
//		// EmpService empSvc = new EmpService();
//		empSvc.deleteEmp(Integer.valueOf(empno));
//		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//		List<EmpVO> list = empSvc.getAll();
//		model.addAttribute("empListData", list);
//		model.addAttribute("success", "- (刪除成功)");
//		return "back-end/emp/listAllEmp"; // 刪除完成後轉交listAllEmp.html
//	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : 
	 * <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
	 */
	@ModelAttribute("AdCarouselListData")
	protected List<AdCarouselVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<AdCarouselVO> list = AdCarSvc.getAll();
		return list;
	}

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
	public BindingResult removeFieldError(AdCarouselVO adcarVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(adcarVO, "adcarVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

}