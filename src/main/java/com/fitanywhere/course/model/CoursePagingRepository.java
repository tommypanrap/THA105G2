package com.fitanywhere.course.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePagingRepository extends JpaRepository<CourseVO,Integer> {


//    Page<CoursesDTO> findAllCoursesDTO(Pageable pageable);
    Page<CoursesDTO> findAllProjectedBy(Pageable pageable);


}
