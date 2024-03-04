package com.fitanywhere.courseqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="course_qa")
public class CourseQAVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="qa_id")
	private Integer qaId;
	
	@Column(name="qa_status")
	private Integer qaStatus;
	
	@Column(name="qa_title", columnDefinition = "longtext")
	private String qaTitle;
	
	@Column(name="u_id")
	private Integer uId;
	
	@Column(name="qa_date")
	private Timestamp qaDate;
	
	@Column(name="qa_photo", columnDefinition = "longblob")
	private byte[] qaPhoto;
	
	@Column(name="cr_id")
	private Integer crId;

	public CourseQAVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseQAVO(Integer qaId, Integer qaStatus, String qaTitle, Integer uId, Timestamp qaDate, byte[] qaPhoto,
			Integer crId) {
		super();
		this.qaId = qaId;
		this.qaStatus = qaStatus;
		this.qaTitle = qaTitle;
		this.uId = uId;
		this.qaDate = qaDate;
		this.qaPhoto = qaPhoto;
		this.crId = crId;
	}

	@Override
	public String toString() {
		return "CourseQAVO [qaId=" + qaId + ", qaStatus=" + qaStatus + ", qaTitle=" + qaTitle + ", uId=" + uId
				+ ", qaDate=" + qaDate + ", qaPhoto=" + Arrays.toString(qaPhoto) + ", crId=" + crId + "]";
	}

	public Integer getQaId() {
		return qaId;
	}

	public void setQaId(Integer qaId) {
		this.qaId = qaId;
	}

	public Integer getQaStatus() {
		return qaStatus;
	}

	public void setQaStatus(Integer qaStatus) {
		this.qaStatus = qaStatus;
	}

	public String getQaTitle() {
		return qaTitle;
	}

	public void setQaTitle(String qaTitle) {
		this.qaTitle = qaTitle;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public Timestamp getQaDate() {
		return qaDate;
	}

	public void setQaDate(Timestamp qaDate) {
		this.qaDate = qaDate;
	}

	public byte[] getQaPhoto() {
		return qaPhoto;
	}

	public void setQaPhoto(byte[] qaPhoto) {
		this.qaPhoto = qaPhoto;
	}

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
