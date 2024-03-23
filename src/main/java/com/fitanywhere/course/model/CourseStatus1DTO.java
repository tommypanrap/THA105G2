package com.fitanywhere.course.model;

import java.sql.Timestamp;
import java.util.Base64;

public class CourseStatus1DTO {
	private String crTitle;
	private String crClass;
	private Integer crId;
	private Integer crTotStar;
	private Integer crCmQuan;
	private Integer crPrice;
	private Timestamp crCreateDate;
	
	public CourseStatus1DTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	public CourseStatus1DTO(String crTitle, String crClass, Integer crId, Integer crTotStar, Integer crCmQuan,
			Integer crPrice, Timestamp crCreateDate) {
		super();
		this.crTitle = crTitle;
		this.crClass = crClass;
		this.crId = crId;
		this.crTotStar = crTotStar;
		this.crCmQuan = crCmQuan;
		this.crPrice = crPrice;
		this.crCreateDate = crCreateDate;
	}





	public String getCrTitle() {
		return crTitle;
	}

	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}

	public String getCrClass() {
		return crClass;
	}

	public void setCrClass(String crClass) {
		this.crClass = crClass;
	}


	public Integer getCrId() {
		return crId;
	}





	public void setCrId(Integer crId) {
		this.crId = crId;
	}





	public Integer getCrTotStar() {
		return crTotStar;
	}

	public void setCrTotStar(Integer crTotStar) {
		this.crTotStar = crTotStar;
	}

	public Integer getCrCmQuan() {
		return crCmQuan;
	}

	public void setCrCmQuan(Integer crCmQuan) {
		this.crCmQuan = crCmQuan;
	}

	public Integer getCrPrice() {
		return crPrice;
	}

	public void setCrPrice(Integer crPrice) {
		this.crPrice = crPrice;
	}


	public Timestamp getCrCreateDate() {
		return crCreateDate;
	}


	public void setCrCreateDate(Timestamp crCreateDate) {
		this.crCreateDate = crCreateDate;
	}
	
	
}
