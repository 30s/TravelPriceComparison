package com.mobin.dao.impl;

import com.mobin.dao.HBaseDao;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 3/15/16.
 */
public class HBaseDaoImpl implements HBaseDao{

    private HbaseTemplate htemplate;
    private Admin admin;

    public void truncate() {
        Connection conn = null;
        try {
            conn = ConnectionFactory.createConnection(htemplate.getConfiguration());
            List<TableName> tablenames = new ArrayList<TableName>();
            tablenames.add(TableName.valueOf("TRAVEL"));
            tablenames.add(TableName.valueOf("TRAVELSORTHOTEL"));
            tablenames.add(TableName.valueOf("TRAVELSORTPRICE"));
            for (TableName tn : tablenames) {
                admin.disableTable(tn);
                admin.truncateTable(tn,false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                admin.close();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public HbaseTemplate getHtemplate() {
        return htemplate;
    }

    public void setHtemplate(HbaseTemplate htemplate) {
        this.htemplate = htemplate;
    }
}
