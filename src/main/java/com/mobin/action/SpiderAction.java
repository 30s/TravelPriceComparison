package com.mobin.action;

import com.mobin.putDataToHBase.PutDataToHBaseDefault;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
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
        spiderAction.extractionData(place);
        spiderAction.cleanData(place);
        spiderAction.convertData(place);
    }




    private JobRunner downURLjobRunner;
    private JobRunner extractionDatajobRunner;
    private JobRunner cartesianjobRunner;
    private JobRunner convertDataJobRunner;
    private JobRunner cleanDataJobRunner;
    private Job cleanDataJob;
    private Job convertDataJob;
    private Job cartesianMRJob;
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
            FileOutputFormat.setOutputPath(downURLSpider,new Path("/"+place+"URL"));
            downURLjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cartesian(String place){
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            cartesianMRJob = context.getBean("cartesianMRJob",Job.class);
            cartesianjobRunner = context.getBean("cartesianjobRunner",JobRunner.class);
            cartesianMRJob.getConfiguration().set("mapred.jar","/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("extraction.................................");
            FileInputFormat.setInputPaths(cartesianMRJob,"/url1.txt");
            FileOutputFormat.setOutputPath(cartesianMRJob,new Path("/"+place));
            cartesianjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //cartesianMRJob

    public void extractionData(String place){
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
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

    public void cleanData(String place){
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            cleanDataJob = context.getBean("cleanDataJob",Job.class);
            cleanDataJobRunner = context.getBean("cleanDataJobRunner",JobRunner.class);
            cleanDataJob.getConfiguration().set("mapred.jar","/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("CLEAN.................................");
            FileInputFormat.setInputPaths(cleanDataJob,"/"+place+"DATA");
            FileOutputFormat.setOutputPath(cleanDataJob,new Path("/"+place+"INFO"));
            cleanDataJobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void putDataToHBase(String place){
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            HbaseTemplate template = context.getBean("htemplate",HbaseTemplate.class);
            Configuration conf = HBaseConfiguration.create();
            Job job=Job.getInstance(conf);
            job.setJarByClass(SpiderAction.class);
            job.setMapperClass(PutDataToHBaseDefault.class);
            job.setNumReduceTasks(0);
            job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "MOBIN");
            job.setOutputKeyClass(ImmutableBytesWritable.class);
            job.setOutputValueClass(Put.class);
            FileInputFormat.setInputPaths(job,"hdfs://master:9000/"+place+"ConvertData/part-r-00000");
            job.setOutputFormatClass(TableOutputFormat.class);
            System.exit(job.waitForCompletion(true)?1:0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void convertData(String place){
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            convertDataJob = context.getBean("convertDataJob",Job.class);
            convertDataJobRunner = context.getBean("convertDataJobRunner",JobRunner.class);
            convertDataJob.getConfiguration().set("mapred.jar","/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("convertData.................................");
            FileInputFormat.setInputPaths(convertDataJob,"/"+place+"INFO");
            FileOutputFormat.setOutputPath(convertDataJob,new Path("/"+place+"ConvertData"));
            convertDataJobRunner.call();
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

    public Job getCartesianMRJob() {
        return cartesianMRJob;
    }

    public void setCartesianMRJob(Job cartesianMRJob) {
        this.cartesianMRJob = cartesianMRJob;
    }

    public JobRunner getCartesianjobRunner() {
        return cartesianjobRunner;
    }

    public void setCartesianjobRunner(JobRunner cartesianjobRunner) {
        this.cartesianjobRunner = cartesianjobRunner;
    }

    public JobRunner getConvertDataJobRunner() {
        return convertDataJobRunner;
    }

    public void setConvertDataJobRunner(JobRunner convertDataJobRunner) {
        this.convertDataJobRunner = convertDataJobRunner;
    }

    public Job getConvertDataJob() {
        return convertDataJob;
    }

    public void setConvertDataJob(Job convertDataJob) {
        this.convertDataJob = convertDataJob;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
