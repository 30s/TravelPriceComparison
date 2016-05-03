package com.mobin.putDataToHBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by hadoop on 5/2/16.
 */
public class GenerateHFile {
    public static class GenerateHFileMapper extends Mapper<LongWritable,
            Text,ImmutableBytesWritable,KeyValue>{
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
             String line = value.toString();
            String[] items = line.split(",");
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(items[0].getBytes());
            Put put = new Put(items[0].getBytes());
            put.addColumn("INFO".getBytes(),"USERID".getBytes(),items[1].getBytes());

            KeyValue kv = new KeyValue(items[0].getBytes(),"INFO".getBytes(),"USERID".getBytes(),items[1].getBytes());
            if(null != kv){
                context.write(rowkey,kv);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        HTable table =  new HTable(conf,"test");
        Job job = Job.getInstance(conf);
        job.setJarByClass(GenerateHFile.class);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(KeyValue.class);
        job.setMapperClass(GenerateHFileMapper.class);
        job.setReducerClass(KeyValueSortReducer.class);
        job.setOutputFormatClass(HFileOutputFormat.class);

        HFileOutputFormat.configureIncrementalLoad(job,table);
        FileInputFormat.addInputPath(job,new Path("hdfs://master:9000/Sogou/"));
        FileOutputFormat.setOutputPath(job,new Path("hdfs://master:9000/HFiles"));
        System.exit(job.waitForCompletion(true)?0:1);

    }
}
