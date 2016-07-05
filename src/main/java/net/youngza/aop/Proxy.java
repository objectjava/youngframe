package net.youngza.aop;

public interface Proxy {
	/**
	 * 1.执行链式代理，可以有多个切面所以要用链式
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
}
