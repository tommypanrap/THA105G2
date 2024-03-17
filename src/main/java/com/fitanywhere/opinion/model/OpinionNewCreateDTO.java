package com.fitanywhere.opinion.model;

import java.util.Date;

public class OpinionNewCreateDTO {
	// OpinionVO的屬性
	private String opTitle;
	private String opContent;

	// UserVO的部分屬性
	private Integer uId;

	public OpinionNewCreateDTO() {
	}

	public OpinionNewCreateDTO(String opTitle, String opContent, Integer uId) {
		this.opTitle = opTitle;
		this.opContent = opContent;
		this.uId = uId;
	}

	public String getOpTitle() {
		return opTitle;
	}

	public String getOpContent() {
		return opContent;
	}

	public Integer getuId() {
		return uId;
	}

	public void setOpTitle(String opTitle) {
		this.opTitle = opTitle;
	}

	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}	

}
