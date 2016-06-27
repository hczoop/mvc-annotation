package com.hczoop.mvc.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.hczoop.mvc.action.Action;
import com.hczoop.mvc.action.ActionForm;
import com.hczoop.mvc.annotation.Controller;
import com.hczoop.mvc.annotation.Resource;
import com.hczoop.mvc.entity.ActionBean;

/**
 * xml 解析
 * 由xml配置改写成annotation方式,解析扫描包下action的配置
 *
 */
public class XmlTools {
	
	public static Map<String, ActionBean> parseXml(String path) {
		System.out.println("parsing xml...");
		Map<String, ActionBean> resultMap = new HashMap<String, ActionBean>();
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File(path));
			//获取根元素
			Element rootElement = doc.getRootElement();
			//获取扫描路径
			Element scan = rootElement.element("component-scan");
			String scanPackage = scan.attribute("base-package").getValue();
		
			String classpath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			//String classpath = XmlTools.class.getClass().getResource("/").getPath();
			
			//获得所有扫描路径
			String[] scanPaths = scanPackage.split(",");
			String packagePath =null;
			File file;
			List<Class> classes = new ArrayList<Class>();
			for(String scanPath : scanPaths) {
				packagePath = classpath + scanPath.replace(".", "/");
				file = new File(packagePath);
				System.out.println("scanning package: " + packagePath);
				iterateFile(file, classes);
			}

			//遍历每一个Class，获取注解
			Controller controller = null;
			Resource resource = null;
			String classValue = null;
			String methodValue = null;
			String actionFormPath = null;
			for(Class c : classes) {
				ActionBean actionBean = new ActionBean();
				//获取class上的注解值
				if(c.isAnnotationPresent(Controller.class)) {
					controller = (Controller) c.getAnnotation(Controller.class);
					classValue = controller.value();
				}
				//获取method上的注解值
				Method method = c.getMethod("execute", new Class[]{ActionForm.class});
				if(method.isAnnotationPresent(Resource.class)) {
					resource = method.getAnnotation(Resource.class);
					methodValue = resource.value();
					actionFormPath = resource.actionfrom();
				}
				actionBean.setAction((Action) c.newInstance());
				actionBean.setActionForm(actionFormPath);
				System.out.println("xmlParser:"+classValue+methodValue);
				resultMap.put(classValue + methodValue, actionBean);
			}
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return resultMap;
	}

	private static void iterateFile(File file, List<Class> classes) {
		String filePath;
		Class clazz = null;
		try{
			if(file.isDirectory()) {
				File[] files = file.listFiles();
				for(File f : files){
					iterateFile(f, classes);
				}
			} else {
				//E:\workSpace\mvc-annotation\src\main\webapp\WEB-INF\classes\com\hczoop\example\action\UserAction.class
				filePath = file.getAbsolutePath();
				filePath = filePath.replace("\\", ".").replace("/", ".");
				filePath = filePath.split(".classes.")[1].split(".class")[0];
				clazz = Class.forName(filePath);
				System.out.println("file path: " + filePath);
				classes.add(clazz);
			}
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取类路径
	 * @return
	 */
	private static String getResourcePath() {
        String className = XmlTools.class.getName();
        String classNamePath = className.replace(".", "/") + ".class";
        URL is = XmlTools.class.getClassLoader().getResource(classNamePath);
        String path = is.getFile();
        path = StringUtils.replace(path, "%20", " ");
 
        return StringUtils.removeStart(path, "/");
    }

	public static void main(String[] args) {
		String path="E:/workSpace/mvc-annotation/src/main/webapp/WEB-INF/MVC.xml";
		Map<String, ActionBean> map = XmlTools.parseXml(path);
		Set<String> set = map.keySet();
		for (String key : set) {
			System.out.println("===="+(ActionBean)map.get(key));
		}
	}

}
