package com.fitanywhere.userlist.model;

public class UserlistAllDataDTO {
	private Integer uId;
	private String uNickname;
	private String uMail;
	private Integer uStatus;
	private Integer cId;

	public UserlistAllDataDTO() {
	}

	public UserlistAllDataDTO(Integer uId, String uNickname, String uMail, Integer uStatus, Integer cId) {
		this.uId = uId;
		this.uNickname = uNickname;
		this.uMail = uMail;
		this.uStatus = uStatus;
		this.cId = cId;
	}

	public Integer getuId() {
		return uId;
	}

	public String getuNickname() {
		return uNickname;
	}

	public String getuMail() {
		return uMail;
	}

	public Integer getuStatus() {
		return uStatus;
	}

	public Integer getcId() {
		return cId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public void setuNickname(String uNickname) {
		this.uNickname = uNickname;
	}

	public void setuMail(String uMail) {
		this.uMail = uMail;
	}

	public void setuStatus(Integer uStatus) {
		this.uStatus = uStatus;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

}
