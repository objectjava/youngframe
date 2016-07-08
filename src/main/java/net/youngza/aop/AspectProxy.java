package net.youngza.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 4.切面代理，模板类
 * @author bj_yangsong
 * 只要继承这个类并且重写其中方法即可实现切面
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
				result=proxyChain.doProxyChain();//加多少个拦截那肯定就执行多少次方法
				after(clzss, method, params);
			}else{
				result=proxyChain.doProxyChain();
			}
		}catch(Exception e){
			logger.error("proxy failure",e);
			error(clzss, method, params);
			throw e;
		}finally{
			end();
		}
		return result;
	}
	//开始
	public void begin(){};
	
	/**
	 * 过滤方法，符合条件的方法才被拦截
	 * @param clzss
	 * @param method
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public boolean intercept(Class<?> clzss,Method method,Object[] params) throws Throwable{
		return true;
	}
	
	/**
	 * 前置增强
	 * @param clzss
	 * @param method
	 * @param params
	 * @throws Throwable
	 */
	public abstract void beafore(Class<?> clzss,Method method,Object[] params)throws Throwable;
	/**
	 * 后置增强
	 * @param clzss
	 * @param method
	 * @param params
	 * @throws Throwable
	 */
	public abstract void after(Class<?> clzss,Method method,Object[] params)throws Throwable;
	
	/**
	 * 抛出异常
	 * @param clzss
	 * @param method
	 * @param params
	 * @throws Throwable
	 */
	public abstract void error(Class<?> clzss,Method method,Object[] params)throws Throwable;
	
	/**
	 * return之前
	 */
	public abstract void end();
}
