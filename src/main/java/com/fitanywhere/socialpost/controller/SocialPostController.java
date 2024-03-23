package com.fitanywhere.socialpost.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fitanywhere.coach.model.CoachVO;
import com.fitanywhere.course.model.CourseStatus0DTO;
import com.fitanywhere.course.model.CourseStatus1DTO;
import com.fitanywhere.course.model.CourseStatus2DTO;
import com.fitanywhere.socialpost.model.SocialPostService;
import com.fitanywhere.socialpost.model.SocialPostVO;
import com.fitanywhere.socialpost.model.SocialReplyService;
import com.fitanywhere.socialpost.model.SocialReplyVO;
import com.fitanywhere.user.model.UserHeadshotOnlyDTO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/socialpost")
public class SocialPostController {

	@Autowired
	SocialPostService socialPostSvc;

	@Autowired
	SocialReplyService socialReplySvc;

	@Autowired
	UserService userSvc;

	@GetMapping("/socialpost/add_socialpost")
	public String addSocialPost(Model model) {
		return "front-end/socialpost/add_socialpost";
	}

	@ModelAttribute("socialPostListData")
	protected List<SocialPostVO> referenceListData(Model model) {

		List<SocialPostVO> list = socialPostSvc.getAll();
		return list;
	}

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

	// 主要的mapping
	@GetMapping("")
	public String social_post_member(HttpServletRequest req, ModelMap model) {

		HttpSession newSession = req.getSession(true);

		int sessionUId = Integer.valueOf(newSession.getAttribute("uId").toString());
//		System.out.println("sessionUId:"+sessionUId);

		model.addAttribute("sessionUId", sessionUId);

		UserVO sessionUserVO = userSvc.getUserDataByID(sessionUId);
		model.addAttribute("userVO", sessionUserVO);

		UserVO userShowVO = userSvc.getUserDataByID(sessionUId);
		model.addAttribute("userShowVO", userShowVO);

		SocialPostVO socialPostVO = new SocialPostVO();
		model.addAttribute("socialPostVO", socialPostVO);

		return "front-end/socialpost/student_socialpost";
	}

	// 主要的mapping 後面帶上 userId
	@GetMapping("{userId}")
	public String social_post_member_id(HttpServletRequest req, ModelMap model,
			@PathVariable("userId") Integer userId) {

		HttpSession newSession = req.getSession(true);

//		UserVO userVO2 = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));
		int sessionUId = Integer.valueOf(newSession.getAttribute("uId").toString());
//		System.out.println("sessionUId:"+sessionUId);

		model.addAttribute("sessionUId", sessionUId);

		UserVO sessionUserVO = userSvc.getUserDataByID(sessionUId);
		model.addAttribute("userVO", sessionUserVO);

		UserVO userShowVO = userSvc.getUserDataByID(userId);
		model.addAttribute("userShowVO", userShowVO);

		SocialPostVO socialPostVO = new SocialPostVO();
		model.addAttribute("socialPostVO", socialPostVO);

		return "front-end/socialpost/student_socialpost";

	}

	@PostMapping("/{spid}/replies")
	public ResponseEntity<Void> add_social_reply(HttpServletRequest req, @RequestParam String replyValue,
			@RequestParam String spid, @RequestParam Integer uId, ModelMap model) {

		HttpSession newSession = req.getSession(true);

		UserVO userVO = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));

		SocialPostVO socialPostVOforAddReply = socialPostSvc.getOneSocialPost(Integer.parseInt(spid));
		SocialReplyVO socialReplyVO = new SocialReplyVO();
		socialReplyVO.setSocialPostVO(socialPostVOforAddReply);
		socialReplyVO.setSrTime(new Timestamp(System.currentTimeMillis()));
		socialReplyVO.setSrUpdate(new Timestamp(System.currentTimeMillis()));
		socialReplyVO.setSrStatus(1);
		socialReplyVO.setUserVO(userVO);
		socialReplyVO.setSrContent(replyValue);

		socialReplySvc.addSocialReply(socialReplyVO);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 新增貼文
	@PostMapping("insert")
	public String insert(HttpServletRequest req, @Valid SocialPostVO socialPostVO, BindingResult result, ModelMap model,
			@RequestParam("sppic") MultipartFile[] parts) throws IOException {
		HttpSession newSession = req.getSession(true);
		UserVO userVO = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));
		socialPostVO.setUserVO(userVO);
		socialPostVO.setSpstatus(1);
		socialPostVO.setSptime(new Timestamp(System.currentTimeMillis()));

		result = removeFieldError(socialPostVO, result, "sppic");

		if (parts[0].isEmpty()) {
			model.addAttribute("errorMessage", "社群貼文: 請上傳圖片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				socialPostVO.setSppic(buf);
			}
		}

		if (result.hasErrors() || parts[0].isEmpty()) {
			return "front-end/socialpost/";
		}

		socialPostSvc.addSocialPost(socialPostVO);

		List<SocialPostVO> list = socialPostSvc.getAll();
		model.addAttribute("socialPostListData", list);
		model.addAttribute("success", "- (新增成功)");

		return "redirect:/socialpost/";
	}

	// 更新貼文
	@PostMapping("update_social_post/{uId}")
	public String update_social_post(@PathVariable("uId") Integer uId, @Valid SocialPostVO socialPostVO,
			BindingResult result, ModelMap model, @RequestParam("sppic") MultipartFile[] parts) throws IOException {

		result = removeFieldError(socialPostVO, result, "sppic");

		if (parts[0].isEmpty()) {

			byte[] sppic = socialPostSvc.getOneSocialPost(socialPostVO.getSpid()).getSppic();
			socialPostVO.setSppic(sppic);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] sppic = multipartFile.getBytes();
				socialPostVO.setSppic(sppic);
			}
		}
		if (result.hasErrors()) {
			return "redirect:/socialpost/" + uId;
		}
		SocialPostVO getSocialPostVO = socialPostSvc.getOneSocialPost(socialPostVO.getSpid());

		socialPostVO.setUserVO(getSocialPostVO.getUserVO());
		socialPostVO.setSptime(getSocialPostVO.getSptime());
		socialPostVO.setSpstatus(getSocialPostVO.getSpstatus());

		socialPostSvc.updateSocialPost(socialPostVO);

		model.addAttribute("success", "- (修改成功)");
		socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(socialPostVO.getSpid()));
		model.addAttribute("socialPostVO", socialPostVO);

		return "redirect:/socialpost/" + uId;
	}

	// 更新使用者資料
	@PostMapping("updateUserProfile")
	public String updateUserProfile(@Valid UserVO userVO, BindingResult result, ModelMap model) throws IOException {

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
		boolean isprofileSuccess = userSvc.updateUserProfile(userVO);
//		Integer uId = userVO.getuId();
//		CoachVO coachVO = coachSvc.getOneCoach(uId);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("isprofileSuccess", userVO);
//		model.addAttribute("coachVO", coachVO);
		model.addAttribute("isprofileSuccess", isprofileSuccess);
		return "front-end/socialpost/student_settings"; // 修改成功後轉交listOneEmp.html
	}

	// 更新狀態 以表示刪除
	@PostMapping("update_for_delete")
	public String update_for_delete(@Valid SocialPostVO socialPostVO, BindingResult result, ModelMap model,
			@RequestParam Integer spStatus, @RequestParam Integer spid) throws IOException {

		socialPostVO = socialPostSvc.getOneSocialPost(spid);
		socialPostVO.setSpstatus(spStatus);
		socialPostSvc.updateSocialPost(socialPostVO);

		model.addAttribute("success", "- (修改成功)");
		socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(socialPostVO.getSpid()));
		model.addAttribute("socialPostVO", socialPostVO);

		return "redirect:/socialpost/";
	}

	@PostMapping("delete")
	public String delete(@RequestParam("spid") String spid, ModelMap model) {

		socialPostSvc.deleteSocialPost(Integer.valueOf(spid));

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
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(socialPostVO, "socialPostVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

	@ModelAttribute("userListData")
	protected List<UserVO> userReferenceListData() {

		List<UserVO> list = userSvc.getAll();

		return list;
	}

//	@PostMapping("/uHeadshot/update")
//	public String updateHeadshot(@RequestParam("file") MultipartFile file, @RequestParam("userId") Integer userId,
//			RedirectAttributes redirectAttributes) {
//
//		if(!file.isEmpty()) {
//			
//				try {
//					byte[] bytes = file.getBytes();
//					UserVO user = userSvc.getUser(userId);
//					if(user != null) {
//						user.setuHeadshot(bytes);
//						userSvc.updateUserHeadshot(null)
//						
//					}else {
//						redirectAttributes.addFlashAttribute("message","找不到用戶!");
//					}
//					
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//		}else {
//			redirectAttributes.addFlashAttribute("message","沒有收到更新頭貼");
//			
//		}
//		
//		
//		return "redirect:/socialpost/student_settings";
//	}

	@GetMapping("/settings")
	public String user_settings() {

		return "front-end/socialpost/student_settings";
	}

	/* =================取值======================== */
	@ModelAttribute("uName")
	public String getuName(Integer uId) {
		uId = 10001;
		String uName = userSvc.getUser(uId).getuName();
		return uName;
	}

	@ModelAttribute("userVO")
	public UserVO getUser(Integer uId) {
		uId = 10001;
		UserVO userVO = userSvc.getUser(uId);
		return userVO;
	}

}
