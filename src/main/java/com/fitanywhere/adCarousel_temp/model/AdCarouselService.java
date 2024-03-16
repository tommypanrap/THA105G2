package com.fitanywhere.adCarousel_temp.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("AdCarouselService")
public class AdCarouselService {

	@Autowired
	AdCarouselRepository repository;

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
	public List<AdCarouselVO> getBaseHomePageAd() {

	    List<AdCarouselVO> allAds = repository.findAll();

	    // Get current date and time in UTC
	    ZonedDateTime utcNow = ZonedDateTime.now(ZoneId.of("UTC"));
	    // Convert to a `java.sql.Date` for comparison
	    java.sql.Date currentSqlDate = java.sql.Date.valueOf(utcNow.toLocalDate());

	    List<AdCarouselVO> list = allAds.stream()
	            .filter(ad -> Integer.valueOf(1).equals(ad.getAdcStatus())
	                    && ad.getAdcStartDate().compareTo(currentSqlDate) <= 0
	                    && ad.getAdcEndDate().compareTo(currentSqlDate) >= 0)
	            .collect(Collectors.toList());

	    if(list.isEmpty() || list.size() == 0) {
	        // 若list為空，將第一筆資料放上去(預設)
	        Optional<AdCarouselVO> noAd = repository.findById(1);
	        list.add(noAd.orElse(null));
	        return list;
	    }
	    return list;
	}

	
	//取得某教練下所有有投放課程的廣告
    public List<AdCarouselVO> getAdOrderByUserId(Integer uId) {
        // 從資料庫獲取所有廣告訂單
        List<AdCarouselVO> allAds = repository.findAll();

        // 篩選出特定使用者ID的廣告訂單
        List<AdCarouselVO> userAds = allAds.stream()
                .filter(ad -> ad.getUserVO() != null && uId.equals(ad.getUserVO().getuId())) // 確保廣告屬於該使用者
                .collect(Collectors.toList());

        return userAds;
    }
}
