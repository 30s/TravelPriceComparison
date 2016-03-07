package com.mobin.downURL;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import us.codecraft.webmagic.Spider;

public class DownURLMapReduce {

	public static class DownURLMap extends Mapper<LongWritable,Text,Text,Text>{
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
			String place[] = value.toString().split("\t");
			String ends = place[1];  //以逗号分隔  目的地
			String start = place[0];//key
			String end = start + "\t" + ends.split(",")[0]; 
			
			String s[] = ends.split(",");
			String urls[] = new String[s.length];
			for (int i = 0; i < s.length; i++) {			
				String url = "http://dujia.qunar.com/pqkd/list_"+s[i]+"_all_"+start+"?ti=3&tm=l01_all_search_origin&searchfrom=all";
			    urls[i] = url;   //出发点到目的地的所有URL
				System.out.println(url);
			}
			DownURLSpider downURLSpider = new DownURLSpider(start);
			//Spider.create(downURLSpider).addUrl(urls).run()
			Spider.create(downURLSpider).addUrl(urls).thread(10).run();//每一个出发点和目的地就去获取所有记录的URL
			for(String page_recordURL : downURLSpider.getAllPageURL()){
				System.out.println(page_recordURL);
				context.write(new Text(start),new Text(page_recordURL));
				
			}
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		 final String INPUT_PATH= "hdfs://master:9000/K-out/part-r-00000";
		    final String OUTPUT_PATH = "hdfs://master:9000/K-DATE";
		  Configuration conf = new Configuration();
			
			Job job = Job.getInstance(conf);
			//conf.set("mapred.jar","kk.jar");
			//((JobConf)job.getConfiguration()).setJar(JarFile);
			job.setMapperClass(DownURLMap.class);	
			//job.setReducerClass(CartesianReduce.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			job.setNumReduceTasks(0);
			job.setJarByClass(DownURLMapReduce.class);
			
			FileInputFormat.setInputPaths(job,INPUT_PATH);
			FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
			job.waitForCompletion(true);
	}
}
