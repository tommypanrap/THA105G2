package com.fitanywhere.order.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.detail.model.DetailService;
import com.fitanywhere.detail.model.DetailVO;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;

import groovy.lang.Binding;
import net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderSvc;


    // 結帳 -> 創建訂單
    @PostMapping("add")
    public ResponseEntity<String> createOrder(@RequestBody OrderVO orderVO, HttpSession session) {
        Integer uId = (Integer) session.getAttribute("uId");
        orderVO.setuId(uId);
        // 確認是否已經登入
//        ModelAndView modelAndView = new ModelAndView();
//        if (uId==null){
//            modelAndView.setViewName("front-end/user/user_login");
//            return modelAndView;
//        }


        // 創建訂單
        orderSvc.addOrder(orderVO);
        //目前先返回訂單歷史頁面
        return ResponseEntity.ok("success");
    }

    @PutMapping("update")
    public String updateOrder(@RequestBody OrderVO orderVO) {
        orderSvc.updateOrder(orderVO);
        return "ok";
    }

    @GetMapping("orders")
    public List<OrderVO> getOrdersByUId(Integer uId){


        return orderSvc.getOrders(uId);
    }

//    @DeleteMapping("delete/{odId}")
//    public String deleteOrder(@PathVariable Integer odId){
//        orderSvc.deleteOrder(odId);
//        return "ok";
//    }

//	@PostMapping("insert")
//	public String insert(@Valid OrderVO orderVO, BindingResult result, ModelMap model
//			) throws IOException {
//
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		if(result.hasErrors()) {
//		return "back-end/order/addOrder";
//		}
//
//
//		// 2. 開始新增資料
//		orderSvc.addOrder(orderVO);
//
//		// 3. 新增完成，準備轉交 (Send the Success view)
//		List<OrderVO> list = orderSvc.getAll();
//		model.addAttribute("orderListData", list);
//		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/order/listAllOrder";// 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/order/listAllOrder")
//	}


//    @PostMapping("getOne_For_Update")
//    public String getOne_For_Update(@RequestParam("odId") String odId, ModelMap model) {
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        /*************************** 2.開始查詢資料 *****************************************/
//        OrderVO orderVO = orderSvc.getOneOrder(Integer.valueOf(odId));
//        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
//        model.addAttribute("orderVO", orderVO);
//        return "back-end/order/update_order_input";
//    }


//    @PostMapping("update")
//    public String update(@Valid OrderVO orderVO, BindingResult result, ModelMap model) {
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        if (result.hasErrors()) {
//            return "back-end/order/update_order_input";
//        }
//        /*************************** 2.開始修改資料 *****************************************/
//        orderSvc.updateOrder(orderVO);
//        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//        model.addAttribute("success", "- (修改成功)");
//        orderVO = orderSvc.getOneOrder(Integer.valueOf(orderVO.getOdId()));
//        model.addAttribute("orderVO", orderVO);
//        return "back-end/order/listOneOrder";
//
//    }


//    @PostMapping("delete")
//    public String delete(@RequestParam("odId") String odId, ModelMap model) {
//        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//        /*************************** 2.開始刪除資料 *****************************************/
//
//        orderSvc.deleteOrder(Integer.valueOf(odId));
//        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
//        List<OrderVO> list = orderSvc.getAll();
//        model.addAttribute("orderListData", list);
//        model.addAttribute("success", "- (刪除成功)");
//        return "back-end/order/listAllOrder"; // 刪除完成後轉交listAllOrder.html
//
//    }


//	@ModelAttribute("orderListData")
//	protected List<OrderVO> referenceListData() {
//
//		List<OrderVO> list = orderSvc.getAll();
//		return list;
//
//	}


    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(OrderVO orderVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
        result = new BeanPropertyBindingResult(orderVO, "orderVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }
}