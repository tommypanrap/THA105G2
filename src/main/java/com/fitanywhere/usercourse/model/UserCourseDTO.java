package com.fitanywhere.usercourse.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCourseDTO {
    private Integer crId;
    private String crTitle;
    private byte[] crCover;
    private Integer crPrice;
    private String crSubtitle;

    public UserCourseDTO(Integer crId, String crTitle, byte[] crCover, Integer crPrice, String crSubtitle) {
        this.crId = crId;
        this.crTitle = crTitle;
        this.crCover = crCover;
        this.crPrice = crPrice;
        this.crSubtitle = crSubtitle;
    }
}
