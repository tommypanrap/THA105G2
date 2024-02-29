package com.fitanywhere.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


// 要過濾網址請到com.fitanywhere.config中的FilterConfig設定
public class LoginStatusFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session == null || !"logged_in".equals(session.getAttribute("loginStatus"))) {
            String contextPath = httpRequest.getContextPath();
            //呼叫專用的Controller處理跳轉登入畫面
            httpResponse.sendRedirect(contextPath + "/user/force_user_login");
        } else {
            chain.doFilter(request, response);
        }
    }
}
