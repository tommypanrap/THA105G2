package com.fitanywhere.forumreply.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.forumreply.model.ForumReplyService;
import com.fitanywhere.forumreply.model.ForumReplyVO;

@Controller
@Validated
@RequestMapping("/forumreply")
public class FrIdController {

	@Autowired
	ForumReplyService forumReplySvc;

	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "會員帳號: 請勿空白") @Digits(integer = 100, fraction = 0, message = "會員編號: 請填數字-請勿超過{integer}位數") @RequestParam("frId") String frId,
			ModelMap model) {

		ForumReplyVO forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(frId));

			List<ForumReplyVO> list = forumReplySvc.getAll();
			model.addAttribute("forumReplyListData", list);

			model.addAttribute("ForumReplyVO", forumReplyVO);
			model.addAttribute("getOne_For_Display", "true");

			// 贴文不存在，設置錯誤訊息
			model.addAttribute("errorMessage", "查無資料");
			return "front-end/forumpost/select_page";
		
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	// @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		List<ForumReplyVO> list = forumReplySvc.getAll();
		model.addAttribute("forumReplyListData", list); // for select_page.html 第行用

		String message = strBuilder.toString();
		return new ModelAndView("front-end/forumreply/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}

}