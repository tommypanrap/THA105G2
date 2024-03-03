package com.fitanywhere.order.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.util.HibernateUtil;

@Service("orderService")
public class OrderService {
	@Autowired
	OrderRepository repository;

//	@Autowired
//	private SessionFactory sessionFactory;

//	public void addOrder(OrderVO orderVO) {
//		repository.save(orderVO);
//	}
	public List<OrderVO> getOrders(Integer uId) {
		return repository.findByuId(uId);
	}
		
	public void addOrder(OrderVO orderVO) {
		repository.insertOrder(
				orderVO.getuId(), 
				orderVO.getDcId(), 
				orderVO.getOdStatus(),
				orderVO.getOdPrice());
	}

	public void updateOrder(OrderVO orderVO) {
		repository.save(orderVO);
	}

	public void deleteOrder(Integer odId) {
		if (repository.existsById(odId)) {
			repository.deleteByOdId(odId);
		}
	}

	public OrderVO getOneOrder(Integer odId) {
		Optional<OrderVO> optional = repository.findById(odId);

		return optional.orElse(null);
	}

	public List<OrderVO> getAll() {
		return repository.findAll();
	}

//	public List<OrderVO> getAll(Map<String, String[]> map) {
//
//		return HibernateUtil_CompositeQuery_Order.getAllC(map, sessionFactory.openSession());
//	}
}
