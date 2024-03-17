package com.fitanywhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
	
//	排程器找adCarouselRepository中將結束日期已經過了的方案狀態改為下架的sql
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
