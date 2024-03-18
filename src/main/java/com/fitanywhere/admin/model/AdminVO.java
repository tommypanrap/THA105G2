package com.fitanywhere.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "administrator")
public class AdminVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "am_id")
	private Integer adminId;

	@Column(name = "am_name")
	private String adminName;

	@Column(name = "am_mail")
	private String adminMail;

	@Column(name = "am_password")
	private String adminPassword;

	@Column(name = "am_level")
	private Integer adminLevel;

	// Constructor
	public AdminVO() {
		// Default constructor
	}

	public Integer getAdminId() {
		return adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public String getAdminMail() {
		return adminMail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public Integer getAdminLevel() {
		return adminLevel;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public void setAdminMail(String adminMail) {
		this.adminMail = adminMail;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public void setAdminLevel(Integer adminLevel) {
		this.adminLevel = adminLevel;
	}

	
}
