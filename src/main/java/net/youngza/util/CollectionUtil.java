package net.youngza.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * 集合工具类 
 * @author bj_yangsong
 *
 */
public class CollectionUtil {
	//判断Collection是否为空
	public static boolean isEmpty(Collection<?> collection){
		return CollectionUtils.isEmpty(collection);
	}
	
	//判断collection是否非空
	public static boolean isNotEmpty(Collection<?> collection){
		return CollectionUtils.isNotEmpty(collection);
	}
	
	//判断map是否为空
	public static boolean isEmpty(Map<?,?> map){
		return MapUtils.isEmpty(map);
	}
	//判断map是否非空
	public static boolean isNotEmpty(Map<?,?> map){
		return MapUtils.isNotEmpty(map);
	}
}
