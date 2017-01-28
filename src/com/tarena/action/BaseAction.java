package com.tarena.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class BaseAction implements SessionAware {

	protected Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}
	
	public String execute() {
		System.out.println("BaseAction...");
		return "success";
	}

}
