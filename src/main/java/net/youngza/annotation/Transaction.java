package net.youngza.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//定义事物注解
@Target(ElementType.METHOD) //应用在方法
@Retention(RetentionPolicy.RUNTIME) //保存在运行时
public @interface Transaction {

}
