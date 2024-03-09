package com.fitanywhere.ad.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fitanywhere.adCarousel.model.AdCarouselVO;
import com.fitanywhere.adDate.model.AdDateVO;



@Entity
@Table(name = "ad")
public class AdVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_id")
	private Integer adId;
	
	@Column(name = "ad_name")
	private String adName;
	
	@Column(name = "ad_status")
	private Integer adStatus;
	
	@Column(name = "ad_day_price")
	private Integer adDayPrice;
	
	@Column(name = "ad_type")
	private String adType;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="adVO")
	@OrderBy("ad_id asc")
//	private Set<AdDateVO> adDates = new HashSet<AdDateVO>();
	private Set<AdDateVO> adDateVO = new HashSet<AdDateVO>();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="adVO")
	@OrderBy("ad_id asc")
//	private Set<AdDateVO> adDates = new HashSet<AdDateVO>();
	private Set<AdCarouselVO> adcarVO = new HashSet<AdCarouselVO>();
	
//	@OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
//	@OrderBy("ad_id dsc")
//	private Set<AdCarouselOrderVO> adcs; 
	//註1:【現在是設定成 cascade="all" lazy="false" inverse="true"之意】
	//註2:【mappedBy="多方的關聯屬性名"：用在雙向關聯中，把關係的控制權反轉】【deptVO是EmpVO的屬性】
	//註3:【原預設為@OneToMany(fetch=FetchType.LAZY, mappedBy="deptVO")之意】--> 【是指原為  lazy="true"  inverse="true"之意】
	//FetchType.EAGER : Defines that data must be eagerly fetched
	//FetchType.LAZY  : Defines that data can be lazily fetched
	
	
//	public Set<AdDateVO> getAdDates() {
//		return this.adDates;
//	}
//
//
//	public void setAdDates(Set<AdDateVO> adDates) {
//		this.adDates = adDates;
//	}


	public AdVO() {
		super();
	}

	public Set<AdDateVO> getAdDateVO() {
		return this.adDateVO;
	}

	public void setAdDateVO(Set<AdDateVO> adDateVO) {
		this.adDateVO = adDateVO;
	}

	public AdVO(Integer adId, String adName, Integer adStatus, Integer adDayPrice) {
		super();
		this.adId = adId;
		this.adName = adName;
		this.adStatus = adStatus;
		this.adDayPrice = adDayPrice;
	}

	@Override
	public String toString() {
		return "AdVO [adId=" + adId + ", adName=" + adName + ", adStatus=" + adStatus + ", adDayPrice="
				+ adDayPrice + "]";
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}


	public Integer getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(Integer adStatus) {
		this.adStatus = adStatus;
	}

	public Integer getAdDayPrice() {
		return adDayPrice;
	}

	public void setAdDayPrice(Integer adDayPrice) {
		this.adDayPrice = adDayPrice;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public Set<AdCarouselVO> getAdcarVO() {
		return this.adcarVO;
	}

	public void setAdcarVO(Set<AdCarouselVO> adcarVO) {
		this.adcarVO = adcarVO;
	}
	
}
