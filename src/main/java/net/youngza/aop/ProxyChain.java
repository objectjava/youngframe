package net.youngza.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;
//2.代理执行
public class ProxyChain {
	private final Class<?> targetClass; //目标类
	private final Object targetObject; //目标对象
	private final Method targetMethod;//目标方法
	private final MethodProxy methodProxy;//方法代理
	private final Object[] methodParams;//方法参数
	private List<Proxy> proxyList=new ArrayList<Proxy>();//代理列表
	private int proxyIndex=0;//代理索引
	
	public ProxyChain(Class<?> targetClass, Object targetObject,
			Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}
	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}
	public Object doProxyChain() throws Throwable {
		Object methodResult;
		if(proxyIndex<proxyList.size()){
			methodResult=proxyList.get(proxyIndex++).doProxy(this); //调用拦截实例化对象的doProxy，也就是切面中的
		}else{
			methodResult=methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}
}
