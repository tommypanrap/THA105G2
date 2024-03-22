package com.fitanywhere.forumpost.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fitanywhere.course.model.CourseCrCoverDTO;
import com.fitanywhere.course.model.CourseVO;

public interface ForumPostRepository extends JpaRepository<ForumPostVO, Integer> {
//    @Transactional
//    @Modifying
//    @Query(value = "delete from forum_post where fp_id =?1", nativeQuery = true)
//    void deleteByFpId(int FpId);

	@Transactional
	@Modifying
	@Query(value = "update forum_post set fp_status = 0 where fp_id = ?1", nativeQuery = true)
	void deleteByFpStatus(int FpId);

	boolean existsByFpTitle(String fpTitle);
	
	@Modifying
	@Transactional
	@Query("UPDATE ForumPostVO f SET f.fpViews = :fpViews WHERE f.fpId = :fpId")
	void updateViews(@Param("fpId") int fpId, @Param("fpViews") int fpViews);
	
	// Tommy
	@Query("SELECT f FROM ForumPostVO f")
	Page<ForumPostVO> getFourCourses(Pageable pageable);
	
	@Query("SELECT new com.fitanywhere.forumpost.model.ForumPostGetCoverDTO(f.fpId, f.fpPic) FROM ForumPostVO f WHERE f.fpId = :fpId")
	ForumPostGetCoverDTO findForumPostCoverById(@Param("fpId") Integer fpId);
	
	

}