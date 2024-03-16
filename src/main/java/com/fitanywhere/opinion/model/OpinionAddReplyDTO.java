package com.fitanywhere.opinion.model;

import java.util.Date;

public class OpinionAddReplyDTO {
	private Integer opId;
	private String opReplyContent;
	private Date opReplyTime;
	private Integer opStatus;

	public OpinionAddReplyDTO() {
	}

	public OpinionAddReplyDTO(Integer opId, String opReplyContent, Date opReplyTime, Integer opStatus) {
		this.opId = opId;
		this.opReplyContent = opReplyContent;
		this.opReplyTime = opReplyTime;
		this.opStatus = opStatus;
	}

	public Integer getOpId() {
		return opId;
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

	public void setOpId(Integer opId) {
		this.opId = opId;
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

}
