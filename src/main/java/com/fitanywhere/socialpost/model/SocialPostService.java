package com.fitanywhere.socialpost.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.user.model.UserJpaRepository;

//import com.fitanywhere.user.model.UserVO;


@Service("socialService")
public class SocialPostService {
	
	@Autowired
	SocialPostRepository repository;
	
	
	@Autowired
	UserJpaRepository userRepository;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	
	
	public void addSocialPost(SocialPostVO socialPostVO) {
		repository.save(socialPostVO);
	}
	
	public void updateSocialPost(SocialPostVO socialPostVO) {
		repository.save(socialPostVO);
	}
	
	public void deleteSocialPost(Integer spid) {
		if (repository.existsById(spid))
			repository.deleteBySpid(spid);
	}
	
	public SocialPostVO getOneSocialPost(Integer spid) {
		Optional<SocialPostVO> optional = repository.findById(spid);

		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	
	public List<SocialPostVO> getAll() {
		return repository.findAll();
	}
	
	
	public class ResourceNotFoundException extends RuntimeException {

	    public ResourceNotFoundException(String message) {
	        super(message);
	    }
	    
	}
	
	// 更新社群貼文狀態 上下架 目前只用於後台
	public void updatePostStatus(Integer spid, Integer spstatus) {
	    SocialPostVO socialPost = repository.findById(spid)
	    		 .orElseThrow(() -> new RuntimeException("Post not found with id " ));
	    socialPost.setSpstatus(spstatus);
	    repository.save(socialPost);
	}
	

	
	
}
