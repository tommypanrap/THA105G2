package com.fitanywhere.order.controller;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;

import groovy.lang.Binding;
import net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderSvc;

	// add one Order
	@GetMapping("addOrder")
	public String addOrder(ModelMap model) {
		OrderVO orderVO = new OrderVO();
		model.addAttribute("orderVO", orderVO);
		return "back-end/order/addOrder";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid OrderVO orderVO, BindingResult result, ModelMap model
			) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		if(result.hasErrors()) {
		return "back-end/order/addOrder";	
		}
		
		
		// 2. 開始新增資料
		orderSvc.addOrder(orderVO);

		// 3. 新增完成，準備轉交 (Send the Success view)
		List<OrderVO> list = orderSvc.getAll();
		model.addAttribute("orderListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/order/listAllOrder";// 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/order/listAllOrder")
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("odId") String odId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		OrderVO orderVO = orderSvc.getOneOrder(Integer.valueOf(odId));
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("orderVO", orderVO);
		return "back-end/order/update_order_input";
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling
	 * POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid OrderVO orderVO, BindingResult result, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		if (result.hasErrors()) {
			return "back-end/order/update_order_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		orderSvc.updateOrder(orderVO);
		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		orderVO = orderSvc.getOneOrder(Integer.valueOf(orderVO.getOdId()));
		model.addAttribute("orderVO", orderVO);
		return "back-end/order/listOneOrder";

	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("odId") String odId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/

		orderSvc.deleteOrder(Integer.valueOf(odId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<OrderVO> list = orderSvc.getAll();
		model.addAttribute("orderListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/order/listAllOrder"; // 刪除完成後轉交listAllOrder.html

	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : <form:select
	 * path="deptno" id="deptno" items="${deptListData}" itemValue="deptno"
	 * itemLabel="dname" />
	 */
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