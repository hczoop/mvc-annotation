package com.hczoop.example.entity;

import com.hczoop.mvc.action.ActionForm;

public class Student extends ActionForm{
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "student [username=" + username + ", password=" + password + "]";
	}
	
	
}
