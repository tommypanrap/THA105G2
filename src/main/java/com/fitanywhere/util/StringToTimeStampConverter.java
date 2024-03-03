package com.fitanywhere.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class StringToTimeStampConverter implements Converter<String, Timestamp> {

	@Override
	public Timestamp convert(String source) {
		if(source == null || source.trim().isEmpty()) 
			return null;
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

			java.util.Date parsedDate = dateFormat.parse(source);

			return new Timestamp(parsedDate.getTime());
//			return source.trim()=null? new Timestamp(parsedDate.getTime()) :null;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd'T'HH:mm:ss format.", e);
		}
	}

}
