package com.fitanywhere.opinion.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitanywhere.user.model.UserVO;

@Repository
public interface OpinionJpaRepository extends JpaRepository<UserVO, Integer> {

	@Query("SELECT new com.fitanywhere.opinion.model.OpinionAllDataDTO(o.opId, o.opTitle, o.opContent, o.opSendTime, "
			+ "o.opReplyContent, o.opReplyTime, o.opStatus, o.user.uId, o.user.uNickname) " + "FROM OpinionVO o")
	Page<OpinionAllDataDTO> findAllOpinionsWithUser(Pageable pageable);

}
