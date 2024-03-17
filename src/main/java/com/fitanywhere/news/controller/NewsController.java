package com.fitanywhere.news.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fitanywhere.news.model.NewsService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.fitanywhere.news.model.NewsVO;
import com.fitanywhere.user.model.UserVO;
import com.fitanywhere.user.model.UserService;


@Controller
public class NewsController {
	
	@Autowired
	NewsService newsSvc;
	
	@Autowired
	UserService userSvc;
	
	
	@GetMapping("/backend_news")
	public String backend_news(Model model)  {
		
		NewsVO newsVO = new NewsVO();
	    model.addAttribute("newsVO", newsVO);

		
        return "back-end/backend_news"; 
    }
	
	
	@PostMapping("/backend_news/add")
	public String add_news(@Valid NewsVO newsVO,BindingResult result,  ModelMap model) {
		
		

		newsSvc.addNews(newsVO);

		
		return "redirect:/backend_news";
	}
	
	@GetMapping("/news/count")
	@ResponseBody
	public int getNewsCount() {
		return newsSvc.getNewsCount();
		
	}
	
	@GetMapping("/socialpost/news")
	public String news(HttpServletRequest req, Model model) {
		
		HttpSession newSession = req.getSession(true);
		
		int sessionUId =  Integer.valueOf(newSession.getAttribute("uId").toString());
		// 透過取得 session 存的 user ID 來取得 UserVO
		UserVO userVO = userSvc.getUserDataByID(sessionUId);
		model.addAttribute("userVO", userVO);
		
		return "front-end/news/news";
	}	
	
	
	// 秀出所有最新消息
	@ModelAttribute("newsListData")
	public List<NewsVO> newsListData() {
		
		List<NewsVO> list = newsSvc.getAllNews();
		
        return list; 
    }
	
	// 秀出不超過今天的所有最新消息
	@ModelAttribute("newsListMember")
	public List<NewsVO> newsListBeforeToday() {
		
		List<NewsVO> list = newsSvc.getAllNewsBeforeToday();
		
        return list; 
    }

}
