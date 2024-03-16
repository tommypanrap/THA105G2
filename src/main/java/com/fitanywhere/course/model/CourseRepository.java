// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.fitanywhere.course.model;

import java.util.List;

import javax.persistence.NamedNativeQuery;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<CourseVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from course where cr_id =?1", nativeQuery = true)
	void deleteByCrid(int crId);

	@Query("SELECT COUNT(*) FROM CourseVO WHERE u_Id = :uId")
    Integer getCourseCount(Integer uId);
	
@Query(value="SELECT SUM(cr_tot_star) AS total_star FROM (SELECT SUM(cr_tot_star) AS cr_tot_star FROM course WHERE u_id = :uId GROUP BY cr_id) AS subquery", nativeQuery = true)
	Integer getTotalStarCount(Integer uId);
	
	@Modifying
	@Query(value="SELECT * FROM course WHERE u_Id = :uId", nativeQuery = true)
    List<CourseVO> getAllcourseByuId(Integer uId);
	
	@Query(value="SELECT cr_class, cr_title, cr_cover, cr_price, cr_id FROM course WHERE u_Id = :uId AND cr_state = 0", nativeQuery = true)
	List<Object[]> getCourseByStatus0(Integer uId);
	
	@Query(value="SELECT cr_tot_star, cr_cm_quan, cr_class, cr_title, cr_cover, cr_price FROM course WHERE u_Id = :uId AND cr_state = 1", nativeQuery = true)
	List<Object[]> getCourseByStatus1(Integer uId);
	
	@Query(value="SELECT cr_class, cr_title, cr_cover, cr_price FROM course WHERE u_Id = :uId AND cr_state = 2", nativeQuery = true)
	List<Object[]> getCourseByStatus2(Integer uId);

	// 讀取指定crId的crCover
	@Query("SELECT new com.fitanywhere.course.model.CourseCrCoverDTO(c.crId, c.crCover) FROM CourseVO c WHERE c.crId = :crId")
	CourseCrCoverDTO findCourseCrCoverById(@Param("crId") Integer crId);

	// uId找課程
	@Query(value = "SELECT * FROM course WHERE u_id = ?1", nativeQuery = true)
	List<CourseVO> getCourseByUId(Integer uId); 
	
    }