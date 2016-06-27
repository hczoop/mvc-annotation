package com.hczoop.example.entity;

import com.hczoop.mvc.action.ActionForm;

public class User extends ActionForm{
	private String name;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", address=" + address + "]";
	}
	
	
}
