package com.fitanywhere.opinion.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionJpaRepository extends JpaRepository<OpinionVO, Integer> {

	// 前台-取得某會員所有資料
	@Query("SELECT new com.fitanywhere.opinion.model.OpinionAllDataDTO(o.opId, o.opTitle, o.opContent, o.opSendTime, "
			+ "o.opReplyContent, o.opReplyTime, o.opStatus, o.user.uId, o.user.uNickname) "
			+ "FROM OpinionVO o WHERE o.user.uId = :uId ORDER BY o.opId DESC")
	Page<OpinionAllDataDTO> findAllOpinionsWithSelectedUser(Pageable pageable, Integer uId);

	// 後台-取得所有資料
	@Query("SELECT new com.fitanywhere.opinion.model.OpinionAllDataDTO(o.opId, o.opTitle, o.opContent, o.opSendTime, "
			+ "o.opReplyContent, o.opReplyTime, o.opStatus, o.user.uId, o.user.uNickname) " + "FROM OpinionVO o "
			+ "ORDER BY o.opId DESC")
	Page<OpinionAllDataDTO> findAllOpinionsWithUser(Pageable pageable);

	// 後台-查詢某筆資料
	@Query("FROM OpinionVO WHERE opId = :opId")
	OpinionVO findByOpId(Integer opId);

	// 後台-更新某筆資料
	@Modifying
	@Query("UPDATE OpinionVO o SET o.opReplyContent = :opReplyContent, o.opReplyTime = CURRENT_TIMESTAMP, o.opStatus = 1 WHERE o.opId = :opId")
	int updateOpinionReplyContentAndStatusById(@Param("opId") Integer opId,
			@Param("opReplyContent") String opReplyContent);

}
