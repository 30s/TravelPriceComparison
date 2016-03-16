package com.mobin.test;

import java.io.IOException;

import com.mobin.putDataToHBase.PutDataToHBaseDefault;
import com.mobin.putDataToHBase.PutDataToHBaseSortHotel;
import com.mobin.putDataToHBase.PutDataToHBaseSortPrice;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;


//将出发点  目的地  综合指标作为RowKey
public class PutDataToHBase {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		final String INPUT_PATH= "hdfs://master:9000/澳门ConvertData/part-r-00000";
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		HbaseTemplate template = context.getBean("htemplate",HbaseTemplate.class);
		Configuration conf = HBaseConfiguration.create();
		Job job=Job.getInstance(conf);
		job.setJarByClass(PutDataToHBase.class);
		job.setMapperClass(PutDataToHBaseDefault.class);
		job.setNumReduceTasks(0);
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "VIEWTEST");
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Put.class);
		FileInputFormat.setInputPaths(job,INPUT_PATH);
		job.setOutputFormatClass(TableOutputFormat.class);
		System.exit(job.waitForCompletion(true)?1:0);
	}

}