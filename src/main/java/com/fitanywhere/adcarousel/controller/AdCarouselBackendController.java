package com.fitanywhere.adcarousel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.ad.model.AdService;
import com.fitanywhere.ad.model.AdVO;
import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Controller
@RequestMapping("/backEnd/adCarousel")
public class AdCarouselBackendController {
	
	@Autowired
    private AdCarouselService AdCarSvc;
	
	@Autowired
    private AdService adSvc;

//	關聯user表格
	@Autowired
	private UserService userSvc;
	
//	關聯課程表格
	@Autowired
	private CourseService courSvc;

//	@GetMapping("/backend_ad")
//	public String backend_ad(Model model)  {
//		AdVO adVO = new AdVO();
//		model.addAttribute("adVO", adVO);
//        return "back-end/backend_ad"; 
//	}
	
	@GetMapping("/backend_ad_carousellist")
	public String backend_ad_list(Model model)  {
        return "back-end/backend_ad_carousellist"; 
    }
	
//	修改方案資料後將資料帶去listall
	@PostMapping("getOne_For_Displayadcarousel")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="輪播編號: 請勿空白")
		@Digits(integer = 3, fraction = 0, message = "輪播編號: 請填數字-請勿超過{integer}位數")
		@Min(value = 1, message = "輪播編號: 不能小於{value}")
		@Max(value = 100, message = "輪播編號: 不能超過{value}")
		@RequestParam("adcId") String adcId,
		ModelMap model) {
		
		/***************************2.開始查詢資料*********************************************/
//		EmpService empSvc = new EmpService();
		AdCarouselVO adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcId));
		
		List<AdCarouselVO> list = AdCarSvc.getAll();
		System.out.println(list);
		model.addAttribute("AdCarouselListData", list); // for select_page.html 第97 109行用
		
		if (adcarVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/backend_ad_carousellist";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("adcarVO", adcarVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
		
//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
		return "back-end/backend_adcarouselupdate"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}

	@ModelAttribute("AdCarouselListData")
	protected List<AdCarouselVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<AdCarouselVO> list = AdCarSvc.getAll();
		return list;
	}
	
//	點下修改按鈕後，將該ROW資料取出帶去修改頁面
	@PostMapping("getOne_For_Updateadcarousel")
	public String getOne_For_Update(@RequestParam("adcId") String adcId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdCarouselVO adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("adcarVO", adcarVO);
		return "back-end/backend_adcarouselupdate"; // 查詢完成後轉交update_emp_input.html
	}

//  修改完成帶回全部廣告訂單
	@PostMapping("updateadcarousel")
	public String update(@Valid AdCarouselVO adcarVO, BindingResult result, ModelMap model,
			@RequestParam("adcUpdatePic") MultipartFile part) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(adcarVO, result, "adcUpdatePic");
		
//		保留不更動的訂單完成時間
		 AdCarouselVO originalAdcarVO = AdCarSvc.getOneAdcId(adcarVO.getAdcId());
		if (part.isEmpty()) { // 使用者未選擇要上傳的新圖片時
			byte[] upFiles = AdCarSvc.getOneAdcId(adcarVO.getAdcId()).getAdcUpdatePic();	
			adcarVO.setAdcUpdatePic(upFiles);
		} else {
				byte[] upFiles = part.getBytes();
				adcarVO.setAdcUpdatePic(upFiles);
		}
//		將原本的訂單完成時間塞回去
		adcarVO.setAdcOrderEnddate(originalAdcarVO.getAdcOrderEnddate());
		System.out.println("test sasasa");
		System.out.println("result has error" + result.hasErrors());
		List<FieldError> listField = result.getFieldErrors();
		for(FieldError error : listField) {
			System.out.println(error);
		}
		System.out.println("test sasasa" );
		if (result.hasErrors()) {
			System.out.println("出錯啦老鐵");
			return "back-end/adCarousel/backend";
			
		}
		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdCarSvc.updateAdCarousel(adcarVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcarVO.getAdcId()));
		model.addAttribute("adcarVO", adcarVO);
		return "back-end/backend_ad_carousellist"; // 修改成功後轉交listOneEmp.html
	}
	
	public BindingResult removeFieldError(AdCarouselVO adCarouselVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(adCarouselVO, "adCarouselVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
}
