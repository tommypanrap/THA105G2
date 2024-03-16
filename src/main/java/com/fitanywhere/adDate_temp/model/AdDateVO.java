package com.fitanywhere.adDate_temp.model;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fitanywhere.ad.model.AdVO;



@Entity
@Table(name="ad_date")
public class AdDateVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_date_id")
	private Integer adDateId;
	
	@Column(name = "ad_start_date")
	private Date adStartDate;
	
	@Column(name = "ad_end_date")
	private Date adEndDate;
	
	@ManyToOne
	@JoinColumn(name = "ad_id")
	private AdVO adVO;
	
	
	// @ManyToOne  (雙向多對一/一對多) 的多對一
	//【此處預設為 @ManyToOne(fetch=FetchType.EAGER) --> 是指 lazy="false"之意】【注意: 此處的預設值與XML版 (p.127及p.132) 的預設值相反】
	//【如果修改為 @ManyToOne(fetch=FetchType.LAZY)  --> 則指 lazy="true" 之意】
	 // 指定用來join table的column(sql table欄位)
	public AdVO getAdVO() {
		return this.adVO;
	}


	public void setAdVO(AdVO adVO) {
		this.adVO = adVO;
	}


	public AdDateVO() {
		super();
	}


	public Integer getAdDateId() {
		return adDateId;
	}


	public void setAdDateId(Integer adDateId) {
		this.adDateId = adDateId;
	}


	public Date getAdStartDate() {
		return adStartDate;
	}


	public void setAdStartDate(Date adStartDate) {
		this.adStartDate = adStartDate;
	}


	public Date getAdDEndDate() {
		return adEndDate;
	}


	public void setAdDEndDate(Date adDEndDate) {
		this.adEndDate = adDEndDate;
	}


	public Date getAdEndDate() {
		return adEndDate;
	}


	public void setAdEndDate(Date adEndDate) {
		this.adEndDate = adEndDate;
	}


	@Override
	public String toString() {
		return "AdDateVO [adDateId=" + adDateId + ", adStartDate=" + adStartDate + ", adEndDate=" + adEndDate
				+ ", adVO=" + adVO + "]";
	}
	
	

}
