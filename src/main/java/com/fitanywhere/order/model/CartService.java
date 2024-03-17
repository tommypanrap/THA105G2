package com.fitanywhere.order.model;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.detail.model.DetailDTO;
import com.fitanywhere.detail.model.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class CartService {

    // user:uId:cart
    private final RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private CourseService courseSvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DetailService detailService;

    @Autowired
    public CartService(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }


    // 獲取使用者已擁有的課程ID Set
    public Set<Integer> getCoursesUesrOwned(Integer uId) {
        if (uId == null){
             return null;
        }
        String cartKey = "user:" + uId + ":ownedCourse";
        // 回傳使用者的已擁有課程 crId Set
        return redisTemplate.opsForSet().members(cartKey);
    }

    // 使用者是否已擁有該課程
    public boolean isCourseOwned(Integer uId, Integer crId) {
        // 使用者是否已登入
        if (uId == null){
            return false;
        }
        String cartKey = "user:" + uId + ":ownedCourse";

        // 如果Redis 沒有該使用者已擁有的key數據 直接 return false
        if (Boolean.FALSE.equals(redisTemplate.hasKey(cartKey))) {
            return false;
        }

        // 最後檢查使用者已擁有課程裡面是否擁有該課程
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(cartKey, crId));
    }

    // 將已登入使用者已擁有的課程存入Redis
    public void storeOwnedCoursesInRedis(Integer uId) {
        // 假設使用者已登入
        // 獲取使用者ID
        // 使用者已購買課程 先存入redis

        if (uId == null) {
            return;
        }
        String cartKey = "user:" + uId + ":ownedCourse";
        List<OrderVO> orders = orderService.getOrders(uId);

        if (orders.isEmpty()) {
            return;
        }

        for (OrderVO order : orders) {
            List<DetailDTO> details = detailService.getDetailDTOByOrderId(order.getOdId());
            for (DetailDTO detail : details) {
                Integer crId = detail.getCrId();
                redisTemplate.opsForSet().add(cartKey, crId);
            }
        }
    }

    //將課程增加到購物車中
    public void addItem(String uId, Integer crId) {
        String cartKey = "user:" + uId + ":cart";
        //判斷購物車內是否已經存在同樣Item
        Double score = redisTemplate.opsForZSet().score(cartKey, crId);
        if (score == null) {
            redisTemplate.opsForZSet().add(cartKey, crId, System.currentTimeMillis());
        }
    }

    //獲取購物車中所有課程的資訊
    public List<CartItemVO> getCartItems(String uId) {
        String cartKey = "user:" + uId + ":cart";
        //取得 0 ~ 倒數1個元素
        Set<Integer> cartCrIds = redisTemplate.opsForZSet().range(cartKey, 0, -1);
        List<CartItemVO> list = null;
        if (cartCrIds != null) {
            list = cartCrIds.stream()
                    .map(crId -> courseSvc.getOneCourse(crId))
                    .map(CartItemVO::new).toList();
        }
        return list;
    }

    //刪除購物車中一項課程
    public void removeItem(String uId, Integer crId) {
        String cartKey = "user:" + uId + ":cart";
//        redisTemplate.opsForList().remove(cartKey, 0, crId);
        redisTemplate.opsForZSet().remove(cartKey, crId);
    }


    //清空購物車
    public void clearCart(String uId) {
        String cartKey = "user:" + uId + ":cart";
        redisTemplate.delete(cartKey);
    }

    //用crId 找到該課程
    public CartItemVO findItemByCrId(String uId, Integer crId) {
        List<CartItemVO> list = getCartItems(uId);
        return list.stream().filter(cartItemVO -> cartItemVO.getCrId().equals(crId)).findFirst().orElse(null);
    }

}