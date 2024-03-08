package com.fitanywhere.order.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartService {

    //user:uId:cart
    private final RedisTemplate<String, CartItemVO> redisTemplate;

    @Autowired
    public CartService(RedisTemplate<String, CartItemVO> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }


    //將課程增加到購物車中
    public void addItem(Integer uId, CartItemVO item) {
        String cartKey = "user:" + uId + ":cart";

//        System.out.println(item.toString());
//        redisTemplate.opsForList().rightPush(cartKey, item);
        //判斷購物車內是否已經存在同樣Item

        if (getCartItems(uId).contains(item)) {
            System.out.println("購物車已存在相同課程");
        } else {
            redisTemplate.opsForList().rightPush(cartKey, item);
        }

    }

    //獲取購物車中所有課程的資訊
    public List<CartItemVO> getCartItems(Integer uId) {
        String cartKey = "user:" + uId + ":cart";
        //取得 0 ~ 倒數1個元素
        return redisTemplate.opsForList().range(cartKey, 0, -1);
    }

    //刪除購物車中一項課程
    public void removeItem(Integer uId, Integer crId) {
        String cartKey = "user:" + uId + ":cart";
        CartItemVO item = findItemByCrId(uId, crId);
        redisTemplate.opsForList().remove(cartKey, 0, item);
    }


    //清空購物車
    public void clearCart(Integer uId) {
        String cartKey = "user:" + uId + ":cart";
        redisTemplate.delete(cartKey);
    }

    //用crId 找到該課程
    public CartItemVO findItemByCrId(Integer uId, Integer crId) {
        List<CartItemVO> list = getCartItems(uId);
        return  list.stream().filter(cartItemVO -> cartItemVO.getCrId().equals(crId)).findFirst().orElse(null);
    }

}
