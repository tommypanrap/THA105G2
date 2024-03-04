package com.fitanywhere.mood.model;

import java.util.HashSet;
import java.util.Set;

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

import com.fitanywhere.socialpost.model.SocialPostVO;
import com.fitanywhere.user.model.UserVO;

@Entity
@Table(name = "mood")
public class MoodVO {
	
	
	@Id
	@Column(name = "mood_id")
	private Integer moodId;
	
	@Column(name = "mood_photo",columnDefinition = "longblob")
	private byte[] moodePhoto;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="moodVO")
	private Set<UserVO> userVO = new HashSet<UserVO>();

	public Integer getMoodId() {
		return moodId;
	}

	public void setMoodId(Integer moodId) {
		this.moodId = moodId;
	}

	public byte[] getMoodePhoto() {
		return moodePhoto;
	}

	public void setMoodePhoto(byte[] moodePhoto) {
		this.moodePhoto = moodePhoto;
	}

	public MoodVO() {
		
	}
	
	
}
