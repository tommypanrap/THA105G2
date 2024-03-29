// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.ad.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<AdVO, Integer> {
    
}
