package com.mobin.test;

import com.mobin.action.AdminAction;
import com.mobin.action.QuartzAction;
import com.mobin.action.SpiderAction;
import com.mobin.dao.impl.TravelDaoImpl;
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
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.web.context.ContextLoaderListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Ptest {


    @Test
    public void dao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HbaseTemplate template = context.getBean("htemplate", HbaseTemplate.class);
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

    }

    @Test
    public void datetest() {

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
    public void createtable() {
        /*ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CreateTable ct = (CreateTable) context.getBean("createtable");
		ct.createView("2016-02-01","澳门","龙虎山");*/
        System.out.println(new Random().nextInt());
    }

    @Test
    public void action() throws InterruptedException, IOException, ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //AdminAction adminAction = context.getBean("adminAction",AdminAction.class);
        //adminAction.oneKeyGet();
        SpiderAction spiderAction = context.getBean("spiderAction", SpiderAction.class);
        spiderAction.cartesian("安庆");
    }


    @Test
    public void downURLaction() throws InterruptedException, IOException, ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //AdminAction adminAction = context.getBean("adminAction",AdminAction.class);
        //adminAction.oneKeyGet();
        SpiderAction spiderAction = context.getBean("spiderAction", SpiderAction.class);
         spiderAction.downURLByPlace("South");
       // spiderAction.downURLFromTuniu("tuniu");
    }




    @Test
    public void extractionaction() throws InterruptedException, IOException, ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //AdminAction adminAction = context.getBean("adminAction",AdminAction.class);
        //adminAction.oneKeyGet();DATA-TH1-page6
        SpiderAction spiderAction = context.getBean("spiderAction", SpiderAction.class);
        spiderAction.extractionData("澳门URL");
    }


    @Test
    public void shuffleaction() throws InterruptedException, IOException, ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //AdminAction adminAction = context.getBean("adminAction",AdminAction.class);
        //adminAction.oneKeyGet();
        SpiderAction spiderAction = context.getBean("spiderAction", SpiderAction.class);
       // spiderAction.cleanData("澳门URL2016-05-21");
        spiderAction.convertData("澳门URL2016-05-212016-05-21");
    }

    @Test
    public void sortCostPer() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        TravelServiceDao travelDao = (TravelServiceDao) context.getBean("travelService");//"2000","3000"
        Page page = travelDao.findPage("1", "2016-05-24", "aomen", "hangzhou","6","2","ASC","2000","3000","5");
        System.out.println(page.getRecords().get(0).getHOTEL());
        System.out.println(page.getRecords().size());
    }

//    @Test
//    public void Quartz() {
//        QuartzAction quartzAction = new QuartzAction();
//        quartzAction.setPlace("澳门");
//        try {
//            quartzAction.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void string() {
//        String s = "mobin";
//        System.out.println(s.toCharArray());
        System.out.println(ContextLoaderListener.
                getCurrentWebApplicationContext().
                getBean("travelService"));
    }

}
