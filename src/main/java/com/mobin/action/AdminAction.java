package com.mobin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mobin.dao.HBaseDao;
import com.mobin.domain.DFSFile;
import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import com.mobin.serviceDao.impl.TravelServiceDaoImpl;
import org.apache.avro.generic.GenericData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
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
	private List<DFSFile> fileslist;

	public List<DFSFile> getFileslist() {
		return fileslist;
	}

	public void setFileslist(List<DFSFile> fileslist) {
		this.fileslist = fileslist;
	}

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

	/*public String showData(){
		String num=null;
		String SP;

		num=ServletActionContext.getRequest().getParameter("num");
		SP =ServletActionContext.getRequest().getParameter("SP");

		page= service.findPage(num,SP);
		page.setUri("showDataAction");
		ServletActionContext.getRequest().setAttribute("page", page);

		return "showdata";
	}*/


	//遍历HDFS下的文件
	public String dfsfile() throws IOException {
		fileslist = new ArrayList<DFSFile>();
		Configuration configuration = new Configuration();
		Path dst = new Path("hdfs://master:9000/");
		FileSystem fs = dst.getFileSystem(configuration);
		//FileSystem fs=FileSystem.get(configuration);   此方法必须在项目下的bin目录下添加hadoop的hdfs/core-site.xml文件

		RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("hdfs://master:9000/dir"), true);

		while (files.hasNext()) {
			DFSFile f = new DFSFile();
			LocatedFileStatus file = files.next();
			System.out.println(file.getPath());
			f.setPermission(file.getPermission().toString());
			f.setOwner(file.getOwner());
			f.setSize(file.getLen());
			f.setName(file.getPath().getName());
			fileslist.add(f);
		}
		return  "dfsfile";
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


	public TravelServiceDao getService() {
		return service;
	}
	public void setService(TravelServiceDao service) {
		this.service = service;
	}
}

