package com.mobin.downURL;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by hadoop on 5/25/16.
 */
public class DownURLReduce  extends Reducer<Text, Text, Text, Text> {
protected void reduce(Text key, Iterable<Text> values,Context context)
        throws IOException, InterruptedException {
//    String value = new String();
//    for (Text text : values) {
//        break;
//    }

    String[] keys = key.toString().split(",");
    context.write(new Text(keys[1]), new Text(keys[0]));
}

}
