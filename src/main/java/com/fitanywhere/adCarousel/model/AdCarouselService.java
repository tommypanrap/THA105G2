package com.fitanywhere.adCarousel.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("AdCarouselService")
public class AdCarouselService {

	@Autowired
	adCarouselRepository repository;

	public void addAdCarousel(AdCarouselVO adCarouselVO) {
		repository.save(adCarouselVO);
	}

	public void updateAdCarousel(AdCarouselVO adCarouselVO) {
		repository.save(adCarouselVO);
	}

	public AdCarouselVO getOneAdcId(Integer adcId) {
		Optional<AdCarouselVO> optional = repository.findById(adcId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<AdCarouselVO> getAll() {
		return repository.findAll();
	}

}
