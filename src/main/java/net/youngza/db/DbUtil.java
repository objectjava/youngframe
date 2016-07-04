package net.youngza.db;

import java.sql.Connection;
import java.sql.SQLException;

import net.youngza.conf.ConfigUtil;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 使用dbcp数据库连接池数据库连接工具
 */
public class DbUtil {
	private static final String DRIVER=ConfigUtil.getDriver();
	private static final String URL=ConfigUtil.getURL();
	private static final String USERNAME=ConfigUtil.getUserName();
	private static final String PASSWORD=ConfigUtil.getPassWord();
	private static final Logger LOGGER=LoggerFactory.getLogger(DbUtil.class);
	//使用ThreadLoacl进行线程隔离，使connection在多线程情况下线程安全,还可以避免参数的传递（*后面是功能重点）
	private static final ThreadLocal<Connection> CONNECTION_HOLDER=new ThreadLocal<Connection>();
	//使用dbcp连接池对数据库连接做出优化
	private static final BasicDataSource DATASOURCE=new BasicDataSource();
	
	static{
		try {
			Class.forName(DRIVER);//加载jdbc驱动
		} catch (ClassNotFoundException e) {
			LOGGER.error("can not load jdbc driver!",e);
		}
		DATASOURCE.setDriverClassName(DRIVER);
		DATASOURCE.setUrl(URL);
		DATASOURCE.setUsername(USERNAME);
		DATASOURCE.setPassword(PASSWORD);
	}
	
	//获取jdbc连接,不能用单例，只能用线程池
	public static Connection getConnection(){
		Connection conn=CONNECTION_HOLDER.get();
		if(conn==null){
			try {
				conn=DATASOURCE.getConnection();
			} catch (SQLException e) {
				LOGGER.error("get jdbc connection failure!",e);
			}finally{
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}
	//关闭连接
	public static void close(){
		Connection conn=CONNECTION_HOLDER.get();
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("jdbc connection colse failure!",e);
			}finally{
				CONNECTION_HOLDER.remove();//删除
			}
		}
	}
}
