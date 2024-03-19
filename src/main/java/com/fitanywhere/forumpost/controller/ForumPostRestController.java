package com.fitanywhere.forumpost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.forumpost.model.ForumPostDTO;
import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
@RestController
@RequestMapping("/forumpost_api")
public class ForumPostRestController {
	

	    @Autowired
	    private ForumPostService ForumPostSvc;

	    @GetMapping("/details")
	    public ResponseEntity<ForumPostDTO> getForumPostDetails(@RequestParam("fpId") Integer fpId) {
	        ForumPostVO forumPost = ForumPostSvc.getOneForumPost(fpId);
	        if (forumPost != null) {
	            ForumPostDTO forumPostDTO = convertToDTO(forumPost);
	            return ResponseEntity.ok(forumPostDTO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    private ForumPostDTO convertToDTO(ForumPostVO forumPost) {
	        ForumPostDTO forumPostDTO = new ForumPostDTO(
	            forumPost.getUserVO().getuId(),
	            forumPost.getFpId(),
	            forumPost.getFpCategory(),
	            forumPost.getFpTitle(),
	            forumPost.getFpContent(),
	            forumPost.getFpStatus(),
	            forumPost.getFpTime(),
	            forumPost.getFpUpdate(),
	            null, // 先將 fpPic 設為空值
	            forumPost.getFpViews()
	        );
	        // 將 fpPic 轉換為 Base64 字符串
	        String base64Pic = forumPostDTO.convertPicToBase64(forumPost.getFpPic());
	        forumPostDTO.setFpPic(base64Pic); // 設置轉換後的 Base64 字符串
	        return forumPostDTO;
	    }

	    
}

