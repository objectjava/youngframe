package net.youngza.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 依赖注入注解
 * @author bj_yangsong
 *
 */
@Target(ElementType.FIELD) //应用在属性
@Retention(RetentionPolicy.RUNTIME) //保存在运行时
public @interface Autowired {

}
