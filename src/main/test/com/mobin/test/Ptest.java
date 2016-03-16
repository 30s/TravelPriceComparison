package com.mobin.test;

import com.mobin.action.AdminAction;
import com.mobin.action.QuartzAction;
import com.mobin.action.SpiderAction;
import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import com.mobin.util.CreateTable;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mobin.dao.TravelDao;
import com.mobin.wordcount.PhoenixHBaseTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Ptest {
	@Test
	public void ttest(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextg.xml");
		PhoenixHBaseTest phoenixHBaseTest = (PhoenixHBaseTest) context.getBean("phoenixTest");
		phoenixHBaseTest.query();
	}
	
	@Test
	public void dao(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		HbaseTemplate template = context.getBean("htemplate",HbaseTemplate.class);
		Connection connection = null;
		try {
			connection = ConnectionFactory.createConnection(template.getConfiguration());
			Admin admin = connection.getAdmin();
			admin.disableTables(Pattern.compile("MOBIN*"));
			//admin.truncateTable(TableName.valueOf("VIEWTEST"),false);
			Table table = connection.getTable(TableName.valueOf("VIEWTEST"));
			//System.out.println(table);
		} catch (IOException e) {
			e.printStackTrace();
		}




		//TravelDao travelDao = (TravelDao) context.getBean("travelDao");
	 //   travelDao.query("澳门","黔东南","2015-12-15");
	}

	@Test
	public void datetest(){

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String date = null;
		try {
			date = sdf.format(sdf.parse("11:02"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date);

	}

	@Test
	public void createtable(){
		/*ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CreateTable ct = (CreateTable) context.getBean("createtable");
		ct.createView("2016-02-01","澳门","龙虎山");*/
		System.out.println(new Random().nextInt());
	}

	@Test
	public void action() throws InterruptedException, IOException, ClassNotFoundException {
		//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//AdminAction adminAction = context.getBean("adminAction",AdminAction.class);
		//adminAction.oneKeyGet();
		SpiderAction spiderAction = new SpiderAction();
		spiderAction.downURLByPlace("澳门");
	}

	@Test
	public void sortByPrice(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TravelServiceDao travelDao = (TravelServiceDao) context.getBean("travelService");
		Page page = travelDao.sortByPrice("ASC","2","2016-03-09","澳门","深圳");
	    System.out.println(page.getRecords().get(0).getTOTALPRICE());
	}

	@Test
	public void sortByHotel(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TravelServiceDao travelDao = (TravelServiceDao) context.getBean("travelService");
		Page page = travelDao.sortByHotel(3,"2","2016-03-09","澳门","西宁");
		System.out.println(page.getRecords().get(0).getTOTALPRICE());
	}

	@Test
	public void Quartz(){
		QuartzAction quartzAction = new QuartzAction();
		quartzAction.setPlace("澳门");
		try {
			quartzAction.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
