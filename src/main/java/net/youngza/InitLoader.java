package net.youngza;


import net.youngza.aop.AOPHelper;
import net.youngza.bean.BeanHelper;
import net.youngza.clzss.ClassHelper;
import net.youngza.clzss.ClassUtil;
import net.youngza.http.ControllerHelper;
import net.youngza.ioc.IOCHelper;

/**
 * 加在相应的初始化Helper类
 * @author bj_yangsong
 */
public class InitLoader {
	public static void init(){
		Class<?> clzsses[]={
				ClassHelper.class,//加载所有指定包目录下的class list
				BeanHelper.class,//加载所有class与bean对象之间的映射
				AOPHelper.class,//代理加载
				IOCHelper.class,//将所有对象注入到Autowired注解Field上
				ControllerHelper.class//加载所有controller类，并实现（requestMethod,requestPath）:(controllerClass,Method)的映射
		};
		for(Class<?> clzss:clzsses){
			ClassUtil.loadClass(clzss.getName(), true);//加载类，运行static方法块，其实在第一次调用的时候我们就会运行static代码块，这里只是集中加载
		}
	}
}
