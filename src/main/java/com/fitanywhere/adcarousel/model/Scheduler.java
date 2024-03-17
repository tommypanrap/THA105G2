package com.fitanywhere.adcarousel.model;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.fitanywhere.adcarousel.model.AdCarouselRepository;

@Component
public class Scheduler {

// 設置排程器每當午夜時檢查是否有廣告需要被下架(更改AdcStatus = 0)
	private final AdCarouselRepository adCarouselRepository;

	// 通過構造器注入AdCarouselRepository
	public Scheduler(AdCarouselRepository adCarouselRepository) {
		this.adCarouselRepository = adCarouselRepository;
	}

	// 每天凌晨0點執行
	@Scheduled(cron = "0 0 */6 * * *")
	public void updateAdCarouselStatus() {
		System.out.println("執行廣告狀態更新: " + LocalDateTime.now());
		adCarouselRepository.updateAdCarouselOrderStatusDirectly();
	}
}
