package com.fitanywhere.forumreply.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ForumReplyRepository extends JpaRepository<ForumReplyVO, Integer> {
//    @Transactional
//    @Modifying
//    @Query(value = "delete from forum_post where fr_id =?1", nativeQuery = true)
//    void deleteByFpId(int FrId);

	@Transactional
	@Modifying
	@Query(value = "update forum_post set fr_status = 0 where fr_id = ?1", nativeQuery = true)
	void deleteByFrStatus(int FrId);


}