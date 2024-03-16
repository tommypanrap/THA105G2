package com.fitanywhere.adCarousel_temp.controller;

import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;

import java.util.*;



@Controller
@Validated
@RequestMapping("/adCarousel")
public class AdCarouselIdController {
	
	@Autowired
	AdCarouselService AdCarSvc;

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(
		/***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		@NotEmpty(message="輪播編號: 請勿空白")
		@Digits(integer = 4, fraction = 0, message = "輪播編號: 請填數字-請勿超過{integer}位數")
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
			return "back-end/adCarousel/select_page";
		}
		
		/***************************3.查詢完成,準備轉交(Send the Success view)*****************/
		model.addAttribute("adcarVO", adcarVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
		
//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
		return "back-end/adCarousel/select_page"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneEmp-div
	}

	@ModelAttribute("AdCarouselListData")
	protected List<AdCarouselVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<AdCarouselVO> list = AdCarSvc.getAll();
		return list;
	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	    StringBuilder strBuilder = new StringBuilder();
	    for (ConstraintViolation<?> violation : violations ) {
	          strBuilder.append(violation.getMessage() + "<br>");
	    }
	    //==== 以下第80~85行是當前面第69行返回 /src/main/resources/templates/back-end/emp/select_page.html 第97行 與 第109行 用的 ====   
//	    model.addAttribute("empVO", new EmpVO());
//    	EmpService empSvc = new EmpService();
		List<AdCarouselVO> list = AdCarSvc.getAll();
		model.addAttribute("AdCarouselListData", list); // for select_page.html 第97 109行用
		
		String message = strBuilder.toString();
	    return new ModelAndView("back-end/adCarousel/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
	}
	
}