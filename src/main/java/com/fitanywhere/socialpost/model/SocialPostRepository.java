package com.fitanywhere.socialpost.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SocialPostRepository extends JpaRepository<SocialPostVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from social_post where sp_id =?1", nativeQuery = true)
	void deleteBySpid(int spid);
}
