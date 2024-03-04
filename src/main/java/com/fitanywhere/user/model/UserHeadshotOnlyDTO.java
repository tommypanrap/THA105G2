package com.fitanywhere.user.model;

public class UserHeadshotOnlyDTO {
	private Integer uId;
	private byte[] uHeadshot;

	// Constructor

	public UserHeadshotOnlyDTO() {
	}

	public UserHeadshotOnlyDTO(Integer uId, byte[] uHeadshot) {
		this.uId = uId;
		this.uHeadshot = uHeadshot;
	}

	// Getters and setters
	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public byte[] getuHeadshot() {
		return uHeadshot;
	}

	public void setuHeadshot(byte[] uHeadshot) {
		this.uHeadshot = uHeadshot;
	}

}
