package com.fitanywhere.order.model;

import java.sql.Timestamp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fitanywhere.discount.model.DiscountVO;
import com.fitanywhere.user.model.UserVO;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`order`")
public class OrderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer odId;
	private Timestamp odCreateDate;
//	private UserVO user;
	private Integer uId;
	private Timestamp odEndDate;
	private Integer odStatus;
	private Integer odPrice;
//	private DiscountVO dc;
	private Integer dcId;

	public OrderVO() {
		super();
	}

	public OrderVO(Integer odId, Timestamp odCreateDate, Integer uId, Timestamp odEndDate, Integer odStatus,
			Integer odPrice, Integer dcId) {
		super();
		this.odId = odId;
		this.odCreateDate = odCreateDate;
		this.uId = uId;
		this.odEndDate = odEndDate;
		this.odStatus = odStatus;
		this.odPrice = odPrice;
		this.dcId = dcId;
	}

	@Id
	@Column(name = "od_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getOdId() {
		return this.odId;
	}

	public void setOdId(Integer odId) {
		this.odId = odId;
	}

	@Column(name = "od_create_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//	 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//	 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@CreatedDate
	public Timestamp getOdCreateDate() {
		return this.odCreateDate;
	}

	public void setOdCreateDate(Timestamp odCreateDate) {
		this.odCreateDate = odCreateDate;
	}

//	@OneToMany
//	@JoinColumn(name="u_id")
	@Column(name = "u_id")
	@NotNull(message="購買人請勿空白")
//	public UserVO getUser() {
//		return this.user;
//	}
	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}
//	public void setUser(UserVO user) {
//		this.user = user;
//	}

	@Column(name = "od_end_date")
//	@LastModifiedDate
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//	 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//	 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	public Timestamp getOdEndDate() {
		return this.odEndDate;
	}

	public void setOdEndDate(Timestamp odEndDate) {
		this.odEndDate = odEndDate;
	}

	
	// 訂單狀態 1:訂單完成; 2:訂單不成立; 3:訂單待付款
	@Column(name = "od_status")
	@NotNull(message="訂單狀態請勿空白")
	public Integer getOdStatus() {
		return this.odStatus;
	}

	public void setOdStatus(Integer odStatus) {
		this.odStatus = odStatus;
	}

	@Column(name = "od_price")
	@NotNull(message="訂單總金額請勿空白")
	public Integer getOdPrice() {
		return this.odPrice;
	}

	public void setOdPrice(Integer odPrice) {
		this.odPrice = odPrice;
	}

//	@ManyToOne
//	@JoinColumn(name="dc_id")
	@Column(name = "dc_id")
//	public DiscountVO getDc() {
//		return this.dc;
//	}
//
//	public void setDc(DiscountVO dc) {
//		this.dc = dc;
//	}

	public Integer getDcId() {
		return dcId;
	}

	public void setDcId(Integer dcId) {
		this.dcId = dcId;
	}

}
