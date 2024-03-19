package com.fitanywhere.forumreply.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.user.model.UserService;


@Service("forumReplyService")
public class ForumReplyService {


        @Autowired
        ForumReplyRepository repository;
        
        @Autowired
    	ForumPostService forumPostSvc;
        
        @Autowired
        UserService userSvc;
        

        public void addForumReply(ForumReplyVO forumReplyVO, int fpId) {
            // 在新增留言時，設置留言對應的貼文ID
            ForumPostVO forumPostVO = new ForumPostVO();
            forumPostVO.setFpId(fpId);
            forumReplyVO.setForumPostVO(forumPostVO);

            // 調用 repository 保存留言對象
            repository.save(forumReplyVO);
        }

        public void updateForumReply(ForumReplyVO forumReplyVO, int fpId) {
            // 確保更新的留言與相應的貼文關聯
            ForumPostVO forumPostVO = new ForumPostVO();
            forumPostVO.setFpId(fpId);
            forumReplyVO.setForumPostVO(forumPostVO);

            // 更新留言
            repository.save(forumReplyVO);
        }

        public void deleteForumReply(Integer frId) {
            if (repository.existsById(frId))
                repository.deleteByFrStatus(frId);
        }
        public ForumReplyVO getOneForumReply(Integer fpId) {
            Optional<ForumReplyVO> optional = repository.findById(fpId);
//            return optional.get();
            return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
        }

        public List<ForumReplyVO> getAll() {
            return repository.findAll();
        }
        
        public List<ForumReplyVO> findReplyByFpId(Integer fpId) {
            return repository.findByForumPostVO_FpId(fpId);
        }
        
        public Timestamp getOriginalFrTime(Integer frId) {
            ForumReplyVO forumReplyVO = repository.findById(frId).orElse(null); // 根據frId查詢資料庫
            if (forumReplyVO != null) {
                return forumReplyVO.getFrTime(); // 取得原本的發文時間
            }
            return null;
        }
        
}