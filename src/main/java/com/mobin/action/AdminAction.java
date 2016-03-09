package com.mobin.action;

import java.util.List;

import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import com.mobin.serviceDao.impl.TravelServiceDaoImpl;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
public class AdminAction extends ActionSupport {

	private List<Travel> travels;
	private TravelServiceDao service;
	private String SP;
	private Page page;
	private String place;

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Travel> getTravels() {
		return travels;
	}

	public void setTravels(List<Travel> travels) {
		this.travels = travels;
	}

	public String getSP() {
		return SP;
	}

	public void setSP(String sP) {
		SP = sP;
	}



	//一键爬取数据
	public String oneKeyGet(){

		String type=ServletActionContext.getRequest().getParameter("type");

		if (type.equals("all")){

			//在这里添加爬虫函数();

		}else if (type.equals("single")){
			String place=ServletActionContext.getRequest().getParameter("place");

			//在这里添加爬虫函数();
		}
		return SUCCESS;
	}

	//一键更新数据
	public String oneKeyUpdate(){
		String type=ServletActionContext.getRequest().getParameter("type");

		if (type.equals("all")){
			//一键更新();
		}else if (type.equals("single")){
			String place=ServletActionContext.getRequest().getParameter("place");
			SpiderAction.execute(place);

		}

		return SUCCESS;

	}

	//一键删除数据
	public String oneKeyDelete(){

		//判断管理员是否选删除所有 并且 删除所有成功
		if (place.equals("all") && service.deleteRecordsAll()){
			return "delete";
		}else {
			if(service.deleteRecordsByPlace(place)==true) return "delete";
		}
		return "delete";
	}

	public String showData(){
		String num=null;
		String SP;

		num=ServletActionContext.getRequest().getParameter("num");
		SP =ServletActionContext.getRequest().getParameter("SP");

		page= service.findPage(num,SP);
		page.setUri("showDataAction");
		ServletActionContext.getRequest().setAttribute("page", page);

		return "showdata";
	}

	public TravelServiceDao getService() {
		return service;
	}

	public void setService(TravelServiceDao service) {
		this.service = service;
	}
}

