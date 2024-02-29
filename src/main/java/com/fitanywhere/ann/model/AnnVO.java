package com.fitanywhere.ann.model;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import com.dept.model.DeptVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/*
 * 註1: classpath必須有javax.persistence-api-x.x.jar 
 * 註2: Annotation可以添加在屬性上，也可以添加在getXxx()方法之上
 */


@Entity  //要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "announcement") //代表這個class是對應到資料庫的實體table，目前對應的table是EMP2 
public class AnnVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "an_id")
//	@NotEmpty(message ="jjjjj")
	private Integer anId;

	@Column(name = "cr_id ")
	private Integer crId;
	
	@Column(name = "an_date")
	private Timestamp anDate;
	
	@Column(name = "an_edit_date")
	private Timestamp anEditDate;
	
	@Column(name = "an_title")
	private String anTitle;
	
	@Column(name = "an_content" , columnDefinition = "longtext")
	private String anContent;


	public AnnVO() { //必需有一個不傳參數建構子(JavaBean基本知識)
	}


	public Integer getAnId() {
		return this.anId;
	}


	public void setAnId(Integer anId) {
		this.anId = anId;
	}


	public Integer getCrId() {
		return this.crId;
	}


	public void setCrId(Integer crId) {
		this.crId = crId;
	}


	public Timestamp getAnDate() {
		return this.anDate;
	}


	public void setAnDate(Timestamp anDate) {
		long currentTimeMillis = System.currentTimeMillis();

		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		this.anDate = currentTimestamp;
	}


	public Timestamp getAnEditDate() {
		return this.anEditDate;
	}


	public void setAnEditDate(Timestamp anEditDate) {
		long currentTimeMillis = System.currentTimeMillis();

		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);

		this.anEditDate = currentTimestamp;
	}


	public String getAnTitle() {
		return this.anTitle;
	}


	public void setAnTitle(String anTitle) {
		this.anTitle = anTitle;
	}


	public String getAnContent() {
		return this.anContent;
	}


	public void setAnContent(String anContent) {
		this.anContent = anContent;
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
	
}
