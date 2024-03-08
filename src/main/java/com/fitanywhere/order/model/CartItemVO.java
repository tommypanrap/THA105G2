package com.fitanywhere.order.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemVO {
    //課程ID
    private Integer crId;

    //課程名稱
    private String crTitle;

    //課程圖片
    private byte[] crCover;

    //課程價格
    private Integer crPrice;

}
