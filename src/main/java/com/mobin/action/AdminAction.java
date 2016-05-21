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
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.web.context.ContextLoaderListener;

public class AdminAction extends ActionSupport {

	private List<Travel> travels;
	private TravelServiceDao service;
	private String SP;
	private Page page;
	private String place;
	private SpiderAction spider;
	private String type;
	private static List<DFSFile> fileslist;


	private String etlFile;
	private String generatehfile;
	private String hfile;
	private String htable;

	private Configuration hadoopConfiguration;


	//通过移动HFile文件到表目录下来达到导入数据的目的
	public String putDataToHBase(String HFile)  {
		try {
			HbaseTemplate template = ContextLoaderListener.getCurrentWebApplicationContext().getBean("htemplate",HbaseTemplate.class);
			Configuration configuration = template.getConfiguration();
			Connection connection = ConnectionFactory.createConnection(configuration);
			LoadIncrementalHFiles loder = new LoadIncrementalHFiles(configuration);
			loder.doBulkLoad(new Path("hdfs://master:9000/HFILE/"+hfile),new HTable(configuration,htable));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "putDataToHBase";
	}



	//遍历HDFS下/Spider的文件
	//Spider目录的文件为待爬虫的URL文件
	public String dfsfile() throws IOException {
		fileslist = new ArrayList<DFSFile>();
		Path dst = new Path("hdfs://master:9000/");
		FileSystem fs = dst.getFileSystem(hadoopConfiguration);
		getFiles(fs,new Path("/Spider"));   //遍历/Spider目录下的非_SUCCESS文件
		return  "dfsfile";
	}

	//遍历HDFS下/UnClean
	//UncClean为爬虫提取数据的存放位置
	public String unCleanFile() throws IOException {
		fileslist = new ArrayList<DFSFile>();
		Path dst = new Path("hdfs://master:9000/");
		FileSystem fs = dst.getFileSystem(hadoopConfiguration);
		getFiles(fs,new Path("/UnClean"));   //遍历/UnClean目录下的非_SUCCESS文件
		return  "uncleanfile";
	}

	//遍历HDFS下/HFILE
	public String hfileFile() throws IOException {
		fileslist = new ArrayList<DFSFile>();
		Path dst = new Path("hdfs://master:9000/");
		FileSystem fs = dst.getFileSystem(hadoopConfiguration);
		getFiles(fs,new Path("/HFILE"));
		return  "hfileFile";
	}

	public static void getFiles(FileSystem fs, Path folderPath) throws IOException {
		List<Path> paths = new ArrayList<Path>();
		if (fs.exists(folderPath)) {
			FileStatus[] fileStatus = fs.listStatus(folderPath);
			for (int i = 0; i < fileStatus.length; i++) {
				FileStatus fileType = fileStatus[i];
					DFSFile hfile = new DFSFile();
					hfile.setPermission(fileType.getPermission().toString());
					hfile.setOwner(fileType.getOwner());
					hfile.setSize(fileType.getLen());
					hfile.setName(fileType.getPath().getName());
					System.out.println(fileType.getPath().getName());
				    fileslist.add(hfile);
				}
			}
	}


	public String ETL() throws Exception {
		SpiderAction.cleanData(etlFile);
		SpiderAction.convertData(etlFile);
		SpiderAction.generateHFile(etlFile);//生成HFILE文件
		return  "etl";
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

	public Configuration getHadoopConfiguration() {
		return hadoopConfiguration;
	}

	public void setHadoopConfiguration(Configuration hadoopConfiguration) {
		this.hadoopConfiguration = hadoopConfiguration;
	}

	public String getEtlFile() {
		return etlFile;
	}

	public void setEtlFile(String etlFile) {
		this.etlFile = etlFile;
	}

	public String getGeneratehfile() {
		return generatehfile;
	}

	public void setGeneratehfile(String generatehfile) {
		this.generatehfile = generatehfile;
	}

	public String getHfile() {
		return hfile;
	}

	public void setHfile(String hfile) {
		this.hfile = hfile;
	}

	public String getHtable() {
		return htable;
	}

	public void setHtable(String htable) {
		this.htable = htable;
	}
}

