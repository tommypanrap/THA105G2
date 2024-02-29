// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.ann.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnnRepository extends JpaRepository<AnnVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from emp3 where an_id =?1", nativeQuery = true)
	void deleteByAnid(int anId);

}