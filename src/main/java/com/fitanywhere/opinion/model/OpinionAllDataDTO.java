package com.fitanywhere.opinion.model;

import java.util.Date;

public class OpinionAllDataDTO {

	// OpinionVO的屬性
	private Integer opId;
	private String opTitle;
	private String opContent;
	private Date opSendTime;
	private String opReplyContent;
	private Date opReplyTime;
	private Integer opStatus;

	// UserVO的部分屬性
	private Integer uId;
	private String uNickname;

	// Constructor
	public OpinionAllDataDTO() {
	}

	public OpinionAllDataDTO(Integer opId, String opTitle, String opContent, Date opSendTime, String opReplyContent,
			Date opReplyTime, Integer opStatus, Integer uId, String uNickname) {
		this.opId = opId;
		this.opTitle = opTitle;
		this.opContent = opContent;
		this.opSendTime = opSendTime;
		this.opReplyContent = opReplyContent;
		this.opReplyTime = opReplyTime;
		this.opStatus = opStatus;
		this.uId = uId;
		this.uNickname = uNickname;
	}

	public Integer getOpId() {
		return opId;
	}

	public String getOpTitle() {
		return opTitle;
	}

	public String getOpContent() {
		return opContent;
	}

	public Date getOpSendTime() {
		return opSendTime;
	}

	public String getOpReplyContent() {
		return opReplyContent;
	}

	public Date getOpReplyTime() {
		return opReplyTime;
	}

	public Integer getOpStatus() {
		return opStatus;
	}

	public Integer getuId() {
		return uId;
	}

	public String getuNickname() {
		return uNickname;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public void setOpTitle(String opTitle) {
		this.opTitle = opTitle;
	}

	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}

	public void setOpSendTime(Date opSendTime) {
		this.opSendTime = opSendTime;
	}

	public void setOpReplyContent(String opReplyContent) {
		this.opReplyContent = opReplyContent;
	}

	public void setOpReplyTime(Date opReplyTime) {
		this.opReplyTime = opReplyTime;
	}

	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}	

}
