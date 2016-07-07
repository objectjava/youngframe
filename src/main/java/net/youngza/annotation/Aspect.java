package net.youngza.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面注解
 * @author bj_yangsong
 * AOP version 1.0
 */
@Target(ElementType.TYPE) //应用在类
@Retention(RetentionPolicy.RUNTIME) //保存在运行时
public @interface Aspect {
	/**
	 * 注解，必须是有注解的类才能使用aop 比如值为Controller 那么这个切面就会拦截所有controller
	 */
	Class<? extends Annotation>[] value();
}
