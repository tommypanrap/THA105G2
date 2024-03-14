package com.fitanywhere.order.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.detail.model.DetailVO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.util.HibernateUtil;

@Service("orderService")
public class OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    CourseService courseSvc;

//	@Autowired
//	private SessionFactory sessionFactory;

    //	public void addOrder(OrderVO orderVO) {
//		repository.save(orderVO);
//	}
    public List<OrderVO> getOrders(Integer uId) {
        return repository.findByuId(uId);
    }

    public void addOrder(OrderVO orderVO) {
        updatePrice(orderVO);
        updateOrder(orderVO);
        repository.save(orderVO);
    }
    private void updatePrice(OrderVO orderVO) {
        int odPrice = 0;
        for (DetailVO detailVO : orderVO.getDetails()) {
            // 綁定訂單編號
            detailVO.setOrderVO(orderVO);
            // 獲取課程價格
            Integer crPrice = courseSvc.getOneCourse(detailVO.getCrId()).getCrPrice();
            // 價格 set 進 Detail
            detailVO.setCdPrice(crPrice);
            odPrice += detailVO.getCdPrice();
        }
        // 更新訂單總金額
        orderVO.setOdPrice(odPrice);
    }
    public void updateOrder(OrderVO orderVO) {
        if (orderVO.getOdId() != null) {
            if (repository.existsById(orderVO.getOdId())) {
                updatePrice(orderVO);
                repository.save(orderVO);
            }
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
