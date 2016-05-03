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

import java.io.IOException;

/**
 * Created by hadoop on 5/2/16.
 */
public class LoadIncrementalHFileToHBase {
    public static void main(String[] args) throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("test"));
        LoadIncrementalHFiles loder = new LoadIncrementalHFiles(configuration);
        loder.doBulkLoad(new Path("hdfs://master:9000/HFile"),new HTable(configuration,"test"));

    }
}
