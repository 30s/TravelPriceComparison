package com.mobin.action;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hadoop on 3/8/16.
 */
public class SpiderAction implements org.quartz.Job {
    private String file;

    private static Job cleanDataJob;
    private static Job convertDataJob;
    private Job cartesianMRJob;
    private Job downURLSpiderJob;
    private Job tuniuURLJob;
    private Job extractionDataSpiderJob;
    private static Job geneateHFileJob;

    private static JobRunner cleanDataJobRunner;
    private static JobRunner convertDataJobRunner;
    private JobRunner cartesianjobRunner;
    private JobRunner downURLjobRunner;
    private JobRunner tuniuURLJobRrunner;
    private JobRunner extractionDatajobRunner;
    private static  JobRunner generateHFilejobRunner;

    private static  HbaseTemplate htemplate;

    private static Path extractionFilePath;
    private static Path cleanFilePath;
    private static Path converFilePath;
    private Configuration hadoopConfiguration;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String file = dataMap.getString("place");
        SpiderAction spiderAction = new SpiderAction();
        spiderAction.extractionData(file);//抽取数据
    }

    /*  以下函数单元测试使用该方式来获取Bean实例  如：
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            extractionDataSpiderJob = context.getBean("extractionDataSpiderJob", Job.class);
            extractionDatajobRunner = context.getBean("extractionDatajobRunner", JobRunner.class);*/

    public void downURLByPlace(String file) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            downURLSpiderJob = context.getBean("downURLSpiderJob", Job.class);
            downURLjobRunner = context.getBean("downURLjobRunner", JobRunner.class);
            downURLSpiderJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("downURL.................................");
            FileInputFormat.setInputPaths(downURLSpiderJob, "/" + file);
            FileOutputFormat.setOutputPath(downURLSpiderJob, new Path("/Spider/" + file +  new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            downURLjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void downURLFromTuniu(String file){
        try {
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
           // context = new ClassPathXmlApplicationContext("applicationContext.xml");
            cartesianMRJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cartesianMRJob", Job.class);
            cartesianjobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cartesianjobRunner", JobRunner.class);
            cartesianMRJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("extraction.................................");
            FileInputFormat.setInputPaths(cartesianMRJob, "/url1.txt");
            FileOutputFormat.setOutputPath(cartesianMRJob, new Path("/" + file));
            cartesianjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractionData(String file) {
        try {
           /* 单元测试使用该方式来获取Bean实例
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            extractionDataSpiderJob = context.getBean("extractionDataSpiderJob", Job.class);
            extractionDatajobRunner = context.getBean("extractionDatajobRunner", JobRunner.class);*/
            extractionDataSpiderJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("extractionDataSpiderJob", Job.class);
            extractionDatajobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("extractionDatajobRunner", JobRunner.class);
            extractionDataSpiderJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("extraction.................................");
            FileInputFormat.setInputPaths(extractionDataSpiderJob, "/Spider/" + file);
            extractionFilePath = new Path("/UnClean/"+  file  + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            FileOutputFormat.setOutputPath(extractionDataSpiderJob, extractionFilePath);
            extractionDatajobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //cleanData  convertData  generateHFile是一连串的操作
    public static  void cleanData(String file) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            cleanDataJob = context.getBean("cleanDataJob", Job.class);
          //  cleanDataJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cleanDataJob", Job.class);
          //  cleanDataJobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("cleanDataJobRunner", JobRunner.class);
            cleanDataJobRunner = context.getBean("cleanDataJobRunner", JobRunner.class);
            cleanDataJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("CLEAN.................................");
            FileInputFormat.setInputPaths(cleanDataJob, new Path("/UnClean/"+file));
            cleanFilePath = new Path("/Clean/" +  file  + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            FileOutputFormat.setOutputPath(cleanDataJob, cleanFilePath);
            cleanDataJobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertData(String file) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            convertDataJob = context.getBean("convertDataJob", Job.class);
          //  convertDataJob = ContextLoaderListener.getCurrentWebApplicationContext().getBean("convertDataJob", Job.class);
            convertDataJobRunner = context.getBean("convertDataJobRunner", JobRunner.class);
           // convertDataJobRunner = ContextLoaderListener.getCurrentWebApplicationContext().getBean("convertDataJobRunner", JobRunner.class);
            convertDataJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
            System.out.println("convertData.................................");
            FileInputFormat.setInputPaths(convertDataJob, new Path("/Clean/"+file));//cleanFilePath
            converFilePath = new Path("/INFO/" +  file);
            FileOutputFormat.setOutputPath(convertDataJob, converFilePath);  //INFO为最终要导入到HBase的数据
            convertDataJobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateHFile(String file) throws Exception {
        Configuration conf = htemplate.getConfiguration();
        HTable table =  new HTable(conf,"TRAVELTEST");
        geneateHFileJob.getInstance(conf);
        geneateHFileJob.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
        System.out.println("generate......................");
        HFileOutputFormat2.configureIncrementalLoad(geneateHFileJob,table,table.getRegionLocator());
        FileInputFormat.setInputPaths(geneateHFileJob,new Path("hdfs://master:9000/澳门INFO"));//
        FileOutputFormat.setOutputPath(geneateHFileJob,new Path("hdfs://master:9000/HFILE/" + "澳门" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        generateHFilejobRunner.call();
    }


    public HbaseTemplate getHtemplate() {
        return htemplate;
    }

    public void setHtemplate(HbaseTemplate htemplate) {
        this.htemplate = htemplate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Path getConverFilePath() {
        return converFilePath;
    }

    public void setConverFilePath(Path converFilePath) {
        this.converFilePath = converFilePath;
    }

    public Path getCleanFilePath() {
        return cleanFilePath;
    }

    public void setCleanFilePath(Path cleanFilePath) {
        this.cleanFilePath = cleanFilePath;
    }

    public Path getExtractionFilePath() {
        return extractionFilePath;
    }

    public void setExtractionFilePath(Path extractionFilePath) {
        this.extractionFilePath = extractionFilePath;
    }

    public Configuration getHadoopConfiguration() {
        return hadoopConfiguration;
    }

    public void setHadoopConfiguration(Configuration hadoopConfiguration) {
        this.hadoopConfiguration = hadoopConfiguration;
    }
}
