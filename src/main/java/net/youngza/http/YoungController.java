package net.youngza.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.youngza.InitLoader;
import net.youngza.bean.BeanHelper;
import net.youngza.conf.ConfigUtil;


public class YoungController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//初始化Helper类
		InitLoader.init();
		//获取ServletContext 用于注册servlet
		ServletContext servletContext=config.getServletContext();
		//注册jsp的servlet
		ServletRegistration jspServlet=servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigUtil.getViewPath()+"*"); //注册目录下所有的jsp文件
		//注册处理静态资源文件默认servlet
		ServletRegistration defaultServlet=servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigUtil.getAssetPath()+"*");
	}

	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取请求方法与请求路径
		String requestMethod=request.getMethod();
		//获取请求路径
		String requestPath=request.getPathInfo();
		
		//获取Action处理器
		Handler handler=ControllerHelper.getHandler(requestMethod, requestPath);
		if(handler!=null){
			//获取controller，及bean实例
			Class<?> controllerClass=handler.getControllerClass();
			Object controllerBean=BeanHelper.getBeanInstance(controllerClass);
			//创建请求参数对象
			Map<String, Object> paramMap=new HashMap<String, Object>();
			
		}
		
		
		
	}
}
