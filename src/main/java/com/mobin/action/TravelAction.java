package com.mobin.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.mobin.common.Page;
import com.mobin.dao.TravelDao;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;

import com.mobin.wordcount.PhoenixHBaseTest;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


public class TravelAction extends ActionSupport {


	private String ST;
	private String SP;
	private String EP;
	private List<Travel> travels;
	private TravelServiceDao travelServiceDao;

	public TravelServiceDao getTravelServiceDao() {
		return travelServiceDao;
	}

	public void setTravelServiceDao(TravelServiceDao travelServiceDao) {
		this.travelServiceDao = travelServiceDao;
	}

	public String execute() throws Exception {
		System.out.println(SP+EP+ST+"333333333");
		travels = travelServiceDao.query("澳门","黔东南","2015-12-15");
		ActionContext.getContext().put("travel",travels);
		return this.SUCCESS;
	}

	public String getST() {
		return ST;
	}

	public void setST(String ST) {
		this.ST = ST;
	}

	public String getSP() {
		return SP;
	}

	public void setSP(String SP) {
		this.SP = SP;
	}

	public String getEP() {
		return EP;
	}

	public void setEP(String EP) {
		this.EP = EP;
	}
}
