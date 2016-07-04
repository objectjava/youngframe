package net.youngza.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * properties 文件加载工具类
 * @author bj_yangsong 
 */
public class ProperUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProperUtil.class);

	// 加在属性文件
	public static Properties loadProperties(String fileName) {
		InputStream inputStream = null;
		Properties properties = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (inputStream == null) {
				throw new FileNotFoundException(fileName + " file not found");
			}
			properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("close input stream failure", e);
				}
			}
		}
		return null;
	}

	// 获取字符型属性，不存在则返回""
	public static String gets(Properties properties,String key) {
		return gets(properties,key, "");
	}

	// 获取字符型属性，可以指定默认值
	public static String gets(Properties properties,String key, String defaultVal) {
		return properties.containsKey(key) ? properties.getProperty(key)
				: defaultVal;
	}

	// 获取整数类型，不存在则返回0
	public static int getInt(Properties properties,String key) {
		return getInt(properties,key, 0);
	}

	// 获取整数类型，可以指定默认值
	public static int getInt(Properties properties,String key, int deafultVal) {
		return properties.containsKey(key) ? CastUtil.castInt(properties.get(key))
				: deafultVal;
	}
	
	//获取boolean值，不存在返回false
	public static boolean getBoolean(Properties properties,String key){
		return getBoolean(properties,key, false);
	}
	
	//获取boolean值，可以指定默认值
	public static boolean getBoolean(Properties properties,String key,boolean defaultVal){
		return properties.containsKey(key)?CastUtil.castBoolean(properties.get(key)):defaultVal;
	}
}
