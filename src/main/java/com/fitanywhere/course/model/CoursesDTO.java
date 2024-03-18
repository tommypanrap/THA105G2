package com.fitanywhere.course.model;


import com.fitanywhere.user.model.UserVO;

import java.io.Serializable;



public class CoursesDTO implements Serializable {
    // 課程ID
    private Integer crId;
    // 課程標題
    private String crTitle;
    // 課程介紹crSubtitle
    private String crSubtitle;
    // 課程價格
    private Integer crPrice;
    // 課程擁有者(教練)ID
    private Integer uId;
    // 課程擁有者(教練)名稱
    private String uNickName;


    public CoursesDTO(){};

    public CoursesDTO(Integer crId, String crTitle, String crSubtitle, Integer crPrice,Integer uId,String uNickName) {
        this.crId = crId;
        this.crTitle = crTitle;
        this.crSubtitle = crSubtitle;
        this.crPrice = crPrice;
        this.uId = uId;
        this.uNickName = uNickName;
    }

    public CoursesDTO(CourseVO courseVO){
        this.crId = courseVO.getCrId();
        this.crTitle = courseVO.getCrTitle();
        this.crSubtitle =courseVO.getCrSubtitle();
        this.crPrice = courseVO.getCrPrice();
        UserVO userVO = courseVO.getUserVO();
        this.uId = userVO.getuId();
        this.uNickName = userVO.getuName();
    }

    public String getuNickName() {
        return uNickName;
    }

    public void setuNickName(String uNickName) {
        this.uNickName = uNickName;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public String getCrTitle() {
        return crTitle;
    }

    public void setCrTitle(String crTitle) {
        this.crTitle = crTitle;
    }

    public String getCrSubtitle() {
        return crSubtitle;
    }

    public void setCrSubtitle(String crSubtitle) {
        this.crSubtitle = crSubtitle;
    }

    public Integer getCrPrice() {
        return crPrice;
    }

    public void setCrPrice(Integer crPrice) {
        this.crPrice = crPrice;
    }
}
