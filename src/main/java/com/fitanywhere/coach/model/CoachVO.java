package com.fitanywhere.coach.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "coach")
public class CoachVO implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Integer cId;

	@Column(name = "u_id")
	private Integer uId;

	@Column(name = "c_intro",columnDefinition = "longtext")
	private String cIntro;

	@Column(name = "c_certification",columnDefinition = "longtext")
	private String cCertification;

	@Column(name = "c_skill",columnDefinition = "longtext")
	private String cSkill;

	// Constructor
	public CoachVO() {
		// Default constructor
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getcIntro() {
		return cIntro;
	}

	public void setcIntro(String cIntro) {
		this.cIntro = cIntro;
	}

	public String getcCertification() {
		return cCertification;
	}

	public void setcCertification(String cCertification) {
		this.cCertification = cCertification;
	}

	public String getcSkill() {
		return cSkill;
	}

	public void setcSkill(String cSkill) {
		this.cSkill = cSkill;
	}

	
}
	

	

