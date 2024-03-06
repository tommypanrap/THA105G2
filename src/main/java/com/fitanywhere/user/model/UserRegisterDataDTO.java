package com.fitanywhere.user.model;

import java.time.LocalDate;
import java.util.Date;

public class UserRegisterDataDTO {
	private String uNickname;
	private String uName;
	private String uMail;
	private String uPhone;
	private Integer uGender;
	private Date uBirth;
	private String uPassword;
	private Integer uStatus;
	private LocalDate uRegisterDate;
	
	public UserRegisterDataDTO() {
	}

	public UserRegisterDataDTO(String uNickname, String uName, String uMail, String uPhone,
			Integer uGender, Date uBirth,String uPassword, Integer uStatus, LocalDate uRegisterDate) {			
		this.uNickname = uNickname;
		this.uName = uName;
		this.uMail = uMail;
		this.uPhone = uPhone;
		this.uGender = uGender;
		this.uBirth = uBirth;
		this.uPassword = uPassword;
		this.uStatus = uStatus;
		this.uRegisterDate = uRegisterDate;
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

	public String getuPassword() {
		return uPassword;
	}

	public Integer getuStatus() {
		return uStatus;
	}

	public LocalDate getuRegisterDate() {
		return uRegisterDate;
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

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}

	public void setuRegisterDate(LocalDate uRegisterDate) {
		this.uRegisterDate = uRegisterDate;
	}
	
	

}
