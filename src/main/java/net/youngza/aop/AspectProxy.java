package net.youngza.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 4.切面代理，模板类
 * @author bj_yangsong
 *
 */
public abstract class AspectProxy implements Proxy{
	private static final Logger logger=LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable{
		Object result=null;
		Class<?> clzss=proxyChain.getTargetClass();
		Method method=proxyChain.getTargetMethod();
		Object[] params=proxyChain.getMethodParams();
		begin();
		try{
			if(intercept(clzss, method, params)){
				beafore(clzss, method, params);
				result=proxyChain.doProxyChain();
				after(clzss, method, params);
			}else{
				result=proxyChain.doProxyChain();
			}
		}catch(Exception e){
			logger.error("proxy failure",e);
			error(clzss, method, params);
			throw e;
		}
		return result;
	}
	public void begin(){};
	public boolean intercept(Class<?> clzss,Method method,Object[] params) throws Throwable{
		return true;
	}
	
	public void beafore(Class<?> clzss,Method method,Object[] params)throws Throwable{}
	
	public void after(Class<?> clzss,Method method,Object[] params)throws Throwable{}
	
	public void error(Class<?> clzss,Method method,Object[] params)throws Throwable{}
	
	public void end(){}
}
