package com.mobin.extractionData;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by hadoop on 3/8/16.
 */
public class ExtractionDataMap extends Mapper<LongWritable,Text,Text,Text>{
    protected void map(LongWritable key, Text value,
                       Context context)
            throws IOException, InterruptedException {
        String[] urls = value.toString().split("\\s");
        System.out.println(urls[1]);
        context.write(new Text(urls[0]),new Text(urls[1]));
    }

}
