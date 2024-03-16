package com.fitanywhere.adcarousel.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fitanywhere.ad.model.AdVO;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserVO;




//import com.fitanywhere.ad.model.AdVO;
//import com.fitanywhere.course.model.CourseVO;
//import com.fitanywhere.user.model.UserVO;

@Entity
@Table(name = "ad_carousel_order")
public class AdCarouselVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adc_id")
	private Integer adcId;
	
	@Column(name = "adc_start_date")
//	@NotNull(message="結束日期: 請勿空白")	
//	@Future(message="日期必須是在今日(不含)之後")
	private Date adcStartDate;
	
	@Column(name = "adc_end_date")
//	@NotNull(message="結束日期: 請勿空白")	
//	@Future(message="日期必須是在今日(不含)之後")
	private Date adcEndDate;
	
	@Column(name = "adc_total_price")
//	@NotNull(message="總價: 請勿空白")
	private Integer adcTotalPrice;
	
	@Column(name = "adc_update_pic" ,columnDefinition = "longblob")
//	@NotEmpty(message="輪播廣告照片: 請上傳照片") 
	private byte[] adcUpdatePic;
	
	@Column(name = "adc_status")
//	@NotNull(message="狀態: 請勿空白")
	private Integer adcStatus;
	
	@Column(name = "adc_order_enddate")
	private Timestamp adcOrderEnddate = Timestamp.valueOf(LocalDateTime.now());
	
	@ManyToOne
	@JoinColumn(name = "ad_id", referencedColumnName = "ad_id")
//	@NotNull(message="方案id: 請勿空白")
	private AdVO adVO;
	
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name = "u_id", referencedColumnName = "u_id")
//	@NotNull(message="教練id: 請勿空白")
	private UserVO userVO;
	
	@ManyToOne
	@JoinColumn(name = "cr_id", referencedColumnName = "cr_id")
//	@NotNull(message="課程id: 請勿空白")
	private CourseVO courseVO;
	

	public AdVO getAdVO() {
		return adVO;
	}

	public void setAdVO(AdVO adVO) {
		this.adVO = adVO;
	}

	public AdCarouselVO() {
		super();
	}
	
	public AdCarouselVO(Integer adcId, Date adcStartDate, Date adcEndDate, Integer adcTotalPrice,
			byte[] adcUpdatePic, Integer adcStatus, Timestamp adcOrderEnddate) {
		super();
		this.adcId = adcId;
		this.adcStartDate = adcStartDate;
		this.adcEndDate = adcEndDate;
		this.adcTotalPrice = adcTotalPrice;
		this.adcUpdatePic = adcUpdatePic;
		this.adcStatus = adcStatus;
		this.adcOrderEnddate = adcOrderEnddate;
	}
	
	

	public UserVO getUserVO() {
		return this.userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	
	

	public CourseVO getCourseVO() {
		return this.courseVO;
	}

	public void setCourseVO(CourseVO courseVO) {
		this.courseVO = courseVO;
	}

	@Override
	public String toString() {
		return "AdCarouselOrderVO [adcId=" + adcId + ", adcStartDate=" + adcStartDate + ", adcEndDate=" + adcEndDate
				+ ", adcTotalPrice=" + adcTotalPrice + ", adcUpdatePic=" + Arrays.toString(adcUpdatePic)
				+ ", adcStatus=" + adcStatus + ", adcOrderEnddate=" + adcOrderEnddate + "]";
	}

	public Integer getAdcId() {
		return adcId;
	}

	public void setAdcId(Integer adcId) {
		this.adcId = adcId;
	}

	public Date getAdcStartDate() {
		return adcStartDate;
	}

	public void setAdcStartDate(Date adcStartDate) {
		this.adcStartDate = adcStartDate;
	}

	public Date getAdcEndDate() {
		return adcEndDate;
	}

	public void setAdcEndDate(Date adcEndDate) {
		this.adcEndDate = adcEndDate;
	}

	public Integer getAdcTotalPrice() {
		return adcTotalPrice;
	}

	public void setAdcTotalPrice(Integer adcTotalPrice) {
		this.adcTotalPrice = adcTotalPrice;
	}

	public byte[] getAdcUpdatePic() {
		return adcUpdatePic;
	}

	public void setAdcUpdatePic(byte[] adcUpdatePic) {
		this.adcUpdatePic = adcUpdatePic;
	}

	public Integer getAdcStatus() {
		return adcStatus;
	}

	public void setAdcStatus(Integer adcStatus) {
		this.adcStatus = adcStatus;
	}

	public Timestamp getAdcOrderEnddate() {
		return adcOrderEnddate;
	}

	public void setAdcOrderEnddate(Timestamp adcOrderEnddate) {
		this.adcOrderEnddate = adcOrderEnddate;
	}

}
