package com.fitanywhere.news.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.news.model.NewsVO;

@Service
public class NewsService {
	
	@Autowired
	NewsRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
   public List<NewsVO> getAllNews() {
        return repository.findAll();
    }
   
   public List<NewsVO> getAllNewsBeforeToday() {
       return repository.findNewsBeforeToday();
   }
   
	public void addNews(NewsVO newsVO) {
		repository.save(newsVO);
	}
	
	public int getNewsCount() {
		return repository.countNewsAfterToday();
	}

}
