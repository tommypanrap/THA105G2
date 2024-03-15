package com.fitanywhere.admin.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminJpaRepository extends JpaRepository<AdminVO, Integer> {
	
	// 判斷是否存在
	boolean existsByAdminMail(String adminMail);
	boolean existsByAdminName(String adminName);
	boolean existsByAdminId(Integer adminId);
	
	// 精準讀取欄位
	@Query("SELECT a.adminPassword FROM AdminVO a WHERE a.adminId = :adminId")
	String findOnlyPasswordByAdminId(Integer adminId);
	
	@Query("SELECT a.adminName FROM AdminVO a WHERE a.adminId = :adminId")
	String findOnlyNameByAdminId(Integer adminId);
	
	@Query("SELECT a.adminMail FROM AdminVO a WHERE a.adminId = :adminId")
	String findOnlyEmailByAdminId(Integer adminId);
	
	@Query("SELECT a.adminLevel FROM AdminVO a WHERE a.adminId = :adminId")
	Integer findOnlyLevelByAdminId(Integer adminId);

}
