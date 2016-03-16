package com.mobin.cleanData;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by hadoop on 3/9/16.
 */
public class TravelDataCleanReducer extends Reducer<Text , Text, Text, NullWritable> {
    protected void reduce(Text key, Iterable<Text> values,Context context)
            throws IOException, InterruptedException {

        String value = new String();
        for (Text text : values) {
            value = text.toString();
            break;
        }
        context.write(new Text(value), NullWritable.get());
    }
}
