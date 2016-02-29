package com.mobin.convert;


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
public class ConvertDate {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
   	 final String INPUT_PATH= "hdfs://master:9000/aomen-data3/part-r-00000";
		    final String OUTPUT_PATH = "hdfs://master:9000/ConvertData";
		    Configuration conf = new Configuration();  
	      // conf.setBoolean("mapreduce.app-submission.cross-platform",true);
	       conf.set("mapred.jar","/home/hadoop/WordCount.jar");
			
			Job job = Job.getInstance(conf);
			job.setMapperClass(ConvertMapper.class);	
			job.setOutputKeyClass(NullWritable.class);
			job.setOutputValueClass(Text.class);
			job.setNumReduceTasks(0);
			job.setJarByClass(ConvertDate.class);
			
			FileInputFormat.setInputPaths(job,INPUT_PATH);
			FileOutputFormat.setOutputPath(job,new Path(OUTPUT_PATH));
			job.waitForCompletion(true);
	}
	
        static class ConvertMapper extends Mapper<LongWritable,Text, NullWritable,Text>{
        	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			private SimpleDateFormat f = new SimpleDateFormat("yy年MM月dd日");
        	
        	protected void map(LongWritable key, Text value,
        			Context context)
        			throws IOException, InterruptedException {
        		String[] _info = null;
                    String[] info = value.toString().split("\t");
                    System.out.println(info[4]);
                                
                    try {
						_info =  change(info[4]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
               
                    double _hotel = hotel(info[5]);
                    double _traffic =  traffic(info[8]);
                    double _sight = info[3].substring(1,info[3].length() - 1).split(",").length * 0.3;
                    double total = (_hotel+_traffic+_sight)/Integer.valueOf(info[7]);
                    StringBuffer sb = new StringBuffer();
                    String values = sb.append(info[0]).append("\t")
                    		          .append(info[1].replaceAll("[\\[\\]]", "")).append("\t")
                    		          .append(info[2].replaceAll("[\\[\\]]", "")).append("\t")
                    		          .append(info[3]).append("\t")
                    		          .append(String.valueOf(_info[0])).append("\t")
                    		          .append(String.valueOf(_info[1])).append("\t")
                    		          .append(info[5]).append("\t")
                    		          .append(info[6]).append("\t")
                    		          .append(info[7]).append("\t")
                    		          .append(info[8]).append("\t")
                    		          .append(info[9]).append("\t")
                    		          .append(info[10]).append("\t")
                    		          .append(total)
                    		          .toString();   
                    context.write(NullWritable.get(), new Text(values));
        	}
        	 public String[] change(String info) throws ParseException{
        		 String ST = "";
        		 String ET = "";
        		 String[] s = new String[2];
        		 if(info.contains("天天")){
        		     ST = sdf.format(new Date());
					 ET = sdf.format(f.parse("17年01月01日"));
        		 }else if (info.contains("多团期")) {
				     String[] _info = info.substring(0, info.lastIndexOf("多团期")).split("\\...");
				     ST = ST(_info[0]);
				     ET = ET(_info[1]);
				 }else if (info.contains("-")) {
					String[] _info = info.split("-");
					ST = ST(_info[0]);
				    ET = ET(_info[1]);
				}
             	s[0] = ST;
             	s[1] = ET;
             	return s;
             }
        	 
        	 public String ST(String st) throws ParseException{
        		 if(st.contains("16年")){
					 return sdf.format(f.parse(st));
				     }else{
					 return sdf.format(f.parse("15年"+st));
				     }
        		 
        	 }
        	 
        	 
        	 public String ET(String et) throws ParseException{
        		 if(et.contains("16年")){
					 return sdf.format(f.parse(et));
				     }else{
					 return sdf.format(f.parse("15年"+et));
				     }
        		 
        	 }
        	 
        	 public double hotel(String hotel) {
        		 double _hotel = 0.2;
     			if(hotel.contains("经济型")){
     				_hotel = _hotel*2;
     			}else if(hotel.contains("舒适型")){
     				_hotel = _hotel*3;
				}else if (hotel.contains("高档型")) {
					_hotel = _hotel*4;
				}else if (hotel.contains("豪华型")) {
					_hotel = _hotel*5;
				}
     			return _hotel;
     		}
        	 
        	 public double traffic(String traffic) {
        		double _traffic = 0.3;
     		    if(traffic.contains("航班")|| traffic.contains("飞机")){
     		    	_traffic = _traffic*5;
     		    }else if (traffic.contains("高铁")) {
     		    	_traffic = _traffic*4;
				}else if (traffic.contains("火车")) {
					_traffic = _traffic*3;
				}else if (traffic.contains("大巴")) {
					_traffic = _traffic*2;
				}
     			return _traffic;
     		}
        }       
}
