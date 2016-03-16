package com.mobin.action;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;

import java.io.IOException;

/**
 * Created by hadoop on 3/8/16.
 */
public class SpiderAction implements org.quartz.Job{

    private String place;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println("The key is  "+key+"  ,and name is  "+place);
        SpiderAction spiderAction = new SpiderAction();
        spiderAction.downURLByPlace(dataMap.getString("place"));
        spiderAction.extractionData(place);
    }


    private JobRunner downURLjobRunner;
    private JobRunner extractionDatajobRunner;
    private Job downURLSpider;
    private Job extractionDataSpider;
    ApplicationContext context = null;



    public  void  downURLByPlace(String place){
        try {
            //TODO 后期要改，现在只能用这个蠢办法了
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            downURLSpider = context.getBean("downURLSpiderJob",Job.class);
            downURLjobRunner = context.getBean("downURLjobRunner",JobRunner.class);
            downURLSpider.getConfiguration().set("mapred.jar","/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("downURL.................................");
            FileInputFormat.setInputPaths(downURLSpider,"/"+place);
            FileOutputFormat.setOutputPath(downURLSpider,new Path("/"+place+"URL7"));
            downURLjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractionData(String place){
        try {
            extractionDataSpider = context.getBean("extractionDataSpiderJob",Job.class);
            extractionDatajobRunner = context.getBean("extractionDatajobRunner",JobRunner.class);
            extractionDataSpider.getConfiguration().set("mapred.jar","/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("extraction.................................");
            FileInputFormat.setInputPaths(extractionDataSpider,"/"+place+"URL");
            FileOutputFormat.setOutputPath(extractionDataSpider,new Path("/"+place+"DATA"));
            extractionDatajobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public JobRunner getDownURLjobRunner() {
        return downURLjobRunner;
    }

    public void setDownURLjobRunner(JobRunner downURLjobRunner) {
        this.downURLjobRunner = downURLjobRunner;
    }

    public JobRunner getExtractionDatajobRunner() {
        return extractionDatajobRunner;
    }

    public void setExtractionDatajobRunner(JobRunner extractionDatajobRunner) {
        this.extractionDatajobRunner = extractionDatajobRunner;
    }

    public Job getDownURLSpider() {
        return downURLSpider;
    }

    public void setDownURLSpider(Job downURLSpider) {
        this.downURLSpider = downURLSpider;
    }

    public Job getExtractionDataSpider() {
        return extractionDataSpider;
    }

    public void setExtractionDataSpider(Job extractionDataSpider) {
        this.extractionDataSpider = extractionDataSpider;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
