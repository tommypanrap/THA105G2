package com.fitanywhere.order.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderVO, Integer> {


    @Query(value = "FROM OrderVO WHERE uId = :uId")
    List<OrderVO> findByuId(Integer uId);



    @Transactional
    @Modifying
    @Query(value = "UPDATE `order` SET od_status = :odStatus WHERE od_id = :odId", nativeQuery = true)
    void updateOdStatus(@Param("odId") Integer odId, @Param("odStatus") Integer odStatus);

}
