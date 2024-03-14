package com.fitanywhere.forumpost.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ForumPostRepository extends JpaRepository<ForumPostVO, Integer> {
//    @Transactional
//    @Modifying
//    @Query(value = "delete from forum_post where fp_id =?1", nativeQuery = true)
//    void deleteByFpId(int FpId);

	@Transactional
	@Modifying
	@Query(value = "update forum_post set fp_status = 0 where fp_id = ?1", nativeQuery = true)
	void deleteByFpStatus(int FpId);

	// 使用 LEFT JOIN 來聯合 ForumPostVO 和 ForumReplyVO 表格
	// 如果您要找到包含標題、作者或內容的結果，我們將使用 OR 邏輯運算符來組合不同的條件
	// 您可以將關鍵字參數傳遞給此查詢以查找包含該關鍵字的結果
	@Query("SELECT DISTINCT fp FROM ForumPostVO fp LEFT JOIN ForumReplyVO r " + "ON fp.fpId = r.forumPostVO.fpId " + // 左外部連接
																														// ForumReplyVO
																														// 表格
			"WHERE fp.fpTitle LIKE %:keyword% OR " + // 標題包含關鍵字
			"fp.fpContent LIKE %:keyword% OR " + // 內容包含關鍵字
			"r.frContent LIKE %:keyword%") // 回覆的內容包含關鍵字
	List<ForumPostVO> findByKeyword(@Param("keyword") String keyword);

	boolean existsByFpTitle(String fpTitle);


}