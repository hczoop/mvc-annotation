package com.hczoop.mvc.entity;

import java.util.HashMap;
import java.util.Map;

import com.hczoop.mvc.action.Action;

public class ActionBean {
	
	private Action action;
	private String actionForm;
	
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public String getActionForm() {
		return actionForm;
	}
	public void setActionForm(String actionForm) {
		this.actionForm = actionForm;
	}
	@Override
	public String toString() {
		return "ActionBean [action=" + action + ", actionForm=" + actionForm
				+ "]";
	}
	
	
	
}
