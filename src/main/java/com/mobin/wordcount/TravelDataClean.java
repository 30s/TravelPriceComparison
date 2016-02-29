package com.mobin.wordcount;

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



public class TravelDataClean {
	
	
	public static class TravelDataCleanMapper extends Mapper<LongWritable, Text, Text, Text>{
		
    @Override
    protected void map(LongWritable key, Text value,Context context)
    		throws IOException, InterruptedException {
    	
                  String line=value.toString();
                  
                  if (StringUtils.contains(line, "http")){
                
                      String fields[]=StringUtils.split(line,"\t");
                               
                  if (fields.length>=9) {
                	           	  
                	  String fields1[] = StringUtils.split(line, "?");

          			  String urlId = fields1[0];

          			  String info = fields1[1];
          			  
          			  context.write(new Text(urlId), new Text(info));
				}
                      
               }                          
    }	  
			  
		}
		
		

	public static class TravelDataCleanReducer extends Reducer<Text , Text, Text, NullWritable>{
		
		 @Override
		protected void reduce(Text key, Iterable<Text> values,Context context)
				throws IOException, InterruptedException {
			
			 String value = new String();
				for (Text text : values) {
					value = text.toString();
					break;
				}
				StringBuffer sb=new StringBuffer().append(key).append(value);

				context.write(new Text(sb.toString()), NullWritable.get());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
	    Configuration conf=new Configuration();
		
		Job job=Job.getInstance(conf);
		
		job.setJarByClass(TravelDataClean.class);
		
		job.setMapperClass(TravelDataCleanMapper.class);
		job.setReducerClass(TravelDataCleanReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/aomen-data3/part-r-00000"));
		
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/aomen-data10"));
		
		System.exit(job.waitForCompletion(true)?1:0);
	}
		
}

