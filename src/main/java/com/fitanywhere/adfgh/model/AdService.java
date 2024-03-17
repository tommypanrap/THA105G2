package com.fitanywhere.adfgh.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("adService")
public class AdService {

	@Autowired
	AdRepository repository;

	public void addAd(AdVO adVO) {
		repository.save(adVO);
	}

	public void updateAd(AdVO adVO) {
		repository.save(adVO);
	}
	

//	public void deleteDept(Integer adId) {
//		if (repository.existsById(adId))
//			repository.deleteById(adId);
//	}

// 看目前廣告有哪些方案(標準、premium、基本款)，分別查詢
	public AdVO getOneAd(Integer adId) {
		Optional<AdVO> optional = repository.findById(adId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

//	得到全部的方案
	public List<AdVO> getAll() {
		return repository.findAll();
	}

}
