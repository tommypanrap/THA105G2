package com.fitanywhere.forumpost.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.user.model.UserService;

@Controller
@Validated
@RequestMapping("/forumpost")
public class FpIdController {

	@Autowired
	ForumPostService forumPostSvc;
	
	@Autowired
	UserService userSvc;
		
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "會員帳號: 請勿空白") @Digits(integer = 100, fraction = 0, message = "會員編號: 請填數字-請勿超過{integer}位數") @RequestParam("fpId") String fpId,
			ModelMap model) {

		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));

		if (forumPostVO != null) {
			// 增加貼文觀看次數
			forumPostVO.setFpViews(forumPostVO.getFpViews() + 1);
			// 更新觀看次數
			forumPostSvc.updateForumPost(forumPostVO);

			List<ForumPostVO> list = forumPostSvc.getAll();
			model.addAttribute("forumPostListData", list);

			model.addAttribute("ForumPostVO", forumPostVO);
			model.addAttribute("getOne_For_Display", "true");

			return "front-end/forumpost/select_page"; 
		} else {
			// 贴文不存在，設置錯誤訊息
			model.addAttribute("errorMessage", "查無資料");
			return "front-end/forumpost/select_page";
		}
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	// @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		List<ForumPostVO> list = forumPostSvc.getAll();
		model.addAttribute("forumPostListData", list); // for select_page.html 第行用

		String message = strBuilder.toString();
		return new ModelAndView("front-end/forumpost/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}

}