package com.mobin.downURL;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import us.codecraft.webmagic.Spider;

import java.io.IOException;

/**
 * Created by hadoop on 3/11/16.
 */
public class DownURLMapper extends Mapper<LongWritable,Text,Text,Text> {
    protected void map(LongWritable key, Text value,
                       Context context)
            throws IOException, InterruptedException {
        String place[] = value.toString().split("\t");
        String ends = place[1];  //以逗号分隔  目的地
        String start = place[0];//key
        String end = start + "\t" + ends.split(",")[0];

        String s[] = ends.split(",");
        String urls[] = new String[s.length];
        for (int i = 0; i < s.length; i++) {
            String url = "http://dujia.qunar.com/pqkd/list_" + s[i] + "_all_" + start + "?ti=3&tm=l01_all_search_origin&searchfrom=all";
            urls[i] = url;   //出发点到目的地的所有URL
            System.out.println(url);
        }
        DownURLSpider downURLSpider = new DownURLSpider(start);
        //Spider.create(downURLSpider).addUrl(urls).run()
        Spider.create(downURLSpider).addUrl(urls).thread(10).run();//每一个出发点和目的地就去获取所有记录的URL
        for (String page_recordURL : downURLSpider.getAllPageURL()) {
            System.out.println(page_recordURL);
            context.write(new Text(start), new Text(page_recordURL));

        }
    }
}
