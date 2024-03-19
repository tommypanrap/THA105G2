package com.fitanywhere.order.controller;

import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderIdController {

    @Autowired
    OrderService orderSvc;

    // 訂單歷史紀錄(目前沒有訂單detail)
    @GetMapping("order_history")
    public String getUserOrders(ModelMap model, HttpSession session) {
        Integer uId = (Integer) session.getAttribute("uId");
        List<OrderVO> orderList = orderSvc.getOrders(uId);
        model.addAttribute("orderListData", orderList);
        model.addAttribute("orderCount",orderList.size());
        return "front-end/order/student_order_history";
    }
}
	

