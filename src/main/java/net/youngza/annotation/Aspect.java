package net.youngza.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面注解
 * @author bj_yangsong
 */
@Target(ElementType.TYPE) //应用在类
@Retention(RetentionPolicy.RUNTIME) //保存在运行时
public @interface Aspect {
	/**
	 * 注解
	 */
	Class<? extends Annotation> value();
}
