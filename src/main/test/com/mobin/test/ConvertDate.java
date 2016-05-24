package com.mobin.test;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;

public class ConvertDate {
    public static void main(String[] args) throws Exception {
        final String INPUT_PATH = "hdfs://master:9000/Clean/澳门URL2016-05-212016-05-21";
        final String OUTPUT_PATH = "hdfs://master:9000/INFO/澳门ConvertData";
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        JobRunner jobRunner = context.getBean("convertDataJobRunner", JobRunner.class);
        Job job = context.getBean("convertDataJob", Job.class);
        FileInputFormat.setInputPaths(job, INPUT_PATH);
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
        jobRunner.call();
    }
}
