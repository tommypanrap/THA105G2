package com.fitanywhere.forumpost.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fitanywhere.forumpost.model.DefaultImage;
import com.fitanywhere.forumpost.model.DefaultImage2;
import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.forumreply.model.ForumReplyService;
import com.fitanywhere.forumreply.model.ForumReplyVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/forumpost")
public class ForumPostController {

	@Autowired
	ForumReplyService forumReplySvc;

	@Autowired
	ForumPostService forumPostSvc;

	@Autowired
	UserService userSvc;

	@GetMapping("addForumPost")
	public String addForumPost(ModelMap model, @ModelAttribute("forumPostVO") ForumPostVO forumPostVO, HttpSession session) {
		if (!isUserLoggedIn(session)) {
	        // 如果未登入，重定向到登錄頁面或其他處理方式
	        return "redirect:/user/force_user_login";
	    }
	    
	    // 如果已登入，根據用戶ID獲取用戶資訊
	    Integer userId = (Integer) session.getAttribute("userId");
	    UserVO userVO = userSvc.getUserDataByID(userId);
	    
	    if (forumPostVO == null) {
	        forumPostVO = new ForumPostVO();
	    }
	    model.addAttribute("forumPostVO", forumPostVO);
	    model.addAttribute("userVO", userVO);
	    return "front-end/forumpost/g2_blog_new_article";
	}
	
	@GetMapping("/details")
	public String getForumPostDetails(@RequestParam("fpId") int fpId, Model model) {
	    ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));
	    if (forumPostVO != null) {
	        // 增加貼文觀看次數
	        forumPostVO.setFpViews(forumPostVO.getFpViews() + 1);
	        // 更新觀看次數
	        forumPostSvc.updateViews(forumPostVO.getFpId(), forumPostVO.getFpViews());
	        
	        List<ForumReplyVO> forumReplyVO = forumReplySvc.findReplyByFpId(fpId);
	        model.addAttribute("forumReplyVO", forumReplyVO);
	    }
	    
	    ForumReplyVO newForumReply = new ForumReplyVO();
	    model.addAttribute("newForumReply", newForumReply);
	    model.addAttribute("forumPostVO", forumPostVO);

	    return "front-end/forumpost/g2_blog_details";
	}
	
	@PostMapping("insert")
	public String insert(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
	                     @RequestParam("fpPic") MultipartFile[] parts, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
	    
		 // 檢查用戶是否已經登入
	    if (!isUserLoggedIn(session)) {
	        // 如果未登入，重定向到登錄頁面或其他處理方式
	        return "redirect:/user/force_user_login";
	    }

	    // 獲取登入用戶的ID
	    Integer uId = (Integer) session.getAttribute("uId");
	    UserVO userVO = userSvc.getUserDataByID(uId);
	    forumPostVO.setUserVO(userVO);
	    
		// 檢查中是否存在相同標題文章
        boolean titleExists = forumPostSvc.existsByFpTitle(forumPostVO.getFpTitle());
        if (titleExists) {
            result.addError(new FieldError("ForumPostVO", "fpTitle", "文章標題已存在"));
         // 將user輸入的數據保存到 RedirectAttributes 中
            redirectAttributes.addFlashAttribute("forumPostVO", forumPostVO);
            // 重定向回新增页面
            return "redirect:/forumpost/addForumPost";
        }
        
     // 檢查用戶是否上傳了圖片，如果沒有，則根據文章分類設置不同的預設圖片
        if (parts == null || parts.length == 0 || parts[0].isEmpty()) {
            // 獲取用戶的文章分類
            String category = forumPostVO.getFpCategory();
            
            // 根據文章分類設置預設圖片
            byte[] defaultPic = getDefaultPicByCategory(category);
            
            // 將預設圖片設置到ForumPostVO對象中
            forumPostVO.setFpPic(defaultPic);
        } else {
            // 將用戶上傳的圖片轉換為字節數組並存儲到ForumPostVO對象中
            forumPostVO.setFpPic(parts[0].getBytes());
        }

        forumPostSvc.addForumPost(forumPostVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<ForumPostVO> list = forumPostSvc.getAll();
        model.addAttribute("forumPostVO", forumPostVO);
        model.addAttribute("forumPostListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/forumpost/listAllForumPost";
    }

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("fpId") String fpId, HttpSession session, ModelMap model) {
	    // 1. 驗證登入狀態
	    if (!isUserLoggedIn(session)) {
	        // 如果未登入，重定向到登錄頁面或其他處理方式
	        return "redirect:/user/force_user_login";
	    }

	    // 2. 確認用戶身份
	    ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));
	    Integer uId = (Integer) session.getAttribute("uId");
	    if (!uId.equals(forumPostVO.getUserVO().getuId())) {
	        // 如果用戶不是貼文的作者，返回錯誤提示
	        model.addAttribute("error", "您無權修改此貼文");
	        return "front-end/forumpost/error";
	    }

	    // 3. 將貼文資料添加到模型中並轉交頁面
	    model.addAttribute("forumPostVO", forumPostVO);
	    return "front-end/forumpost/g2_blog_update_article";
	}
	
	@PostMapping("update")
	public String update(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
	                     @RequestParam("fpPic") MultipartFile[] parts, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
	    // 檢查用戶是否已經登入
	    if (!isUserLoggedIn(session)) {
	        // 如果未登入，重定向到登錄頁面或其他處理方式
	        return "redirect:/user/force_user_login";
	    }

	    // 確認用戶是否是貼文的作者
	    Integer uId = (Integer) session.getAttribute("uId");
	    ForumPostVO originalPost = forumPostSvc.getOneForumPost(forumPostVO.getFpId());
	    if (!uId.equals(originalPost.getUserVO().getuId())) {
	        // 如果用戶不是貼文的作者，返回錯誤提示
	        model.addAttribute("error", "您無權修改此貼文");
	        return "front-end/error";
	    }

	    // 檢查中是否存在相同標題文章
	    boolean titleExists = forumPostSvc.existsByFpTitle(forumPostVO.getFpTitle());
	    if (titleExists) {
	        result.addError(new FieldError("ForumPostVO", "fpTitle", "文章标题已存在"));
	    }

	    // 如果有圖片上傳，將其轉換成byte並設置ForumPostVO中
	    if (!parts[0].isEmpty()) {
	        byte[] fpPic = parts[0].getBytes();
	        forumPostVO.setFpPic(fpPic);
	    }

	    forumPostVO.setFpTime(originalPost.getFpTime());
	    forumPostVO.setFpViews(originalPost.getFpViews());
	    forumPostVO.setFpPic(originalPost.getFpPic());

	    forumPostSvc.updateForumPost(forumPostVO);

	    model.addAttribute("success", "- (修改成功)");
	    forumPostVO = forumPostSvc.getOneForumPost(forumPostVO.getFpId());
	    model.addAttribute("forumPostVO", forumPostVO);
	    return "redirect:/forumpost/details?fpId=" + forumPostVO.getFpId();
	}


	@PostMapping("delete")
	public String delete(@RequestParam("fpId") String fpId, HttpSession session, ModelMap model) {
	    // 檢查用戶是否已經登入
	    if (!isUserLoggedIn(session)) {
	        // 如果未登入，重定向到登錄頁面或其他處理方式
	        return "redirect:/user/force_user_login";
	    }

	    // 確認用戶是否是貼文的作者
	    Integer uId = (Integer) session.getAttribute("uId");
	    ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));
	    if (!uId.equals(forumPostVO.getUserVO().getuId())) {
	        // 如果用戶不是貼文的作者，返回錯誤提示
	        model.addAttribute("error", "只有貼文作者才能刪除貼文");
	        return "front-end/forumpost/error";
	    }

	    // 執行刪除操作
	    forumPostSvc.deleteForumPost(Integer.valueOf(fpId));

	    // 刪除完成後重定向或返回到合適的頁面
	    return "redirect:/forumpost/listAllForumPost";
	}

	
    @GetMapping("listAllForumPost")
	public String listAllForumPost(Model model) {
    	 List<ForumPostVO> forumPostVO = forumPostSvc.getAll();
         model.addAttribute("forumPostVO", forumPostVO);
		return "front-end/forumpost/g2_blog_list";
	}
    
	@GetMapping("select_page")
	public String select_page(Model model) {
		return "front-end/forumpost/select_page";
	}
	
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ForumPostVO forumPostVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(forumPostVO, "ForumPostVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
	private boolean isUserLoggedIn(HttpSession session) {
	    // 檢查會話中是否存在用戶ID，如果存在則認為用戶已登入
	    Integer uId = (Integer) session.getAttribute("uId");
	    return uId != null;
	}
	
	// 根據文章分類獲取預設圖片
	private byte[] getDefaultPicByCategory(String category) {
	    switch (category) {
	        case "重量訓練":
	            return java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPic1());
	        case "皮拉提斯":
	            return java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPic2());
	        case "有氧訓練":
	            return java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPic3());
	        case "徒手健身":
	            return java.util.Base64.getDecoder().decode(DefaultImage2.getDefaultPic4());
	        case "飲食分享":
	            return java.util.Base64.getDecoder().decode(DefaultImage2.getDefaultPic5());
	        default:
	            return null; // 或者設置一個預設的圖片
	    }
	}
	
//	====
	
	@ModelAttribute("forumPostListData")
	protected List<ForumPostVO> forumPostListData() {
		List<ForumPostVO> list = forumPostSvc.getAll();
		return list;
	}
	
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData() {
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	
	@ModelAttribute("ForumPostVO")
	public ForumPostVO getForumPostVO() {
	    return new ForumPostVO(); 
	}


}