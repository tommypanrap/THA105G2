package com.fitanywhere.forumpost.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fitanywhere.user.model.UserVO;

@Entity
@Table(name= "forum_post")
//@Where(clause = "fp_status = 1")
public class ForumPostVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "u_id")
	private UserVO userVO;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fp_id")
	private Integer fpId;
	
//	private Integer uId;
		
	@Column(name = "fp_category")
	private String fpCategory;
	
	@Column(name = "fp_title")
	private String fpTitle;
	
	@Column(name = "fp_content", columnDefinition = "longtext")
	private String fpContent;
	
	@Column(name = "fp_status")
	private Boolean fpStatus;
	
	@Column(name = "fp_time")
//	@CreatedDate
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp fpTime;
	
	@Column(name = "fp_update")
//	@LastModifiedDate 								
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp fpUpdate;
	
	@Column(name = "fp_pic", columnDefinition = "longblob")
	private byte[] fpPic;
	
	@Column(name = "fp_views")
	private Integer fpViews;

	@Column(name = "fp_favorite")
	private Boolean fpFavorite;

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public String getFpCategory() {
		return fpCategory;
	}

	public void setFpCategory(String fpCategory) {
		this.fpCategory = fpCategory;
	}

	public String getFpTitle() {
		return fpTitle;
	}

	public void setFpTitle(String fpTitle) {
		this.fpTitle = fpTitle;
	}

	public String getFpContent() {
		return fpContent;
	}

	public void setFpContent(String fpContent) {
		this.fpContent = fpContent;
	}

	public Boolean getFpStatus() {
		return fpStatus;
	}

	public void setFpStatus(Boolean fpStatus) {
		this.fpStatus = fpStatus;
	}

	public Timestamp getFpTime() {
		return fpTime;
	}

	public void setFpTime(Timestamp fpTime) {
		this.fpTime = fpTime;
	}

	public Timestamp getFpUpdate() {
		return fpUpdate;
	}

	public void setFpUpdate(Timestamp fpUpdate) {
		this.fpUpdate = fpUpdate;
	}

	public byte[] getFpPic() {
		return fpPic;
	}

	public void setFpPic(byte[] fpPic) {
		this.fpPic = fpPic;
	}

	public Integer getFpViews() {
		return fpViews;
	}

	public void setFpViews(Integer fpViews) {
		this.fpViews = fpViews;
	}

	public Boolean getFpFavorite() {
		return fpFavorite;
	}

	public void setFpFavorite(Boolean fpFavorite) {
		this.fpFavorite = fpFavorite;
	}

	public ForumPostVO(UserVO userVO, Integer fpId, String fpCategory, String fpTitle, String fpContent,
			Boolean fpStatus, Timestamp fpTime, Timestamp fpUpdate, byte[] fpPic, Integer fpViews, Boolean fpFavorite) {
		super();
		this.userVO = userVO;
		this.fpId = fpId;
		this.fpCategory = fpCategory;
		this.fpTitle = fpTitle;
		this.fpContent = fpContent;
		this.fpStatus = fpStatus;
		this.fpTime = fpTime;
		this.fpUpdate = fpUpdate;
		this.fpPic = fpPic;
		this.fpViews = fpViews;
		this.fpFavorite = fpFavorite;
	}

	public ForumPostVO() {
		super();
	}


	
	
}
