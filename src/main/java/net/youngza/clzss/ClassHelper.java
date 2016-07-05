package net.youngza.clzss;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import net.youngza.annotation.Controller;
import net.youngza.annotation.Service;
import net.youngza.conf.ConfigUtil;

/**
 * 类操作助手类
 * @author bj_yangsong
 *
 */
public final class ClassHelper {
	/**
	 * 用于存放所加载的类
	 */
	private static final Set<Class<?>> CLASS_SET;
	static {
		String basePackage=ConfigUtil.getBasePackage();
		CLASS_SET=ClassUtil.getClassSet(basePackage);
	}
	/**
	 * 获取应用包名下所类
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * 获取应用报名下所有service类
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> clzss:CLASS_SET){
			if(clzss.isAnnotationPresent(Service.class)){
				classSet.add(clzss);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取引用下所有controller类
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> clzss:CLASS_SET){
			if(clzss.isAnnotationPresent(Controller.class)){
				classSet.add(clzss);
			}
		}
		return classSet;
	}
	/**
	 * 获取所有bean 只扫面Service Controller注解的类
	 */
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		classSet.addAll(getServiceClassSet());
		classSet.addAll(getControllerClassSet());
		return classSet;
	}
	/**
	 * 获取应用包名下某父类（或接口）的所有子类（或实现类）
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> clzss:CLASS_SET){
			//是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口
			if(superClass.isAssignableFrom(clzss)&&!superClass.equals(clzss)){
				classSet.add(clzss);
			}
		}
		return classSet;
	}
	/**
	 * 获取应用包名下带有某注解的所有类
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> clzss:CLASS_SET){
			if(clzss.isAnnotationPresent(annotationClass)){
				classSet.add(clzss);
			}
		}
		return classSet;
	}
}
