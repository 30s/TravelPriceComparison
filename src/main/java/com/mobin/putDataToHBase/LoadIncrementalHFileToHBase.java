package com.mobin.putDataToHBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.io.IOException;

/**
 * Created by hadoop on 5/2/16.
 */
public class LoadIncrementalHFileToHBase {
    public static void main(String[] args) throws Exception {


        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HbaseTemplate template = context.getBean("htemplate",HbaseTemplate.class);

        Configuration configuration = template.getConfiguration();
        System.out.println(configuration);
        System.out.println(template);
        System.out.println(context);
        Connection connection = ConnectionFactory.createConnection(configuration);
       // Table table = connection.getTable(TableName.valueOf("TRAVELTEST"));
        LoadIncrementalHFiles loder = new LoadIncrementalHFiles(configuration);
        loder.doBulkLoad(new Path("hdfs://master:9000/HFILE/澳门2016-05-21"),new HTable(configuration,"TRAVELTEST"));

    }
}
