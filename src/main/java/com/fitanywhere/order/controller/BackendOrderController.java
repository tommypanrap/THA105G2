package com.fitanywhere.order.controller;

import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/backend")
public class BackendOrderController {

    @Autowired
    OrderService orderSvc;

    @Autowired
    private OrderService orderService;

    @GetMapping("order-get")
    public String backend_course_order(Model model) {
        List<OrderVO> allOrders = orderService.getAll();
        model.addAttribute("allOrders", allOrders);


        return "back-end/backend_course_order";
    }
    @PutMapping("update/status")
    public ResponseEntity<?> updateOdStatus(@RequestBody OrderVO orderVO) {
        boolean success = orderSvc.updateOdStatus(orderVO);
        if (success) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.ok().body("error");
    }
}
