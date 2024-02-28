package com.fitanywhere.socialpost.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("socialService")
public class SocialPostService {
	
	@Autowired
	SocialPostRepository repository;
	
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
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}
	
	public List<SocialPostVO> getAll() {
		return repository.findAll();
	}
	
//	public List<EmpVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}
	
	
}
