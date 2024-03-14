package com.fitanywhere.coursedetail.controller;

import com.fitanywhere.coursedetail.model.CourseDetailServiceImpl;
import com.fitanywhere.coursedetail.model.CourseVideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/course")
public class CourseDetailController {

    @Autowired
    private CourseVideoUtil videoUtil;

    @Autowired
    private CourseDetailServiceImpl courseDetailSvc;



    // 根據課程單元影片ID（cdId）串流播放單一影片
    @GetMapping(value = "/single_course/video/{cdId}/{sale}")
    public ResponseEntity<StreamingResponseBody> sendStreamingVideo(
            @PathVariable("cdId") Integer cdId,
            @PathVariable("sale") Integer sale,
            @RequestHeader(value = "Range", required = false) String rangeHeader){
        ResponseEntity<StreamingResponseBody> response = null;

        try{
            response = videoUtil.getPartialVideo(rangeHeader, cdId, sale);
        }catch (Exception ie){
            ie.printStackTrace();
        }
        return response;
    }

}
