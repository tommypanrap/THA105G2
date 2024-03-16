package com.fitanywhere.course.model;

import java.util.Base64;

public class CourseStatus2DTO {
	private String crClass;
    private String crTitle;
    private byte[] crCover;
    private Integer crPrice;
    
    public String getBase64Image() {
        // 將 byte[] 格式的圖片轉換為 base64 字串
        String base64Image = Base64.getEncoder().encodeToString(this.crCover);
        return base64Image;
    }
    
	public CourseStatus2DTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseStatus2DTO(String crClass, String crTitle, byte[] crCover, Integer crPrice) {
		super();
		this.crClass = crClass;
		this.crTitle = crTitle;
		this.crCover = crCover;
		this.crPrice = crPrice;
	}

	public String getCrClass() {
		return crClass;
	}

	public void setCrClass(String crClass) {
		this.crClass = crClass;
	}

	public String getCrTitle() {
		return crTitle;
	}

	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}

	public byte[] getCrCover() {
		return crCover;
	}

	public void setCrCover(byte[] crCover) {
		this.crCover = crCover;
	}

	public Integer getCrPrice() {
		return crPrice;
	}

	public void setCrPrice(Integer crPrice) {
		this.crPrice = crPrice;
	}
}
