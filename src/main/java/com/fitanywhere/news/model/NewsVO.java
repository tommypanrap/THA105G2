package com.fitanywhere.news.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class NewsVO {
	
	@Id
	@Column(name = "news_id")
	private Integer newsId;
	
	@Column(name = "news")
	private String news;
	
	@Column(name= "news_content")
	private String newsContent;
	
	@Column(name= "news_date")
	private Timestamp newsDate;

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public Timestamp getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Timestamp newsDate) {
		this.newsDate = newsDate;
	}

	public NewsVO() {
		super();
	}
	
	

}
