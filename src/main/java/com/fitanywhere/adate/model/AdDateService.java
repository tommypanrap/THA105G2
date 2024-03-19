package com.fitanywhere.adate.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adDateService")
public class AdDateService {

	@Autowired
	AdDateRepository repository;

	public void addAdDate(AdDateVO adDateVO) {
		repository.save(adDateVO);
	}

	public void updateAdDate(AdDateVO adDateVO) {
		repository.save(adDateVO);
	}
	

//	public void deleteDept(Integer adId) {
//		if (repository.existsById(adId))
//			repository.deleteById(adId);
//	}

// 看目前廣告有哪些方案(標準、premium、基本款)，分別查詢
	public AdDateVO getOneadDateId(Integer adDateId) {
		Optional<AdDateVO> optional = repository.findById(adDateId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<AdDateVO> getAll() {
		return repository.findAll();
	}

}
