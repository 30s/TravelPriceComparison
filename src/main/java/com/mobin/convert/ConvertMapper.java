package com.mobin.convert;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 3/9/16.
 */
public class ConvertMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat f = new SimpleDateFormat("yy年MM月dd日");



    protected void map(LongWritable key, Text value,
                       Context context)
            throws IOException, InterruptedException {
        String[] _info = null;
        String[] info = value.toString().split("\t");
        try {
            _info = change(info[5]);//
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double _hotel = hotel(info[14]);//
        // info[5] = hoteled(info[5]);
        String hotellevel = hoteled(info[14]);//
        double _traffic = traffic(info[8]);  //
        double _sight = info[4].substring(1, info[4].length() - 1).split(",").length * 0.3;//
        double total = (_hotel + _traffic + _sight+555) / Integer.valueOf(info[7]);//
        StringBuffer sb = new StringBuffer();
        String values = sb
                .append(info[0]).append("\t")
                //出发点
                .append(PinyinHelper.convertToPinyinString(info[1].replaceAll("[\\[\\]]", ""),"", PinyinFormat.WITHOUT_TONE)).append("\t")
                //目的地
                .append(PinyinHelper.convertToPinyinString(info[2].replaceAll("[\\[\\]]", ""),"", PinyinFormat.WITHOUT_TONE)).append("\t")
                //标题
                .append(info[3].replaceAll("[\\[\\]]", "")).append("\t")
                //出发时间
                .append(String.valueOf(_info[0])).append("\t")
                //结束时间
                .append(String.valueOf(_info[1])).append("\t")

                //出游天数
                .append(info[6]).append("\t")
                //价格
                .append(info[7]).append("\t")
                //交通方式
                .append(info[8]).append("\t")
                //是否往返
                .append(info[9]).append("\t")
                //是否直达
                .append(info[10]).append("\t")
                //跟团游
                .append(info[11]).append("\t")
                //图片
                .append(info[12]).append("\t")
                //代理
                .append(info[13]).append("\t")
                //酒店级别
                .append(info[14]).append("\t")
                //数据来源
                .append("去哪儿网").append("\t")
                //酒店等级
                .append(hotellevel).append("\t")
                //性价比
                .append(total)
                .toString();
        context.write(NullWritable.get(), new Text(values));
    }

    public String[] change(String info) throws ParseException {
        String ST = "";
        String ET = "";
        String[] s = new String[2];
        if (info.contains("天天")) {
            ST = sdf.format(new Date());
            ET = sdf.format(f.parse("17年01月01日"));
        } else if (info.contains("多团期")) {
            String[] _info = info.substring(0, info.lastIndexOf("多团期")).split("\\...");
            ST = ST(_info[0]);
            ET = ET(_info[1]);
        } else if (info.contains("-")) {
            String[] _info = info.split("-");
            ST = ST(_info[0]);
            ET = ET(_info[1]);
        }
        s[0] = ST;
        s[1] = ET;
        return s;
    }

    public String ST(String st) throws ParseException {
        return sdf.format(f.parse("16年" + st));
    }


    public String ET(String et) throws ParseException {
        return sdf.format(f.parse("16年" + et));
    }

    ////估算酒店星性价比
    public double hotel(String hotel) {
        double _hotel = 0.2;
        if (hotel.contains("经济型")) {
            _hotel = _hotel * 2;
        } else if (hotel.contains("舒适型")) {
            _hotel = _hotel * 3;
        } else if (hotel.contains("高档型")) {
            _hotel = _hotel * 4;
        } else if (hotel.contains("豪华型")) {
            _hotel = _hotel * 5;
        }else{
            _hotel = _hotel * 1;
        }
        return _hotel;
    }

    //判断酒店星级数
    public String hoteled(String hotel) {
        String level = null;
        if (hotel.contains("经济型")) {
            level = "2";
        } else if (hotel.contains("舒适型")) {
            level = "3";
        } else if (hotel.contains("高档型")) {
            level = "4";
        } else if (hotel.contains("豪华型")) {
            level = "5";
        } else
            level = "1";
        return level;
    }

    public double traffic(String traffic) {
        double _traffic = 0.3;
        if (traffic.contains("航班") || traffic.contains("飞机")) {
            _traffic = _traffic * 5;
        } else if (traffic.contains("高铁")) {
            _traffic = _traffic * 4;
        } else if (traffic.contains("火车")) {
            _traffic = _traffic * 3;
        } else if (traffic.contains("大巴")) {
            _traffic = _traffic * 2;
        }
        return _traffic;
    }
}

