package com.hczoop.example.action;

import com.hczoop.example.entity.Student;
import com.hczoop.mvc.action.Action;
import com.hczoop.mvc.action.ActionForm;
import com.hczoop.mvc.annotation.Controller;
import com.hczoop.mvc.annotation.Resource;

@Controller("/stu")
public class StudentAction implements Action{

	@Resource(value = "/test.action",actionfrom="com.hczoop.example.entity.Student")
	public String execute(ActionForm form) {
		Student student = (Student)form;
		System.out.println(student);
		return "/view/fail.jsp";
	}

}
