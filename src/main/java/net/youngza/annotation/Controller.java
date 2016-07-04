package net.youngza.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制器注解
 * @author bj_yangsong
 */
@Target(ElementType.TYPE) //应用在类上
@Retention(RetentionPolicy.RUNTIME) //保存在运行时
public @interface Controller {

}
