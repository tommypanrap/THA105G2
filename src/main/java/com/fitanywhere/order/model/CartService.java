package com.fitanywhere.order.model;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartService {

    //user:uId:cart
    private final RedisTemplate<String,Integer> redisTemplate;

    @Autowired
    private CourseService courseSvc;

    @Autowired
    public CartService(RedisTemplate<String,Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }


    //將課程增加到購物車中
    public void addItem(String uId, Integer crId) {
        String cartKey = "user:" + uId + ":cart";
        //判斷購物車內是否已經存在同樣Item
        boolean exists = getCartItems(uId).stream().anyMatch(cartItemVO -> cartItemVO.getCrId().equals(crId));
        if (!exists) {
            redisTemplate.opsForList().rightPush(cartKey, crId);
        }
    }

    //獲取購物車中所有課程的資訊
    public List<CartItemVO> getCartItems(String uId) {
        String cartKey = "user:" + uId + ":cart";
        //取得 0 ~ 倒數1個元素
        List<Integer> cartCrIds = redisTemplate.opsForList().range(cartKey, 0, -1);
        List<CartItemVO> list = null;
        if (cartCrIds!=null){
            list = cartCrIds.stream()
                    .map(crId -> courseSvc.getOneCourse(crId))
                    .map(CartItemVO::new).toList();

        }
        return list;
    }

    //刪除購物車中一項課程
    public void removeItem(String uId, Integer crId) {
        String cartKey = "user:" + uId + ":cart";
        redisTemplate.opsForList().remove(cartKey, 0, crId);
    }


    //清空購物車
    public void clearCart(String uId) {
        String cartKey = "user:" + uId + ":cart";
        redisTemplate.delete(cartKey);
    }

    //用crId 找到該課程
    public CartItemVO findItemByCrId(String uId, Integer crId) {
        List<CartItemVO> list = getCartItems(uId);
        return  list.stream().filter(cartItemVO -> cartItemVO.getCrId().equals(crId)).findFirst().orElse(null);
    }

}
