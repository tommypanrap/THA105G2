package com.fitanywhere.userviewlist.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserVO;

//@Entity
//@Table(name = "user_view_list")
public class UserViewListVO implements Serializable {
	private static final long serialVersionUID = 1L;

	
//	@Column(name = "cr_id")
	private Integer crId;
	
//	@MapsId 
//	@JoinColumn(name = "cr_id", referencedColumnName = "cr_id")
	private CourseVO course;

//	@Column(name = "u_id")
	private Integer uId;
	
//	@MapsId 
//	@JoinColumn(name = "u_id", referencedColumnName = "u_id")
	private UserVO user;
	
//	@Column(name="view_status")
	private Integer viewStatus;

	public UserViewListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserViewListVO(Integer crId, CourseVO course, Integer uId, UserVO user, Integer viewStatus) {
		super();
		this.crId = crId;
		this.course = course;
		this.uId = uId;
		this.user = user;
		this.viewStatus = viewStatus;
	}

	@Override
	public String toString() {
		return "UserViewListVO [crId=" + crId + ", course=" + course + ", uId=" + uId + ", user=" + user
				+ ", viewStatus=" + viewStatus + "]";
	}

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public CourseVO getCourse() {
		return course;
	}

	public void setCourse(CourseVO course) {
		this.course = course;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
