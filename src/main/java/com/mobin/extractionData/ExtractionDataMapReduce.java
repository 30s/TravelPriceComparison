package com.mobin.extractionData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.mobin.downURL.DownURLMapReduce;
import com.mobin.downURL.DownURLMapReduce.DownURLMap;

import us.codecraft.webmagic.Spider;

public class ExtractionDataMapReduce {

	public static class ExtractionDataMap extends Mapper<LongWritable,Text,Text,Text>{
		@Override
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
           String[] urls = value.toString().split("\\s");
           System.out.println(urls[1]);
           context.write(new Text(urls[0]),new Text(urls[1]));		
		}
		
	}
	
	
	public static class ExtractionDataReduce extends Reducer<Text,Text,NullWritable,Text>{
		
		@Override
		protected void reduce(Text key, Iterable<Text> values,
				Context context)
				throws IOException, InterruptedException {
		     
			 ExtractionDataSpider extractionDataSpider = new ExtractionDataSpider();
             List<String> urls = new ArrayList<String>();
			
			for (Text url : values) {
				System.out.println(url);
				urls.add(url.toString());
				
			}			
			
			Spider.create(extractionDataSpider).thread(10).addUrl(urls.toArray(new String[urls.size()])).run();			
			int len = extractionDataSpider.getPageMessage().size();
			System.out.println(9999999);
			for(int i = 0; i < len; i ++){
				System.out.println(extractionDataSpider.getPageMessage().get(i));
				context.write(NullWritable.get(), new Text(extractionDataSpider.getPageMessage().get(i)));
			}
		}	
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		 final String INPUT_PATH= "hdfs://master:9000/K-DATE/part-m-00000";
		    final String OUTPUT_PATH = "hdfs://master:9000/K-DATE1";
		  Configuration conf = new Configuration();
			
			Job job = Job.getInstance(conf);
			//conf.set("mapred.jar","kk.jar");
			//((JobConf)job.getConfiguration()).setJar(JarFile);
			job.setMapperClass(ExtractionDataMap.class);	
			job.setReducerClass(ExtractionDataReduce.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			job.setJarByClass(ExtractionDataMapReduce.class);
		//	job.setNumReduceTasks(3);
			
			FileInputFormat.setInputPaths(job,INPUT_PATH);
			FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
			job.waitForCompletion(true);
	}
}
