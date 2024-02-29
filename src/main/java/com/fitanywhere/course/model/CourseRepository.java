// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.course.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRepository extends JpaRepository<CourseVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from course where cr_id =?1", nativeQuery = true)
	void deleteByCrid(int crId);

}