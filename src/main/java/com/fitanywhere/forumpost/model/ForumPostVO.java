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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fitanywhere.user.model.UserVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "forum_post")
@Data
@NoArgsConstructor
public class ForumPostVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "u_id", referencedColumnName = "u_id")
	private UserVO userVO;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fp_id")
	private Integer fpId;

	@Column(name = "fp_category")
	private String fpCategory;

	@Column(name = "fp_title")
	private String fpTitle;

	@Column(name = "fp_content", columnDefinition = "longtext")
	private String fpContent;

	@Column(name = "fp_status")
	private Integer fpStatus;

	@Column(name = "fp_time", updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp fpTime;

	@Column(name = "fp_update")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp fpUpdate;

	@Column(name = "fp_pic", columnDefinition = "longblob")
	private byte[] fpPic;

	@Column(name = "fp_views")
	private Integer fpViews;

	public ForumPostVO(UserVO userVO, Integer fpId, String fpCategory, String fpTitle, String fpContent,
			Integer fpStatus, Timestamp fpTime, Timestamp fpUpdate, byte[] fpPic, Integer fpViews) {
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
	}

	@PrePersist
	protected void onCreate() {
		fpTime = new Timestamp(System.currentTimeMillis());
		fpUpdate = fpTime;
		if (this.fpStatus == null || this.fpStatus == 1) {
			this.fpStatus = 1; // 默認貼文狀態存活
		}
		if (this.fpViews == null) {
			this.fpViews = 0; // 默認觀看數為0
		}
		if (this.fpPic == null || this.fpPic.length == 0) {
			this.fpPic = java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPicBase64()); // 如果USER沒設置圖片則預設一張圖片
		}
	}

	@PreUpdate
	protected void onUpdate() {
		fpUpdate = new Timestamp(System.currentTimeMillis());
		if (this.fpStatus == null || this.fpStatus == 1) {
			this.fpStatus = 1;
		}
	}

}
