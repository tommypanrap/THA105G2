package com.fitanywhere.socialpost.model;

public class SocialPostSearchDTO {
	private String searchValue;
    private Integer uId;
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public SocialPostSearchDTO() {
		super();
	}
    
    
}
