package com.mobin.action;

import com.opensymphony.xwork2.ActionSupport;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 * Created by hadoop on 3/14/16.
 */
public class QuartzAction extends ActionSupport{
    private String place;
    private String startTime;   //"0 0 0/12 ? * MON,WED,FRI"
    private int repeateCount;


    public String execute() throws Exception {
        System.out.println(222);
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        JobDetail jobDetail = newJob(SpiderAction.class)
                .withIdentity("spiderJob","groupSpider")
                .usingJobData("place","澳门")
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("cronTrigger", "groupSpider")
                .withSchedule(dailyAtHourAndMinute(10,23))//two
                .forJob("spiderJob","groupSpider")
                .build();

        scheduler.scheduleJob(jobDetail,trigger);
        return this.SUCCESS;
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
}
