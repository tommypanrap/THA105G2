package com.fitanywhere.discount.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import groovy.transform.builder.InitializerStrategy.SET;

@Entity
@Table(name = "discount")
public class DiscountVO {
	
	private Integer dcId;
	private Timestamp dcStart;
	private Timestamp dcEnd;
	private Integer dcProject;
	private Integer dcStatus;

	
	public DiscountVO() {
		super();
	}

	public DiscountVO(Integer dcId, Timestamp dcStart, Timestamp dcEnd, Integer dcProject, Integer dcStatus) {
		super();
		this.dcId = dcId;
		this.dcStart = dcStart;
		this.dcEnd = dcEnd;
		this.dcProject = dcProject;
		this.dcStatus = dcStatus;
	}

	@Id
	@Column(name = "dc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getDcId() {
		return dcId;
	}

	public void setDcId(Integer dcId) {
		this.dcId = dcId;
	}
	
	
	@Column(name = "dc_start")
	public Timestamp getDcStart() {
		return dcStart;
	}

	public void setDcStart(Timestamp dcStart) {
		this.dcStart = dcStart;
	}

	@Column(name = "dc_end")
	public Timestamp getDcEnd() {
		return dcEnd;
	}

	public void setDcEnd(Timestamp dcEnd) {
		this.dcEnd = dcEnd;
	}

	@Column(name = "dc_project")
	public Integer getDcProject() {
		return dcProject;
	}

	public void setDcProject(Integer dcProject) {
		this.dcProject = dcProject;
	}

	@Column(name = "dc_status")
	public Integer getDcStatus() {
		return dcStatus;
	}

	public void setDcStatus(Integer dcStatus) {
		this.dcStatus = dcStatus;
	}

}
