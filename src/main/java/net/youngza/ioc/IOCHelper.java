package net.youngza.ioc;

import java.lang.reflect.Field;
import java.util.Map;

import net.youngza.annotation.Autowired;
import net.youngza.bean.BeanHelper;
import net.youngza.reflection.ReflectionUtil;
import net.youngza.util.ArrayUtil;
import net.youngza.util.CollectionUtil;

/**
 * 依赖注入工具类
 * @author bj_yangsong
 * 遍历所有serivce 和controller 之后填充其中的autowired
 * 只要加在，ioc就自动注入了
 */
public class IOCHelper {
	static {
		Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
		if(CollectionUtil.isNotEmpty(beanMap)){
			for(Map.Entry<Class<?>, Object> entry:beanMap.entrySet()){
				Class<?> beanClass=entry.getKey();
				Object beanInstance=entry.getValue();
				/*
				 * getFields()只能获取public的字段，包括父类的。
				 * 而getDeclaredFields()只能获取自己声明的各种字段，
				 * 包括public，protected，private。
				 */
				Field[] beanFileds=beanClass.getDeclaredFields();
				if(ArrayUtil.isNotEmpty(beanFileds)){
					for(Field field:beanFileds){
						//判断属性是否是Autowired注解
						if(field.isAnnotationPresent(Autowired.class)){
							//获得field的数据类型
							Class<?> beanFieldClass=field.getType();
							Object beanFieldInstance=beanMap.get(beanFieldClass);
							if(beanFieldInstance!=null){
								//通过反射初始化field值
								ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
