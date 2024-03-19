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

		// Eugen添加了"/opinion/user_opinion_list/*",
		// "/user_create_opinion/*","/opinion_api/user_create_opinion"
		// Joy 添加了"/order/*","/cart/*"
		// Mok 添加"/course/my_courses/*"
		// XiaoXin 添加了"/adCarousel/addAdCarousel/*"
		registrationBean.addUrlPatterns("/opinion/user_opinion_list/*", "/user_create_opinion/*",
				"/opinion_api/user_create_opinion", "/socialpost/*", "/order/*", "/cart/*", "/course/my_courses/*","/adCarousel/addAdCarousel/*");
		return registrationBean;
	}


//    =========================================================================

//  要過濾器的網址請記得用星號結果 例如想過濾Controller的"/testFolder/test/" 請寫成"/testFolder/test/*"
	@Bean
	public FilterRegistrationBean<AdminLoginStatusFilter> adminLoginStatusFilter() {
		FilterRegistrationBean<AdminLoginStatusFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AdminLoginStatusFilter());
//      請在這邊設定需要過濾管理員登入狀態的網址
		// Eugen添加了"/backend_userlist/*", "/backend_userlist_searchByEmail/*",
		// "/backend_userlist_api/*", "/backend_opinion/*"
		// Joy 添加了"/backend/order-get"
		registrationBean.addUrlPatterns("/backend_userlist/*", "/backend_userlist_searchByEmail/*",
				"/backend_userlist_api/*", "/backend_opinion/*", "/update_opinion_reply/*", "/backend/*","/backend_ad/addAd/*","/backend_ad/getOne_For_Update/*","/adCarousel/getOne_For_Updateadcarousel/*");
		return registrationBean;
	}


}
