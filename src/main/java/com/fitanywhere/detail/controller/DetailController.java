package com.fitanywhere.detail.controller;

import com.fitanywhere.detail.model.DetailService;
import com.fitanywhere.detail.model.DetailVO;
import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detail")
public class DetailController {

    @Autowired
    DetailService detailService;
    @Autowired
    OrderService orderService;


    // 新增訂單名細
    @PostMapping("insert")
    public List<DetailVO> insert(@RequestBody DetailVO detailVO) {
        detailService.addDetail(detailVO);
        return detailService.getDetailsByOrderId(detailVO.getOrderVO().getOdId());
    }

    // 刪除訂單名細
    @DeleteMapping("delete/{deId}")
    public List<DetailVO> delete(@PathVariable Integer deId) {
        // 刪除
        detailService.deleteDetail(deId);
        // 返回該筆訂單的明細
        DetailVO detailVO = detailService.getOneDetail(deId);
        return detailService.getDetailsByOrderId(detailVO.getOrderVO().getOdId());
    }

    // 修改訂單名細
    @PutMapping("update")
    public List<DetailVO> update(DetailVO detailVO) {
        detailService.updateDetail(detailVO);
        // 需要把總金額重新set回Order
        return detailService.getDetailsByOrderId(detailVO.getOrderVO().getOdId());
    }



    @PostMapping("details")
    public List<DetailVO> getDetailsByOdId(Integer odId){

        return detailService.getDetailsByOrderId(odId);
    }

}
