package com.fitanywhere.opinion.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OpinionService {

	@Autowired
	private OpinionJpaRepository opinionJpaRepository;

	public Page<OpinionAllDataDTO> getAllOpinionsWithUsers(Pageable pageable) {			
		return opinionJpaRepository.findAllOpinionsWithUser(pageable);
	}

}
