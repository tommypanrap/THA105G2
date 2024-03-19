package com.fitanywhere.order.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderVO, Integer> {

//	@Transactional
//	@Modifying
//	@Query(value = "delete from `order` where od_id = ?1", nativeQuery = true)
//	void deleteByOdId(int odId);

//	@Modifying
//	@Query(value = "UPDATE OrderVO SET OrderVO.odStatus= :odStatus WHERE odId = :orderId")
//	void updateOdStatusByOdId(Integer odStatus,Integer odId);


//	@Transactional
//	@Modifying
//	//	INSERT INTO `order` (od_id, od_create_date, u_id, od_end_date, od_status, od_price, dc_id)VALUES(2, '2023-08-03 12:30:00', 10002, '2023-08-04 18:45:00', 1, 70, 2);
//	@Query(value = "INSERT INTO `order` (u_id , dc_id , od_status , od_price)VALUES(?1 , ?2 , ?3 , ?4)" , nativeQuery = true)
//	void insertOrder(int uId, int dcId, int odStatus, int odPrice);
//
	@Query(value="FROM OrderVO WHERE uId = :uId")
	List<OrderVO> findByuId(Integer uId);

	// Mok 我的課程抓order
	@Query(value = "SELECT u_id FROM `order` WHERE u_id = 1?", nativeQuery = true)
	boolean existsByOrder(Integer uId, Integer crId);

	}

