package com.fitanywhere;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@ModelAttribute("isLoggedIn")
    public boolean addGlobalAttributes(HttpServletRequest request) {
        // 檢查session或其他機制來判斷用戶是否登入
        HttpSession session = request.getSession(false);
        return (session != null && session.getAttribute("uId") != null);
    }
}
