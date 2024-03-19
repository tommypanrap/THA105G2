package com.fitanywhere.ad.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fitanywhere.ad.model.AdService;
import com.fitanywhere.ad.model.AdVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;




@Controller
@Validated
@RequestMapping("/ad")
public class AdIdController {
 
 @Autowired
 AdService adSvc;
 
 @Autowired
 CourseService courSvc;

 /*
  * This method will be called on select_page.html form submission, handling POST
  * request It also validates the user input
  */
 @GetMapping("getOne_For_Display")
 public String getOne_For_Display(
  /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
//  @NotEmpty(message="方案編號: 請勿空白")
//  @Digits(integer = 1, fraction = 0, message = "方案編號: 請填數字-請勿超過{integer}位數")
//  @Min(value = 1, message = "方案編號: 不能小於{value}")
//  @Max(value = 100, message = "方案編號: 不能超過{value}")
//  @RequestParam("adId") String adId,
  ModelMap model) {
  Integer adId = 1;
  Integer crId = 10002;
  /***************************2.開始查詢資料*********************************************/
//  EmpService empSvc = new EmpService();
  AdVO adVO = adSvc.getOneAd(Integer.valueOf(adId));
  CourseVO courseVO = courSvc.getOneCourse(Integer.valueOf(crId));

  List<AdVO> list = adSvc.getAll();
  List<CourseVO> courseList = courSvc.getAll(); 

  model.addAttribute("adListData", list); // for select_page.html 第97 109行用
  model.addAttribute("courseListData", courseList); // for select_page.html 第97 109行用
  
  if (adVO == null) {
   model.addAttribute("errorMessage", "查無資料");
   return "back-end/ad/select_page";
  }
  
  /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
  model.addAttribute("adVO", adVO);
  model.addAttribute("courseVO", courseVO);
  model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
  
  
//  return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
  return "back-end/ad/select_page"; // 查詢完成後轉交select_page.html由其第128行insert listOneEmp.html內的th:fragment="listOneEmp-div
//  return "front-end/ad/instructor_advertising";
 }
 
    @GetMapping("/getAdPrice")
    public ResponseEntity<?> getAdPrice(@RequestParam("id") Integer adId) {
        AdVO adVO = adSvc.getOneAd(adId); // 假設這個方法會返回一個包含廣告資料的AdVO實例
        if (adVO == null) {
            return ResponseEntity.notFound().build(); // 如果沒有找到廣告，返回404
        }
        // 假設AdVO有一個getAdDayPrice方法可以獲取每日價格
        Integer adDayPrice = adVO.getAdDayPrice();
        return ResponseEntity.ok().body(Collections.singletonMap("ad_day_price", adDayPrice));
    }
 
 
 @ExceptionHandler(value = { ConstraintViolationException.class })
 //@ResponseStatus(value = HttpStatus.BAD_REQUEST)
 public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
     Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
     StringBuilder strBuilder = new StringBuilder();
     for (ConstraintViolation<?> violation : violations ) {
           strBuilder.append(violation.getMessage() + "<br>");
     }
     //==== 以下第80~85行是當前面第69行返回 /src/main/resources/templates/back-end/emp/select_page.html 第97行 與 第109行 用的 ====   
//     model.addAttribute("empVO", new EmpVO());
//     EmpService empSvc = new EmpService();
  List<AdVO> list = adSvc.getAll();
  System.out.println(list);
  model.addAttribute("adListData", list); // for select_page.html 第97 109行用
  
  String message = strBuilder.toString();
     return new ModelAndView("back-end/ad/select_page", "errorMessage", "請修正以下錯誤:<br>"+message);
 }
 
}