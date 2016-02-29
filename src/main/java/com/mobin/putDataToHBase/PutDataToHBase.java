package com.mobin.putDataToHBase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hdfs.tools.GetConf;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.NullOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class PutDataToHBase {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		final String INPUT_PATH= "hdfs://master:9000/ConvertData/part-m-00000";
		Configuration conf = HBaseConfiguration.create();
        Job job=Job.getInstance(conf);
		
		job.setJarByClass(PutDataToHBase.class);
		
		job.setMapperClass(HBaseMapper.class);
		job.setNumReduceTasks(0);
		
		job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "TRAVEL");
		
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Put.class);
		
		FileInputFormat.setInputPaths(job,INPUT_PATH);
		job.setOutputFormatClass(TableOutputFormat.class);


		System.exit(job.waitForCompletion(true)?1:0);
	}
	
	static class HBaseMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put>{


		
		
		protected void map(LongWritable key, Text value,
				Context context)
				throws IOException, InterruptedException {
		      String[] values = value.toString().split("\t");
		      String rowkey = values[1]+values[2]+values[12];
		      Put put = new Put(Bytes.toBytes(rowkey));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("URL"), Bytes.toBytes(values[0]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("SP"), Bytes.toBytes(values[1]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("EP"), Bytes.toBytes(values[2]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("ST"), Bytes.toBytes(values[4]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("ET"), Bytes.toBytes(values[5]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("SIGHTS"), Bytes.toBytes(values[3]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("ALLDATE"), Bytes.toBytes(values[7]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("HOTEL"), Bytes.toBytes(values[6]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("TOTALPRICE"), Bytes.toBytes(values[8]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("TRAFFIC"), Bytes.toBytes(values[9]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("TRAVELTYPE"), Bytes.toBytes(values[10]));
		      put.addColumn(Bytes.toBytes("INFO"),Bytes.toBytes("SUPPLIER"), Bytes.toBytes(values[11]));
		      
		      context.write(new ImmutableBytesWritable(Bytes.toBytes(rowkey)),put);
		}
	}
}
