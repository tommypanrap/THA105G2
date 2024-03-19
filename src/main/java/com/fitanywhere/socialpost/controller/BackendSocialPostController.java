package com.fitanywhere.socialpost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fitanywhere.forumpost.model.ForumPostDTO;
import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.socialpost.model.SocialPostService;
import com.fitanywhere.socialpost.model.SocialPostUpdateStatusDTO;
import com.fitanywhere.socialpost.model.SocialPostVO;


@Controller
//@RequestMapping("/backend")
public class BackendSocialPostController {
	

	@Autowired
	SocialPostService socialPostSvc;
	
	@Autowired
	ForumPostService forumPostSvc;
	
	@GetMapping("/backend_social")
	public String backend_social(Model model)  {
		
		List<ForumPostVO> forumPosts = forumPostSvc.getAll();
	    model.addAttribute("forumPosts", forumPosts);
		List<SocialPostVO> socialPosts = socialPostSvc.getAll();
	    model.addAttribute("socialPosts", socialPosts);

		
        return "back-end/backend_social"; 
    }
	
	
	@PostMapping("/backend/updateSocialPostStatus")
	public ResponseEntity<?> updateSocialPostStatus(@RequestBody SocialPostUpdateStatusDTO updateDTO) {
	    try {
	    	socialPostSvc.updatePostStatus(updateDTO.getSpid(), updateDTO.getSpstatus());
	        return ResponseEntity.ok("更新成功");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("更新失敗");
	    }
	}
	
	@PostMapping("/backend/updateForumPostStatus")
	public ResponseEntity<?> updateforumPostStatus(@RequestBody ForumPostDTO forumPostDTO) {
	    try {
	    	forumPostSvc.updateforumPostStatus(forumPostDTO.getFpId(), forumPostDTO.getFpStatus());
	        return ResponseEntity.ok("更新成功");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("更新失敗");
	    }
	}

}



