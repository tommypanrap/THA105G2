package com.fitanywhere.opinion.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.opinion.model.OpinionAllDataDTO;
import com.fitanywhere.opinion.model.OpinionService;

@Controller
public class OpinionController {

	@Autowired
	private OpinionService opinionService;
	
	// 前端會員的意見列表
	@GetMapping("/opinion/user_opinion_list")
	public String frontEndOpinion(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
		// 從Session取得會員資料
		HttpSession session = request.getSession(false);
		Integer uId = null;
		if (session != null && session.getAttribute("loginStatus") != null) {
			uId = (Integer) session.getAttribute("uId");
		} else {
			return "redirect:/user/force_user_login";
		}
		
		// 取回用分頁型態分割的所有資料
		Pageable pageable = PageRequest.of(page, size);
		Page<OpinionAllDataDTO> opinionPage = opinionService.getAllOpinionsWithSelectedUser(pageable, uId);

		model.addAttribute("opinionPage", opinionPage);
		return "front-end/opinion/student_opinion";
	}
	
	// 後端會員意見一覽表
	@GetMapping("/backend_opinion")
	public String listAllOpinions(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<OpinionAllDataDTO> opinionPage = opinionService.getAllOpinionsWithUsers(pageable);

		model.addAttribute("opinionPage", opinionPage);
		return "back-end/backend_opinion";
	}

}
