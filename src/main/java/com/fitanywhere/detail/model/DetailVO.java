package com.fitanywhere.detail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fitanywhere.order.model.OrderVO;
import com.fitanywhere.user.model.UserVO;

@Entity
@Table(name = "detail")
public class DetailVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "de_id", insertable = false, updatable = false)
	private Integer deId;

	// 被贈送者
	@ManyToOne
	@JoinColumn(name = "u_id")
	private UserVO user;

	@Column(name = "cr_id")
	private Integer crId;

	@Column(name = "cd_price")
	private Integer cdPrice;

	@Column(name = "cd_gift")
	private Integer cdGift;

	@Column(name = "cd_gift_status")
	private Integer cdGiftStatus;

	@Column(name = "cd_gift_remark")
	private String cdGiftRemark;

	@Column(name = "od_id")
	private Integer odId;

	public DetailVO() {
		super();
	}

	public DetailVO(Integer deId, UserVO user, Integer crId, Integer cdPrice, Integer cdGift, Integer cdGiftStatus,
			String cdGiftRemark, Integer odId) {
		super();
		this.deId = deId;
		this.user = user;
		this.crId = crId;
		this.cdPrice = cdPrice;
		this.cdGift = cdGift;
		this.cdGiftStatus = cdGiftStatus;
		this.cdGiftRemark = cdGiftRemark;
		this.odId = odId;
	}

	public Integer getDeId() {
		return deId;
	}

	public void setDeId(Integer deId) {
		this.deId = deId;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public Integer getCdPrice() {
		return cdPrice;
	}

	public void setCdPrice(Integer cdPrice) {
		this.cdPrice = cdPrice;
	}

	public Integer getCdGift() {
		return cdGift;
	}

	public void setCdGift(Integer cdGift) {
		this.cdGift = cdGift;
	}

	public Integer getCdGiftStatus() {
		return cdGiftStatus;
	}

	public void setCdGiftStatus(Integer cdGiftStatus) {
		this.cdGiftStatus = cdGiftStatus;
	}

	public String getCdGiftRemark() {
		return cdGiftRemark;
	}

	public void setCdGiftRemark(String cdGiftRemark) {
		this.cdGiftRemark = cdGiftRemark;
	}

	public Integer getOdId() {
		return odId;
	}

	public void setOdId(Integer odId) {
		this.odId = odId;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="de_id", referencedColumnName="od_id")
//	private OrderVO orderVO;
//	

}
