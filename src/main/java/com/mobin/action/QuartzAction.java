package com.mobin.action;

import com.opensymphony.xwork2.ActionSupport;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 * Created by hadoop on 3/14/16.
 * 爬虫调度器
 */
public class QuartzAction extends ActionSupport{
    private String place;
    private String startTime;   //"0 0 0/12 ? * MON,WED,FRI"
    private int repeateCount;
    private static  int hour;
    private static  int min;
    private static  Date date = null;


    public String execute() throws Exception {
        System.out.println(222);
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        JobDetail jobDetail = newJob(SpiderAction.class)
                .withIdentity("spiderJob","groupSpider")
                .usingJobData("place",place)
                .build();

        System.out.println(repeateCount+"55555");
        Trigger trigger = newTrigger()
                .withIdentity("simpleTigger","groupSpider")
                .startAt(QuartzAction.coventDate())
                .withSchedule(simpleSchedule().withIntervalInHours(6).withRepeatCount(repeateCount))
                .forJob(jobDetail)
                .build();


        scheduler.scheduleJob(jobDetail,trigger);
        return this.SUCCESS;
    }

    public static  Date coventDate(){

        try {
            date = new SimpleDateFormat("HH:mm").parse(String.valueOf(hour)+":"+String.valueOf(min));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getRepeateCount() {
        return repeateCount;
    }

    public void setRepeateCount(int repeateCount) {
        this.repeateCount = repeateCount;
    }

    public static int getHour() {
        return hour;
    }

    public static void setHour(int hour) {
        QuartzAction.hour = hour;
    }

    public static int getMin() {
        return min;
    }

    public static void setMin(int min) {
        QuartzAction.min = min;
    }
}
