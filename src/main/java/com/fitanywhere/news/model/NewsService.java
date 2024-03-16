package com.fitanywhere.news.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class NewsService {
	@Autowired
	NewsRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

}
