package com.fitanywhere.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fitanywhere.filter.AdminLoginStatusFilter;
import com.fitanywhere.filter.LoginStatusFilter;

// Eugen
@Configuration
public class FilterConfig {
//  要過濾器的網址請記得用星號結果 例如想過濾Controller的"/testFolder/test/" 請寫成"/testFolder/test/*"
    @Bean
    public FilterRegistrationBean<LoginStatusFilter> loginStatusFilter() {
        FilterRegistrationBean<LoginStatusFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginStatusFilter());
//      請在這邊設定需要過濾登入狀態的網址  
        registrationBean.addUrlPatterns("/user/test_B/*", "/test_B/", "/test_C/","/socialpost/student_socialpost");
        return registrationBean;
    }
//    =========================================================================
    @Bean
    public FilterRegistrationBean<AdminLoginStatusFilter> adminLoginStatusFilter() {
        FilterRegistrationBean<AdminLoginStatusFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminLoginStatusFilter());
//      請在這邊設定需要過濾管理員登入狀態的網址  
        registrationBean.addUrlPatterns("/backend_user1111/*", "/其他後端");
        return registrationBean;
    }
    
}
