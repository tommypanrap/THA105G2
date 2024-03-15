package com.fitanywhere.coursedetail.model;

import com.fitanywhere.course.model.CourseVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
*在spring boot
* @Data 等於自動加上Getter/Setter、ToString、EqualsAndHashCode、RequiredArgsConstructor
* @NoArgsConstructor 加上一個無參建構子 JavaBean基礎知識
* */

import com.fitanywhere.course.model.CourseVO;

@Data
@NoArgsConstructor
@Entity
@Table(name = "course_detail")
public class CourseDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cd_id")
	private Integer cdId;
	
//	@ManyToOne
//    @JoinColumn(name="cr_id", referencedColumnName="cr_id")
//    private CourseVO courseVO;
	

	@ManyToOne
	@JoinColumn(name="cr_id")
	private CourseVO courseVO;
	
	@Column(name="cd_video",columnDefinition = "longtext")
	private String cdVideo;
	
	@Column(name="cd_sale_video",columnDefinition = "longtext")
	private String cdSaleVideo;
	
	@Column(name="cd_pdf",columnDefinition = "longblob")
	private byte[] cdPdf;

	@Column(name="cd_title")
	private String cdTitle;

	
//	public CourseDetailVO(Integer cdId, CourseVO courseVO, String cdVideo, String cdSaleVideo, byte[] cdPdf) {
//		super();
//		this.cdId = cdId;
//		this.courseVO = courseVO;
//		this.cdVideo = cdVideo;
//		this.cdSaleVideo = cdSaleVideo;
//		this.cdPdf = cdPdf;
//	}

//	@Override
//	public String toString() {
//		return "CourseDetailVO [cdId=" + cdId + ", crId=" + courseVO + ", cdVideo=" + cdVideo + ", cdSaleVideo="
//				+ cdSaleVideo + ", cdPdf=" + Arrays.toString(cdPdf) + "]";
//	}

//	public CourseVO getCourseVO() {
//		return courseVO;
//	}
//
//	public void setCourseVO(CourseVO courseVO) {
//		this.courseVO = courseVO;
//	}



	public Integer getCdId() {
		return cdId;
	}

	public void setCdId(Integer cdId) {
		this.cdId = cdId;
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
	
	@Column(name="cd_unit")
	private String cdUnit;
}
