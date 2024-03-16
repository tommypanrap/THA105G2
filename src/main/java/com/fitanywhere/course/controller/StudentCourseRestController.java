package com.fitanywhere.course.controller;

import com.fitanywhere.course.model.CourseCrCoverDTO;
import com.fitanywhere.course.model.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cr_cover")
public class StudentCourseRestController {
    @Autowired
    private CourseService courseSvc;

    @PostMapping("/course_image")
    public ResponseEntity<byte[]> getCourseCrCover(@RequestBody Map<String, Integer> crCover){
        Integer crId = crCover.get("cr_id");
        CourseCrCoverDTO crCoverDTO = courseSvc.getCourseCrCoverById(crId);
        if (crCoverDTO != null && crCoverDTO.getCrCover() != null) {
            byte[] photoBytes = crCoverDTO.getCrCover();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

            return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
