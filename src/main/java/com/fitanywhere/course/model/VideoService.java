package com.fitanywhere.course.model;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface VideoService {
	
	ResponseEntity<StreamingResponseBody> getPartialVideo(String rangeHeader, String videoId) throws IOException;
}
