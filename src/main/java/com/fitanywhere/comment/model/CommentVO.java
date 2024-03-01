package com.fitanywhere.comment.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "comment")
public class CommentVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_id")
	private Integer cmId;
	@Column(name = "cr_id")
	private Integer crId;
	@Column(name = "cm_content", columnDefinition = "longtext")
	private String cmContent;
	@Column(name = "cm_date")
	private Timestamp cmDate;
	@Column(name = "cm_star")
	private Integer cmStar;
	@Column(name = "cm_status")
	private Integer cmStatus;
	@Column(name = "u_id")
	private Integer uId;
	
	public CommentVO() {
		super();
	}

	public CommentVO(Integer cmId, Integer crId, String cmContent, Timestamp cmDate, Integer cmStar, Integer cmStatus,
			Integer uId) {
		super();
		this.cmId = cmId;
		this.crId = crId;
		this.cmContent = cmContent;
		this.cmDate = cmDate;
		this.cmStar = cmStar;
		this.cmStatus = cmStatus;
		this.uId = uId;
	}

	@Override
	public String toString() {
		return "CommentVO [cmId=" + cmId + ", crId=" + crId + ", cmContent=" + cmContent + ", cmDate=" + cmDate
				+ ", cmStar=" + cmStar + ", cmStatus=" + cmStatus + ", uId=" + uId + "]";
	}

	public Integer getCmId() {
		return cmId;
	}

	public void setCmId(Integer cmId) {
		this.cmId = cmId;
	}

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public String getCmContent() {
		return cmContent;
	}

	public void setCmContent(String cmContent) {
		this.cmContent = cmContent;
	}

	public Timestamp getCmDate() {
		return cmDate;
	}

	public void setCmDate(Timestamp cmDate) {
		this.cmDate = cmDate;
	}

	public Integer getCmStar() {
		return cmStar;
	}

	public void setCmStar(Integer cmStar) {
		this.cmStar = cmStar;
	}

	public Integer getCmStatus() {
		return cmStatus;
	}

	public void setCmStatus(Integer cmStatus) {
		this.cmStatus = cmStatus;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
