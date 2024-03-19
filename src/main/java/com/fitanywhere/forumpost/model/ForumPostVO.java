package com.fitanywhere.forumpost.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fitanywhere.forumreply.model.ForumReplyVO;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userVO")
	@OrderBy("u_id asc") // asc = 根據指定的欄位排序
	private Set<ForumReplyVO> forumReply = new HashSet<ForumReplyVO>();

	public Set<ForumReplyVO> getForumReply() {
		return forumReply;
	}

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
			this.fpStatus = 1; // 預設貼文狀態存活
		}
		if (this.fpViews == null) {
			this.fpViews = 0; // 預設觀看數為0
		}
	}

	@PreUpdate
	protected void onUpdate() {
		fpUpdate = new Timestamp(System.currentTimeMillis());
		if (this.fpStatus == null || this.fpStatus == 1) {
			this.fpStatus = 1;
		}
	}
	
	public byte[] convertBase64ToPic(String base64Pic) {
        if (base64Pic != null && !base64Pic.isEmpty()) {
            return Base64.getDecoder().decode(base64Pic);
        } else {
            return null;
        }
    }

}
