package com.mobin.CartesianMapReduce;



import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CartesianMapReduce {

	//产生(出发点    目的地1,目的地2,.....)这样的数据格式
  public static class CartesianMap extends Mapper<LongWritable,Text,Text,Text>{
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		context.write(new Text("1"),value);//原样输出给Reduce
	} 
  }
  
  public static class CartesianReduce extends Reducer<Text,Text,Text,Text>{	  
	  protected void reduce(Text key2, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
		  int j;
		  StringBuffer sbBuffer;
		  System.out.println(values.toString());
		  List<String> place = new ArrayList<String>();
		 // String place[] = new String[561];
		  for (Text text : values) {
			  //保存reduce中的value
			  place.add(text.toString());
		  }
		 
	   for (int i = 347; i< 368; i ++) {
		   sbBuffer = new StringBuffer();		  
		  for(j = 368; j < 564; j ++){
			 // System.out.println(place[i]);
			  if(j == place.size()){
				  sbBuffer.append(place.get(j));
			  }else {
				  //将目的地以逗号为连接符连接起来
				  sbBuffer.append(place.get(j)).append(",");
			}
			 
		  } 
		  System.out.println(place.get(i)+"666"+sbBuffer.toString());
		  context.write(new Text(place.get(i)), new Text(sbBuffer.toString()));
	}
		  
	}
	  
  }
  
  public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
	  final String INPUT_PATH= "hdfs://master:9000/url1.txt";
	    final String OUTPUT_PATH = "hdfs://master:9000/Z-out";
	  Configuration conf = new Configuration();
		final FileSystem fileSystem = FileSystem.get(new URI(INPUT_PATH), conf);
		if(fileSystem.exists(new Path(OUTPUT_PATH))){
			fileSystem.delete(new Path(OUTPUT_PATH),true);
		}
		Job job = new Job(conf,CartesianMapReduce.class.getSimpleName());
		//conf.set("mapred.jar","kk.jar");
		//((JobConf)job.getConfiguration()).setJar(JarFile);
		job.setMapperClass(CartesianMap.class);	
		job.setReducerClass(CartesianReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setJarByClass(CartesianMapReduce.class);
		FileInputFormat.setInputPaths(job,INPUT_PATH);
		FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
		job.waitForCompletion(true);
}
}
