package com.fitanywhere.user.controller;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@RestController
//在路徑上多加一層"/api/"做為區別
@RequestMapping("/user_api")
public class UserRestController {
	
	 @Autowired
	    private UserService userService;

	    @PostMapping("/find_account_by_mail")
	    public int checkAccountbyMail(@RequestBody Map<String, String> requestBody) {
	        String uMail = requestBody.get("u_email");
	        boolean isUserExist = userService.isEmailRegistered(uMail);

	        // 返回數字表示賬戶是否存在：0表示存在，1表示不存在
	        return isUserExist ? 0 : 1;
	    }
	    
	    @PostMapping("/process_user_login")
	    public int processUserLogin(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
	        String uMail = loginData.get("u_email");
	        String password = loginData.get("u_password");
	        UserVO user = userService.userLogin(uMail, password);

	        if (user != null) {
	            // 登入成功的Session處理
	            request.getSession().invalidate(); // 刪除舊Session
	            HttpSession newSession = request.getSession(true); // 建立新Session
	            
	            // 在新Session寫入已登入會員資訊
	            newSession.setAttribute("uId", user.getuId());
	            newSession.setAttribute("uNickname", user.getuNickname());
	            newSession.setAttribute("uCoach", user.getuCoach());
	            newSession.setAttribute("uStatus", user.getuStatus());	
	            
	            //有登入的Session才有"loginStatus" 直接確認Session有沒有"loginStatus"這個項目就能判斷有無登入 "logged_in"值到是可不用比對
	            newSession.setAttribute("loginStatus", "logged_in"); //登入狀態 
	            newSession.setAttribute("uPerm", 9); //暫定9代表一般會員
	            
	            newSession.setAttribute("loginDate", new Date()); //登入時間
	            newSession.setAttribute("lastActiveTime", new Date()); //最後活動時間	            
	            newSession.setMaxInactiveInterval(60 * 60); // Session保存期限(秒)

	            return 0; // 登入成功
	        } else {
	            return 1; // 登入失敗
	        }
	    }

}
