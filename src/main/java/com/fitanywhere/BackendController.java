package com.fitanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fitanywhere.ann.model.AnnVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseStatus1DTO;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.course.model.VideoServiceImpl;
import com.fitanywhere.coursedetail.model.CourseDetailService;
import com.fitanywhere.coursedetail.model.CourseDetailVO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.validation.Valid;

@Controller
public class BackendController {

	@Autowired
	CourseService courseSvc;
	
	@Autowired
	CourseDetailService courseDetailSvc;
	
	@Autowired
	private VideoServiceImpl videoSvc;
	
	
	@GetMapping("/backend_course")
	public String backend_course(Model model)  {
        return "back-end/backend_course"; 
    }
	
	@PostMapping("/check_course")
	public String check_course(Model model,Integer crId)  {
		
		CourseVO courseVO = courseSvc.getOneCourse(crId);
		List<String> cdVideosList = courseDetailSvc.getCourseVideoPathByCourseId(crId);
		List<String> cdSaleVideosList = courseDetailSvc.getCourseSaleVideoPathByCourseId(crId);
		List<CourseDetailVO> cdList = courseDetailSvc.getCourseDetailByCrId(crId);
		
		model.addAttribute("courseVO", courseVO);
		model.addAttribute("cdList", cdList);
		model.addAttribute("cdVideosList", cdVideosList);
		model.addAttribute("cdSaleVideosList", cdSaleVideosList);
		return "back-end/check_course"; 
	}
	
	@PostMapping("/approval")
	public String approval(@RequestParam("crId")Integer crId,@RequestParam("crState")Integer crState,Model model) {
		courseSvc.checkCourse(crId, crState);
		return "back-end/backend_course";
	}
	
	@GetMapping("/backend_ad")
	public String backend_ad(Model model)  {
        return "back-end/backend_ad"; 
	}
	
	@GetMapping("/backend_ad_list")
	public String backend_ad_list(Model model)  {
        return "back-end/backend_ad_list"; 
    }
	
	@GetMapping("/backend_course_order")
	public String backend_course_order(Model model)  {
        return "back-end/backend_course_order"; 
    }
	
//	@GetMapping("/backend_ad")
//	public String backend_ad(Model model)  {
//		AdVO adVO = new AdVO();
//		model.addAttribute("adVO", adVO);
//        return "back-end/backend_ad"; 
//	}
//	
//	@GetMapping("/backend_ad_list")
//	public String backend_ad_list(Model model)  {
//        return "back-end/backend_ad_list"; 
//    }
//	

	@GetMapping("/backend_course_discount")
	public String backend_course_discount(Model model)  {
        return "back-end/backend_course_discount"; 
    }
	

	
	@ModelAttribute("courseList")
	public List<CourseVO> getcourse1(Integer uId) {
		List<CourseVO> courseList = courseSvc.getAll();
		return courseList;
	}
	
	@GetMapping("/checkCrCover/{crId}")
    public ResponseEntity<byte[]> getCourseCrCover(@PathVariable Integer crId) {
    	CourseVO courseVO = courseSvc.getOneCourse(crId);
    	String path = "src/main/resources/static/assets/images/client/client-1.png";
    	Path filePath = Paths.get(path);
    	byte [] defaultHeadshot = null;
    	try {
    		defaultHeadshot = Files.readAllBytes(filePath);
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	if ( courseVO.getCrCover() != null) {
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.IMAGE_JPEG);
    		return new ResponseEntity<>(courseVO.getCrCover(), headers, HttpStatus.OK);
    	} else {
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.IMAGE_JPEG);
    		return new ResponseEntity<>(defaultHeadshot, headers, HttpStatus.OK);
    	}
    }
	
    @GetMapping("/backend/cdVideo")
	public ResponseEntity<StreamingResponseBody> sendStreamingVideo(@RequestParam("videoId") String videoId,
			@RequestHeader(value = "Range", required = false) String rangeHeader) {
		ResponseEntity<StreamingResponseBody> response = null;
		try {
			response = videoSvc.getPartialVideo(rangeHeader, videoId);
			
			
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return response;
	}
}
