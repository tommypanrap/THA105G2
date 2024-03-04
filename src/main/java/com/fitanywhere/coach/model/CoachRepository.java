// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.coach.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CoachRepository extends JpaRepository<CoachVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from coach where c_id =?1", nativeQuery = true)
	void deleteByCid(int cId);

}