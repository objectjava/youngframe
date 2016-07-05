package net.youngza.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.youngza.clzss.ClassHelper;
import net.youngza.reflection.ReflectionUtil;

/*
 * Bean 助手类，单例的
 */
public class BeanHelper {
	//定义bean映射（用于存放bean类与bean实例的映射）
	private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>, Object>();
	
	static{
		Set<Class<?>> classSet=ClassHelper.getBeanClassSet();
		for(Class<?> clzss:classSet){
			Object obj=ReflectionUtil.newInstance(clzss);
			BEAN_MAP.put(clzss, obj);
		}
	}
	//获取Bean映射
	public static Map<Class<?>,Object> getBeanMap(){
		return BEAN_MAP;
	}

	//获取bean实例,定义泛型，这样在调用时就不用强制类型转换了
	public static <T> T getBeanInstance(Class<T> clzss){
		if(!BEAN_MAP.containsKey(clzss)){
			throw new RuntimeException("can not get bean class:"+clzss);
		}
		return (T)BEAN_MAP.get(clzss);
	}
	//设置bean实例
	public static void setBeanInstance(Class<?> clzss,Object obj){
		BEAN_MAP.put(clzss, obj);
	}
}
