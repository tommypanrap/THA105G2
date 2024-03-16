// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.adDDate.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdDateRepository extends JpaRepository<AdDateVO, Integer> {
    
}
