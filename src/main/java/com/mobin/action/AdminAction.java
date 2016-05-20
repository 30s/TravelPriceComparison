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
import org.apache.hadoop.fs.*;
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
	private static List<DFSFile> fileslist;

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
		Path dst = new Path("hdfs://master:9000/Spider");
		FileSystem fs = dst.getFileSystem(configuration);
		getFiles(fs,new Path("/Spider"));   //遍历/Spider目录下的非_SUCCESS文件
		return  "dfsfile";
	}

	public static void getFiles(FileSystem fs, Path folderPath) throws IOException {
		List<Path> paths = new ArrayList<Path>();
		if (fs.exists(folderPath)) {
			FileStatus[] fileStatus = fs.listStatus(folderPath);
			for (int i = 0; i < fileStatus.length; i++) {
				FileStatus fileType = fileStatus[i];
				if (fileType.isDirectory()) {
					getFiles(fs,fileType.getPath());
				}
				else{

					String fileName = fileType.getPath().getName();
					if(!fileName.equals("_SUCCESS")){
						DFSFile f = new DFSFile();
						f.setPermission(fileType.getPermission().toString());
						f.setOwner(fileType.getOwner());
						f.setSize(fileType.getLen());
						f.setName(fileType.getPath().getName());
						System.out.println(fileType.getPath().getName());
						fileslist.add(f);
					}
				}

			}
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


	public TravelServiceDao getService() {
		return service;
	}
	public void setService(TravelServiceDao service) {
		this.service = service;
	}
}

