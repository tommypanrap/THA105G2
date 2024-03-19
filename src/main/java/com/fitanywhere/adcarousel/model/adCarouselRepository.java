// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.adcarousel.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface adCarouselRepository extends JpaRepository<AdCarouselVO, Integer> {


}