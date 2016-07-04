package net.youngza.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流操作工具类
 * @author bj_yangsong
 *
 */
public class StreamUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(StreamUtil.class);
	
	/**
	 * 从输入流中获取字符串
	 */
	public static String getString(InputStream in){
		//StringBuffer和StringBuilder类的区别也在于此，新引入的StringBuilder类不是线程安全的，但其在单线程中的性能比StringBuffer高
		StringBuilder stringBuilder=new StringBuilder();
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			String line;
			while((line=reader.readLine())!=null){
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			LOGGER.error("get String failure.",e);
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();
	}
	
}
