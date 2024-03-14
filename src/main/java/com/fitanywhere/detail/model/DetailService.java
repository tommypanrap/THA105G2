package com.fitanywhere.detail.model;

import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("detailService")
public class DetailService {

    @Autowired
    DetailRepository repository;

    @Autowired
    OrderService orderSvc;

    public void addDetail(DetailVO detailVO) {
        repository.save(detailVO);

    }

    public void deleteDetail(Integer deId){
        if (repository.existsById(deId)){
            repository.deleteById(deId);
        }

    }

    public void updateDetail(DetailVO detailVO){
        repository.save(detailVO);

    }


    public List<DetailVO> getAll(){
        return repository.findAll();
    }

    public DetailVO getOneDetail(Integer deId){

        return repository.findById(deId).orElse(null);
    }
    // 用訂單編號查找該訂單明細
    public List<DetailVO> getDetailsByOrderId(Integer odId){
       return repository.findDetailsByOdId(odId);
    }
}
