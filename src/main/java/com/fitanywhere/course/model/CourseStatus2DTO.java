package com.fitanywhere.course.model;

import java.util.Base64;

public class CourseStatus2DTO {
	private String crClass;
    private String crTitle;
    private Integer crId;
    private Integer crPrice;
    
    
	public CourseStatus2DTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CourseStatus2DTO(String crClass, String crTitle, Integer crId, Integer crPrice) {
		super();
		this.crClass = crClass;
		this.crTitle = crTitle;
		this.crId = crId;
		this.crPrice = crPrice;
	}


	public String getCrClass() {
		return crClass;
	}

	public void setCrClass(String crClass) {
		this.crClass = crClass;
	}

	public String getCrTitle() {
		return crTitle;
	}

	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}


	public Integer getCrId() {
		return crId;
	}


	public void setCrId(Integer crId) {
		this.crId = crId;
	}


	public Integer getCrPrice() {
		return crPrice;
	}

	public void setCrPrice(Integer crPrice) {
		this.crPrice = crPrice;
	}
}
