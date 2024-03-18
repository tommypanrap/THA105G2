// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.usercourse.model;

import com.fitanywhere.user.model.UserReadDataDTO;
import com.fitanywhere.user.model.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from course where cr_id =?1", nativeQuery = true)
	void deleteByusCrId(int usCrId);


	@Query(value = "SELECT COUNT(DISTINCT u_id) AS unique_uId_count FROM user_course where cr_id IN" +
			"(SELECT cr_id FROM course where u_id = :uId)", nativeQuery = true)
	Integer getStudentCount(Integer uId);


	@Query(value = "SELECT u.u_nickname FROM user_course uc INNER JOIN user u ON uc.u_id = u.u_id WHERE uc.u_id = :uId", nativeQuery = true)
	String getNickname(@Param("uId") Integer uId);



}

