package com.fitanywhere.forumreply.controller;

import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumreply.model.ForumReplyService;
import com.fitanywhere.forumreply.model.ForumReplyVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
public class ForumReplyController {

	@Autowired
	ForumReplyService forumReplySvc;

	@Autowired
	UserService userSvc;

	@Autowired
	ForumPostService forumPostSvc;

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("frId") String frId, ModelMap model) {
		/*************************** 2.開始查詢資料 *****************************************/
		ForumReplyVO forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(frId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("ForumReplyVO", forumReplyVO);
		return "front-end/forumreply/update_forumreply_input";
	}
	
	@PostMapping("/details/{fpId}/reply")
	 public String addForumReply(@RequestParam("fpId") int fpId, @ModelAttribute("newForumReply") ForumReplyVO newForumReply) {
//		 int postId = Integer.parseInt(fpId);
	     forumReplySvc.addForumReply(newForumReply, fpId);

	     // 重定向到貼文詳細頁面，用戶可以看到新留言已經添加
	     String redirectUrl = "redirect:/details?fpId=" + fpId;
	     return redirectUrl;
	 }

	@PostMapping("/details/{fpId}/updatereply")
	public String update(@Valid ForumReplyVO forumReplyVO, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes,
			@PathVariable("fpId") int fpId) throws IOException {

		forumReplyVO.setFrTime(forumReplySvc.getOriginalFrTime(forumReplyVO.getFrId()));

		forumReplySvc.updateForumReply(forumReplyVO, fpId);

		model.addAttribute("success", "- (修改成功)");
		forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(forumReplyVO.getFrId()));
		model.addAttribute("ForumReplyVO", forumReplyVO);
		return "redirect:/details/" + fpId;
	}

	@PostMapping("/details/{fpId}/deletereply")
	public String delete(@RequestParam("frId") String frId, @PathVariable("fpId") int fpId, ModelMap model) {

		ForumReplyVO forumReply = forumReplySvc.getOneForumReply(Integer.valueOf(frId));

		if (forumReply.getForumPostVO().getFpId() == fpId) {
			forumReplySvc.deleteForumReply(Integer.valueOf(frId));
		} else {
			return "redirect:/forumreply/listAllForumReply";
		}
		return "redirect:/details/" + fpId;
	}

	@GetMapping("/listAllForumReplies")
	public String listAllForumReplies(Model model) {
		List<ForumReplyVO> forumReplyListData = forumReplySvc.getAll();
		model.addAttribute("forumReplyListData", forumReplyListData);
		return "forumreply/listAllForumReplies"; // 返回视图名称
	}

	@GetMapping("select_page")
	public String select_page(Model model) {
		return "front-end/forumreply/select_page";
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ForumReplyVO forumReplyVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(forumReplyVO, "ForumReplyVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

//	====

	@ModelAttribute("forumReplyListData")
	protected List<ForumReplyVO> forumReplyListData() {
		List<ForumReplyVO> list = forumReplySvc.getAll();
		return list;
	}

	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData() {
		List<UserVO> list = userSvc.getAll();
		return list;
	}

	@ModelAttribute("ForumReplyVO")
	public ForumReplyVO getForumReplyVO() {
		return new ForumReplyVO();
	}
}