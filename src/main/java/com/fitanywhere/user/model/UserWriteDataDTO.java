package com.fitanywhere.user.model;

import java.util.Date;

public class UserWriteDataDTO {

	private Integer uId;		
	private String uName;	
	private String uPhone;
	private Integer uGender;
	private Date uBirth;	

	public UserWriteDataDTO() {
	}

	public UserWriteDataDTO(Integer uId, String uName,String uPhone,
			Integer uGender, Date uBirth) {
		this.uId = uId;				
		this.uName = uName;		
		this.uPhone = uPhone;
		this.uGender = uGender;
		this.uBirth = uBirth;		
	}

	public Integer getuId() {
		return uId;
	}

	public String getuName() {
		return uName;
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

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public void setuName(String uName) {
		this.uName = uName;
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

	

}
