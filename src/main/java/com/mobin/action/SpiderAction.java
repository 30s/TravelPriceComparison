package com.mobin.action;

import com.mobin.putDataToHBase.PutDataToHBaseDefault;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
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
import org.springframework.web.context.ContextLoaderListener;

import java.io.IOException;

/**
 * Created by hadoop on 3/8/16.
 */
public class SpiderAction implements org.quartz.Job {

    private String file;
    private JobRunner downURLjobRunner;
    private JobRunner tuniuURLJobRrunner;
    private JobRunner extractionDatajobRunner;
    private JobRunner cartesianjobRunner;
    private JobRunner convertDataJobRunner;
    private JobRunner cleanDataJobRunner;
    private Job cleanDataJob;
    private Job convertDataJob;
    private Job cartesianMRJob;
    private Job downURLSpider;
    private Job tuniuURLJob;
    private Job extractionDataSpider;


    ApplicationContext context = null;


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println("The key is  " + key + "  ,and name is  " + file);
        SpiderAction spiderAction = new SpiderAction();
        spiderAction.extractionData(file);//抽取数据
        spiderAction.cleanData(file);     //清洗数据
        spiderAction.convertData(file);   //数据转换
    }





    public void downURLByPlace(String file) {
        try {
            //TODO 后期要改，现在只能用这个蠢办法了
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            downURLSpider = ContextLoaderListener.getCurrentWebApplicationContext().getBean("downURLSpiderJob", Job.class);
            downURLjobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("downURLjobRunner", JobRunner.class);
            downURLSpider.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("downURL.................................");
            FileInputFormat.setInputPaths(downURLSpider, "/" + file);
            FileOutputFormat.setOutputPath(downURLSpider, new Path("/" + file + "URL"));
            downURLjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void downURLFromTuniu(String file){
        try {
            //TODO 后期要改，现在只能用这个蠢办法了
           // context = new ClassPathXmlApplicationContext("applicationContext.xml");
            tuniuURLJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("tuniuURLJob", Job.class);
            tuniuURLJobRrunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("tuniuURLJobRrunner", JobRunner.class);
            tuniuURLJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("tuniu.................................");
            FileInputFormat.setInputPaths(tuniuURLJob, "/" + file);
            FileOutputFormat.setOutputPath(tuniuURLJob, new Path("/" + file + "URL"));
            tuniuURLJobRrunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cartesian(String file) {
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            cartesianMRJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cartesianMRJob", Job.class);
            cartesianjobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cartesianjobRunner", JobRunner.class);
            cartesianMRJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("extraction.................................");
            FileInputFormat.setInputPaths(cartesianMRJob, "/url1.txt");
            FileOutputFormat.setOutputPath(cartesianMRJob, new Path("/" + file));
            cartesianjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //cartesianMRJob

    public void extractionData(String file) {
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            extractionDataSpider = ContextLoaderListener.getCurrentWebApplicationContext().getBean("extractionDataSpiderJob", Job.class);
            extractionDatajobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("extractionDatajobRunner", JobRunner.class);
            extractionDataSpider.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("extraction.................................");
            FileInputFormat.setInputPaths(extractionDataSpider, "/" + file + "URL");
            FileOutputFormat.setOutputPath(extractionDataSpider, new Path("/" + file + "DATA"));
            extractionDatajobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cleanData(String file) {
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            cleanDataJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cleanDataJob", Job.class);
            cleanDataJobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cleanDataJobRunner", JobRunner.class);
            cleanDataJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("CLEAN.................................");
            FileInputFormat.setInputPaths(cleanDataJob, "/" + file + "DATA");
            FileOutputFormat.setOutputPath(cleanDataJob, new Path("/" + file + "CLEANEDDATA"));
            cleanDataJobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void putDataToHBase(String HFile)  {
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            HbaseTemplate template = ContextLoaderListener.getCurrentWebApplicationContext().getBean("htemplate",HbaseTemplate.class);
            Configuration configuration = template.getConfiguration();
            Connection connection = ConnectionFactory.createConnection(configuration);
            LoadIncrementalHFiles loder = new LoadIncrementalHFiles(configuration);
            loder.doBulkLoad(new Path("hdfs://master:9000/"+HFile),new HTable(configuration,"TRAVELS"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void convertData(String file) {
        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            convertDataJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("convertDataJob", Job.class);
            convertDataJobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("convertDataJobRunner", JobRunner.class);
            convertDataJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
            System.out.println("convertData.................................");
            FileInputFormat.setInputPaths(convertDataJob, "/" + file + "CLEANEDDATA");
            FileOutputFormat.setOutputPath(convertDataJob, new Path("/" + file + "INFO"));  //INFO为最终要导入到HBase的数据
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
