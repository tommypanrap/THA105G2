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
@Table(name = "forum_reply")
@Data
@NoArgsConstructor
public class ForumReplyVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "u_id", referencedColumnName = "u_id")
	private UserVO userVO;

	@ManyToOne
	@JoinColumn(name = "fp_id", referencedColumnName = "fp_id")
	private ForumPostVO forumPostVO;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fr_id")
	private Integer frId;

	@Column(name = "fr_content", columnDefinition = "longtext")
	private String frContent;

	@Column(name = "fr_status")
	private Integer frStatus;

	@Column(name = "fr_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp frTime;

	@Column(name = "fr_update")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp frUpdate;

	@Column(name = "fr_pic", columnDefinition = "longblob")
	private byte[] frPic;

	public ForumReplyVO(UserVO userVO, ForumPostVO forumPostVO, Integer frId, String frContent, Integer frStatus,
			Timestamp fpTime, Timestamp frUpdate, byte[] frPic) {
		super();
		this.userVO = userVO;
		this.forumPostVO = forumPostVO;
		this.frId = frId;
		this.frContent = frContent;
		this.frStatus = frStatus;
		this.frTime = fpTime;
		this.frUpdate = frUpdate;
		this.frPic = frPic;
	}

	@PrePersist
	protected void onCreate() {
		frTime = new Timestamp(System.currentTimeMillis());
		frUpdate = frTime;
		if (this.frStatus == null || this.frStatus == 1) {
			this.frStatus = 1; // 默認貼文狀態存活
		}
	}

	@PreUpdate
	protected void onUpdate() {
		frUpdate = new Timestamp(System.currentTimeMillis());
		if (this.frStatus == null || this.frStatus == 1) {
			this.frStatus = 1;
		}
	}
}