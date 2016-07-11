package net.youngza.db;

import java.lang.reflect.Method;

import net.youngza.annotation.Aspect;
import net.youngza.annotation.Service;
import net.youngza.annotation.Transaction;
import net.youngza.aop.AspectProxy;

/**
 * 事物切面
 * @author bj_yangsong
 */
@Aspect(Service.class)
public class AspectTransaction extends AspectProxy{
	//可以避免同一个线程中 事物逻辑执行多次，以为service会调用多个db操作，这里还可以加事物传播级别
	private static final ThreadLocal<Boolean> FLAG_HOLDER=new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {
			return false;
		};
	};
	
	@Override
	public boolean intercept(Class<?> clzss, Method method, Object[] params)
			throws Throwable {
		boolean flag=FLAG_HOLDER.get();
		if(!flag&&method.isAnnotationPresent(Transaction.class)){
			FLAG_HOLDER.set(true);
			return true;
		}
		return false;
	}

	@Override
	public void begin() {
		
	}

	@Override
	public void beafore(Class<?> clzss, Method method, Object[] params)
			throws Throwable {
		DbConnectionUtil.beginTransaction();
	}

	@Override
	public void after(Class<?> clzss, Method method, Object[] params)
			throws Throwable {
		DbConnectionUtil.CommitTransaction();
	}

	@Override
	public void error(Class<?> clzss, Method method, Object[] params)
			throws Throwable {
		DbConnectionUtil.rollBackTransaction();
	}

	@Override
	public void end() {
		FLAG_HOLDER.remove();
	}
}
