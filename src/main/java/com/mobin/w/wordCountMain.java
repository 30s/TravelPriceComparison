package com.mobin.w;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

public class wordCountMain {

	static final String INPUT_PATH= "hdfs://master:9000/wordcount_line4.txt";
	static final String OUTPUT_PATH = "hdfs://master:9000/wor22";
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		Configuration conf = new YarnConfiguration();
		final FileSystem fileSystem = FileSystem.get(new URI(INPUT_PATH), conf);
		if(fileSystem.exists(new Path(OUTPUT_PATH))){
			fileSystem.delete(new Path(OUTPUT_PATH),true);
		}

		conf.set("fs.defaultFS", "hdfs://master:9000");
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.address", "master:8032");
		conf.set("mapred.jar", "/home/hadoop/hadoop-2.6.0/WordCount.jar");
		Job job = new Job(conf, "wordcount");
		job.setMapperClass(Mymaper.class);
		job.setReducerClass(myreduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setJarByClass(wordCountMain.class);


		FileInputFormat.setInputPaths(job,INPUT_PATH);
		FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
		job.waitForCompletion(true);

	}
}
