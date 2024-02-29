package com.fitanywhere.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_id")
	private Integer uId;

	@Column(name = "mood_id")
	private Integer moodId;

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

	@Column(name = "u_verified")
	private Integer uVerified;
//	0 = 未驗證email; 1 = 已驗證email;

	@Column(name = "u_coach")
	private Integer uCoach;
//	0 = 一般會員; 1 = 教練;

	@Column(name = "u_gender")
	private Integer uGender;
//	0 = 男; 1 = 女; 2 = 其他;
	
	@Column(name="u_headshot",columnDefinition = "longblob")
	private byte[] uHeadshot;

	@Column(name = "u_birth")
	private Date uBirth;

	@Column(name = "u_status")
	private Integer uStatus;
//	0 = 正常會員; 1 = 帳號關閉; 

	@Column(name = "u_registerdate")
	private Date uRegisterdate;

	@Column(name = "c_intro")
	private String cIntro;

	// Constructor
	public UserVO() {
		// Default constructor
	}

	// Getters and setters 
	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public Integer getMoodId() {
		return moodId;
	}

	public void setMoodId(Integer moodId) {
		this.moodId = moodId;
	}

	public String getuNickname() {
		return uNickname;
	}

	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuMail() {
		return uMail;
	}

	public void setuMail(String uMail) {
		this.uMail = uMail;
	}

	public String getuPassword() {
		return uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public Integer getuVerified() {
		return uVerified;
	}

	public void setuVerified(Integer uVerified) {
		this.uVerified = uVerified;
	}

	public Integer getuCoach() {
		return uCoach;
	}

	public void setuCoach(Integer uCoach) {
		this.uCoach = uCoach;
	}

	public Integer getuGender() {
		return uGender;
	}

	public void setuGender(Integer uGender) {
		this.uGender = uGender;
	}

	public byte[] getuHeadshot() {
		return uHeadshot;
	}

	public void setuHeadshot(byte[] uHeadshot) {
		this.uHeadshot = uHeadshot;
	}

	public Date getuBirth() {
		return uBirth;
	}

	public void setuBirth(Date uBirth) {
		this.uBirth = uBirth;
	}

	public Integer getuStatus() {
		return uStatus;
	}

	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}

	public Date getuRegisterdate() {
		return uRegisterdate;
	}

	public void setuRegisterdate(Date uRegisterdate) {
		this.uRegisterdate = uRegisterdate;
	}

	public String getcIntro() {
		return cIntro;
	}

	public void setcIntro(String cIntro) {
		this.cIntro = cIntro;
	}

	public UserVO(Integer uId, Integer moodId, String uNickname, String uName, String uMail, String uPassword,
			String uPhone, Integer uVerified, Integer uCoach, Integer uGender, Integer uAge, byte[] uHeadshot,
			Date uBirth, Integer uStatus, String cIntro) {
		super();
		this.uId = uId;
		this.moodId = moodId;
		this.uNickname = uNickname;
		this.uName = uName;
		this.uMail = uMail;
		this.uPassword = uPassword;
		this.uPhone = uPhone;
		this.uVerified = uVerified;
		this.uCoach = uCoach;
		this.uGender = uGender;
		this.uHeadshot = uHeadshot;
		this.uBirth = uBirth;
		this.uStatus = uStatus;
		this.cIntro = cIntro;
	}
}
	

	

