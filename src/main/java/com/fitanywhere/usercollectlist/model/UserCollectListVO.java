package com.fitanywhere.usercollectlist.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserVO;

//@Entity
//@Table(name = "user_collect_list")
public class UserCollectListVO implements Serializable {
	private static final long serialVersionUID = 1L;

//	PRIMARY KEY (cr_id, u_id),
//    cr_id INT ,

	
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

	public UserCollectListVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCollectListVO(Integer crId, CourseVO course, Integer uId, UserVO user) {
		super();
		this.crId = crId;
		this.course = course;
		this.uId = uId;
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserCollectListVO [crId=" + crId + ", course=" + course + ", uId=" + uId + ", user=" + user + "]";
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
