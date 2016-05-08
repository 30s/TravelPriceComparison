package com.mobin.putDataToHBase;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 5/2/16.
 */
public class GenerateHFile {
    public static class GenerateHFileMapper extends Mapper<LongWritable,
            Text, ImmutableBytesWritable,Put> {

        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            Map<ImmutableBytesWritable,Put> map = new HashedMap();
            String line = value.toString();
            String[] items = line.split("\t");

            String ROWKEY = items[1] + items[2] + items[17];
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(ROWKEY.getBytes());

            String price = null;
            //将价格转换为具有相同长度的字段，这样才能达到自然排序的效果
            if (items[7].length() == 1)
                price = "000" + items[7];
            else if (items[7].length() == 2)
                price = "00" + items[7];
            else if (items[7].length() == 3)
                price = "0" + items[7];
            else if (items[7].length() == 4)
                price = items[7];
            String PRICEROWKEY = items[1] + "PRICE" + items[2] + price;
            ImmutableBytesWritable pricerowkey = new ImmutableBytesWritable(PRICEROWKEY.getBytes());
            Put put = new Put(ROWKEY.getBytes());   //ROWKEY

            put.addColumn("INFO".getBytes(), "URL".getBytes(), items[0].getBytes());
            put.addColumn("INFO".getBytes(), "SP".getBytes(), items[1].getBytes());
            put.addColumn("INFO".getBytes(), "EP".getBytes(), items[2].getBytes());
            put.addColumn("INFO".getBytes(), "TITLE".getBytes(), items[3].getBytes());
            put.addColumn("INFO".getBytes(), "TOUATT".getBytes(), items[4].getBytes());  //旅游景点
            put.addColumn("INFO".getBytes(), "ST".getBytes(), items[5].getBytes());
            put.addColumn("INFO".getBytes(), "TDATA".getBytes(), Bytes.toBytes(Integer.valueOf(items[6])));//出游天数
            put.addColumn("INFO".getBytes(), "PRICE".getBytes(), Bytes.toBytes(Integer.valueOf(items[7])));  // (int)
            put.addColumn("INFO".getBytes(), "TRAFFIC".getBytes(), items[8].getBytes());//交通方式
            put.addColumn("INFO".getBytes(), "RETURN".getBytes(), items[9].getBytes());//是否往返
            put.addColumn("INFO".getBytes(), "THROUGH".getBytes(), items[10].getBytes());//是否直达
            put.addColumn("INFO".getBytes(), "TTYPE".getBytes(), items[11].getBytes());  //跟团游
            put.addColumn("INFO".getBytes(), "IMAGE".getBytes(), items[12].getBytes());  //图片
            put.addColumn("INFO".getBytes(), "PROXY".getBytes(), items[13].getBytes());
            put.addColumn("INFO".getBytes(), "HOTEL".getBytes(), items[14].getBytes()); //酒店
            put.addColumn("INFO".getBytes(), "ORIGIN".getBytes(), items[15].getBytes());  //数据来源
            put.addColumn("INFO".getBytes(), "HGRADE".getBytes(), Bytes.toBytes(Integer.valueOf(items[16])));   //酒店级别(int)
            put.addColumn("INFO".getBytes(), "COSTPER".getBytes(), items[17].getBytes());  ////性价比

            Put put1 = new Put(PRICEROWKEY.getBytes());
            put1.addColumn("INFO".getBytes(), "ST".getBytes(), items[5].getBytes());
            put1.addColumn("INFO".getBytes(), "CPERROWKEY".getBytes(), ROWKEY.getBytes());//性价比rowkey

            map.put(rowkey,put);
            map.put(pricerowkey,put);

           // KeyValue kv = new KeyValue(ROWKEY.getBytes(), "INFO".getBytes(), "USERID".getBytes(), items[1].getBytes());
            //if(null != kv){

                context.write(rowkey,put);
                context.write(pricerowkey,put1);


           //  }
        }


    }

    /*public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        HTable table =  new HTable(conf,"TRAVELS");
        Job job = Job.getInstance(conf);
        job.setJarByClass(GenerateHFile.class);
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(KeyValue.class);
        job.setMapperClass(GenerateHFileMapper.class);
        job.setReducerClass(KeyValueSortReducer.class);
        job.setOutputFormatClass(HFileOutputFormat.class);

        HFileOutputFormat.configureIncrementalLoad(job,table);
        FileInputFormat.addInputPath(job,new Path("hdfs://master:9000/澳门INFO"));
        FileOutputFormat.setOutputPath(job,new Path("hdfs://master:9000/澳门HFILE"));
        System.exit(job.waitForCompletion(true)?0:1);

    }*/
}
