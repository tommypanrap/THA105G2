package com.fitanywhere.socialpost.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fitanywhere.socialpost.model.SocialPostService;
import com.fitanywhere.socialpost.model.SocialPostUpdateStatusDTO;
import com.fitanywhere.socialpost.model.SocialPostVO;
import com.fitanywhere.user.model.UserService;


@Controller
@RequestMapping("/backend")
public class BackendSocialPostController {
	

	@Autowired
	SocialPostService socialPostSvc;
	
	@GetMapping("/social")
	public String backend_social(Model model)  {
		
		List<SocialPostVO> socialPosts = socialPostSvc.getAll();
	    model.addAttribute("socialPosts", socialPosts);

		
        return "back-end/backend_social"; 
    }
	
	
	@PostMapping("/updateSocialPostStatus")
	public ResponseEntity<?> updateSocialPostStatus(@RequestBody SocialPostUpdateStatusDTO updateDTO) {
	    try {
	    	socialPostSvc.updatePostStatus(updateDTO.getSpid(), updateDTO.getSpstatus());
	        return ResponseEntity.ok("更新成功");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("更新失敗");
	    }
	}

}



