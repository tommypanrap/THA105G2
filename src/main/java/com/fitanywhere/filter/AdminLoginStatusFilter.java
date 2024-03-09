package com.fitanywhere.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Eugen
//要過濾網址請到com.fitanywhere.config中的FilterConfig設定
public class AdminLoginStatusFilter implements Filter {

 @Override
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
     HttpServletRequest httpRequest = (HttpServletRequest) request;
     HttpServletResponse httpResponse = (HttpServletResponse) response;
     HttpSession session = httpRequest.getSession(false);
     
     if (session == null || !"admin_logged_in".equals(session.getAttribute("adminLoginStatus"))) {
         String contextPath = httpRequest.getContextPath();
         //呼叫專用的Controller處理跳轉登入畫面         
         httpResponse.sendRedirect(contextPath + "/backend_login");
     } else {
         chain.doFilter(request, response);
     }
 }
}
