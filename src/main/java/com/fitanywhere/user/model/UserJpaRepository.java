package com.fitanywhere.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//處理登入時依據會員信箱查詢所需資料
@Repository
public interface UserJpaRepository extends JpaRepository<UserVO, Integer> {

	@Query("FROM UserVO WHERE uMail = :uMail")
	UserVO findByuMail(String uMail);

	@Query("FROM UserVO WHERE uNickname = :uNickname")
	UserVO findByuNickname(String uNickname);

	@Query("FROM UserVO WHERE uPhone = :uPhone")
	UserVO findByuPhone(String uPhone);

	@Query("FROM UserVO WHERE uId = :uId")
	UserVO findByuId(Integer uId);

	@Query("SELECT new com.fitanywhere.user.model.UserHeadshotOnlyDTO(u.uId, u.uHeadshot) FROM UserVO u WHERE u.uId = :uId")
	UserHeadshotOnlyDTO findUserHeadshotById(@Param("uId") Integer uId);

	@Query("SELECT new com.fitanywhere.user.model.UserReadDataDTO(" + "u.uId, u.uNickname, u.uName, u.uMail, u.uPhone, "
			+ "u.uGender, u.uBirth, u.uStatus, u.uRegisterdate) " + "FROM UserVO u WHERE u.uId = :uId")
	UserReadDataDTO findUserDataById(Integer uId);

}
