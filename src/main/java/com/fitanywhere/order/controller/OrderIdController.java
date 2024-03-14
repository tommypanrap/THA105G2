package com.fitanywhere.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;

@Controller
@Validated
@RequestMapping("/order")
public class OrderIdController {

	@Autowired
	OrderService orderSvc;

	// 訂單歷史紀錄(目前沒有訂單detail)
	@GetMapping("order_history")
	public String getUserOrders(ModelMap model,HttpSession session) {
			Integer uId =(Integer) session.getAttribute("uId");
				List<OrderVO> orderList = orderSvc.getOrders(uId);
				model.addAttribute("orderListData",orderList);

				return "front-end/order/student_order_history";
			}
	}
	
	
	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request It also validates the user input
	 */
//	@PostMapping("getOne_For_Display")
//	public String getOne_For_Display(
//			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//	@RequestParam("odId") String odId,
//	ModelMap model) {
//		/***************************
//		 * 2.開始查詢資料
//		 *********************************************/
//		OrderVO orderVO = orderSvc.getOneOrder(Integer.valueOf(odId));
//		List<OrderVO> list = orderSvc.getAll();
//		model.addAttribute("orderListData", list); // for select_page.html 第97 109行用
//
//		if (orderVO == null) {
//			model.addAttribute("errorMessage", "查無資料");
//			return "back-end/order/select_page";
//		}
//
//		/***************************
//		 * 3.查詢完成,準備轉交(Send the Success view)
//		 *****************/
//		model.addAttribute("orderVO", orderVO);
//		model.addAttribute("getOne_For_Display", "true");
//
//		return "back-end/order/select_page";
//
//	}

//	@ExceptionHandler(value = { ConstraintViolationException.class })
//	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
//
//		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//		StringBuilder strBuilder = new StringBuilder();
//		for (ConstraintViolation<?> violation : violations) {
//			strBuilder.append(violation.getMessage() + "<br>");
//		}
//		List<OrderVO> list = orderSvc.getAll();
//		model.addAttribute("orderListData", list);
//
//		String message = strBuilder.toString();
//
//		return new ModelAndView("back-end/order/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
//	}

//}
