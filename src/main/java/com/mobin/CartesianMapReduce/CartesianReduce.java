package com.mobin.CartesianMapReduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 3/8/16.
 */
public class CartesianReduce extends Reducer<Text,Text,Text,Text> {
    protected void reduce(Text key2, Iterable<Text> values,
                          Context context)
            throws IOException, InterruptedException {
        int j;
        StringBuffer sbBuffer;
        System.out.println(values.toString());
        List<String> place = new ArrayList<String>();
        // String place[] = new String[561];
        for (Text text : values) {
            //保存reduce中的value
            place.add(text.toString());
        }

        for (int i = 1; i< 2; i ++) {
            sbBuffer = new StringBuffer();
            for(j = 368; j < 564; j ++){
                // System.out.println(place[i]);
                if(j == place.size()){
                    sbBuffer.append(place.get(j));
                }else {
                    //将目的地以逗号为连接符连接起来
                    sbBuffer.append(place.get(j)).append(",");
                }

            }
            System.out.println(place.get(i)+"666"+sbBuffer.toString());
            context.write(new Text(place.get(i)), new Text(sbBuffer.toString()));
        }

    }
}
