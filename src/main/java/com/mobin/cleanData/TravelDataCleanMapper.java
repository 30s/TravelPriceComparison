package com.mobin.cleanData;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by hadoop on 3/9/16.
 */
public class TravelDataCleanMapper extends Mapper<LongWritable, Text, Text, Text> {
    protected void map(LongWritable key, Text value,Context context)
            throws IOException, InterruptedException {

        String line=value.toString();

        if (StringUtils.contains(line, "http")){

            String fields[]=StringUtils.split(line,"\t");

            if (fields.length>=10) {

                String fields1[] = StringUtils.split(line, "?");

                String urlId = fields1[0];

                String info = fields1[1];

                context.write(new Text(urlId), new Text(info));
            }

        }
    }
}
