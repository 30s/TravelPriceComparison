package com.mobin.action;

import com.mobin.serviceDao.TravelServiceDao;
import com.mobin.serviceDao.impl.TravelServiceDaoImpl;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private String user;
	private String password;
	private TravelServiceDao service;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() throws Exception {
		
		if(service.login(user, password)){
			return SUCCESS;
		}else {
			addFieldError("user", "用户名或密码不正确!");
			return ERROR;
		}
		
	}

	
	public String showTop(){
		return "showTop";
	}
	
	public String showLeft(){
		return "showLeft";
	}
	
	public String showRight(){
		return "showRight";
	}
	
	public String oneKeyGetData(){
		return "oneKeyGetData";
	}
	
	public String oneKeyUpdate(){
		return "oneKeyUpdate";
	}

	public TravelServiceDao getService() {
		return service;
	}

	public void setService(TravelServiceDao service) {
		this.service = service;
	}
}
