package com.fitanywhere.socialpost.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.socialpost.model.SocialPostService;
import com.fitanywhere.socialpost.model.SocialPostVO;
import com.fitanywhere.mood.model.MoodVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/socialpost")
public class SocialPostController {
	
	@Autowired
	SocialPostService socialPostSvc;
	
	@Autowired
	UserService userSvc;
	
	@GetMapping("select_page")
	public String select_page(Model model) {
		return "front-end/socialpost/select_page";
	}
	

	
	
	
    @GetMapping("list_all_socialpost")
	public String listAllSocialPost(Model model) {
		return "front-end/socialpost/list_all_socialpost";
	}
    
    
    @GetMapping("/socialpost/add_socialpost")
	public String addSocialPost(Model model) {
		return "front-end/socialpost/add_socialpost";
	}
    
    @ModelAttribute("socialPostListData")  // for select_page.html 第97 109行用 // for listAllEmp.html 第85行用
  	protected List<SocialPostVO> referenceListData(Model model) {
  		
      	List<SocialPostVO> list = socialPostSvc.getAll();
  		return list;
  	}
	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("add_socialpost")
	public String addSocialPost(ModelMap model) {
		SocialPostVO socialPostVO = new SocialPostVO();
		model.addAttribute("socialPostVO", socialPostVO);
		return "front-end/socialpost/add_socialpost";
	}
	
	@GetMapping("show_login_socialpost_test")
	public String showLoginSocialPost(HttpServletRequest req, ModelMap model) {
		HttpSession newSession = req.getSession(true);
		
		UserVO userVO = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));
	    model.addAttribute("userVO", userVO);
		
		SocialPostVO socialPostVO = new SocialPostVO();
		model.addAttribute("socialPostVO", socialPostVO);
		return "front-end/socialpost/show_login_socialpost_test";
	}

	@GetMapping("student_socialpost")
	public String getUserInfo(HttpServletRequest req, ModelMap model) {
		HttpSession newSession = req.getSession(true);
		System.out.println("印出session"+newSession);
	    UserVO userVO = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));
	    model.addAttribute("userVO", userVO);
	    
	    System.out.println("心情"+userVO.getMoodVO());
	    
	    SocialPostVO socialPostVO = new SocialPostVO();
		model.addAttribute("socialPostVO", socialPostVO);
		 
		return "front-end/socialpost/student_socialpost";
	}
	
	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
		public String insert(HttpServletRequest req, @Valid SocialPostVO socialPostVO,BindingResult result, ModelMap model,
				@RequestParam("sppic") MultipartFile[] parts )throws IOException {
		HttpSession newSession = req.getSession(true);
		UserVO userVO = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));
		socialPostVO.setUserVO(userVO);
		
		socialPostVO.setSptime(new Timestamp(System.currentTimeMillis()));
		
		
			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		result = removeFieldError(socialPostVO, result, "sppic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				socialPostVO.setSppic(buf);
			}
		}

			if (result.hasErrors() || parts[0].isEmpty()) {
				return "front-end/socialpost/socialpost";
			}
			
			/*************************** 2.開始新增資料 *****************************************/
			socialPostSvc.addSocialPost(socialPostVO);
			/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
			List<SocialPostVO> list = socialPostSvc.getAll();
			model.addAttribute("socialPostListData", list);
			model.addAttribute("success", "- (新增成功)");
			
			
			return "redirect:/socialpost/student_socialpost";
		}
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("spid") String spid, ModelMap model) {
		
		/*************************** 2.開始查詢資料 *****************************************/
		SocialPostVO socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(spid));
		
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("socialPostVO", socialPostVO);
		
		return "front-end/socialpost/update_socialpost_input"; // 查詢完成後轉交update_emp_input.html
	}
	
	
	@PostMapping("update_social_post")
	public String update_social_post(@RequestParam("spid") String spid, ModelMap model) {
		System.out.println("update_social_post有	進來");
		/*************************** 2.開始查詢資料 *****************************************/
		SocialPostVO socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(spid));
		
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("socialPostVO", socialPostVO);
		
		return "redirect:/socialpost/student_socialpost"; // 
	}
	
	@PostMapping("update")
	public String update(@Valid SocialPostVO socialPostVO, BindingResult result, ModelMap model,
			@RequestParam("sppic") MultipartFile[] parts) throws IOException {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(socialPostVO, result, "sppic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] sppic = socialPostSvc.getOneSocialPost(socialPostVO.getSpid()).getSppic();
			socialPostVO.setSppic(sppic);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] sppic = multipartFile.getBytes();
				socialPostVO.setSppic(sppic);
			}
		}
		if (result.hasErrors()) {
			return "front-end/socialpost/update_socialpost_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		socialPostSvc.updateSocialPost(socialPostVO);
		
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(socialPostVO.getSpid()));
		model.addAttribute("socialPostVO", socialPostVO);

		return "front-end/socialpost/list_one_socialpost";
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam("spid") String spid, ModelMap model) {
		
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		/*************************** 2.開始刪除資料 *****************************************/
		socialPostSvc.deleteSocialPost(Integer.valueOf(spid));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<SocialPostVO> list = socialPostSvc.getAll();
		model.addAttribute("socialpostListData", list);
		model.addAttribute("success", "- (刪除成功)");
		
		return "front-end/socialpost/listAllEmp";
		
	}
	
	@ModelAttribute("socialPostListData")
	protected List<SocialPostVO> referenceListData() {
		List<SocialPostVO> list = socialPostSvc.getAll();
		return list;
	}
	
	
	public BindingResult removeFieldError(SocialPostVO socialPostVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname))
				.collect(Collectors.toList());
		result = new BeanPropertyBindingResult(socialPostVO, "socialPostVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	

@ModelAttribute("userListData")
protected List<UserVO> userReferenceListData() {
	// DeptService deptSvc = new DeptService();
	List<UserVO> list = userSvc.getAll();

	return list;
}
	
}
