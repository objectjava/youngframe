package net.youngza.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * json工具类
 */
public class JsonUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(JsonUtil.class);
	
	private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
	/**
	 * 将POJO转换成JSON
	 */
	public static <T> String toJson(T obj){
		String json;
		try {
			json=OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("conver POJO to JSON failure",e);
			throw new RuntimeException(e);
		}
		return json;
	}
	
	/**
	 * 将json转换成POJO
	 */
	public static <T> T getObject(String json,Class<T> clzss){
		T pojo;
		try {
			pojo=OBJECT_MAPPER.readValue(json, clzss);
		} catch (Exception e) {
			LOGGER.error("conver json to Object failure",e);
			throw new RuntimeException(e);
		} 
		return pojo;
	}
}
