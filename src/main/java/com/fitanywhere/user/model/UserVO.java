package com.fitanywhere.user.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitanywhere.adcarousel.model.AdCarouselVO;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.mood.model.MoodVO;
import com.fitanywhere.opinion.model.OpinionVO;
import com.fitanywhere.socialpost.model.SocialPostVO;
import com.fitanywhere.socialpost.model.SocialReplyVO;

@Entity
@Table(name = "user")
public class UserVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_id")
	private Integer uId;	
	

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OpinionVO> opinions = new HashSet<>();

	// Tommy
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "mood_id")
	private MoodVO moodVO;

	// Mok
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userVO")
	@JsonIgnore
	private Set<CourseVO> courses = new HashSet<CourseVO>();

	public Set<CourseVO> getCourses() {
		return courses;
	}

	public void setCourses(Set<CourseVO> courses) {
		this.courses = courses;
	}

	public MoodVO getMoodVO() {
		return this.moodVO;
	}

	public void setMoodVO(MoodVO moodVO) {
		this.moodVO = moodVO;
	}

	@Column(name = "u_nickname")
	private String uNickname;

	@Column(name = "u_name")
	private String uName;

	@Column(name = "u_mail")
	private String uMail;

	@Column(name = "u_password")
	private String uPassword;

	@Column(name = "u_phone")
	private String uPhone;

	@Column(name = "u_gender")
	private Integer uGender;
//	0 = 男; 1 = 女; 2 = 其他;

	@Column(name = "u_headshot", columnDefinition = "longblob")
	private byte[] uHeadshot;

	@Column(name = "u_birth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uBirth;

	@Column(name = "u_status")
	private Integer uStatus;
//	0 = 正常會員; 1 = 可登入但部分功能限制的帳號(懲罰中); 2 = 自行永久關閉帳號; 3 = 被檢舉停權帳號;   

	@Column(name = "u_registerdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uRegisterdate;

//	xiaoxin

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userVO")
	@JsonIgnore
	private Set<AdCarouselVO> adCarousel = new HashSet<AdCarouselVO>();

	
	//ROY	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="userVO")
	@OrderBy("u_id asc") //asc = 根據指定的欄位排序
	private Set<ForumPostVO> forumPost = new HashSet<ForumPostVO>();
	public Set<ForumPostVO> getForumPost() {
		return forumPost;
	}

	public void setForumPost(Set<ForumPostVO> forumPost) {
		this.forumPost = forumPost;
	}

	public Set<AdCarouselVO> getAdCarousel() {
		return this.adCarousel;
	}

	public void setAdCarousel(Set<AdCarouselVO> adCarousel) {
		this.adCarousel = adCarousel;
	}

	// Tommy
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userVO")
	@JsonIgnore
	@OrderBy("spid ASC")
	private Set<SocialPostVO> socialposts = new HashSet<SocialPostVO>();

	// Tommy
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userVO")
	@JsonIgnore
	private Set<SocialReplyVO> socialReplys = new HashSet<SocialReplyVO>();

	public Set<SocialReplyVO> getSocialReplys() {
		return socialReplys;
	}

	public void setSocialReplys(Set<SocialReplyVO> socialReplys) {
		this.socialReplys = socialReplys;
	}

	public Set<SocialPostVO> getSocialposts() {
		return this.socialposts;
	}

	public void setSocialposts(Set<SocialPostVO> socialposts) {
		this.socialposts = socialposts;
	}

	// Constructor
	public UserVO() {
		// Default constructor
	}

	// Getters and setters
	public Integer getuId() {
		return this.uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

//	public Integer getMoodId() {
//		return moodId;
//	}
//
//	public void setMoodId(Integer moodId) {
//		this.moodId = moodId;
//	}

	public String getuNickname() {
		return this.uNickname;
	}

	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}

	public String getuName() {
		return this.uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuMail() {
		return this.uMail;
	}

	public void setuMail(String uMail) {
		this.uMail = uMail;
	}

	public String getuPassword() {
		return this.uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public String getuPhone() {
		return this.uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public Integer getuGender() {
		return this.uGender;
	}

	public void setuGender(Integer uGender) {
		this.uGender = uGender;
	}

	public byte[] getuHeadshot() {
		return this.uHeadshot;
	}

	public void setuHeadshot(byte[] uHeadshot) {
		this.uHeadshot = uHeadshot;
	}

	public Date getuBirth() {
		return this.uBirth;
	}

	public void setuBirth(Date uBirth) {
		this.uBirth = uBirth;
	}

	public Integer getuStatus() {
		return this.uStatus;
	}

	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}

	public Date getuRegisterdate() {
		return this.uRegisterdate;
	}

	public void setuRegisterdate(Date uRegisterdate) {
		this.uRegisterdate = uRegisterdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<OpinionVO> getOpinions() {
		return opinions;
	}

	public void setOpinions(Set<OpinionVO> opinions) {
		this.opinions = opinions;
	}	
	
}
