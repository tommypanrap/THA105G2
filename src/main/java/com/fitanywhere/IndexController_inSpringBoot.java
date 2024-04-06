package com.fitanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.forumpost.model.ForumPostGetCoverDTO;
import com.fitanywhere.forumpost.model.ForumPostRepository;
import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.user.model.UserHeadshotOnlyDTO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class IndexController_inSpringBoot {

	@Autowired
	CourseService courseSvc;

	@Autowired
	ForumPostService forumPostSvc;

	@Autowired
	private AdCarouselService AdCarSvc;

	@Autowired
	private UserService userSvc;

	@GetMapping("/")
	public String index(Model model) {

//		HttpSession newSession = req.getSession(true);
//		 boolean isLoggedIn = (newSession != null && newSession.getAttribute("uId") != null);
//		 model.addAttribute("isLoggedIn", isLoggedIn);
		 
		List<CourseVO> list = courseSvc.getSixCourses();
//	
		list.forEach(courseVO ->
		{	
			
			

				
			byte[] crCover = courseVO.getCrCover();
			if (crCover!=null){
			String base64CrCover = Base64.getEncoder().encodeToString(crCover);
			courseVO.setBase64CrCover(base64CrCover);
			}else{
				courseVO.setBase64CrCover("");
			}
		});
//
		model.addAttribute("courseListData", list);

		return "index"; // view

	}

//	請求進來刷新廣告判斷是否符合要求

	@ModelAttribute("ads")
	public List<AdCarouselVO> populateAds() {
		return AdCarSvc.getBaseHomePageAd();
	}

	@ModelAttribute("forumPostListFour")
	public List<ForumPostVO> forumPostListFour() {
		return forumPostSvc.findFourCourses();
	}

//	@GetMapping("/course_on_index")
//	public string course_on_index() {
//		return
//	}

	@PostMapping("/index/forumPost/img")
	public ResponseEntity<byte[]> getOriginalFpPic(@RequestBody Map<String, Integer> fpCover) {
		Integer fpId= fpCover.get("fp_id");
		ForumPostGetCoverDTO forumPostGetCoverDTO = forumPostSvc.getForumPostCoverById(fpId);
		
			byte[] imageBytes = forumPostSvc.getOneForumPost(Integer.valueOf(fpId)).getFpPic();

			if (forumPostGetCoverDTO != null && forumPostGetCoverDTO.getFpPic() != null) {

				byte[] photoBytes = forumPostGetCoverDTO.getFpPic();

	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

	            return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}

		

	}

}
