package net.youngza.http;

import java.util.Map;

import net.youngza.util.CastUtil;

/**
 * 请求参数对象
 * @author bj_yangsong
 */
public class Param {
	private Map<String,Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	/**
	 * 根据参数获取long型数据
	 */
	public long getLong(String name){
		return CastUtil.castLong(paramMap.get(name));
	}
	
	/**
	 * 获取所有字段信息
	 */
	public Map<String,Object> getMap(){
		return paramMap;
	}
}
