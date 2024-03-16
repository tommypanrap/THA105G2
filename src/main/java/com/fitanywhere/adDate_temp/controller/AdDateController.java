package com.fitanywhere.adDate_temp.controller;

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
import com.fitanywhere.addate.model.AdDateService;
import com.fitanywhere.addate.model.AdDateVO;

@Controller
@RequestMapping("/adDate")
public class AdDateController {

	@Autowired
	private AdService adSvc;

	@Autowired
	private AdDateService adDateSvc;

	@GetMapping("/addAdDate")
	public String addAdDate(ModelMap model) {
		AdDateVO adDateVO = new AdDateVO();
		model.addAttribute("adDateVO", adDateVO);
		return "back-end/adDate/addAdDate";
	}

	@PostMapping("insert")
	public String insert(@Valid AdDateVO adDateVO, ModelMap model) {

		adDateSvc.addAdDate(adDateVO);

		List<AdDateVO> list = adDateSvc.getAll();
		model.addAttribute("adDateListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/adDate/listAllAdDate";
	}

//    查詢單個id資料以進行更新
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("adDateId") String adDateId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		System.out.println("come");
		AdDateVO adDateVO = adDateSvc.getOneadDateId(Integer.valueOf(adDateId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("adDateVO", adDateVO);
		return "back-end/adDate/update_adDate_input"; // 查詢完成後轉交update_emp_input.html
	}

	@PostMapping("update")
	public String update(@Valid AdDateVO adDateVO, BindingResult result, ModelMap model) {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		result = removeFieldError(adDateVO, result, "adId");
		if (result.hasErrors()) {
			return "back-end/adDate/update_adDate_input";
		}

		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		adDateSvc.updateAdDate(adDateVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		adDateVO = adDateSvc.getOneadDateId(Integer.valueOf(adDateVO.getAdDateId()));
		model.addAttribute("adDateVO", adDateVO);
		return "back-end/adDate/listOneAdDate"; // 修改成功後轉交listOneEmp.html
	}

//    關聯到另一個表 
	@ModelAttribute("adListData")
	protected List<AdVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<AdVO> list = adSvc.getAll();
		return list;
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(AdDateVO adDateVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(adDateVO, "adDateVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

	@GetMapping("select_page")
	public String select_pageAdDate(Model model) {
		return "back-end/adDate/select_page";
	}

	@GetMapping("listAllAdDate")
	public String listAllAdDate(Model model) {
		return "back-end/adDate/listAllAdDate";
	}

	@ModelAttribute("adDateListData") // for select_page.html 第97 109行用 // for listAllEmp.html 第117 133行用
	protected List<AdDateVO> referenceAdDateListData(Model model) {

		List<AdDateVO> list = adDateSvc.getAll();
		return list;
	}

}
