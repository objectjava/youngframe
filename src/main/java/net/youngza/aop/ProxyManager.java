package net.youngza.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 3.代理管理器，创建所有代理对象
 * @author bj_yangsong
 * cglib 动态代理
 * 工厂模式
 */
public class ProxyManager{
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass,final List<Proxy> proxyList){
		//获取代理
		return (T) Enhancer.create(targetClass, new MethodInterceptor(){
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
				.doProxyChain(); //调用invokeSuper，获取的是被拦截方法的返回值
			}
		});
	}
}
