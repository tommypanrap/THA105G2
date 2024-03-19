package com.fitanywhere.ad.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.ad.model.AdService;
import com.fitanywhere.ad.model.AdVO;
import com.fitanywhere.adate.model.AdDateService;
import com.fitanywhere.adate.model.AdDateVO;
import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;

@Controller
@RequestMapping("/ad")
public class adController {

	@Autowired
	private AdService adSvc;

	@Autowired
	private AdDateService adDateSvc;

	@Autowired
	private AdCarouselService AdCarSvc;

	@GetMapping("/addAd")
	public String addAd(ModelMap model) {
		AdVO adVO = new AdVO();
		model.addAttribute("adVO", adVO);
		return "back-end/ad/addAd";
	}

	@PostMapping("insert")
	public String insert(@Valid AdVO adVO, ModelMap model) {

		adSvc.addAd(adVO);

		List<AdVO> list = adSvc.getAll();
		model.addAttribute("adListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/ad/listAllAd";
	}

//    查詢單個id資料以進行更新
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("adId") String adId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		AdVO adVO = adSvc.getOneAd(Integer.valueOf(adId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("adVO", adVO);
		return "back-end/ad/update_ad_input"; // 查詢完成後轉交update_emp_input.html
	}

	@PostMapping("update")
	public String update(@Valid AdVO adVO, BindingResult result, ModelMap model) {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		result = removeFieldError(adVO, result, "adId");
		if (result.hasErrors()) {
			return "back-end/ad/update_ad_input";
		}

		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		adSvc.updateAd(adVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		adVO = adSvc.getOneAd(Integer.valueOf(adVO.getAdId()));
		model.addAttribute("adVO", adVO);
		return "back-end/ad/listOneAd"; // 修改成功後轉交listOneEmp.html
	}

//    關聯到另一個表 
	@ModelAttribute("adDateListData")
	protected List<AdDateVO> referenceadDateListData() {
		// DeptService deptSvc = new DeptService();
		List<AdDateVO> list = adDateSvc.getAll();
		return list;
	}

	@ModelAttribute("AdCarouselListData")
	protected List<AdCarouselVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<AdCarouselVO> list = AdCarSvc.getAll();
		return list;
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(AdVO adVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(adVO, "adVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

	@GetMapping("select_page")
	public String select_page(Model model) {
		return "back-end/ad/select_page";
	}

	@GetMapping("listAllAd")
	public String listAllAd(Model model) {
		return "back-end/ad/listAllAd";
	}

	@ModelAttribute("adListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<AdVO> referenceListData(Model model) {

		List<AdVO> list = adSvc.getAll();
		return list;
	}
	
	@GetMapping("/hello")
	public String goToIndex(Model model) {
		return "back-end/index2";
	}

}
