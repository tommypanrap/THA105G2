package com.fitanywhere.forumpost.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service("forumPostService")
public class ForumPostService {


        @Autowired
        ForumPostRepository repository;

        public void addForumPost(ForumPostVO forumPostVO) {
            repository.save(forumPostVO);
        }
//        @Transactional
//        public void updateForumPost(ForumPostVO updatedForumPost) throws Exception {
//            Optional<ForumPostVO> existingPostOptional = repository.findById(updatedForumPost.getFpId());
//            
//            if (existingPostOptional.isPresent()) {
//                ForumPostVO existingPost = existingPostOptional.get();
//                
//                existingPost.setFpCategory(updatedForumPost.getFpCategory());
//                existingPost.setFpTitle(updatedForumPost.getFpTitle());
//                existingPost.setFpContent(updatedForumPost.getFpContent());
//                existingPost.setFpStatus(updatedForumPost.getFpStatus());
//                existingPost.setFpTime(updatedForumPost.getFpTime());
//                existingPost.setFpUpdate(updatedForumPost.getFpUpdate());
//                existingPost.setFpPic(updatedForumPost.getFpPic());
//                existingPost.setFpViews(updatedForumPost.getFpViews());
//                existingPost.setFpFavorite(updatedForumPost.getFpFavorite());
//                
//                repository.save(existingPost);
//            } else {
//            	throw new Exception("Post with ID " + updatedForumPost.getFpId() + " not found");
//            }
//        }


        public void updateForumPost(ForumPostVO forumPostVO) {
            repository.save(forumPostVO);
        }

        public void deleteForumPost(Integer fpId) {
            if (repository.existsById(fpId))
                repository.deleteByFpId(fpId);
        }

        public ForumPostVO getOneForumPost(Integer fpId) {
            Optional<ForumPostVO> optional = repository.findById(fpId);
//            return optional.get();
            return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
        }

        public List<ForumPostVO> getAll() {
            return repository.findAll();
        }
    
}