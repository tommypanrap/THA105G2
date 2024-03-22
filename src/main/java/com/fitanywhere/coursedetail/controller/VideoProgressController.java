package com.fitanywhere.coursedetail.controller;

import com.fitanywhere.coursedetail.model.VideoProgressDTO;
import com.fitanywhere.coursedetail.model.VideoProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/video")
public class VideoProgressController {

    @Autowired
    private VideoProgressService VideoProgressSvc;



    @PostMapping("/progress/save")
    public ResponseEntity<?> saveProgress(@RequestBody VideoProgressDTO videoProgressDTO) {
        VideoProgressSvc.saveProgress(videoProgressDTO.getUId(), videoProgressDTO.getCdId(), videoProgressDTO.getProgress());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/progress/get")
    public ResponseEntity<?> getProgress(@RequestParam String uId, @RequestParam String cdId) {
        Double progress = VideoProgressSvc.getProgress(uId, cdId);
        if(progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
