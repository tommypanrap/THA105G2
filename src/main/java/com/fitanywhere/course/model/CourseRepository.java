// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.course.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<CourseVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from course where cr_id =?1", nativeQuery = true)
	void deleteByCrid(int crId);

	@Query("SELECT COUNT(*) FROM CourseVO WHERE u_Id = :uId")
    Integer getCourseCount(Integer uId);
	
//	xiao xin
	@Query(value = "SELECT * FROM course WHERE u_id = ?1", nativeQuery = true)
	List<CourseVO> getCourseByUId(Integer uId); 
	
    }