package com.fitanywhere.order.model;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.detail.model.DetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("orderService")
public class OrderService {
    @Autowired
    OrderRepository repository;

    @Autowired
    CourseService courseSvc;

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

    public boolean updateOdStatus(OrderVO orderVO) {
        if (orderVO.getOdId() != null) {
            if (repository.existsById(orderVO.getOdId())) {
                repository.updateOdStatus(orderVO.getOdId(), orderVO.getOdStatus());
                return true;
            }
        }
        return false;
    }


    public OrderVO getOneOrder(Integer odId) {
        Optional<OrderVO> optional = repository.findById(odId);

        return optional.orElse(null);
    }

    public List<OrderVO> getAll() {
        return repository.findAll();
    }

}
