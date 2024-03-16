// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.user_course.model;

import java.util.List;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourseVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from course where cr_id =?1", nativeQuery = true)
	void deleteByusCrId(int usCrId);

//	@Query("SELECT COUNT(*) FROM CourseVO WHERE u_Id = :uId")
//    Integer getCourseCount(Integer uId);
	
	@Query(value = "SELECT COUNT(DISTINCT u_id) AS unique_uId_count FROM user_course where cr_id IN" +
			"(SELECT cr_id FROM course where u_id = :uId)" ,nativeQuery = true)
	Integer getStudentCount(Integer uId);
	
    }
