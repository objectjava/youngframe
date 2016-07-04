package net.youngza.conf;

import java.util.Properties;

import net.youngza.constant.ConfigConstant;
import net.youngza.util.ProperUtil;

public class ConfigUtil {
	private static final Properties properties=ProperUtil.loadProperties("config.properties");
	//获取驱动地址，默认为mysql
	public static String getDriver(){
		return ProperUtil.gets(properties, ConfigConstant.DRIVER, "com.mysql.jdbc.Driver");
	}
	//获取URL
	public static String getURL(){
		return ProperUtil.gets(properties, ConfigConstant.URL);
	}
	//获取数据库用户名
	public static String getUserName(){
		return ProperUtil.gets(properties, ConfigConstant.USERNAME);
	}
	//获取密码
	public static String getPassWord(){
		return ProperUtil.gets(properties, ConfigConstant.PASSWORD);
	}
	//获取基础包路径
	public static String getBasePackage(){
		return ProperUtil.gets(properties, ConfigConstant.BASEPACKAGE);
	}
	//获取视图路径,默认是/WEB-INF/view/
	public static String getViewPath(){
		return ProperUtil.gets(properties, ConfigConstant.VIEWPATH,"/WEB-INF/view/");
	}
	//获取静态文件路径,默认/asset/
	public static String getAssetPath(){
		return ProperUtil.gets(properties, ConfigConstant.ASSETPATH,"/asset/");
	}
}
