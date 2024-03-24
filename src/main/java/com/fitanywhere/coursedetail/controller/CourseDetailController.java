package com.fitanywhere.coursedetail.controller;

import com.fitanywhere.coursedetail.model.CourseDetailRepository;
import com.fitanywhere.coursedetail.model.CourseDetailServiceImpl;
import com.fitanywhere.coursedetail.model.CourseVideoUtil;
import com.fitanywhere.order.model.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/course")
public class CourseDetailController {

    @Autowired
    private CourseVideoUtil videoUtil;

    @Autowired
    private CourseDetailServiceImpl courseDetailSvc;

    @Autowired
    private CourseDetailRepository repository;


    @GetMapping(value = "/single_course/video/{cdId}/{sale}")
    public ResponseEntity<StreamingResponseBody> sendStreamingVideo(
            // 根據課程單元影片ID（cdId）串流播放單一影片
            @PathVariable("cdId") Integer cdId,
            @PathVariable("sale") Integer sale,
            @RequestHeader(value = "Range", required = false) String rangeHeader,
            HttpSession session){
        ResponseEntity<StreamingResponseBody> response = null;

        try{
            // util 已經有寫到 contentLength
            return videoUtil.getPartialVideo(rangeHeader, cdId, sale);
        }catch (Exception ie){
            ie.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getcourseBycdId/{cdId}")
    public ResponseEntity<Integer> geCrIdByCdId(@PathVariable Integer cdId) {
        try {
            Integer crId = courseDetailSvc.findCrIdByCdId(cdId);
            if (crId != null) {
                return ResponseEntity.ok(crId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
