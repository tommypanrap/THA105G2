package com.fitanywhere.order.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class CartVO {
    private Integer uId;
    private List<CartItemVO> items;
}
