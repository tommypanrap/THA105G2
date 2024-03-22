package com.fitanywhere.forumpost.model;

public class ForumPostGetCoverDTO {
	private Integer fpId;
	
	private byte[] fpPic;

	public Integer getFpId() {
		return fpId;
	}

	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}

	public byte[] getFpPic() {
		return fpPic;
	}

	public void setFpPic(byte[] fpPic) {
		this.fpPic = fpPic;
	}

	public ForumPostGetCoverDTO(Integer fpId, byte[] fpPic) {
		super();
		this.fpId = fpId;
		this.fpPic = fpPic;
	}

	public ForumPostGetCoverDTO() {
		super();
	}
	
	
}
