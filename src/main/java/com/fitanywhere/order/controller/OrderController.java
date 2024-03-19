package com.fitanywhere.order.controller;

import com.fitanywhere.order.model.CartService;
import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderSvc;

    @Autowired
    CartService cartService;

    // 結帳 -> 創建訂單
    @PostMapping("add")
    public ResponseEntity<String> createOrder(@RequestBody OrderVO orderVO, HttpSession session) {
        Integer uId = (Integer) session.getAttribute("uId");
        if (uId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入！");
        }

        orderVO.setuId(uId);

        // 購物車為空
        if (orderVO.getDetails().isEmpty()) {
            return ResponseEntity.ok().body("cartIsEmpty");
        }

        // 創建訂單
        orderSvc.addOrder(orderVO);

        // 重新抓取使用者已購買課程
        cartService.storeOwnedCoursesInRedis(uId);

        // 清空購物車
        cartService.clearCart(uId.toString());

        // 目前先返回訂單歷史頁面
        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, "/order/order_history")
                .body("success");
    }

    @PutMapping("update")
    public String updateOrder(@RequestBody OrderVO orderVO) {
//        OrderVO orderVO1 = orderSvc.getOneOrder(orderVO.getOdId());
//        orderVO1.setOdStatus(orderVO.getOdStatus());
        orderSvc.updateOrder(orderVO);
        return "ok";
    }

    @GetMapping("orders")
    public List<OrderVO> getOrdersByUId(Integer uId) {


        return orderSvc.getOrders(uId);
    }


}