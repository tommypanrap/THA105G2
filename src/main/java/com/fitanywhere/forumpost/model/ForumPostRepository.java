package com.fitanywhere.forumpost.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



public interface ForumPostRepository extends JpaRepository<ForumPostVO, Integer>{
    @Transactional
    @Modifying
    @Query(value = "delete from forum_post where fp_id =?1", nativeQuery = true)
    void deleteByFpId(int FpId);
	
//    @Transactional
//    @Modifying
//    @Query(value = "update forum_post set fp_status = 0 where fp_id = ?1", nativeQuery = true)
//	void deleteByFpStatus(int FpId);
    
    
}