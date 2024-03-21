package com.fitanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
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
//		System.out.println(courseSvc.getAll());
		List<CourseVO> list = courseSvc.getSixCourses();
	
		list.forEach(courseVO ->
		{	
			
			

				
			byte[] crCover = courseVO.getCrCover();
			if (crCover!=null){
			String base64CrCover = Base64.getEncoder().encodeToString(crCover);
			courseVO.setBase64CrCover(base64CrCover);
			}
		});

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

	@GetMapping("/index/forumPost/DBGifReader")
	public ResponseEntity<String> dBGifReader(@RequestParam("fpId") String fpId, HttpServletRequest req,
			HttpServletResponse res) throws IOException {


		try {
			byte[] imageBytes = forumPostSvc.getOneForumPost(Integer.valueOf(fpId)).getFpPic();

			if (imageBytes != null && imageBytes.length > 0) {

				String base64Image = Base64.getEncoder().encodeToString(imageBytes);
				String base64Src = "data:image/gif;base64," + base64Image;
				return ResponseEntity.ok(base64Src);
			} else {
				return ResponseEntity.noContent().build();

			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}

	}

}
