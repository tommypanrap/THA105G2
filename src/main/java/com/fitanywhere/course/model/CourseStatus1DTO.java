package com.fitanywhere.course.model;

import java.sql.Timestamp;
import java.util.Base64;

public class CourseStatus1DTO {
	private String crTitle;
	private String crClass;
	private byte[] crCover;
	private Integer crTotStar;
	private Integer crCmQuan;
	private Integer crPrice;
	private Timestamp crCreateDate;
	
	public CourseStatus1DTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public CourseStatus1DTO(String crTitle, String crClass, byte[] crCover, Integer crTotStar, Integer crCmQuan,
			Integer crPrice, Timestamp crCreateDate) {
		super();
		this.crTitle = crTitle;
		this.crClass = crClass;
		this.crCover = crCover;
		this.crTotStar = crTotStar;
		this.crCmQuan = crCmQuan;
		this.crPrice = crPrice;
		this.crCreateDate = crCreateDate;
	}


	public String getBase64Image() {
        // 將 byte[] 格式的圖片轉換為 base64 字串
        String base64Image = Base64.getEncoder().encodeToString(this.crCover);
        return base64Image;
    }

	public String getCrTitle() {
		return crTitle;
	}

	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}

	public String getCrClass() {
		return crClass;
	}

	public void setCrClass(String crClass) {
		this.crClass = crClass;
	}

	public byte[] getCrCover() {
		return crCover;
	}

	public void setCrCover(byte[] crCover) {
		this.crCover = crCover;
	}

	public Integer getCrTotStar() {
		return crTotStar;
	}

	public void setCrTotStar(Integer crTotStar) {
		this.crTotStar = crTotStar;
	}

	public Integer getCrCmQuan() {
		return crCmQuan;
	}

	public void setCrCmQuan(Integer crCmQuan) {
		this.crCmQuan = crCmQuan;
	}

	public Integer getCrPrice() {
		return crPrice;
	}

	public void setCrPrice(Integer crPrice) {
		this.crPrice = crPrice;
	}


	public Timestamp getCrCreateDate() {
		return crCreateDate;
	}


	public void setCrCreateDate(Timestamp crCreateDate) {
		this.crCreateDate = crCreateDate;
	}
	
	
}
