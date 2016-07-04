package net.youngza.util;

import org.apache.commons.lang3.ArrayUtils;

/*
 * 数组工具类
 */
public class ArrayUtil {
	//判断数组是否为空
	public static boolean isEmpty(Object[] obj){
		return ArrayUtils.isEmpty(obj);
	}
	
	//判断数组是否非空
	public static boolean isNotEmpty(Object[] obj){
		return !ArrayUtils.isEmpty(obj);
	}
}
