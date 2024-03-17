package com.fitanywhere.news.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import com.fitanywhere.news.model.NewsVO;

public interface NewsRepository extends JpaRepository<NewsVO, Integer> {

	@Query("SELECT count(n) FROM NewsVO n WHERE n.newsDate <= CURRENT_DATE")
    int countNewsAfterToday();
	
	
	@Query("SELECT n FROM NewsVO n WHERE n.newsDate <= CURRENT_DATE")
	List<NewsVO> findNewsBeforeToday();

}
