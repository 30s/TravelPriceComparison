package com.mobin.extractionData;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 3/8/16.
 */
public class ExtractionDataReduce extends Reducer<Text,Text,NullWritable,Text> {
    protected void reduce(Text key, Iterable<Text> values,
                          Context context)
            throws IOException, InterruptedException {

        ExtractionDataSpider extractionDataSpider = new ExtractionDataSpider();
        List<String> urls = new ArrayList<String>();

        for (Text url : values) {
            System.out.println(url);
            urls.add(url.toString());

        }

        Spider.create(extractionDataSpider).thread(10).addUrl(urls.toArray(new String[urls.size()])).run();
        int len = extractionDataSpider.getPageMessage().size();
        System.out.println(9999999);
        for(int i = 0; i < len; i ++){
            System.out.println(extractionDataSpider.getPageMessage().get(i));
            context.write(NullWritable.get(), new Text(extractionDataSpider.getPageMessage().get(i)));
        }
    }
}
