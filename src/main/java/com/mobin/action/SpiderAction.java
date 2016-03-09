package com.mobin.action;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.data.hadoop.mapreduce.JobRunner;

import java.io.IOException;

/**
 * Created by hadoop on 3/8/16.
 */
public class SpiderAction {

    private JobRunner downURLjobRunner;
    private JobRunner extractionDatajobRunner;
    private Job downURLSpider;
    private Job extractionDataSpider;

    public  void  downURLByPlace(String place){
        try {
            FileInputFormat.setInputPaths(downURLSpider,place);
            FileOutputFormat.setOutputPath(downURLSpider,new Path("/"+place+"URL"));
            downURLjobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractionData(String place){
        try {
            FileInputFormat.setInputPaths(extractionDataSpider,"/"+place);
            FileOutputFormat.setOutputPath(extractionDataSpider,new Path("/"+place+"DATA"));
            extractionDatajobRunner.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void execute(String place){
        SpiderAction spiderAction = new SpiderAction();
        spiderAction.downURLByPlace(place);
        spiderAction.extractionData(place);
    }

}
