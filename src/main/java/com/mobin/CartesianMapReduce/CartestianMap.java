package com.mobin.CartesianMapReduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by hadoop on 3/8/16.
 */
public class CartestianMap extends Mapper<LongWritable,Text,Text,Text> {
    protected void map(LongWritable key, Text value,
                       Context context)
            throws IOException, InterruptedException {
        context.write(new Text("1"),value);//原样输出给Reduce
    }
}
