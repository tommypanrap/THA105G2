 package com.fitanywhere.adcarousel.controller;

 import javax.servlet.http.HttpSession;
 import javax.validation.Valid;

 import org.springframework.validation.BeanPropertyBindingResult;
 import org.springframework.validation.BindingResult;
 import org.springframework.validation.FieldError;

 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.ad.model.AdService;
import com.fitanywhere.ad.model.AdVO;
import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;
import com.fitanywhere.course.model.CourseService;
 import com.fitanywhere.course.model.CourseVO;
 import com.fitanywhere.user.model.UserService;
 import com.fitanywhere.user.model.UserVO;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;

 import java.io.IOException;
 import java.util.*;
 import java.util.stream.Collectors;


 @Controller
 @RequestMapping("/adCarousel")
 public class AdCarouselController {

  
  @Autowired
  private AdCarouselService AdCarSvc;
  
  @Autowired
  private AdService adSvc;

 // 關聯user表格
  @Autowired
  private UserService userSvc;
  
 // 關聯課程表格
  @Autowired
  private CourseService courSvc;

  /*
  * This method will serve as addEmp.html handler.
  */
 // @GetMapping("addAdCarousel")
 // public String addAdCarousel(ModelMap model) {
 //  AdCarouselVO adcarVO = new AdCarouselVO();
 //  model.addAttribute("adcarVO", adcarVO);
 //  return "back-end/adCarousel/addAdCarousel";
 // }
 // 
 // 前端畫面，先寫死用戶做測試
  @GetMapping("addAdCarousel")
  public String addAdCarousel(Integer adId,HttpSession session,ModelMap model) {

   /***************************2.開始查詢資料*********************************************/
   Integer uId = (Integer) session.getAttribute("uId");
//   AdVO adVO = adSvc.getOneAd(Integer.valueOf(adId));
   AdVO adVO = new AdVO();
   CourseVO courseVO = new CourseVO();
   UserVO userVO = new UserVO();
   AdCarouselVO adcarVO = new AdCarouselVO();
   String uName = (String) session.getAttribute("uName");
   
   List<AdVO> list = adSvc.getAll();
   List<UserVO> userList = userSvc.getUserByUId(uId);
   List<CourseVO> courseList = courSvc.getCourseByUId(uId);
//   List<AdCarouselVO> dayPriceList = AdCarSvc.getDayPriceByAdId(adId);
   
// //  將教練id抓到的課程廣告裝起來
   List<AdCarouselVO> userAds = AdCarSvc.getAdOrderByUserId(uId);
   model.addAttribute("userAds", userAds);
//   
// //  將透過方案id的方案單價裝起來
//   List<AdCarouselVO> dayPrices = AdCarSvc.getDayPriceByAdId(adId);
//   model.addAttribute("dayPrices", dayPrices);
//   
   model.addAttribute("uName", uName);     //用戶名稱顯示在sidebar
   model.addAttribute("adListData", list); // for select_page.html 第97 109行用
   model.addAttribute("courseListData", courseList); // for select_page.html 第97 109行用
   model.addAttribute("adcarVO", adcarVO); 
   model.addAttribute("userVO", userList);
   
 //  model.addAttribute("courseListData", courseList); 
 //  model.addAttribute("AdCarouselListData", adcarList); 
   
   if (adVO == null) {
    model.addAttribute("errorMessage", "查無資料");
    return "front-end/ad/instructor_advertising";
   }
   
   /***************************3.查詢完成,準備轉交(Send the Success view)*****************/
   model.addAttribute("adVO", adVO);
   model.addAttribute("courseVO", courseVO);
   model.addAttribute("userVO", userVO);
   model.addAttribute("ForPhotoUserVO", userList.get(0)); //讓頁面顯示用戶照片
   model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
   
   return "front-end/ad/instructor_advertising";
  }

 // @ModelAttribute("userAds")
 // public List<AdCarouselVO> getAdOrderByUserId(Integer uId) {
 //  
 //  return AdCarSvc.getAdOrderByUserId(uId);
 // }
  
  @PostMapping("insert") 
  public String insert(@Valid AdCarouselVO adcarVO, BindingResult result, ModelMap model,
    @RequestParam("adcUpdatePic") MultipartFile file, HttpSession session, 
    @RequestParam("adId") Integer adId,
    @RequestParam("crId") Integer crId) throws IOException {
 
   /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
   // 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
 //  result = removeFieldError(adcarVO, result, "adcUpdatePic");
 //
      if (file.isEmpty()) { // 使用者未選擇要上傳的圖片時
          model.addAttribute("errorMessage", "輪播方案: 請上傳照片");
          return "back-end/adCarousel/addAdCarousel";
      } else {
          byte[] buf = file.getBytes();
          adcarVO.setAdcUpdatePic(buf);
          result = removeFieldError(adcarVO, result, "adcUpdatePic");
      }
      
      Object sessionuId = session.getAttribute("uId");
      if(null != sessionuId) {
       Integer uId = Integer.valueOf(sessionuId.toString());
       UserVO userVO = new UserVO();
       userVO.setuId(uId);
       adcarVO.setUserVO(userVO);
      }
      if(null != adId) {
       AdVO adVO = new AdVO();
       adVO.setAdId(adId);
       adcarVO.setAdVO(adVO);
      }
      if(null != crId) {
 
       CourseVO courseVO = new CourseVO();
       courseVO.setCrId(crId);
       adcarVO.setCourseVO(courseVO);
      }
      
      if (result.hasErrors()) {
          return "back-end/adCarousel/addAdCarousel";
      }
   /*************************** 2.開始新增資料 *****************************************/
   // EmpService empSvc = new EmpService();
   AdCarSvc.addAdCarousel(adcarVO);
   /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
   prepareModelForView(session, model);
   List<AdCarouselVO> list = AdCarSvc.getAll();
   model.addAttribute("AdCarouselListData", list);
   model.addAttribute("success", "- (新增成功)");
 //  return "redirect:/adCarousel/listAllAdCarousel"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
   return "front-end/ad/instructor_advertising"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
  }
  
  private void prepareModelForView(HttpSession session, ModelMap model) {
      Integer uId = (Integer) session.getAttribute("uId");
      if (uId != null) {
          // 根據uId重新獲取用戶資訊及其他必要的資料
          List<UserVO> userList = userSvc.getUserByUId(uId);
          List<CourseVO> courseList = courSvc.getCourseByUId(uId);
          List<AdVO> list = adSvc.getAll();
          List<AdCarouselVO> userAds = AdCarSvc.getAdOrderByUserId(uId);
          
          // 再次添加必要的模型屬性
          model.addAttribute("userAds", userAds);
          model.addAttribute("uName", session.getAttribute("uName"));
          model.addAttribute("adListData", list);
          model.addAttribute("courseListData", courseList);
          model.addAttribute("ForPhotoUserVO", userList.isEmpty() ? null : userList.get(0)); // 重新設置ForPhotoUserVO
          // 根據實際情況添加其他屬性
      } else {
          // 處理uId為null的情況，可能需要重定向到登入頁面或添加錯誤訊息
      }
  }
 
  
  
//  @PostMapping("insert")
//  public String insert(@Valid AdCarouselVO adcarVO, BindingResult result, ModelMap model,
//    @RequestParam("adcUpdatePic") MultipartFile file, @RequestParam("uId") Integer uId, 
//    @RequestParam("adId") Integer adId,
//    @RequestParam("crId") Integer crId) throws IOException {
//
//   /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//   // 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
// //  result = removeFieldError(adcarVO, result, "adcUpdatePic");
// //
//   if (file.isEmpty()) { // 使用者未選擇要上傳的圖片時
//    model.addAttribute("errorMessage", "輪播方案: 請上傳照片");
//    return "back-end/adCarousel/addAdCarousel";
//   } else {
//    byte[] buf = file.getBytes();
//    adcarVO.setAdcUpdatePic(buf);
//    result = removeFieldError(adcarVO, result, "adcUpdatePic");
//   }
//   
//   if(null != uId) {
//    UserVO userVO = new UserVO();
//    userVO.setuId(uId);
//    adcarVO.setUserVO(userVO);
//   }
//   if(null != adId) {
//    AdVO adVO = new AdVO();
//    adVO.setAdId(adId);
//    adcarVO.setAdVO(adVO);
//   }
//   if(null != crId) {
//
//    CourseVO courseVO = new CourseVO();
//    courseVO.setCrId(crId);
//    adcarVO.setCourseVO(courseVO);
//   }
//   
//   if (result.hasErrors()) {
//    System.out.println("出錯了");
//    System.out.println("result has error" + result.hasErrors());
//    List<FieldError> listField = result.getFieldErrors();
//    for(FieldError error : listField) {
//     System.out.println(error);
//    }
//    return "back-end/adCarousel/addAdCarousel";
//   }
//   /*************************** 2.開始新增資料 *****************************************/
//   // EmpService empSvc = new EmpService();
//   AdCarSvc.addAdCarousel(adcarVO);
//   /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
//   List<AdCarouselVO> list = AdCarSvc.getAll();
//   model.addAttribute("AdCarouselListData", list);
//   model.addAttribute("success", "- (新增成功)");
// //  return "redirect:/adCarousel/listAllAdCarousel"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
//   return "front-end/ad/instructor_advertising"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
//  }
  
  

  /*
  * This method will be called on listAllEmp.html form submission, handling POST request
  */
//測試
// @PostMapping("getOne_For_Update")
// public String getOne_For_Update(@RequestParam("adcId") String adcId, ModelMap model) {
//  /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//  /*************************** 2.開始查詢資料 *****************************************/
//  // EmpService empSvc = new EmpService();
//  AdCarouselVO adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcId));
//
//  /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
//  model.addAttribute("adcarVO", adcarVO);
//  return "back-end/adCarousel/update_adCarousel_input"; // 查詢完成後轉交update_emp_input.html
// }

/*
* This method will be called on update_emp_input.html form submission, handling POST request It also validates the user input
*/
// 測試
// @PostMapping("update")
// public String update(@Valid AdCarouselVO adcarVO, BindingResult result, ModelMap model,
//   @RequestParam("adcUpdatePic") MultipartFile[] parts) throws IOException {
//
//  /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//  // 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//  result = removeFieldError(adcarVO, result, "adcUpdatePic");
//
//  if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
//   // EmpService empSvc = new EmpService();
//   byte[] upFiles = AdCarSvc.getOneAdcId(adcarVO.getAdcId()).getAdcUpdatePic();
//   adcarVO.setAdcUpdatePic(upFiles);
//  } else {
//   for (MultipartFile multipartFile : parts) {
//    byte[] upFiles = multipartFile.getBytes();
//    adcarVO.setAdcUpdatePic(upFiles);
//   }
//  }
//  if (result.hasErrors()) {
//   return "back-end/adCarousel/update_adCarousel_input";
//  }
//  /*************************** 2.開始修改資料 *****************************************/
//  // EmpService empSvc = new EmpService();
//  AdCarSvc.updateAdCarousel(adcarVO);
//  /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//  model.addAttribute("success", "- (修改成功)");
//  adcarVO = AdCarSvc.getOneAdcId(Integer.valueOf(adcarVO.getAdcId()));
//  model.addAttribute("adcarVO", adcarVO);
//  return "back-end/adCarousel/listOneAdCarousel"; // 修改成功後轉交listOneEmp.html
// }

@GetMapping("select_page")
public String select_pageadCarousel(Model model) {
 return "back-end/adCarousel/select_page";
}

@GetMapping("listAllAdCarousel")
public String listAlladCarousel(Model model) {
 return "back-end/adCarousel/listAllAdCarousel";
}

@ModelAttribute("AdCarouselListData")
protected List<AdCarouselVO> referenceAdCarouselListData() {

 List<AdCarouselVO> list = AdCarSvc.getAll();
 return list;
}

/*
* 第一種作法 Method used to populate the List Data in view. 如 : 
* <form:select path="deptno" id="deptno" items="${deptListData}" itemValue="deptno" itemLabel="dname" />
*/
@ModelAttribute("AdCarouselListData")
protected List<AdCarouselVO> referenceListData() {
 // DeptService deptSvc = new DeptService();
 List<AdCarouselVO> list = AdCarSvc.getAll();
 return list;
}

// 去除BindingResult中某個欄位的FieldError紀錄
public BindingResult removeFieldError(AdCarouselVO adcarVO, BindingResult result, String removedFieldname) {
 List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
   .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
   .collect(Collectors.toList());
 result = new BeanPropertyBindingResult(adcarVO, "adcarVO");
 for (FieldError fieldError : errorsListToKeep) {
  result.addError(fieldError);
 }
 return result;
}

}