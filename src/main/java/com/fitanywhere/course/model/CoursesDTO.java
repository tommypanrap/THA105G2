package com.fitanywhere.course.model;


import java.io.Serializable;



public class CoursesDTO implements Serializable {
    // 課程ID
    private Integer crId;
    // 課程標題
    private String crTitle;
    // 課程介紹crSubtitle
    private String crSubtitle;

    private Integer crPrice;
    public CoursesDTO(){};

    public CoursesDTO(Integer crId, String crTitle, String crSubtitle, Integer crPrice) {
        this.crId = crId;
        this.crTitle = crTitle;
        this.crSubtitle = crSubtitle;
        this.crPrice = crPrice;
    }

    public CoursesDTO(CourseVO courseVO){
        this.crId = courseVO.getCrId();
        this.crTitle = courseVO.getCrTitle();
        this.crSubtitle =courseVO.getCrSubtitle();
        this.crPrice = courseVO.getCrPrice();
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
