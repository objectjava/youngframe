package net.youngza.clzss;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.youngza.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类操作工具
 * @author bj_yangsong
 */
public class ClassUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(ClassUtil.class);
	
	//获取类加载器
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
	
	//加载一个类,是否初始化，这里初始化指的是否运行静态方法块,为了提高加在类的性能可以设置为false
	public static Class<?> loadClass(String className,boolean isInitialized){
		Class<?> clzss;
		try {
			clzss=Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			LOGGER.error("load class failure!",e);
			throw new RuntimeException(e);
		}
		return clzss;
	}
	
	//获取指定包名下所有类，或者jar包下所有类
	public static Set<Class<?>> getClassSet(String packageName){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		try {
			Enumeration<URL> urls=getClassLoader().getResources(packageName.replaceAll(".", "/"));
			while(urls.hasMoreElements()){
				URL url=urls.nextElement();
				if(url!=null){
					String protocol=url.getProtocol();
					if(protocol.equals("file")){
						String packagePath=url.getPath().replaceAll("%20", " ");//把目录中的空格%20替换掉
						addClass(classSet, packagePath, packageName);
					}else if(protocol.equals("jar")){
						JarURLConnection jarURLConnection=(JarURLConnection)url.openConnection();
						if(jarURLConnection!=null){
							JarFile jarFile=jarURLConnection.getJarFile();
							if(jarFile!=null){
								Enumeration<JarEntry> jarEntries=jarFile.entries();
								while(jarEntries.hasMoreElements()){
									JarEntry jarEntry=jarEntries.nextElement();
									String jarEntryName=jarEntry.getName();
									if(jarEntryName.endsWith(".class")){
										String className=jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll(".", "/");
										doAddClass(classSet, className);
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classSet;
	}
	
	private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
		File[] files=new File(packagePath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isFile()&&file.getName().endsWith(".class")||file.isDirectory()) ; //只加载文件且是.class和目录
			}
		});
		for(File file:files){
			String fileName=file.getName();
			if(file.isFile()){
				String className=fileName.substring(0,fileName.lastIndexOf("."));
				if(StringUtil.isNotEmpty(packageName)){
					className=packageName+"."+className;
				}
				doAddClass(classSet,className);
			}else{
				String subPackagePath=fileName;
				if(StringUtil.isNotEmpty(packagePath)){
					subPackagePath=packagePath+"/"+subPackagePath;
				}
				String subPackageName=fileName;
				if(StringUtil.isNotEmpty(packageName)){
					subPackageName=packageName+"."+subPackageName;
				}
				addClass(classSet,subPackagePath,subPackageName); //迭代子包下的所有类加入到classSet
			}
		}
	} 
	private static void doAddClass(Set<Class<?>> classSet,String className){
		Class<?> clzss=loadClass(className,false);
		classSet.add(clzss);
	}
}
