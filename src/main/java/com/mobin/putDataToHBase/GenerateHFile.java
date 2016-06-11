package com.mobin.putDataToHBase;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

/**
 * Created by hadoop on 5/2/16.
 */
public class GenerateHFile extends Mapper<LongWritable,
        Text, ImmutableBytesWritable, Put>{

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

            //SP-EP-COSTPER
            String ROWKEY = items[1] + items[2] + items[19];
            ImmutableBytesWritable rowkey = new ImmutableBytesWritable(ROWKEY.getBytes());

            Put put = new Put(ROWKEY.getBytes());   //ROWKEY
            put.addColumn("INFO".getBytes(), "URL".getBytes(), items[0].getBytes());
            put.addColumn("INFO".getBytes(), "SP".getBytes(), items[17].getBytes());
            put.addColumn("INFO".getBytes(), "EP".getBytes(), items[18].getBytes());
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
            put.addColumn("INFO".getBytes(), "COSTPER".getBytes(), items[19].getBytes());  ////性价比
            put.addColumn("INFO".getBytes(),"TSPORT".getBytes(),items[10].getBytes());
            //出发点-目的地-价格-随机值
            //SP-PRICE-EP-ROWKEY
            String PRICEROWKEY = items[1] + "PRICE" + items[2] + price + "-" + ROWKEY;
            ImmutableBytesWritable pricerowkey = new ImmutableBytesWritable(PRICEROWKEY.getBytes());
            Put put1 = new Put(PRICEROWKEY.getBytes());
            KeyValue kv = new KeyValue(PRICEROWKEY.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            put1.add(kv);

            //SP-AAA-EP-TDATA-HGRADE-PRICE-ROWKEY
            //出发点-AAA-目的地-出游天数-酒店等级-价格-ROWKEY
            String PRROWKEY = items[1] + "AAA" + items[2] + items[6] + items[16] + price + "-" + ROWKEY;
            ImmutableBytesWritable _PRROWKEY = new ImmutableBytesWritable(PRROWKEY.getBytes());
            Put _PRROWKEYPut = new Put(PRROWKEY.getBytes());
            // _PRROWKEYPut.addColumn("INDEX".getBytes(),"IX".getBytes(),Bytes.toBytes(0));
            KeyValue kv1 = new KeyValue(PRROWKEY.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _PRROWKEYPut.add(kv1);

            //SP-BBB-EP-TDATA-HGRADE-COSTPER-ROWKEY
            //出发点-BBB-目的地-DH-出游天数-酒店等级-性价比-ROWKEY
            String DHROWKEY = items[1] + "BBB" + items[2] + items[6] + items[16] + items[19] + "-" + ROWKEY;
            ImmutableBytesWritable _DHROWKEY = new ImmutableBytesWritable(DHROWKEY.getBytes());
            Put _DHROWKEYPut = new Put(DHROWKEY.getBytes());
            KeyValue kv2 = new KeyValue(DHROWKEY.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _DHROWKEYPut.add(kv2);

            //按酒店等级分组并按性价比排序
            //SP-CCC-EP-HGRADE-COSTPER-ROWKEY
            //出发点-CCC-目的地-酒店等级-性价比-ROWKEY
            String HOTELROWKEY = items[1] + "CCC" + items[2] + items[16] + items[19] + "-" + ROWKEY;
            ImmutableBytesWritable _HOTELROWKEY = new ImmutableBytesWritable(HOTELROWKEY.getBytes());
            Put _HOTELROWKEYPut = new Put(HOTELROWKEY.getBytes());
            KeyValue kv3 = new KeyValue(HOTELROWKEY.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _HOTELROWKEYPut.add(kv3);

            //按酒店等级分组并按性价比排序
            //SP-DDD-EP-HGRADE-COSTPER-ROWKEY
            //出发点-DDD-目的地-出游天数-性价比-ROWKEY
            String TDATAROWKEY = items[1] + "DDD" + items[2] + items[6] + items[19] + "-" + ROWKEY;
            ImmutableBytesWritable _TDATAROWKEY = new ImmutableBytesWritable(TDATAROWKEY.getBytes());
            Put _TDATAROWKEYPut = new Put(TDATAROWKEY.getBytes());
            KeyValue kv4 = new KeyValue(TDATAROWKEY.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _TDATAROWKEYPut.add(kv4);

            //SP-EEE-EP-HGRADE-COSTPER-ROWKEY
            //出发点-EEE-目的地-出游天数-价格-ROWKEY
            String TDATAROWKEY1 = items[1] + "EEE" + items[2] + items[6] + price + "-" + ROWKEY;
            ImmutableBytesWritable _TDATAROWKEY1 = new ImmutableBytesWritable(TDATAROWKEY1.getBytes());
            Put _TDATAROWKEY1Put = new Put(TDATAROWKEY1.getBytes());
            KeyValue kv5 = new KeyValue(TDATAROWKEY1.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _TDATAROWKEY1Put.add(kv5);

            //SP-FFF-EP-HGRADE-COSTPER-ROWKEY
            //出发点-FFF-目的地-酒店等级-价格-ROWKEY
            String HOTELROWKEY1 = items[1] + "FFF" + items[2] + items[16] + price + "-" + ROWKEY;
            ImmutableBytesWritable _HOTELROWKEY1 = new ImmutableBytesWritable(HOTELROWKEY1.getBytes());
            Put _HOTELROWKEY1Put = new Put(HOTELROWKEY1.getBytes());
            KeyValue kv6 = new KeyValue(HOTELROWKEY1.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _HOTELROWKEY1Put.add(kv6);

            //出发点-GGG-目的地-交通等级-价格-ROWKEY
            String TRAFFICROWKEY = items[1] + "GGG" + items[2] + items[20] + price + "-" + ROWKEY;
            ImmutableBytesWritable _TRAFFICROWKEY = new ImmutableBytesWritable(TRAFFICROWKEY.getBytes());
            Put _TRAFFICROWKEYPut = new Put(TRAFFICROWKEY.getBytes());
            KeyValue kv7 = new KeyValue(TRAFFICROWKEY.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _TRAFFICROWKEYPut.add(kv7);

            //出发点-HHH-目的地-交通等级-酒店等级-出游天数-价格-ROWKEY
            String TRAFFICROWKEY1 = items[1] + "HHH" + items[2] + items[20] + items[16] + items[6] + price + "-" + ROWKEY;
            ImmutableBytesWritable _TRAFFICROWKEY1 = new ImmutableBytesWritable(TRAFFICROWKEY1.getBytes());
            Put _TRAFFICROWKEY1Put = new Put(TRAFFICROWKEY1.getBytes());
            KeyValue kv8 = new KeyValue(TRAFFICROWKEY1.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _TRAFFICROWKEY1Put.add(kv8);


            //出发点-KKK-目的地-交通等级-性价比-ROWKEY
            String TRAFFICROWKEY2 = items[1] + "KKK" + items[2] + items[20] + items[19] + "-" + ROWKEY;
            ImmutableBytesWritable _TRAFFICROWKEY2 = new ImmutableBytesWritable(TRAFFICROWKEY2.getBytes());
            Put _TRAFFICROWKEY2Put = new Put(TRAFFICROWKEY2.getBytes());
            KeyValue kv9 = new KeyValue(TRAFFICROWKEY2.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _TRAFFICROWKEY2Put.add(kv9);


            //出发点-LLL-目的地-交通等级-酒店等级-出游天数-性价比-ROWKEY
            String TRAFFICROWKEY3 = items[1] + "LLL" + items[2] + items[20] + items[16] + items[6] + items[19] + "-" + ROWKEY;
            ImmutableBytesWritable _TRAFFICROWKEY3 = new ImmutableBytesWritable(TRAFFICROWKEY3.getBytes());
            Put _TRAFFICROWKEY3Put = new Put(TRAFFICROWKEY3.getBytes());
            KeyValue kv10 = new KeyValue(TRAFFICROWKEY3.getBytes(), "INDEX".getBytes(), "IX".getBytes());
            _TRAFFICROWKEY3Put.add(kv10);



            context.write(rowkey, put);   //基于性价比
            context.write(pricerowkey, put1);   ////基于价格
            context.write(_DHROWKEY, _DHROWKEYPut);  //多列组合查询
            context.write(_PRROWKEY, _PRROWKEYPut);   //多列组合查询

            context.write(_TDATAROWKEY, _TDATAROWKEYPut);   //多列组合查询
            context.write(_TDATAROWKEY1, _TDATAROWKEY1Put);   //多列组合查询

            context.write(_HOTELROWKEY1, _HOTELROWKEY1Put);   //多列组合查询
            context.write(_HOTELROWKEY, _HOTELROWKEYPut);   //多列组合查询

            context.write(_TRAFFICROWKEY, _TRAFFICROWKEYPut);   //多列组合查询
            context.write(_TRAFFICROWKEY1, _TRAFFICROWKEY1Put);   //多列组合查询
            context.write(_TRAFFICROWKEY2, _TRAFFICROWKEY2Put);   //多列组合查询
            context.write(_TRAFFICROWKEY3, _TRAFFICROWKEY3Put);   //多列组合查询


        }
}
