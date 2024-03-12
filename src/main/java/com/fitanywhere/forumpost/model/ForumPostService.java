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
            return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
        }

        public List<ForumPostVO> getAll() {
            return repository.findAll();
        }
   
}