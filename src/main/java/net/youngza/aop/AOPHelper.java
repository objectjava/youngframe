package net.youngza.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.youngza.annotation.Aspect;
import net.youngza.bean.BeanHelper;
import net.youngza.clzss.ClassHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 可以自定义注解来实现要拦截的类
 * 
 *1. 切面需要带上@Aspect注解且继承AspectProxy(模板作用)，覆盖其中的start after等方法
 *2. 获取切面与被拦截类的列表
 *3. 或得拦截类与切面对象的列表
 */
public class AOPHelper {
	private static final Logger LOGGER=LoggerFactory.getLogger(AOPHelper.class);
	static {
		try{
			Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap(); //切面类：拦截类
			Map<Class<?>,List<Proxy>> targetMap=createTargetMap(proxyMap);
			for(Map.Entry<Class<?>, List<Proxy>> targetEntry:targetMap.entrySet()){
				Class<?> targetClass=targetEntry.getKey();//需要被拦截的类
				List<Proxy> proxyList=targetEntry.getValue();//切面实例
				Object proxy=ProxyManager.createProxy(targetClass, proxyList); //返回代理对象
				BeanHelper.setBeanInstance(targetClass, proxy);//aop 切面中也需要注入值,估计这里有错误
			}
		}catch(Exception e){
			LOGGER.error("aop failure",e);
		}
	}
	
	//获取所有切面类，需要拦截的类列表：一个切面对应多个拦截列表
	private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();
		Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);//获取所有切面class
		for(Class<?> proxyClass:proxyClassSet){
			if(proxyClass.isAnnotationPresent(Aspect.class)){//并且有@Aspect注解
				Aspect aspect=proxyClass.getAnnotation(Aspect.class);//获取切面上的注解
				Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
		return proxyMap;
	}
	
	//获取除Aspect.value 注解上的Class列表，也就是获取所有需要拦截的列表，比如获取所有带controller类的注解类
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet=new HashSet<Class<?>>();
		Class<? extends Annotation>[] annotations=aspect.value();
		for(Class<? extends Annotation> annotation:annotations){
			if(annotation !=null && !annotation.equals(Aspect.class)){ //切面上不能再加切面了
				targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
			}
		}
		return targetClassSet;
	}
	
	//转换成一个拦截列表，对应多个实例化切面类
	private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
		Map<Class<?>,List<Proxy>> targetMap=new HashMap<Class<?>, List<Proxy>>();
		for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
			Class<?> proxyClass=proxyEntry.getKey();//切面，切面也实现了Proxy接口
			Set<Class<?>> targetClassSet=proxyEntry.getValue();//需要拦截的列表
			for(Class<?> targetClass:targetClassSet){
				Proxy proxy=(Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(targetClass)){
					targetMap.get(targetClass).add(proxy);
				}else{
					List<Proxy> proxyList=new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
