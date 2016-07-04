package net.youngza.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bj_yangsong
 * 服务类注解
 */
@Target(ElementType.TYPE) //应用在类上
@Retention(RetentionPolicy.RUNTIME) //保存在运行时
public @interface Service {
	
}
