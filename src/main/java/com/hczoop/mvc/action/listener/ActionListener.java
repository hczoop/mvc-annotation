package com.hczoop.mvc.action.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hczoop.mvc.entity.ActionBean;
import com.hczoop.mvc.util.XmlTools;

public class ActionListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		String xmlpath = context.getInitParameter("mvc-config");
		String tomcatpath =context.getRealPath("\\");
		System.out.println(tomcatpath+xmlpath);
		//通过扫描存放action包中类的注解得到配置文件
		Map<String, ActionBean> map = XmlTools.parseXml(tomcatpath+xmlpath); 
		System.out.println(map);
		//放入application作用
		context.setAttribute("mvc", map);
		System.out.println("提示：加载完成!");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO ServletContex销毁

	}

}
