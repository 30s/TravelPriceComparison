package com.mobin.test;

import com.mobin.util.CreateTable;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mobin.dao.TravelDao;
import com.mobin.wordcount.PhoenixHBaseTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	    TravelDao travelDao = (TravelDao) context.getBean("travelDao");
	 //   travelDao.query("澳门","黔东南","2015-12-15");
	}

	@Test
	public void datetest(){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = null;
		try {
			date = sdf.format(new SimpleDateFormat("yy年MM月dd日").parse("2016年11月02日"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(date);

	}

	@Test
	public void createtable(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		CreateTable ct = (CreateTable) context.getBean("createtable");
		ct.createView("2016-02-01","澳门","龙虎山");
	}

}
