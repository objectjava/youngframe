package net.youngza.http;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 * @author bj_yangsong
 *
 */
public class Handler {
	//controller 类
	private Class<?> controllerClasses;
	//action方法
	private Method actionMethod;
	
	public Handler(Class<?> controllerClasses, Method actionMethod) {
		this.controllerClasses = controllerClasses;
		this.actionMethod = actionMethod;
	}
	public Method getActionMethod() {
		return actionMethod;
	}
	public Class<?> getControllerClass(){
		return controllerClasses;
	}
}
