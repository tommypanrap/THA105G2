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
import com.fitanywhere.socialpost.model.SocialReplyVO;
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

	@GetMapping("student_socialpost")
	public String getUserInfo(HttpServletRequest req, ModelMap model) {
		
		
//		model.addAttribute("matchingUsers", matchingUsers);
		
		SocialReplyVO socialReplyVO = new SocialReplyVO();
		model.addAttribute("SocialReplyVO", socialReplyVO);

		HttpSession newSession = req.getSession(true);

		UserVO userVO = userSvc.getUserDataByID(Integer.valueOf(newSession.getAttribute("uId").toString()));
		model.addAttribute("userVO", userVO);
		
//		測試資料印出用
//		for (SocialPostVO socialPost : userVO.getSocialposts()) {
//			if (socialPost.getSpstatus().equals(1)) {
//				System.out.println("SocialPost Spid: " + socialPost.getSpid());
//				System.out.println("SocialPost Title: " + socialPost.getSptitle());
//				System.out.println("SocialPost Content: " + socialPost.getSpcontent());
//
//				for (SocialReplyVO socialReply : socialPost.getSocialReplys()) {
//					System.out.println("123 SocialReply Content: " + socialReply.getSrContent());
//				}
//			}
//		}

		SocialPostVO socialPostVO = new SocialPostVO();
		model.addAttribute("socialPostVO", socialPostVO);

		List<UserVO> matchingUsers = (List<UserVO>) req.getSession().getAttribute("matchingUsers");
	    model.addAttribute("matchingUsers", matchingUsers);
		
		
		return "front-end/socialpost/student_socialpost";
	}
	
	@PostMapping("search_social_member")
	public String search_social_member(@RequestParam String searchValue, ModelMap model, HttpSession session) throws IOException  {
		
//		System.out.println(searchValue);
		
		List<UserVO> matchingUsers = userSvc.searchUsersByNickname(searchValue);
		System.out.println(matchingUsers);
		
		session.setAttribute("matchingUsers", matchingUsers);
		
		return "redirect:/socialpost/student_socialpost";
	}

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

		socialPostSvc.addSocialPost(socialPostVO);

		List<SocialPostVO> list = socialPostSvc.getAll();
		model.addAttribute("socialPostListData", list);
		model.addAttribute("success", "- (新增成功)");

		return "redirect:/socialpost/student_socialpost";
	}

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("spid") String spid, ModelMap model) {

		SocialPostVO socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(spid));

		model.addAttribute("socialPostVO", socialPostVO);

		return "front-end/socialpost/update_socialpost_input";
	}

	@PostMapping("update_social_post")
	public String update_social_post(@Valid SocialPostVO socialPostVO, BindingResult result, ModelMap model,
			@RequestParam("sppic") MultipartFile[] parts) throws IOException {

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
			return "redirect:/socialpost/student_socialpost";
		}
		SocialPostVO getSocialPostVO = socialPostSvc.getOneSocialPost(socialPostVO.getSpid());

		socialPostVO.setUserVO(getSocialPostVO.getUserVO());
		socialPostVO.setSptime(getSocialPostVO.getSptime());
		socialPostVO.setSpstatus(getSocialPostVO.getSpstatus());

		socialPostSvc.updateSocialPost(socialPostVO);

		model.addAttribute("success", "- (修改成功)");
		socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(socialPostVO.getSpid()));
		model.addAttribute("socialPostVO", socialPostVO);

		return "redirect:/socialpost/student_socialpost";
	}

	@PostMapping("update_for_delete")
	public String update_for_delete(@Valid SocialPostVO socialPostVO, BindingResult result, ModelMap model,
			@RequestParam Integer spStatus, @RequestParam Integer spid) throws IOException {

		socialPostVO = socialPostSvc.getOneSocialPost(spid);
		socialPostVO.setSpstatus(spStatus);
		socialPostSvc.updateSocialPost(socialPostVO);

		model.addAttribute("success", "- (修改成功)");
		socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(socialPostVO.getSpid()));
		model.addAttribute("socialPostVO", socialPostVO);

		return "redirect:/socialpost/student_socialpost";
	}

	@PostMapping("update")
	public String update(@Valid SocialPostVO socialPostVO, BindingResult result, ModelMap model,
			@RequestParam("sppic") MultipartFile[] parts) throws IOException {

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
			return "front-end/socialpost/update_socialpost_input";
		}

		socialPostSvc.updateSocialPost(socialPostVO);

		model.addAttribute("success", "- (修改成功)");
		socialPostVO = socialPostSvc.getOneSocialPost(Integer.valueOf(socialPostVO.getSpid()));
		model.addAttribute("socialPostVO", socialPostVO);

		return "front-end/socialpost/list_one_socialpost";
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

}
