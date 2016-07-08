package net.youngza.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
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
import net.youngza.reflection.ReflectionUtil;
import net.youngza.util.ArrayUtil;
import net.youngza.util.CodeUtil;
import net.youngza.util.CollectionUtil;
import net.youngza.util.JsonUtil;
import net.youngza.util.StreamUtil;
import net.youngza.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author bj_yangsong
 * 前端控制器拦截所有
 */

public class YoungController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	//servlet2.5可能不支持
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
			Enumeration<String> paramName=request.getParameterNames();
			while(paramName.hasMoreElements()){
				String key=paramName.nextElement();
				String value=request.getParameter(key);
				paramMap.put(key, value);
			}
			String body=CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
			if(StringUtil.isNotEmpty(body)){
				String[] params=StringUtils.split(body,"&");
				if(ArrayUtil.isNotEmpty(params)){
					for(String param : params){
						String[] array=param.split("=");
						if(ArrayUtil.isNotEmpty(array)&&array.length==2){
							paramMap.put(array[0], array[1]);
						}
					}	
				}
			}
			Param param=new Param(paramMap);
			Method method=handler.getActionMethod();
			//利用反射调用方法
			Object result=ReflectionUtil.invokeMethod(controllerBean, method, param);
			//判断action执行之后返回类型,视图，data
			if(result instanceof View){
				View view=(View)result;
				String path=view.getPath();
				if(path.startsWith("/")){
					response.sendRedirect(path);//url跳转
				}
				
				if(StringUtil.isNotEmpty(path)){
					String viewPath=ConfigUtil.getViewPath();
					if(viewPath.lastIndexOf("/")!=-1){
						path=viewPath+path;
					}else{
						path=viewPath+"/"+path;
					}
				}
				Map<String,Object> model=view.getModel();
				if(CollectionUtil.isNotEmpty(model)){
					for(Map.Entry<String, Object> entry:model.entrySet()){
						request.setAttribute(entry.getKey(), entry.getValue());
					}
				}
				request.getRequestDispatcher(path).forward(request, response);
			}else if(result instanceof Data){
				Data data=(Data) result;
				Object model=data.getModel();
				if(model!=null){
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					String json=JsonUtil.toJson(model);
					PrintWriter out=response.getWriter();
					out.write(json);
					out.flush();
					out.close();
				}
			}
		}
	}
}
