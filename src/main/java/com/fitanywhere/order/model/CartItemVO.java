package com.fitanywhere.order.model;

import com.fitanywhere.course.model.CourseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
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


    private String base64CrCover;

    public CartItemVO(CourseVO courseVO) {
        this.crId = courseVO.getCrId();
        this.crTitle = courseVO.getCrTitle();
        this.crCover = courseVO.getCrCover();
        this.crPrice = courseVO.getCrPrice();
        this.base64CrCover = courseVO.getBase64CrCover();
    }
}


