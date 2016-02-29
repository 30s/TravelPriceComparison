package com.mobin.wordcount;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class Mymaper extends Mapper<LongWritable,Text,Text,IntWritable>{
	private IntWritable countvalue = new IntWritable(1);
	public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
		String s[] = value.toString().split(",");
		context.write(new Text(s[3]),countvalue);
		
	}

}
