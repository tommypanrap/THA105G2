package com.fitanywhere.usercourse.controller;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.detail.model.DetailService;
import com.fitanywhere.detail.model.DetailVO;
import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import com.fitanywhere.usercourse.model.HistoryService;
import com.fitanywhere.usercourse.model.UserCourseDTO;
import com.fitanywhere.usercourse.model.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/course")
public class UserCourseController {
    @Autowired
    OrderService orderSvc;
    @Autowired
    DetailService detailSvc;
    @Autowired
    CourseService courseSvc;
    @Autowired
    UserCourseService userCourseSvc;
    @Autowired
    HistoryService historySvc;

    @GetMapping(value = "/my_courses")
    public String myCourseList(ModelMap model, HttpSession session) {
        // 從訂單歷史紀錄抓我的課程
        Integer uId = (Integer) session.getAttribute("uId");
        List<OrderVO> orderList = orderSvc.getOrders(uId);

//        System.out.println("當前登入的ID:" + uId);

        // 儲存購買的課程
        List<CourseVO> myCourse = new ArrayList<>();
        int count = 0;

        for (OrderVO orderVO : orderList) {
            List<DetailVO> details = detailSvc.getDetailsByOrderId(orderVO.getOdId());
            for (DetailVO detailVO : details) {
                CourseVO course = courseSvc.getOneCourse(detailVO.getCrId());
                // 避免重複的課程被加入
                if (course != null && !myCourse.contains(course)) {
                    myCourse.add(course);
                    count++;
//                    System.out.println("抓取order的清單: "+ myCourse);
                }
            }
        }

        String uNickname = userCourseSvc.getNickname(uId);


        // 抓課程歷史瀏覽紀錄
        List<UserCourseDTO> courseHistory = historySvc.getView(String.valueOf(uId));
        model.addAttribute("courseHistory", courseHistory);

        model.addAttribute("myCourse", myCourse);
        model.addAttribute("uId", uId);
        model.addAttribute("uNickname", uNickname);
        model.addAttribute("count", count);

        return "front-end/mj/student_enrolled_courses";
    }

}