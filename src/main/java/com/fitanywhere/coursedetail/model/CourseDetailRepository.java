// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.coursedetail.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetailVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from coursedetail where cd_id =?1", nativeQuery = true)
	void deleteByCdid(int crId);

	
    }