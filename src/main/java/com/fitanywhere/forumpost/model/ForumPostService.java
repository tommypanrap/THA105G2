package com.fitanywhere.forumpost.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
        }

        public List<ForumPostVO> getAll() {
            return repository.findAll();
        }
        
        public boolean existsByFpTitle(String fpTitle) {
            return repository.existsByFpTitle(fpTitle);
        }
        
        public Timestamp getOriginalFpTime(Integer fpId) {
            ForumPostVO forumPostVO = repository.findById(fpId).orElse(null); // 假设您的实体类名为ForumPost，根据ID查询数据库中的记录
            if (forumPostVO != null) {
                return forumPostVO.getFpTime(); // 获取原始的fpTime值
            }
            return null;
        }
        
        public Integer getOriginalFpViews(Integer fpId) {
            ForumPostVO forumPostVO = repository.findById(fpId).orElse(null);
            if (forumPostVO != null) {
                return forumPostVO.getFpViews(); // 獲取原始的fpViews
            }
            return null;
        }

        public byte[] getOriginalFpPic(Integer fpId) {
            ForumPostVO forumPost = repository.findById(fpId).orElse(null);
            if (forumPost != null) {
                return forumPost.getFpPic(); // 獲取原本的fpPic
            }
            return null;
        }

   
}