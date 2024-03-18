package com.fitanywhere.usercourse.controller;

import com.fitanywhere.usercourse.model.HistoryService;
import com.fitanywhere.usercourse.model.UserCourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historySvc;

    @PostMapping("/add")
    public ResponseEntity<?> addCourseHistory(HttpServletRequest request, @RequestParam("uId") Integer uId, @RequestParam("crId") Integer crId) {
        HttpSession session = request.getSession();
        Integer thisUId = (Integer) session.getAttribute("uId");

        if (thisUId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not login");
        }

        historySvc.recordView(uId, crId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{uId}")
    public ResponseEntity<List<UserCourseDTO>> getCourseHistory(@PathVariable("uId") String uId) {

        List<UserCourseDTO> courseHistory = historySvc.getView(uId);
        return ResponseEntity.ok(courseHistory);
    }
}
