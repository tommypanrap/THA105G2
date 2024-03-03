package com.fitanywhere.user.model;

import java.util.Date;

public class UserReadDataDTO {

	private Integer uId;	
	private String uNickname;
	private String uName;
	private String uMail;
	private String uPhone;
	private Integer uGender;
	private Date uBirth;
	private Integer uStatus;
	private Date uRegisterDate;

	public UserReadDataDTO() {
	}

	public UserReadDataDTO(Integer uId, String uNickname, String uName, String uMail, String uPhone,
			Integer uGender, Date uBirth, Integer uStatus, Date uRegisterDate) {
		this.uId = uId;		
		this.uNickname = uNickname;
		this.uName = uName;
		this.uMail = uMail;
		this.uPhone = uPhone;
		this.uGender = uGender;
		this.uBirth = uBirth;
		this.uStatus = uStatus;
		this.uRegisterDate = uRegisterDate;
	}

	public Integer getuId() {
		return uId;
	}

	public String getuNickname() {
		return uNickname;
	}

	public String getuName() {
		return uName;
	}

	public String getuMail() {
		return uMail;
	}

	public String getuPhone() {
		return uPhone;
	}

	public Integer getuGender() {
		return uGender;
	}

	public Date getuBirth() {
		return uBirth;
	}

	public Integer getuStatus() {
		return uStatus;
	}

	public Date getuRegisterDate() {
		return uRegisterDate;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public void setuMail(String uMail) {
		this.uMail = uMail;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public void setuGender(Integer uGender) {
		this.uGender = uGender;
	}

	public void setuBirth(Date uBirth) {
		this.uBirth = uBirth;
	}

	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}

	public void setuRegisterDate(Date uRegisterDate) {
		this.uRegisterDate = uRegisterDate;
	}

	

}
