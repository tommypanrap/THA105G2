package com.fitanywhere.news.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import com.fitanywhere.news.model.NewsVO;

public interface NewsRepository extends JpaRepository<NewsVO, Integer> {

}
