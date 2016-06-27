package com.hczoop.example.action;

import com.hczoop.example.entity.User;
import com.hczoop.mvc.action.Action;
import com.hczoop.mvc.action.ActionForm;
import com.hczoop.mvc.annotation.Controller;
import com.hczoop.mvc.annotation.Resource;

@Controller("/user")
public class UserAction implements Action{

	@Resource(value = "/login.action",actionfrom="com.hczoop.example.entity.User")
	public String execute(ActionForm form) {
		User user = (User)form;
		System.out.println(user.toString());
		if(null != user && user.getName().equals("hczoop")){
			return "/view/success.jsp";
		}else{
			return "/view/fail.jsp";
		}
	}

}
