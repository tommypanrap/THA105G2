package com.fitanywhere.order.controller;

import com.fitanywhere.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;

import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 將課程新增到購物車中
    @PostMapping("add")
    public List<CartItemVO> addCart(@RequestBody CartItemVO cartItemVO, HttpSession session) {
        Integer uId = (Integer) session.getAttribute("uId");
        cartService.addItem(uId, cartItemVO);
        List<CartItemVO> list = cartService.getCartItems(uId);
        return list;
    }


    // 獲取購物車中所有課程的資訊
    @GetMapping("get")
    public List<CartItemVO> getItems(ModelMap model, HttpSession session) {
        Integer uId = (Integer) session.getAttribute("uId");
        List<CartItemVO> list = cartService.getCartItems(uId);
        model.addAttribute("cartItemListData", list);
        return list;
    }

    // 刪除購物車個別課程
    @DeleteMapping("remove/{crId}")
    public List<CartItemVO> deleteItem(@PathVariable Integer crId, HttpSession session){
        Integer uId = (Integer) session.getAttribute("uId");
        cartService.removeItem(uId,crId);


        return cartService.getCartItems(uId);
    }

    // 清空購物車
    @DeleteMapping("clear")
    public String clearItems(HttpSession session){
        Integer uId = (Integer) session.getAttribute("uId");
       cartService.clearCart(uId);
       return "all clear";
    }

//    --------------------------------------------------


    //確認購物車頁面
    @GetMapping("check")
    public ModelAndView checkCart(ModelMap model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front-end/cart/cart");
        return modelAndView;
    }

    //結帳頁面
    @GetMapping("checkout")
    public ModelAndView checkoutCart(ModelMap model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front-end/cart/checkout");
        return modelAndView;
    }
}
