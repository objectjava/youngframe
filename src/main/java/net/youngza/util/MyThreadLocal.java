package net.youngza.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/*
 * 模拟实现ThreadLocal
 * 就是用该线程隔离开了
 * 引用场景：
 * 1.使用在jdbc中connection达到事物，在生产中推荐使用连接池
 * 事物：ACID
 * 1.原子性（基础）：要么做完要么不做
 * 2.一致性（老大）：A，B同步
 * 3.隔离性（手段）：update、delete，保证一致性的重要手段
 * 隔离很难实现：规定规范，事物隔离级别  解决高并发下 脏读、不可重复读、幻读 ，一个事物影响到另一个事物了
 * 脏读：A事物读取了b事物未提交的结果并做了操作
 * 不可重复读：A事物读取了b事物已提交的更改数据
 * 幻读：A事物读取了B事物已提交的新增数据
 * 当插入数据时候锁定表，当修改数据的时候锁定行
 * 4.持久性（目的）
 * 
 * Spring事物传播级别
 */
public class MyThreadLocal<T> {
	private Map<Thread, T> container=Collections.synchronizedMap(new HashMap<Thread, T>());
	
	public void set(T value){
		container.put(Thread.currentThread(), value);
	}
	
	public T get(){
		Thread thread=Thread.currentThread();
		T value=container.get(thread);
		if(value==null&& !container.containsKey(thread)){
			value=initialValue();
			set(value);
		}
		return value;
	}
	
	public void remove(){
		container.remove(Thread.currentThread());
	}
	
	protected T initialValue(){
		return null;
	}
}
