package com.fitanywhere.order.model;

import com.fitanywhere.detail.model.DetailVO;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


//import com.fitanywhere.discount.model.DiscountVO;


@Entity
//@EntityListeners(OrderRepoListener.class)
@Table(name = "`order`")
@ToString
public class OrderVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "od_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer odId;


    @Column(name = "od_create_date", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//	 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//	 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    private Timestamp odCreateDate;


    //	private UserVO user;
//	@OneToMany
//	@JoinColumn(name="u_id")
    @Column(name = "u_id")
//    @NotNull(message = "購買人請勿空白")
    private Integer uId;


    @Column(name = "od_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	 @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp odEndDate;


    // 1:訂單完成  2:訂單失敗 3:訂單處理中
    @Column(name = "od_status")
//    @NotNull(message = "訂單狀態請勿空白")
    private Integer odStatus;


    @Column(name = "od_price")
//    @NotNull(message = "訂單總金額請勿空白")
    private Integer odPrice;


    //	private DiscountVO dc;
    @Column(name = "dc_id")
    private Integer dcId;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "orderVO")
    private Set<DetailVO> details = new HashSet<>();

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


    // 更新狀態 自動更新時間
    @PreUpdate
    public void onPreUpdate() {
        if (odStatus == 1) {
            odEndDate = new Timestamp(new Date().getTime());
        }
    }


    public Integer getOdId() {
        return this.odId;
    }

    public void setOdId(Integer odId) {
        this.odId = odId;
    }


    public Timestamp getOdCreateDate() {
        return this.odCreateDate;
    }

    public void setOdCreateDate(Timestamp odCreateDate) {
        this.odCreateDate = odCreateDate;
    }


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


    public Timestamp getOdEndDate() {
        return this.odEndDate;
    }

    public void setOdEndDate(Timestamp odEndDate) {
        this.odEndDate = odEndDate;
    }


    // 訂單狀態 1:訂單完成; 2:訂單不成立; 3:訂單待付款

    public Integer getOdStatus() {
        return this.odStatus;
    }

    public void setOdStatus(Integer odStatus) {
        this.odStatus = odStatus;
    }


    public Integer getOdPrice() {
        return this.odPrice;
    }

    public void setOdPrice(Integer odPrice) {
        this.odPrice = odPrice;
    }

//	@ManyToOne
//	@JoinColumn(name="dc_id")

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


    public Set<DetailVO> getDetails() {
        return details;
    }

    public void setDetails(Set<DetailVO> details) {
        this.details = details;
    }
}
