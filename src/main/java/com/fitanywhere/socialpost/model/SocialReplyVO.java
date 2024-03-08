package com.fitanywhere.socialpost.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fitanywhere.user.model.UserVO;
import com.fitanywhere.mood.model.MoodVO;
import com.fitanywhere.socialpost.model.SocialPostVO;


@Entity
@Table(name = "social_reply")
public class SocialReplyVO {
	
	@Id
	@Column(name = "sr_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer srId;
	
	@ManyToOne
	@JoinColumn(name = "u_id")
	private UserVO userVO;
	
	@ManyToOne
	@JoinColumn(name = "sp_id")
	private SocialPostVO socialPostVO;
	
	@Column(name = "sr_time")
	private Timestamp srTime;
	
	@Column(name = "sr_update")
	private Timestamp srUpdate;
	
	@Column(name = "sr_status")
	private Integer srStatus;
	
	@Column(name = "sr_content" , columnDefinition = "longtext")
	private String srContent;

	public Integer getSrId() {
		return srId;
	}

	public void setSrId(Integer srId) {
		this.srId = srId;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public SocialPostVO getSocialPostVO() {
		return socialPostVO;
	}

	public void setSocialPostVO(SocialPostVO socialPostVO) {
		this.socialPostVO = socialPostVO;
	}

	public Timestamp getSrTime() {
		return srTime;
	}

	public void setSrTime(Timestamp srTime) {
		this.srTime = srTime;
	}

	public Timestamp getSrUpdate() {
		return srUpdate;
	}

	public void setSrUpdate(Timestamp srUpdate) {
		this.srUpdate = srUpdate;
	}

	public Integer getSrStatus() {
		return srStatus;
	}

	public void setSrStatus(Integer srStatus) {
		this.srStatus = srStatus;
	}

	public String getSrContent() {
		return srContent;
	}

	public void setSrContent(String srContent) {
		this.srContent = srContent;
	}

	public SocialReplyVO() {
		super();
	}
	

	

}
