package com.fitanywhere.detail.model;

import javax.persistence.*;

import com.fitanywhere.order.model.OrderVO;
import com.fitanywhere.user.model.UserVO;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "detail")

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailVO implements java.io.Serializable{
	@Serial
	private static final long serialVersionUID = 1L;



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "de_id", insertable = false, updatable = false)
	private Integer deId;

	// 被贈送者
//	@ManyToOne
//	@JoinColumn(name = "u_id")
//	private UserVO user;
	//被贈送者
	@Column(name = "u_id")
	private Integer uId;


	// 課程
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

//	@Transient
//	private Integer odId;


	// 訂單編號
	@ManyToOne
	@JoinColumn(name = "od_id")
	private OrderVO orderVO;



// ----------------------------------------


	public Integer getDeId() {
		return deId;
	}

	public void setDeId(Integer deId) {
		this.deId = deId;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
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

//	public Integer getOdId() {
//		return odId;
//	}
//
//	public void setOdId(Integer odId) {
//		this.odId = odId;
//	}

	public OrderVO getOrderVO() {
		return orderVO;
	}

	public void setOrderVO(OrderVO orderVO) {
		this.orderVO = orderVO;
	}
}
