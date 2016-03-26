package com.mobin.action;

import java.io.IOException;
import java.util.List;

import com.mobin.dao.HBaseDao;
import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import com.mobin.serviceDao.impl.TravelServiceDaoImpl;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
public class AdminAction extends ActionSupport {

	private List<Travel> travels;
	private TravelServiceDao service;
	private String SP;
	private Page page;
	private String place;
	private SpiderAction spider;
	private String type;
	private HBaseDao hBaseDao;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public SpiderAction getSpider() {
		return spider;
	}

	public void setSpider(SpiderAction spider) {
		this.spider = spider;
	}

	public HBaseDao gethBaseDao() {
		return hBaseDao;
	}

	public void sethBaseDao(HBaseDao hBaseDao) {
		this.hBaseDao = hBaseDao;
	}

	//一键更新数据
	public String oneKeyUpdate(){
			System.out.println("99999999999999999999999999999999999999");
			String place=ServletActionContext.getRequest().getParameter("place");
		    spider.putDataToHBase(place);
		return SUCCESS;

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

