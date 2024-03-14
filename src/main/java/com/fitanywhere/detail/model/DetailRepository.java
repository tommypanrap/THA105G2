package com.fitanywhere.detail.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailRepository extends JpaRepository<DetailVO,Integer> {

    @Query(value = "SELECT * from detail WHERE od_id=?1" ,nativeQuery = true)
    List<DetailVO> findDetailsByOdId(int odId);
}
