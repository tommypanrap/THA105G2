package com.fitanywhere.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Eugen
@Configuration
public class TimeZoneConfig {

	private static final Logger logger = LoggerFactory.getLogger(TimeZoneConfig.class);

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
        logger.info("應用時區已設置為：{}", TimeZone.getDefault().getID());
    }
}
