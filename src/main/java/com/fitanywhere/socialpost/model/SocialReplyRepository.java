package com.fitanywhere.socialpost.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SocialReplyRepository extends JpaRepository<SocialReplyVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from social_reply where sr_id =?1", nativeQuery = true)
	void deleteBySrId(int srId);
	

}
