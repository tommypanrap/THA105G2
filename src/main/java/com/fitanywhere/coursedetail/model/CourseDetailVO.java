package com.fitanywhere.coursedetail.model;

import com.fitanywhere.course.model.CourseVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/*
*在spring boot
* @Data 等於自動加上Getter/Setter、ToString、EqualsAndHashCode、RequiredArgsConstructor
* @NoArgsConstructor 加上一個無參建構子 JavaBean基礎知識
* */

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

	@Column(name="cd_unit")
	private String cdUnit;
}
