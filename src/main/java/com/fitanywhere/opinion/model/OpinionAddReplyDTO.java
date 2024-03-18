package com.fitanywhere.opinion.model;

import java.time.LocalDateTime;

public class OpinionAddReplyDTO {
	private Integer opId;
	private String opReplyContent;
	private LocalDateTime opReplyTime;
	private Integer opStatus;

	public OpinionAddReplyDTO() {
	}

	public OpinionAddReplyDTO(Integer opId, String opReplyContent, LocalDateTime opReplyTime, Integer opStatus) {
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

	public LocalDateTime getOpReplyTime() {
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

	public void setOpReplyTime(LocalDateTime opReplyTime) {
		this.opReplyTime = opReplyTime;
	}

	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}

}
