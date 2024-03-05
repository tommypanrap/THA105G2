package com.fitanywhere.order.controller;

import com.fitanywhere.course.model.CartVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping("/add")
    public String addCart(@RequestBody CartVO cartVO, HttpSession session) {
        Integer uId = 10001;//先寫死等登
//		Integer uId = (Integer)session.getAttribute("uId");
        String cartKey = "user:" + uId + ":cart";
        JedisPooled jedis = new JedisPooled("localhost", 6379);
        jedis.sadd(cartKey, cartVO.getCourseId().toString());

        System.out.println(cartVO);
        return "{\"result\": \"good!\"}";
    }

    @PostMapping("/get")
    public  String getCart(@RequestBody CartVO cartVO, HttpSession session ){
        Integer uId = 10001;//先寫死等登
//		Integer uId = (Integer)session.getAttribute("uId");

        JedisPooled jedis = new JedisPooled("localhost", 6379);
        jedis.get("user:"+uId+":cart");


        return "{\"result\": \"ok!\"}";
    }

}
