package com.mobin.test;
import java.io.IOException;

import com.mobin.putDataToHBase.GenerateHFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
//将出发点 目的地 综合指标作为RowKey
public class PutDataToHBase {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		final String INPUT_PATH= "hdfs://master:9000/INFO/澳门URL2016-05-212016-05-21";
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		HbaseTemplate template = context.getBean("htemplate",HbaseTemplate.class);

		Configuration conf = template.getConfiguration();
		HTable table =  new HTable(conf,"TRAVELTEST");
		Job job=Job.getInstance(conf);
		job.getConfiguration().set("mapred.jar", "/home/hadoop/TravelProject/out/artifacts/Travel/Travel.jar");
		job.setJarByClass(PutDataToHBase.class);
		job.setMapperClass(GenerateHFile.class);
		job.setMapOutputKeyClass(ImmutableBytesWritable.class);
		job.setMapOutputValueClass(Put.class);
		job.setOutputFormatClass(HFileOutputFormat2.class);

		HFileOutputFormat2.configureIncrementalLoad(job,table,table.getRegionLocator());
		FileInputFormat.addInputPath(job,new Path("hdfs://master:9000/INFO/澳门URL2016-05-212016-05-21"));
		FileOutputFormat.setOutputPath(job,new Path("hdfs://master:9000/HFILE/澳门2016-05-21"));
		System.exit(job.waitForCompletion(true)?0:1);
	}
}