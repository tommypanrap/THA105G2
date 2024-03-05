package com.fitanywhere.course.model;

public class CartVO {
    private Integer courseId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "courseId=" + courseId +
                '}';
    }
}
