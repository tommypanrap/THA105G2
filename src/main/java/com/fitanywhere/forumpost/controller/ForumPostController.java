package com.fitanywhere.forumpost.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/forumpost")
public class ForumPostController {

    @Autowired
    ForumPostService forumPostSvc;
    
    @Autowired
    UserService userSvc;

    @GetMapping("add_ForumPost")
    public String addForumPost(ModelMap model) {
        ForumPostVO forumPostVO = new ForumPostVO();
        model.addAttribute("ForumPostVO", forumPostVO);
        return "front-end/forumpost/addForumPost";
    }
    @GetMapping("list")
    public String list(ModelMap model) {
    	ForumPostVO forumPostVO = new ForumPostVO();
    	model.addAttribute("ForumPostVO", forumPostVO);
    	return "front-end/forumpost/select_page";
    }
    
    @GetMapping("list_All_Forum_Post")
    public String listAllForumPost(ModelMap model) {
    	ForumPostVO forumPostVO = new ForumPostVO();
    	model.addAttribute("ForumPostVO", forumPostVO);
    	return "front-end/forumpost/g2_blog_list";
    }

    @PostMapping("insert")
    public String insert(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
                         @RequestParam("fpPic") MultipartFile[] parts) throws IOException {

        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        // 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
        result = removeFieldError(forumPostVO, result, "fpPic");

        if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
            model.addAttribute("errorMessage", "貼文圖片: 請上傳圖片");
        } else {
            for (MultipartFile multipartFile : parts) {
                byte[] buf = multipartFile.getBytes();
                forumPostVO.setFpPic(buf);
            }
        }
        if (result.hasErrors() || parts[0].isEmpty()) {
            return "front-end/forumpost/addForumPost";
        }
        /*************************** 2.開始新增資料 *****************************************/
        // EmpService empSvc = new EmpService();
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
        forumPostVO.setFpTime(currentTimestamp);
        forumPostVO.setFpUpdate(currentTimestamp);
        forumPostSvc.addForumPost(forumPostVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<ForumPostVO> list = forumPostSvc.getAll();
        model.addAttribute("forumPostListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/forumpost/listAllForumPost"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/user/listAllUser")
    }

    /*
     * This method will be called on listAllEmp.html form submission, handling POST request
     */
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("fpId") String fpId, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        // EmpService empSvc = new EmpService();
        ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));

        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("ForumPostVO", forumPostVO);
        return "front-end/forumpost/update_forumpost_input"; // 查詢完成後轉交update_user_input.html
    }

    /*
     * This method will be called on update_user_input.html form submission, handling POST request It also validates the user input
     */
    @PostMapping("update")
    public String update(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
                         @RequestParam("fpPic") MultipartFile[] parts) throws IOException {

        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        // 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
        result = removeFieldError(forumPostVO, result, "fpPic");

        if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
            // EmpService empSvc = new EmpService();
            byte[] fpPic = forumPostSvc.getOneForumPost(forumPostVO.getFpId()).getFpPic();
            forumPostVO.setFpPic(fpPic);

        } else {
            for (MultipartFile multipartFile : parts) {
                byte[] fpPic = multipartFile.getBytes();
                forumPostVO.setFpPic(fpPic);
            }
        }
        if (result.hasErrors()) {
            return "front-end/forumpost/update_forumpost_input";
        }
        /*************************** 2.開始修改資料 *****************************************/
        // EmpService empSvc = new EmpService();
        
        ForumPostVO originalTime = forumPostSvc.getOneForumPost(forumPostVO.getFpId());
        forumPostVO.setFpTime(originalTime.getFpTime());
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
        forumPostVO.setFpUpdate(currentTimestamp);
        forumPostSvc.updateForumPost(forumPostVO);

        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "- (修改成功)");
        forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(forumPostVO.getFpId()));
        model.addAttribute("ForumPostVO", forumPostVO);
        return "front-end/forumpost/listOneForumPost"; // 修改成功後轉交listOneUser.html
    }

    /*
     * This method will be called on listAllUser.html form submission, handling POST request
     */
    @PostMapping("delete")
    public String delete(@RequestParam("fpId") String fpId, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始刪除資料 *****************************************/
        // EmpService empSvc = new EmpService();
        forumPostSvc.deleteForumPost(Integer.valueOf(fpId));
        /*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
        List<ForumPostVO> list = forumPostSvc.getAll();
        model.addAttribute("forumPostListData", list);
        model.addAttribute("success", "- (刪除成功)");
		return "front-end/forumpost/listAllForumPost"; // 刪除完成後轉交listAllUser.html
	}
    
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData(){
		List<UserVO> list = userSvc.getAll();
		return list;
	}

    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(ForumPostVO forumPostVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(forumPostVO, "ForumPostVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }

}