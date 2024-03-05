package com.fitanywhere.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

//處理登入時依據會員信箱查詢所需資料
@Repository
public interface UserJpaRepository extends JpaRepository<UserVO, Integer> {


// =========================
	// 如果只是要單純判斷會員資料是否存在 用這個比較省效能
	boolean existsByuMail(String uMail);

	boolean existsByuNickname(String uNickname);

	boolean existsByuPhone(String uPhone);

	boolean existsByuId(Integer uId);

// =========================
	// 如果需要透過指定資料查詢整筆會員資料才用這些 因為都會讀整筆資料封裝UserVO比較吃效能
	@Query("FROM UserVO WHERE uMail = :uMail")
	UserVO findByuMail(String uMail);

	@Query("FROM UserVO WHERE uNickname = :uNickname")
	UserVO findByuNickname(String uNickname);

	@Query("FROM UserVO WHERE uPhone = :uPhone")
	UserVO findByuPhone(String uPhone);

	@Query("FROM UserVO WHERE uId = :uId")
	UserVO findByuId(Integer uId);

// =========================
// 使用DTO封裝 適合從別的Controller呼叫對應的UserService取得	

	// 讀取指定uID的uHeadshot
	@Query("SELECT new com.fitanywhere.user.model.UserHeadshotOnlyDTO(u.uId, u.uHeadshot) FROM UserVO u WHERE u.uId = :uId")
	UserHeadshotOnlyDTO findUserHeadshotDTOById(@Param("uId") Integer uId);

	// 讀取指定uID除uHeadshot外的所有非敏感資料
	@Query("SELECT new com.fitanywhere.user.model.UserReadDataDTO(" + "u.uId, u.uNickname, u.uName, u.uMail, u.uPhone, "
			+ "u.uGender, u.uBirth, u.uStatus, u.uRegisterdate) " + "FROM UserVO u WHERE u.uId = :uId")
	UserReadDataDTO findUserDataDTOById(Integer uId);

// =========================
// 精準讀取某特定欄位	

	// 依照uMail取得uPassword
	@Query("SELECT u.uPassword FROM UserVO u WHERE u.uMail = :uMail")
	String findOnlyPasswordByuMail(String uMail);

	// 依照uMail取得uId
	@Query("SELECT u.uId FROM UserVO u WHERE u.uMail = :uMail")
	Integer findOnlyIdByuMail(String uMail);
	
	// 依照uMail取得uNickname
	@Query("SELECT u.uNickname FROM UserVO u WHERE u.uMail = :uMail")
	String findOnlyNicknameByuMail(String uMail);

// =========================
	// 精準寫入某特定欄位	
	
	// 依據uId更新uPassword
    @Transactional
    @Modifying
    @Query("UPDATE UserVO u SET u.uPassword = :encryptedPassword WHERE u.uId = :uId")
    int updatePasswordById(Integer uId, String encryptedPassword);

// =========================
    //andy 單取出user的大頭照
    @Query("SELECT u.uHeadshot FROM UserVO u WHERE u.uId = :uId")
    byte[] getUserHeadshotByUserId(Integer uId);

}
