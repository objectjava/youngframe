package net.youngza.http;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.youngza.annotation.Action;
import net.youngza.clzss.ClassHelper;
import net.youngza.util.ArrayUtil;
import net.youngza.util.CollectionUtil;

/**
 * 控制器助手类
 * @author bj_yangsong
 *
 */
public class ControllerHelper {
	//用于存放请求和处理器的映射关系
	private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request, Handler>(); 
	static {
		Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
		if(CollectionUtil.isNotEmpty(controllerClassSet)){
			for(Class<?> controllerClass:controllerClassSet){
				//获取Controller类中定义的方法
				Method[] methods=controllerClass.getDeclaredMethods();
				if(ArrayUtil.isNotEmpty(methods)){
					for(Method method:methods){
						//判断是否有action注解,可以不加判断直接把里面的方法全部当做action
						if(method.isAnnotationPresent(Action.class)){
							//从Action注解中获取URL映射规则
							Action action=method.getAnnotation(Action.class);
							String mapping=action.value();
							//验证URL映射规则,action注解规则
							if(mapping.matches("\\w+:/\\w*")){
								String[] array=mapping.split(":");
								if(ArrayUtil.isNotEmpty(array)&&array.length==2){
									//获取请求方法和请求路径
									String requestMethod=array[0];
									String requestPath=array[1];
									Request request=new Request(requestMethod, requestPath);//方法，和请求路径
									Handler handler=new Handler(controllerClass,method); //class method
									//初始化action map
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}
	
	//获取handler
	public static Handler getHandler(String requestMethod,String requestPath){
		Request request=new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
