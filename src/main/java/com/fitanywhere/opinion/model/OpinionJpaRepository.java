package com.fitanywhere.opinion.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitanywhere.user.model.UserVO;

@Repository
public interface OpinionJpaRepository extends JpaRepository<UserVO, Integer> {

	@Query("SELECT new com.fitanywhere.opinion.model.OpinionAllDataDTO(o.opId, o.opTitle, o.opContent, o.opSendTime, "
			+ "o.opReplyContent, o.opReplyTime, o.opStatus, o.user.uId, o.user.uNickname) " + "FROM OpinionVO o " + "ORDER BY o.opId DESC")
	Page<OpinionAllDataDTO> findAllOpinionsWithUser(Pageable pageable);

	@Query("FROM OpinionVO WHERE opId = :opId")
	OpinionVO findByOpId(Integer opId);

	@Modifying
	@Query("UPDATE OpinionVO o SET o.opReplyContent = :opReplyContent, o.opReplyTime = CURRENT_TIMESTAMP, o.opStatus = 1 WHERE o.opId = :opId")
	int updateOpinionReplyContentAndStatusById(@Param("opId") Integer opId,
	        @Param("opReplyContent") String opReplyContent);


	void save(OpinionVO opinion);

}
