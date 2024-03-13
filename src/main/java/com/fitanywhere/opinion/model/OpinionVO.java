package com.fitanywhere.opinion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fitanywhere.user.model.UserVO;

@Entity
@Table(name = "opinion")
public class OpinionVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "op_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer opId;
	
	@ManyToOne
    @JoinColumn(name = "u_id", referencedColumnName = "u_id")
    private UserVO user;
	
	@Column(name = "op_title", length = 150)
    private String opTitle;

    @Column(name = "op_content", columnDefinition = "LONGTEXT")
    private String opContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "op_send_time")
    private Date opSendTime;

    @Column(name = "op_reply_content", columnDefinition = "LONGTEXT")
    private String opReplyContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "op_reply_time")
    private Date opReplyTime;

    @Column(name = "op_status")
    private int opStatus;

    public OpinionVO() {
        // Default constructor
    }
	
    public OpinionVO(UserVO user, String opTitle, String opContent, Date opSendTime, int opStatus) {
        this.user = user;
        this.opTitle = opTitle;
        this.opContent = opContent;
        this.opSendTime = opSendTime;       
        this.opStatus = opStatus;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getOpId() {
		return opId;
	}

	public UserVO getUser() {
		return user;
	}

	public String getOpTitle() {
		return opTitle;
	}

	public String getOpContent() {
		return opContent;
	}

	public Date getOpSendTime() {
		return opSendTime;
	}

	public String getOpReplyContent() {
		return opReplyContent;
	}

	public Date getOpReplyTime() {
		return opReplyTime;
	}

	public int getOpStatus() {
		return opStatus;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public void setOpTitle(String opTitle) {
		this.opTitle = opTitle;
	}

	public void setOpContent(String opContent) {
		this.opContent = opContent;
	}

	public void setOpSendTime(Date opSendTime) {
		this.opSendTime = opSendTime;
	}

	public void setOpReplyContent(String opReplyContent) {
		this.opReplyContent = opReplyContent;
	}

	public void setOpReplyTime(Date opReplyTime) {
		this.opReplyTime = opReplyTime;
	}

	public void setOpStatus(int opStatus) {
		this.opStatus = opStatus;
	}


}
