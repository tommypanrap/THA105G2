package com.fitanywhere.adcarousel.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fitanywhere.course.model.CourseVO;

public interface AdCarouselRepository extends JpaRepository<AdCarouselVO, Integer> {
 
// 將結束日期已經過了的方案狀態改為下架
  @Modifying
  @Transactional
  @Query(value = "UPDATE ad_carousel_order SET adc_status = 0 WHERE adc_end_date < NOW()", nativeQuery = true)
  void updateAdCarouselOrderStatusDirectly();
  
// @Query(value = "SELECT * FROM ad WHERE ad_id = ?1", nativeQuery = true)
// List<AdCarouselVO> getDayPriceByAdId(Integer adId); 
}