package com.fitanywhere.opinion.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpinionService {

	@Autowired
	private OpinionJpaRepository opinionJpaRepository;

	@Transactional(readOnly = true)
	public Page<OpinionAllDataDTO> getAllOpinionsWithUsers(Pageable pageable) {
		return opinionJpaRepository.findAllOpinionsWithUser(pageable);
	}

	@Transactional
	public OpinionAddReplyDTO updateOpinionData(OpinionAddReplyDTO opinionDTO) {
		int updatedCount = opinionJpaRepository.updateOpinionReplyContentAndStatusById(opinionDTO.getOpId(),
				opinionDTO.getOpReplyContent());
		if (updatedCount > 0) {
			// 更新成功
			opinionDTO.setOpReplyTime(new Date()); // 寫入當下時間
			return opinionDTO;
		} else {
			// 更新失败
			return null;
		}
	}
}
