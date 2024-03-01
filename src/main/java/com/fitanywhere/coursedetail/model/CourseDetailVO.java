package com.fitanywhere.coursedetail.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_detail")
public class CourseDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_id")
	private Integer cdId;
	
	@Column(name="cr_id")
	private Integer crId;
	
	@Column(name="cd_video",columnDefinition = "longtext")
	private String cdVideo;
	
	@Column(name="cd_sale_video",columnDefinition = "longtext")
	private String cdSaleVideo;
	
	@Column(name="cd_pdf",columnDefinition = "longblob")
	private byte[] cdPdf;

	public CourseDetailVO() {
		super();
	}

	public CourseDetailVO(Integer cdId, Integer crId, String cdVideo, String cdSaleVideo, byte[] cdPdf) {
		super();
		this.cdId = cdId;
		this.crId = crId;
		this.cdVideo = cdVideo;
		this.cdSaleVideo = cdSaleVideo;
		this.cdPdf = cdPdf;
	}

	@Override
	public String toString() {
		return "CourseDetailVO [cdId=" + cdId + ", crId=" + crId + ", cdVideo=" + cdVideo + ", cdSaleVideo="
				+ cdSaleVideo + ", cdPdf=" + Arrays.toString(cdPdf) + "]";
	}

	public Integer getCdId() {
		return cdId;
	}

	public void setCdId(Integer cdId) {
		this.cdId = cdId;
	}

	public Integer getCrId() {
		return crId;
	}

	public void setCrId(Integer crId) {
		this.crId = crId;
	}

	public String getCdVideo() {
		return cdVideo;
	}

	public void setCdVideo(String cdVideo) {
		this.cdVideo = cdVideo;
	}

	public String getCdSaleVideo() {
		return cdSaleVideo;
	}

	public void setCdSaleVideo(String cdSaleVideo) {
		this.cdSaleVideo = cdSaleVideo;
	}

	public byte[] getCdPdf() {
		return cdPdf;
	}

	public void setCdPdf(byte[] cdPdf) {
		this.cdPdf = cdPdf;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
