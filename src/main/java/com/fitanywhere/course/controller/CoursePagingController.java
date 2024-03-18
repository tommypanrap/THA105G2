package com.fitanywhere.course.controller;

import com.fitanywhere.course.model.CourseCrCoverDTO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.course.model.CoursesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/course")
public class CoursePagingController {
    @Autowired
    CourseService courseSvc;


    @GetMapping("/courses")
    public String getCourses(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "9") int size,
                             ModelMap model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CoursesDTO> page1 = courseSvc.getAllCourses(pageable);


        model.addAttribute("totalPages",page1.getTotalPages());
        model.addAttribute("currentPage",page1.getNumber());
        model.addAttribute("courseListData", page1);


        return "front-end/mj/course_filter_two_toggle";
    }


    @GetMapping("/img")
    public ResponseEntity<StreamingResponseBody> getCourseCrCover(@RequestParam("crId") String crIdParam) {
        try {
            Integer crId = Integer.parseInt(crIdParam);
            CourseCrCoverDTO crCoverDTO = courseSvc.getCourseCrCoverById(crId);
            if (crCoverDTO != null && crCoverDTO.getCrCover() != null) {
                byte[] photoBytes = crCoverDTO.getCrCover();

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

                StreamingResponseBody responseBody = outputStream -> {
                    outputStream.write(photoBytes);
                    outputStream.flush();
                };

                return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            // 处理 cr_id 参数无效的情况
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // 購物車功能-課程資訊新增到 model
//	@GetMapping("courses")
//	public String getCourses(ModelMap model) {
//		List<CourseVO> list = courseSvc.getAll();
//
//		//將圖片轉成base64
////		list.forEach(courseVO ->
////		{
////			byte[] crCover = courseVO.getCrCover();
////			if (crCover!=null){
////			String base64CrCover = Base64.getEncoder().encodeToString(crCover);
////			courseVO.setBase64CrCover(base64CrCover);
////			}
////		});
//
//
//
//		model.addAttribute("courseListData", list);
//		return "front-end/mj/course_filter_two_toggle";
//	}


}
