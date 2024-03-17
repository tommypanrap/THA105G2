package com.fitanywhere.course.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitanywhere.adcarshipousel.model.AdCarouselVO;
import com.fitanywhere.coursedetail.model.CourseDetailVO;
import com.fitanywhere.user.model.UserVO;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fitanywhere.coursedetail.model.CourseDetailVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * 註1: classpath必須有javax.persistence-api-x.x.jar 
 * 註2: Annotation可以添加在屬性上，也可以添加在getXxx()方法之上
 */

@DynamicUpdate
@Entity  //要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "course") 
public class CourseVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cr_id")
//	@NotEmpty(message ="jjjjj")
	private Integer crId;
	
	// mok
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="u_id")
	private UserVO userVO;

	@Column(name = "cr_class")
	private String crClass;

	@Column(name = "cr_state")
	private Integer crState;

	@Column(name = "cr_title")
	private String crTitle;
	
	@Column(name = "cr_subtitle")
	private String crSubtitle;

	@Column(name = "cr_intro", columnDefinition = "longtext")
	private String crIntro;

	@Column(name = "cr_cover", columnDefinition = "longblob")
	private byte[] crCover;

	@Column(name = "cr_price")
	private Integer crPrice;

	@Column(name = "cr_create_date")
	@CreatedDate 									// mok新增-創建時自動生成時間
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // mok新增-時間格式
	private Timestamp crCreateDate;

	
	@Column(name = "cr_edit_date")
	@LastModifiedDate 								// mok新增-編輯後自動生成時間
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  // mok新增-時間格式
	private Timestamp crEditDate;

	@Column(name = "cr_cm_quan")
	private Integer crCmQuan;

	@Column(name = "cr_tot_star")
	private Integer crTotStar;

	@Column(name = "cr_purpose_1")
	private String crPurpose1;

	@Column(name = "cr_purpose_2")
	private String crPurpose2;

	@Column(name = "cr_purpose_3")
	private String crPurpose3;

	@Column(name = "cr_pre")
	private String crPre;

	@Column(name = "cr_target_1")
	private String crTarget1;

	@Column(name = "cr_hello_msg", columnDefinition = "longtext")
	private String crHelloMsg;

	@Column(name = "cr_cong", columnDefinition = "longtext")
	private String crCong;

	@Column(name = "cr_level")
	private String crLevel;
	

//	xiaoxin
	@OneToMany(fetch=FetchType.EAGER, mappedBy="courseVO")
	@JsonIgnore
	private Set<AdCarouselVO> adCarousel = new HashSet<AdCarouselVO>();



	// mok
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "courseVO")
	@OrderBy("cdId asc")
	@JsonIgnore
	private Set<CourseDetailVO> courseDetails = new HashSet<CourseDetailVO>();

	public Set<CourseDetailVO> getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(Set<CourseDetailVO> courseDetails) {
		this.courseDetails = courseDetails;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}


//	public Set<AdCarouselVO> getAdCarousel() {
//		return this.adCarousel;
//	}


	public void setAdCarousel(Set<AdCarouselVO> adCarousel) {
		this.adCarousel = adCarousel;

	}



	// Joy
	@Transient
	private String base64CrCover;
	public CourseVO() { //必需有一個不傳參數建構子(JavaBean基本知識)
	}


	public Integer getCrId() {
		return this.crId;
	}


	public void setCrId(Integer crId) {
		this.crId = crId;
	}


	public String getCrClass() {
		return this.crClass;
	}


	public void setCrClass(String crClass) {
		this.crClass = crClass;
	}


	public Integer getCrState() {
		return this.crState;
	}


	public void setCrState(Integer crState) {
		this.crState = crState;
	}


	public String getCrTitle() {
		return this.crTitle;
	}


	public void setCrTitle(String crTitle) {
		this.crTitle = crTitle;
	}


	public String getCrSubtitle() {
		return this.crSubtitle;
	}


	public void setCrSubtitle(String crSubtitle) {
		this.crSubtitle = crSubtitle;
	}


	public String getCrIntro() {
		return this.crIntro;
	}


	public void setCrIntro(String crIntro) {
		this.crIntro = crIntro;
	}


	public byte[] getCrCover() {
		return this.crCover;
	}


	public void setCrCover(byte[] crCover) {
		this.crCover = crCover;
	}


	public Integer getCrPrice() {
		return this.crPrice;
	}


	public void setCrPrice(Integer crPrice) {
		this.crPrice = crPrice;
	}


	public Timestamp getCrCreateDate() {
		return this.crCreateDate;
	}


	public void setCrCreateDate(Timestamp crCreateDate) {
		this.crCreateDate = crCreateDate;
	}


	public Timestamp getCrEditDate() {
		return this.crEditDate;
	}


	public void setCrEditDate(Timestamp crEditDate) {
		this.crEditDate = crEditDate;
	}


	public Integer getCrCmQuan() {
		return this.crCmQuan;
	}


	public void setCrCmQuan(Integer crCmQuan) {
		this.crCmQuan = crCmQuan;
	}


	public Integer getCrTotStar() {
		return this.crTotStar;
	}


	public void setCrTotStar(Integer crTotStar) {
		this.crTotStar = crTotStar;
	}


	public String getCrPurpose1() {
		return this.crPurpose1;
	}


	public void setCrPurpose1(String crPurpose1) {
		this.crPurpose1 = crPurpose1;
	}


	public String getCrPurpose2() {
		return this.crPurpose2;
	}


	public void setCrPurpose2(String crPurpose2) {
		this.crPurpose2 = crPurpose2;
	}


	public String getCrPurpose3() {
		return this.crPurpose3;
	}


	public void setCrPurpose3(String crPurpose3) {
		this.crPurpose3 = crPurpose3;
	}


	public String getCrPre() {
		return this.crPre;
	}


	public void setCrPre(String crPre) {
		this.crPre = crPre;
	}


	public String getCrTarget1() {
		return this.crTarget1;
	}


	public void setCrTarget1(String crTarget1) {
		this.crTarget1 = crTarget1;
	}


	public String getCrHelloMsg() {
		return this.crHelloMsg;
	}


	public void setCrHelloMsg(String crHelloMsg) {
		this.crHelloMsg = crHelloMsg;
	}


	public String getCrCong() {
		return this.crCong;
	}


	public void setCrCong(String crCong) {
		this.crCong = crCong;
	}


	public String getCrLevel() {
		return this.crLevel;
	}


	public void setCrLevel(String crLevel) {
		this.crLevel = crLevel;
	}



//	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵
//	@Column(name = "EMPNO")  //@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
//	@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
//	public Integer getEmpno() {
//		return this.empno;
//	}
//
//	public void setEmpno(Integer empno) {
//		this.empno = empno;
//	}
//
//	// @ManyToOne  (雙向多對一/一對多) 的多對一
//	//【此處預設為 @ManyToOne(fetch=FetchType.EAGER) --> 是指 lazy="false"之意】【注意: 此處的預設值與XML版 (p.127及p.132) 的預設值相反】
//	//【如果修改為 @ManyToOne(fetch=FetchType.LAZY)  --> 則指 lazy="true" 之意】
//	@ManyToOne
//	@JoinColumn(name = "DEPTNO")   // 指定用來join table的column
//	public DeptVO getDeptVO() {
//		return this.deptVO;
//	}
//
//	public void setDeptVO(DeptVO deptVO) {
//		this.deptVO = deptVO;
//	}
//
//	@Column(name = "ENAME")
//	@NotEmpty(message="員工姓名: 請勿空白")
//	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
//	public String getEname() {
//		return this.ename;
//	}
//
//	public void setEname(String ename) {
//		this.ename = ename;
//	}
//
//	@Column(name = "JOB")
//	@NotEmpty(message="員工職位: 請勿空白")
//	@Size(min=2,max=10,message="員工職位: 長度必需在{min}到{max}之間")
//	public String getJob() {
//		return this.job;
//	}
//
//	public void setJob(String job) {
//		this.job = job;
//	}
//
//	@Column(name = "HIREDATE")
////	@NotNull(message="雇用日期: 請勿空白")	
////	@Future(message="日期必須是在今日(不含)之後")
////	@Past(message="日期必須是在今日(含)之前")
////	@DateTimeFormat(pattern="yyyy-MM-dd") 
////	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
//	public Date getHiredate() {
//		return this.hiredate;
//	}
//
//	public void setHiredate(Date hiredate) {
//		this.hiredate = hiredate;
//	}
//
//	@Column(name = "SAL")
//	@NotNull(message="員工薪水: 請勿空白")
//	@DecimalMin(value = "10000.00", message = "員工薪水: 不能小於{value}")
//	@DecimalMax(value = "99999.99", message = "員工薪水: 不能超過{value}")
//	public Double getSal() {
//		return this.sal;
//	}
//
//	public void setSal(Double sal) {
//		this.sal = sal;
//	}
//
//	@Column(name = "COMM")
//	@NotNull(message="員工獎金: 請勿空白")
//	@DecimalMin(value = "1.00", message = "員工獎金: 不能小於{value}")
//	@DecimalMax(value = "99999.99", message = "員工獎金: 不能超過{value}")
//	public Double getComm() {
//		return this.comm;
//	}
//
//	public void setComm(Double comm) {
//		this.comm = comm;
//	}
//	
//	@Column(name = "UPFILES")
////	@NotEmpty(message="員工照片: 請上傳照片") --> 由EmpController.java 第60行處理錯誤信息
//	public byte[] getUpFiles() {
//		return upFiles;
//	}
//	public void setUpFiles(byte[] upFiles) {
//		this.upFiles = upFiles;
//	}


	public String getBase64CrCover() {
		return base64CrCover;
	}

	public void setBase64CrCover(String base64CrCover) {
		this.base64CrCover = base64CrCover;
	}

}
