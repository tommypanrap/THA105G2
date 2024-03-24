package com.fitanywhere.mood.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoodService {
	
	@Autowired
	MoodRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public MoodVO getMoodVO(Integer moodId) {
		return repository.getById(moodId);
	}
	
}
