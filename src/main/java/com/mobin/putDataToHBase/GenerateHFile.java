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
import java.util.Random;

/**
 * Created by hadoop on 5/2/16.
 */
public class GenerateHFile {
    public static class GenerateHFileMapper extends Mapper<LongWritable,
            Text, ImmutableBytesWritable,Put> {

        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] items = line.split("\t");

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

            //数据区的行键(ROWKEY)   SP-EP-性价比


            //数据区的行键(ROWKEY)   SP-PRICE-EP-价格-随机值

            //SP-EP-COSTPER
            String ROWKEY = items[1] + items[2] + items[17];
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(ROWKEY.getBytes());



            //出发点-目的地-DH-出游天数-性价比-ROWKEY  => //直接加WHERE


            //出发点-目的地-DH-酒店等级-性价比-ROWKEY   ////直接加WHERE

            Put put = new Put(ROWKEY.getBytes());   //ROWKEY
            put.addColumn("INFO".getBytes(), "URL".getBytes(), items[0].getBytes());
            put.addColumn("INFO".getBytes(), "SP".getBytes(), items[1].getBytes());
            put.addColumn("INFO".getBytes(), "EP".getBytes(), items[2].getBytes());
            put.addColumn("INFO".getBytes(), "TITLE".getBytes(), items[3].getBytes());
            put.addColumn("INFO".getBytes(), "TOUATT".getBytes(), items[4].getBytes());  //旅游景点
            put.addColumn("INFO".getBytes(), "ST".getBytes(), items[5].getBytes());
            put.addColumn("INFO".getBytes(), "TDATA".getBytes(), items[6].getBytes());//出游天数
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




            //出发点-目的地-价格-随机值
            //SP-EP-PRICE-ROWKEY
            String PRICEROWKEY = items[1] + "PRICE" + items[2] + price + ROWKEY;
            ImmutableBytesWritable pricerowkey = new ImmutableBytesWritable(PRICEROWKEY.getBytes());
            Put put1 = new Put(PRICEROWKEY.getBytes());
            KeyValue kv = new KeyValue(PRICEROWKEY.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            put1.add(kv);



            //SP-DH-EP-TDATA-HGRADE-PRICE-ROWKEY
            //出发点-DH-目的地-出游天数-酒店等级-价格-ROWKEY
            String PRROWKEY = items[1] + "AAA" + items[2]  + items[6]  + items[16]  +price +"-"+ ROWKEY;
            ImmutableBytesWritable _PRROWKEY = new ImmutableBytesWritable(PRROWKEY.getBytes());
            Put _PRROWKEYPut = new Put(PRROWKEY.getBytes());
            _PRROWKEYPut.addColumn("INDEX".getBytes(),"IX".getBytes(),Bytes.toBytes(0));
            KeyValue kv1 = new KeyValue(PRROWKEY.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            _PRROWKEYPut.add(kv1);

            //SP-DH-EP-TDATA-HGRADE-COSTPER-ROWKEY
            //出发点-目的地-DH-出游天数-酒店等级-性价比-ROWKEY
            String DHROWKEY = items[1]+ "BBB" + items[2] + items[6] + items[16] + items[17] + "-" + ROWKEY;
            ImmutableBytesWritable _DHROWKEY = new ImmutableBytesWritable(DHROWKEY.getBytes());
            Put _DHROWKEYPut = new Put(DHROWKEY.getBytes());
            KeyValue kv2 = new KeyValue(DHROWKEY.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            _DHROWKEYPut.add(kv2);

            //按酒店等级分组并按性价比排序
            //SP-HOTEL-EP-HGRADE-COSTPER-ROWKEY
            //出发点-HOTEL-目的地-酒店等级-性价比-ROWKEY
            String HOTELROWKEY = items[1]+ "CCC" + items[2] + items[16] + items[17] + "-" + ROWKEY;
            ImmutableBytesWritable _HOTELROWKEY = new ImmutableBytesWritable(HOTELROWKEY.getBytes());
            Put _HOTELROWKEYPut = new Put(HOTELROWKEY.getBytes());
            KeyValue kv3 = new KeyValue(HOTELROWKEY.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            _DHROWKEYPut.add(kv3);


            //按酒店等级分组并按性价比排序
            //SP-HOTEL-EP-HGRADE-COSTPER-ROWKEY
            //出发点-HOTEL-目的地-出游天数-性价比-ROWKEY
            String TDATAROWKEY = items[1]+ "DDD" + items[2] + items[6] + items[17] + "-" + ROWKEY;
            ImmutableBytesWritable _TDATAROWKEY = new ImmutableBytesWritable(TDATAROWKEY.getBytes());
            Put _TDATAROWKEYPut = new Put(TDATAROWKEY.getBytes());
            KeyValue kv4 = new KeyValue(TDATAROWKEY.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            _DHROWKEYPut.add(kv4);


            //SP-HOTEL-EP-HGRADE-COSTPER-ROWKEY
            //出发点-HOTEL-目的地-出游天数-价格-ROWKEY
            String TDATAROWKEY1 = items[1]+ "EEE" + items[2] + items[6] + price+ "-" + ROWKEY;
            ImmutableBytesWritable _TDATAROWKEY1 = new ImmutableBytesWritable(TDATAROWKEY1.getBytes());
            Put _TDATAROWKEY1Put = new Put(TDATAROWKEY1.getBytes());
            KeyValue kv5 = new KeyValue(TDATAROWKEY1.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            _DHROWKEYPut.add(kv5);


            //SP-HOTEL-EP-HGRADE-COSTPER-ROWKEY
            //出发点-HOTEL-目的地-酒店等级-价格-ROWKEY
            String HOTELROWKEY1 = items[1]+ "FFF" + items[2] + items[6] + price+ "-" + ROWKEY;
            ImmutableBytesWritable _HOTELROWKEY1 = new ImmutableBytesWritable(HOTELROWKEY1.getBytes());
            Put _HOTELROWKEY11Put = new Put(HOTELROWKEY1.getBytes());
            KeyValue kv6 = new KeyValue(HOTELROWKEY1.getBytes(),"INDEX".getBytes(),"IX".getBytes());
            _DHROWKEYPut.add(kv6);





            //if(null != kv){

                context.write(rowkey,put);   //基于性价比
                context.write(pricerowkey,put1);   ////基于价格
                context.write(_DHROWKEY,_DHROWKEYPut);  //多列组合查询
                context.write(_PRROWKEY,_PRROWKEYPut);   //多列组合查询


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
