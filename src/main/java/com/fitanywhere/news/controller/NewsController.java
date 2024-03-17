package com.fitanywhere.news.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fitanywhere.news.model.NewsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fitanywhere.news.model.NewsVO;


@Controller
public class NewsController {
	
	@Autowired
	NewsService newsSvc;
	
	@GetMapping("/backend_news")
	public String backend_news(Model model)  {
		
		
		
        return "back-end/backend_news"; 
    }
	
	@ModelAttribute("newsListData")
	public List<NewsVO> newsListData() {
		
		List<NewsVO> list = newsSvc.getAllNews();
		
        return list; 
    }

}
