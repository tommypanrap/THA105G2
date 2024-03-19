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
import org.springframework.web.bind.annotation.PathVariable;
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
	public String addForumPost(ModelMap model, @ModelAttribute("forumPostVO") ForumPostVO forumPostVO) {
	    if (forumPostVO == null) {
	        forumPostVO = new ForumPostVO();
	    }
	    model.addAttribute("forumPostVO", forumPostVO);
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
	                     @RequestParam("fpPic") MultipartFile[] parts, RedirectAttributes redirectAttributes) throws IOException {
	    
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
            byte[] defaultPic = null;
            switch (category) {
                case "重量訓練":
                    defaultPic = java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPic1());
                    break;
                case "皮拉提斯":
                    defaultPic = java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPic2());
                    break;
                case "有氧訓練":
                    defaultPic = java.util.Base64.getDecoder().decode(DefaultImage.getDefaultPic3());
                    break;
                case "徒手健身":
                    defaultPic = java.util.Base64.getDecoder().decode(DefaultImage2.getDefaultPic4());
                    break;
                case "飲食分享":
                    defaultPic = java.util.Base64.getDecoder().decode(DefaultImage2.getDefaultPic5());
                    break;
            }
            
            // 將預設圖片設置到ForumPostVO對象中
            forumPostVO.setFpPic(defaultPic);
        } else {
            // 將用戶上傳的圖片轉換為字節數組並存儲到ForumPostVO對象中
            forumPostVO.setFpPic(parts[0].getBytes());
        }

        forumPostSvc.addForumPost(forumPostVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<ForumPostVO> list = forumPostSvc.getAll();
        model.addAttribute("forumPostListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/forumpost/listAllForumPost";
    }

	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("fpId") String fpId, ModelMap model) {
		/*************************** 2.開始查詢資料 *****************************************/
		ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("forumPostVO", forumPostVO);
		return "front-end/forumpost/g2_blog_update_article"; 
	}
	
	@PostMapping("update")
	public String update(@Valid ForumPostVO forumPostVO, BindingResult result, ModelMap model,
	                     @RequestParam("fpPic") MultipartFile[] parts, RedirectAttributes redirectAttributes) throws IOException {

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

	    forumPostVO.setFpTime(forumPostSvc.getOriginalFpTime(forumPostVO.getFpId()));
        forumPostVO.setFpViews(forumPostSvc.getOriginalFpViews(forumPostVO.getFpId()));
        forumPostVO.setFpPic(forumPostSvc.getOriginalFpPic(forumPostVO.getFpId()));
        
//	    if (result.hasErrors()) {
//            // 將user輸入的數據保存到 Model 中
//	        redirectAttributes.addFlashAttribute("forumPostVO", forumPostVO);
//	        return "redirect:/forumpost/update_forumpost_input";
//	    }

	    forumPostSvc.updateForumPost(forumPostVO);

	    model.addAttribute("success", "- (修改成功)");
	    forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(forumPostVO.getFpId()));
	    model.addAttribute("forumPostVO", forumPostVO);
	    return "front-end/forumpost/listOneForumPost"; // 修改成功後轉交listOneUser.html
	}


	@PostMapping("delete")
	public String delete(@RequestParam("fpId") String fpId, HttpSession session, ModelMap model) {
	    // 1. 從 session 中獲取當前用戶身份信息
	    UserVO currentUser = (UserVO) session.getAttribute("currentUser");
	    if (currentUser == null) {
	        // 如果用戶未登錄，重定向到登錄頁面或返回錯誤信息
	        return "redirect:/login";
	    }

	    // 2. 獲取要刪除的貼文
	    ForumPostVO forumPostVO = forumPostSvc.getOneForumPost(Integer.valueOf(fpId));

	    // 3. 檢查用戶身份是否有權刪除貼文
	    if (!currentUser.equals(forumPostVO.getUserVO())) {
	        // 如果用戶不是貼文作者，則拒絕刪除操作
	        model.addAttribute("error", "只有貼文作者才能刪除貼文");
	        return "front-end/error"; // 返回一個錯誤頁面或重新導
	    }

	    // 4. 執行刪除操作
	    forumPostSvc.deleteForumPost(Integer.valueOf(fpId));

	    // 5. 刪除完成後重定向或返回到合適的頁面
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