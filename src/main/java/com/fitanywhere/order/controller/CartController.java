package com.fitanywhere.order.controller;

import com.fitanywhere.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 將課程新增到購物車中
    @PostMapping("add")
    public List<CartItemVO> addCart(@RequestBody Integer crId, HttpSession session, HttpServletRequest request) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        String uId = uIdInt != null ? uIdInt.toString() : request.getRemoteAddr();
        session.setAttribute("clientIP", uId);

        cartService.addItem(uId, crId);
        return cartService.getCartItems(uId);
    }


    // 獲取購物車中所有課程的資訊
    @GetMapping("get")
    public List<CartItemVO> getItems(ModelMap model, HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        String uId = uIdInt != null ? uIdInt.toString() : (String) session.getAttribute("clientIP");

        List<CartItemVO> list = cartService.getCartItems(uId);






        model.addAttribute("cartItemListData", list);
        return list;
    }

    // 刪除購物車個別課程
    @DeleteMapping("remove/{crId}")
    public List<CartItemVO> deleteItem(@PathVariable Integer crId, HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        String uId = uIdInt != null ? uIdInt.toString() : (String) session.getAttribute("clientIP");

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
        modelAndView.setViewName("front-end/cart/cart");

        Integer uIdInt = (Integer) session.getAttribute("uId");
        String uId = uIdInt != null ? uIdInt.toString() : (String) session.getAttribute("clientIP");

        List<CartItemVO> list = cartService.getCartItems(uId);

        list.forEach(CartItemVO ->
        {
            byte[] crCover = CartItemVO.getCrCover();
            if (crCover!=null){
                String base64CrCover = Base64.getEncoder().encodeToString(crCover);
                CartItemVO.setBase64CrCover(base64CrCover);
            }

        });

        model.addAttribute("cartItemListData", list);

        int TotalPrice = list.stream().mapToInt(CartItemVO::getCrPrice).sum();
        model.addAttribute("TotalPrice", TotalPrice);
        return modelAndView;
    }

    //結帳頁面
    @GetMapping("checkout")
    public ModelAndView checkoutCart(ModelMap model, HttpSession session) {
        Integer uIdInt = (Integer) session.getAttribute("uId");
        String uId = uIdInt != null ? uIdInt.toString() : (String) session.getAttribute("clientIP");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("front-end/cart/checkout");

        return modelAndView;
    }
}
