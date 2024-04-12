package com.fitanywhere.order.controller;

import com.fitanywhere.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;


    // 將課程新增到購物車中
    @PostMapping("add")
    public ResponseEntity<?> addCart(@RequestBody Integer crId, HttpSession session, HttpServletRequest request) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        if (uIdInt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("請先登入！");
        }
        String uId = uIdInt.toString();
        boolean isOwned = cartService.isCourseOwned(uIdInt, crId);

        if (isOwned) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("您已擁有該課程！");
        }

        cartService.addItem(uId, crId);
        return ResponseEntity.ok("成功添加至購物車！");
    }


    // 獲取購物車中所有課程的資訊
    @GetMapping("get")
    public List<CartItemVO> getItems(ModelMap model, HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        if (uIdInt == null) {        	
            return new ArrayList<CartItemVO>();
        }
        String uId = uIdInt.toString();
        List<CartItemVO> list = cartService.getCartItems(uId);
        model.addAttribute("cartItemListData", list);
        return list;
    }

    // 刪除購物車個別課程
    @DeleteMapping("remove/{crId}")
    public List<CartItemVO> deleteItem(@PathVariable Integer crId, HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        if (uIdInt == null) {
            return null;
        }

        String uId = uIdInt.toString();

        cartService.removeItem(uId, crId);


        return cartService.getCartItems(uId);
    }

    // 清空購物車
    @DeleteMapping("clear")
    public String clearItems(HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");

        String uId = uIdInt != null ? uIdInt.toString() : (String) session.getAttribute("clientIP");

        cartService.clearCart(uId);
        return "all clear";
    }

//    --------------------------------------------------


    //確認購物車頁面
    @GetMapping("check")
    public ModelAndView checkCart(ModelMap model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Integer uIdInt = (Integer) session.getAttribute("uId");

        modelAndView.setViewName("front-end/cart/cart");

        String uId = uIdInt.toString();

        List<CartItemVO> list = cartService.getCartItems(uId);

        list.forEach(CartItemVO ->
        {
            byte[] crCover = CartItemVO.getCrCover();
            if (crCover != null) {
                String base64CrCover = Base64.getEncoder().encodeToString(crCover);
                CartItemVO.setBase64CrCover(base64CrCover);
            }

        });

        model.addAttribute("cartItemListData", list);

        int TotalPrice = list.stream().mapToInt(CartItemVO::getCrPrice).sum();
        model.addAttribute("TotalPrice", TotalPrice);
        return modelAndView;
    }

    //結帳頁面(刪)
    @GetMapping("checkout")
    public ModelAndView checkoutCart(ModelMap model, HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        String uId = uIdInt != null ? uIdInt.toString() : (String) session.getAttribute("clientIP");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front-end/cart/checkout");

        return modelAndView;
    }

    // Mok 新增使用者是否已擁有該課程
    @GetMapping("/ownership/{uId}/{crId}")
    public ResponseEntity<Map<String, Boolean>> isCourseOwned(@PathVariable Integer uId, @PathVariable Integer crId) {
        boolean isOwned = cartService.isCourseOwned(uId, crId);
        Map<String, Boolean> response = Collections.singletonMap("hasAccess", isOwned);
        return ResponseEntity.ok(response);
    }

}
