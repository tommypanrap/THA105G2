package com.fitanywhere.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fitanywhere.filter.LoginStatusFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginStatusFilter> loginStatusFilter() {
        FilterRegistrationBean<LoginStatusFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginStatusFilter());
//      請在這邊設定需要過濾登入狀態的網址  
        registrationBean.addUrlPatterns("/user/test/*", "/test_B/", "/test_C/");
        return registrationBean;
    }
//    =========================================================================
    
}
