package com.fitanywhere.forumpost.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserService;


@Service("forumPostService")
public class ForumPostService {


        @Autowired
        ForumPostRepository repository;
        
        @Autowired
        UserService userSvc;
        

        public void addForumPost(ForumPostVO forumPostVO) {
            repository.save(forumPostVO);
        }

        public void updateForumPost(ForumPostVO forumPostVO) {
            repository.save(forumPostVO);
        }

        public void deleteForumPost(Integer fpId) {
            if (repository.existsById(fpId))
                repository.deleteByFpStatus(fpId);
        }
        public ForumPostVO getOneForumPost(Integer fpId) {
            Optional<ForumPostVO> optional = repository.findById(fpId);
//            return optional.get();
            return optional.orElse(null);  
        }

        public List<ForumPostVO> getAll() {
        	Sort sortByUpdateTime = Sort.by(Sort.Direction.DESC, "fpUpdate");
            return repository.findAll(sortByUpdateTime);
        }
        
        public boolean existsByFpTitle(String fpTitle) {
            return repository.existsByFpTitle(fpTitle);
        }
        
        public Timestamp getOriginalFpTime(Integer fpId) {
            ForumPostVO forumPostVO = repository.findById(fpId).orElse(null); // 根據fpId查詢資料庫
            if (forumPostVO != null) {
                return forumPostVO.getFpTime(); // 取得原本的發文時間
            }
            return null;
        }
        
        public Integer getOriginalFpViews(Integer fpId) {
            ForumPostVO forumPostVO = repository.findById(fpId).orElse(null);
            if (forumPostVO != null) {
                return forumPostVO.getFpViews(); // 取得原本的觀看數
            }
            return null;
        }

        public byte[] getOriginalFpPic(Integer fpId) {
            ForumPostVO forumPost = repository.findById(fpId).orElse(null);
            if (forumPost != null) {
                return forumPost.getFpPic(); // 取得原本的發文圖片
            }
            return null;
        }

        public void updateViews(int fpId, int fpViews) {
            repository.updateViews(fpId, fpViews);
        }

     // 更新社群貼文狀態 上下架 目前只用於後台
        @Transactional
    	public void updateforumPostStatus(Integer fpId, Integer fpStatus) {
    	    ForumPostVO forumPost = repository.findById(fpId)
    	    		 .orElseThrow(() -> new RuntimeException("Post not found with id " ));
    	    forumPost.setFpStatus(fpStatus);
    	    repository.save(forumPost);
        }

        // Tommy
        public List<ForumPostVO> findFourCourses() {
    		
    		Pageable firstPageWithFourForumPosts = PageRequest.of(0, 4);
    		Page<ForumPostVO> forumPosts = repository.getFourCourses(firstPageWithFourForumPosts);
    		List<ForumPostVO> forumPostListFour = forumPosts.getContent();
    		
    		return forumPostListFour;

    	}
   
}