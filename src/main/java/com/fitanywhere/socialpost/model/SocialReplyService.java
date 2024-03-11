package com.fitanywhere.socialpost.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.socialpost.model.SocialReplyRepository;
import com.fitanywhere.user.model.UserJpaRepository;

@Service("socialReplyService")
public class SocialReplyService {
	
	@Autowired
	SocialReplyRepository repository;
	
	@Autowired
	UserJpaRepository userRepository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public void addSocialReply(SocialReplyVO socialReplyVO) {
		repository.save(socialReplyVO);
	}
	
	public void updateSocialReply(SocialReplyVO socialReplyVO) {
		repository.save(socialReplyVO);
	}
	
	
	
	public void deleteSocialReply(Integer srid) {
		if (repository.existsById(srid))
			repository.deleteBySrId(srid);
	}
	

}
