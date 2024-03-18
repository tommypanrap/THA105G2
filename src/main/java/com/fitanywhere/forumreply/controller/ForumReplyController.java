package com.fitanywhere.forumreply.controller;

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

import com.fitanywhere.forumpost.model.ForumPostService;
import com.fitanywhere.forumpost.model.ForumPostVO;
import com.fitanywhere.forumreply.model.ForumReplyService;
import com.fitanywhere.forumreply.model.ForumReplyVO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@Controller
@RequestMapping("/forumreply")
public class ForumReplyController {

	@Autowired
	ForumReplyService forumReplySvc;

	@Autowired
	UserService userSvc;
	
	@Autowired
	ForumPostService forumPostSvc;

	 @GetMapping("addForumReply")
	    public String addForumReply(@RequestParam("fpId") int fpId, ModelMap model) {
	        ForumReplyVO forumReplyVO = new ForumReplyVO(); // 创建一个新的 ForumReplyVO 对象
	        List<ForumPostVO> forumPostListData = forumPostSvc.getAll(); // 获取所有贴文数据
	        model.addAttribute("forumReplyVO", forumReplyVO); // 将 ForumReplyVO 对象添加到模型中
	        model.addAttribute("forumPostListData", forumPostListData); // 将所有贴文数据添加到模型中
	        model.addAttribute("fpId", fpId); // 添加postId到模型中
	        return "front-end/forumreply/addForumReply"; // 返回新增页面
	    }


	 @PostMapping("insert")
		public String insert(@Valid ForumReplyVO forumReplyVO, BindingResult result, ModelMap model,
		                     @RequestParam("frPic") MultipartFile[] parts, RedirectAttributes redirectAttributes) throws IOException {
		    
	     // 将留言信息保存到数据库中
	     forumReplySvc.addForumReply(forumReplyVO);
	     
	     Integer fpId = forumReplyVO.getForumPostVO().getFpId();
	     model.addAttribute("ForumReplyVO", forumReplyVO);

	     // 重定向到留言所属的文章详情页面
	     return "redirect:/forumpost/g2_blog_Details?postId=" + fpId;
	 }
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("frId") String frId, ModelMap model) {
		/*************************** 2.開始查詢資料 *****************************************/
		ForumReplyVO forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(frId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("ForumReplyVO", forumReplyVO);
		return "front-end/forumreply/update_forumreply_input"; 
	}
	
	@PostMapping("update")
	public String update(@Valid ForumReplyVO forumReplyVO, BindingResult result, ModelMap model,
	                     @RequestParam("frPic") MultipartFile[] parts, RedirectAttributes redirectAttributes) throws IOException {

	    // 如果有圖片上傳，將其轉換成byte並設置ForumPostVO中
	    if (!parts[0].isEmpty()) {
	        byte[] frPic = parts[0].getBytes();
	        forumReplyVO.setFrPic(frPic);
	    }

	    forumReplyVO.setFrTime(forumReplySvc.getOriginalFrTime(forumReplyVO.getFrId()));
        forumReplyVO.setFrPic(forumReplySvc.getOriginalFrPic(forumReplyVO.getFrId()));
        
	    forumReplySvc.updateForumReply(forumReplyVO);

	    model.addAttribute("success", "- (修改成功)");
	    forumReplyVO = forumReplySvc.getOneForumReply(Integer.valueOf(forumReplyVO.getFrId()));
	    model.addAttribute("ForumReplyVO", forumReplyVO);
	    return "front-end/forumreply/listOneForumReply"; 
	}


	@PostMapping("delete")
	public String delete(@RequestParam("frId") String frId, HttpSession session, ModelMap model) {
	    // 1. 從 session 中獲取當前用戶身份信息
	    UserVO currentUser = (UserVO) session.getAttribute("currentUser");
	    if (currentUser == null) {
	        // 如果用戶未登錄，重定向到登錄頁面或返回錯誤信息
	        return "redirect:/login";
	    }

	    // 2. 獲取要刪除的貼文
	    ForumReplyVO forumReply = forumReplySvc.getOneForumReply(Integer.valueOf(frId));

	    // 3. 檢查用戶身份是否有權刪除貼文
	    if (!currentUser.equals(forumReply.getUserVO())) {
	        // 如果用戶不是貼文作者，則拒絕刪除操作
	        model.addAttribute("error", "只有貼文作者才能刪除貼文");
	        return "front-end/error"; // 返回一個錯誤頁面或重新導
	    }

	    // 4. 執行刪除操作
	    forumReplySvc.deleteForumReply(Integer.valueOf(frId));

	    // 5. 刪除完成後重定向或返回到合適的頁面
	    return "redirect:/forumreply/listAllForumReply";
	}

	
    @GetMapping("listAllForumReply")
	public String listAllForumReply(Model model) {
    	 List<ForumReplyVO> forumReplys = forumReplySvc.getAll();
         model.addAttribute("forumReplys", forumReplys);
		return "front-end/forumreply/listAllForumReply";
	}
    
	@GetMapping("select_page")
	public String select_page(Model model) {
		return "front-end/forumreply/select_page";
	}
	
	
	
	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(ForumReplyVO forumReplyVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(forumReplyVO, "ForumReplyVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}
	
//	====
	
	@ModelAttribute("forumReplyListData")
	protected List<ForumReplyVO> forumReplyListData() {
		List<ForumReplyVO> list = forumReplySvc.getAll();
		return list;
	}
	
	@ModelAttribute("userListData")
	protected List<UserVO> referenceListData() {
		List<UserVO> list = userSvc.getAll();
		return list;
	}
	
	@ModelAttribute("ForumReplyVO")
	public ForumReplyVO getForumReplyVO() {
	    return new ForumReplyVO(); 
	}


}