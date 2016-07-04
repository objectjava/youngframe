package net.youngza.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码与解码工具类
 * @author bj_yangsong
 *
 */
public class CodeUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(CodeUtil.class);
	
	/**
	 * 将url编码
	 */
	public static String encodeURL(String source){
		String target;
		try {
			target=URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("encode url failure",e);
			throw new RuntimeException(e); //向上抛不用初始化
		}
		return target;
	}
	/**
	 * 将url解码
	 */
	public static String decodeURL(String source){
		String target;
		try {
			target=URLDecoder.decode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("decode url failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}
	
}
