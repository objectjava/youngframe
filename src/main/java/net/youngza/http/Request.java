package net.youngza.http;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author bj_yangsong
 * 封装请求信息
 */
public class Request {
	//请求方法
	private String requestMehtod;
	//请求路径
	private String requestPath;
	public Request(String requestMethod, String requestPath) {
		this.requestMehtod = requestMehtod;
		this.requestPath = requestPath;
	}
	public String getRequestMehtod() {
		return requestMehtod;
	}
	public String getRequestPath() {
		return requestPath;
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
}
