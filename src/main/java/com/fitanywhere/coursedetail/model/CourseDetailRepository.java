package com.fitanywhere.coursedetail.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseDetailRepository extends JpaRepository<CourseDetailVO, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from course_detail where cd_id = ?1", nativeQuery = true)
    void deleteByCdId(int cdId);

    // 查cd_video影片
    @Query(value = "SELECT cd_video FROM course_detail WHERE cd_id = ?1", nativeQuery = true)
    String getCourseVideoPath(Integer cdId);

    // 查sale_video影片
    @Query(value = "SELECT cd_sale_video FROM course_detail WHERE cd_id = ?1", nativeQuery = true)
    String getSaleVideoPath(Integer cdId);

    // 根據課程ID獲取課程下所有單元影片的資訊
    @Query(value = "SELECT * FROM course_detail WHERE cr_id = ?1", nativeQuery = true)
    List<CourseDetailVO> findVideosByCourseId(Integer crId);

    // 計算單元數量
    @Query(value = "SELECT COUNT(cd_unit) FROM course_detail WHERE cr_id = :crId", nativeQuery = true)
    Integer getunitCount(Integer crId);
}
