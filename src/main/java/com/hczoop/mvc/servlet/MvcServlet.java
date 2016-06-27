package com.hczoop.mvc.servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hczoop.mvc.action.Action;
import com.hczoop.mvc.action.ActionForm;
import com.hczoop.mvc.entity.ActionBean;

public class MvcServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String path = request.getServletPath();//获取请求路径
		System.out.println(path);
		Map<String, ActionBean> map = (Map<String, ActionBean>) request.getServletContext().getAttribute("mvc");
		ActionBean actionBean = map.get(path);
		ActionForm actionForm = getBean(actionBean.getActionForm(), request);
		try {
			Action action = actionBean.getAction();
			String url = action.execute(actionForm);
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private ActionForm getBean(String className,HttpServletRequest request) {
		ActionForm actionForm =  null;
		try {
			Class<?> clazz = Class.forName(className);
			actionForm = (ActionForm)clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				field.setAccessible(true); //修改访问权限
				field.set(actionForm, request.getParameter(field.getName()));
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForm;
	}
	
}
