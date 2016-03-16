package com.mobin.test;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;


public class TravelDataClean {
	public static void main(String[] args) throws Exception {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		/*JobRunner jobRunner = applicationContext.getBean("downURLjobRunner",JobRunner.class);
		Job job = applicationContext.getBean("downURLSpider",Job.class);*/

		/*JobRunner jobRunner = applicationContext.getBean("extractionDatajobRunner",JobRunner.class);
		Job job = applicationContext.getBean("extractionDataSpider",Job.class);*/


		JobRunner jobRunner = applicationContext.getBean("cleanDataJobRunner",JobRunner.class);
		Job job = applicationContext.getBean("cleanDataJob",Job.class);
		job.getConfiguration().set("mapred.jar","/home/hadoop/TravelProject/out/artifacts/Travel_jar/Travel.jar");
		
		FileInputFormat.setInputPaths(job, "hdfs://master:9000/澳门DATA/part-r-00000");
		
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/澳门INFO1"));

		jobRunner.call();
		
		System.exit(job.waitForCompletion(true)?1:0);
	}
		
}

